package ece651.sp22.grp8.risk;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class UtilityTest {
  @Test
  public void send_recv_string_object() throws IOException, ClassNotFoundException {
    Thread th = new Thread() {
      @Override()
      public void run() {
        try {
          Socket s = new Socket("localhost", 8895);
          Utility clientUtility = new Utility(s);
          clientUtility.sendMsg("I am Ian");
        } catch (IOException e) {
        }
      }
    };

    th.start();
    ServerSocket s = new ServerSocket(8895);
    Socket Connectionfd = s.accept();
    Utility serverUtility = new Utility(Connectionfd);

    String recvMsg = serverUtility.recvMsg();
    assertEquals("I am Ian", recvMsg);

    s.close();
  }

  @Test
  public void test_send_Map() throws IOException, ClassNotFoundException {
    Thread th = new Thread() {
      @Override()
      public void run() {
        try {
          // client side
          Socket sc = new Socket("localhost", 8896);
          Utility ClientUtility = new Utility(sc);

          StringPacket sp = (StringPacket)ClientUtility.recvPacket();
          assertEquals(20, Integer.parseInt(sp.getObject()));
          Map map = new Map();
          map.setInitialUnits(30);
          MapPacket mp = new MapPacket(map);
          mp.packPacket(map);
          ClientUtility.sendPacket(mp);

          mp = (MapPacket)ClientUtility.recvPacket();
          assertEquals(1000, mp.getObject().getInitialUnits());
        }

        catch (IOException e) {
        }


      }
    };
    // server side
    th.start();
    ServerSocket s = new ServerSocket(8896);
    Socket Connectionfd = s.accept();
    Utility serverUtility = new Utility(Connectionfd);

    serverUtility.sendMsg( String.valueOf(20));
    MapPacket mapPacket = (MapPacket)serverUtility.recvPacket();
    assertEquals(mapPacket.getObject().getInitialUnits(), 30);
    
    Map ClientMap = mapPacket.getObject();
    ClientMap.setInitialUnits(1000);
    mapPacket.packPacket(ClientMap);
    serverUtility.sendPacket( mapPacket);
    

    s.close();;
  }

  @Test
  public void test_IOException() throws IOException {
    StringPacket sp = new StringPacket();
    String msg = "Ian is here";
    sp.packPacket(msg);

//    Utility u = new Utility(new Socket("localhost", 80));
//    assertThrows(IOException.class, () -> u.sendPacket(sp)); todo
  }
}
