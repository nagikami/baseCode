package nagi.java.bio;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BioServer {
    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(9999);

        for (;;) {
            final Socket socket = serverSocket.accept();
            executorService.submit(() -> handler(socket));
        }
    }

    public static void handler(Socket socket) {
        try (InputStream inputStream = socket.getInputStream()) {
            byte[] bytes = new byte[1024];
            int read;
            System.out.println("current thread" + Thread.currentThread().getName());
            while ((read = inputStream.read(bytes)) != -1) {
                System.out.println(new String(bytes, 0, read, StandardCharsets.UTF_8));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
