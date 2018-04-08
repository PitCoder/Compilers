import java.util.List;
import java.util.ArrayList;
import utils.Fraction;
import domain.Polynomial;

public class Multiply{
  public Multiply(){
  }

  public Polynomial multiplication(List<Polynomial> polynomials){
    Polynomial current = polynomial.get(0);
    for(int i=1; i<polynomials.size(); i++){
      current = multiplyTwo(current,polynomials.get(i));
    }
    current.reduce();
    return current;
  }

  public Polynomial multiplyTwo(Polynomial n, Polynomial t){
    Fraction fraction = new Fraction();
    Polynomial solution = new Polynomial();
    for(int i=0;i<n.termNumber();i++){
      for(int j=0;j<t.termNumber();j++){
        int mul[] = fraction.multiplication(n.termAt(i).getCoefficient()[0],n.termAt(i).getCoefficient()[1],t.termAt(j).getCoefficient()[0],t.termAt(j).getCoefficient()[1]);
        solution.addTerm(mul[0],mul[1],n.termAt(i).getExponent() + t.termAt(j).getExponent());
      }
    }
    solution.reduce();
    return solution;
  }
}
