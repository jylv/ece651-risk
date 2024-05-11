package ece651.sp22.grp8.risk;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ComputerPlayerTest {
    @Test
    public void test_Equal(){
        Player p1 = new ComputerPlayer(1);
        p1.setGameID(1000);
        Player p2 = new ComputerPlayer(1);
        p2.setGameID(1000);
        assertEquals(p1,p2);
        p1.setColor("Red");
        p2.setColor("Red");
        assertEquals(p1,p2);
        p2.addFood(1);
        assertNotEquals(p1,p2);
        p1.addFood(1);
        assertEquals(p1,p2);
        p2.addTech(1);
        assertNotEquals(p1,p2);


        Player p3 = new ComputerPlayer(2);
        p3.setColor("Red");
        p3.setGameID(1000);
        assertNotEquals(p1,p3);

        Player p4 = new ComputerPlayer(1);
        p4.setColor("Red");
        p4.setGameID(2000);
        assertNotEquals(p1,p4);

        Player p5 = new HumanPlayer(1);
        p5.setGameID(1000);
        p5.setColor("Red");
        assertNotEquals(p1,p5);
    }

}
