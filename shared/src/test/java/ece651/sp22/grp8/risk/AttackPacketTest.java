package ece651.sp22.grp8.risk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.jupiter.api.Test;

public class AttackPacketTest {
  @Test
  public void test_const() {
      Attack attack = new Attack(0, "Mars", "Earth", 1,0);
      AttackPacket ap = new AttackPacket(attack);
      ap.packPacket(attack);
      assertEquals(attack,ap.getObject());
      String expected = "playerId: 0 origin: Mars, des: Earth, attack unit: 0 with level 1";
      assertEquals(expected,ap.toString());
      Attack attack1 = new Attack(0, "on", "Earth", 1,0);
      AttackPacket ap1 = new AttackPacket(attack1);
      assertNotEquals(ap1,ap);
      assertNotEquals(ap1,2);
      assertNotEquals(ap1.hashCode(),ap.hashCode());
  }

  @Test
  public void test_send_recv() throws IOException, ClassNotFoundException {
      Attack mv = new Attack(10, "USA", "Taipei", 1,0);
      Thread th = new Thread() {
          @Override()
          public void run() {
              try {
                  AttackPacket mp = new AttackPacket(mv);
                  mp.packPacket(mv);
                  Utility u = new Utility(new Socket("localhost", 8893));
                  u.sendPacket(mp);
              }

              catch (IOException e) {
              }
          }
      };

      th.start();
      ServerSocket server = new ServerSocket(8893, 10);
      Socket Connectionfd = server.accept();
      Utility Network = new Utility(Connectionfd);
      Packet recvPacket = (Packet) Network.recvPacket();
      server.close();

      assertEquals(mv, recvPacket.getObject());
  }
}
