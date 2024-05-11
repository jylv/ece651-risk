package ece651.sp22.grp8.risk;

import java.io.Serializable;

public class MapPacket implements Packet, Serializable {
  Map map;

  public MapPacket(Map map) {
    this.map = map;
  }

  @Override
  public Map getObject() {
    return this.map;
  }

  @Override
  public void packPacket(Object obj) {
    Map packed = (Map)obj;
    this.map = packed;
  }
}
