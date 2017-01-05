package dpatterns;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SocketReceiver extends Thread {
  private DatagramSocket socket;
  private byte[] buffer = new byte[4096];
  private DatagramPacket packet;
  private boolean running = true;
  private String message = "";
  
  public SocketReceiver(DatagramSocket socket, InetAddress group) {
    this.socket = socket;
    packet = new DatagramPacket(buffer, buffer.length, group, socket.getLocalPort());
  }
  
  public void run() {
    while (running) {
      try {
        socket.receive(packet);
        synchronized (this) {
          message = new String(buffer, 0, packet.getLength());
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
  
  public String receive() {
    String m;
    synchronized (this) {
      m = new String(message);
      message = "";
    }
    return m;
  }
}
