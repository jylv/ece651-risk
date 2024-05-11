package ece651.sp22.grp8.risk;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.*;

public class MoveCheckerTest {
  @Test
  public void test_() {
    MoveChecker m=null;
    MoveChecker mv=new MoveChecker(m);
    MapFactory mf = new MapFactory();
    Map map = mf.generateMapWithPlayer(2);
    Set<Player> set = new HashSet<>();
    Player A=new HumanPlayer(1000);
    Player B=new HumanPlayer(2000);
    set.add(A);
    set.add(B);
    map.setAssign(set);
    mv.checkOwner(map,"Oz",A);
    mv.checkMove(map,"Oz","Rashar",A,1,1);
    mv.checkMove(map,"Oz","Rashar",B,1,0);
    mv.checkMove(map,"Oz","Rashar",B,100,0);
    mv.checkMove(map,"Oz","Rashar",A,100,0);
    mv.checkMove(map,"Oz","Oz",A,100,0);
    mv.checkMove(map,"Oz","Oz",B,100,1);
    mv.checkMove(map,"Oz","Oz",B,0,1);
    mv.checkMove(map,"Oz","Oz",A,100,1);
    mv.checkMove(map,"Oz","Oz",A,100,1);
    mv.checkMove(map,"Mordor","Elantris",A,0,1);
    mv.checkMove(map,"Mordor","Elantris",B,0,1);
    mv.checkMove(map,"Hogwarts","Mordor",B,0,1);
    mv.checkMove(map,"Hogwarts","Mordor",A,0,1);
    mv.checkMove(map,"Oz","Rashar",A,1,-2);
    mv.checkMove(map,"Oz","Rashar",B,1,-2);
    mv.checkMove(map,"Oz","Rashar",B,100,-2);
    mv.checkMove(map,"Oz","Rashar",A,100,-2);
    mv.checkMove(map,"Oz","Oz",A,100,-2);
    mv.checkMove(map,"Oz","Oz",B,100,-2);
    mv.checkMove(map,"Oz","Oz",B,0,-2);
    mv.checkMove(map,"Oz","Oz",A,100,-2);
    mv.checkMove(map,"Oz","Oz",A,100,-2);
    mv.checkMove(map,"Mordor","Elantris",A,0,-2);
    mv.checkMove(map,"Mordor","Elantris",B,0,-2);
    mv.checkMove(map,"Hogwarts","Mordor",B,0,-2);
    mv.checkMove(map,"Hogwarts","Mordor",A,0,-2);
    Territory t=map.getTerritoryByName("Mordor");
    HashMap<Integer, Integer> ll = t.getUnitMap();
    ll.put(0,2);
    t.updateUnitMap(ll);
    String s=mv.checkMove(map,"Elantris","Oz",A,0,1);
    assertEquals(s,GamePrompt.CANNOT_REACH_DEST);
  }

  @Test
  public void test_check(){
    MoveChecker mv=new MoveChecker(null);
    Map map = makeMapForThree();
    Set<Player> set = new HashSet<>();
    Player A=new HumanPlayer(1000);
    Player B=new HumanPlayer(2000);
    Player C=new HumanPlayer(3000);
    set.add(A);
    set.add(B);
    set.add(C);
    map.setAssign(set);

    Territory t=map.getTerritoryByName("Elantris");
    HashMap<Integer, Integer> ll = t.getUnitMap();
    ll.put(0,8);
    ll.put(1,2);
    t.setUnitMap(ll);
    B.foodAmount=20;
    String s=mv.checkMove(map,"Elantris","Oz",B,0,8);
    assertEquals(s,GamePrompt.OK);
  }

  public Map makeMapForThree(){
    Map map = new Map();
    // set initial units
    map.setInitialUnits(9);
    map.initialTech = 18;
    map.initialFood= 18;
    // generate territories
    Territory Narnia = new Territory("Narnia",2,8,8);
    Territory Elantris = new Territory("Elantris",4,6,4);
    Territory Mordor = new Territory("Mordor",3,4,6);

    Territory Midkemia = new Territory("Midkemia",2,8,6);
    Territory Hogwarts = new Territory("Hogwarts",2,8,8);
    Territory Scadrial = new Territory("Scadrial",5,2,4);

    Territory Gondor = new Territory("Gondor",2,10,10);
    Territory Oz = new Territory("Oz",3,4,4);
    Territory Roshar = new Territory("Roshar",4,4,4);

    map.setTerritorySet(new HashSet<>(Arrays.asList(Narnia,Midkemia,Oz,Elantris,Scadrial,Roshar,Mordor,Hogwarts,Gondor)));
    // generate neighbors
    HashMap<String,ArrayList<String>> territoriesMap = new HashMap<>();
    territoriesMap.put("Mordor",new ArrayList<>(Arrays.asList("Oz","Gondor","Scadrial","Hogwarts")));
    territoriesMap.put("Narnia",new ArrayList<>(Arrays.asList("Elantris","Midkemia")));
    territoriesMap.put("Midkemia",new ArrayList<>(Arrays.asList("Narnia","Elantris","Scadrial","Oz")));
    territoriesMap.put("Oz",new ArrayList<>(Arrays.asList("Scadrial","Midkemia","Mordor","Gondor")));
    territoriesMap.put("Gondor",new ArrayList<>(Arrays.asList("Oz","Mordor")));
    territoriesMap.put("Elantris",new ArrayList<>(Arrays.asList("Narnia","Midkemia","Scadrial","Roshar")));
    territoriesMap.put("Scadrial",new ArrayList<>(Arrays.asList("Elantris","Midkemia","Oz","Mordor","Hogwarts","Roshar")));
    territoriesMap.put("Roshar",new ArrayList<>(Arrays.asList("Elantris","Scadrial","Hogwarts")));
    territoriesMap.put("Hogwarts",new ArrayList<>(Arrays.asList("Mordor","Scadrial","Roshar")));
    map.setTerritoriesMap(territoriesMap);
    // generate groups
    ArrayList<Territory> red = new ArrayList<>();
    red.add(Elantris);
    red.add(Midkemia);
    red.add(Narnia);
    red.add(Oz);
    ArrayList<Territory> green = new ArrayList<>();
    green.add(Hogwarts);
    green.add(Mordor);
    green.add(Scadrial);
    ArrayList<Territory> blue = new ArrayList<>();
    blue.add(Gondor);
    blue.add(Roshar);
    HashMap<String,ArrayList<Territory>> group = new HashMap<>();
    group.put("Red",red);
    group.put("Green",green);
    group.put("Blue",blue);
    map.setGroups(group);
    return map;
  }


}
