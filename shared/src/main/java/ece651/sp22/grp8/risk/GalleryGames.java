package ece651.sp22.grp8.risk;

import java.util.ArrayList;
import java.util.HashMap;

public class GalleryGames extends AbstractAction{
    private final HashMap<Long, ArrayList<Long>> games;

    /**
     * constructor
     */
    public GalleryGames(long PlayerID,  HashMap<Long, ArrayList<Long>> games){
        super(PlayerID);
        this.games = games;
    }

    public HashMap<Long, ArrayList<Long>> getGames(){
        return games;
    }

    @Override
    public String toString() {
        return "games: " + games;
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass().equals(getClass())) {
            GalleryGames gg = (GalleryGames) o;
            return gg.toString().equals(toString());
        }
        return false;
    }
}
