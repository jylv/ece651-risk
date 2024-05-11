package ece651.sp22.grp8.risk;

public class UpUnit extends AbstractAction{
    private String territory;
    private int oriLevel;
    private int num;
    private int destLevel;


    public UpUnit(long playerID,String territory,int oriLevel,int num,int destLevel) {
        super(playerID);
        this.territory = territory;
        this.oriLevel = oriLevel;
        this.num = num;
        this.destLevel = destLevel;
    }

    public String getTerritory() {
        return territory;
    }

    public int getOriLevel() {
        return oriLevel;
    }

    public int getNum() {
        return num;
    }

    public int getDestLevel() {
        return destLevel;
    }

    @Override
    public String toString() {
        return "player: " + playerId + " want to upgrade "+
                num+" units from level "+oriLevel+" to level "+destLevel+" in "+territory;
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass().equals(getClass())) {
            UpUnit upUnit = (UpUnit)o;
            return upUnit.toString().equals(toString());
        }

        return false;
    }
}
