public class Symbol {
  private String    symbolName;
  private String    functionName;
  private short     type;
  private Complejo  data;

  public Symbol(String symName, String funName, short type, Complejo data) {
    this.symbolName   = symName;
    this.functionName = funName;
    this.type         = type;
    this.data         = data;
  }

  public String getSymbolName() {return symbolName;}
  public String getFunctionName() {return functionName;}
  public short getType() {return type;}
  public Complejo getData() {return data;}

  public void setData(Complejo data) {this.data = data;}
}
