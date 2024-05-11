package ece651.sp22.grp8.risk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class CommitPacketTest {
  @Test
  public void test_Commit_cons() {
    CommitPacket cp = new CommitPacket(new Commit(0, false));
    CommitPacket cp2 = new CommitPacket();
    assertEquals(null, cp2.getObject());
    String expected = "player: 0, status: false";
    assertEquals(expected, cp.toString());
  }

  @Test
  public void test_packCommit() {
    Commit msg = new Commit(0, true);
    CommitPacket cp = new CommitPacket();
    cp.packPacket(msg);

    assertEquals(true, cp.getObject().toString().equals(msg.toString()));
  }

  @Test
  public void test_hashCode() {
    CommitPacket cp = new CommitPacket(new Commit(0, false));
    CommitPacket cp2 = new CommitPacket(new Commit(1, true));
    CommitPacket cp3 = new CommitPacket(new Commit(0, false));

    assertEquals(cp.hashCode(), cp3.hashCode());
    assertNotEquals(cp.hashCode(), cp2.hashCode());
  }

  @Test
  public void test_toString() {
    CommitPacket cp = new CommitPacket(new Commit(0, false));
    CommitPacket cp2 = new CommitPacket(new Commit(1, true));
    CommitPacket cp3 = new CommitPacket(new Commit(0, false));

    assertEquals(cp.toString(), cp3.toString());
    assertNotEquals(cp.toString(), cp2.toString());
  }

  @Test
  public void test_equals() {
    CommitPacket cp = new CommitPacket(new Commit(0, false));
    CommitPacket cp2 = new CommitPacket(new Commit(1, true));
    CommitPacket cp3 = new CommitPacket(new Commit(0, false));

    assertEquals(cp, cp3);
    assertNotEquals(cp, cp2);

    Commit c = new Commit(0, false);
    assertEquals(false, cp3.equals(c));
  }


  @Test
  public void test_send_recv() throws IOException, ClassNotFoundException {
    Commit commit = new Commit(102, false);
    Thread th = new Thread() {
      @Override()
      public void run() {
        try {
          CommitPacket cp = new CommitPacket();
          cp.packPacket(commit);
          Utility u = new Utility(new Socket("localhost", 8892));
          u.sendPacket(cp);
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
    server.close();

    assertEquals(commit, recvPacket.getObject());
  }
}
