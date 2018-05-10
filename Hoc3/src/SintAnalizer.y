/***** DELECLARATIONS *****/
/*** USED JAVA LIBRARIES ***/
%{
    import java.io.*;
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.lang.reflect.*;
%}

/*** YACC DELECLARATIONS ***/
%token BLTIN
%token VAR
%token TERM
%token SNUM

//Operators precedence
%right '='
%left '-' '+'
%left '/' '*'
%left '^'
%nonassoc '(' ')'
%nonassoc '[' ']'

%%
/***** ACTIONS *****/
/*** GRAMMAR AS FOLLOWS ***/
list :	/* Nothing -> Epsilon */
			| list '\n'
      | list asgn '\n' {
        Polynomial result = (Polynomial) $2.obj;
        System.out.println("Result: " + result.toString() + "\n");
        System.out.print("Expression: ");
      }
			| list expr '\n'  {
        Polynomial result = (Polynomial) $2.obj;
        System.out.println("Result: " + result.toString() + "\n");
        System.out.print("Expression: ");
      }
			;

asgn : VAR '=' expr   {
  System.out.println("Doing an assignation");
  InputText t = (InputText) $1.obj;
  Symbol s = lookUpTable(t.getText());
  if(s==null){
    install(t.getText(), (Polynomial) $3.obj, (short) 1);
  }
  else{
    update(s, (Polynomial) $3.obj);
  }
  $$ = new ParserVal((Polynomial) $3.obj);
}
     ;

expr : poly
     | VAR {
       System.out.println("Variable Detected");
       InputText t = (InputText) $1.obj;
       Symbol s = lookUpTable(t.getText());
       if(s!=null){
         Polynomial data = (Polynomial) s.getData();
         $$ = new ParserVal(data);
       }
       else{
         yyerror("Variable not declared");
         System.exit(0);
       }
     }
     | asgn {
       System.out.println("Assignment Detected");
     }
     | BLTIN '(' SNUM ')' {
       System.out.print("Built-in Detected: ");
       Polynomial p = new Polynomial();
       InputText t = (InputText) $1.obj;
       Symbol s = lookUpTable(t.getText());
       String func_name = s.getName();
       int n = Integer.parseInt(((InputText) $3.obj).getText());

       if(func_name.compareTo("binomial") == 0){
         Binomial bi = new Binomial();
          if(n >= 0){
            System.out.println("Newton binomial (Single positive expansion)");
            p = bi.newtonBinomial('p',n);
          }
          else{
            yyerror("Not suitable built-in");
            System.exit(0);
          }
       }
       else if(func_name.compareTo("nbinomial") == 0){
         Binomial bi = new Binomial();
         if(n >= 0){
           System.out.println("Newton binomial (Single negative expansion)");
           p = bi.newtonBinomial('n',n);
         }
         else{
           yyerror("Not suitable built-in");
           System.exit(0);
         }
       }
       else{
         yyerror("Not suitable built-in");
         System.exit(0);
       }
       $$ = new ParserVal(p);
     }
     | BLTIN '(' SNUM ',' SNUM ')' {
       System.out.print("Built-in Detected: ");
       Polynomial p = new Polynomial();
       InputText t = (InputText) $1.obj;
       Symbol s = lookUpTable(t.getText());
       String func_name = s.getName();
       int y = Integer.parseInt(((InputText) $3.obj).getText());
       int n = Integer.parseInt(((InputText) $5.obj).getText());

       if(func_name.compareTo("binomial") == 0){
         Binomial bi = new Binomial();
          if(n >= 0){
            System.out.println("Newton binomial (Compound positive expansion)");
            p = bi.newtonBinomial('p',y,n);
          }
          else{
            yyerror("Variable not valid n value for Newton Binomial expansion");
            System.exit(0);
          }
       }
       else if(func_name.compareTo("nbinomial") == 0){
         Binomial bi = new Binomial();
         if(n >= 0){
           System.out.println("Newton binomial (Compound negative expansion)");
           p = bi.newtonBinomial('n',y,n);
         }
         else{
           yyerror("Variable not valid n value for Newton Binomial expansion");
           System.exit(0);
         }
       }
       else if(func_name.compareTo("geometric") == 0){
         System.out.println("Geometric Serie");
         Geometric geo = new Geometric();
          if(n >= 0){
             p = geo.geometricSerie(y,Math.abs(n));
          }
          else{
            yyerror("Variable not valid n value for Geometric Serie");
            System.exit(0);
          }
       }
       else{
        yyerror("Not suitable built-in");
        System.exit(0);
       }
       $$ = new ParserVal(p);
     }
     | expr '+' expr {
       System.out.println("Addition Detected");
       Polynomial a = (Polynomial) $1.obj;
       Polynomial b = (Polynomial) $3.obj;
       Sum s = new Sum();
       Polynomial sumab = s.sum(a,b);
       $$ = new ParserVal(sumab);
     }
     | expr '-' expr {
       System.out.println("Substraction Detected");
       Polynomial a = (Polynomial) $1.obj;
       Polynomial b = (Polynomial) $3.obj;
       Sub sb = new Sub();
       Polynomial subab = sb.sub(a,b);
       $$ = new ParserVal(subab);
     }
     | expr '*' expr {
       System.out.println("Multiplication Detected");
       Polynomial a = (Polynomial) $1.obj;
       Polynomial b = (Polynomial) $3.obj;
       Mul mul = new Mul();
       Polynomial mulab = mul.multiplyTwo(a,b);
       $$ = new ParserVal(mulab);
     }
     | expr '/' expr {
       System.out.println("Division Detected");
       Polynomial a = (Polynomial) $1.obj;
       Polynomial b = (Polynomial) $3.obj;
       Div div = new Div();
       Polynomial divab = div.division(b,a);
       $$ = new ParserVal(divab);
     }
     | expr '^' SNUM {
       System.out.println("Power Detected");
       Polynomial a = (Polynomial) $1.obj;
       Polynomial powan = new Polynomial();
       int n = Integer.parseInt(((InputText) $3.obj).getText());
       if(n>=0){
         Pow pow = new Pow();
         powan = pow.power(a,n);
       }
       else{
         yyerror("Variable not valid n value for Pow");
         System.exit(0);
       }
       $$ = new ParserVal(powan);
     }
     | '(' expr ')' {
       System.out.println("Nesting Detected");
       Polynomial p = (Polynomial) $2.obj;
       $$ = new ParserVal(p);
     }
     ;

