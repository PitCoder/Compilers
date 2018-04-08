public class Symbol {
  private String      symbolName;
  private String      functionName;
  private short       type;
  private Polynomial  data;

  public Symbol(String symbName, String funcName, short type, Polynomial data) {
    this.symbolName   = symbName;
    this.functionName = funcName;
    this.type         = type;
    this.data         = data;
  }

  public String getSymbolName(){
    return symbolName;
  }

  public String getFunctionName(){
    return functionName;
  }

  public short getType(){
    return type;
  }

  public Polynomial getData(){
    return data;
  }

  public void setData(Polynomial data) {
    this.data = data;
  }
}
