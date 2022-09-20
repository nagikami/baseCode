package com.nagi;

import com.alibaba.fastjson2.JSONObject;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class SignController {
    @GetMapping("sign")
    @CrossOrigin
    public JSONObject sign(@RequestParam String accessKey, @RequestParam String secretKey) throws Exception{
        String target = "localhost:60000";
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
                .usePlaintext()
                .build();
        try {
            SignClient client = new SignClient(channel);
            com.nagi.SignReply sign = client.sign(accessKey, secretKey);
            boolean isOk = false;
            String message = "reply is null";
            if(sign != null){
                isOk = sign.getIsOk();
                message = sign.getMessage();
            }
            JSONObject result = new JSONObject();
            result.put("status", isOk);
            result.put("message", message);
            return result;
        } finally {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
