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
//#line 19 "Parser.java"




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
public final static short IF=258;
public final static short ELSE=259;
public final static short WHILE=260;
public final static short FOR=261;
public final static short PRINT=262;
public final static short EQ=263;
public final static short NEQ=264;
public final static short GT=265;
public final static short GE=266;
public final static short LT=267;
public final static short LE=268;
public final static short DIG=269;
public final static short VAR=270;
public final static short CNUMBER=271;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    0,    0,    1,    2,    2,    2,    2,
    2,    2,    2,    5,    4,    7,    8,    9,   10,    6,
   11,   11,   11,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,
};
final static short yylen[] = {                            2,
    0,    2,    3,    3,    3,    3,    1,    2,    4,    4,
    7,   10,    3,    3,    1,    1,    1,    1,    1,    0,
    0,    2,    2,    1,    1,    1,    4,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,
};
final static short yydefred[] = {                         1,
    0,    0,   16,   15,   17,    0,    0,   24,    2,    0,
   21,    0,    0,    0,    0,    0,    0,    0,   26,    0,
    0,    0,    0,    3,    4,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    5,    0,    0,    0,    0,
    0,    0,    0,   39,   22,   13,   23,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   32,    0,
   20,   20,    0,    0,   27,   14,    9,    0,    0,    0,
    0,   20,    0,   11,    0,    0,    0,   20,   12,
};
final static short yydgoto[] = {                          1,
   19,   13,   48,   15,   39,   67,   16,   17,   64,   76,
   23,
};
final static short yysindex[] = {                         0,
  291,  -35,    0,    0,    0,  -38,  -55,    0,    0,  -38,
    0,   -3,   -2,  183,  -30,  -30,  -29,  -38,    0,  375,
  -38,  347,  262,    0,    0,  -38,  -38,  -38,  -38,  -38,
  -38,  -38,  -38,  -38,  -38,    0, -257,  -38,  255,  255,
  -38,  388,  375,    0,    0,    0,    0,  375,  375,  375,
  375,  375,  375,  375,  292,  292,  -85,  -85,    0,  417,
    0,    0,  375,  -45,    0,    0,    0, -243,  -30,  255,
  -42,    0,  -38,    0,  375,  -23,  255,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,  -10,    0,    0,    0,
    0,  433,    0,    0,    0,    0,    0,    0,    0,  166,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   67,    0,    0,    0,    0,  195,   84,   91,
  106,  113,  145,  233,   46,   52,    5,   26,    0,    0,
    0,    0,  -37,    0,    0,    0,    0,  320,    0,    0,
    0,    0,    0,    0,  -18,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   23,  -19,  685,    0,  -15,  -59,    0,    0,    0,    0,
    0,
};
final static int YYTABLESIZE=758;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         25,
   40,   10,   68,   47,   18,   21,   24,   25,   37,   38,
   41,   59,   74,   69,   30,   70,   73,   77,   79,   61,
   62,   18,   19,   12,    0,    0,    0,    0,    0,   25,
   25,   25,   25,    0,   25,   31,   25,    0,    0,    0,
    0,    0,    0,    0,   30,   30,   30,   30,   25,   30,
   72,   30,    0,   71,    0,   29,    0,   78,    0,    0,
    0,   28,    0,   30,    0,   31,   31,   31,   31,    0,
   31,    0,   31,    0,    0,    0,    6,    0,    0,    0,
    0,    0,    0,   25,   31,   29,   29,    0,   29,    0,
   29,   28,   28,   33,   28,    0,   28,    0,    0,    0,
   34,    0,    0,    0,   29,    0,    6,    6,    0,    0,
   28,    0,   25,    0,   25,   35,    0,    0,    0,    0,
    0,    0,   36,   33,   33,    6,    0,   30,    0,   30,
   34,   34,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   33,    0,    0,   35,   35,    0,   31,   34,
   31,    0,   36,   36,   37,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   35,    0,    0,    0,   29,    0,
   29,   36,    0,    0,   28,    8,   28,   26,   27,   28,
   29,   30,   31,    0,   37,   37,    0,    0,    0,    6,
    0,    6,   36,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   37,    7,    8,   33,    0,   33,    0,
    0,    0,    0,   34,    0,   34,    0,    0,    2,    0,
    0,    0,    0,    0,   34,   33,    0,   32,   35,   35,
   35,    7,    8,    0,    7,   36,    0,   36,    0,    0,
    0,    0,   38,    0,    0,    0,   25,   25,   25,   25,
   25,   25,   25,   25,   25,   25,   25,   25,    0,   25,
   25,   30,   30,   30,   30,   30,   30,   37,    0,   37,
    0,   45,   38,   38,   30,   30,   37,    0,    0,    0,
    0,    0,   31,   31,   31,   31,   31,   31,    8,    0,
    8,   38,    0,    0,   10,   31,   31,    0,    0,    0,
    9,   10,   29,   29,   29,   29,   29,   29,   28,   28,
   28,   28,   28,   28,    0,   29,   29,    7,    0,    7,
    0,   28,   28,    6,    6,    6,    6,    6,    6,   10,
   10,    0,    0,   34,    0,    0,    6,    6,   35,    0,
   33,   33,   33,   33,   33,   33,    0,   34,   34,   34,
   34,   34,   34,   33,   33,   38,    0,   38,    0,   10,
   34,   34,   35,   35,   35,   35,   35,   35,    0,   36,
   36,   36,   36,   36,   36,   35,   35,   11,    0,    0,
    0,    0,   36,   36,   11,   37,   46,   44,   34,   33,
    0,   32,    0,   35,    0,    0,    0,    0,    0,    0,
    0,   37,   37,   37,   37,   37,   37,    0,    0,    0,
    0,    0,    0,   11,   37,   37,   34,   33,    0,   32,
    0,   35,    8,    8,    8,    8,    8,    8,   65,   34,
   33,    0,   32,    0,   35,    8,    8,    0,    0,    0,
   37,    0,   10,    0,   10,   26,   27,   28,   29,   30,
   31,    7,    7,    7,    7,    7,    7,   66,   34,   33,
    0,   32,    0,   35,    7,    7,    0,    0,   37,    0,
    0,    0,    0,    0,   26,   26,    0,   26,    0,   26,
    0,   37,    0,    0,    0,    0,    0,    0,    0,   38,
   38,   38,   38,   38,   38,    0,    0,    0,    0,    0,
    0,    0,   38,   38,    0,    0,    0,    0,    0,    0,
   37,    2,    3,    0,    4,    5,    6,    0,    2,    3,
    0,    4,    5,    6,    7,    8,   26,    0,    0,    0,
    0,    7,    8,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    2,    3,    0,
    4,    5,    6,    0,   26,   27,   28,   29,   30,   31,
    7,    8,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   10,   10,    0,   10,
   10,   10,    0,    0,    0,    0,    0,    0,    0,   10,
   10,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   26,
   27,   28,   29,   30,   31,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   26,   27,   28,
   29,   30,   31,    0,    0,    0,    0,    0,    0,    0,
   26,   27,   28,   29,   30,   31,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   26,
   27,   28,   29,   30,   31,   14,    0,    0,    0,    0,
   20,    0,    0,    0,   22,   26,   26,   26,   26,   26,
   26,    0,   42,    0,    0,   43,    0,    0,    0,    0,
   49,   50,   51,   52,   53,   54,   55,   56,   57,   58,
    0,    0,   60,    0,    0,   63,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   75,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         10,
   16,   40,   62,   23,   40,   61,   10,   10,   94,   40,
   40,  269,   72,   59,   10,  259,   59,   41,   78,   39,
   40,   59,   41,    1,   -1,   -1,   -1,   -1,   -1,   40,
   41,   42,   43,   -1,   45,   10,   47,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   40,   41,   42,   43,   59,   45,
   70,   47,   -1,   69,   -1,   10,   -1,   77,   -1,   -1,
   -1,   10,   -1,   59,   -1,   40,   41,   42,   43,   -1,
   45,   -1,   47,   -1,   -1,   -1,   10,   -1,   -1,   -1,
   -1,   -1,   -1,   94,   59,   40,   41,   -1,   43,   -1,
   45,   40,   41,   10,   43,   -1,   45,   -1,   -1,   -1,
   10,   -1,   -1,   -1,   59,   -1,   40,   41,   -1,   -1,
   59,   -1,  123,   -1,  125,   10,   -1,   -1,   -1,   -1,
   -1,   -1,   10,   40,   41,   59,   -1,  123,   -1,  125,
   40,   41,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   59,   -1,   -1,   40,   41,   -1,  123,   59,
  125,   -1,   40,   41,   10,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   59,   -1,   -1,   -1,  123,   -1,
  125,   59,   -1,   -1,  123,   10,  125,  263,  264,  265,
  266,  267,  268,   -1,   40,   41,   -1,   -1,   -1,  123,
   -1,  125,   10,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   59,   10,   40,  123,   -1,  125,   -1,
   -1,   -1,   -1,  123,   -1,  125,   -1,   -1,  257,   -1,
   -1,   -1,   -1,   -1,   42,   43,   -1,   45,  123,   47,
  125,  270,  271,   -1,   40,  123,   -1,  125,   -1,   -1,
   -1,   -1,   10,   -1,   -1,   -1,  257,  258,  259,  260,
  261,  262,  263,  264,  265,  266,  267,  268,   -1,  270,
  271,  257,  258,  259,  260,  261,  262,  123,   -1,  125,
   -1,   10,   40,   41,  270,  271,   94,   -1,   -1,   -1,
   -1,   -1,  257,  258,  259,  260,  261,  262,  123,   -1,
  125,   59,   -1,   -1,   40,  270,  271,   -1,   -1,   -1,
   10,   40,  257,  258,  259,  260,  261,  262,  257,  258,
  259,  260,  261,  262,   -1,  270,  271,  123,   -1,  125,
   -1,  270,  271,  257,  258,  259,  260,  261,  262,   10,
   40,   -1,   -1,   42,   -1,   -1,  270,  271,   47,   -1,
  257,  258,  259,  260,  261,  262,   -1,  257,  258,  259,
  260,  261,  262,  270,  271,  123,   -1,  125,   -1,   40,
  270,  271,  257,  258,  259,  260,  261,  262,   -1,  257,
  258,  259,  260,  261,  262,  270,  271,  123,   -1,   -1,
   -1,   -1,  270,  271,  123,   94,  125,   41,   42,   43,
   -1,   45,   -1,   47,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  257,  258,  259,  260,  261,  262,   -1,   -1,   -1,
   -1,   -1,   -1,  123,  270,  271,   42,   43,   -1,   45,
   -1,   47,  257,  258,  259,  260,  261,  262,   41,   42,
   43,   -1,   45,   -1,   47,  270,  271,   -1,   -1,   -1,
   94,   -1,  123,   -1,  125,  263,  264,  265,  266,  267,
  268,  257,  258,  259,  260,  261,  262,   41,   42,   43,
   -1,   45,   -1,   47,  270,  271,   -1,   -1,   94,   -1,
   -1,   -1,   -1,   -1,   42,   43,   -1,   45,   -1,   47,
   -1,   94,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,
  258,  259,  260,  261,  262,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  270,  271,   -1,   -1,   -1,   -1,   -1,   -1,
   94,  257,  258,   -1,  260,  261,  262,   -1,  257,  258,
   -1,  260,  261,  262,  270,  271,   94,   -1,   -1,   -1,
   -1,  270,  271,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,   -1,
  260,  261,  262,   -1,  263,  264,  265,  266,  267,  268,
  270,  271,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,   -1,  260,
  261,  262,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  270,
  271,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  263,
  264,  265,  266,  267,  268,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  263,  264,  265,
  266,  267,  268,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  263,  264,  265,  266,  267,  268,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  263,
  264,  265,  266,  267,  268,    1,   -1,   -1,   -1,   -1,
    6,   -1,   -1,   -1,   10,  263,  264,  265,  266,  267,
  268,   -1,   18,   -1,   -1,   21,   -1,   -1,   -1,   -1,
   26,   27,   28,   29,   30,   31,   32,   33,   34,   35,
   -1,   -1,   38,   -1,   -1,   41,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   73,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=271;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,"'\\n'",null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'",
