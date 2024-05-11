package ece651.sp22.grp8.risk;

public class Commit extends AbstractAction {

  private boolean commit;

  public Commit(long playerID, boolean commit) {
    super(playerID);
    this.commit = commit;
  }

  public boolean isCommit() {
    return commit;
  }

  @Override
  public String toString() {
    return "player: " + playerId + ", status: " +  commit;
  }

  @Override
  public boolean equals(Object o) {
    if (o.getClass().equals(getClass())) {
      Commit commit = (Commit)o;
      return commit.toString().equals(toString());
    }

    return false;
  }

}
