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

  void install(String sysName, String funName, Numero data, short type) {
    Symbol s = new Symbol(sysName, funName, type, data);
    symbols.add(s);
  
  }
  void install2(String name, Coordenada data, ArrayList<String> DireccionesVar) {	
  		String aux="";
  		for(int i=0; i<DireccionesVar.size(); i++){
			aux=aux+DireccionesVar.get(i);
  		}
	
  		Symbol s = new Symbol(name, (short) 1, data, aux);
  		symbols.add(s);
	}
  
  void install(Symbol s) {
    symbols.add(s);
  }

  void update(Symbol s, Numero data) {
    Symbol newSymbol = s;  
    symbols.remove(s);
    newSymbol.setData(data);
    symbols.add(newSymbol);    
  }
    
  void installExistente(String name, Coordenada data, ArrayList<String> DireccionesVar){
	  int indice=-1;
	  String aux="";
	  for(int i=0; i<DireccionesVar.size(); i++){
			aux=aux+DireccionesVar.get(i);
	  }
	  for (Symbol s: symbols) {
		  indice++;
		  if (s.getSymbolName().compareTo(name) == 0) { // The symbol is already in the table !
			  break;
		  }
	  }
	  symbols.remove(indice);
	  Symbol s = new Symbol(name, (short) 1, data, aux);
	  symbols.add(s);	
	}
	
   
  void print() {
    for (Symbol k: symbols) {
      System.out.println("Name: " + k.getSymbolName() + "\tCrds: "+k.getCoordenadas() + "");
    }
  }
    
}