null,"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,
"';'",null,"'='",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,"'^'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,"BLTIN","IF","ELSE","WHILE","FOR",
"PRINT","EQ","NEQ","GT","GE","LT","LE","DIG","VAR","CNUMBER",
};
final static String yyrule[] = {
"$accept : list",
"list :",
"list : list '\\n'",
"list : list asgn '\\n'",
"list : list stmt '\\n'",
"list : list exp '\\n'",
"asgn : VAR '=' exp",
"stmt : exp",
"stmt : PRINT exp",
"stmt : while cond stmt end",
"stmt : if cond stmt end",
"stmt : if cond stmt end ELSE stmt end",
"stmt : for '(' inicio ';' cond ';' inc ')' stmt end",
"stmt : '{' stmtlist '}'",
"cond : '(' exp ')'",
"while : WHILE",
"if : IF",
"for : FOR",
"inicio : exp",
"inc : exp",
"end :",
"stmtlist :",
"stmtlist : stmtlist '\\n'",
"stmtlist : stmtlist stmt",
"exp : CNUMBER",
"exp : VAR",
"exp : asgn",
"exp : BLTIN '(' exp ')'",
"exp : exp '+' exp",
"exp : exp '-' exp",
"exp : exp '*' exp",
"exp : exp '/' exp",
"exp : exp '^' DIG",
"exp : exp EQ exp",
"exp : exp NEQ exp",
"exp : exp GT exp",
"exp : exp GE exp",
"exp : exp LT exp",
"exp : exp LE exp",
"exp : '(' exp ')'",
};

