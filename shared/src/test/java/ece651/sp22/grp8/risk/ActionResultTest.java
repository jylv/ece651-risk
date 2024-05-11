package ece651.sp22.grp8.risk;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class ActionResultTest {
    @Test
    public void test_All(){
        //set and get
        ActionResult ar = new ActionResult(1L,"ok");
        ar.addLostTerri("oz");
        ar.addGetTerri("narnia");
        HashSet<String> h1 = new HashSet<>();
        h1.add("oz");
        HashSet<String> h2 = new HashSet<>();
        h2.add("narnia");
        assertEquals("ok",ar.getGameStatus());
        assertEquals(h1,ar.getLostTerri());
        assertEquals(h2,ar.getGetTerri());
        //to string
        assertEquals("playerId: 1 getTerritories: [narnia], loseTerritories: [oz]",ar.toString());
        //equal
        ActionResult ar2 = new ActionResult(1L,"ok");
        ar2.addLostTerri("oz");
        assertNotEquals(ar, ar2);
        ar2.addGetTerri("narnia");
        assertEquals(ar,ar2);
        assertNotEquals(ar, "a");
    }

}