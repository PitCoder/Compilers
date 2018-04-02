public class Table{
	Symbol symbolList;

	public Table(){
		symbolList = null;
	}

//With this function we create a linked list between the symbols
	Symbol install(String s, short t, double d){
			Symbol symb = new Symbol(s,t,d);
			symb.setNext(symbolList);
			this.symbolList = symb;
			return symb;
	}

//With this function we iterate the linked list until we finf the symbol
	Symbol lookup(String s){
		for(Symbol sp = symbolList; sp!=null; sp=sp.getNext())
			if((sp.getName()).equals(s))
				return sp;
		return null;
	}
}
