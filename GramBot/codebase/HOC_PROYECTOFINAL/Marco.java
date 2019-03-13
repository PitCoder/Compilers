public class Marco {
	Symbol s;
	int    retpc;
	int    argn;//n ele pila
	int    nargs;
	
  public Marco(Symbol s, int retpc, int argn, int nargs){
		this.s      = s;
		this.retpc  = retpc;
		this.argn   = argn;
		this.nargs  = nargs;
	}
}