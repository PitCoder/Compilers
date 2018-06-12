public class Pow{
  public Pow(){

  }

  public Polynomial power(Polynomial a, int n){
    Mul mul = new Mul();
    Polynomial result = new Polynomial();
    result.addTerm(new Term(1,1,0));

    if(n == 0){
      return result;
    }
    else{
      for(int k=1; k<=n ;k++)
        result = mul.multiplyTwo(a, result);
    }
    
    return result;
  }
}
