package ece651.sp22.grp8.risk;

import java.util.HashSet;

public class ActionResult extends AbstractAction{
    private HashSet<String> getTerri; //attack and get territories
    private HashSet<String> lostTerri; //lost territories
    private String gameStatus;

    /**
     * constructor
     */
    public ActionResult(long PlayerID,String gameStatus){
        super(PlayerID);
        this.getTerri=new HashSet<>();
        this.lostTerri=new HashSet<>();
        this.gameStatus = gameStatus;
    }

    public void addGetTerri(String s){
        getTerri.add(s);
    }

    public void addLostTerri(String s){
        lostTerri.add(s);
    }

    public String getGameStatus(){return gameStatus;}

    /**
     * getter
     */
    public HashSet<String> getGetTerri() {
        return getTerri;
    }

    public HashSet<String> getLostTerri() {
        return lostTerri;
    }

    @Override
    public String toString() {
        return "playerId: " + playerId + " getTerritories: " + getTerri + ", loseTerritories: " + lostTerri;
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass().equals(getClass())) {
            ActionResult aR = (ActionResult) o;
            return aR.toString().equals(toString());
        }

        return false;
    }
}
