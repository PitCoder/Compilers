import java.util.ArrayList;
public class Symbol {
  private String    symbolName;
  private String    functionName;
  private short     type;
  private Numero  data;
  private Coordenada data2;
  String Direcciones;
  int               defn;

  public Symbol(String symName, String funName, short type, Numero data) {
    this.symbolName   = symName;
    this.functionName = funName;
    this.type         = type;
    this.data         = data;
  }
	
  public Symbol(String symName, short type, Coordenada data,String Dir) {
	this.symbolName   = symName;
    this.type = type;
    this.data2 = data;
    this.Direcciones = Dir;
  }

  public String getSymbolName() {return symbolName;}
  public String getFunctionName() {return functionName;}
  public short getType() {return type;}
  public Numero getData() {return data;}
  public Coordenada getData2() 	{return data2;}
  public String printData2() {return("");}
  public String getCoordenadas(){return Direcciones;}

  public void setData(Numero data) {this.data = data;}
}

/*
public class Symbol {
  Coordenada data;
  String Direcciones;

  public Symbol(String symName, short type, Coordenada data,String Dir) {
	this.symbolName   = symName;
    this.name = name;
    this.type = type;
    this.data = data;
    this.Direcciones = Dir;
  }

  public String getName() 		{return name;}
  public short getType()  		{return type;}
  public Coordenada getData() 	{return data;}
  public String getCoordenadas(){return Direcciones;}
}*/