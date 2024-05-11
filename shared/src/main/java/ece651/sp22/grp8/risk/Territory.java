package ece651.sp22.grp8.risk;

import java.io.Serializable;
import java.util.HashMap;

public class Territory implements Serializable {
  private final String name; //the name of the territory
  private Player owner; //the owner of the territory
  public int size; //the cost to move through it
  public int foodProd; //food resource production
  public int techProd; // tech resource production
  private HashMap<Integer,Integer> unitMap; //<level,num>
  public HashMap<Long, HashMap<Integer,Integer>> attackInfo; //<playerID, HashMap<level,num>>

  /**
   * constructs a Territory with the specified name
   * @param name is the name of the newly constructed Territory
   */
  public Territory(String name){
    this.name = name;
    this.owner = null;
    this.size = 0;
    this.foodProd = 0;
    this.techProd = 0;
    this.attackInfo = new HashMap<>();
    this.initialUnitMap();
  }

  public Territory(String name, int size, int foodProd, int techProd){
    this.name = name;
    this.owner = null;
    this.size = size;
    this.foodProd = foodProd;
    this.techProd = techProd;
    this.attackInfo = new HashMap<>();
    this.initialUnitMap();
  }

  /**
   * initial unitMap
   */
  public void initialUnitMap(){
    this.unitMap = new HashMap<>();
    // the num of units of each level is 0 at beginning
    for(int i=0;i<7;i++){
      unitMap.put(i,0);
    }
  }


  public void setUnitMap(HashMap<Integer, Integer> unitMap) {
    this.unitMap = unitMap;
  }

  /**
   * the getter and setter functions
   */
  public String getName() {
    return name;
  }

  public Player getOwner() {
    return owner;
  }

  public HashMap<Integer, Integer> getUnitMap() {
    return unitMap;
  }

  public void setOwner(Player owner) {
    this.owner = owner;
  }

  public void updateUnitMap(HashMap<Integer,Integer> newUnits){
    for(int level: newUnits.keySet()){
      unitMap.replace(level,0,newUnits.get(level));
    }
  }

  /**
   * get lowest level or highest level of units
   * @param low get low or high
   * @return
   */
  public int getLevel(boolean low){
    if(low){
      for(int i=0; i<7;i++){
        if(unitMap.get(i)!=0){
          return i;
        }
      }
      return -1;
    }
    else{
      for(int i=6; i>=0;i--){
        if(unitMap.get(i)!=0){
          return i;
        }
      }
      return -1;
    }

  }


  /**
   * increase and decrease units
   */
  public void increaseUnit(int level, int toAdd){
    unitMap.replace(level,unitMap.get(level),unitMap.get(level)+toAdd);
  }

  public void decreaseUnit(int level, int todecre){
    unitMap.replace(level,unitMap.get(level),unitMap.get(level)-todecre);
  }

  public int getUnitsSum(){
    int sum = 0;
    for(int level:unitMap.keySet()){
      sum += unitMap.get(level);
    }
    return sum;
  }

  @Override
  public boolean equals(Object o){
    if(o.getClass().equals(getClass())){
      Territory other = (Territory) o;
      return name.equals((other).getName()) &&
              size== (other).size &&
              foodProd== (other).foodProd &&
              techProd== (other).techProd &&
              owner.equals((other).getOwner()) &&
              unitMap.equals((other).getUnitMap());
    }
    return false;
  }

  @Override
  public String toString(){
    String ans = "name: "+name+"\n"+"owner: "+owner+"\n"+"size: "+size+"\n"+"foodProd: "+foodProd+"\n"+"techProd: "+techProd+"\n";
    for(int level:unitMap.keySet()){
      ans += "unit level:"+level+"  num:"+unitMap.get(level)+"\n";
    }
    return ans;
  }

  @Override
  public int hashCode(){
    return toString().hashCode();
  }

}
