package ece651.sp22.grp8.risk;

import java.io.Serializable;

public class UpUnitPacket implements Packet, Serializable {
    private UpUnit upUnit;

    public UpUnitPacket(UpUnit upUnit) {
        this.upUnit = upUnit;
    }

    @Override
    public void packPacket(Object upUnit) {
        UpUnit packedUpUnit = (UpUnit) upUnit;
        this.upUnit = packedUpUnit;
    }

    @Override
    public UpUnit getObject() {
        return upUnit;
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass().equals(getClass())) {
            UpUnitPacket up = (UpUnitPacket) o;
            return up.toString().equals(toString());
        }
        return false;
    }

    @Override
    public String toString() {
        return upUnit.toString();
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}

