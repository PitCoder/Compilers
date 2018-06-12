public class Functions {
  /***** CONSTRUCTOR *****/
  public Functions(){

  }

  public Integer cbi(Integer a, Integer b){
    Binomial bi = new Binomial();
    return bi.binomialCoeff(a,b);
  }

  /*** Geometric serie function ***/
  public Polynomial geom(Integer a, Integer n){
    Geometric geo = new Geometric();
    return geo.geometricSerie(a,n);
  }

  /*** Positive newton binomial expansion functions ***/
  /* Here we apply polymorfism */
  public Polynomial bi(Integer n){
    Binomial bi = new Binomial();
    return bi.newtonBinomial('p',n);
  }

  public Polynomial bi(Integer y, Integer n){
    Binomial bi = new Binomial();
    return bi.newtonBinomial('p', y, n);
  }

  /*** Negative newton binomial expansion functions ***/
  /* Here we apply polymorfism */
  public Polynomial nbi(Integer n){
    Binomial bi = new Binomial();
    return bi.newtonBinomial('n',n);
  }

  public Polynomial nbi(Integer y, Integer n){
    Binomial bi = new Binomial();
    return bi.newtonBinomial('n', y, n);
  }

  /*** Indefinitive Integral ***/
  public Polynomial intgr(Polynomial p){
    Integral integral = new Integral();
    return integral.indefinite(p);
  }

  /*** Indefinitive Derivative ***/
  public Polynomial derv(Polynomial p){
    Derivative derivative = new Derivative();
    return derivative.indefinite(p);
  }
}
