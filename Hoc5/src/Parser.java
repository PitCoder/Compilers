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
public final static short VAR=258;
public final static short TERM=259;
public final static short NUM=260;
public final static short IF=261;
public final static short ELIF=262;
public final static short ELSE=263;
public final static short WHILE=264;
public final static short EQ=265;
public final static short NEQ=266;
public final static short GT=267;
public final static short LT=268;
public final static short GE=269;
public final static short LE=270;
public final static short PRINT=271;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    0,    0,    1,    3,    3,    3,    3,
    3,    3,    9,    9,    5,    4,    7,    8,    6,    2,
    2,    2,    2,    2,    2,    2,    2,    2,    2,    2,
    2,    2,    2,    2,    2,    2,    2,    2,   10,   10,
   11,   11,   12,
};
final static short yylen[] = {                            2,
    0,    2,    3,    3,    3,    3,    2,    3,    4,    4,
    7,    3,    0,    2,    3,    1,    1,    1,    0,    1,
    1,    1,    6,    4,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    1,    3,    3,    2,
    1,    2,    1,
};
final static short yydefred[] = {                         1,
    0,    0,    0,   37,   17,   16,    0,    0,    0,    2,
   13,    0,    0,    0,    0,    0,   20,    0,    0,   22,
    0,    0,   43,   40,    0,    0,    0,    3,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    4,    7,    5,    0,    0,    0,    0,    0,    8,   38,
   39,   42,   12,    0,   14,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   30,    0,   19,   19,
    0,   24,   15,    9,    0,    0,   18,    0,   23,   19,
   11,
};
final static short yydgoto[] = {                          1,
   20,   54,   14,   15,   45,   74,   16,   78,   27,   17,
   25,   26,
};
final static short yysindex[] = {                         0,
   -5,  -38,  -58,    0,    0,    0,  -15,  -15,  -87,    0,
    0,   -6,   69,   -3,  -31,  -31,    0,  -15,  -15,    0,
  134,  141,    0,    0,  -83, -246,  261,    0,  -15,  -15,
  -15,  -15,  -15,  -15,  -15,  -15,  -15,  -15,  -15,  -15,
    0,    0,    0,  -15,  282,  282,  123,  420,    0,    0,
    0,    0,    0,  152,    0,  432,  432,  432,  432,  432,
  432,  108,  108,  -80,  -80,  -80,    0,  163,    0,    0,
  -15,    0,    0,    0, -248,  179,    0,  282,    0,    0,
    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    2,    0,    0,    0,    0,    0,    0,    0,
    0,  413,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -77,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   80,    0,    0,
    0,    0,    0,    0,    0,   41,   78,   88,   94,  100,
  110,   21,   58,   11,   30,   50,    0,    0,    0,    0,
    0,    0,    0,    0,  -10,    0,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
   16,  526,  -19,    0,    3,  -69,    0,    0,    0,    0,
   -8,    0,
};
final static int YYTABLESIZE=690;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         10,
   75,   18,   19,   28,   10,   24,   43,   55,   44,   51,
   81,   21,   23,   40,   77,   41,   12,   52,   46,    0,
   28,    0,    0,    0,    8,   69,   70,    0,    0,   10,
   26,    0,    0,    0,    8,    0,    0,    0,   21,   29,
    0,    0,   21,   21,   21,   21,   21,   28,   21,    0,
   31,   28,   28,   28,   28,   28,    0,   28,   80,   27,
   21,   26,    0,   26,   26,   26,   29,   25,    0,   28,
   29,   29,   29,   29,   29,    9,   29,    0,   41,   26,
   10,   31,    0,    0,   31,    9,   27,   32,   29,    6,
   27,   27,   27,   27,   27,   21,   27,   33,   25,   31,
   25,   25,   25,   34,    0,   38,    0,    0,   27,   35,
   39,   36,   10,   35,   10,   37,   25,   11,   32,   36,
    6,   32,    0,    6,    0,    0,    0,   42,   33,    0,
    0,   33,    0,    0,   34,    0,   32,   34,    6,    0,
   35,    0,    0,   35,   38,    0,   33,    0,    0,   39,
   36,    0,   34,   36,   37,    0,    0,    0,   35,   38,
    0,    0,   40,   72,   39,   36,   71,   35,   36,   37,
   38,   23,    0,    0,    0,   39,   36,   38,   35,    0,
   37,   50,   39,   36,    0,   35,    0,   37,   38,    0,
    0,    0,   49,   39,   36,    0,   35,    0,   37,   38,
    0,   40,    0,   73,   39,   36,    0,   35,    0,   37,
   42,    0,    0,    0,    0,   38,   40,    0,    0,   79,
   39,   36,    0,   35,    0,   37,    0,   40,    0,    0,
    0,    0,    0,    0,   40,    0,    0,    0,    0,    0,
    0,    2,    3,    0,    4,   40,   10,   10,    0,   10,
   10,    2,    3,   10,    4,    5,   40,    0,    6,    0,
   10,    0,    0,    0,    0,    7,   21,   21,   21,   21,
   21,   21,   40,    0,    0,   28,   28,   28,   28,   28,
   28,    0,    0,    0,    0,   26,   26,   26,   26,   26,
   26,    0,    0,    0,   29,   29,   29,   29,   29,   29,
    8,    0,    0,    0,    0,   31,   31,   31,   31,   31,
   31,    0,    0,    0,   27,   27,   27,   27,   27,   27,
    0,    8,   25,   25,   25,   25,   25,   25,    0,    0,
    0,    0,    0,   29,   30,   31,   32,   33,   34,    0,
    0,    0,   32,   32,   32,   32,   32,   32,    0,    0,
    0,    9,   33,   33,   33,   33,   33,   33,   34,   34,
   34,   34,   34,   34,   35,   35,   35,   35,   35,   35,
    0,    0,    9,    0,   36,   36,   36,   36,   36,   36,
    0,    0,    0,   11,    0,   53,    0,   29,   30,   31,
   32,   33,   34,    0,    0,    0,    0,    0,   29,   30,
   31,   32,   33,   34,   11,   29,   30,   31,   32,   33,
   34,    0,    0,    0,    0,    0,   29,   30,   31,   32,
   33,   34,    0,    0,    0,    0,    0,   29,   30,   31,
   32,   33,   34,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   29,   30,   31,   32,   33,   34,   22,
    0,    0,    0,    0,   22,   22,   38,   22,    0,   22,
    0,   39,   36,    0,   35,    0,   37,    0,   38,    0,
    0,   22,    0,   39,   36,    0,   35,    0,   37,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   22,    0,    0,    0,
    0,    0,    0,   40,    0,    0,    0,    2,    3,    0,
    4,    5,    0,    0,    6,   40,   13,    0,    0,    0,
    0,    7,   21,   22,    0,    0,    0,    0,    2,    3,
    0,    4,    5,   47,   48,    6,    0,    0,    0,    0,
    0,    0,    7,    0,   56,   57,   58,   59,   60,   61,
   62,   63,   64,   65,   66,   67,    0,    0,    0,   68,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   76,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   22,   22,   22,
   22,   22,   22,    0,   29,   30,   31,   32,   33,   34,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         10,
   70,   40,   61,   10,   10,   93,   10,   27,   40,   93,
   80,   10,  259,   94,  263,   93,    1,   26,   16,   -1,
   10,   -1,   -1,   -1,   40,   45,   46,   -1,   -1,   40,
   10,   -1,   -1,   -1,   40,   -1,   -1,   -1,   37,   10,
   -1,   -1,   41,   42,   43,   44,   45,   37,   47,   -1,
   10,   41,   42,   43,   44,   45,   -1,   47,   78,   10,
   59,   41,   -1,   43,   44,   45,   37,   10,   -1,   59,
   41,   42,   43,   44,   45,   91,   47,   -1,   10,   59,
   91,   41,   -1,   -1,   44,   91,   37,   10,   59,   10,
   41,   42,   43,   44,   45,   94,   47,   10,   41,   59,
   43,   44,   45,   10,   -1,   37,   -1,   -1,   59,   10,
   42,   43,  123,   45,  125,   47,   59,  123,   41,   10,
   41,   44,   -1,   44,   -1,   -1,   -1,   59,   41,   -1,
   -1,   44,   -1,   -1,   41,   -1,   59,   44,   59,   -1,
   41,   -1,   -1,   44,   37,   -1,   59,   -1,   -1,   42,
   41,   -1,   59,   44,   47,   -1,   -1,   -1,   59,   37,
   -1,   -1,   94,   41,   42,   43,   44,   45,   59,   47,
   37,  259,   -1,   -1,   -1,   42,   43,   37,   45,   -1,
   47,   41,   42,   43,   -1,   45,   -1,   47,   37,   -1,
   -1,   -1,   59,   42,   43,   -1,   45,   -1,   47,   37,
   -1,   94,   -1,   41,   42,   43,   -1,   45,   -1,   47,
   59,   -1,   -1,   -1,   -1,   37,   94,   -1,   -1,   41,
   42,   43,   -1,   45,   -1,   47,   -1,   94,   -1,   -1,
   -1,   -1,   -1,   -1,   94,   -1,   -1,   -1,   -1,   -1,
   -1,  257,  258,   -1,  260,   94,  257,  258,   -1,  260,
  261,  257,  258,  264,  260,  261,   94,   -1,  264,   -1,
  271,   -1,   -1,   -1,   -1,  271,  265,  266,  267,  268,
  269,  270,   94,   -1,   -1,  265,  266,  267,  268,  269,
  270,   -1,   -1,   -1,   -1,  265,  266,  267,  268,  269,
  270,   -1,   -1,   -1,  265,  266,  267,  268,  269,  270,
   40,   -1,   -1,   -1,   -1,  265,  266,  267,  268,  269,
  270,   -1,   -1,   -1,  265,  266,  267,  268,  269,  270,
   -1,   40,  265,  266,  267,  268,  269,  270,   -1,   -1,
   -1,   -1,   -1,  265,  266,  267,  268,  269,  270,   -1,
   -1,   -1,  265,  266,  267,  268,  269,  270,   -1,   -1,
   -1,   91,  265,  266,  267,  268,  269,  270,  265,  266,
  267,  268,  269,  270,  265,  266,  267,  268,  269,  270,
   -1,   -1,   91,   -1,  265,  266,  267,  268,  269,  270,
   -1,   -1,   -1,  123,   -1,  125,   -1,  265,  266,  267,
  268,  269,  270,   -1,   -1,   -1,   -1,   -1,  265,  266,
  267,  268,  269,  270,  123,  265,  266,  267,  268,  269,
  270,   -1,   -1,   -1,   -1,   -1,  265,  266,  267,  268,
  269,  270,   -1,   -1,   -1,   -1,   -1,  265,  266,  267,
  268,  269,  270,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  265,  266,  267,  268,  269,  270,   37,
   -1,   -1,   -1,   -1,   42,   43,   37,   45,   -1,   47,
   -1,   42,   43,   -1,   45,   -1,   47,   -1,   37,   -1,
   -1,   59,   -1,   42,   43,   -1,   45,   -1,   47,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   94,   -1,   -1,   -1,
   -1,   -1,   -1,   94,   -1,   -1,   -1,  257,  258,   -1,
  260,  261,   -1,   -1,  264,   94,    1,   -1,   -1,   -1,
   -1,  271,    7,    8,   -1,   -1,   -1,   -1,  257,  258,
   -1,  260,  261,   18,   19,  264,   -1,   -1,   -1,   -1,
   -1,   -1,  271,   -1,   29,   30,   31,   32,   33,   34,
   35,   36,   37,   38,   39,   40,   -1,   -1,   -1,   44,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   71,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  265,  266,  267,
  268,  269,  270,   -1,  265,  266,  267,  268,  269,  270,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=271;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,"'\\n'",null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,"'%'",null,null,"'('","')'","'*'","'+'",
