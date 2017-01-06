package commands;

import dpatterns.ChatSocket;

public class CommandFactory {
  public static Command extractCommand(ChatSocket socket, String text) {
    if (text.startsWith(":")) {
      String[] params = text.substring(1).split(" ");
      
      switch (params[0].toLowerCase()) {
      case "name":
        return new ChangeUsernameCommand(socket, params);
        
      case "color":
        return new ChangeColorCommand(socket, params);
        
      case "sound":
        return new MuteCommand();
        
      case "shout":
        return new ShoutCommand(socket, params);
      
      case "group":
        return new ChangeGroupCommand(socket, params);
        
      case "me":
        return new MeCommand(socket, params);
        
      case "clear":
        return new ClearCommand();
        
      default:
        return new InvalidCommand(params);
      }
    }
    return null;
  }
}
