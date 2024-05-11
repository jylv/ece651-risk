package ece651.sp22.grp8.risk;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.jupiter.api.Test;

public class MapPacketTest {
  @Test
  public void test_send_recv() throws IOException, ClassNotFoundException {
    Map map = new Map();
    Thread th = new Thread() {
      @Override()
      public void run() {
        try {
          MapPacket mp = new MapPacket(map);
          mp.packPacket(map);
          Utility u = new Utility(new Socket("localhost", 8892));
          u.sendPacket(mp);
          u.recvPacket();
        }

        catch (IOException e) {
        }


      }
    };

    th.start();
    ServerSocket server = new ServerSocket(8892, 10);
    Socket Connectionfd = server.accept();
    Utility Network = new Utility(Connectionfd);
    Packet recvPacket = (Packet) Network.recvPacket();
    Network.sendPacket( recvPacket);


    server.close();

    assertEquals(map.getClass(), recvPacket.getObject().getClass());

  }

  @Test
  public void test_cons() {
    MapPacket mp = new MapPacket(new Map());
    assertEquals(Map.class, mp.getObject().getClass());
  }

}
