%{
  import java.io.*;
  import java.util.ArrayList;
  import java.lang.reflect.*;
%}

%token VAR
%token CNUMBER
%token BLTIN
%token DIG

%right '='
%left '-' '+'
%left '*' '/'
      
%%
list:	/* nada */
			| list '\n'
      | list asgn '\n' {}
			| list exp '\n' {Complejo res = (Complejo) $2.obj; System.out.print("Resultado: ");         res.imprimeComplejo();}
			;
asgn: VAR '=' exp   { Cadena c = (Cadena) $1.obj; 
                      Symbol s = symbolTable.lookUpTable(c.getCadena());                      
                      if (s == null) 
                        symbolTable.install(c.getCadena(), null, (Complejo) $3.obj, (short) 1);
                      else
                        symbolTable.update(s, (Complejo) $3.obj);                     
                    }
      ;
exp:  CNUMBER                     {Complejo c = (Complejo) $1.obj;}
     | VAR                        { Cadena c      = (Cadena) $1.obj;
                                    Symbol s      = symbolTable.lookUpTable(c.getCadena());
                                    if (s != null) {
                                      Complejo data = (Complejo) s.getData();
                                      $$ = new ParserVal(data);
                                    } else {
                                      yyerror("Variable no declarada");
                                      System.exit(0);
                                    }
                                  }
     | asgn     
     | BLTIN '(' exp ')'          { Cadena c        = (Cadena) $1.obj; 
                                    Symbol s        = symbolTable.lookUpTable(c.getCadena());
                                    String fName    = s.getFunctionName();                                    
                                    Complejo res    = new Complejo(0, 0);
                                    Functions f     = new Functions(); 
                                    Class cl        = f.getClass();
                                    Class[] cArg    = new Class[1];
                                    cArg[0]         = res.getClass();    
                                    Object param[]  = new Object[1];
                                    
                                    /* Se obtiene el metodo */
                                    try {
                                      Method method   = cl.getMethod(fName, cArg);
                                      System.out.println("Method: " + method);
                                      param[0]        = (Complejo) $3.obj;
                                      res             = (Complejo) method.invoke(f, param);
                                    } catch (NoSuchMethodException ex) {
                                    } catch (IllegalAccessException ex) {   
                                    } catch (InvocationTargetException ex) {
                                    }
                                    
                                    /* Se asigna el resultado a $$ */
                                    $$ = new ParserVal(res);
                                  }
     | exp '+' exp                { Complejo c1   = (Complejo) $1.obj; Complejo c2 = (Complejo) $3.obj;
                                    Complejo res  = c1.sumaComplejos(c1, c2);
                                    $$            = new ParserVal(res);
                                  }  
     | exp '-' exp                { Complejo c1   = (Complejo) $1.obj; Complejo c2 = (Complejo) $3.obj;
                                    Complejo res  = c1.restaComplejos(c1, c2);
                                    $$            = new ParserVal(res);
                                  }
     | exp '*' exp                { Complejo c1   = (Complejo) $1.obj; Complejo c2 = (Complejo) $3.obj;
                                    Complejo res  = c1.multiplicaComplejos(c1, c2);
                                    $$            = new ParserVal(res);                                     
                                  }
     | exp '/' exp                { Complejo c1   = (Complejo) $1.obj; Complejo c2 = (Complejo) $3.obj;
                                    Complejo res  = c1.divideComplejos(c1, c2);
                                    $$            = new ParserVal(res);
                                  }
     | exp '^' DIG                { Complejo c    = (Complejo) $1.obj; 
                                    Cadena powN   = (Cadena) $3.obj;
                                    Complejo res  = c.pow(c, powN);
                                    $$            = new ParserVal(res);
                                  }
     | '(' exp ')'                { Complejo c = (Complejo) $2.obj; $$ = new ParserVal(c); }
    ;
%%

/** CÓDIGO DE SOPORTE **/
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
  
  /* Tabla de simbolos */
  SymbolTable symbolTable = new SymbolTable();
  
/* Método para la inicializacion de las funciones disponibles */
void init() { 
  /* Se inicializan las funciones */
  String[] callNames = {"exp", "sin", "cos"};
  String[] funcNames = {"exp", "sinus", "cosine"};
  for (int i = 0; i < funcNames.length; i++) {
    symbolTable.install(callNames[i], funcNames[i], null, (short) 2);  
  }
}

  public static void main(String args[]) throws IOException {    
    System.out.println("Calculadora Números Complejos");

    Parser yyparser;
    
    System.out.print("Expression: ");
    
	  yyparser = new Parser(new InputStreamReader(System.in));    

    yyparser.init();

    yyparser.yyparse();
    
  }
