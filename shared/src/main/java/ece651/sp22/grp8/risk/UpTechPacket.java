package ece651.sp22.grp8.risk;

import java.io.Serializable;

public class UpTechPacket implements Packet, Serializable {
    private UpTech upTech;

    public UpTechPacket(UpTech upTech) {
        this.upTech = upTech;
    }

    @Override
    public void packPacket(Object upTech) {
        UpTech packedUpTech = (UpTech)upTech;
        this.upTech = packedUpTech;
    }

    @Override
    public UpTech getObject() {
        return upTech;
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass().equals(getClass())) {
            UpTechPacket up = (UpTechPacket) o;
            return up.toString().equals(toString());
        }
        return false;
    }

    @Override
    public String toString() {
        return upTech.toString();
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}

