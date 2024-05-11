package ece651.sp22.grp8.risk;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

public class LeavePacketTest {
    @Test
    public void test_Leave_cons() {
        LeavePacket cp = new LeavePacket(new Leave(0, false));
        LeavePacket cp2 = new LeavePacket();
        assertEquals(null, cp2.getObject());
        String expected = "player: 0, leave: false";
        assertEquals(expected, cp.toString());
    }

    @Test
    public void test_packLeave() {
        Leave msg = new Leave(0, true);
        LeavePacket cp = new LeavePacket();
        cp.packPacket(msg);

        assertEquals(true, cp.getObject().toString().equals(msg.toString()));
    }

    @Test
    public void test_hashCode() {
        LeavePacket cp = new LeavePacket(new Leave(0, false));
        LeavePacket cp2 = new LeavePacket(new Leave(1, true));
        LeavePacket cp3 = new LeavePacket(new Leave(0, false));

        assertEquals(cp.hashCode(), cp3.hashCode());
        assertNotEquals(cp.hashCode(), cp2.hashCode());
    }

    @Test
    public void test_toString() {
        LeavePacket cp = new LeavePacket(new Leave(0, false));
        LeavePacket cp2 = new LeavePacket(new Leave(1, true));
        LeavePacket cp3 = new LeavePacket(new Leave(0, false));

        assertEquals(cp.toString(), cp3.toString());
        assertNotEquals(cp.toString(), cp2.toString());
    }

    @Test
    public void test_equals() {
        LeavePacket cp = new LeavePacket(new Leave(0, false));
        LeavePacket cp2 = new LeavePacket(new Leave(1, true));
        LeavePacket cp3 = new LeavePacket(new Leave(0, false));

        assertEquals(cp, cp3);
        assertNotEquals(cp, cp2);

        Leave c = new Leave(0, false);
        assertEquals(false, cp3.equals(c));
    }

    @Test
    public void test_send_recv() throws IOException, ClassNotFoundException {
        Leave leave = new Leave(102, false);
        Thread th = new Thread() {
            @Override()
            public void run() {
                try {
                    LeavePacket cp = new LeavePacket();
                    cp.packPacket(leave);
                    Utility u = new Utility(new Socket("localhost", 8892));
                    u.sendPacket(cp);
                }
                catch (IOException e) {
                }
            }
        };

        th.start();
        ServerSocket server = new ServerSocket(8892, 10);
        Socket Connectionfd = server.accept();
        Utility Network = new Utility(Connectionfd);
        Packet recvPacket = (Packet) Network.recvPacket();
        server.close();

        assertEquals(leave, recvPacket.getObject());
    }

}