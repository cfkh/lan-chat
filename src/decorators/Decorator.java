package decorators;

public abstract class Decorator {
  protected Object str;
  
  public Decorator(Object str) {
    this.str = str;
  }

  public abstract String toString();
}
