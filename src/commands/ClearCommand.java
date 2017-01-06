package commands;

import dpatterns.Main;

public class ClearCommand implements Command {
  
  @Override
  public void execute() {
    Main.textPane.setText("<html><head></head><body></body></html>");
  }

}
