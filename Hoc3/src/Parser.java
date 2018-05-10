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






//#line 4 "SintAnalizer.y"
    import java.io.*;
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.lang.reflect.*;
//#line 22 "Parser.java"




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
public final static short BLTIN=257;
public final static short VAR=258;
public final static short TERM=259;
public final static short SNUM=260;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    0,    1,    2,    2,    2,    2,    2,
    2,    2,    2,    2,    2,    2,    3,    3,    4,    4,
    5,
};
final static short yylen[] = {                            2,
    0,    2,    3,    3,    3,    1,    1,    1,    4,    6,
    3,    3,    3,    3,    3,    3,    3,    2,    1,    2,
    1,
};
final static short yydefred[] = {                         1,
    0,    0,    0,    0,    0,    2,    0,    0,    6,    0,
    0,    8,    0,   21,   18,    0,    0,    3,    0,    0,
    0,    0,    0,    4,    0,    0,   16,   17,   20,    0,
    0,    0,    0,   15,    9,    0,    0,   10,
};
final static short yydgoto[] = {                          1,
   12,    8,    9,   16,   17,
};
final static short yysindex[] = {                         0,
  -10,  -33,  -48,  -39,  -91,    0,    5,   -1,    0, -239,
  -39,    0,  -37,    0,    0,  -68, -232,    0,  -39,  -39,
  -39,  -39, -229,    0,  -15,  -31,    0,    0,    0,  -23,
  -23,  -62,  -62,    0,    0, -223,   -2,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,   -7,    0,    0,    0,  -25,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -46,    0,    0,    0,
    0,    0,    0,    0,    0,   18,    0,    0,    0,   33,
   39,   13,   23,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   44,   75,    0,   31,    0,
};
final static int YYTABLESIZE=248;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                          6,
    4,   15,    7,   27,   22,   20,   10,   19,   24,   21,
   22,   20,   11,   19,   18,   21,    8,    8,   22,    8,
   25,    8,   14,   21,   28,   35,   14,    5,   36,    4,
   34,   23,   13,    7,    7,    7,   37,    7,   38,    7,
   22,   20,   12,   19,    7,   21,   19,   29,   11,    0,
    0,    5,    0,   14,   14,   14,   23,   14,    5,   14,
    0,    0,   23,   13,   13,   13,    0,   13,    8,   13,
   23,    0,    0,   12,    0,   12,    0,   12,   13,   11,
    5,   11,    0,   11,    0,   26,    7,    0,    0,    0,
    0,    0,   23,   30,   31,   32,   33,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   14,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    2,    3,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    2,    3,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         10,
   40,   93,   10,   41,   42,   43,   40,   45,   10,   47,
   42,   43,   61,   45,   10,   47,   42,   43,   42,   45,
  260,   47,   10,   47,   93,   41,  259,   10,   44,   40,
  260,   94,   10,   41,   42,   43,  260,   45,   41,   47,
   42,   43,   10,   45,    1,   47,   93,   17,   10,   -1,
   -1,   91,   -1,   41,   42,   43,   94,   45,   41,   47,
   -1,   -1,   94,   41,   42,   43,   -1,   45,   94,   47,
   94,   -1,   -1,   41,   -1,   43,   -1,   45,    4,   41,
   91,   43,   -1,   45,   -1,   11,   94,   -1,   -1,   -1,
   -1,   -1,   94,   19,   20,   21,   22,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  259,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,
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
null,"'['",null,"']'","'^'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,"BLTIN","VAR","TERM","SNUM",
};
final static String yyrule[] = {
"$accept : list",
"list :",
"list : list '\\n'",
"list : list asgn '\\n'",
"list : list expr '\\n'",
"asgn : VAR '=' expr",
"expr : poly",
"expr : VAR",
"expr : asgn",
"expr : BLTIN '(' SNUM ')'",
"expr : BLTIN '(' SNUM ',' SNUM ')'",
"expr : expr '+' expr",
"expr : expr '-' expr",
"expr : expr '*' expr",
"expr : expr '/' expr",
"expr : expr '^' SNUM",
"expr : '(' expr ')'",
"poly : '[' terms ']'",
"poly : '[' ']'",
"terms : term",
"terms : term terms",
"term : TERM",
};

//#line 246 "SintAnalizer.y"

/***** SUPPORT CODE *****/
/*** MANDATORY USER SUPPLIED METHODS ***/

  /* Stores a reference to the lexer bject */
  private Yylex lexer;

  /* Interface to the lexer */
  private int yylex () {
    int yyl_return = -1;
    try {
      yyl_return = lexer.yylex();
    }
    catch (IOException e) {
      System.err.println("IO error :"+e);
    }
    return yyl_return;
  }

  /* Error reporting */
 public void yyerror (String error) {
   System.err.println ("Error: " + error);
 }

 /* The lexer is created in the constructor */
 public Parser(Reader r) {
  lexer = new Yylex(r, this);
}

