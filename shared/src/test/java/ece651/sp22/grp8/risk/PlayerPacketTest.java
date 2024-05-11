package ece651.sp22.grp8.risk;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class PlayerPacketTest {
    @Test
    public void test_send_recv() throws IOException, ClassNotFoundException {
        Player p = new HumanPlayer(1);
        Thread th = new Thread() {
            @Override()
            public void run() {
                try {
                    PlayerPacket mp = new PlayerPacket(p);
                    mp.packPacket(p);
                    Utility u = new Utility(new Socket("localhost", 8892));
                    u.sendPacket(mp);
                    u.recvPacket();
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
        Network.sendPacket(recvPacket);


        server.close();

        assertEquals(p.getClass(), recvPacket.getObject().getClass());

    }

    @Test
    public void test_cons() {
        PlayerPacket mp = new PlayerPacket(new HumanPlayer(1));
        assertEquals(HumanPlayer.class, mp.getObject().getClass());
    }

}
