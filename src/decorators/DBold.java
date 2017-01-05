package decorators;

public class DBold extends Decorator {

  public DBold(Object str) {
    super(str);
  }

  @Override
  public String toString() {
    return "<b>" + str + "</b>";
  }

}
