import java.util.ArrayList;

public class SymbolTable {
  private ArrayList<Symbol> symbols = new ArrayList<>();

  public SymbolTable(){
    this.symbols = new ArrayList<Symbol>();
  }

  Symbol lookUpTable(String symbolName) {
    for (Symbol s: symbols) {
      if (s.getSymbolName().compareTo(symbolName) == 0) { // The symbol is already in the table !
        return s;
      }
    }
    return null;
  }

  void install(String symbName, String funcName, short type, Polynomial data) {
    Symbol s = new Symbol(symbName, funcName, type, data);
    this.symbols.add(s);
  }

  void update(Symbol s, Polynomial data) {
    Symbol newSymbol = s;
    this.symbols.remove(s);
    newSymbol.setData(data);
    this.symbols.add(newSymbol);
  }
}
