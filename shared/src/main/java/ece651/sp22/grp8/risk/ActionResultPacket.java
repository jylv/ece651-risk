package ece651.sp22.grp8.risk;

import java.io.Serializable;

public class ActionResultPacket implements Packet, Serializable {
    private ActionResult actionResult;

    public ActionResultPacket(ActionResult actionResult) {
        this.actionResult = actionResult;
    }

    @Override
    public void packPacket(Object actionResult) {
        ActionResult packedAR = (ActionResult)actionResult;
        this.actionResult = packedAR;
    }

    @Override
    public ActionResult getObject() {
        return this.actionResult;
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass().equals(getClass())) {
            ActionResultPacket arp = (ActionResultPacket) o;
            return arp.toString().equals(toString());
        }
        return false;
    }

    @Override
    public String toString() {
        return actionResult.toString();
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

}
