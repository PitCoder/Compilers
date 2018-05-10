public class Geometric{
  public Geometric(){
  }

  public Polynomial geometricSerie(int a, int n){
    Polynomial polynomial = new Polynomial();
    int numerator = a;
    int denominator = 1;

    for(int k=0; k<n; k++){
      int exponent = k;
      polynomial.addTerm(new Term(numerator, denominator, exponent));
    }

    return polynomial;
  }
}
