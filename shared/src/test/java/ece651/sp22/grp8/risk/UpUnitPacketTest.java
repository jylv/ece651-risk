package ece651.sp22.grp8.risk;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class UpUnitPacketTest {
    @Test
    public void test_const() {
        UpUnit uu = new UpUnit(0, "Mars", 1, 2,3);
        UpUnitPacket ap = new UpUnitPacket(uu);
        ap.packPacket(uu);
        assertEquals(uu,ap.getObject());
        String expected = "player: 0 want to upgrade 2 units from level 1 to level 3 in Mars";
        assertEquals(expected,ap.toString());
        UpUnit u1 = new UpUnit(0, "on", 2,3,4);
        UpUnitPacket ap1 = new UpUnitPacket(u1);
        assertNotEquals(ap1,ap);
        assertNotEquals(ap1,2);
        assertNotEquals(ap1.hashCode(),ap.hashCode());
    }

    @Test
    public void test_send_recv() throws IOException, ClassNotFoundException {
        UpUnit mv = new UpUnit(10, "USA", 1,2,3);
        Thread th = new Thread() {
            @Override()
            public void run() {
                try {
                    UpUnitPacket mp = new UpUnitPacket(mv);
                    mp.packPacket(mv);
                    Utility u = new Utility(new Socket("localhost", 8893));
                    u.sendPacket(mp);
                }

                catch (IOException e) {
                }
            }
        };

        th.start();
        ServerSocket server = new ServerSocket(8893, 10);
        Socket Connectionfd = server.accept();
        Utility Network = new Utility(Connectionfd);
        Packet recvPacket = (Packet) Network.recvPacket();
        server.close();

        assertEquals(mv, recvPacket.getObject());
    }

}