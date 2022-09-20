package com.nagi;

import io.grpc.Channel;
import io.grpc.StatusRuntimeException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SignClient {
    private static final Logger logger = Logger.getLogger(com.nagi.SignGrpc.class.getName());

    private final com.nagi.SignGrpc.SignBlockingStub blockingStub;

    public SignClient(Channel channel) {
        blockingStub = com.nagi.SignGrpc.newBlockingStub(channel);
    }

    public com.nagi.SignReply sign(String accessKey, String secretKey) {
        com.nagi.SignRequest request = com.nagi.SignRequest.newBuilder().setAccessKey(accessKey).setSecretKey(secretKey).build();
        com.nagi.SignReply response = null;
        try {
            response = blockingStub.signUser(request);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
        }
        return response;
    }
}
