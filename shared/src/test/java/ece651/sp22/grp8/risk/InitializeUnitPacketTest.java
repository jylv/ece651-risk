package ece651.sp22.grp8.risk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class InitializeUnitPacketTest {
  @Test
  public void test_const() {
    HashMap<String, Integer> hm = genHashMap();
    InitializeUnit it = new InitializeUnit(10, hm);
    InitializeUnitPacket ip = new InitializeUnitPacket(it);
    ip.packPacket(it);
    InitializeUnit it2 = new InitializeUnit(20, hm);
    InitializeUnitPacket ip2 = new InitializeUnitPacket(it2);
    assertEquals(ip, ip2);
    assertNotEquals(it,it2);
    InitializeUnitPacket ip3 = new InitializeUnitPacket(it);
    assertEquals(ip3, ip);
    assertEquals(false, ip.equals(it));
  }

  HashMap<String, Integer> genHashMap() {
    String Terro1 = "Duke";
    String Terro2 = "UNC";
    String Terro3 = "UW";
    HashMap<String, Integer> TerriorityByUnit = new HashMap<String, Integer>();
    TerriorityByUnit.put(Terro3, 10);
    TerriorityByUnit.put(Terro2, 1);
    TerriorityByUnit.put(Terro1, 9);
    return TerriorityByUnit;
  }

  @Test
  public void test_send_recv() throws IOException, ClassNotFoundException{
    HashMap<String, Integer> hm = genHashMap();
    InitializeUnit iu = new InitializeUnit(20, hm);
    Thread th = new Thread() {
      @Override()
      public void run() {
        try {
          InitializeUnitPacket ip = new InitializeUnitPacket(iu);
          ip.packPacket(iu);
          Utility u = new Utility(new Socket("localhost", 8891));
          u.sendPacket(ip);
        }

        catch (IOException e) {
        }
      }
    };

    th.start();
    ServerSocket server = new ServerSocket(8891, 10);
    Socket Connectionfd = server.accept();
    Utility Network = new Utility(Connectionfd);
    Packet recvPacket = (Packet) Network.recvPacket();
    server.close();

    assertEquals(iu, recvPacket.getObject());
  }



}
