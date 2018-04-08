import utils.Fraction;
import domain.Polynomial;
import domain.Term;

public class Divide{
  private Polynomial divisor;
  private Polynomial dividend;
  private Fraction fraction;

  public Divide(){
  }

  public polynomial[] division(Polynomial divisor, Polynomial dividend){
    Multiply mul = new Multiply();
    Sum sum = new Sum();

    Polynomial result[] = new Polynomial[3];
    Polynomial one = new Polynomial();
    one.addTerm(1,1,0);
    Polynomial zero = new Polynomial();
    zero.addTerm(0,1,0);
    Polynomial rest = new Polynomial();
    Polynomial minus = new Polynomial();
    minus.addTerm(-1,1,0);
    Polynomial temp;

    Term a = divisor.termAt(0);
    Term b = dividend.termAt(0);
    Term c;

    if(a.getExponent() == 0){
      dividend.divideWithCoeff(a.getCoefficient());
      result[0] = dividend
      result[1] = one;
      result[2] = zero;
    }
    else{
      if(a.getExponent() > b.getExponent()){
        result[0] = dividend;
        result[1] = divisor;
        result[2] = zero;
      }
      else{
        while(a.getExponent() <= b.getExponent()){
          int div[] = fraction.division(b.getCoefficient()[0], b.getCoefficient[1], a.getCoefficient()[0], a.getCoefficient()[1]);
          c = new Term(div[0], div[1], b.getExponent() - a.getExponent());
          rest.addTerm(c);
          temp = new Polynomial();
          temp.addTermc(c);
          temp = mul.multiplyTwo(temp, divisor);
          temp = mul.multiplyTwo(temp, minus);
          dividend = sum.sum(dividend,temp);
          b = dividend.termAt(0);
        }
        if(dividend.toString().equals("0")){
          result[0] = zero;
          result[1] = one;
          result[2] = zero;
        }
        else{
          dividend.reduce();
          result[0] = result;
          result[1] = one;
          result[2] = dividend;
        }
      }
    }
    return result;
  }
}
