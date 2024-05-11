package ece651.sp22.grp8.risk;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.*;
import java.util.stream.Collectors;

public class MapTest {
    @Test
    public void test_mapSet() {
        MapFactory mf = new MapFactory();
        Map m = mf.generateMapWithPlayer(2);
        Set<Territory> all = m.getTerritorySet();
        HashMap<String, ArrayList<Territory>> gg = m.getGroups();
        ArrayList<String> s = (ArrayList<String>) gg.keySet().stream().collect(Collectors.toList());
        assertEquals(s.size(),2);
        Set<Territory> la = m.getTerritorySet();
        ArrayList<Territory> b = m.getGroups().get("Red");
        assertEquals(b.size(),4);
        assertNotEquals(la.size(),0);
        assertEquals(gg.size(),2);
        assertEquals(8,all.size());
        assertNotNull(all);
        assertEquals(8,m.getTerritorySize());
    }

    @Test
    public void test_handle_attack(){
        MapFactory mf = new MapFactory();
        Map m = mf.generateMapWithPlayer(2);
        MapTextView view = new MapTextView(m);
        Player p1 = new HumanPlayer(1);
        Player p2 = new HumanPlayer(2);
        ArrayList<Player> p = new ArrayList<>();
        p.add(p1);
        p.add(p2);
        m.setAssign(p);
        ArrayList<Territory> t1all = m.getTerritoriesByOwnerId(1);
        ArrayList<Territory> t2all = m.getTerritoriesByOwnerId(2);
        HashMap<Long,Player> players = new HashMap<>();
        players.put(1L,p1);
        players.put(2L,p2);
        assertEquals(4,m.getTerritoriesByOwnerId(2).size());
        assertEquals(4,m.getGroups().get("Green").size());
    }

    @Test
    public void test_initTerriAndUpdate(){
        MapFactory mf = new MapFactory();
        Map m = mf.generateMapWithPlayer(2);
        MapTextView view = new MapTextView(m);
        HashMap<String,Integer> add = new HashMap<>();
        add.put("Elantris",3);
        add.put("Narnia",2);
        add.put("Mordor",1);
        add.put("Hogwarts",4);
        InitializeUnit IU = new InitializeUnit(1L,add);
        m.initTerritoryUnits(IU,"Red");
    }

    @Test
    public void test_mapGetter(){
        MapFactory mf = new MapFactory();
        Map m = mf.generateMapWithPlayer(2);
        Player p1 = new HumanPlayer(1);
        Player p2 = new HumanPlayer(2);
        ArrayList<Player> p = new ArrayList<>();
        p.add(p1);
        p.add(p2);
        m.setAssign(p);
        m.setInitialUnits(8);
        Map m2 = mf.generateMapWithPlayer(2);
        m2.setAssign(p);
        m2.setInitialUnits(8);
        Map m3 = mf.generateMapWithPlayer(2);
        m3.setInitialUnits(8);
        ArrayList<Player> pp2 = new ArrayList<>();
        p.add(p2);
        p.add(p1);
        m3.setAssign(pp2);

        assertEquals(8,m.getInitialUnits());
        assertEquals("Red",m.getAssign(1));
        assertNull(m.getTerritoryByName("AA"));
        assertEquals(m,m2);
        assertNotEquals(m,m3);
        assertNotEquals(m,"a");
        m2.getGroups().get("Red").clear();
        assertNotEquals(m,m2);

    }

    @Test
    public void test_compareSet(){
        MapFactory mf = new MapFactory();
        Map m = mf.generateMapWithPlayer(2);
        Territory t1 = m.getTerritoryByName("Oz");
        Territory t2 = m.getTerritoryByName("Narnia");
        ArrayList<Territory> h1 = new ArrayList<>();
        ArrayList<Territory> h2 = new ArrayList<>();
        h1.add(t1);
        h2.add(t2);
        assertFalse(m.compareSetTerritory(h1,h2));
        h1.add(t2);
        assertFalse(m.compareSetTerritory(h1,h2));
        h2.add(t1);
        h2.add(t2);
        assertFalse(m.compareSetTerritory(h1,h2));
    }

    @Test
    public void test_equal() {
        MapFactory mf=new MapFactory();
        Map m1=mf.generateMapWithPlayer(2);
        Map m2=mf.generateMapWithPlayer(2);
        m1.setInitialUnits(30);
        m2.setInitialUnits(30);
        Player p1 = new HumanPlayer(1);
        Player p2 = new HumanPlayer(2);
        ArrayList<Player> p = new ArrayList<>();
        p.add(p1);
        p.add(p2);
        m1.setAssign(p);
        m2.setAssign(p);
        m1.equals(m2);
        HashMap<String, ArrayList<Territory>> a1 = m1.getGroups();
        for(String str:a1.keySet()){
            a1.put(str,new ArrayList<>());
        }
        m1.setGroups(a1);
        m1.equals(m2);
    }

    @Test
    public void test_default() {
        MapFactory mf = new MapFactory();
        Map m = mf.generateMapWithPlayer(2);
        m.getTerritoriesByOwnerId(0);
        Player p1 = new HumanPlayer(1);
        Player p2 = new HumanPlayer(2);
        ArrayList<Player> p = new ArrayList<>();
        p.add(p1);
        p.add(p2);
        m.setAssign(p);
        m.doDefaultInitializeUnits(1);
        m.doDefaultInitializeUnits(2);
        m.doDefaultInitializeUnits(3);
    }


}
