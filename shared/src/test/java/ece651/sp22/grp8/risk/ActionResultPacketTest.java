package ece651.sp22.grp8.risk;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

public  class ActionResultPacketTest {
    @Test
    public void test_toString() {
        ActionResult ar = new ActionResult(0,"aa");
        ActionResultPacket ap = new ActionResultPacket(ar);

        ActionResult at = new ActionResult(0, "aa");
        ActionResultPacket apArg = new ActionResultPacket(at);
        String expected = "playerId: 0 getTerritories: [], loseTerritories: []";
        assertEquals(expected, at.toString());
        assertEquals(expected, apArg.toString());
    }

    @Test
    public void test_equals() {
        ActionResult at = new ActionResult(10,"ok");
        ActionResultPacket ap = new ActionResultPacket(at);
        assertEquals(false, ap.equals(at));
    }

    @Test
    public void test_hashCode() {
        ActionResultPacket ap1 = new ActionResultPacket(new ActionResult(0,"ok"));
        ActionResultPacket ap2 = new ActionResultPacket(new ActionResult(1,"ok"));
        ActionResultPacket ap3 = new ActionResultPacket(new ActionResult(0,"ok"));

        assertEquals(ap1, ap3);
        assertEquals(ap1.hashCode(), ap3.hashCode());
        assertNotEquals(ap1.hashCode(), ap2.hashCode());
    }

    @Test
    public void test_send_recv() throws IOException, ClassNotFoundException{
        ActionResult at = new ActionResult(10, "ok");
        Thread th = new Thread() {
            @Override()
            public void run() {
                try {
                    ActionResultPacket ap = new ActionResultPacket(at);
                    ap.packPacket(at);
                    Utility u = new Utility(new Socket("localhost", 8892));
                    u.sendPacket(ap);
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
        assertEquals(at, recvPacket.getObject());
    }

}