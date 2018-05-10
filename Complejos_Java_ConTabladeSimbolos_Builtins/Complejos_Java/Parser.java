//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "complejos.y"
  import java.io.*;
  import java.util.ArrayList;
//#line 20 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt;
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short VAR=257;
public final static short CNUMBER=258;
public final static short BLTIN=259;
public final static short DIG=260;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    0,    1,    2,    2,    2,    2,    2,
    2,    2,    2,    2,    2,
};
final static short yylen[] = {                            2,
    0,    2,    3,    3,    3,    1,    1,    1,    6,    4,
    3,    3,    3,    3,    3,
};
final static short yydefred[] = {                         1,
    0,    0,    6,    0,    2,    0,    0,    0,    0,    0,
    8,    0,    3,    0,    0,    0,    0,    4,    0,    0,
   15,    0,    0,   13,   14,    0,   10,    0,    9,
};
final static short yydgoto[] = {                          1,
   11,    8,
};
final static short yysindex[] = {                         0,
  -10,  -51,    0,  -24,    0,  -39,   14,   -2,  -39,  -39,
    0,  -16,    0,  -39,  -39,  -39,  -39,    0,   16,  -38,
    0,  -30,  -30,    0,    0, -232,    0,   -9,    0,
};
final static short yyrindex[] = {                         0,
    0,   -8,    0,    0,    0,    0,   22,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   13,    0,
    0,    3,    8,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   37,    5,
};
final static int YYTABLESIZE=249;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                          5,
    6,    7,   27,   16,   15,   26,   14,   18,   17,    9,
   12,   16,   12,   19,   20,   10,   17,   11,   22,   23,
   24,   25,    5,   13,   21,   16,   15,   28,   14,    6,
   17,   29,    7,    7,    7,    7,    7,    7,    7,   16,
   15,    0,   14,   12,   17,   12,   12,   12,   11,    0,
   11,   11,   11,    5,    0,    0,    5,   16,   15,    0,
   14,    0,   17,    8,    8,    0,    8,    0,    8,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    2,    3,    4,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    2,    3,    4,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         10,
   40,   10,   41,   42,   43,   44,   45,   10,   47,   61,
    6,   42,   10,    9,   10,   40,   47,   10,   14,   15,
   16,   17,   10,   10,   41,   42,   43,  260,   45,   40,
   47,   41,   41,   42,   43,   44,   45,    1,   47,   42,
   43,   -1,   45,   41,   47,   43,   44,   45,   41,   -1,
   43,   44,   45,   41,   -1,   -1,   44,   42,   43,   -1,
   45,   -1,   47,   42,   43,   -1,   45,   -1,   47,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,  259,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,  259,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=260;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,"'\\n'",null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'",
"','","'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,
null,null,"'='",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"VAR","CNUMBER","BLTIN","DIG",
};
final static String yyrule[] = {
"$accept : list",
"list :",
"list : list '\\n'",
"list : list asgn '\\n'",
"list : list exp '\\n'",
"asgn : VAR '=' exp",
"exp : CNUMBER",
"exp : VAR",
"exp : asgn",
"exp : BLTIN '(' exp ',' DIG ')'",
"exp : BLTIN '(' exp ')'",
"exp : exp '+' exp",
"exp : exp '-' exp",
"exp : exp '*' exp",
"exp : exp '/' exp",
"exp : '(' exp ')'",
};

//#line 86 "complejos.y"

/** CÓDIGO DE SOPORTE **/
  private Yylex lexer;
  Functions f     = new Functions();


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

/* Operaciones básicas de complejos */
  public Complejo sumaComplejos(Complejo c1, Complejo c2) {
    Complejo res = new Complejo(c1.getReal() + c2.getReal(),
                        c1.getImg() + c2.getImg());
    return res;
  }

  public Complejo restaComplejos(Complejo c1, Complejo c2) {
    Complejo res = new Complejo(c1.getReal() - c2.getReal(),
                                c1.getImg() - c2.getImg());
    return res;
  }

  public Complejo multiplicaComplejos(Complejo c1, Complejo c2) {
    Complejo res = new Complejo((c1.getReal() * c2.getReal()) - (c1.getImg() * c2.getImg()),
                                  (c1.getImg() * c2.getReal()) + (c1.getReal() * c2.getImg()) );
    return res;
  }

  public Complejo divideComplejos(Complejo c1, Complejo c2) {
    float d = (float) ( (c2.getReal() * c2.getReal()) + (c2.getImg() * c2.getImg()) );
    Complejo res = new Complejo((float) (c1.getReal() * c2.getReal() + c1.getImg() * c2.getImg()) / d,
                              (float) (c1.getImg() * c2.getReal() - c1.getReal() * c2.getImg()) / d );
    return res;
  }

  public void imprimeComplejo(Complejo c) {
    float real  = c.getReal();
    float img   = c.getImg();

    if (img > 0)
      System.out.println("" + real + " + " + img + " i");
    else
      System.out.println("" + real + " " + img + " i");
  }


