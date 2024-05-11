package ece651.sp22.grp8.risk;

public class Move extends AbstractAction {

  private String originTerritory;
  private String destTerritory;
  private int level;
  private int moveUnits;

  public String getOriTerri() {
    return originTerritory;
  }

  public String getDestTerri() {
    return destTerritory;
  }

  public int getMoveUnits() {
    return moveUnits;
  }

  public int getLevel() {
    return level;
  }

  public Move(long playerID, String originTerritory, String destTerritory, int level, int moveUnits) {
    super(playerID);
    this.originTerritory = originTerritory;
    this.destTerritory = destTerritory;
    this.level = level;
    this.moveUnits = moveUnits;
  }

  @Override
  public String toString() {
    return "playerId: " + playerId + " origin: " + originTerritory +
            ", des: " + destTerritory + ", move unit: " + moveUnits+" with level "+level;
  }

  @Override
  public boolean equals(Object o) {
    if (o.getClass().equals(getClass())) {
      Move move = (Move)o;
      return move.toString().equals(toString());
    }
    return false;
  }
}
