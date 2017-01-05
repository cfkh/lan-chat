package decorators;

public class DItalic extends Decorator {

  public DItalic(Object str) {
    super(str);
  }

  @Override
  public String toString() {
    return "<em>" + str + "</em>";
  }

}