//#line 124 "complejos.y"

/** SUPPORT CODE **/
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
    maq.initCode();
    
    while (true) {
      System.out.print("Expression: ");
      maq.newProgram();
      
      yyparser = new Parser(new InputStreamReader(System.in));        
      yyparser.yyparse();
            
      maq.execute(0);
    }

  }
//#line 432 "Parser.java"
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
//#line 22 "complejos.y"
{ maq.code("STOP"); return 1; }
//break;
case 4:
//#line 23 "complejos.y"
{ maq.code("STOP"); return 1; }
//break;
case 5:
//#line 24 "complejos.y"
{ maq.code("printComplex"); maq.code("STOP"); 
                          return 1;
                        }
//break;
case 6:
//#line 28 "complejos.y"
{ int numI = maq.code("varPush");
                      maq.code((Cadena) val_peek(2).obj);
                      maq.code("asgVar");                       
                      yyval = new ParserVal(val_peek(0).obj);    
                    }
break;
case 7:
//#line 34 "complejos.y"
{ maq.code("pop"); }
break;
case 8:
//#line 35 "complejos.y"
{ maq.code("printComplex"); 
                    yyval = new ParserVal(val_peek(0).obj); 
                  }
break;
case 9:
//#line 38 "complejos.y"
{ maq.getProg().setElementAt(val_peek(1).obj, (int) val_peek(3).obj + 1);
                              maq.getProg().setElementAt(val_peek(0).obj, (int) val_peek(3).obj + 2); 
                            }
