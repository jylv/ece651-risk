package ece651.sp22.grp8.risk;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class UnitUpgradeCheckerTest {
  @Test
  public void test_() {
    UnitUpgradeChecker ch = new UnitUpgradeChecker(null);
    MapFactory mf = new MapFactory();
    Map map = mf.generateMapWithPlayer(2);
    Set<Player> set = new HashSet<>();
    Player A=new HumanPlayer(1000);
    Player B=new HumanPlayer(2000);
    set.add(A);
    set.add(B);
    map.setAssign(set);

    Territory t1 = map.getTerritoryByName("Oz");
    ActionHandler a = new ActionHandler();
    String s1=ch.checkUpgradeUnit(map,B,"Oz",0,1,1,a.costMap);
    assertEquals(s1,GamePrompt.START_NOT_BELONG);
    String s2=ch.checkUpgradeUnit(map,B,"Scadrial",0,1,1,a.costMap);
    assertEquals(s2,GamePrompt.START_NOT_BELONG);

    String s3=ch.checkUpgradeUnit(map,A,"Oz",-1,1,1,a.costMap);
    assertEquals(s3,GamePrompt.UNIT_NOT_EXIST);
    String s4=ch.checkUpgradeUnit(map,A,"Scadrial",0,1,1,a.costMap);
    assertEquals(s4,GamePrompt.UNIT_NOT_ENOUGH);
    HashMap<Integer, Integer> unitMap = t1.getUnitMap();
    int n=unitMap.size();
    assertEquals(n,7);
    unitMap.put(0,5);
    unitMap.put(1,5);
    unitMap.put(2,5);
    unitMap.put(3,5);
    t1.updateUnitMap(unitMap);
    String s5=ch.checkUpgradeUnit(map,A,"Oz",1,1,2,a.costMap);
    assertEquals(s5,GamePrompt.TECH_LEVEL_NOT_ENOUGH);

    A.techLevel=7;
    String s6=ch.checkUpgradeUnit(map,A,"Oz",2,1,1,a.costMap);
    assertEquals(s6,GamePrompt.DIFFERENCE_WRONG);
    String s7=ch.checkUpgradeUnit(map,A,"Oz",1,1,2,a.costMap);
    assertEquals(s7,GamePrompt.TECH_NOT_ENOUGH);
    A.techAmount=100;
    String s8=ch.checkUpgradeUnit(map,A,"Oz",1,1,2,a.costMap);
    assertEquals(s8,GamePrompt.OK);


  }


}
