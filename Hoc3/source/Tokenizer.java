public class Tokenizer{
  private String expression;

  public Tokenizer(String string){
    this.expression = string;
  }

  public int[] getComponents(){
    String tokenstr = this.expression;
    int components[] = new int[3];
    int numerator;
    int denominator;
    int exponent;
    int sign;
    int lowerindex;
    int upperindex;

    tokenstr = tokenstr.replaceAll("[\\s]*",""); //Quitamos todos los espacios en blanco

    if(tokenstr.contains("x")){
      if(tokenstr.contains("/")){
        lowerindex = 0;
        upperindex = tokenstr.indexOf("/");
        numerator = Integer.parseInt(tokenstr.substring(lowerindex, upperindex));

        lowerindex = upperindex++;
        upperindex = tokenstr.indexOf("x");
        denominator = Integer.parseInt(tokenstr.substring(lowerindex, upperindex));

        lowerindex = upperindex++;
        exponent = Integer.parseInt(tokenstr.substring(lowerindex));
      }
      else{
        lowerindex = 0;
        upperindex = tokenstr.indexOf("x");
        numerator = Integer.parseInt(tokenstr.substring(lowerindex, upperindex));

        denominator = 1;

        lowerindex = upperindex++;
        exponent = Integer.parseInt(tokenstr.substring(lowerindex));
      }
    }
    else{
      if(tokenstr.contains("/")){ //contiene una fraccion
        lowerindex = 0;
        upperindex = tokenstr.indexOf("/");
        numerator = Integer.parseInt(tokenstr.substring(lowerindex, upperindex));

        lowerindex = upperindex++;
        denominator = Integer.parseInt(tokenstr.substring(lowerindex));

        exponent = 0;
      }
      else{
        lowerindex = 0;
        numerator = Integer.parseInt(tokenstr.substring(lowerindex));

        denominator = 1;

        exponent = 0;
      }
    }
    components[0] = numerator;
    components[1] = denominator;
    components[2] = exponent;
    return components;
  }
}
