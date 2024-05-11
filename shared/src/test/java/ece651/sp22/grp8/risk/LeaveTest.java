package ece651.sp22.grp8.risk;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LeaveTest {
    @Test
    public void test_cons() {
        Leave c = new Leave(10, false);
        assertEquals(c.getLeave(), false);
        assertEquals(10, c.getPlayerId());
    }

    @Test
    public void test_toString() {
        Leave c = new Leave(10, true);
        String expected = "player: 10, leave: true";
        assertEquals(expected, c.toString());
    }

    @Test
    public void test_isEquals() {
        Leave c1 = new Leave(0, false);
        Leave c2 = new Leave(0, false);
        Leave c3 = new Leave(20, true);

        assertTrue(c1.equals(c2));
        assertFalse(c1.equals(c3));
        assertFalse(c1.equals(1));
    }


}