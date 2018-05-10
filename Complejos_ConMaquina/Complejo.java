public class Complejo {
  private float real;
  private float img;

  public Complejo(float real, float img) {
    this.real = real;
    this.img  = img;
  }

  public float getReal() {return real; }
  
  public float getImg() {return img; }
  
  public Complejo sumaComplejos(Complejo c1, Complejo c2) {
    Complejo res = new Complejo(c1.getReal() + c2.getReal(),
                        c1.getImg() + c2.getImg());
    return res;
  }

  public Complejo restaComplejos(Complejo c1, Complejo c2) {
    Complejo res = new Complejo(c1.getReal() - c2.getReal(),
                                c1.getImg() - c2.getImg());
    return res;
  }

  public Complejo multiplicaComplejos(Complejo c1, Complejo c2) {
    Complejo res = new Complejo((c1.getReal() * c2.getReal()) - (c1.getImg() * c2.getImg()),
                                  (c1.getImg() * c2.getReal()) + (c1.getReal() * c2.getImg()) );
    return res; 
  }

  public Complejo divideComplejos(Complejo c1, Complejo c2) {    
    float d = (float) ( (c2.getReal() * c2.getReal()) + (c2.getImg() * c2.getImg()) );
    Complejo res = new Complejo((float) (c1.getReal() * c2.getReal() + c1.getImg() * c2.getImg()) / d,
                              (float) (c1.getImg() * c2.getReal() - c1.getReal() * c2.getImg()) / d );
    return res;
  }

  public Complejo pow(Complejo c, Cadena powN) {
    int number = Integer.parseInt(powN.getCadena());
    Complejo res = c;
    for (int i = 1; i < number; i++) {
      res = multiplicaComplejos(res, c);    
    }
    return res;
  }
  
  public void imprimeComplejo() {
    float real  = this.getReal();
    float img   = this.getImg();
    
    if (img >= 0)
      System.out.println("" + real + " + " + img + " i");
    else
      System.out.println("" + real + " " + img + " i");
  }

}
