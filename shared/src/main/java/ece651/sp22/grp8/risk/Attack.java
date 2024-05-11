package ece651.sp22.grp8.risk;

public class Attack extends AbstractAction {
  private String originTerritory; //the ori territory
  private String destTerritory; // the dest territory
  private int level; // the attack unit level
  private int attackUnits; // the attack unit num

  public Attack(long playerID, String originTerritory, String destTerritory, int level, int attackUnits) {
    super(playerID);
    this.originTerritory = originTerritory;
    this.destTerritory = destTerritory;
    this.level = level;
    this.attackUnits = attackUnits;
  }

  public String getOriginTerritory() {
    return originTerritory;
  }

  public String getDestTerritory() {
    return destTerritory;
  }

  public int getLevel() {
    return level;
  }

  public int getAttackUnits() {
    return attackUnits;
  }

  @Override
  public String toString() {
    return "playerId: " + playerId + " origin: " + originTerritory +
            ", des: " + destTerritory + ", attack unit: " + attackUnits+" with level "+level;
  }

  @Override
  public boolean equals(Object o) {
    if (o.getClass().equals(getClass())) {
      Attack attack = (Attack)o;
      return attack.toString().equals(toString());
    }
    return false;
  }
}
