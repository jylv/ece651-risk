package ece651.sp22.grp8.risk;

import java.io.Serializable;

public class AttackPacket implements Packet, Serializable {
  private Attack attack;

  public AttackPacket(Attack attack) {
    this.attack = attack;
  }

  @Override
  public void packPacket(Object attack) {
    Attack packedAttack = (Attack)attack;
    this.attack = packedAttack;
  }

  @Override
  public Attack getObject() {
    return this.attack;
  }

  @Override
  public boolean equals(Object o) {
    if (o.getClass().equals(getClass())) {
      AttackPacket ap = (AttackPacket) o;
      return ap.toString().equals(toString());
    }
    return false;
  }

  @Override
  public String toString() {
    return attack.toString();
  }

  @Override
  public int hashCode() {
    return toString().hashCode();
  }
}
