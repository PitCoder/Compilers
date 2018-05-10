public class Symbol {
  private String name;
  private String funcName;
  private short type;
  private Polynomial data;

  public Symbol(String name, String funcName, short type, Polynomial data) {
    this.name = name;
    this.funcName = funcName;
    this.type = type;
    this.data = data;
  }

  public String getName() {
    return this.name;
  }

  public String getFuncName() {
    return this.funcName;
  }

  public short getType() {
    return this.type;
  }

  public Polynomial getData() {
    return this.data;
  }

  public void setData(Polynomial data) {
    this.data = data;
  }
}