poly : '[' terms ']' {
  System.out.println("Polynomial with terms detected");
  ArrayList<Term> t = (ArrayList<Term>) $2.obj;
  Polynomial p = new Polynomial(t);
  $$ = new ParserVal(p);

}
     | '['']' {
  System.out.println("Empty polynomial detected");
  Polynomial p = new Polynomial();
  $$ = new ParserVal(p);
}
    ;

terms : term {
  Term t = (Term) $1.obj;
  ArrayList<Term> ts = new ArrayList<>();
  ts.add(t);
  $$ = new ParserVal(ts);
}
      | term terms {
  System.out.println("List of terms Detected");
  Term t = (Term) $1.obj;
  ArrayList<Term> ts = (ArrayList<Term>) $2.obj;
  ts.add(t);
  $$ = new ParserVal(ts);
}
      ;

term : TERM {
  System.out.println("Term Detected");
  Term t = (Term) $1.obj;
  $$ = new ParserVal(t);
}
     ;
%%

/***** SUPPORT CODE *****/
/*** MANDATORY USER SUPPLIED METHODS ***/

  /* Stores a reference to the lexer bject */
  private Yylex lexer;

  /* Interface to the lexer */
  private int yylex () {
    int yyl_return = -1;
    try {
      yyl_return = lexer.yylex();
    }
    catch (IOException e) {
      System.err.println("IO error :"+e);
    }
    return yyl_return;
  }

  /* Error reporting */
 public void yyerror (String error) {
   System.err.println ("Error: " + error);
 }

 /* The lexer is created in the constructor */
 public Parser(Reader r) {
  lexer = new Yylex(r, this);
}

/*** SYMBOL TABLE SUPPLIED METHODS ***/
HashMap<String,Symbol> symbolTable = new HashMap<>();

  Symbol lookUpTable(String symbolName) {
    return symbolTable.get(symbolName);
}

  void install(String name, Polynomial data, short type) {
    Symbol s = new Symbol(name, type, data);
    symbolTable.put(name,s);
}

  void update(Symbol s, Polynomial data) {
    symbolTable.remove(s.getName());
    s.setData(data);
    symbolTable.put(s.getName(),s);
}

  /* We initiialize the symbol table */
  void init() {
    /* We initialize the functions */
    String[] funcNames = {"geometric", "binomial", "nbinomial", "integer", "derivate"};
    for (int i = 0; i < funcNames.length; i++) {
      install(funcNames[i], null, (short) 2);
    }
}


public static void main(String args[]) throws IOException {
  System.out.println("Calculadora de Polinomios");
  /* We create an instance of the class Parser */
  Parser yyparser;

  /* Here we request an input for an expression */
  System.out.print("Expression: ");
  /* We call the constructor of the class Parser to which we pass as an argument the input string */
  try{
    yyparser = new Parser(new InputStreamReader(System.in));
    /* We initialize the lockup table */
    yyparser.init();
    /* We initialize the parser */
    yyparser.yyparse();
  }
  catch(Exception e){
    e.printStackTrace();
  }
}
