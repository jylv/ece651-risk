package ece651.sp22.grp8.risk;

import java.io.Serializable;

public class SwitchPacket implements Packet, Serializable {
  private String str;

  public SwitchPacket() {
    this.str = null;
  }

  public SwitchPacket(String str) {
    this.str = str;
  }

  @Override
  public void packPacket(Object str) {
    this.str = (String) str;
  }

  @Override
  public String getObject() {
    return str;
  }

  @Override
  public boolean equals(Object o) {
    if (o.getClass().equals(getClass())) {
      SwitchPacket cp = (SwitchPacket)o;
      return cp.toString().equals(toString());
    }
    return false;
  }

  @Override
  public String toString() {
    return str;
  }

  @Override
  public int hashCode() {
    return toString().hashCode();
  }
}