/* Metodos de la tabla de símbolos */
ArrayList<Symbol> symbolTable = new ArrayList<>();

Symbol lookUpTable(String symbolName) {
  for (Symbol s: symbolTable) {
    if (s.getName().compareTo(symbolName) == 0) { // The symbol is already in the table !
      return s;
    }
  }

  return null;
}

void install(String name, Complejo data, short type) {
  Symbol s = new Symbol(name, type, data);
  symbolTable.add(s);
}

void update(Symbol s, Complejo data) {
  Symbol newSymbol = s;
  symbolTable.remove(s);
  newSymbol.setData(data);
  symbolTable.add(newSymbol);
}

/* Método para la inicializacion de las funciones disponibles */
void init() {
  /* Se inicializan las funciones */
  String[] funcNames = {"exp", "sin", "cos", "pow"};
  for (int i = 0; i < funcNames.length; i++) {
    install(funcNames[i], null, (short) 2);
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
//#line 342 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop");
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 3:
//#line 18 "complejos.y"
{}
break;
case 4:
//#line 19 "complejos.y"
{Complejo res = (Complejo) val_peek(1).obj; System.out.print("Resultado: "); imprimeComplejo(res);}
break;
case 5:
//#line 21 "complejos.y"
{ Cadena c = (Cadena) val_peek(2).obj;
                      Symbol s = lookUpTable(c.getCadena());
                      if (s == null)
                        install(c.getCadena(), (Complejo) val_peek(0).obj, (short) 1);
                      else
                        update(s, (Complejo) val_peek(0).obj);
                    }
break;
case 6:
//#line 29 "complejos.y"
{Complejo c = (Complejo) val_peek(0).obj;}
break;
case 7:
//#line 30 "complejos.y"
{ Cadena c      = (Cadena) val_peek(0).obj;
                                    Symbol s      = lookUpTable(c.getCadena());
                                    if (s != null) {
                                      Complejo data = (Complejo) s.getData();
                                      yyval = new ParserVal(data);
                                    } else {
                                      yyerror("Variable no declarada");
                                      System.exit(0);
                                    }
                                  }
break;
case 9:
//#line 41 "complejos.y"
{ Cadena c1       = (Cadena) val_peek(5).obj;
                                    Symbol s        = lookUpTable(c1.getCadena());
                                    String fName    = s.getName();
                                    Complejo res    = new Complejo(0, 0);
                                    Cadena powN     = (Cadena) val_peek(1).obj;

                                    if (fName.compareTo("pow") == 0) {
                                      res = f.pow((Complejo) val_peek(3).obj, powN);
                                    }
                                    yyval = new ParserVal(res);

                                  }
break;
case 10:
//#line 53 "complejos.y"
{ Cadena c        = (Cadena) val_peek(3).obj;
                                    Symbol s        = lookUpTable(c.getCadena());
                                    String fName    = s.getName();
                                    Complejo res    = new Complejo(0, 0);

                                    if (fName.compareTo("exp") == 0) {
                                      res = f.exp((Complejo) val_peek(1).obj);
                                    } else if (fName.compareTo("sin") == 0) {
                                      res = f.sinus((Complejo) val_peek(1).obj);
                                    } else if (fName.compareTo("cos") == 0) {
                                      res = f.cosine((Complejo) val_peek(1).obj);
                                    }
                                    yyval = new ParserVal(res);
                                  }
break;
case 11:
//#line 67 "complejos.y"
{ Complejo c1   = (Complejo) val_peek(2).obj; Complejo c2 = (Complejo) val_peek(0).obj;
                                    Complejo res  = sumaComplejos(c1, c2);
                                    yyval            = new ParserVal(res);
                                  }
break;
case 12:
//#line 71 "complejos.y"
{ Complejo c1   = (Complejo) val_peek(2).obj; Complejo c2 = (Complejo) val_peek(0).obj;
                                    Complejo res  = restaComplejos(c1, c2);
                                    yyval            = new ParserVal(res);
                                  }
break;
case 13:
//#line 75 "complejos.y"
{ Complejo c1   = (Complejo) val_peek(2).obj; Complejo c2 = (Complejo) val_peek(0).obj;
                                    Complejo res  = multiplicaComplejos(c1, c2);
                                    yyval            = new ParserVal(res);
                                  }
break;
case 14:
//#line 79 "complejos.y"
{ Complejo c1   = (Complejo) val_peek(2).obj; Complejo c2 = (Complejo) val_peek(0).obj;
                                    Complejo res  = divideComplejos(c1, c2);
                                    yyval            = new ParserVal(res);
                                  }
break;
case 15:
//#line 83 "complejos.y"
{Complejo c = (Complejo) val_peek(1).obj; yyval = new ParserVal(c); }
break;
//#line 590 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
