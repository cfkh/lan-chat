package commands;

import java.util.Arrays;

import decorators.DBold;
import decorators.DColor;
import decorators.DItalic;
import dpatterns.ChatSocket;

public class MeCommand implements Command {

  private ChatSocket socket;
  private String[] words;
  
  public MeCommand(ChatSocket socket, String[] params) {
    this.socket = socket;
    this.words = params;
  }
  
  @Override
  public void execute() {
    String line = "";
    for (String w: Arrays.copyOfRange(words, 1, words.length)) {
      line += w + " ";
    }
    String text = "" + new DItalic(new DBold(new DColor(socket.name, socket.color)) + " " + line.substring(0, line.length() - 1));
    socket.send(text);
  }

}
