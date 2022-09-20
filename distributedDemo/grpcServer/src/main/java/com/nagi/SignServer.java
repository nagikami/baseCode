package com.nagi;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Server that manages startup/shutdown of a {@code Greeter} server.
 */
@Slf4j
public class SignServer {
    private static final Logger logger = Logger.getLogger(SignServer.class.getName());

    private Server server;

    private void start() throws IOException {
        /* The port on which the server should run */
        int port = 60000;
        server = ServerBuilder.forPort(port)
                .addService(new SignImpl())
                .build()
                .start();
        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Use stderr here since the logger may have been reset by its JVM shutdown hook.
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            try {
                SignServer.this.stop();
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
            System.err.println("*** server shut down");
        }));
    }

    private void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * Main launches the server from the command line.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        final SignServer server = new SignServer();
        server.start();
        server.blockUntilShutdown();
    }

    static class SignImpl extends com.nagi.SignGrpc.SignImplBase {

        @Override
        public void signUser(com.nagi.SignRequest req, StreamObserver<com.nagi.SignReply> responseObserver) {
            String accessKey = req.getAccessKey();
            String secretKey = req.getSecretKey();
            com.nagi.SignReply reply;
            boolean isOk = false;
            String message;
            if (isUser(accessKey)) {
                message = "user already existed";
            } else {
                cmdExe("mc admin user add local " + accessKey + " " + secretKey);
                cmdExe("mc mb local/" + accessKey);
                isOk = true;
                if(!setPolicy(accessKey)){
                    isOk = false;
                    message = "set policy failed";
                } else {
                    message = "user added successfully";
                }
            }
            reply = com.nagi.SignReply.newBuilder().setIsOk(isOk).setMessage(message).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }

        private List<String> userList() {
            List<String> result = cmdExe("mc admin user list local");
            List<String> userList = new ArrayList<>();
            for (String u : result) {
                if (u.contains("enabled")) {
                    String[] split = u.split("\\s{2,}");
                    userList.add(split[1]);
                }
            }
            return userList;
        }

        private boolean isUser(String user) {
            List<String> userList = userList();
            for (String u : userList) {
                if (u.equals(user)) {
                    return true;
                }
            }
            return false;
        }

        public boolean setPolicy(String accessKey) {
            if (isUser(accessKey)) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Version", "2012-10-17");
                JSONArray statements = new JSONArray();
                JSONObject statement = new JSONObject();
                statement.put("Effect", "Allow");
                JSONArray actionArray = new JSONArray();
                actionArray.add("s3:*");
                statement.put("Action", actionArray);
                JSONArray resource = new JSONArray();
                resource.add("arn:aws:s3:::" + accessKey + "/*");
                statement.put("Resource", resource);
                statements.add(statement);
                jsonObject.put("Statement", statements);
                String policy = jsonObject.toJSONString();
                return set(policy, accessKey);
            } else {
                return false;
            }
        }

        private boolean set(String policy, String user) {
            if (policy.contains("\"Statement\":[]")) {
                cmdExe("mc admin policy remove local/ " + user);
                log.error("Statement is null");
                return false;
            }
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File("policy.json")))) {
                bw.write(policy);
            } catch (IOException e) {
                log.error("write failed", e);
                return false;
            }
            cmdExe("cp ./policy.json /tmp/");
            cmdExe("mc admin policy add local/ " + user + " /tmp/policy.json");
            cmdExe("mc admin policy set local/ " + user + " user=" + user);
            return true;
        }

        private List<String> cmdExe(String cmd) {
            ProcessBuilder pb;
            Process process = null;
            String[] cmdList = cmd.split(" ");
            List<String> result = new ArrayList<>();
            try {
                pb = new ProcessBuilder(cmdList);
                pb.redirectErrorStream(true);
                process = pb.start();
            } catch (Exception e) {
                log.error(cmd  + " executed failed", e);
            }
            ProcessWorker processWorker = new ProcessWorker(process);
            processWorker.start();
            try {
                processWorker.join(60 * 1000);
                if (processWorker.isCompleted()) {
                    result = processWorker.getResult();
                } else {
                    assert process != null;
                    process.destroy();
                    processWorker.interrupt();
                    result = processWorker.getResult();
                    log.error("timed out");
                }
            } catch (InterruptedException e) {
                log.error("Thread is interrupted", e);
                processWorker.interrupt();
            }
            return result;
        }

        private static class ProcessWorker extends Thread {
            private final Process process;
            private final List<String> result = new ArrayList<>();
            private volatile boolean completed;

            private ProcessWorker(Process process) {
                this.process = process;
            }

            @Override
            public void run() {
                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String str;
                    while ((str = bufferedReader.readLine()) != null) {
                        result.add(str);
                    }
                    completed = true;
                } catch (Exception e) {
                    log.error("read process output stream failed", e);
                }
            }

            public List<String> getResult() {
                return result;
            }

            public boolean isCompleted() {
                return completed;
            }
        }
    }
}