break;
case 10:
//#line 41 "complejos.y"
{ maq.getProg().setElementAt(val_peek(1).obj, (int) val_peek(3).obj + 1); 
                              maq.getProg().setElementAt(val_peek(0).obj, (int) val_peek(3).obj + 3); 
                            }
break;
case 11:
//#line 44 "complejos.y"
{  maq.getProg().setElementAt(val_peek(4).obj, (int) val_peek(6).obj + 1);
                                          maq.getProg().setElementAt(val_peek(1).obj, (int) val_peek(6).obj + 2); 
                                          maq.getProg().setElementAt(val_peek(0).obj, (int) val_peek(6).obj + 3);
	                                     }
break;
case 12:
//#line 48 "complejos.y"
{  maq.getProg().setElementAt(val_peek(7).obj, (int) val_peek(9).obj + 1);
                                                        maq.getProg().setElementAt(val_peek(5).obj, (int) val_peek(9).obj + 2); 
                                                        maq.getProg().setElementAt(val_peek(3).obj, (int) val_peek(9).obj + 3);
                                                        maq.getProg().setElementAt(val_peek(1).obj, (int) val_peek(9).obj + 4);
                                                        maq.getProg().setElementAt(val_peek(0).obj, (int) val_peek(9).obj + 5);
                                                     }
