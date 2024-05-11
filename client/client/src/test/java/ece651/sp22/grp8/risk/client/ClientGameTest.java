package ece651.sp22.grp8.risk.client;

import ece651.sp22.grp8.risk.*;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ClientGameTest {
  private ClientGame createClientGameWithNum(int num) {
    ClientGame game = new ClientGame();
    MapFactory factory = new MapFactory();
    Map m = factory.generateMapWithPlayer(num);
    game.setMapAndView(m);
    game.setPlayerID(1);
    game.setGameID(101);
    game.getMap().setAssign(generatePlayerList(num));
    game.setInitialPara();
    return game;
  }

    private ArrayList<Player> generatePlayerList(int num) {
        ArrayList<Player> PL = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            PL.add(new HumanPlayer(i + 1));
        }
        return PL;
    }

    @Test
    public void test_addAttack(){
        ClientGame game = createClientGameWithNum(2);
        game.addAttackOneTurn("Oz",0,1);
        game.addAttackOneTurn("Oz",0,2);
        game.addAttackOneTurn("Oz",1,4);
        assertEquals(2,game.attackOneTurn.get("Oz").keySet().size());
        assertEquals(3,game.attackOneTurn.get("Oz").get(0));
        assertEquals(4,game.attackOneTurn.get("Oz").get(1));
    }

    @Test
    public void test_setAndGet(){
        ClientGame game = createClientGameWithNum(2);
        assertEquals(2,game.getPlayerNum());
        assertEquals(8,game.remainUnits);
        assertEquals(4,game.currTerri.size());
        assertEquals("Red",game.getColor());
        Player p = new HumanPlayer(2000);
        game.setPlayer(p);
        assertEquals(p,game.getPlayer());
        assertEquals(2000,game.getPlayerID());

    }

  @Test
  void test_initialUnitsOnce(){
    ClientGame game = createClientGameWithNum(2);
    ArrayList<Territory> currTerri = game.currTerri;
    Territory t = currTerri.get(0);
    assertEquals(GamePrompt.LACK_UNITS, game.initialUnitsOnce(t, 8,9));
    assertEquals(GamePrompt.INITIAL_END_EARLY, game.initialUnitsOnce(t, 8,8));

    ClientGame game2 = createClientGameWithNum(2);
    game2.currTerri.remove(0);
    game2.currTerri.remove(0);
    Territory t1 = game2.currTerri.get(0);
    assertEquals(GamePrompt.INITIAL_END_EARLY, game2.initialUnitsOnce(t1, 8,3));


  }









//  @Test
//  void test_doOneMove() throws IOException {
//    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//    ClientGame game = createClientGameWithNum("5\n6\n4\nmordor\n0\n3\nhogWarts\nHoGwarts\n2\noz\n", bytes, 2);
//    game.initialUnitsPhase();
//    bytes.reset();
//    Move m = game.doOneMove();
//    assertEquals("Mordor", m.getOriTerri());
//    assertEquals("Hogwarts", m.getDestTerri());
//    assertEquals(3, m.getMoveUnits());
//    game.handleMove(m, GamePrompt.OK,4);
//    assertEquals(3, game.getMap().getTerritoryByName("Mordor").getUnitMap().get(0));
//    assertEquals(8, game.getMap().getTerritoryByName("Hogwarts").getUnitMap().get(0));
//    bytes.reset();
//    game.handleMove(m, "invalid",-1);
//    assertEquals("invalid", bytes.toString());
//  }
//
//  @Test
//  void test_doOneAttack() throws IOException {
//    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//    ClientGame game = createClientGameWithNum("5\n6\n4\nHoGwarts\n0\n2\noz\nHoGwarts\n0\n2\noz\n", bytes, 2);
//    game.initialUnitsPhase();
//    bytes.reset();
//    Attack a = game.doOneAttack();
//    assertEquals("Hogwarts", a.getOriginTerritory());
//    assertEquals("Oz", a.getDestTerritory());
//    assertEquals(2, a.getAttackUnits());
//    game.handleAttack(a, GamePrompt.OK);
//    assertEquals(1, game.attackOneTurn.size());
//    game.handleAttack(a, GamePrompt.OK);
//    assertEquals(1, game.attackOneTurn.size());
//    bytes.reset();
//    game.handleAttack(a, "invalid");
//    assertEquals("invalid", bytes.toString());
//  }



  @Test
  void test_commit() {
    ClientGame game = createClientGameWithNum(2);
    CommitPacket cp = game.doCommit();
    Commit c = cp.getObject();
    assertTrue(c.isCommit());
  }

  @Test
  void test_printResult(){
    ClientGame game = createClientGameWithNum(2);
    ActionResult aR = new ActionResult(1, "test");
    assertNull(game.printResult(aR));
    aR.addGetTerri("aaaa");
    aR.addGetTerri("bbbb");
    String expected = "*************************\n" + "In last turn, you got aaaa, bbbb.\n"
        + "*************************\n";
    assertEquals(expected, game.printResult(aR));
    aR.addLostTerri("ccc");
    aR.addLostTerri("ddd");
    expected = "*************************\n" + "In last turn, you lost ccc, ddd.\n"
        + "In last turn, you got aaaa, bbbb.\n" + "*************************\n";
    assertEquals(expected, game.printResult(aR));

    ClientGame game2 = createClientGameWithNum(2);
    ActionResult aR2 = new ActionResult(2, "test");
    aR2.addLostTerri("ccc");
    aR2.addLostTerri("ddd");
    expected = "*************************\n" + "In last turn, you lost ccc, ddd.\n" + "*************************\n";
    assertEquals(expected, game2.printResult(aR2));
  }



}
