package ece651.sp22.grp8.risk;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UpTechTest {
    @Test
    public void test_cons() {
        UpTech c = new UpTech(10, false);
        assertEquals(c.isUpTech(), false);
        assertEquals(10, c.getPlayerId());
    }

    @Test
    public void test_toString() {
        UpTech c = new UpTech(10, true);
        String expected = "player: 10 wants to upgrade tech: true";
        assertEquals(expected, c.toString());
    }

    @Test
    public void test_isEquals() {
        UpTech c1 = new UpTech(0, false);
        UpTech c2 = new UpTech(0, false);
        UpTech c3 = new UpTech(20, true);

        assertTrue(c1.equals(c2));
        assertFalse(c1.equals(c3));
        assertFalse(c1.equals(1));
    }

}