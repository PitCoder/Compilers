import java.util.*;

public class SymbolTable {
  private HashMap<String, Symbol> symbolTable = new HashMap<>();

  Symbol lookUpTable(String symbolName) {
    return symbolTable.get(symbolName);
  }

  void install(String symbolname, String funcname, Polynomial data, short type) {
    Symbol s = new Symbol(symbolname, funcname, type, data);
    symbolTable.put(symbolname,s);
  }

  void install(String symbolname, String funcname, Integer data, short type) {
    Symbol s = new Symbol(symbolname, funcname, type, data);
    symbolTable.put(symbolname,s);
  }

  void update(Symbol s, Polynomial data) {
    symbolTable.remove(s.getName());
    s.setData(data);
    symbolTable.put(s.getName(),s);
  }

  void update(Symbol s, Integer data) {
    symbolTable.remove(s.getName());
    s.setData(data);
    symbolTable.put(s.getName(),s);
  }

  void print() {
    Set<String> symbols = symbolTable.keySet();
    Iterator<String> iterator = symbols.iterator();
    while(iterator.hasNext()){
      System.out.println(iterator.next());
    }
  }
}
