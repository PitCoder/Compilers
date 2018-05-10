public class Symbol {
  String name;
  short type;
  Polynomial data;

  public Symbol(String name, short type, Polynomial data) {
    this.name = name;
    this.type = type;
    this.data = data;
  }

  public String getName() {return name;}
  public short getType() {return type;}
  public Polynomial getData() {return data;}

  public void setData(Polynomial data) {this.data = data;}
}
