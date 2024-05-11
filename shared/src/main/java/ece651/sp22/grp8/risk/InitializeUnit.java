package ece651.sp22.grp8.risk;

import java.util.HashMap;

public class InitializeUnit extends AbstractAction {
  public HashMap<String, Integer> territoryByUnit;

  public InitializeUnit(long playerID, HashMap<String, Integer> territoryByUnit) {
    super(playerID);
    this.territoryByUnit = territoryByUnit;
  }

  public Integer getUnitByName(String territory) {
    return territoryByUnit.get(territory);
  }

  @Override
  public String toString() {
    String str = "";
    for (HashMap.Entry<String, Integer> entry : territoryByUnit.entrySet()) {
      String key = entry.getKey();
      Integer val = entry.getValue();
      str += key + ": " + val + ", ";
    }
    return str;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj.getClass().equals(getClass())) {
      InitializeUnit it = (InitializeUnit)obj;
      return it.toString().equals(toString())&&this.getPlayerId() ==((InitializeUnit) obj).getPlayerId();
    }

    return false;
  }

  @Override
  public int hashCode() {
    return toString().hashCode();
  }

}