break;
case 13:
//#line 54 "complejos.y"
{yyval  =  val_peek(1); }
break;
case 14:
//#line 56 "complejos.y"
{ maq.code("STOP");                    
                    yyval = new ParserVal(val_peek(1).obj);
                  }
break;
case 15:
//#line 60 "complejos.y"
{ int numI = maq.code("whileCode"); 
                               maq.code("STOP"); maq.code("STOP");
                               yyval = new ParserVal(new Integer(numI));
                             }
break;
case 16:
//#line 65 "complejos.y"
{ int numI = maq.code("ifCode");
          maq.code("STOP"); maq.code("STOP"); maq.code("STOP");
          yyval = new ParserVal(new Integer(numI));
        }
break;
case 17:
//#line 70 "complejos.y"
{ int numI = maq.code("forCode");
           maq.code("STOP"); maq.code("STOP"); maq.code("STOP"); maq.code("STOP"); maq.code("STOP");
           yyval = new ParserVal(new Integer(numI));
         }
break;
case 18:
//#line 75 "complejos.y"
{ maq.code("STOP");
              yyval = new ParserVal(val_peek(0).obj);
            }
break;
case 19:
//#line 79 "complejos.y"
{ maq.code("STOP");
              yyval = new ParserVal(val_peek(0).obj);
            }
break;
case 20:
//#line 83 "complejos.y"
{ maq.code("STOP");
           yyval = new ParserVal(new Integer(maq.getProg().size())); 
         }
break;
case 21:
//#line 87 "complejos.y"
{ yyval = new ParserVal(new Integer(maq.getProg().size())); }
break;
case 24:
//#line 91 "complejos.y"
{ Complejo c = (Complejo) val_peek(0).obj; 
                                    int numI = maq.code("cNumber");                                   
                                    maq.code(c);
                                    yyval = new ParserVal(new Integer(numI));
                                  }
break;
case 25:
//#line 96 "complejos.y"
{ int numI = maq.code("varPush");
                                    maq.code((Cadena) val_peek(0).obj);
                                    maq.code("getVarValue");                                    
                                    yyval = new ParserVal(new Integer(numI));
                                  }
break;
case 26:
//#line 101 "complejos.y"
{}
break;
case 27:
//#line 102 "complejos.y"
{ maq.code("bltinPush");
                                    maq.code((Cadena) val_peek(3).obj);
                                    maq.code("bltin");                                    
                                  }
break;
case 28:
//#line 106 "complejos.y"
{ maq.code("add"); }
break;
case 29:
//#line 107 "complejos.y"
{ maq.code("sub"); }
break;
case 30:
//#line 108 "complejos.y"
{ maq.code("mult"); }
break;
case 31:
//#line 109 "complejos.y"
{ maq.code("div"); }
break;
case 32:
//#line 110 "complejos.y"
{ maq.code("powN");
                                    maq.code((Cadena) val_peek(0).obj);
                                    maq.code("pow");                                    
                                  }
break;
case 33:
//#line 115 "complejos.y"
{ maq.code("eq");}
break;
case 34:
//#line 116 "complejos.y"
{ maq.code("neq");}
break;
case 35:
//#line 117 "complejos.y"
{ maq.code("gt");}
break;
case 36:
//#line 118 "complejos.y"
{ maq.code("ge");}
break;
case 37:
//#line 119 "complejos.y"
{ maq.code("lt");}
break;
case 38:
//#line 120 "complejos.y"
{ maq.code("le");}
break;
case 39:
//#line 121 "complejos.y"
{ yyval = val_peek(1); }
break;
//#line 772 "Parser.java"
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
