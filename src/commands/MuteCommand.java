package commands;

import decorators.DItalic;
import dpatterns.Main;

public class MuteCommand implements Command {
  
  @Override
  public void execute() {
    Main.muted = !Main.muted;
    Main.insertMessage(new DItalic("Sound is now " + (Main.muted ? "OFF" : "ON") + "<br/>"));
  }

}
