public class Div{
  public Div(){
  }

  public Polynomial[] division(Polynomial divisor, Polynomial dividend){
    divisor.reduce();
    dividend.reduce();
    System.out.println("Divisor: " + divisor.toString());
    System.out.println("Dividend: " + dividend.toString());

    Polynomial[] res = new Polynomial[2];
    Polynomial result = new Polynomial();
    Polynomial temp;

    Fraction fraction = new Fraction();
    Mul mul = new Mul();
    Sum sum = new Sum();

    Polynomial one = new Polynomial();
    one.addTerm(1,1,0);
    Polynomial zero = new Polynomial();
    zero.addTerm(0,1,0);
    Polynomial minus = new Polynomial();
    minus.addTerm(-1,1,0);

    Term a = divisor.termAt(0);
    Term b = dividend.termAt(0);
    Term c;

    if(a.getExponent() == 0){
      System.out.println("I detected that exp(a) = 0");
      dividend.divideWithCoeff(a.getCoefficient());
      res[0] = dividend;
      res[1] = divisor;
      return res;
    }
    else{
      if(a.getExponent() > b.getExponent()){
        System.out.println("I detected that exp(a) > exp(b)");
        res[0] = zero;
        res[1] = dividend;
        return res;
      }
      else{
        while(a.getExponent() <= b.getExponent()){
          int div[] = fraction.division(b.getCoefficient()[0], b.getCoefficient()[1], a.getCoefficient()[0], a.getCoefficient()[1]);
          c = new Term(div[0], div[1], b.getExponent() - a.getExponent());
          result.addTerm(c);
          temp = new Polynomial();
          temp.addTerm(c);
          temp = mul.multiplyTwo(temp, divisor);
          temp = mul.multiplyTwo(temp, minus);
          dividend = sum.sum(dividend,temp);
          b = dividend.termAt(0);
        }
        if(dividend.toString().equals("0")){
          res[0] = result;
          res[1] = zero;
          return res;
        }
        else{
          System.out.println("I detected a reduction");
          dividend.reduce();
          res[0] = result;
          res[1] = dividend;
          return res;
        }
      }
    }
  }
}
