package ece651.sp22.grp8.risk;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class AttackCheckerTest {
    @Test
    public void test_() {
        AttackChecker a = null;
        AttackChecker ac = new AttackChecker(a);
        MapFactory mf = new MapFactory();
        Map map = mf.generateMapWithPlayer(2);
        Set<Player> set = new HashSet<>();
        Player A=new HumanPlayer(1000);
        Player B=new HumanPlayer(2000);
        set.add(A);
        set.add(B);
        map.setAssign(set);
        ac.checkAttack(map,"Oz","Rashar",A,0,0);
        ac.checkAttack(map,"Oz","Rashar",A,-1,0);
        ac.checkAttack(map,"Oz","Rashar",B,0,1);
        ac.checkAttack(map,"Oz","Oz",B,0,1);
        ac.checkAttack(map,"Oz","Oz",A,0,1);
        ac.checkAttack(map,"Narnia","Midkemia",A,0,1);
        ac.checkAttack(map,"Narnia","Midkemia",B,0,1);
        Territory t = map.getTerritoryByName("Narnia");
        HashMap<Integer, Integer> unitMap = t.getUnitMap();
        unitMap.put(0,5);
        unitMap.put(1,5);
        unitMap.put(2,5);
        t.updateUnitMap(unitMap);
        String s = ac.checkAttack(map, "Narnia", "Midkemia", B, 0, 1);
        assertEquals(s,GamePrompt.FOOD_NOT_ENOUGH);
        B.foodAmount=1000;
        String s2 = ac.checkAttack(map, "Narnia", "Midkemia", B, 0, 1);
        assertEquals(s2,GamePrompt.OK);
        String s3 = ac.checkAttack(map, "Narnia", "Oz", B, 0, 1);
        assertEquals(s3,GamePrompt.CANNOT_ATTACK);
        t.increaseUnit(2,1);
        assertEquals(GamePrompt.NOT_BELONG,ac.checkAttack(map,"Narnia","Midkemia",A,3,1));
    }

}
