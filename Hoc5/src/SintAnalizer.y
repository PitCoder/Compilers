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
%token IF ELIF ELSE
%token WHILE
%token EQ NEQ GT LT GE LE
%token PRINT

//Operators precedence
%right '='
%left ','
%left IF ELIF ELSE
%left EQ NEQ GT LT GE LE
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
        System.out.println("List with Assignment");
        machine.code("printAsignment");
        machine.code("STOP");
        return 1;
        //machine.execute(flag);
      }
			| list expr '\n'  {
        System.out.println("List with Expression");
        machine.code("printExpression");
        machine.code("STOP");
        return 1;
        //machine.execute(flag);
      }
      | list stmt '\n'  {
        System.out.println("List with Statement");
        //machine.code("printExpression");
        machine.code("STOP");
        return 1;
        //machine.execute(flag);
        //flag = true;
      }
			;

asgn : VAR '=' expr   {
  System.out.println("Doing an assignation");
  int numline = machine.code("varPush");
  machine.code((InputText) $1.obj);
  machine.code("asgnVar");
  $$ = new ParserVal($3.obj);
}
     ;

stmt : expr ';' { System.out.println("Expr Statement"); }
     | PRINT expr ';' {
       System.out.println("Sintactic: Print Expr Statement");
       machine.code("printExpression");
}
     | while cond stmt end {
  System.out.println("While Statement");
  machine.getProgram().setElementAt($3.obj, (int) $1.obj + 1);
  machine.getProgram().setElementAt($4.obj, (int) $1.obj + 2);
}
     | if cond stmt end {
       System.out.println("Sintactic: If Statement");
       machine.getProgram().setElementAt($3.obj, (int) $1.obj + 1);
       machine.getProgram().setElementAt($4.obj, (int) $1.obj + 3);
}
     | if cond stmt end else stmt end {
       System.out.println("Sintactic: If-Else Statement");
       machine.getProgram().setElementAt($3.obj, (int) $1.obj + 1);
       machine.getProgram().setElementAt($6.obj, (int) $1.obj + 2);
       machine.getProgram().setElementAt($7.obj, (int) $1.obj + 3);
}
/*
     | if cond stmt end eliflist else stmt end {
       System.out.println("Sintactic: If-Elif-Else Statement");
       machine.getProgram().setElementAt($3.obj, (int) $1.obj + 1);
       machine.getProgram().setElementAt($7.obj, (int) $1.obj + 2);
       machine.getProgram().setElementAt($8.obj, (int) $1.obj + 3);
}
*/
     | '{' stmtlist '}' {
  $$ = $2;
}
     ;

stmtlist : {
  System.out.println("Sintactic: Statement List");
  $$ = new ParserVal(new Integer(machine.getProgram().size()));
}
     	   | stmtlist stmt
         ;
/*
eliflist :  elif cond stmt end {
  System.out.println("Elif List");
  machine.getProgram().setElementAt($3.obj, (int) $1.obj + 1);
  machine.getProgram().setElementAt($7.obj, (int) $1.obj + 2);
  machine.getProgram().setElementAt($8.obj, (int) $1.obj + 3);
}
         |  elif cond stmt end eliflist
         ;
*/
cond : '(' expr ')' {
  System.out.println("Sintactic: condition detected");
  machine.code("STOP"); //Con este stop se termina de evaluar la condicion
  $$ = new ParserVal($2.obj);
}
     ;

while : WHILE {
  System.out.println("Sintactic: While statement detected");
  int numline = machine.code("whileCode");
  machine.code("STOP");
  machine.code("STOP");
  $$ = new ParserVal(new Integer(numline));
}

if : IF {
  System.out.println("If statement detected");
  int numline = machine.code("ifCode");
  machine.code("STOP");
  machine.code("STOP");
  machine.code("STOP");
  $$ = new ParserVal(new Integer(numline));
}
/*
elif : ELIF {
  System.out.println("Sintactic: Elif statement detected");
  //int numline = machine.code("elifCode");
  //machine.code("STOP");ted

  //machine.code("STOP");
  //machine.code("STOP");
  //$$ = new ParserVal(new Integer(numline));
}
*/

else : ELSE {
  System.out.println("Sintactic: Else statement detected");
}

