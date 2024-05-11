package ece651.sp22.grp8.risk;

import java.io.Serializable;

public class MovePacket implements Packet, Serializable {
  private Move move;

  public MovePacket(Move move) {
    this.move = move;
  }

  @Override
  public void packPacket(Object move) {
    Move packedMove = (Move)move;
    this.move = packedMove;
  }

  @Override
  public Move getObject() {
    return this.move;
  }

  @Override
  public String toString() {
    return move.toString();
  }

  @Override
  public int hashCode() {
    return move.toString().hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (o.getClass().equals(getClass())) {
      MovePacket move = (MovePacket)o;
      return move.toString().equals(toString());
    }
    return false;
  }
}
