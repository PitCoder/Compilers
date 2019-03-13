public class Numero {
  public float real;

  public Numero(float real) {
    this.real = real;
  }

  public float getReal() {return real; }
  
  public void imprimeNumero() {
    float real  = this.getReal();
    
      System.out.println("" + real);

  }
  
  /* Methods for compare in conditionals */
  public boolean equals(Numero n1, Numero n2) {
    Numero res = new Numero(n1.getReal()-n2.getReal());
    return res.getReal() == 0;
  }
  
  public boolean greaterThan(Numero n1, Numero n2) {
    return n1.getReal()-n2.getReal() > 0;
  }
  
  public boolean greaterOrEqualsThan(Numero n1, Numero n2) {
    return n1.getReal()-n2.getReal() >= 0;
  }
  
  public boolean lessThan(Numero n1, Numero n2) {
    return n1.getReal()-n2.getReal() < 0;
  }
  
  public boolean lessOrEqualsThan(Numero n1, Numero n2) {
    return n1.getReal()-n2.getReal() <= 0;
  }

}
