import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Polynomial{
  private int scale = 1;
  private Fraction fraction = new Fraction();
  private List<Term> polynomial;

  public Polynomial(){
    this.polynomial = new ArrayList<Term>();
  }

  public Polynomial(ArrayList<Term> terms){
    this.polynomial = terms;
  }

  public void setScale(int a){
    this.scale = a;
  }

  public int getScale(){
    return scale;
  }

  @Override
  public String toString(){
    if(polynomial.isEmpty()){
      return "0";
    }
    String polString = "";
    for(int i=0;i<polynomial.size();i++){
      if(i!=0 && polynomial.get(i).getCoefficient()[0]>0){
        polString += "+";
      }
      polString+=polynomial.get(i).toString();
    }
    return polString;
  }

/**
  Polymorfism in method addTerm
  Add a term to the polynomial
  @param term
  @return void
*/

  public void addTerm(Term term){
    polynomial.add(term);
  }

  public void addTerm(int numerator, int denominator, int exponent){
    polynomial.add(new Term(numerator,denominator,exponent));
    Collections.sort(polynomial); //Calling to sort method allows us to sort the polynomial in ascending order.
  }

/**
  Search for a term given an index
  @param index
  @return term in the index given
*/
  public Term termAt(int index){
    return polynomial.get(index);
  }

/**
  @param int that it is the degree of the term
  @return term
*/
  public Term getTermOfDegree(int degree){
    for(Term term : polynomial){
      if(term.getExponent()==degree){
        return term;
      }
    }
    return new Term(0,1,0);
  }

/**
  Made a copy of the polynomial
  @param void
  @return a polynomial that is a clone of the polynomial
*/
  public Polynomial clone(){
    Polynomial clone = new Polynomial();
    for(Term term : polynomial){
      clone.addTerm(term);
    }
    return clone;
  }

  public void negate(){
    for(Term term : polynomial){
      term.getCoefficient()[0] *= -1;
    }
  }

/**
  Simplifies the polynomial
  (useful when we do operations that modifiy the original number of terms)
  @param void
  @return void
*/
  public void reduce(){
    List<Term> result = new ArrayList<Term>();
    HashMap<Integer,Term> exponents = new HashMap<Integer,Term>();
    for(int i=0;i<polynomial.size();i++){
      //We checked if the map doesn't have already a key for this exponent and that this exponent is positive (Due HashMaps don't allow negative numbers)
      if(!exponents.containsKey(polynomial.get(i).getExponent()) && polynomial.get(i).getExponent()>=0){
        //We asociate the Term with the key (exponent) and we map them
        exponents.put(polynomial.get(i).getExponent(), new Term(polynomial.get(i).getCoefficient()[0],polynomial.get(i).getCoefficient()[1],polynomial.get(i).getExponent()));
      }
      //If we already have a key for the given exponent or the exponent has a negative value
      else{
        //We invoke the method sum in fraction class which will return to us an array with the sum of all values
        int sum[] = fraction.sum(exponents.get(polynomial.get(i).getExponent()).getCoefficient()[0], exponents.get(polynomial.get(i).getExponent()).getCoefficient()[1], polynomial.get(i).getCoefficient()[0], polynomial.get(i).getCoefficient()[1]);
        //We update the key with the new values
        exponents.put(polynomial.get(i).getExponent(),new Term(sum[0],sum[1],polynomial.get(i).getExponent()));
      }
    }
    //We retirve the keySet and we iterate it, we will only add to the result polynomial those term which coefficient is distinct than zero
    for(int n : exponents.keySet()){
      if(exponents.get(n).getCoefficient()[0]!=0){
        result.add(exponents.get(n));
      }
    }
    //We sort the terms in ascending order
    Collections.sort(result);
    //We update the polynomial
    this.polynomial = result;
  }

/**
  Divide all the terms of the polynomial by a given coefficient
  @param int[] with the coefficient numerator and denominator
  @return void
*/
  public void divideWithCoeff(int coeff[]){
    List<Term> result = new ArrayList<Term>();
    for(Term term : polynomial){
      int div[] = fraction.division(term.getCoefficient()[0], term.getCoefficient()[1], coeff[0], coeff[1]);
      result.add(new Term(div[0], div[1], term.getExponent()));
    }
    this.polynomial = result;
  }


/**
  Returns the highest degree if the polynomial
  @param void
  @return int that it is the highest degree of the polynomial
*/
  public int highestDegree(){
    return polynomial.get(0).getExponent();
  }

/**
  Returns all the terms of the polynomial
  @param void
  @return a list of terms idk: the polynomial itself but no its class
*/
  public List<Term> getTerms(){
    return polynomial;
  }

/**
  Divide all the terms of the polynomial with the term given the exponent
  @param int exponent
  @return polynomial divided
*/
  public Polynomial divideWithDegree(int exponent){
    Polynomial result = new Polynomial();
    for(Term term : polynomial){
      result.addTerm(term.getCoefficient()[0],term.getCoefficient()[1],term.getExponent()-exponent);
    }
    result.reduce();
    return result;
  }

/**
  Returns the number of terms that the polynomial have
  @param void
  @return int that it is the number of terms
*/
  public int termNumber(){
    return polynomial.size();
  }

/**
  Returns the lowest degree exponent
  @param void
  @return int that it is the lowest degree exponent
*/
  public int lowestDegree(){
    return polynomial.get(polynomial.size()-1).getExponent();
  }


/**
  Polymorfism in method valueAtevuelve el grado mayor del polinomio
  Returns the value of the polynomial at x (This methos is used to evaluate an expression)
  @param double x is the value of the variable
  @return double y that is the value at x, but with the scale applied
*/
  public double valueAt(double x){
    x = x/scale;
    double y = 0;
    for(Term term : polynomial){
      y += Math.pow(x,term.getExponent()) * fraction.turnToDecimal(term.getCoefficient()[0],term.getCoefficient()[1]);
    }
    return y*scale;
  }

  /**
    Returns the value of the polynomial at x (This method is used to graph)
    @param int x that is the value of the variable
    @return int with the values of the sum of each term in the polynomial
  */
  public int[] valueAt(int x){
    int sum[] = {0,1};
    for(Term term : polynomial){
      int mul[] = fraction.multiplication((int)Math.pow(x, term.getExponent()), 1, term.getCoefficient()[0], term.getCoefficient()[1]);
      sum = fraction.sum(sum[0], sum[1], mul[0], mul[1]);
    }
    return sum;
  }
}
