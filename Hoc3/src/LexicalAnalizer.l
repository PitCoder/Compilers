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
operator   = ["+""-""*""/""=""("")""[""]""^"]            //Operators for calculator
comma      = ,                    //Comma character
ws         = [ \t]+               //Whitespace Regex
digits     = [0-9]                //Digit Regex
number     = (0|([1-9]{digits}*)) //Number Regex
signednum  = [+|-]*(0|([1-9]{digits}*))
nonzeronum = ([1-9]{digits}*)
term       = {ws}*[+|-]*{ws}*{number}{ws}*([/][+|-]*{nonzeronum})*x[+|-]*{ws}*{number}{ws}*
variable   = [a-zA-Z][a-zA-Z0-9_]* //Variable - Indentifier Regex
builtin    = geometric | binomial | nbinomial | integer | derivate // geom - abbrv for geometric and bi for binomial
%%

/***** LEXICAL RULES *****/
/*
  MacroUsage ::= '{' Indentifier '}'
  Action     ::= '{' JavaCode '}' | '|'
*/
/*** ACTION FOR BUILT-IN ***/
{builtin}   {
  yyparser.yylval = new ParserVal(new InputText(yytext()));
  System.out.println("Built-in detected: " + yytext());
  return Parser.BLTIN;
}

/*** ACTION FOR VARIABLE ***/
{variable}  {
  yyparser.yylval = new ParserVal(new InputText(yytext()));
  System.out.println("Variable detected: " + yytext());
  return Parser.VAR;
}

/*** ACTION FOR TERM ***/
{term}  {
  System.out.println("Term detected: " + yytext());
  Auxiliar aux = new Auxiliar(yytext());
  yyparser.yylval = new ParserVal(new Term(aux.getNumerator(), aux.getDenominator(), aux.getCoefficient()));
  return Parser.TERM;
}

/*** ACTION FOR OPERATOR | COMMA ***/
{operator} | {comma}  {
  System.out.println("Comma-Operator detected: " + yycharat(0));
  return (int) yycharat(0); //Since they are single chars defined in ascii values, we just return the integer value
}

/*** ACTION FOR NUMBER ***/
{signednum}  {
  System.out.println("Signed Number detected: " + yytext());
  yyparser.yylval = new ParserVal(new InputText(yytext()));
  return Parser.SNUM;
}

/*** ACTION FOR NEWLINE ***/
{newline}   {
  System.out.println("End of line detected: " + yytext());
  return (int) yycharat(0); //Since they are single chars defined in ascii values, we just return the integer value
}

/*** ACTION FOR WHITESPACE | ANY OTHER CHARACTER ***/
{ws}  {
  /* Do nothing */
}

. {
  /* Do nothing */
}

/*** ERROR FALLBACK ***/
[^]   {
  System.err.println("Error: unexpected character '"+yytext()+"'");
  return -1;
}
