%%

%byaccj

%{
  private Parser yyparser;

  public Yylex(java.io.Reader r, Parser yyparser) {
    this(r);
    this.yyparser = yyparser;
  }
%}

nl          = \n | \r | \r\n
op          = [-+*()/=\^]
ws          = [ \t]+
digits      = [0-9]
number      = (0|[1-9]+{digits}*)\.?{digits}*
im          = [i]
complexnum  = {ws}*[-]*{ws}*{number}{ws}*[+|-]{ws}*{number}{ws}*{im}{ws}*
var         = [a-zA-Z][a-zA-Z]*
built       = sin | exp | cos | pow
comma       = ,


%%
{built} { yyparser.yylval = new ParserVal(new Cadena(yytext())); return Parser.BLTIN; }
{var} {yyparser.yylval = new ParserVal(new Cadena(yytext())); return Parser.VAR;}
{complexnum} { Auxiliar a = new Auxiliar(); 
               int [] cmp = a.obtenerComponentes(yytext());
               yyparser.yylval = new ParserVal( new Complejo(cmp[0], cmp[1]) );
               return Parser.CNUMBER; 
             }
{op} | {comma} {return (int) yycharat(0); }
{digits} { yyparser.yylval = new ParserVal(new Cadena(yytext())); return Parser.DIG;}
{nl}   { return (int) yycharat(0); }
{ws}   {}
.      {}
/* error fallback */
[^]    { System.err.println("Error: unexpected character '"+yytext()+"'"); return -1; }
