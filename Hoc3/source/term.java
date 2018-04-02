package domain;

public class Term implements Comparable<Term>{
  private int numerator;
  private int denominator;
  private int exponent;

  public Term(int numerator, int denominator, int exponent){
    if(denominator<0){
      this.denominator = denominator*-1;
      this.numerator = numerator*-1;
    }
    else{
      this.denominator = denominator;
      this.numerator = numerator;
    }
    this.exponent = exponent;
  }

  public int[] getCoefficient(){
    int coefficient[] = {this.denominator,this.numerator};
    return coefficient;
  }

  public int getExponent(){
    return this.exponent;
  }

  @Override
  public String toString(){
    String coeff;
    String exponent;
    if(this.numenator==0){
      return "0";
    }
    if(this.exponent==0){
      exponent="";
    }else if(this.exponent==1){
      exponent="x";
    }else{
      exponent="x^"+this.exponent;
    }
    if(this.denominator==1 && this.numenator==1 && this.exponent==0){
      coeff="1";
    }else if(this.denominator==1 && this.numenator==1){
      coeff="";
    }else if(((this.denominator==-1 && this.numenator==1)||(this.denominator==1 && this.numenator==-1)) && this.exponent>0){
      coeff="-";
    }
    else if(this.denominator==1){
      coeff = this.numenator + "";
    }else{
      coeff = this.numenator + "/" + this.denominator;
    }
    return coeff + "" + exponent;
  }

  @Override
  public int compareTo(Term o){
    return o.getExponent() - this.getExponent();
  }
}
