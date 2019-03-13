%%

%byaccj

%{
  private Parser yyparser;

  public Yylex(java.io.Reader r, Parser yyparser) {
    this(r);
    this.yyparser = yyparser;
  }
%}

nl          = \n | \r | \r\n | ; | \{ | \} | \,
ws          = [ \t]+
op          = [-+*()/=\^]
digits      = [0-9]
number      = (0|[1-9]+{digits}*)\.?{digits}*

varcrd      = crd[a-zA-Z][a-zA-Z]*
var         = [a-zA-Z][a-zA-Z]*

built       = sin | exp | cos | pow

while       = while
for         = for
if          = if
else        = else
  
print       = print
eq          = ==
neq			= #=
gt          = >
ge          = >=
lt          = <
le          = <=
func        = func
proc        = proc
ret         = return
arg         = \$[0-9]


NE  = "[ne]"
SE  = "[se]"
NO  = "[no]"
SO  = "[so]"

%%
{while}   {return Parser.WHILE;}
{for} {return Parser.FOR;}
{if}      {return Parser.IF;}
{else}    {return Parser.ELSE;}

{print}   {return Parser.PRINT;}
{eq}      {return Parser.EQ;}
{neq}     {return Parser.NEQ;}
{gt}      {return Parser.GT;}
{ge}      {return Parser.GE;}
{lt}      {return Parser.LT;}
{le}      {return Parser.LE;}
/* Funciones */
{proc} {return Parser.PROC;}
{func} {return Parser.FUNC;}
{ret} {return Parser.RETURN;}
{arg}  {String arg = yytext();
        arg = arg.substring(1);
        int na = Integer.parseInt(arg);
        yyparser.yylval = new ParserVal(na);;
        return Parser.ARG; }
/**/




c	  {return ('c');}
n	  {return ('n');}
s	  {return ('s');}
e	  {return ('e');}
o	  {return ('o');}
u 	{return ('u');}
d 	{return ('d');}


{built}   {yyparser.yylval = new ParserVal(new Cadena(yytext())); return Parser.BLTIN; }
{varcrd}     {yyparser.yylval = new ParserVal(new Cadena(yytext())); return Parser.VARCRD;}
{var}     {yyparser.yylval = new ParserVal(new Cadena(yytext())); return Parser.VAR;}

{op}      {return (int) yycharat(0); }
{number}  {yyparser.yylval = new ParserVal(new Numero(Float.parseFloat(yytext()))); return Parser.NUMBER;}
{nl}      {return (int) yycharat(0); }

{NE}         {return ('%'); }
{SE}         {return ('&'); }
{NO}         {return ('#'); }
{SO}         {return ('"'); }




{ws}   {}
.      {}
/* error fallback */
[^]    { System.err.println("Error: unexpected character '"+yytext()+"'"); return -1; }
