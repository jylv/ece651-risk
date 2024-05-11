package ece651.sp22.grp8.risk;

import java.io.Serializable;

public class LeavePacket implements Packet, Serializable {
    private Leave leave;

    public LeavePacket() {
        this.leave = null;
    }

    public LeavePacket(Leave leave) {
        this.leave = leave;
    }

    @Override
    public Object getObject() {
        return leave;
    }

    @Override
    public void packPacket(Object obj) {
        this.leave = (Leave) obj;
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass().equals(getClass())) {
            LeavePacket lp = (LeavePacket)o;
            return lp.toString().equals(toString());
        }
        return false;
    }

    @Override
    public String toString() {
        return leave.toString();
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
