package vip.freddie.metro.example;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import vip.freddie.metro.packet.IPacket;

@NoArgsConstructor
@AllArgsConstructor
public class TestPacket implements IPacket {

    private String key;
    private int value;

    @Override
    public void onReceive() {
        System.out.println("Received [" + this.key + "] with value [" + this.value + "]");
    }

}