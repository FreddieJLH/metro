package vip.freddie.metro;

import com.google.gson.JsonObject;
import redis.clients.jedis.JedisPubSub;
import vip.freddie.metro.packet.IPacket;

public class MetroPubSub extends JedisPubSub {

    public static final String CHANNEL = "METRO-PACKET";

    @Override
    public void onMessage(String channel, String message) {
        if (!channel.equalsIgnoreCase(CHANNEL))
            return;

        try {
            JsonObject object = Metro.GSON.fromJson(message, JsonObject.class);
            String className = object.get("packet__class").getAsString();

            Class<?> aClass = Class.forName(className);
            object.remove("packet__class");

            IPacket packet = (IPacket) Metro.GSON.fromJson(object.toString(), aClass);
            packet.onReceive();
        } catch (Exception ignored) {}
    }

}