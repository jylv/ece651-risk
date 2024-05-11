package ece651.sp22.grp8.risk.server;

import ece651.sp22.grp8.risk.*;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Game {
    private final int playerNum;
    private final long gameID;
    private long ownerID = 0L;
    private final MapFactory mapFactory;
    private Map map;
    private final HashMap<String,Integer> status = new HashMap<>();
    private final HashMap<Long,Player> players;
    private final ActionHandler actionHandler;
    private HashMap<Long, ActionResult> resultOneTurn;


    /**
     * Create a new game with given player number and gameID
     * @param playerNum is the number of players
     * @param gameID is the key for a game
     */
    public Game(int playerNum, long gameID){
        this.playerNum = playerNum;
        this.gameID = gameID;
        this.mapFactory = new MapFactory();
        this.players = new HashMap<>();
        this.resultOneTurn = new HashMap<>();
        this.actionHandler = new ActionHandler();
        initStatus();
    }

    public long getOwnerID() {
        return ownerID;
    }

    private void initStatus() {
        status.put("SETUP",0);
        status.put("INIT_UNIT",0);
        status.put("ACTION",0);
        status.put("RESOLVE",0);
        status.put("LEAVE",0);
    }

    /**
     * Check whether one player own all the territories
     * @return the winner player ID or null if there is
     * no winner.
     */
    public String checkGameWinner() {
        for (Player p : players.values()) {
            //todo: add if others disconnect, win
            if (map.getTerritoriesByOwnerId(p.getPlayerID()).size() == map.getTerritorySize()) {
                return p.getColor();
            }
        }
        return null;
    }

    public void combatAndUpdate(){
        //combat
        actionHandler.handleAttack(map,players);
        //add one unit in each territory as the end of each turn
        actionHandler.updateOneTurn(map);
        // update player resources
        actionHandler.updateResource(map,players);
        // update player tech level if th orders valid
        actionHandler.updateLevel(players);
        //update attack result info of each player
        handleResult();
    }

    public void handleResult(){
        //initialize new result for this turn
        resultOneTurn = new HashMap<>();
        if(checkGameWinner()!=null){ //there is a winner, game over
            for(long playerID:players.keySet()){
                if(Objects.equals(map.getAssign(playerID), checkGameWinner())){
                    resultOneTurn.put(playerID,new ActionResult(playerID,"WIN"));
                }else{
                    resultOneTurn.put(playerID,new ActionResult(playerID,"END"));
                }
            }
        }else{ // game continue
            for(long playerID:players.keySet()){
                if(map.getTerritoriesByOwnerId(playerID).size() == 0){
                    resultOneTurn.put(playerID,new ActionResult(playerID,"LOSE"));
                }else{
                    resultOneTurn.put(playerID,new ActionResult(playerID,"CONTINUE"));
                }
            }
        }
        //add attack result information
        for(String t: map.attackResult.keySet()){
            long oldOwner = map.attackResult.get(t);
            long newOwner = map.getTerritoryByName(t).getOwner().getPlayerID();
            resultOneTurn.get(oldOwner).addLostTerri(t);
            resultOneTurn.get(newOwner).addGetTerri(t);
        }
        //clear map attackResult at this turn
        map.attackResult.clear();
    }

    public HashMap<Long, ActionResult> getResultOneTurn() {
        return resultOneTurn;
    }

    public int getStatusNum(String status){
        return this.status.get(status);
    }

    /**
     * Equal function only check whether they have the same
     * gameID, not the same status
     * @param other
     * @return
     */
    @Override
    public boolean equals(Object other){
        if(other != null && other.getClass().equals(getClass())){
            return gameID == ((Game) other).getGameID();
        }
        return false;
    }

    @Override
    public String toString() {
        return "Game{" +
                "gameID=" + gameID +
                '}';
    }

    /**
     * Use GameID (converted to string) as hashcode
     * @return
     */
    @Override
    public int hashCode() {
        return Long.toString (gameID).hashCode();
    }

    public int getPlayerNum(){return playerNum;}

    public synchronized void updateStatusNum(String status, int diff){ this.status.put(status,this.status.get(status)+diff);}

    public long getGameID() {
        return gameID;
    }

    /**
     * Get a player object given player ID
     * @param playerID is the player ID
     * @return this player if it's here, or null if it's not here
     */
    public Player getPlayer(long playerID){
        return players.get(playerID);
    }

    public Map getMap(){
        return this.map;
    }

    /**
     * After all players join in the game at the first time,
     * generate a map according to the player number, and
     * assign each player a color
     */
    public void generateMap() {
        this.map = mapFactory.generateMapWithPlayer(playerNum);
        System.out.println("generate succeess\n");
        this.map.setAssign(players.values());
    }

    /**
     * Add a player into the game
     * @param playerID is the player ID
     */
    public synchronized void addOnePlayer(long playerID){
        if(players.size() == 0){
            ownerID = playerID;
        }
        this.players.put(playerID,new HumanPlayer(playerID));
    }

    /**
     * This method check whether a game has enough players
     * @return yes if there are enough players
     */
    public Boolean isGameStarted(){
        return status.get("SETUP") == playerNum;
    }


    /**
     * Show this msg before one player leave
     * @return winner msg
     */
    public String showBeforeLeave(){
        String winner = checkGameWinner();
        String winnerMsg;
        if(winner == null){
            winnerMsg  = "Game Over.\n";
        }else{
            winnerMsg  = "Game Over. The Winner of game "+gameID+ " is player "+
                    winner+".\n";
        }
        return winnerMsg;
    }
}
