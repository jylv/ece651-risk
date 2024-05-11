package ece651.sp22.grp8.risk;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UpUnitTest {
    @Test
    public void test_const(){
        UpUnit uu = new UpUnit(1,"begin",1,3,4);
        assertEquals("begin",uu.getTerritory());
        assertEquals(1,uu.getOriLevel());
        assertEquals(3,uu.getNum());
        assertEquals(4,uu.getDestLevel());
        String expected = "player: 1 want to upgrade 3 units from level 1 to level 4 in begin";
        assertEquals(expected,uu.toString());
    }

    @Test
    public void test_equal(){
        UpUnit u1 = new UpUnit(1,"begin",1,3,4);
        UpUnit u2 = new UpUnit(2,"begin",1,3,4);
        UpUnit u3 = new UpUnit(1,"oz",1,3,4);
        UpUnit u4 = new UpUnit(1,"begin",2,3,4);
        UpUnit u5 = new UpUnit(1,"begin",1,9,4);
        UpUnit u6 = new UpUnit(1,"begin",1,3,3);
        assertNotEquals(u1,u2);
        assertNotEquals(u1,u3);
        assertNotEquals(u1,u4);
        assertNotEquals(u1,u5);
        assertNotEquals(u1,u6);
        assertNotEquals(u1,1);
    }

}