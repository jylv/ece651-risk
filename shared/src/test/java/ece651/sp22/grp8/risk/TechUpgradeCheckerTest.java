package ece651.sp22.grp8.risk;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TechUpgradeCheckerTest {
    @Test
    public void test_() {
        TechUpgradeChecker ch = new TechUpgradeChecker(null);
        MapFactory mf = new MapFactory();
        Map map = mf.generateMapWithPlayer(2);
        Set<Player> set = new HashSet<>();
        Player A=new HumanPlayer(1000);
        Player B=new HumanPlayer(2000);
        set.add(A);
        set.add(B);
        map.setAssign(set);
        ActionHandler a = new ActionHandler();
        String s = ch.techLevelChecker(A,a.upgradeCost);
        assertEquals(s,GamePrompt.TECH_NOT_ENOUGH);
        A.techAmount=1000;
        String s3 = ch.techLevelChecker(A,a.upgradeCost);
        assertEquals(s3,GamePrompt.OK);
        A.techLevel=6;
        String s2 = ch.techLevelChecker(A,a.upgradeCost);
        assertEquals(s2,GamePrompt.TECH_LEVEL_MAX);
        A.hasUpgrade=true;
        String s1 = ch.techLevelChecker(A,a.upgradeCost);
        assertEquals(s1,GamePrompt.HAS_UPDATE);
    }


}