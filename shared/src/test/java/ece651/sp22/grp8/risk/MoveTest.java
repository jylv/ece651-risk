package ece651.sp22.grp8.risk;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class MoveTest {
  @Test
  public void test_get() {
    Move move = new Move(0, "Duke", "UNC", 1,10);
    assertEquals("Duke", move.getOriTerri());
    assertEquals("UNC", move.getDestTerri());
    assertEquals(10, move.getMoveUnits());
    assertEquals(1,move.getLevel());
  }

  @Test
  public void test_equals() {
    Move at1 = new Move(0, "Duke", "UNC", 1,100);
    Move at2 = new Move(0, "Duke", "UNC", 1,100);
    Move at3 = new Move(0, "Duke", "Drew", 1,100);
    Move at4 = new Move(0, "Duke", "UNC", 2,100);
    Move at5 = new Move(0, "Duke", "UNC", 1,50);
    assertEquals(at1,at2);
    assertNotEquals(at1,at3);
    assertNotEquals(at1,at4);
    assertNotEquals(at1,at5);
    assertNotEquals(at1,new Map());

  }

}
