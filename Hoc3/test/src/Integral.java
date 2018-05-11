public class Integral{
  private Polynomial polynomial;
  private Fraction fraction;
  private boolean indefinite;
  private int[] interval;

  public Integral(){
    this.fraction = new Fraction();
    this.interval = new int[2];
  }

  public int[] definite(int l, int u, Polynomial p){
    Polynomial integrated;
    this.polynomial = p;
    interval[0] = l;
    interval[1] = u;
    integrated = integrate();
    int b[] = integrated.valueAt(interval[1]);
    int a[] = fraction.multiplication(integrated.valueAt(interval[0])[0], integrated.valueAt(interval[0])[1], -1, 1);
    return fraction.sum(b[0], b[1], a[0], a[1]);
  }

  public Polynomial indefinite(Polynomial p){
    this.polynomial = p;
    return integrate();
  }

  private Polynomial integrate(){
    Polynomial result = new Polynomial();
    for(int i=0; i<polynomial.termNumber(); i++){
      int mul[] = new int[2];
      if(polynomial.termAt(i).getExponent() != 0 ){
        mul = fraction.multiplication(polynomial.termAt(i).getCoefficient()[0], polynomial.termAt(i).getCoefficient()[1], 1, polynomial.termAt(i).getExponent() + 1);
      }
      else{
        mul[0] = polynomial.termAt(i).getCoefficient()[0];
        mul[1] = polynomial.termAt(i).getCoefficient()[1];
      }
      result.addTerm(mul[0], mul[1], polynomial.termAt(i).getExponent()+1);
    }
    result.reduce();
    return result;
  }
}
