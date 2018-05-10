public class Functions {
  /***** CONSTRUCTOR *****/
  public Functions(){

  }

  /*** Geometric serie function ***/
  public Polynomial geom(int a, int n){
    Geometric geo = new Geometric();
    return geo.geometricSerie(a,n);
  }

  /*** Positive newton binomial expansion functions ***/
  /* Here we apply polymorfism */
  public Polynomial bi(int n){
    Binomial bi = new Binomial();
    return bi.newtonBinomial('p',n);
  }

  public Polynomial bi(int y, int n){
    Binomial bi = new Binomial();
    return bi.newtonBinomial('p', y, n);
  }

  /*** Negative newton binomial expansion functions ***/
  /* Here we apply polymorfism */
  public Polynomial nbi(int n){
    Binomial bi = new Binomial();
    return bi.newtonBinomial('n',n);
  }

  public Polynomial nbi(int y, int n){
    Binomial bi = new Binomial();
    return bi.newtonBinomial('n', y, n);
  }
}