/*** SYMBOL TABLE SUPPLIED METHODS ***/
HashMap<String,Symbol> symbolTable = new HashMap<>();

  Symbol lookUpTable(String symbolName) {
    return symbolTable.get(symbolName);
}

  void install(String name, Polynomial data, short type) {
    Symbol s = new Symbol(name, type, data);
    symbolTable.put(name,s);
}

  void update(Symbol s, Polynomial data) {
    Symbol newSymbol = s;
    symbolTable.remove(s.getName());
    newSymbol.setData(data);
    symbolTable.put(s.getName(),s);
}

  /* We initiialize the symbol table */
  void init() {
    /* We initialize the functions */
    String[] funcNames = {"geometric", "binomial", "nbinomial", "integer", "derivate"};
    for (int i = 0; i < funcNames.length; i++) {
      install(funcNames[i], null, (short) 2);
    }
}


public static void main(String args[]) throws IOException {
  System.out.println("Calculadora de Polinomios");
  /* We create an instance of the class Parser */
  Parser yyparser;

  /* Here we request an input for an expression */
  System.out.print("Expression: ");
  /* We call the constructor of the class Parser to which we pass as an argument the input string */
  try{
    yyparser = new Parser(new InputStreamReader(System.in));
    /* We initialize the lockup table */
    yyparser.init();
    /* We initialize the parser */
    yyparser.yyparse();
  }
  catch(Exception e){
    e.printStackTrace();
  }
}
//#line 317 "Parser.java"
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
//#line 29 "SintAnalizer.y"
{
        Polynomial result = (Polynomial) val_peek(1).obj;
        System.out.println("Result: " + result.toString() + "\n");
        System.out.print("Expression: ");
      }
break;
case 4:
//#line 34 "SintAnalizer.y"
{
        Polynomial result = (Polynomial) val_peek(1).obj;
        System.out.println("Result: " + result.toString() + "\n");
        System.out.print("Expression: ");
      }
break;
case 5:
//#line 41 "SintAnalizer.y"
{
  System.out.println("Doing an assignation");
  InputText t = (InputText) val_peek(2).obj;
  Symbol s = lookUpTable(t.getText());
  if(s==null){
    install(t.getText(), (Polynomial) val_peek(0).obj, (short) 1);
  }
  else{
    update(s, (Polynomial) val_peek(0).obj);
  }
  yyval = new ParserVal((Polynomial) val_peek(0).obj);
}
break;
case 7:
//#line 56 "SintAnalizer.y"
{
       System.out.println("Variable Detected");
       InputText t = (InputText) val_peek(0).obj;
       Symbol s = lookUpTable(t.getText());
       if(s!=null){
         Polynomial data = (Polynomial) s.getData();
         yyval = new ParserVal(data);
       }
       else{
         yyerror("Variable not declared");
         System.exit(0);
       }
     }
break;
case 8:
//#line 69 "SintAnalizer.y"
{
       System.out.println("Assignment Detected");
     }
break;
case 9:
//#line 72 "SintAnalizer.y"
{
       System.out.print("Built-in Detected: ");
       Polynomial p = new Polynomial();
       InputText t = (InputText) val_peek(3).obj;
       Symbol s = lookUpTable(t.getText());
       String func_name = s.getName();
       int n = Integer.parseInt(((InputText) val_peek(1).obj).getText());

       if(func_name.compareTo("binomial") == 0){
         Binomial bi = new Binomial();
          if(n >= 0){
            System.out.println("Newton binomial (Single positive expansion)");
            p = bi.newtonBinomial('p',n);
          }
          else{
            yyerror("Not suitable built-in");
            System.exit(0);
          }
       }
       else if(func_name.compareTo("nbinomial") == 0){
         Binomial bi = new Binomial();
         if(n >= 0){
           System.out.println("Newton binomial (Single negative expansion)");
           p = bi.newtonBinomial('n',n);
         }
         else{
           yyerror("Not suitable built-in");
           System.exit(0);
         }
       }
       else{
         yyerror("Not suitable built-in");
         System.exit(0);
       }
       yyval = new ParserVal(p);
     }
