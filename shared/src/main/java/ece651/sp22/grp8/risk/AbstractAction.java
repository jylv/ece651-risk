package ece651.sp22.grp8.risk;

import java.io.Serializable;

public abstract class AbstractAction implements Serializable {

    public long playerId;

    public long getPlayerId() {
        return playerId;
    }

    AbstractAction(long playerId) {
        this.playerId = playerId;
    }

}
