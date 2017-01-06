package commands;

import decorators.DBold;
import decorators.DColor;
import decorators.DItalic;
import dpatterns.ChatSocket;

public class ChangeColorCommand implements Command {

  private ChatSocket socket;
  private String color;
  
  
  public ChangeColorCommand(ChatSocket socket, String[] params) {
    this.socket = socket;
    this.color = params.length >=2 ? params[1] : ChatSocket.generateColor();
  }
  
  @Override
  public void execute() {
    String text = "" + new DItalic(new DBold(new DColor(socket.name, color)) + " changed their user color.");
    socket.send(text);
    socket.color = color;
  }

}