break;
case 10:
//#line 108 "SintAnalizer.y"
{
       System.out.print("Built-in Detected: ");
       Polynomial p = new Polynomial();
       InputText t = (InputText) val_peek(5).obj;
       Symbol s = lookUpTable(t.getText());
       String func_name = s.getName();
       int y = Integer.parseInt(((InputText) val_peek(3).obj).getText());
       int n = Integer.parseInt(((InputText) val_peek(1).obj).getText());

       if(func_name.compareTo("binomial") == 0){
         Binomial bi = new Binomial();
          if(n >= 0){
            System.out.println("Newton binomial (Compound positive expansion)");
            p = bi.newtonBinomial('p',y,n);
          }
          else{
            yyerror("Variable not valid n value for Newton Binomial expansion");
            System.exit(0);
          }
       }
       else if(func_name.compareTo("nbinomial") == 0){
         Binomial bi = new Binomial();
         if(n >= 0){
           System.out.println("Newton binomial (Compound negative expansion)");
           p = bi.newtonBinomial('n',y,n);
         }
         else{
           yyerror("Variable not valid n value for Newton Binomial expansion");
           System.exit(0);
         }
       }
       else if(func_name.compareTo("geometric") == 0){
         System.out.println("Geometric Serie");
         Geometric geo = new Geometric();
          if(n >= 0){
             p = geo.geometricSerie(y,Math.abs(n));
          }
          else{
            yyerror("Variable not valid n value for Geometric Serie");
            System.exit(0);
          }
       }
       else{
        yyerror("Not suitable built-in");
        System.exit(0);
       }
       yyval = new ParserVal(p);
     }
break;
case 11:
//#line 156 "SintAnalizer.y"
{
       System.out.println("Addition Detected");
       Polynomial a = (Polynomial) val_peek(2).obj;
       Polynomial b = (Polynomial) val_peek(0).obj;
       Sum s = new Sum();
       Polynomial sumab = s.sum(a,b);
       yyval = new ParserVal(sumab);
     }
break;
case 12:
//#line 164 "SintAnalizer.y"
{
       System.out.println("Substraction Detected");
       Polynomial a = (Polynomial) val_peek(2).obj;
       Polynomial b = (Polynomial) val_peek(0).obj;
       Sub sb = new Sub();
       Polynomial subab = sb.sub(a,b);
       yyval = new ParserVal(subab);
     }
break;
case 13:
//#line 172 "SintAnalizer.y"
{
       System.out.println("Multiplication Detected");
       Polynomial a = (Polynomial) val_peek(2).obj;
       Polynomial b = (Polynomial) val_peek(0).obj;
       Mul mul = new Mul();
       Polynomial mulab = mul.multiplyTwo(a,b);
       yyval = new ParserVal(mulab);
     }
break;
case 14:
//#line 180 "SintAnalizer.y"
{
       System.out.println("Division Detected");
       Polynomial a = (Polynomial) val_peek(2).obj;
       Polynomial b = (Polynomial) val_peek(0).obj;
       Div div = new Div();
       Polynomial divab = div.division(b,a);
       yyval = new ParserVal(divab);
     }
break;
case 15:
//#line 188 "SintAnalizer.y"
{
       System.out.println("Power Detected");
       Polynomial a = (Polynomial) val_peek(2).obj;
       Polynomial powan = new Polynomial();
       int n = Integer.parseInt(((InputText) val_peek(0).obj).getText());
       if(n>=0){
         Pow pow = new Pow();
         powan = pow.power(a,n);
       }
       else{
         yyerror("Variable not valid n value for Pow");
         System.exit(0);
       }
       yyval = new ParserVal(powan);
     }
break;
case 16:
//#line 203 "SintAnalizer.y"
{
       System.out.println("Nesting Detected");
       Polynomial p = (Polynomial) val_peek(1).obj;
       yyval = new ParserVal(p);
     }
break;
case 17:
//#line 210 "SintAnalizer.y"
{
  System.out.println("Polynomial with terms detected");
  ArrayList<Term> t = (ArrayList<Term>) val_peek(1).obj;
  Polynomial p = new Polynomial(t);
  yyval = new ParserVal(p);

}
break;
case 18:
//#line 217 "SintAnalizer.y"
{
  System.out.println("Empty polynomial detected");
  Polynomial p = new Polynomial();
  yyval = new ParserVal(p);
}
break;
case 19:
//#line 224 "SintAnalizer.y"
{
  Term t = (Term) val_peek(0).obj;
  ArrayList<Term> ts = new ArrayList<>();
  ts.add(t);
  yyval = new ParserVal(ts);
}
break;
case 20:
//#line 230 "SintAnalizer.y"
{
  System.out.println("List of terms Detected");
  Term t = (Term) val_peek(1).obj;
  ArrayList<Term> ts = (ArrayList<Term>) val_peek(0).obj;
  ts.add(t);
  yyval = new ParserVal(ts);
}
break;
case 21:
//#line 239 "SintAnalizer.y"
{
  System.out.println("Term Detected");
  Term t = (Term) val_peek(0).obj;
  yyval = new ParserVal(t);
}
break;
//#line 724 "Parser.java"
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
