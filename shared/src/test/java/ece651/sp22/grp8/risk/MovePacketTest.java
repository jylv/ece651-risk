package ece651.sp22.grp8.risk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.jupiter.api.Test;

public class MovePacketTest {
  @Test
  public void test_MovePacket_cons() {
    Move m = new Move(10, "Duke", "UNC", 0,5);
    MovePacket mp = new MovePacket(m);
    String expected = "playerId: 10 origin: Duke, des: UNC, move unit: 5 with level 0";
    assertEquals(expected, mp.toString());
  }

  @Test
  public void test_hashCode() {
    MovePacket mp = new MovePacket(new Move(10, "Duke", "UNC", 1,5));
    MovePacket mp_2 = new MovePacket(new Move(10, "UNC", "Duke",1, 5));

    assertNotEquals(mp.hashCode(), mp_2.hashCode());
  }

  @Test
  public void test_equals() {
    MovePacket mp = new MovePacket(new Move(10, "Duke", "UNC",1, 5));
    MovePacket mp2 = new MovePacket(new Move(10, "UNC", "Duke", 1,5));
    MovePacket mp3 = new MovePacket(new Move(10, "Duke", "UNC", 1,5));
    Move m = new Move(10, "Duke", "UNC",1, 5);

    assertEquals(true, mp.equals(mp3));
    assertEquals(false, mp.equals(mp2));
    assertEquals(false, mp.equals(m));
  }

  @Test
  public void test_send_recv() throws IOException, ClassNotFoundException {
    Move mv = new Move(10, "USA", "Taipei", 1,0);
    Thread th = new Thread() {
      @Override()
      public void run() {
        try {
          MovePacket mp = new MovePacket(mv);
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