"','","'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,
"';'",null,"'='",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'['",null,"']'","'^'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,"BLTIN","VAR","TERM","NUM","IF",
"ELIF","ELSE","WHILE","EQ","NEQ","GT","LT","GE","LE","PRINT",
};
final static String yyrule[] = {
"$accept : list",
"list :",
"list : list '\\n'",
"list : list asgn '\\n'",
"list : list expr '\\n'",
"list : list stmt '\\n'",
"asgn : VAR '=' expr",
"stmt : expr ';'",
"stmt : PRINT expr ';'",
"stmt : while cond stmt end",
"stmt : if cond stmt end",
"stmt : if cond stmt end else stmt end",
"stmt : '{' stmtlist '}'",
"stmtlist :",
"stmtlist : stmtlist stmt",
"cond : '(' expr ')'",
"while : WHILE",
"if : IF",
"else : ELSE",
"end :",
"expr : poly",
"expr : VAR",
"expr : asgn",
"expr : BLTIN '(' expr ',' expr ')'",
"expr : BLTIN '(' expr ')'",
"expr : expr '+' expr",
"expr : expr '-' expr",
"expr : expr '*' expr",
"expr : expr '/' expr",
"expr : expr '%' expr",
"expr : expr '^' expr",
"expr : expr EQ expr",
"expr : expr NEQ expr",
"expr : expr GT expr",
"expr : expr LT expr",
"expr : expr GE expr",
"expr : expr LE expr",
"expr : NUM",
"expr : '(' expr ')'",
"poly : '[' terms ']'",
"poly : '[' ']'",
"terms : term",
"terms : term terms",
"term : TERM",
};

