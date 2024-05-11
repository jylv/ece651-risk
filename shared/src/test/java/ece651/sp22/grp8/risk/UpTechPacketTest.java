package ece651.sp22.grp8.risk;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class UpTechPacketTest {
    @Test
    public void test_Commit_cons() {
        UpTechPacket cp = new UpTechPacket(new UpTech(0, false));
        String expected = "player: 0 wants to upgrade tech: false";
        assertEquals(expected, cp.toString());
    }

    @Test
    public void test_packCommit() {
        UpTech msg = new UpTech(0, true);
        UpTechPacket cp = new UpTechPacket(msg);
        cp.packPacket(msg);

        assertEquals(true, cp.getObject().toString().equals(msg.toString()));
    }

    @Test
    public void test_hashCode() {
        UpTechPacket cp = new UpTechPacket(new UpTech(0, false));
        UpTechPacket cp2 = new UpTechPacket(new UpTech(1, true));
        UpTechPacket cp3 = new UpTechPacket(new UpTech(0, false));

        assertEquals(cp.hashCode(), cp3.hashCode());
        assertNotEquals(cp.hashCode(), cp2.hashCode());
    }

    @Test
    public void test_toString() {
        UpTechPacket cp = new UpTechPacket(new UpTech(0, false));
        UpTechPacket cp2 = new UpTechPacket(new UpTech(1, true));
        UpTechPacket cp3 = new UpTechPacket(new UpTech(0, false));

        assertEquals(cp.toString(), cp3.toString());
        assertNotEquals(cp.toString(), cp2.toString());
    }

    @Test
    public void test_equals() {
        UpTechPacket cp = new UpTechPacket(new UpTech(0, false));
        UpTechPacket cp2 = new UpTechPacket(new UpTech(1, true));
        UpTechPacket cp3 = new UpTechPacket(new UpTech(0, false));

        assertEquals(cp, cp3);
        assertNotEquals(cp, cp2);

        UpTech c = new UpTech(0, false);
        assertEquals(false, cp3.equals(c));
    }


    @Test
    public void test_send_recv() throws IOException, ClassNotFoundException {
        UpTech commit = new UpTech(102, false);
        Thread th = new Thread() {
            @Override()
            public void run() {
                try {
                    UpTechPacket cp = new UpTechPacket(commit);
                    cp.packPacket(commit);
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

        assertEquals(commit, recvPacket.getObject());
    }

}