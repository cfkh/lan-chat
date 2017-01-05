package commands;

import java.util.Arrays;

import dpatterns.ChatSocket;

public class ShoutCommand implements Command {

  private ChatSocket socket;
  private String[] words;
  
  public ShoutCommand(ChatSocket socket, String[] params) {
    this.socket = socket;
    this.words = params;
  }
  
  @Override
  public void execute() {
    String line = "";
    for (String w: Arrays.copyOfRange(words, 1, words.length)) {
      line += w.toUpperCase() + " ";
    }
    socket.shout(line.substring(0, line.length() - 1));
  }

}
