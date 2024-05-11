package ece651.sp22.grp8.risk.server;

import ece651.sp22.grp8.risk.ActionResult;
import ece651.sp22.grp8.risk.HumanPlayer;
import ece651.sp22.grp8.risk.Map;
import ece651.sp22.grp8.risk.Player;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    @Test
    void test_constructor(){
        Game game = new Game(3,5000);
        assertEquals(3,game.getPlayerNum());
        assertEquals(5000,game.getGameID());
        assertEquals(new HashMap<>(),game.getResultOneTurn());
    }

    @Test
    void test_override_funcs(){
        Game g1 = new Game(2,5000);
        Game g2 = new Game(2,5001);
        Game g3 = new Game(2,5000);
        assertEquals(g1,g3);
        assertNotEquals(g1,3);
        assertNotEquals(g1,g2);
        assertNotEquals(g1,null);
        assertEquals(Long.toString (5000).hashCode(),g1.hashCode());
        assertEquals(Long.toString (5001).hashCode(),g2.hashCode());
        String expected = "Game{" +
                "gameID=" + 5001 +
                '}';
        assertEquals(expected,g2.toString());
    }


    @Test
    public void test_isGameFull(){
        Game game = new Game(5,5000);
        game.addOnePlayer(1000);
        game.addOnePlayer(1001);
        game.addOnePlayer(1002);
        game.addOnePlayer(1003);
        game.addOnePlayer(1004);
        assertFalse(game.isGameStarted());
        game.updateStatusNum("SETUP",1);
        game.updateStatusNum("SETUP",1);
        game.updateStatusNum("SETUP",1);
        game.updateStatusNum("SETUP",1);
        assertFalse(game.isGameStarted());
        game.updateStatusNum("SETUP",1);
        assertTrue(game.isGameStarted());
    }


    @Test
    public void test_getPlayer(){
        Game game = new Game(5,5000);
        game.addOnePlayer(1000);
        game.addOnePlayer(1001);
        game.addOnePlayer(1002);
        Player expected = new HumanPlayer(1000);
        assertEquals(expected,game.getPlayer(1000));
        assertNull(game.getPlayer(2000)); //search for a wrong player
    }

    @Test
    public void test_getStatusNum(){
        Game game = new Game(5,5000);
        game.updateStatusNum("SETUP",1);
        game.updateStatusNum("SETUP",1);
        game.updateStatusNum("SETUP",1);
        game.updateStatusNum("SETUP",1);
        game.updateStatusNum("SETUP",1);
        assertEquals(5,game.getStatusNum("SETUP"));
    }

    /*
    @Test
    public void test_combat_update(){
        Game game = new Game(3,5000);
        game.addOnePlayer(1000);
        game.addOnePlayer(1001);
        game.addOnePlayer(1002);
        game.generateMap();
        game.getMap().doDefaultInitializeUnits(1000);
        game.getMap().doDefaultInitializeUnits(1001);
        game.getMap().doDefaultInitializeUnits(1002);
        assertEquals("Game Over.\n",game.showBeforeLeave());
        game.getMap().getTerritoryByName("Elantris").addAttack(1001,1000);
        game.combatAndUpdate();
        assertNull(game.checkGameWinner());
        game.getMap().getTerritoryByName("Narnia").addAttack(1001,1000);
        game.getMap().getTerritoryByName("Mordor").addAttack(1001,1000);
        game.combatAndUpdate();
        game.getMap().getTerritoryByName("Hogwarts").addAttack(1001,1000);
        game.getMap().getTerritoryByName("Scadrial").addAttack(1001,1000);
        game.getMap().getTerritoryByName("Midkemia").addAttack(1001,1000);
        game.combatAndUpdate();
        assertEquals("Blue",game.checkGameWinner());
        assertEquals("Game Over. The Winner of game 5000 is player Blue.\n",game.showBeforeLeave());
    }

     */

}
