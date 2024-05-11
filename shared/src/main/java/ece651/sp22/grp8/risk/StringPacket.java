package ece651.sp22.grp8.risk;

import java.io.Serializable;

public class StringPacket implements Packet, Serializable {
  String msg;

  @Override
  public String getObject() {
    return msg;
  }

  @Override
  public void packPacket(Object obj) {
    String packed = (String)obj;
    this.msg = packed;
  }

}
