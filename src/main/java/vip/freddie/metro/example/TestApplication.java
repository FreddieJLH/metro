package vip.freddie.metro.example;

import lombok.Getter;
import redis.clients.jedis.JedisPool;
import vip.freddie.metro.Metro;

@Getter
public class TestApplication {

    private final Metro metro;

    public TestApplication() {
        JedisPool jedisPool = new JedisPool("127.0.0.1", 6379);
        this.metro = new Metro(jedisPool);

        TestPacket testPacket = new TestPacket("Test", 5);
        this.metro.sendPacket(testPacket);
    }

    public static void main(String[] args) {
        new TestApplication();
    }

}