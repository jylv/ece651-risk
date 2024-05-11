package ece651.sp22.grp8.risk.server;

import java.io.*;

import java.net.Socket;

import ece651.sp22.grp8.risk.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.api.parallel.Resources;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ServerAppTest {
  Socket c1,c2,c3,c4;
  Utility u1,u2,u3,u4;
  Map m1,m2,m3;
  /*
  protected void test_server_main_helper(String input, String expected) throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes, true);
    //remember the current stdin and stdout
    InputStream oldIn = System.in;
    PrintStream oldOut = System.out;
    //set new in and out
    try {
      System.setOut(out);
      ServerApp.main(new String[0]);
    }
    finally {
      System.setIn(oldIn);
      System.setOut(oldOut);
      System.out.print(bytes);
    }
  }

  private String readFile(String fileName) throws IOException {
    InputStream expectedStream = getClass().getClassLoader().getResourceAsStream(fileName);
    assertNotNull(expectedStream);
    return new String(expectedStream.readAllBytes());
  }

  @Test
  @ResourceLock(value = Resources.SYSTEM_OUT, mode = ResourceAccessMode.READ_WRITE)
  void test_main() throws IOException, InterruptedException, ClassNotFoundException {
    Thread s = new Thread() {
      @Override()
      public void run() {
        try {
          test_server_main_helper("",readFile("sout1.txt"));
        }catch (IOException ignored) {
        }
      }
    };
    s.start();
    Thread.sleep(1000); // this is a bit of a hack.

    test_setup_phase();
    test_init_phase();
    test_action_phase();
    test_player_disconnect();


    test_failedServer();

    s.interrupt();
    s.join();
  }

  private void test_action_phase() throws IOException, ClassNotFoundException {
    recvMap();
    //find origin
    Territory c1origin = null, c1dest = null;
    for(Territory t : m1.getTerritoriesByOwnerId(1000L)){
      if(t.getUnits().getNumberOfTroops() != 0){
          c1origin = t;
          break;
      }
    }
    for(Territory t : m1.getTerritoriesByOwnerId(1000L)){
      if(t.getUnits().getNumberOfTroops() == 0){
        c1dest = t;
        break;
      }
    }
    Move move = new Move(1000L,c1origin.getName(),c1dest.getName(),1);
    u1.sendPacket(new MovePacket(move));
    Move move1 = new Move(1000L,c1origin.getName(),c1origin.getName(),0);
    u1.sendPacket(new MovePacket(move1));
    Attack attack = new Attack(1000L, c1origin.getName(), c1dest.getName(), 1);
    u1.sendPacket(new AttackPacket(attack));
    Commit commit = new Commit(1000L,true);
    u1.sendPacket(new CommitPacket(commit)); //c1 commit
    Territory c3origin = null, c3dest = null;
    for(Territory t : m3.getTerritoriesByOwnerId(1002L)){
      if(t.getUnits().getNumberOfTroops() != 0){
        c3origin = t;
        break;
      }
    }
    for(Territory t : m1.getTerritoriesByOwnerId(1000L)){
      u3.sendPacket(new AttackPacket(new Attack(1002L, c3origin.getName(), t.getName(), 1)));
    }
    Commit commit3 = new Commit(1002L,true);
    u3.sendPacket(new CommitPacket(commit3));//c3 commit
  }

  private void test_init_phase() throws IOException, ClassNotFoundException {
    recvMap();
    u1.sendPacket(new InitializeUnitPacket(m1.doDefaultInitializeUnits(1000L)));
    u2.sendPacket(null);
    u3.sendPacket(new InitializeUnitPacket(m3.doDefaultInitializeUnits(1002L)));
  }

  private void test_setup_phase() throws IOException, ClassNotFoundException {
    //client 1 join
    c1 = new Socket("localhost", 8888);
    u1 = new Utility(c1);
    u1.sendMsg("Apply for my player ID\n");
    assertEquals(GamePrompt.GREETING,u1.recvMsg());
    assertEquals("1000",u1.recvMsg()); //playerID
    u1.sendMsg("Apply for my game ID\n");
    u1.sendMsg("3");//number of players
    assertEquals("5000",u1.recvMsg());
    //client 2 join
    c2 = new Socket("localhost", 8888);
    u2.sendMsg("Apply for my player ID\n");
    assertEquals(GamePrompt.GREETING,u2.recvMsg());
    assertEquals("1001",u2.recvMsg()); //playerID
    //try invalid input
    u2.sendMsg("20");
    assertEquals("The game with ID 20 does not exist!\nTry another one!\n",u2.recvMsg());
    //right input
    u2.sendMsg("5000");
    //client 3 join
    c3 = new Socket("localhost", 8888);
    u3.sendMsg("Apply for my player ID\n");
    assertEquals(GamePrompt.GREETING,u3.recvMsg());
    assertEquals("1002",u3.recvMsg()); //playerID
    u3.sendMsg("5000");
    test_extra_player();
    assertEquals(GamePrompt.OK,u2.recvMsg()); //join 5000
    assertEquals(GamePrompt.OK,u3.recvMsg()); //join 5000
    recvMap();
  }

  private void recvMap() throws IOException, ClassNotFoundException {
    Object o1 = u1.recvPacket().getObject();
    assertEquals(Map.class,o1.getClass());
    m1 = (Map) o1;
    //c2 leave
    Object o3 = u3.recvPacket().getObject();
    assertEquals(Map.class,o3.getClass());
    m3 = (Map) o3;
  }

  private void test_extra_player() throws IOException {
    //client 4 join
    c4 = new Socket("localhost", 8888);
    u4.sendMsg( "Apply for my player ID\n");
    assertEquals(GamePrompt.GREETING,u4.recvMsg());
    assertEquals("1003",u4.recvMsg()); //playerID
    //try invalid input
    u4.sendMsg("5000");
    assertEquals("The game with ID 5000 has already started!\nTry another one!\n",u4.recvMsg());
  }

  private void test_failedServer() throws InterruptedException {
    Thread.sleep(100); // this is a bit of a hack.
    Thread failedserver = new Thread() {
      @Override()
      public void run() {
        try {
          test_server_main_helper("sin1.txt","Server start failed!\n");
        }catch (IOException e) {
        }
      }
    };
    failedserver.start();
    Thread.sleep(100); // this is a bit of a hack.
    failedserver.interrupt();
    failedserver.join();
  }

  private void test_player_disconnect() throws IOException, ClassNotFoundException {
    u1.sendMsg(null);
    u3.sendMsg(null); //all leave
  }

   */

}
