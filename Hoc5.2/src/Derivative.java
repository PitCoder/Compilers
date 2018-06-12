public class Derivative{
  private Polynomial polynomial;
  private Fraction fraction;
  private int[] interval;

  public Derivative(){
    this.fraction = new Fraction();
    this.interval = new int[2];
  }

  public int[] definite(int l, int u, Polynomial p){
    Polynomial derivated;
    this.polynomial = p;
    interval[0] = l;
    interval[1] = u;
    derivated = derivative();
    int b[] = derivated.valueAt(interval[1]);
    int a[] = fraction.multiplication(derivated.valueAt(interval[0])[0], derivated.valueAt(interval[0])[1], -1, 1);
    return fraction.sum(b[0], b[1], a[0], b[1]);
  }

  public Polynomial indefinite(Polynomial p){
    this.polynomial = p;
    return derivative();
  }

  private Polynomial derivative(){
    Polynomial result = new Polynomial();
    for(int i=0; i<polynomial.termNumber(); i++){
      Term current = polynomial.termAt(i);
      if(polynomial.termAt(i).getExponent() == 0){
        result.addTerm(0,1,0);
      }
      else{
        int coeff[] = fraction.multiplication(current.getCoefficient()[0], current.getCoefficient()[1], current.getExponent(), 1);
        result.addTerm(coeff[0], coeff[1], current.getExponent()-1);
      }
    }
    result.reduce();
    return result;
  }
}
