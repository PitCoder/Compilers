//********Importación de Librerias*******//
%{
  import java.io.*;
  import java.lang.reflect.*;
  import java.util.ArrayList;
%}

//********Sección de Reglas*******//
%token BLTIN
%token VARIABLE
%token TERM
%token DIGIT

%right '='
%left '+' '-'
%left '*' '/'
%nonassoc '(' ')'
%nonassoc '[' ']'

%%
list : /* Epsilon operator */
     | list '\n'
     | list asgn '\n'
     | list expr '\n'
     ;

asgn : VARIABLE '=' expr {
  System.out.println("Detecte una asginación");
}

expr  : polynomial{
  Polynomial p = (Polynomial)$1.obj;
  System.out.println("Detecte un polinomio: " + p.toString());
}
      | expr '+' expr {
Polynomial p1 = (Polynomial)$1.obj;
Polynomial p2 = (Polynomial)$3.obj;
System.out.println("Detecte una suma de polinomios: " + p1.toString() + "+" + p2.toString());
}
      | expr '-' expr {
  Polynomial p1 = (Polynomial)$1.obj;
  Polynomial p2 = (Polynomial)$3.obj;
  System.out.println("Detecte una resta de polinomios: " + p1.toString() + "-" + p2.toString());
}
      | expr '*' expr {
  Polynomial p1 = (Polynomial)$1.obj;
  Polynomial p2 = (Polynomial)$3.obj;
  System.out.println("Detecte un producto de polinomios: " + p1.toString() + "*" + p2.toString());
}
      | expr '/' expr {
  Polynomial p1 = (Polynomial)$1.obj;
  Polynomial p2 = (Polynomial)$3.obj;
  System.out.println("Detecte una division de polinomios: " + p1.toString() + "/" + p2.toString());
}
      | VARIABLE {
  System.out.println("Detecte una variable");
}
      | BLTIN '(' expr ',' DIGIT ')' {
  System.out.println("Detecte una función de 2 args");
}
      | BLTIN '(' expr ')' {
   System.out.println("Detecte una función de 1 arg");
}
      | '(' expr ')' {
  System.out.println("Expresion entre parentesis");
}
     ;

polynomial : '[' terms ']' {
  Polynomial p = new Polynomial((ArrayList<Term>)$2.obj);
  $$ = new ParserVal(p);
}
           | '['']' {
  Polynomial p = new Polynomial();
  $$ = new ParserVal(p);
}
           ;

terms : term  {
  ArrayList<Term> ts = new ArrayList<Term>();
  ts.add((Term)$1.obj);
  $$ = new ParserVal(ts);
}
      | term terms {
  ArrayList<Term> ts = (ArrayList<Term>)$2.obj;
  ts.add((Term)$1.obj);
  $$ = new ParserVal(ts);
}
      ;

term  : TERM {
  Term t = (Term)$1.obj;
  $$ = new ParserVal(t);
}
      ;
%%

//********Código de Soporte*******//
private Yylex lexer;

private int yylex(){
  int yylexReturn = -1;

  try{
    yylval = new ParserVal(0);
    yylexReturn = lexer.yylex();
  }
  catch(IOException error){
      System.err.println("IO error :" + error);
  }

  return yylexReturn;
}

//Función de soporte de error sintáctico
//*Debe implementarse según la especificacón de la documentación de byaccj
public void yyerror(String error){
  System.err.println("Error: " + error);
}

public Parser(Reader r){
  lexer = new Yylex(r, this);
}

//Declaración de nuestra tabla de símbolos
SymbolTable symbolTable = new SymbolTable();

//Función que inicializa la tabla de símbolos
//Aqui se declaran 2 arreglos estáticos que corresponden al nombre de llamada
//y nombre de la tabla de las funciones "binomial" y "geometrica"
void init(){
  String[] callNames = {"binomial","geometric"};
  String[] funcNames = {"binFunc","geoFunc"};
  for(int i=0; i<funcNames.length; i++){
    symbolTable.install(callNames[i], funcNames[i], (short) 2, null);
  }

}

//Función Main, aquí inicia nuestro parser
public static void main(String args[]) throws IOException{
  System.out.println("Calculadora de Polinomios");
  Parser yyparser;
  System.out.println("Expresion: ");
  yyparser = new Parser(new InputStreamReader(System.in));
  yyparser.init();
  yyparser.yyparse();
}
