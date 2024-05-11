package ece651.sp22.grp8.risk;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ActionHandlerTest {
    @Test
    public void test_dealWithMove(){
        ActionHandler ac = new ActionHandler();
        MapFactory mf = new MapFactory();
        Map map = mf.generateMapWithPlayer(2);
        ArrayList<Player> set = new ArrayList<>();
        Player A=new HumanPlayer(1000);
        Player B=new HumanPlayer(2000);
        set.add(B);
        set.add(A);
        map.setAssign(set);
        map.getTerritoryByName("Oz").increaseUnit(0,8);

        Move move = new Move(2000,"Oz","Roshar",0,8);
        HashMap<String,Integer> r1 = ac.dealWithMove(map, A, move);
        for (String s: r1.keySet()){
            assertEquals(-1,r1.get(s));
            assertNotEquals(GamePrompt.OK,s);
        }

        A.addFood(100);
        r1 = ac.dealWithMove(map, A, move);
        for (String s: r1.keySet()){
            assertEquals(48,r1.get(s));
            assertEquals(GamePrompt.OK,s);
        }
    }

    @Test
    public void test_addandUpdateAttack(){
        ActionHandler ac = new ActionHandler();
        MapFactory mf = new MapFactory();
        Map map = mf.generateMapWithPlayer(2);
        Player A=new HumanPlayer(1000);
        Player B=new HumanPlayer(2000);
        Set<Player> set = new HashSet<>();
        set.add(A);
        set.add(B);
        map.setAssign(set);
        Territory t = map.getTerritoryByName("Oz");
        ac.addAttack(t,2000,0,1);
        ac.addAttack(t,2000,0,2);
        ac.addAttack(t,2000,1,4);
        ac.addAttack(t,2000,2,5);
        assertEquals(3,t.attackInfo.get(2000L).get(0));
        assertEquals(4,t.attackInfo.get(2000L).get(1));
        assertEquals(5,t.attackInfo.get(2000L).get(2));
        assertEquals(2,ac.getAttackLevel(t,2000L,false));
        assertEquals(0,ac.getAttackLevel(t,2000L,true));
        t.attackInfo.get(2000L).replace(0,3,0);
        t.attackInfo.get(2000L).replace(2,5,0);
        ac.updateAttack(t,2000);
        assertEquals(1,t.attackInfo.get(2000L).keySet().size());
    }

    @Test
    public void test_rollDice(){
        ActionHandler ac = new ActionHandler();
        assertNotEquals(0,ac.rollDice());
        assertNotEquals(21,ac.rollDice());
    }

    @Test
    public void test_handleAttack(){
        ActionHandler ac = new ActionHandler();
        MapFactory mf = new MapFactory();
        Map m = mf.generateMapWithPlayer(2);
        Player p1 = new HumanPlayer(1);
        Player p2 = new HumanPlayer(2);
        ArrayList<Player> p = new ArrayList<>();
        p.add(p1);
        p.add(p2);
        m.setAssign(p);
        ArrayList<Territory> t1all = m.getTerritoriesByOwnerId(1);
        ArrayList<Territory> t2all = m.getTerritoriesByOwnerId(2);
        for(Territory t: t1all){
            t.increaseUnit(0,5);
        }
        for(Territory t: t2all){
            ac.addAttack(t,1,1,1);
        }
        HashMap<Long,Player> players = new HashMap<>();
        players.put(1L,p1);
        players.put(2L,p2);
        ac.handleAttack(m,players);
        assertEquals(0,m.getTerritoriesByOwnerId(2).size());
        assertEquals(0,m.getGroups().get("Green").size());
    }

    @Test
    public void test_dealWithAttack(){
        ActionHandler ac = new ActionHandler();
        MapFactory mf = new MapFactory();
        Map map = mf.generateMapWithPlayer(2);
        ArrayList<Player> player = new ArrayList<>();
        Player A=new HumanPlayer(1000);
        Player B=new HumanPlayer(2000);
        player.add(A);
        player.add(B);
        map.setAssign(player);
        map.getTerritoryByName("Scadrial").increaseUnit(0,8);

        Attack attack = new Attack(2000,"Scadrial","Elantris",0,2);
        assertEquals(GamePrompt.FOOD_NOT_ENOUGH,ac.dealWithAttack(map,B,attack));
        B.addFood(2);
        assertEquals(GamePrompt.OK,ac.dealWithAttack(map,B,attack));
    }

    @Test
    public void test_update(){
        ActionHandler ac = new ActionHandler();
        MapFactory mf = new MapFactory();
        Map map = mf.generateMapWithPlayer(2);
        ArrayList<Player> player = new ArrayList<>();
        Player A=new HumanPlayer(1000);
        Player B=new HumanPlayer(2000);
        player.add(A);
        player.add(B);
        map.setAssign(player);
        HashMap<Long, Player> players = new HashMap<>();
        players.put(1000L,A);
        players.put(2000L,B);
        ac.updateResource(map,players);
        assertEquals(16,A.getTechAmount());

        map.getTerritoryByName("Elantris").setOwner(B);
        ac.updateGroups(map);
        assertEquals(5,map.getGroups().get("Green").size());

        ac.updateOneTurn(map);
        assertEquals(1,map.getTerritoryByName("Oz").getUnitMap().get(0));

        A.setHasUpgrade(true);
        ac.updateLevel(players);
        assertEquals(2,A.getTechLevel());
    }

    @Test
    public void test_upUnitCost(){
        ActionHandler ac = new ActionHandler();
        assertEquals(548,ac.upUnitCost(1,4,6));
    }

    @Test
    public void test_upFunc(){
        ActionHandler ac = new ActionHandler();
        MapFactory mf = new MapFactory();
        Map map = mf.generateMapWithPlayer(2);
        ArrayList<Player> player = new ArrayList<>();
        Player A=new HumanPlayer(1000);
        Player B=new HumanPlayer(2000);
        player.add(A);
        player.add(B);
        map.setAssign(player);
        HashMap<Long, Player> players = new HashMap<>();
        players.put(1000L,A);
        players.put(2000L,B);


        UpUnit uu = new UpUnit(1000,"Narnia",1,2,3);
        assertNotEquals(GamePrompt.OK,ac.dealWithUpUnit(map,A,uu));
        map.getTerritoryByName("Narnia").increaseUnit(1,3);
        UpTech ut = new UpTech(1000,true);
        assertNotEquals(GamePrompt.OK,ac.dealWithUpTech(A,ut));
        A.addTech(200);
        assertEquals(GamePrompt.OK,ac.dealWithUpTech(A,ut));
        A.addTechLevel();
        A.addTechLevel();
        assertEquals(GamePrompt.OK,ac.dealWithUpUnit(map,A,uu));

    }



    @Test
    public void test_dice() {
        ActionHandler ac = new ActionHandler();
        MapFactory mf = new MapFactory();
        Map map = mf.generateMapWithPlayer(2);
        ArrayList<Player> player = new ArrayList<>();
        Player A = new HumanPlayer(1000);
        Player B = new HumanPlayer(2000);
        player.add(A);
        player.add(B);
        map.setAssign(player);
        HashMap<Long, Player> players = new HashMap<>();
        players.put(1000L, A);
        players.put(2000L, B);

        UpUnit uu = new UpUnit(1000, "Narnia", 1, 2, 3);
        assertNotEquals(GamePrompt.OK, ac.dealWithUpUnit(map, A, uu));
        map.getTerritoryByName("Narnia").increaseUnit(1, 3);
        UpTech ut = new UpTech(1000, true);
        assertNotEquals(GamePrompt.OK, ac.dealWithUpTech(A, ut));
        A.addTech(200);
        assertEquals(GamePrompt.OK, ac.dealWithUpTech(A, ut));
        A.addTechLevel();
        A.addTechLevel();
        assertEquals(GamePrompt.OK, ac.dealWithUpUnit(map, A, uu));
        Territory t = map.getTerritoryByName("Narnia");
        HashMap<Integer, Integer> mmm = t.getUnitMap();
        mmm.put(0, 3);
        mmm.put(1, 3);
        mmm.put(2, 3);
        mmm.put(3, 3);
        t.updateUnitMap(mmm);
        ac.doAttack(t, players);
        HashMap<Integer, Integer> mm = new HashMap<>();
        mm.put(0, 1);
        mm.put(1, 1);
        HashMap<Long, HashMap<Integer, Integer>> at = new HashMap<>();
        at.put(1000L, mm);
        t.attackInfo = at;
        ac.doAttack(t, players);
    }

    @Test
    public void test_doAttack(){
        Territory t = new Territory("Oz");
        t.increaseUnit(0,1);
        Player p1 = new HumanPlayer(1);
        Player p2 = new HumanPlayer(2);
        Player p3 = new HumanPlayer(3);
        t.setOwner(p1);
        HashMap<Long, Player> players = new HashMap<>();
        players.put(1L,p1);
        players.put(2L,p2);
        players.put(3L,p3);

        HashMap<Integer,Integer> attack2 = new HashMap<>();
        attack2.put(0,5);
        attack2.put(1,2);
        t.attackInfo.put(2L,attack2);
        t.attackInfo.put(3L,new HashMap<>());
        ActionHandler ac = new ActionHandler();
        ac.doAttack(t,players);
        assertNotEquals(p3,t.getOwner());

    }

}