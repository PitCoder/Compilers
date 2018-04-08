%%
%unicode
%byaccj

%{
  private Parser yyparser;

  public Yylex(java.io.Reader r, Parser yyparser){
    this(r);
    this.yyparser = yyparser;
  }

  public int getLine(){
    return yyline;
  }
%}

newline        = \r|\n|\r\n
operator       = [-+*()/=\^]
comma          = ,
whitespace     = [\t]+
digit          = [0-9]
number         = ([1-9]+{digit}*)
term           = {whitespace}*[+|-]*{whitespace}*(({number}[/]{number})|{number}){whitespace}*x[+|-]*{whitespace}*{number}{whitespace}* | {whitespace}*[+|-]*{whitespace}*(({number}[/]{number})|{number}){whitespace}*
variable       = [a-zA-Z][a-zA-Z]*
built          = (geometric) | (binomial)
%%

{built}      { yyparser.yylval = new ParserVal(new Chain(yytext()));
               return Parser.BLTIN;
             }
{variable}   { yyparser.yylval = new ParserVal(new Chain(yytext()));
               return Parser.VARIABLE;
             }
{term}       { Tokenizer tokenizer = new Tokenizer(yytext());
               int[] components = tokenizer.getComponents();
               yyparser.yylval = new ParserVal(new Term(components[0], components[1], components[2]));
               return Parser.TERM;
             }
{operator}   { return (int)yycharat(0); }
{comma}      { return (int)yycharat(0); }
{digit}      { yyparser.yylval = new ParserVal(new Chain(yytext()));
               return Parser.DIGIT;
             }
{newline}    { return (int)yycharat(0); }
{whitespace} {/* Ignore this */}

/* Error fallback */
[^] { System.err.println("Error: unexpected character '" + yytext() + "'");
      return -1;
    }
