package ece651.sp22.grp8.risk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.HashMap;
import java.util.HashSet;

import ece651.sp22.grp8.risk.HumanPlayer;
import ece651.sp22.grp8.risk.Player;
import ece651.sp22.grp8.risk.Territory;
import org.junit.jupiter.api.Test;

public class TerritoryTest {
  @Test
  public void test_Constructors() {
    Territory t = new Territory("Gondor",2,3,5);
    Player p = new HumanPlayer(1);
    t.setOwner(p);
    assertEquals("Gondor",t.getName());
    assertEquals(2,t.size);
    assertEquals(3,t.foodProd);
    assertEquals(5,t.techProd);
    Territory t2 = new Territory("Oz");
    assertEquals(0,t2.size);
    assertEquals(0,t.getUnitsSum());
    assertEquals(p,t.getOwner());
  }

  @Test
    public void test_units(){
      Territory t = new Territory("Gondor",2,3,5);
      HashMap<Integer,Integer> newUnits = new HashMap<>();
      newUnits.put(0,0);
      newUnits.put(1,1);
      newUnits.put(2,2);
      newUnits.put(3,3);
      newUnits.put(4,4);
      newUnits.put(5,5);
      newUnits.put(6,6);
      t.updateUnitMap(newUnits);
      assertEquals(1,t.getLevel(true));
      assertEquals(6,t.getLevel(false));
      assertEquals(5,t.getUnitMap().get(5));
      t.increaseUnit(1,4);
      assertEquals(5,t.getUnitMap().get(1));
      t.decreaseUnit(4,2);
      assertEquals(2,t.getUnitMap().get(4));
      t.increaseUnit(0,2);
      assertEquals(0,t.getLevel(true));
      assertEquals(25,t.getUnitsSum());
      Territory t2 = new Territory("oz");
      assertEquals(-1,t2.getLevel(true));
      assertEquals(-1,t2.getLevel(false));
  }

  @Test
  public void test_equalAndHashCode(){
      Territory t1 = new Territory("Gondor",1,1,1);
      Territory t2 = new Territory("Gondor",1,1,1); //equal
      Territory t3 = new Territory("Gondor",2,1,1); //size
      Territory t4 = new Territory("Gondor",1,2,1); //food
      Territory t5 = new Territory("Gondor",1,1,2); //tech
      Territory t6 = new Territory("Gondor",1,1,1); //unitsMap
      t6.increaseUnit(0,1);
      Territory t7 = new Territory("Oz",1,1,1); //name
      Territory t8 = new Territory("Gondor",1,1,1); //owner
      Player p1 = new HumanPlayer(1);
      Player p2 = new HumanPlayer(2);
      t1.setOwner(p1);
      t2.setOwner(p1);
      t3.setOwner(p1);
      t4.setOwner(p1);
      t5.setOwner(p1);
      t6.setOwner(p1);
      t7.setOwner(p1);
      t8.setOwner(p2);
      assertEquals(t1,t2);
      assertNotEquals(t1,t3);
      assertNotEquals(t1,t4);
      assertNotEquals(t1,t5);
      assertNotEquals(t1,t6);
      assertNotEquals(t1,t7);
      assertNotEquals(t1,t8);
      assertNotEquals(t1,"s");
      assertEquals(t1.hashCode(),t2.hashCode());
      assertNotEquals(t1.hashCode(),t3.hashCode());
  }


//
//  @Test
//  public void test_attack(){
//    Player p1 = new HumanPlayer(1);
//    Player p2 = new HumanPlayer(2);
//    Player p3 = new HumanPlayer(3);
//    Player p4 = new HumanPlayer(4);
//    HashMap<Long,Player> players = new HashMap<>();
//    players.put(1L,p1);
//    players.put(2L,p2);
//    players.put(3L,p3);
//    players.put(4L,p3);
//
//    Territory t = new Territory("Oz");
//    t.setOwner(p1);
//
//    t.addAttack(2,3);
//    t.addAttack(2,2);
//    t.addAttack(3,5);
//    t.addAttack(4,0);
//    assertEquals(5,t.attackInfo.get(2L));
//    t.doAttack(players);
//    assertEquals(0,t.attackInfo.size());
//  }
//
//  @Test
//  public void test_rollDice(){
//    Territory t = new Territory("Oz");
//    assertNotEquals(0,t.rollDice());
//    assertNotEquals(21,t.rollDice());
//  }






}
