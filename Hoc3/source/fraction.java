package utils;

public class Fraction{
/**
  Looks for the greatest common divisor given 2 numbers
  Euclides algorithm is implement here complexity O(logn)
  @param int number a int number b
  @return the greatest common divisor
*/
  private int greatestCommonDivisor(){
    if(b > a){
      int temp = a;
      a = b;
      b = temp;
    }
    while(b!=0){
      int m = a%b;
      a = b;
      b = m;
    }
    return a;
  }

/**
  Turns to decimal the fraction of two integer numbers
  @param int a, int b
  @return double a/b that it is the decimal representation of this fraction
*/
  public double turnToDecimal(int a, int b){
    return (double)a/b;
  }

/**
    Sum two fractions
    @param int a, int b representing the 1st fraction int c, int d representing the 2nd fraction
    @return int[] with the resulting fraction of this sum
*/
  public int[] sum(int a, int b, int c, int d){
    int sum[] =  new int[2];
    sum[0] = ((a*d)+(c*b))/greatestCommonDivisor((a*d+c*b),b*d);
    sum[1] = (b*d)/greatestCommonDivisor((a*d+c*b),b*d);
    if(sum[1]<0){
      sum[1] = sum[1]*-1;
      sum[0] = sum[0]*-1;
    }
    return sum;
  }

/**
  Multiply two fractions
  @param int a, int b representing the 1st fraction int c, int d representing the 2nd fraction
  @return int[] with the resulting fraction of this multiplication
*/
  public int[] multiplication(int a, int b, int c, int d){
    int mul[] = new int[2];
    mul[0]=(a*c)/greatestCommonDivisor(a*c,b*d);
    mul[1]=(b*d)/greatestCommonDivisor(a*c,b*d);
    if(mul[1]<0){
      mul[1]=mul[1]*-1;
      mul[0]=mul[0]*-1;
    }
    return mul;
  }

/**
  Divide two fractions
  @param int a, int b representing the 1st fraction int c, int d representing the 2nd fraction
  @return int[] with the resulting fraction of this division
*/
  public int[] division(int a, int b, int c, int d){
    int div[] = new int[2];
    if(multiplication(a,b,d,c)[0]<0){
      div[1]=multiplication(a,b,d,c)[1]*-1;
      div[0]=multiplication(a,b,d,c)[0]*-1;
      return div;
    }else{
      return multiplication(a,b,d,c);
    }
  }
/**
  Raises the fraction to the desired power
  @param int a numerator, int b denominator of the fraction, int exp exponent to which our fraction will be raise
  @return int[] with the fraction raised to specified power
*/
  public int[] pow(int a, int b, int exp){
    int pow[] = new int[2];
    pow[0] = (int)Math.pow(a,exp)/greatestCommonDivisor((int)Math.pow(a,exp),(int)Math.pow(b,exp));
    pow[1] = (int)Math.pow(a, exp)/greatestCommonDivisor((int)Math.pow(a,exp),(int)Math.pow(b,exp));
    return pow;
  }
}
