package vip.freddie.metro;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.Getter;
import redis.clients.jedis.*;
import vip.freddie.metro.packet.*;

@Getter
public class Metro {

    public static final Gson GSON = new Gson();

    private final JedisPool jedisPool;
    private final PacketHandler packetHandler;

    public Metro(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
        this.packetHandler = new PacketHandler(this);
    }

    public void sendPacket(IPacket packet) {
        this.packetHandler.sendPacket(packet);
    }

    public void sendMessage(String channel, JsonObject message) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            jedis.publish(channel, Metro.GSON.toJson(message));
        }
    }

    public Jedis getResource() {
        return this.jedisPool.getResource();
    }

}