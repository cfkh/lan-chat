package commands;

import java.util.Arrays;

import dpatterns.ChatSocket;

public class ChangeUsernameCommand implements Command {

  private ChatSocket socket;
  private String name;
  
  
  public ChangeUsernameCommand(ChatSocket socket, String[] params) {
    this.socket = socket;
    this.name = "";
    if (params.length > 1) {
      for (String w: Arrays.copyOfRange(params, 1, params.length)) {
        this.name += w + " ";
      }
    }
    if (this.name.equals("")) {
      this.name = ChatSocket.generateName((int) (Math.random() * 3) + 3) + " " + ChatSocket.generateName((int) (Math.random() * 3) + 4);
    }
    this.name = this.name.substring(0, this.name.length() - 1);
  }
  
  @Override
  public void execute() {
    socket.changeName(name);
  }

}
