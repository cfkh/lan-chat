package commands;

import dpatterns.ChatSocket;
import dpatterns.Main;

public class ChangeColorCommand implements Command {

  private ChatSocket socket;
  private String color;
  
  
  public ChangeColorCommand(ChatSocket socket, String[] params) {
    this.socket = socket;
    this.color = params.length >=2 ? params[1] : ChatSocket.generateColor();
  }
  
  @Override
  public void execute() {
    socket.changeColor(color);
  }

}
