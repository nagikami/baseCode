package nagi.redis;

import redis.clients.jedis.Jedis;

import java.util.Random;

public class VerifyCode {
    private Jedis jedis = new Jedis("192.168.56.101", 6379);
    private Random random = new Random();


    public static void main(String[] args) {
        String phoneNumber = "18267899876";
        VerifyCode verifyCode = new VerifyCode();
        //verifyCode.sendCode(phoneNumber);
        verifyCode.verify(phoneNumber, "74137");
    }

    public void verify(String phoneNumber, String code) {
        String verifyKey = "VerifyCode:" + phoneNumber;
        String trueCode = jedis.get(verifyKey);
        if (code.equals(trueCode)) {
            System.out.println("Verify passed");
        } else {
            System.out.println("Verify failed");
        }
    }

    public void sendCode(String phoneNumber) {
        String verifyKeyCount = "VerifyCode:" + phoneNumber + ":count";
        String verifyKey = "VerifyCode:" + phoneNumber;
        String count = jedis.get(verifyKeyCount);
        if (count != null && Integer.parseInt(count) > 2) {
            System.out.println("times over 3");
            return;
        }
        String code = getCode();
        jedis.set(verifyKey, code);
        jedis.incr(verifyKeyCount);
    }

    public String getCode() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            stringBuilder.append(random.nextInt(10));
        }
        return stringBuilder.toString();
    }
}
