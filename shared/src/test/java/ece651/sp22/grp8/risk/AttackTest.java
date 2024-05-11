package ece651.sp22.grp8.risk;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class AttackTest {
  @Test
  public void test_get() {
    Attack attack = new Attack(0, "Duke", "UNC", 1,10);
    assertEquals("Duke", attack.getOriginTerritory());
    assertEquals("UNC", attack.getDestTerritory());
    assertEquals(10, attack.getAttackUnits());
    assertEquals(1,attack.getLevel());
  }

  @Test
  public void test_equals() {
    Attack at1 = new Attack(0, "Duke", "UNC", 1,100);
    Attack at2 = new Attack(0, "Duke", "UNC", 1,100);
    Attack at3 = new Attack(0, "Duke", "Drew", 1,100);
    Attack at4 = new Attack(0, "Duke", "UNC", 2,100);
    Attack at5 = new Attack(0, "Duke", "UNC", 1,50);
    assertEquals(at1,at2);
    assertNotEquals(at1,at3);
    assertNotEquals(at1,at4);
    assertNotEquals(at1,at5);
    assertNotEquals(at1,new Map());

  }

}
