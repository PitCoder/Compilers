import java.util.ArrayList;

public class SymbolTable {
  private ArrayList<Symbol> symbols = new ArrayList<>();
  
  Symbol lookUpTable(String symbolName) {
    for (Symbol s: symbols) {
      if (s.getSymbolName().compareTo(symbolName) == 0) { // The symbol is already in the table !
        return s;
      } 
    }

    return null;
  }

  void install(String sysName, String funName, Complejo data, short type) {
    Symbol s = new Symbol(sysName, funName, type, data);
    symbols.add(s);
  }

  void update(Symbol s, Complejo data) {
    Symbol newSymbol = s;  
    symbols.remove(s);
    newSymbol.setData(data);
    symbols.add(newSymbol);    
  }
  
  void print() {
    for (Symbol s: symbols) {
      System.out.println("Name: " + s.getSymbolName());
    }
  }
}