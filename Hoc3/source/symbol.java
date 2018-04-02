class Symbol{
	public String name;
	public short type;
	public double val;

	private Simbolo next;

	Symbol(String s, short t, double d){
		this.name  = s;
		this.type  = t;
		this.value = d;
	}

	public Simbolo getNext(){
		return next;
	}

  public void setNext(Symbol s){
		next = s;
	}

	public String getName(){
		return name;
	}
}
