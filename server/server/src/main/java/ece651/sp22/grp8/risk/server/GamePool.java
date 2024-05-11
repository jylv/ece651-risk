package ece651.sp22.grp8.risk.server;

import ece651.sp22.grp8.risk.Map;

import java.util.ArrayList;
import java.util.HashMap;

public class GamePool {
    private final HashMap<Long,Game> games = new HashMap<>();; //gameID, game
    private final HashMap<Long, ArrayList<Long>> gameGallery = new HashMap<>(); //username, gameID list
    /**
     * Create a new pool to store games
     */
    public GamePool(){
        this.gameGallery.put(0L,new ArrayList<>()); //to store game without any player
    }

    public HashMap<Long, ArrayList<Long>> getGameGallery() {
        return gameGallery;
    }

    public Game getGame(long gameID){
        return games.get(gameID);
    }

    public void addGame(long GameID, Game game){
        games.put(GameID,game);
    }

    public Map getGameMap(long gameID){
        return games.get(gameID).getMap();
    }

    public boolean containGame(long gameID){
        return games.containsKey(gameID);
    }
}
