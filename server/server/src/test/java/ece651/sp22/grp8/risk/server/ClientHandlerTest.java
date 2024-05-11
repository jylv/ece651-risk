package ece651.sp22.grp8.risk.server;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import ece651.sp22.grp8.risk.Utility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static ece651.sp22.grp8.risk.Utility.*;

public class ClientHandlerTest {
    @Test
    public void test_socket(){
        Thread th1 = new Thread(() -> {
            try {
                test_socket_server();
            } catch (Exception ignored) {
            }
        });
        Thread th2 = new Thread(() -> {
            try {
                test_socket_client();
            } catch (Exception ignored) {
            }
        });
    }

    private void test_socket_server() throws IOException {
        ServerSocket ss = new ServerSocket(8888);
        Socket client = ss.accept();
        Utility utility = new Utility(client);
        Assertions.assertEquals("Hello Server!\nSecond line.\nThird line.\n",utility.recvMsg());
        utility.sendMsg("Hello Client!\nSecond line.\nThird line.\n");
        Assertions.assertEquals("Client received.\n",utility.recvMsg());
    }


    private void test_socket_client() throws IOException {
        Socket client = new Socket("127.0.0.1",8888);
        Utility utility = new Utility(client);
        utility.sendMsg("Hello Server!\nSecond line.\nThird line.\n");
        assertEquals("Hello Client!\nSecond line.\nThird line.\n",utility.recvMsg());
        utility.sendMsg("Client received.\n");
    }



}
