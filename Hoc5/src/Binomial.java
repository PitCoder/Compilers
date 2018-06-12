public class Binomial{
  public Binomial(){
  }

//It will be in terms of x thus we multiply the terms
  public Polynomial newtonBinomial(char mode, int y, int n){
    Polynomial polynomial = new Polynomial();

    if(mode == 'p'){
      for(int k=0; k<=n; k++){
        int numerator = binomialCoeff(n,k) * (int) Math.pow((double)y,(double)k);
        int denominator = 1;
        int exponent = n-k;

        polynomial.addTerm(new Term(numerator, denominator, exponent));
      }
    }
    else{
      for(int k=0; k<=n; k++){
        int numerator = binomialCoeff(n,k) * (int) Math.pow((double)y,(double)k);
        numerator *= (int) Math.pow((double)-1,(double)k);
        int denominator = 1;
        int exponent = n-k;

        polynomial.addTerm(new Term(numerator, denominator, exponent));
      }
    }
    return polynomial;
  }

  public Polynomial newtonBinomial(char mode, int n){
    Polynomial polynomial = new Polynomial();

    if(mode == 'p'){
      for(int k=0; k<=n; k++){
        int numerator = binomialCoeff(n,k);
        int denominator = 1;
        int exponent = n-k;

        polynomial.addTerm(new Term(numerator, denominator, exponent));
      }
    }
    else{
      for(int k=0; k<=n; k++){
        int numerator = binomialCoeff(n,k) * (int) Math.pow((double)-1,(double)k);
        int denominator = 1;
        int exponent = n-k;

        polynomial.addTerm(new Term(numerator, denominator, exponent));
      }
    }
    return polynomial;
  }

  // memoization plus Space Optimization
  public int binomialCoeff(int n, int k){
    int C[] = new int[k + 1];

    // nC0 is 1
    C[0] = 1;
    for (int i = 1; i <= n; i++){
    // Compute next row of pascal
    // triangle using the previous row
    for (int j = Math.min(i, k); j > 0; j--)
      C[j] = C[j] + C[j-1];
    }

    return C[k];
   }
}
