package nagi.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

public class JedisDemo {
    private Jedis jedis = new Jedis("192.168.56.101", 6379);

    @Test
    public void stringOperations() {
        jedis.set("nagi", "hello");
        System.out.println(jedis.get("nagi"));
    }
}
