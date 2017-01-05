package commands;

import decorators.DItalic;
import dpatterns.Main;

public class InvalidCommand implements Command {

  private String cmd;
  
  public InvalidCommand(String[] params) {
    this.cmd = params[0];
  }
  
  @Override
  public void execute() {
    Main.insertMessage(new DItalic("<font color=red>Unknown command:</font> " + cmd + "<br/>"));
  }

}
