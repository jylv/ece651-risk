package ece651.sp22.grp8.risk;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;

public class Utility {
  private final Socket client;
  private final HashMap<Socket, ObjectOutputStream> ConnectionOutMap;
  private final HashMap<Socket, ObjectInputStream> ConnectionInMap;

  public Utility(Socket s) {
    client = s;
    ConnectionInMap = new HashMap<>();
    ConnectionOutMap = new HashMap<>();
  }

  public void sendMsg(String msg) {
    StringPacket sp = new StringPacket();
    sp.packPacket(msg);
    try {
      this.sendPacket(sp);
    }catch (IOException ignored) {
    }
  }

  public String recvMsg() {
    StringBuilder sb = new StringBuilder();
    try {
      StringPacket recvPacket = (StringPacket)recvPacket();
      String recvString = recvPacket.getObject();
      sb.append(recvString);
    } catch (IOException ignored) {
    }
    return sb.toString();
  }

  /**
   * Generate ObjectOutputStream based on Socket s
   *
   **/
  private ObjectOutputStream genObjectOutputStream() throws IOException {
    ObjectOutputStream objectout = ConnectionOutMap.get(client);
    if (!ConnectionOutMap.containsKey(client)) {
      OutputStream out = client.getOutputStream();
      objectout = new ObjectOutputStream(out);
      ConnectionOutMap.put(client, objectout);
    }
    
    return objectout;
  }

  /**
   * generate Input Stream object based on given socket
   *
   * @return ObjectInputStream
   * @throws IOException
   **/
  private ObjectInputStream genObjectInputStream() throws IOException {
    ObjectInputStream inObject = ConnectionInMap.get(client);
    if (ConnectionInMap.containsKey(client) == false) {
      InputStream in = client.getInputStream();
      inObject = new ObjectInputStream(in);
      ConnectionInMap.put(client, inObject);
    }
    return inObject;
  }

  /**
   * Send Object to specified socket file
   *
   * @param o: object to send
   * @throws IOException
   **/
  public void sendPacket(Packet o) throws IOException {
    ObjectOutputStream objectout = genObjectOutputStream();
    objectout.reset();
    objectout.writeObject(o);
    objectout.flush();
  }

  /**
   * receive object from spcified socket
   *
   * @throws IOException, {@link ClassNotFoundException}
   **/
  public Packet recvPacket() throws IOException {
    ObjectInputStream inObject = genObjectInputStream();
    Packet packet = null;
    try {
      packet = (Packet) inObject.readObject();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return packet;
  }
}
