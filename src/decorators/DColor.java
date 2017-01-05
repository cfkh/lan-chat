package decorators;

public class DColor extends Decorator {

  private String color;
  
  public DColor(Object str, String color) {
    super(str);
    this.color = color;
  }

  @Override
  public String toString() {
    return "<font color=\"" + color + "\">" + str + "</font>";
  }

}
