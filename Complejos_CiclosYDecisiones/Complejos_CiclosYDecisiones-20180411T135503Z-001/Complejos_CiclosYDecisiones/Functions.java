public class Functions {
  public Complejo sinus(Complejo c) {
    float a = c.getReal(); float b = c.getImg();

    float real  = (float) (Math.sin(a) * (float) ((Math.exp(b) + Math.exp(-b)) / 2));
    float img   = (float) (Math.cos(a) * (float) ((Math.exp(b) - Math.exp(-b)) / 2));

    Complejo res = new Complejo(real, img);
    return res;
  }

  public Complejo cosine(Complejo c) {
    float a = c.getReal(); float b = c.getImg();

    float real  = (float) (Math.cos(a) * (float) ((Math.exp(b) + Math.exp(-b)) / 2));
    float img   = (float) (Math.sin(a) * (float) ((Math.exp(b) - Math.exp(-b)) / 2));

    Complejo res = new Complejo(real, -img);
    return res;
    
  }

  public Complejo exp(Complejo c) {
    float a = c.getReal(); float b = c.getImg();

    float real = (float) (Math.exp(a) * Math.cos(b));
    float img  = (float) (Math.exp(a) * Math.sin(b));
    
    Complejo res = new Complejo(real, img);
    return res;
  }  

  private Complejo multiplicaComplejos(Complejo c1, Complejo c2) {
    Complejo res = new Complejo((c1.getReal() * c2.getReal()) - (c1.getImg() * c2.getImg()),
                                  (c1.getImg() * c2.getReal()) + (c1.getReal() * c2.getImg()) );
    return res; 
  }
}
