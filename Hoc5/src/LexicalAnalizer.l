/***** USER SUPPORT CODE *****/

/***** OPTIONS AND DECLARATIONS *****/
%%
%public //Enable the class Yylex to be public
/*** CONFIGURING BYACC/J COMPATIBILITY ***/
%byaccj
%{
  /* Store a reference to the parser object */
  private Parser yyparser;

  /* Constructor taking an additional parser object */
  public Yylex(java.io.Reader r, Parser yyparser){
    this(r);
    this.yyparser = yyparser;
  }
%}

newline    = \r | \n | \r\n       //Using Java conventions
operator   = ["+""-""*""/""%""=""^"";"]            //Operators for calculator
key        = ["("")""[""]""{""}"]
comma      = ,                    //Comma character
ws         = [ \t]+               //Whitespace Regex
digits     = [0-9]                //Digit Regex
number     = (0|([1-9]{digits}*)) //Number Regex
//signednum  = [+|-]*(0|([1-9]{digits}*))
nonzeronum = ([1-9]{digits}*)
term       = {ws}*[+|-]*{ws}*{number}{ws}*([/][+|-]*{nonzeronum})*x[+|-]*{ws}*{number}{ws}*
variable   = [a-zA-Z][a-zA-Z0-9_]* //Variable - Indentifier Regex
eq         = ==
neq        = #=
gt         = >
ge         = >=
lt         = <
le         = <=
if         = if
else       = else
elif       = elif
while      = while
print      = print
builtin    = geometric | binomial | nbinomial | integral | derivative | coefbinomial // geom - abbrv for geometric and bi for binomial
%%

/***** LEXICAL RULES *****/
/*
  MacroUsage ::= '{' Indentifier '}'
  Action     ::= '{' JavaCode '}' | '|'
*/
/*** ACTION FOR IF CONDITION ***/
{if} {
  System.out.println("Lexer: If condition detected: " + yytext());
  return Parser.IF;
}

/*** ACTION FOR ELSE CONDITION ***/
{else} {
  System.out.println("Lexer: Else condition detected: " + yytext());
  return Parser.ELSE;
}

/*** ACTION FOR ELIF CONDITION ***/
{elif} {
  System.out.println("Lexer: Elif condition detected: " + yytext());
  return Parser.ELIF;
}

/*** ACTION FOR WHILE CYCLE ***/
{while} {
  System.out.println("Lexer: While condition detected: " + yytext());
  return Parser.WHILE;
}

/*** ACTION FOR RELATIONAL OPERATORS ***/

{eq}  {
  System.out.println("Lexer: Equal conditional detected: " + yytext());
  return Parser.EQ;
}

{neq} {
  System.out.println("Lexer: Distinct conditional detected: " + yytext());
  return Parser.NEQ;
}

{gt}  {
  System.out.println("Lexer: Greater than conditional detected: " + yytext());
  return Parser.GT;
}

{ge}  {
  System.out.println("Lexer: Greater or Equal conditional detected: " + yytext());
  return Parser.GE;
}

{lt}  {
  System.out.println("Lexer: Lesser conditional detected: " + yytext());
  return Parser.LT;
}

{le}  {
  System.out.println("Lexer: Lesser or Equal conditional detected: " + yytext());
  return Parser.LE;
}

/*** ACTION FOR PRINTING ***/
{print} {
  System.out.println("Lexer: Print command detected: " + yytext());
  return Parser.PRINT;
}

/*** ACTION FOR BUILT-IN ***/
{builtin}   {
  yyparser.yylval = new ParserVal(new InputText(yytext()));
  System.out.println("Lexer: Built-in detected: " + yytext());
  return Parser.BLTIN;
}

/*** ACTION FOR VARIABLE ***/
{variable}  {
  yyparser.yylval = new ParserVal(new InputText(yytext()));
  System.out.println("Lexer: Variable detected: " + yytext());
  return Parser.VAR;
}

/*** ACTION FOR TERM ***/
{term}  {
  System.out.println("Lexer: Term detected: " + yytext());
  Auxiliar aux = new Auxiliar(yytext());
  yyparser.yylval = new ParserVal(new Term(aux.getNumerator(), aux.getDenominator(), aux.getCoefficient()));
  return Parser.TERM;
}

/*** ACTION FOR OPERATOR | COMMA | KEY ***/
{operator} | {comma} | {key} {
  System.out.println("Lexer: Comma-Operator-Key detected: " + yycharat(0));
  return (int) yycharat(0); //Since they are single chars defined in ascii values, we just return the integer value
}

/*** ACTION FOR NUMBER ***/
{number}  {
  System.out.println("Lexer: Number detected: " + yytext());
  yyparser.yylval = new ParserVal(new InputText(yytext()));
  return Parser.NUM;
}

/*** ACTION FOR WHITESPACE | ANY OTHER CHARACTER ***/
{ws}  {
  /* Do nothing */
}

. {
  /* Do nothing */
}

/*** ACTION FOR NEWLINE ***/
{newline}   {
  System.out.println("Lexer: End of line detected: " + yytext());
  return (int) yycharat(0); //Since they are single chars defined in ascii values, we just return the integer value
}

/*** ERROR FALLBACK ***/
[^]   {
  System.err.println("Lexer: Error: unexpected character '"+yytext()+"'");
  return -1;
}
