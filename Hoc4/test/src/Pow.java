public class Pow{
  public Pow{

  }

  public Polynomial power(Polynomial a, int n){
    Polynomial result = new Polynomial(new Term(0,1,0));
    Mul mul = new Mul();

    for(int k=0; k<n ;k++)
      result = mul.multiplyTwo(a, result);

    return result;
  }
}
