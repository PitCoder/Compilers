public class Symbol {
  private String name;
  private String funcName;
  private short type;
  private Polynomial pdata;
  private Integer idata;

  public Symbol(String name, String funcName, short type, Polynomial data) {
    this.name = name;
    this.funcName = funcName;
    this.type = type;
    this.pdata = data;
    this.idata = null;
  }

  public Symbol(String name, String funcName, short type, Integer data) {
    this.name = name;
    this.funcName = funcName;
    this.type = type;
    this.idata = data;
    this.pdata = null;
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

  public Polynomial getPData() {
    return this.pdata;
  }

  public Integer getIData() {
    return this.idata;
  }

  public void setData(Polynomial data) {
    this.pdata = data;
    this.idata = null;
  }

  public void setData(Integer data) {
    this.idata = data;
    this.pdata = null;
  }
}
