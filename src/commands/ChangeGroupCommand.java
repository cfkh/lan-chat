package commands;

import dpatterns.ChatSocket;
import dpatterns.Main;

public class ChangeGroupCommand implements Command {

  private ChatSocket socket;
  private String ip;
  
  
  public ChangeGroupCommand(ChatSocket socket, String[] params) {
    this.socket = socket;
    this.ip = params.length >=2 ? params[1] : "";
  }
  
  @Override
  public void execute() {
    socket.changeGroup(ip);
  }

}
