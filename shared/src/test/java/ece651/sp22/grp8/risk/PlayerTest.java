package ece651.sp22.grp8.risk;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    public void test_setAndGet(){
        Player p = new HumanPlayer(1);
        p.setColor("Red");
        p.setGameID(1000);
        p.setPlayerID(20);
        p.addTech(2);
        p.addFood(3);
        assertEquals("Red",p.getColor());
        assertEquals(1000,p.getGameID());
        assertEquals(20,p.getPlayerID());
        assertEquals(2,p.getTechAmount());
        assertEquals(3,p.getFoodAmount());
        assertFalse(p.isHasUpgrade());
        p.setHasUpgrade(true);
        assertTrue(p.isHasUpgrade());
        p.decFood(1);
        p.decTech(1);
        assertEquals(1,p.getTechAmount());
        assertEquals(2,p.getFoodAmount());
        p.addTechLevel();
        assertEquals(2,p.getTechLevel());
    }

    @Test
    public void test_hashcodeAndEqual(){
        Player p1 = new HumanPlayer(1);
        p1.setGameID(1000);
        Player p2 = new HumanPlayer(1);
        p2.setGameID(1000);
        Player pp = new ComputerPlayer(1);
        pp.setGameID(1000);
        assertEquals(p1,p2);
        assertNotEquals(p1,pp);
        assertEquals(p1.hashCode(),p2.hashCode());


        p1.setColor("Red");
        p2.setColor("Red");
        assertEquals(p1,p2);
        assertEquals(p1.hashCode(),p2.hashCode());

        Player p3 = new HumanPlayer(2);
        p3.setColor("Red");
        p3.setGameID(1000);
        assertNotEquals(p1.hashCode(),p3.hashCode());
        assertNotEquals(p1,p3);
        assertNotEquals(p1,1);

        Player p4 = new HumanPlayer(1);
        p4.setColor("Red");
        p4.setGameID(2000);
        assertNotEquals(p1.hashCode(),p4.hashCode());
        assertNotEquals(p1,p4);

        Player p5 = new HumanPlayer(1);
        p5.setColor("Green");
        p5.setGameID(1000);
        assertNotEquals(p1.hashCode(),p5.hashCode());
        assertNotEquals(p1,p5);

        String expected = "Player: Red\n" +
                "playerID: 1\n" +
                "gameID: 1000\n" +
                "techLevel: 1\n" +
                "foodAmount: 0\n" +
                "techAmount: 0";
        assertEquals(expected,p1.toString());
    }
}