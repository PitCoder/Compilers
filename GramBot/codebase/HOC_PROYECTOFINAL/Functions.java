public class Functions {
  public Numero sinus(Numero c) {
    float a = c.getReal();

    float real  = (float) (Math.sin(a));

    Numero res = new Numero(real);
    return res;
  }

  public Numero cosine(Numero c) {
    float a = c.getReal();

    float real  = (float) (Math.cos(a));

    Numero res = new Numero(real);
    return res;
    
  }

  public Numero exp(Numero c) {
    float a = c.getReal(); 

    float real = (float) (Math.exp(a));
    
    Numero res = new Numero(real);
    return res;
  }  

}
