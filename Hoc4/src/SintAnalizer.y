/***** DELECLARATIONS *****/
/*** USED JAVA LIBRARIES ***/
%{
    import java.io.*;
%}

/*** YACC DELECLARATIONS ***/
%token BLTIN
%token VAR
%token TERM
%token NUM

//Operators precedence
%right '='
%left '-' '+'
%left '/' '%' '*'
%left '^'
%nonassoc '(' ')'
%nonassoc '[' ']'

%%
/***** ACTIONS *****/
/*** GRAMMAR AS FOLLOWS ***/
list :	/* Nothing -> Epsilon */
			| list '\n'
      | list asgn '\n' {
        machine.code("printAsignment");
        machine.code("STOP");
        machine.execute(flag);
        flag = true;
      }
			| list expr '\n'  {
        machine.code("printPolynomial");
        machine.code("STOP");
        machine.execute(flag);
        flag = true;
      }
			;

asgn : VAR '=' expr   {
  //System.out.println("Doing an assignation");
  machine.code("varPush");
  machine.code((InputText) $1.obj);
  machine.code("asgnVar");
}
     ;

expr : poly
     | VAR {
       //System.out.println("Variable Detected");
       machine.code("varPush");
       machine.code((InputText) $1.obj);
       machine.code("retvarVal");
     }
     | asgn {
       //System.out.println("Assignment Detected");
       /* No extra code generated */
     }
     | BLTIN '(' NUM ')' {
       //System.out.print("Built-in Detected: ");
       machine.code("number");
       machine.code((InputText) $3.obj);
       machine.code("numParam");
       machine.code(new InputText("1"));
       machine.code("builtinPush");
       machine.code((InputText) $1.obj);
       machine.code("builtin");
     }
     | BLTIN '(' NUM ',' NUM ')' {
       //System.out.print("Built-in Detected: ");
       machine.code("number");
       machine.code((InputText) $3.obj);
       machine.code("number");
       machine.code((InputText) $5.obj);
       machine.code("numParam");
       machine.code(new InputText("2"));
       machine.code("builtinPush");
       machine.code((InputText) $1.obj);
       machine.code("builtin");
     }
     | BLTIN '(' expr ')' {
       //System.out.print("Built-in Detected: ");
       machine.code("numParam");
       machine.code(new InputText("1"));
       machine.code("builtinPush");
       machine.code((InputText) $1.obj);
       machine.code("builtin");
     }
     | expr '+' expr {
       //System.out.println("Addition Detected");
       machine.code("addition");
     }
     | expr '-' expr {
       //System.out.println("Substraction Detected");
       machine.code("substraction");
     }
     | expr '*' expr {
       //System.out.println("Multiplication Detected");
       machine.code("multiplication");
     }
     | expr '/' expr {
       //System.out.println("Division Detected");
       machine.code("division");
     }
     | expr '%' expr {
       //System.out.println("Division Detected");
       machine.code("module");
     }
     | expr '^' NUM {
       //System.out.println("Power Detected");
       machine.code("number");
       machine.code((InputText) $3.obj);
       machine.code("power");
     }
     | '(' expr ')' {
       //System.out.println("Nesting Detected");
       /* No extra code generated */
     }
     ;

poly : '[' terms ']' {
  //System.out.println("Polynomial with terms detected");
  machine.code("poly");
}
     | '['']' {
  //System.out.println("Empty polynomial detected");
  machine.code("poly");
}
    ;

terms : term {
  //System.out.println("Creation List of terms Detected");
  machine.code("createLT");
}
      | term terms {
  //System.out.println("List of terms Detected");
  machine.code("addtoLT");
}
      ;

term : TERM {
  //System.out.println("Term Detected");
  Term t = (Term) $1.obj;
  machine.code("term");
  machine.code(t);
}
     ;
%%

/***** SUPPORT CODE *****/
/*** MANDATORY USER SUPPLIED METHODS ***/

  /* Stores a reference to the lexer bject */
  static Machine machine = new Machine();
  private Yylex lexer;
  private boolean flag = false;

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
    machine.initMachine();
    /* We initialize the parser */
    yyparser.yyparse();
  }
  catch(Exception e){
    e.printStackTrace();
  }
}
