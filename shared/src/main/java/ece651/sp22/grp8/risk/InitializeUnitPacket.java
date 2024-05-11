package ece651.sp22.grp8.risk;

import java.io.Serializable;

public class InitializeUnitPacket implements Packet, Serializable {
  private InitializeUnit inital;

  public InitializeUnitPacket(InitializeUnit it) {
    this.inital = it;
  }

  @Override
  public InitializeUnit getObject() {
    return inital;
  }

  @Override
  public void packPacket(Object obj) {
    InitializeUnit packed = (InitializeUnit)obj;
    this.inital = packed;
  }

  @Override
  public String toString() {
    return inital.toString();
  }

  @Override
  public int hashCode() {
    return toString().hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (getClass().equals(obj.getClass())) {
      InitializeUnitPacket ip = (InitializeUnitPacket)obj;
      return ip.hashCode() == hashCode();
    }

    return false;
  }
  
}
