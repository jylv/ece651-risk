package ece651.sp22.grp8.risk.client;

import ece651.sp22.grp8.risk.*;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class ClientGame {
  private Player player;
  private ActionHandler acHandler;
  private Map map;
  private MapTextView mapTextView;
  private int playerNum;
  public HashMap<String, HashMap<Integer, Integer>> attackOneTurn;

  // fields for initialize
  public int remainUnits;
  public ArrayList<Territory> currTerri;

  public ClientGame() {
    this.attackOneTurn = new HashMap<>();
    this.acHandler = new ActionHandler();
    this.remainUnits = 0;
    this.currTerri = new ArrayList<>();
  }

  // the setter of the game
  public void setPlayer(Player player) {
    this.player = player;
  }

  public Player getPlayer() {
    return player;
  }

  public void setPlayerID(long id) {
    this.player = new HumanPlayer(id);
  }

  public void setGameID(long id) {
    this.player.setGameID(id);
  }

  public void setMapAndView(Map map) {
    this.map = map;
    this.mapTextView = new MapTextView(map);
  }

  public void addAttackOneTurn(String territory, int level, int units) {
    // if the player has attacked this territory, add unit
    if (attackOneTurn.containsKey(territory)) {
      // if unit of this level has attacked this territory, add unit
      if (attackOneTurn.get(territory).containsKey(level)) {
        int oldUnit = attackOneTurn.get(territory).get(level);
        attackOneTurn.get(territory).replace(level, oldUnit, oldUnit + units);
      }
      // else, put attack info
      else {
        attackOneTurn.get(territory).put(level, units);
      }
    }
    // else, put attack info
    else {
      HashMap<Integer, Integer> newAttack = new HashMap<>();
      newAttack.put(level, units);
      attackOneTurn.put(territory, newAttack);
    }
  }

  // the getter of the game
  public long getPlayerID() {
    return player.getPlayerID();
  }

  public Map getMap() {
    return map;
  }

  public String getColor() {
    return player.getColor();
  }

  // functions for initialize units
  public void setInitialPara() {
    this.player.setColor(map.getAssign(player.getPlayerID()));
    this.currTerri = map.getTerritoriesByOwnerId(player.getPlayerID());
    this.remainUnits = map.getInitialUnits();
    this.playerNum = map.getGroups().keySet().size();
  }

  public int getPlayerNum() {
    return playerNum;
  }

  /**
   * do initialize units once for a territory
   */
  public String initialUnitsOnce(Territory territory, int remainUnits, int num) {
    if (num > remainUnits) {
      return GamePrompt.LACK_UNITS;
    } else {
      // once the unit is valid, do the initializing and modify the parameter
      territory.getUnitMap().replace(0, 0, num);
      this.remainUnits -= num;
      this.currTerri.remove(0);
      // return different results Strings
      if (this.remainUnits == 0) {
        return GamePrompt.INITIAL_END_EARLY;
      } else if (this.currTerri.size() == 1) {
        currTerri.get(0).getUnitMap().replace(0, 0, this.remainUnits);
        this.remainUnits -= num;
        this.currTerri.remove(0);
        map.getGroups().put(map.getAssign(player.getPlayerID()), map.getTerritoriesByOwnerId(player.getPlayerID()));
        return GamePrompt.INITIAL_END_EARLY;
      } else {
        return GamePrompt.OK;
      }
    }
  }

  /**
   * generate initializeUnit packet for sending to the server
   */
  public InitializeUnitPacket packInitializeUnits() {
    // generate InitializeUnit and InitializeUnitPacket
    HashMap<String, Integer> territoryByUnit = new HashMap<>();
    for (Territory t : getMap().getTerritoriesByOwnerId(player.getPlayerID())) {
      territoryByUnit.put(t.getName(), t.getUnitMap().get(0));
    }
    InitializeUnit iU = new InitializeUnit(player.getPlayerID(), territoryByUnit);
    InitializeUnitPacket iUPacket = new InitializeUnitPacket(iU);
    return iUPacket;
  }

  // functions for actions
  /**
   * handle the move action at local
   */
  public void handleMove(Move move, int cost) {
    Territory origin = map.getTerritoryByName(move.getOriTerri());
    Territory dest = map.getTerritoryByName(move.getDestTerri());
    origin.decreaseUnit(move.getLevel(), move.getMoveUnits());
    dest.increaseUnit(move.getLevel(), move.getMoveUnits());
    player.decFood(cost);
  }

  /**
   * handle the attack action at local if valid
   */
  public void handleAttack(Attack attack) {
    Territory origin = map.getTerritoryByName(attack.getOriginTerritory());
    Territory dest = map.getTerritoryByName(attack.getDestTerritory());
    origin.decreaseUnit(attack.getLevel(), attack.getAttackUnits());
    addAttackOneTurn(attack.getDestTerritory(), attack.getLevel(), attack.getAttackUnits());
    player.decFood(attack.getAttackUnits());
  }

  public void handleUT() {
    player.decTech(acHandler.upgradeCost.get(player.getTechLevel()));
  }

  public void handleUU(UpUnit uu) {
    Territory t = map.getTerritoryByName(uu.getTerritory());
    t.decreaseUnit(uu.getOriLevel(), uu.getNum());
    t.increaseUnit(uu.getDestLevel(), uu.getNum());
    player.decTech(acHandler.upUnitCost(uu.getOriLevel(), uu.getNum(), uu.getDestLevel()));
  }

  public CommitPacket doCommit() {
    // generate commit and commitPacket
    Commit c = new Commit(player.getPlayerID(), true);
    return new CommitPacket(c);
  }

  // some func for print info
  /**
   * print the attack info of this turn by this player
   */
  public String printAttackInfo() {
    StringBuilder str = new StringBuilder();
    // print attack info in this turn
    if (attackOneTurn.size() != 0) {
      str.append("In this turn, you have been:\n");
      for (String t : attackOneTurn.keySet()) {
        str.append("Attack " + t + " with:\n");
        for (int level : attackOneTurn.get(t).keySet()) {
          str.append(attackOneTurn.get(t).get(level) + " level " + level + " units.\n");
        }
      }
    }
    return str.toString();
  }

  /**
   * print the result of this turn
   * 
   * @param aR
   * @return
   */
  public String printResult(ActionResult aR) {
    if (aR.getGetTerri().size() == 0 && aR.getLostTerri().size() == 0) {
      return null;
    }

    String res = "*************************\n";
    if (aR.getLostTerri().size() != 0) {
      boolean start = true;
      res = res + "In last turn, you lost ";
      for (String t : aR.getLostTerri()) {
        if (start) {
          res += t;
          start = false;
        } else {
          res += ", " + t;
        }
      }
      res += ".\n";
    }
    if (aR.getGetTerri().size() != 0) {
      boolean start = true;
      res = res + "In last turn, you got ";
      for (String t : aR.getGetTerri()) {
        if (start) {
          res += t;
          start = false;
        } else {
          res += ", " + t;
        }
      }
      res += ".\n";
    }
    res += "*************************\n";
    return res;
  }
}
