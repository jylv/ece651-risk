package ece651.sp22.grp8.risk;

import java.io.Serializable;

public class PlayerPacket implements Packet, Serializable {
    Player p;

    public PlayerPacket(Player p) {
        this.p = p;
    }


    @Override
    public Player getObject() {
        return this.p;
    }

    @Override
    public void packPacket(Object obj) {
        Player packed = (Player)obj;
        this.p = packed;
    }
}
