package ece651.sp22.grp8.risk;

public class Leave extends AbstractAction {
    /**
     * True means disconnect with the game,
     * and False means continue to watch
     */
    Boolean leave;

    public Leave(long playerID, boolean leave){
        super(playerID);
        this.leave = leave;
    }

    public Boolean getLeave() {
        return leave;
    }

    @Override
    public String toString() {
        return "player: " + playerId + ", leave: " +  leave;
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass().equals(getClass())) {
            Leave leave = (Leave)o;
            return leave.toString().equals(toString());
        }
        return false;
    }
}
