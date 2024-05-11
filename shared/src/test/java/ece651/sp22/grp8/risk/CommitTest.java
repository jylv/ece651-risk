package ece651.sp22.grp8.risk;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CommitTest {
  @Test
  public void test_cons() {
    Commit c = new Commit(10, false);
    assertEquals(c.isCommit(), false);
    assertEquals(10, c.getPlayerId());
  }

  @Test
  public void test_toString() {
    Commit c = new Commit(10, true);
    String expected = "player: 10, status: true";
    assertEquals(expected, c.toString());
  }

  @Test
  public void test_isEquals() {
    Commit c1 = new Commit(0, false);
    Commit c2 = new Commit(0, false);
    Commit c3 = new Commit(20, true);

    assertTrue(c1.equals(c2));
    assertFalse(c1.equals(c3));
    assertFalse(c1.equals(1));
  }

}
