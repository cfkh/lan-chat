package commands;

import dpatterns.ChatSocket;

public class CommandFactory {
  public static Command extractCommand(ChatSocket socket, String text) {
    if (text.startsWith(":")) {
      String[] cmd = text.substring(1).split(" ");
      
      switch (cmd[0].toLowerCase()) {
      case "name":
        return new ChangeUsernameCommand(socket, cmd);
        
      case "color":
        return new ChangeColorCommand(socket, cmd);
        
      case "sound":
        return new MuteCommand();
        
      case "shout":
        return new ShoutCommand(socket, cmd);
      
      case "group":
        return new ChangeGroupCommand(socket, cmd);
        
      default:
        return new InvalidCommand(cmd);
      }
    }
    return null;
  }
}
