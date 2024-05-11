package ece651.sp22.grp8.risk;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;

public class CommitPacket implements Packet, Serializable {
  private Commit commit;

  public CommitPacket() {
    this.commit = null;
  }

  public CommitPacket(Commit commit) {
    this.commit = commit;
  }

  @Override
  public void packPacket(Object commit) {
    Commit packedCommit = (Commit)commit;
    this.commit = packedCommit;
  }

  @Override
  public Commit getObject() {
    return commit;
  }

  @Override
  public boolean equals(Object o) {
    if (o.getClass().equals(getClass())) {
      CommitPacket cp = (CommitPacket)o;
      return cp.toString().equals(toString());
    }
    return false;
  }

  @Override
  public String toString() {
    return commit.toString();
  }

  @Override
  public int hashCode() {
    return toString().hashCode();
  }
}