//#line 287 "SintAnalizer.y"

/***** SUPPORT CODE *****/
/*** MANDATORY USER SUPPLIED METHODS ***/

  /* Stores a reference to the lexer bject */
  //static Machine machine = new Machine();
  static public Machine machine = new Machine();
  private Yylex lexer;
  private boolean flag = false;

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

public static void main(String args[]) throws IOException {
  System.out.println("Calculadora de Polinomios");
  /* We create an instance of the class Parser */
  Parser yyparser;
  machine.initMachine();

  for(;;){
    /* Here we request an input for an expression */
    /* We call the constructor of the class Parser to which we pass as an argument the input string */
    try{
      System.out.print("Expression: ");
      yyparser = new Parser(new InputStreamReader(System.in));
      machine.newProgram();
      /* We initialize the parser */
      yyparser.yyparse();
      /* We start executing the program */
      machine.execute(0);
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }
}
//#line 435 "Parser.java"
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
//#line 33 "SintAnalizer.y"
{
        System.out.println("List with Assignment");
        machine.code("printAsignment");
        machine.code("STOP");
        return 1;
        /*machine.execute(flag);*/
      }

case 4:
//#line 40 "SintAnalizer.y"
{
        System.out.println("List with Expression");
        machine.code("printExpression");
        machine.code("STOP");
        return 1;
        /*machine.execute(flag);*/
      }

case 5:
//#line 47 "SintAnalizer.y"
{
        System.out.println("List with Statement");
        /*machine.code("printExpression");*/
        machine.code("STOP");
        return 1;
        /*machine.execute(flag);*/
        /*flag = true;*/
      }

case 6:
//#line 57 "SintAnalizer.y"
{
  System.out.println("Doing an assignation");
  int numline = machine.code("varPush");
  machine.code((InputText) val_peek(2).obj);
  machine.code("asgnVar");
  yyval = new ParserVal(val_peek(0).obj);
}
break;
case 7:
//#line 66 "SintAnalizer.y"
{ System.out.println("Expr Statement"); }
break;
case 8:
//#line 67 "SintAnalizer.y"
{
       System.out.println("Sintactic: Print Expr Statement");
       machine.code("printExpression");
}
break;
case 9:
//#line 71 "SintAnalizer.y"
{
  System.out.println("While Statement");
  machine.getProgram().setElementAt(val_peek(1).obj, (int) val_peek(3).obj + 1);
  machine.getProgram().setElementAt(val_peek(0).obj, (int) val_peek(3).obj + 2);
}
break;
case 10:
//#line 76 "SintAnalizer.y"
{
       System.out.println("Sintactic: If Statement");
       machine.getProgram().setElementAt(val_peek(1).obj, (int) val_peek(3).obj + 1);
       machine.getProgram().setElementAt(val_peek(0).obj, (int) val_peek(3).obj + 3);
}
break;
case 11:
//#line 81 "SintAnalizer.y"
{
       System.out.println("Sintactic: If-Else Statement");
       machine.getProgram().setElementAt(val_peek(4).obj, (int) val_peek(6).obj + 1);
       machine.getProgram().setElementAt(val_peek(1).obj, (int) val_peek(6).obj + 2);
       machine.getProgram().setElementAt(val_peek(0).obj, (int) val_peek(6).obj + 3);
}
break;
case 12:
//#line 95 "SintAnalizer.y"
{
  yyval = val_peek(1);
}
break;
case 13:
//#line 100 "SintAnalizer.y"
{
  System.out.println("Sintactic: Statement List");
  yyval = new ParserVal(new Integer(machine.getProgram().size()));
}
break;
case 15:
//#line 116 "SintAnalizer.y"
{
  System.out.println("Sintactic: condition detected");
  machine.code("STOP"); /*Con este stop se termina de evaluar la condicion*/
  yyval = new ParserVal(val_peek(1).obj);
}
break;
case 16:
//#line 123 "SintAnalizer.y"
{
  System.out.println("Sintactic: While statement detected");
  int numline = machine.code("whileCode");
  machine.code("STOP");
  machine.code("STOP");
  yyval = new ParserVal(new Integer(numline));
}
break;
case 17:
//#line 131 "SintAnalizer.y"
{
  System.out.println("If statement detected");
  int numline = machine.code("ifCode");
  machine.code("STOP");
  machine.code("STOP");
  machine.code("STOP");
  yyval = new ParserVal(new Integer(numline));
}
break;
case 18:
//#line 151 "SintAnalizer.y"
{
  System.out.println("Sintactic: Else statement detected");
}
break;
case 19:
//#line 155 "SintAnalizer.y"
{
  System.out.println("Sintactic: End Detected");
  machine.code("STOP");
  /*Este entero nos inidica la linea de programa donde finaliza el cuerpo de la condicion*/
  yyval = new ParserVal(new Integer(machine.getProgram().size()));
}
break;
case 21:
//#line 164 "SintAnalizer.y"
{
       System.out.println("Sintactic: Variable Detected");
       int numline = machine.code("varPush");
       machine.code((InputText) val_peek(0).obj);
       machine.code("retvarVal");
       yyval = new ParserVal(new Integer(numline));
     }
break;
case 22:
//#line 171 "SintAnalizer.y"
{
       System.out.println("Sintactic: Assignment Detected");
       /* No extra code generated */
     }
break;
case 23:
//#line 175 "SintAnalizer.y"
{
       System.out.print("Built-in with 2 parameters Detected");
       machine.code("numParam");
       machine.code(new InputText("2"));
       machine.code("builtinPush");
       machine.code((InputText) val_peek(5).obj);
       machine.code("builtin");
     }
break;
case 24:
//#line 183 "SintAnalizer.y"
{
       System.out.print("Built-in with 1 parameter Detected");
       machine.code("numParam");
       machine.code(new InputText("1"));
       machine.code("builtinPush");
       machine.code((InputText) val_peek(3).obj);
       machine.code("builtin");
     }
break;
case 25:
//#line 193 "SintAnalizer.y"
{
       System.out.println("Sintactic: Addition Detected");
       machine.code("addition");
     }
break;
case 26:
//#line 197 "SintAnalizer.y"
{
       System.out.println("Sintactic: Substraction Detected");
       machine.code("substraction");
     }
break;
case 27:
//#line 201 "SintAnalizer.y"
{
       System.out.println("Sintactic: Multiplication Detected");
       machine.code("multiplication");
     }
break;
case 28:
//#line 205 "SintAnalizer.y"
{
       System.out.println("Sintactic: Division Detected");
       machine.code("division");
     }
break;
case 29:
//#line 209 "SintAnalizer.y"
{
       System.out.println("Sintactic: Division Detected");
       machine.code("module");
     }
break;
case 30:
//#line 213 "SintAnalizer.y"
{
       System.out.println("Sintactic: Power Detected");
       machine.code("power");
     }
break;
case 31:
//#line 219 "SintAnalizer.y"
{
       System.out.println("Sintactic: Equal Operator Detected");
       machine.code("equal");
     }
break;
case 32:
//#line 223 "SintAnalizer.y"
{
       System.out.println("Sintactic: Not equal Operator Detected");
       machine.code("distinct");
     }
break;
case 33:
//#line 227 "SintAnalizer.y"
{
       System.out.println("Sintactic: Greater than Operator Detected");
       machine.code("greater");
     }
break;
case 34:
//#line 231 "SintAnalizer.y"
{
       System.out.println("Sintactic: Lesser than Operator Detected");
       machine.code("lesser");
     }
break;
case 35:
//#line 235 "SintAnalizer.y"
{
       System.out.println("Sintactic: Greater or Equal Operator Detected");
       machine.code("greaterEq");
     }
break;
case 36:
//#line 239 "SintAnalizer.y"
{
       System.out.println("Sintactic: Lesser or Equal Operator Detected");
       machine.code("lesserEq");
     }
break;
case 37:
//#line 245 "SintAnalizer.y"
{
       System.out.println("Sintactic: Number detected");
       int numline = machine.code("number");
       machine.code((InputText) val_peek(0).obj);
       yyval = new ParserVal(new Integer(numline));
       /* No extra code generated */
     }
break;
case 38:
//#line 252 "SintAnalizer.y"
{
       System.out.println("Nesting Detected");
       yyval = val_peek(1);
     }
break;
case 39:
//#line 258 "SintAnalizer.y"
{
  System.out.println("Sintactic:Polynomial with terms detected");
  int numline = machine.code("poly");
  yyval = new ParserVal(new Integer(numline));
}
break;
case 40:
//#line 263 "SintAnalizer.y"
{
  System.out.println("Sintactic: Empty polynomial detected");
  machine.code("poly");
}
break;
case 41:
//#line 269 "SintAnalizer.y"
{
  System.out.println("Sintactic: Creation List of terms Detected");
  machine.code("createLT");
}
break;
case 42:
//#line 273 "SintAnalizer.y"
{
  System.out.println("Sintactic: List of terms Detected");
  machine.code("addtoLT");
}
break;
case 43:
//#line 279 "SintAnalizer.y"
{
  System.out.println("Sintactic: Term Detected");
  Term t = (Term) val_peek(0).obj;
  machine.code("term");
  machine.code(t);
}
break;
//#line 896 "Parser.java"
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
