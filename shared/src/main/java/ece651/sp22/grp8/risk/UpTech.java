package ece651.sp22.grp8.risk;

public class UpTech extends AbstractAction{
    private boolean upTech;

    public UpTech(long playerID,boolean upTech) {
        super(playerID);
        this.upTech = upTech;
    }

    public boolean isUpTech() {
        return upTech;
    }

    @Override
    public String toString() {
        return "player: " + playerId + " wants to upgrade tech: "+upTech;
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass().equals(getClass())) {
            UpTech upTech = (UpTech)o;
            return upTech.toString().equals(toString());
        }

        return false;
    }
}
