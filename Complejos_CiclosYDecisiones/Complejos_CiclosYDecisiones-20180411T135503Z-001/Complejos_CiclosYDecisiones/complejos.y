%{
  import java.io.*;  
%}

%token BLTIN
%token IF ELSE WHILE
%token EQ NEQ
%token DIG
%token VAR
%token CNUMBER

%right '='
%left '-' '+'
%left '*' '/'

  /* RULES SECTION */
%%
list:	/* nada */
			| list '\n'
      | list asgn '\n'  { /*maq.code("printVar"); */maq.code("STOP"); 
                          maq.execute(flag, 0); flag = true; 
                        }
      | list stmt '\n'  {maq.code("STOP"); maq.execute(flag, 0); flag = true;}
			| list exp '\n'   {  maq.code("printComplex"); maq.code("STOP"); 
                            maq.execute(flag, 0); flag = true;
                        }
			;
asgn: VAR '=' exp   { int numI = maq.code("varPush");
                      maq.code((Cadena) $1.obj);
                      maq.code("asgVar"); 
                      System.out.println("ASGNNI: " + numI); 
                      $$ = new ParserVal(new Integer(numI) - 6);     
                    }
      ;
stmt: exp {}
      |  while cond stmt end  { maq.getProg().setElementAt($3.obj, (int) $1.obj + 1);
                                maq.getProg().setElementAt($4.obj, (int) $1.obj + 2); 
                                }                                
      ;
cond: '(' exp ')' { maq.code("STOP");
                    System.out.println("exp @: " + $2.obj);
                    $$ = new ParserVal($2.obj);
                  }
      ;
while:	WHILE                { int numI = maq.code("whileCode"); 
                               maq.code("STOP"); maq.code("STOP");
                               $$ = new ParserVal(new Integer(numI));
                             }
      ;
end:     { maq.code("STOP");
           $$ = new ParserVal(new Integer(maq.getProg().size())); 
         }
    ;
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
     | asgn     
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
      /* Condicionales */
     | exp EQ exp                 { maq.code("eq");}
     | exp NEQ exp                { maq.code("neq");}
     | '(' exp ')'                { $$ = $2; }
    ;
%%

/** CÓDIGO DE SOPORTE **/
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
    
    System.out.print("Expression: ");
    
	  yyparser = new Parser(new InputStreamReader(System.in));    

    maq.initCode();

    yyparser.yyparse();

  }
