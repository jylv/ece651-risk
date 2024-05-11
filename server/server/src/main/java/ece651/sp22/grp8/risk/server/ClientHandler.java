package ece651.sp22.grp8.risk.server;

import ece651.sp22.grp8.risk.*;

import javax.print.DocFlavor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ClientHandler implements Runnable {
    private final long playerID;
    private long gameID;
    private static long nextPlayerID = 1000; //init game number
    private static long nextGameID = 5000; //init game number
    private final GamePool gamePool;
    private final Utility utility;
    final BufferedReader inputReader;
    final PrintStream out;
    private final ActionHandler actionHandler;
    private final MyDatabase database;

    public ClientHandler(Socket s, BufferedReader inputReader, PrintStream out, GamePool gamePool,MyDatabase database) {
        this.playerID = nextPlayerID++;
        this.inputReader = inputReader;
        this.out = out;
        this.gamePool = gamePool;
        this.utility = new Utility(s);
        this.actionHandler = new ActionHandler();
        this.database = database;
    }

    @Override
    public void run() {
        try {
            out.print("Connected with player " + playerID + ".\n");
            creatPlayer();
            joinGamePhase();
            initUnitPhase();
            while(true){
                String gameStatus = actionPhase();//CONTINUE
                if (gameStatus.equals("LOSE")) {
                    losePhase();
                } else if (gameStatus.equals("END")) {
                    out.print("Player " + this.gamePool.getGameMap(gameID).getAssign(playerID)
                            + " loses the game.\n");
                }
                out.print(this.gamePool.getGame(gameID).showBeforeLeave());
                utility.sendMsg(this.gamePool.getGame(gameID).showBeforeLeave());
            }

        } catch (Exception e) {
            out.print("Player " + playerID + " accidentally leave the game.\n");
            out.print("error: " + e + "\n");
            e.printStackTrace();
            this.gamePool.getGame(gameID).updateStatusNum("LEAVE", 1);
            disconnectAtInit();
            disconnectAtAttack();
        }
    }

    private void disconnectAtInit() {
        if (getStatus().equals("INIT_UNIT")) {
            InitializeUnit iu = gamePool.getGameMap(gameID).doDefaultInitializeUnits(playerID); //recv updated map
            String color = this.gamePool.getGame(gameID).getPlayer(playerID).getColor();
            this.gamePool.getGameMap(this.gameID).initTerritoryUnits(iu, color); //set init unit at map
            waitForOthers("INIT_UNIT");
            out.print("Player " + playerID + " next phase: action.\n");
        }
    }

    /**
     * When someone accidently leave the game, check what the
     * statu the game is
     *
     * @return optional: INIT_UNIT or ACTION
     */
    private String getStatus() {
        Game game = this.gamePool.getGame(gameID);
        if (game.getStatusNum("INIT_UNIT") < game.getPlayerNum()) {//leave at init unit phase
            return "INIT_UNIT";
        } else { //leave at attack phase
            return "ACTION";
        }
    }

    private void disconnectAtAttack() {
        while (this.gamePool.getGame(gameID).checkGameWinner() == null) {
            out.print("Player " + playerID + " disconnects at this turn.\n");
            getCombatResult();
            if (gamePool.getGame(gameID).getStatusNum("LEAVE") ==
                    gamePool.getGame(gameID).getPlayerNum()) {
                out.print("Oops, all players left the game!\n");
                return;
            }
        }
    }

    /**
     * This is a helper function for all phases
     * Block this thread until all players in this game finished
     * this phase
     *
     * @param status means the current phase of the game
     */
    private void waitForOthers(String status) {
        gamePool.getGame(gameID).updateStatusNum(status, 1);
        //block, wait for others
        out.print("Player " + playerID + " " + status + " : " +
                gamePool.getGame(gameID).getStatusNum(status) + "\n");
        while (true) {
            int a = gamePool.getGame(gameID).getStatusNum(status);
            int b = gamePool.getGame(gameID).getPlayerNum();
            if (a % b == 0) {
                break;
            }
        }
        out.print("Player " + playerID + " continue at status: " + status + "\n");
    }


    private void getCombatResult() {
        waitForOthers("ACTION");
        if (gamePool.getGame(gameID).getOwnerID() == playerID) {
            //roll dice and get combat results and update unit
            gamePool.getGame(gameID).combatAndUpdate();
        }
        waitForOthers("RESOLVE");
    }

    private void losePhase() throws IOException {
        Leave leave = (Leave) utility.recvPacket().getObject();
        if (!leave.getLeave()) {//watch
            out.print("Player " + playerID + " continues to watch the game.\n");
            while (this.gamePool.getGame(gameID).checkGameWinner() == null) {
                utility.sendPacket(new LeavePacket(new Leave(playerID, false)));
                sendMap();//send a whole map to this player
                sendPlayer();
                getCombatResult();
            }
            utility.sendPacket(new LeavePacket(new Leave(playerID, true)));
        } else {//leave
            out.print("Player " + playerID + " leaves the game.\n");
            while (this.gamePool.getGame(gameID).checkGameWinner() == null) {
                getCombatResult();
            }
        }
    }

    /**
     * collect actions from the player and compute results
     *
     * @return game status : continue, win, lose, end
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private String playOneTurn() throws IOException, ClassNotFoundException {
        sendMap();//send an whole map to this player
        sendPlayer(); //send the player info
        Object o;
        //receive move and attack request
        do {
            o = utility.recvPacket().getObject();
            if (o.getClass() == Move.class) {
                HashMap<String, Integer> moveResult = actionHandler.dealWithMove(this.gamePool.getGameMap(this.gameID), this.gamePool.getGame(gameID).getPlayer(playerID), (Move) o);
                for (String problem : moveResult.keySet()) {
                    utility.sendMsg(problem);
                    utility.sendMsg((moveResult.get(problem)).toString());
                }
            } else if (o.getClass() == Attack.class) {
                utility.sendMsg(actionHandler.dealWithAttack(this.gamePool.getGameMap(this.gameID), this.gamePool.getGame(gameID).getPlayer(playerID), (Attack) o));
            } else if (o.getClass() == UpUnit.class) {
                utility.sendMsg(actionHandler.dealWithUpUnit(this.gamePool.getGameMap(this.gameID), this.gamePool.getGame(gameID).getPlayer(playerID), (UpUnit) o));
            } else if (o.getClass() == UpTech.class) {
                utility.sendMsg(actionHandler.dealWithUpTech(this.gamePool.getGame(gameID).getPlayer(playerID), (UpTech) o));
            } else if (o.getClass() == String.class && o.equals(GamePrompt.BACKTOGALLERY)) {
                utility.sendPacket(new GallerayGamesPacket(new GalleryGames(this.playerID, gamePool.getGameGallery())));
                if(joinGamePhase().equals(GamePrompt.NEWGAME)){
                    initUnitPhase();
                }
                sendMap();
                sendPlayer();
            }
        } while (o.getClass() != Commit.class);
        getCombatResult();
        //send result of this turn
        ActionResult aR = gamePool.getGame(gameID).getResultOneTurn().get(playerID);
        utility.sendPacket(new ActionResultPacket(aR));
        return aR.getGameStatus();
    }

    /**
     * At this phase, the server deal with each action (move or attack)
     * each player wants to do.
     *
     * @return game status: continue, win, lose, end
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private String actionPhase() throws IOException, ClassNotFoundException {
        String gameStatus = "CONTINUE";
        while (gameStatus.equals("CONTINUE")) {
            gameStatus = playOneTurn();
        }
        return gameStatus;
    }


    /**
     * At this phase, the server tell each player how many units can be
     * placed. Then receive and store their placement results.
     */
    private void initUnitPhase() throws IOException {
        sendMap();
        InitializeUnit iu = ((InitializeUnitPacket) utility.recvPacket()).getObject(); //recv updated map
        String color = this.gamePool.getGame(gameID).getPlayer(playerID).getColor();
        this.gamePool.getGameMap(this.gameID).initTerritoryUnits(iu, color); //set init unit at map
        this.gamePool.getGame(gameID).getPlayer(playerID).addFood(this.gamePool.getGameMap(this.gameID).initialFood);
        this.gamePool.getGame(gameID).getPlayer(playerID).addTech(this.gamePool.getGameMap(this.gameID).initialTech);
        waitForOthers("INIT_UNIT");
        out.print("Player " + playerID + " next phase: action.\n");
    }

    /**
     * This phase assign each client a player, a game and a map
     *
     * @throws IOException
     * @return join a new game or rejoin an old game
     */
    private String joinGamePhase() throws IOException {
        String whichGame = getGames();
        if(whichGame.equals(GamePrompt.NEWGAME)){
            waitForOthers("SETUP");
            gamePool.getGame(gameID).generateMap();
        }
        sendMap();
        out.print("Player " + playerID + " next phase: initiate unit.\n");
        return whichGame;
    }


    private void sendMap() {
        try {
            utility.sendPacket(new MapPacket(gamePool.getGameMap(gameID)));
        } catch (IOException ignored) {
        }
    }

    private void sendPlayer() throws IOException {
        utility.sendPacket(new PlayerPacket(gamePool.getGame(gameID).getPlayer(playerID)));
    }


    /**
     * This method wait for a player's ID request,
     * send it back, and update the nextPlayerID
     */
    private void creatPlayer() throws IOException {
        while (true) {
            Object request = utility.recvMsg();
            if (Objects.equals(request, GamePrompt.LOGIN)) {
                String username = utility.recvMsg();
                String password = utility.recvMsg();
                String result = database.queryUserExist(username, password);
                if (result.equals(GamePrompt.OK)) {
                    utility.sendMsg(GamePrompt.OK);
                    utility.sendMsg(String.valueOf(playerID));
                    gamePool.getGameGallery().put(this.playerID, new ArrayList<>());
                    utility.sendPacket(new GallerayGamesPacket(new GalleryGames(this.playerID, gamePool.getGameGallery())));
                    return;
                } else {
                    utility.sendMsg(result);
                }
            } else if (Objects.equals(request, GamePrompt.REGISTER)) {
                String username = utility.recvMsg();
                String password = utility.recvMsg();
                out.print("username: " + username + "\n");
                out.print("password: " + password + "\n");
                String result = database.insertNewUser(username, password);
                if (result.equals(GamePrompt.OK)) {
                    utility.sendMsg(GamePrompt.OK);
                    utility.sendMsg(String.valueOf(playerID));
                } else {
                    utility.sendMsg(result);
                }
            } else {
                out.print("unrecognized packet\n");
                out.print(request);
            }
        }

    }

    /**
     * A helper function to create a new game
     */
    private void createNewGame() throws IOException {
        int playerNum = Integer.parseInt(Objects.requireNonNull(utility.recvMsg()));
        gamePool.addGame(nextGameID, new Game(playerNum, nextGameID));
        this.gameID = nextGameID++;
        out.print("Player " + this.playerID + " creates a new game " + this.gameID + ".\n");
        gamePool.getGame(gameID).addOnePlayer(this.playerID);
        addNewGameRecord(this.gameID);
        out.print("Player " + this.playerID + " joins the game " + this.gameID + ".\n");
        utility.sendMsg(String.valueOf(this.gameID));
        utility.sendPacket(new GallerayGamesPacket(new GalleryGames(this.playerID, gamePool.getGameGallery())));
    }

    /**
     * This method asks the player for the gameID, or
     * create a new game with required player number.
     * It will not return until join a game
     * @return NEWGAME means join a new game
     *         REJOIN means back to an old game
     */
    private String getGames() throws IOException {
        while (true) {
            String text = utility.recvMsg();
            if (Objects.equals(text, GamePrompt.NEWGAME)) {
                createNewGame();
            } else {
                this.gameID = Long.parseLong(text);
                if(!gamePool.containGame(this.gameID)){ //game doesn't exist
                    utility.sendMsg("The game with ID " + this.gameID + " does not exist!\n" +
                            "Try another one!\n");
                } else if (!gamePool.getGame(gameID).isGameStarted()) { // join a game at the first time
                    gamePool.getGame(gameID).addOnePlayer(this.playerID);
                    out.print("Player " + this.playerID + " joins the game " + this.gameID + ".\n");
                    addGameJoinRecord(this.playerID, this.gameID);
                    utility.sendMsg(GamePrompt.NEWGAME);
                    return GamePrompt.NEWGAME;
                } else if(gamePool.getGame(gameID).getPlayer(playerID) == null){ // game have already started
                    utility.sendMsg("The game with ID " + this.gameID + " has already started!\n" +
                            "Try another one!\n");
                } else { // rejoin a game
                    out.print("Player " + this.playerID + " rejoins the game " + this.gameID + ".\n");
                    utility.sendMsg(GamePrompt.REJOIN);
                    return GamePrompt.REJOIN;
                }
            }
        }
    }

    private void addNewGameRecord(long gameID) {
        ArrayList<Long> arr = gamePool.getGameGallery().get(0L);
        arr.add(gameID);
    }

    private void addGameJoinRecord(long playerID, long gameID) {
        ArrayList<Long> arr = gamePool.getGameGallery().get(playerID);
        arr.add(gameID);
    }
}