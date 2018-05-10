public class Auxiliar{
  private int numerator;
  private int denominator;
  private int coefficient;

  public Auxiliar (String input){
    String[] parts = input.split("\\/|x");
    /*
    for(int i=0; i<parts.length;i++){
      System.out.println(parts[i]);
    }
    */

    if(input.contains("/")){
      //System.out.println("The term include is defined by a fraction");
      //System.out.println(parts.length + " This shall be 3 :9");

      try{
        this.numerator   = Integer.parseInt(parts[0].replaceAll("([ \\t]+)*",""));
        this.denominator = Integer.parseInt(parts[1].replaceAll("([ \\t]+)*",""));
        this.coefficient = Integer.parseInt(parts[2].replaceAll("([ \\t]+)*",""));
      }
      catch(Exception e){
        e.printStackTrace();
      }
    }
    else{
      //System.out.println("The term include is defined by a integer");
      //System.out.println(parts.length + " This shall be 2 :9");

      try{
        this.numerator   = Integer.parseInt(parts[0].replaceAll(" ",""));
        this.denominator = 1;
        this.coefficient = Integer.parseInt(parts[1].replaceAll(" ",""));
      }
      catch(Exception e){
        e.printStackTrace();
      }
    }
  }

  public int getNumerator(){
    return this.numerator;
  }

  public int getDenominator(){
    return this.denominator;
  }

  public int getCoefficient(){
    return this.coefficient;
  }
}