end : {
  System.out.println("Sintactic: End Detected");
  machine.code("STOP");
  //Este entero nos inidica la linea de programa donde finaliza el cuerpo de la condicion
  $$ = new ParserVal(new Integer(machine.getProgram().size()));
}


expr : poly
     | VAR {
       System.out.println("Sintactic: Variable Detected");
       int numline = machine.code("varPush");
       machine.code((InputText) $1.obj);
       machine.code("retvarVal");
       $$ = new ParserVal(new Integer(numline));
     }
     | asgn {
       System.out.println("Sintactic: Assignment Detected");
       /* No extra code generated */
     }
     | BLTIN '(' expr ',' expr ')' {
       System.out.print("Built-in with 2 parameters Detected");
       machine.code("numParam");
       machine.code(new InputText("2"));
       machine.code("builtinPush");
       machine.code((InputText) $1.obj);
       machine.code("builtin");
     }
     | BLTIN '(' expr ')' {
       System.out.print("Built-in with 1 parameter Detected");
       machine.code("numParam");
       machine.code(new InputText("1"));
       machine.code("builtinPush");
       machine.code((InputText) $1.obj);
       machine.code("builtin");
     }

     /** Arithmetic Functions **/
     | expr '+' expr {
       System.out.println("Sintactic: Addition Detected");
       machine.code("addition");
     }
     | expr '-' expr {
       System.out.println("Sintactic: Substraction Detected");
       machine.code("substraction");
     }
     | expr '*' expr {
       System.out.println("Sintactic: Multiplication Detected");
       machine.code("multiplication");
     }
     | expr '/' expr {
       System.out.println("Sintactic: Division Detected");
       machine.code("division");
     }
     | expr '%' expr {
       System.out.println("Sintactic: Division Detected");
       machine.code("module");
     }
     | expr '^' expr {
       System.out.println("Sintactic: Power Detected");
       machine.code("power");
     }

     /** Conditional operations **/
     | expr EQ expr {
       System.out.println("Sintactic: Equal Operator Detected");
       machine.code("equal");
     }
     | expr NEQ expr {
       System.out.println("Sintactic: Not equal Operator Detected");
       machine.code("distinct");
     }
     | expr GT expr {
       System.out.println("Sintactic: Greater than Operator Detected");
       machine.code("greater");
     }
     | expr LT expr {
       System.out.println("Sintactic: Lesser than Operator Detected");
       machine.code("lesser");
     }
     | expr GE expr {
       System.out.println("Sintactic: Greater or Equal Operator Detected");
       machine.code("greaterEq");
     }
     | expr LE expr {
       System.out.println("Sintactic: Lesser or Equal Operator Detected");
       machine.code("lesserEq");
     }

     /** Other type of values **/
     | NUM {
       System.out.println("Sintactic: Number detected");
       int numline = machine.code("number");
       machine.code((InputText) $1.obj);
       $$ = new ParserVal(new Integer(numline));
       /* No extra code generated */
     }
     | '(' expr ')' {
       System.out.println("Nesting Detected");
       $$ = $2;
     }
     ;

poly : '[' terms ']' {
  System.out.println("Sintactic:Polynomial with terms detected");
  int numline = machine.code("poly");
  $$ = new ParserVal(new Integer(numline));
}
     | '['']' {
  System.out.println("Sintactic: Empty polynomial detected");
  machine.code("poly");
}
    ;

terms : term {
  System.out.println("Sintactic: Creation List of terms Detected");
  machine.code("createLT");
}
      | term terms {
  System.out.println("Sintactic: List of terms Detected");
  machine.code("addtoLT");
}
      ;

term : TERM {
  System.out.println("Sintactic: Term Detected");
  Term t = (Term) $1.obj;
  machine.code("term");
  machine.code(t);
}
     ;
%%

/***** SUPPORT CODE *****/
/*** MANDATORY USER SUPPLIED METHODS ***/

  /* Stores a reference to the lexer bject */
  //static Machine machine = new Machine();
  static public Machine machine = new Machine();
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
  machine.initMachine();

  for(;;){
    /* Here we request an input for an expression */
    /* We call the constructor of the class Parser to which we pass as an argument the input string */
    try{
      System.out.print("Expression: ");
      yyparser = new Parser(new InputStreamReader(System.in));
      machine.newProgram();
      /* We initialize the parser */
      yyparser.yyparse();
      /* We start executing the program */
      machine.execute(0);
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }
}
