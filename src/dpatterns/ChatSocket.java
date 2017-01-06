package dpatterns;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import decorators.DBold;
import decorators.DColor;
import decorators.DItalic;

public class ChatSocket {

  private MulticastSocket socket;
  private InetAddress group;
  private SocketReceiver receiver;
  private String name = generateName((int) (Math.random() * 3) + 3) + " " + generateName((int) (Math.random() * 3) + 4);
  private String color = generateColor();

  public ChatSocket(int port, String address) {
    try {
      this.group = InetAddress.getByName(address);
      socket = new MulticastSocket(port);
      socket.joinGroup(group);
      socket.setReuseAddress(true);
      receiver = new SocketReceiver(socket, group);
      receiver.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
    String text = "" + new DItalic(new DBold(new DColor(this.name, color)) + " joined the group " + group + ".");
    send(text);
  }

  public void send(String message) {
    DatagramPacket p = new DatagramPacket(message.getBytes(), message.length(), group, socket.getLocalPort());
    try {
      socket.send(p);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public void say(String message) {
    String text = new DBold(new DColor(name + ": ", color)) + message;
    send(text);
  }
  
  public void shout(String message) {
    String text = "" + new DItalic(new DBold(new DColor(name, color)) + " shouts: " + message);
    send(text);
  }

  public String receive() {
    return receiver.receive();
  }

  public void changeName(String name) {
    String text = "" + new DItalic(new DBold(new DColor(this.name, color)) + " changed their name to " + new DBold(new DColor(name, color)) + ".");
    send(text);
    this.name = name;
  }

  public void changeColor(String color) {
    String text = "" + new DItalic(new DBold(new DColor(this.name, color)) + " changed their user color.");
    send(text);
    this.color = color;
  }
  
  public void changeGroup(String ip) {
    String text = "" + new DItalic(new DBold(new DColor(this.name, color)) + " left the group " + group + ".");
    send(text);
    try {
      socket.leaveGroup(group);
      this.group = InetAddress.getByName(ip);
      socket.joinGroup(this.group);
    } catch (IOException e) {
      e.printStackTrace();
    }
    text = "" + new DItalic(new DBold(new DColor(this.name, color)) + " joined the group " + group + ".");
    send(text);
  }

  public static String generateName(int length) {
    String v = "aeiouy";
    String c = "bcdfghjklmnpqrstvwxz";
    String regex = "([" + v + "][" + c + "]|[" + c + "][" + v + "]|st|ch|br|ph|fl|[bdkp]r"
        + "|([" + v + "](b[bdflst]|c[k]|d[dst]|f[dfklstz]|g[gst]|k[dt]|l[bdfgklmnpstvxz]|m[mst]|n[dfgjkmnprstvxz]|p[fst]|r[bdfgklmnprstvxz]|s[dgkstz]|t[stz]))"
        + "|([" + c + "]([aeiou][aeiou]|[aeou]y|y[aeiou])))$";
    String letters = "abcdefghijklmnopqrstuvwwxyz";
    Pattern p = Pattern.compile(regex);
    String name = "";
    for (int i = 0; i < length; i++) {
      while (true) {
        char letter = (letters).charAt((int) Math.floor((Math.random() * (letters).length())));
        Matcher m = p.matcher(name + letter);
        if (name.equals("") || m.find()) {
          name += letter;
          break;
        }
      }
    }
    return Character.toUpperCase(name.charAt(0)) + name.substring(1);
  }

  public static String generateColor() {
    return "#" + Integer.toHexString((int) (Math.random() * 0xcccccc));
  }

}
