package vip.freddie.metro.packet;

import com.google.gson.JsonObject;
import redis.clients.jedis.Jedis;
import vip.freddie.metro.Metro;
import vip.freddie.metro.MetroPubSub;

public class PacketHandler {

    private final Metro metro;

    public PacketHandler(Metro metro) {
        this.metro = metro;

        try (Jedis jedis = this.metro.getResource()) {
            jedis.subscribe(new MetroPubSub(), MetroPubSub.CHANNEL);
        }
    }

    public void sendPacket(IPacket packet) {
        new Thread(() -> {
            JsonObject object = Metro.GSON.toJsonTree(packet).getAsJsonObject();
            object.addProperty("packet__class", packet.getClass().getName());

            this.metro.sendMessage(MetroPubSub.CHANNEL, object);
        }).start();
    }

}