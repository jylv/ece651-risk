package ece651.sp22.grp8.risk;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

public abstract class Player implements Serializable {
  protected String color; //the name of the Player
  protected long playerID; //the Id of the Player
  protected long gameID = -1; //the Id of the Game
  protected int techLevel; //maximum technology level, start from 1;
  protected int foodAmount; //the amount of food the player owes
  protected int techAmount; //the amount of tech the player owes
  protected boolean hasUpgrade; // has upgrade in this turn

  /**
   * constructs a Player with the specified id
   * @param playerID is the ID of the newly constructed player
   */
  public Player(long playerID){
    this.color = null;
    this.playerID = playerID;
    this.techLevel = 1;
    this.foodAmount = 0;
    this.techAmount = 0;
    this.hasUpgrade = false;
  }


  /**
   * the getter functions
   */
  public String getColor() {
    return color;
  }
  public long getPlayerID() {return playerID;}

  public long getGameID() {return gameID;}

  public int getTechLevel() {
    return techLevel;
  }

  public int getFoodAmount() {
    return foodAmount;
  }

  public int getTechAmount() {
    return techAmount;
  }

  public boolean isHasUpgrade() {
    return hasUpgrade;
  }

  /**
   * the setter functions
   */

  public void setPlayerID(long playerID) {this.playerID = playerID;}

  public void setGameID(long gameID) {
    this.gameID = gameID;
  }

  public void setColor(String color){
    this.color = color;
  }

  public void addFood(int num) {
    this.foodAmount += num;
  }

  public void decFood(int num) {
    this.foodAmount -= num;
  }

  public void addTech(int num){
    this.techAmount += num;
  }

  public void decTech(int num){
    this.techAmount -= num;
  }

  public void setHasUpgrade(boolean is){this.hasUpgrade = is;}

  public void addTechLevel(){this.techLevel += 1;}

  @Override
  public boolean equals(Object other){
    if(other.getClass().equals(getClass())) {
      if(color==null){
        return playerID == ((Player) other).playerID  &&
                gameID == ((Player) other).gameID &&
                ((Player) other).color==null &&
                techLevel == ((Player) other).getTechLevel() &&
                foodAmount == ((Player) other).getFoodAmount() &&
                techAmount == ((Player) other).getTechAmount();
      }
      else {
        return playerID == ((Player) other).playerID &&
                gameID == ((Player) other).gameID &&
                color.equals(((Player) other).color) &&
                techLevel == ((Player) other).getTechLevel() &&
                foodAmount == ((Player) other).getFoodAmount() &&
                techAmount == ((Player) other).getTechAmount();
      }
    }
    return false;
  }

  @Override
  public String toString() {
    return "Player: " + color +
            "\nplayerID: " + playerID +
            "\ngameID: " + gameID +
            "\ntechLevel: "+ techLevel+
            "\nfoodAmount: "+ foodAmount+
            "\ntechAmount: "+ techAmount;
  }

  @Override
  public int hashCode() {
    return toString().hashCode();
  }

}
