%{
  import java.io.*;
%}

/* Used tokens */
%token BLTIN
%token IF ELSE WHILE PRINT
%token EQ NEQ GT GE LT LE
%token DIG
%token VAR
%token CNUMBER

/* Operators */
%right '='
%left '-' '+'
%left '*' '/'

/* RULES SECTION */
%%
list:	/* nothing */
			| list '\n'
      | list asgn '\n'  { maq.code("STOP"); return 1; }
      | list stmt '\n'  { maq.code("STOP"); return 1; }
			| list exp '\n'   { maq.code("printComplex"); maq.code("STOP");
                          return 1;
                        }
			;
asgn: VAR '=' exp   { int numI = maq.code("varPush");
                      maq.code((Cadena) $1.obj);
                      maq.code("asgVar");
                      $$ = new ParserVal($3.obj);
                    }
      ;
stmt: exp { maq.code("pop"); }
      | PRINT exp { maq.code("printComplex");
                    $$ = new ParserVal($2.obj);
                  }
      | while cond stmt end { maq.getProg().setElementAt($3.obj, (int) $1.obj + 1);
                              maq.getProg().setElementAt($4.obj, (int) $1.obj + 2);
                            }
      | if cond stmt end    { maq.getProg().setElementAt($3.obj, (int) $1.obj + 1);
                              maq.getProg().setElementAt($4.obj, (int) $1.obj + 3);
                            }
      | if cond stmt end ELSE stmt end {  maq.getProg().setElementAt($3.obj, (int) $1.obj + 1);
                                          maq.getProg().setElementAt($6.obj, (int) $1.obj + 2);
                                          maq.getProg().setElementAt($7.obj, (int) $1.obj + 3);
	                                     }
      | '{' stmtlist '}' {$$  =  $2; }
      ;
cond: '(' exp ')' { maq.code("STOP");
                    $$ = new ParserVal($2.obj);
                  }
      ;
while:	WHILE                { int numI = maq.code("whileCode");
                               maq.code("STOP"); maq.code("STOP");
                               $$ = new ParserVal(new Integer(numI));
                             }
      ;
if: IF  { int numI = maq.code("ifCode");
          maq.code("STOP"); maq.code("STOP"); maq.code("STOP");
          $$ = new ParserVal(new Integer(numI));
        }
end:     { maq.code("STOP");
           $$ = new ParserVal(new Integer(maq.getProg().size()));
         }
    ;
stmtlist: 	{ $$ = new ParserVal(new Integer(maq.getProg().size())); }
	       | stmtlist '\n'
	       | stmtlist stmt
exp:  CNUMBER                     { Complejo c = (Complejo) $1.obj;
                                    int numI = maq.code("cNumber");
                                    maq.code(c);
                                    $$ = new ParserVal(new Integer(numI));
                                  }
     | VAR                        { int numI = maq.code("varPush");
                                    maq.code((Cadena) $1.obj);
                                    maq.code("getVarValue");
                                    $$ = new ParserVal(new Integer(numI));
                                  }
     | asgn   {}
     | BLTIN '(' exp ')'          { maq.code("bltinPush");
                                    maq.code((Cadena) $1.obj);
                                    maq.code("bltin");
                                  }
     | exp '+' exp                { maq.code("add"); }
     | exp '-' exp                { maq.code("sub"); }
     | exp '*' exp                { maq.code("mult"); }
     | exp '/' exp                { maq.code("div"); }
     | exp '^' DIG                { maq.code("powN");
                                    maq.code((Cadena) $3.obj);
                                    maq.code("pow");
                                  }
      /* Conditionals operations */
     | exp EQ exp                 { maq.code("eq");}
     | exp NEQ exp                { maq.code("neq");}
     | exp GT exp                 { maq.code("gt");}
     | exp GE exp                 { maq.code("ge");}
     | exp LT exp                 { maq.code("lt");}
     | exp LE exp                 { maq.code("le");}
     | '(' exp ')'                { $$ = $2; }
    ;
%%

/** SUPPORT CODE **/
  static Maquina maq = new Maquina();
  private Yylex lexer;

  private int yylex () {
    int yyl_return = -1;
    try {
      yylval = new ParserVal(0);
      yyl_return = lexer.yylex();
    }
    catch (IOException e) {
      System.err.println("IO error :"+e);
    }

    return yyl_return;
  }


  public void yyerror (String error) {
    System.err.println ("Error: " + error);
  }


  public Parser(Reader r) {
    lexer = new Yylex(r, this);
  }

  boolean flag = false;

  public static void main(String args[]) throws IOException {
    System.out.println(".:: Complex Number Calculator ::.");

    Parser yyparser;
    maq.initCode();

    while (true) {
      System.out.print("Expression: ");
      maq.newProgram();

      yyparser = new Parser(new InputStreamReader(System.in));
      yyparser.yyparse();

      maq.execute(0);
    }

  }
