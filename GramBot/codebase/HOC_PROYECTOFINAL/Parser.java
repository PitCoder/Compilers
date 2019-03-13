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






//#line 4 "hoc6.y"

import java.lang.Math;
import java.io.*;
import java.util.StringTokenizer;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.nio.charset.StandardCharsets;
//#line 27 "Parser.java"




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
public final static short FUNC=263;
public final static short PROC=264;
public final static short RETURN=265;
public final static short EQ=266;
public final static short NEQ=267;
public final static short GT=268;
public final static short GE=269;
public final static short LT=270;
public final static short LE=271;
public final static short FUNCTION=272;
public final static short PROCEDURE=273;
public final static short ARG=274;
public final static short NUMBER=275;
public final static short VAR=276;
public final static short VARCRD=277;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    0,    0,    0,    0,    0,    2,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
   11,   10,   13,   14,   15,   16,   12,   17,   17,   17,
    1,    1,    7,    7,    7,    9,    9,    9,    8,    4,
    4,    4,    4,    4,    4,    4,    4,    4,    4,    4,
    4,    4,    4,    4,    4,    4,    4,    5,   18,   18,
   18,    6,    6,    6,   20,   20,   20,   19,   19,   19,
   19,   19,   19,   19,   19,   19,   19,
};
final static short yylen[] = {                            2,
    0,    2,    3,    3,    3,    3,    3,    3,    3,    1,
    2,    5,    2,    1,    1,    4,    4,    7,   10,    3,
    3,    1,    1,    1,    1,    1,    0,    0,    2,    2,
    5,    5,    1,    1,    1,    0,    1,    3,    0,    1,
    1,    1,    5,    1,    4,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    2,    1,
    1,    3,    3,    1,    1,    2,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         1,
    0,    0,   23,   22,   24,    0,    0,    0,    0,   34,
   35,   42,   40,    0,    0,    2,    0,   28,   65,    0,
    0,    0,    0,    0,    0,   39,    0,    0,    0,    0,
    0,   44,    0,   39,   33,    0,    0,    0,    0,    0,
    0,    0,    3,    4,    5,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    6,    0,    7,    0,    0,
    8,    0,    0,    0,    0,    0,   68,   69,   70,   71,
   72,   73,   74,   75,   76,   77,   66,    0,    0,    0,
    0,    0,   67,    0,    0,    0,   51,   29,   20,   30,
    0,   14,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   63,   62,    0,    0,   27,   27,
    0,    0,   45,    0,    0,    0,   59,    0,    0,   21,
   16,    0,    0,    0,   32,   31,    0,    0,    0,    0,
   43,    0,   27,    0,   18,    0,    0,    0,   27,   19,
};
final static short yydgoto[] = {                          1,
   20,   32,   22,   91,   92,   93,   34,   62,  119,   27,
   64,  121,   28,   29,  112,  137,   42,   86,   77,   30,
};
final static short yysindex[] = {                         0,
   69,  -36,    0,    0,    0,   27, -261, -261,   27,    0,
    0,    0,    0,  -45,  -21,    0,   27,    0,    0,  -17,
   28,   33,  146,   37,   -4,    0,   10,   10,   13,  477,
   27,    0,  305,    0,    0,   16,   21,  305,   27,  -98,
  519, 1089,    0,    0,    0,   27,   27,   27,   27,   27,
   27,   27,   27,   27,   27,    0,   27,    0,  -97,  -97,
    0,   26,   27,  -31,  -31,   27,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  725,   29,   31,
   32,  305,    0,    0,  -35,  477,    0,    0,    0,    0,
  305,    0,  -35,  305,  305,  305,  305,  305,  305,  115,
  115,  -70,  -70,  305,    0,    0,   27,  896,    0,    0,
  305,   15,    0,   27,  -31,  -31,    0,  305,  -24,    0,
    0, -184,   10,  -23,    0,    0,    0,   27,  -31,   18,
    0,  305,    0,   27,    0,  305,   39,  -31,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -10,   17,    0,    0,    0,    0,    0,
  609,    0,    0,    0,    0,    0,    0,    0,    0,  482,
    0,    0,  851,    0,    0,    0,    0,  883,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  385,    0,   48,  277,  925,    0,    0,    0,    0,
  948,    0,  971,  549,  576,  639,  740,  761,  803,  432,
  457,  173,  364,  830,    0,    0,  -22,    0,    0,    0,
   19,    0,    0,  -22,    0,    0,    0,  -16,    0,    0,
    0, 1112,    0,    0,    0,    0,   96,    0,    0,    0,
    0,  -15,    0,    0,    0,   40,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   86,  -19, 1264,   89,    4,    6,   60,  -14,    0,
  -25,  -74,    0,    0,    0,    0,    0,    0,    9,    0,
};
final static int YYTABLESIZE=1398;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         41,
   84,   19,   65,   31,   25,   61,   26,   60,   17,   59,
   10,   11,   36,   37,   35,   39,  127,  131,   36,  128,
  128,   36,   90,   57,   37,   38,   67,   37,   38,   33,
   41,   41,   41,   41,   41,  122,   41,   44,   60,   40,
   59,   43,   45,   85,  109,  110,   58,   26,   41,   63,
   67,   67,   66,   67,   67,   80,   67,   60,  135,   67,
   81,   67,  105,  106,  140,  107,   17,   19,  114,   26,
   26,  115,  116,  123,  129,   67,  134,   25,   16,  138,
   26,   60,   60,   41,   60,   60,   21,   60,   41,   24,
   65,   18,   65,   79,  117,  125,  126,  130,    0,  124,
    0,    0,    0,    0,    0,   12,   60,    0,   17,  133,
    0,    0,   41,    0,   41,   67,   67,   67,  139,    0,
   26,   26,    0,    0,    0,    0,   67,   67,    0,    0,
    0,   67,    0,   67,   26,   12,    0,   43,   43,   67,
   43,   67,   43,   26,    0,    0,   60,   60,   60,    0,
    0,    0,    0,    0,   12,   56,   54,   60,   60,    0,
    0,   55,   60,    0,   60,    0,    0,   19,    0,    0,
   60,    0,   60,    0,    0,    0,    0,    0,   83,   83,
    0,    0,   48,    0,    0,    0,    0,   54,   53,   43,
   52,   18,   55,    0,   12,   46,   47,   48,   49,   50,
   51,    0,    0,    0,    0,    0,    0,    0,   57,    0,
    0,    0,   48,   48,   48,   48,   48,   48,   12,   48,
   12,    0,    0,    0,    0,    2,    3,    0,    4,    5,
    6,   48,    0,    9,    0,    0,    0,    0,    0,   57,
   10,   11,   12,   13,   14,   15,   41,   41,   41,   41,
   41,   41,    0,    0,   41,   41,   41,   41,   41,   41,
   41,   41,   41,   41,   41,   41,   41,    0,    0,    0,
    0,   48,    0,   67,   67,   67,   67,   67,   67,    0,
    0,   67,    0,    2,    0,    0,   61,    0,   67,   67,
   67,   67,   67,   67,    0,   48,    0,   48,   10,   11,
   12,   13,   14,    0,   60,   60,   60,   60,   60,   60,
   61,   61,   60,   61,   61,    0,   61,    0,    0,   60,
   60,   60,   60,   60,   60,    2,    3,    0,    4,    5,
    6,    7,    8,    9,    0,   61,    0,    0,    0,    0,
   10,   11,   12,   13,   14,   15,   54,   53,    0,   52,
    0,   55,   12,   12,   12,   12,   12,   12,    0,    0,
   12,   43,   43,   43,   43,   43,   43,   12,   12,   12,
   12,   12,   12,   49,    0,   61,   61,   61,    0,    0,
   46,   47,   48,   49,   50,   51,   61,   61,    0,    0,
    0,   61,    0,   61,    9,    0,    0,    0,   57,   61,
    0,   61,    0,   49,   49,   49,   49,   49,   49,    0,
   49,   46,   47,   48,   49,   50,   51,    0,    0,    0,
    0,    0,   49,    0,    9,    9,    0,    0,    9,   48,
   48,   48,   48,   48,   48,    0,    0,   48,    0,    0,
    0,   47,    0,    9,   48,   48,   48,   48,   48,   48,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   49,    0,    0,    0,   46,    0,    0,    0,
    0,   47,   47,    0,   47,   47,   47,    0,    0,    0,
    0,    0,    0,    9,    0,    0,   49,    0,   49,    0,
   47,   64,    0,    0,    0,    0,   46,   46,    0,   46,
   46,   46,    0,    0,    0,    0,    0,    9,    0,    9,
   76,   75,    0,   74,   73,   46,    0,    0,    0,    0,
    0,   64,    0,    0,   64,    0,   64,    0,    0,    0,
   47,    0,    0,   61,   61,   61,   61,   61,   61,    0,
   64,   61,    0,    0,    0,    0,    0,    0,   61,   61,
   61,   61,   61,   61,   47,   46,   47,    0,   52,   87,
   54,   53,    0,   52,    0,   55,    0,    0,    0,    0,
   46,   47,   48,   49,   50,   51,   72,   67,    0,   46,
   64,   46,    0,    0,    0,   53,   68,   69,   52,   52,
    0,   70,   52,   71,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   64,    0,   64,   52,    0,    0,
    0,    0,   57,    0,    0,   53,   53,    0,    0,   53,
   49,   49,   49,   49,   49,   49,    0,    0,   49,    0,
    0,    0,    0,    0,   53,   49,   49,   49,   49,   49,
   49,    9,    9,    9,    9,    9,    9,   52,   54,    9,
   44,   44,    0,   44,    0,   44,    9,    9,    9,    9,
    9,    9,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   52,    0,   52,   53,    0,    0,    0,   54,   54,
    0,    0,   54,    0,    0,    0,    0,    0,   47,   47,
   47,   47,   47,   47,    0,    0,   47,   54,   53,    0,
   53,    0,   44,   47,   47,   47,   47,   47,   47,    0,
    0,    0,    0,   46,   46,   46,   46,   46,   46,    0,
    0,   46,    0,    0,    0,    0,    0,    0,   46,   46,
   46,   46,   46,   46,    0,    0,    0,   54,   64,   64,
   64,   64,   64,   64,    0,    0,   64,    0,    0,   55,
    0,    0,    0,   64,   64,   64,   64,   64,   64,    0,
    0,   54,    0,   54,    0,  113,   54,   53,    0,   52,
   56,   55,    0,    0,    0,    0,    0,    0,    0,   55,
   55,    0,    0,   55,   46,   47,   48,   49,   50,   51,
    0,    0,    0,    0,    0,    0,    0,    0,   55,    0,
   56,   56,    0,    0,   56,   52,   52,   52,   52,   52,
   52,    0,   57,   52,    0,    0,    0,    0,   57,   56,
   52,   52,   52,   52,   52,   52,    0,    0,    0,    0,
    0,    0,   53,   53,   53,   53,   53,   53,   55,   50,
   53,    0,   57,   57,    0,    0,   57,   53,   53,   53,
   53,   53,   53,    0,    0,    0,    0,    0,    0,   56,
   13,   57,   55,    0,   55,    0,    0,    0,    0,   50,
   50,    0,    0,   50,   44,   44,   44,   44,   44,   44,
    0,    0,    0,   56,    0,   56,    0,    0,   50,    0,
   13,    0,   11,    0,    0,   54,   54,   54,   54,   54,
   54,   57,    0,   54,    0,    0,    0,    0,    0,   13,
   54,   54,   54,   54,   54,   54,    0,    0,    0,    0,
    0,    0,   11,    0,    0,   57,    0,   57,   50,    0,
    0,    0,    0,    0,   58,    0,  120,   54,   53,    0,
   52,   11,   55,    0,    0,    0,    0,    0,    0,   13,
    0,    0,   50,    0,   50,    0,    0,   10,    0,    0,
    0,    0,    0,    0,   58,    0,    0,    0,    0,    0,
    0,    0,    0,   13,    0,   13,    0,    0,    0,    0,
   15,   11,    0,   58,    0,    0,    0,   10,    0,   57,
   46,   47,   48,   49,   50,   51,   55,   55,   55,   55,
   55,   55,    0,    0,   55,   11,   10,   11,    0,    0,
   15,   55,   55,   55,   55,   55,   55,   56,   56,   56,
   56,   56,   56,   58,    0,   56,    0,    0,    0,   15,
    0,    0,   56,   56,   56,   56,   56,   56,    0,    0,
    0,    0,    0,    0,    0,    0,   10,   58,    0,   58,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   57,
   57,   57,   57,   57,   57,    0,    0,   57,    0,   15,
   10,    0,   10,    0,   57,   57,   57,   57,   57,   57,
    0,    0,    0,    0,    0,    0,   50,   50,   50,   50,
   50,   50,    0,   15,   50,   15,    0,    0,   88,    0,
    0,   50,   50,   50,   50,   50,   50,   13,   13,   13,
   13,   13,   13,    0,    0,   13,    0,    0,    0,    0,
    0,   17,   13,   13,   13,   13,   13,   13,   17,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   11,
   11,   11,   11,   11,   11,    0,    0,   11,    0,    0,
    0,   17,    0,    0,   11,   11,   11,   11,   11,   11,
    0,   46,   47,   48,   49,   50,   51,    0,    0,    0,
   17,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   58,   58,   58,   58,   58,   58,   19,    0,   58,
    0,    0,    0,    0,    0,    0,   58,   58,   58,   58,
   58,   58,    0,    0,   10,   10,   10,   10,   10,   10,
   17,   18,   10,   89,    0,    0,    0,    0,    0,   10,
   10,   10,   10,   10,   10,    0,    0,   15,   15,   15,
   15,   15,   15,    0,   17,   15,   17,    0,    0,    0,
    0,    0,   15,   15,   15,   15,   15,   15,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   23,    0,    0,    0,    0,   33,
    0,    0,   38,    0,    0,    0,    0,    0,    0,    0,
   41,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   78,    0,    0,    0,    0,    0,
    0,    0,   82,    0,    0,    0,    0,    0,    0,   94,
   95,   96,   97,   98,   99,  100,  101,  102,  103,    0,
  104,    0,    0,    0,    0,    0,  108,    0,    0,  111,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    2,    3,    0,    4,    5,
    6,    0,    0,    9,    0,    0,    0,    0,    0,    0,
   10,   11,   12,   13,   14,   15,    0,    0,   17,   17,
  118,   17,   17,   17,    0,    0,   17,  118,    0,    0,
    0,    0,    0,   17,   17,   17,   17,   17,   17,    0,
    0,  132,    0,    0,    0,    0,    0,  136,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         10,
   99,   99,   28,   40,    1,   10,    1,   43,   40,   45,
  272,  273,    7,    8,  276,   61,   41,   41,   41,   44,
   44,   44,   42,   94,   41,   41,   10,   44,   44,   40,
   41,   42,   43,   44,   45,  110,   47,   10,   43,   61,
   45,   59,   10,   40,   64,   65,   10,   42,   59,   40,
   34,   35,   40,   37,   38,   40,   40,   10,  133,   43,
   40,   45,   59,   60,  139,   40,   40,   99,   40,   64,
   65,   41,   41,   59,  259,   59,   59,   59,   10,   41,
   41,   34,   35,   94,   37,   38,    1,   40,   99,    1,
   43,  123,   45,   34,   86,  115,  116,  123,   -1,  114,
   -1,   -1,   -1,   -1,   -1,   10,   59,   -1,   40,  129,
   -1,   -1,  123,   -1,  125,   99,  100,  101,  138,   -1,
  115,  116,   -1,   -1,   -1,   -1,  110,  111,   -1,   -1,
   -1,  115,   -1,  117,  129,   40,   -1,   42,   43,  123,
   45,  125,   47,  138,   -1,   -1,   99,  100,  101,   -1,
   -1,   -1,   -1,   -1,   59,   10,   42,  110,  111,   -1,
   -1,   47,  115,   -1,  117,   -1,   -1,   99,   -1,   -1,
  123,   -1,  125,   -1,   -1,   -1,   -1,   -1,  277,  277,
   -1,   -1,   10,   -1,   -1,   -1,   -1,   42,   43,   94,
   45,  123,   47,   -1,   99,  266,  267,  268,  269,  270,
  271,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   94,   -1,
   -1,   -1,   40,   41,   42,   43,   44,   45,  123,   47,
  125,   -1,   -1,   -1,   -1,  257,  258,   -1,  260,  261,
  262,   59,   -1,  265,   -1,   -1,   -1,   -1,   -1,   94,
  272,  273,  274,  275,  276,  277,  257,  258,  259,  260,
  261,  262,   -1,   -1,  265,  266,  267,  268,  269,  270,
  271,  272,  273,  274,  275,  276,  277,   -1,   -1,   -1,
   -1,   99,   -1,  257,  258,  259,  260,  261,  262,   -1,
   -1,  265,   -1,  257,   -1,   -1,   10,   -1,  272,  273,
  274,  275,  276,  277,   -1,  123,   -1,  125,  272,  273,
  274,  275,  276,   -1,  257,  258,  259,  260,  261,  262,
   34,   35,  265,   37,   38,   -1,   40,   -1,   -1,  272,
  273,  274,  275,  276,  277,  257,  258,   -1,  260,  261,
  262,  263,  264,  265,   -1,   59,   -1,   -1,   -1,   -1,
  272,  273,  274,  275,  276,  277,   42,   43,   -1,   45,
   -1,   47,  257,  258,  259,  260,  261,  262,   -1,   -1,
  265,  266,  267,  268,  269,  270,  271,  272,  273,  274,
  275,  276,  277,   10,   -1,   99,  100,  101,   -1,   -1,
  266,  267,  268,  269,  270,  271,  110,  111,   -1,   -1,
   -1,  115,   -1,  117,   10,   -1,   -1,   -1,   94,  123,
   -1,  125,   -1,   40,   41,   42,   43,   44,   45,   -1,
   47,  266,  267,  268,  269,  270,  271,   -1,   -1,   -1,
   -1,   -1,   59,   -1,   40,   41,   -1,   -1,   44,  257,
  258,  259,  260,  261,  262,   -1,   -1,  265,   -1,   -1,
   -1,   10,   -1,   59,  272,  273,  274,  275,  276,  277,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   99,   -1,   -1,   -1,   10,   -1,   -1,   -1,
   -1,   40,   41,   -1,   43,   44,   45,   -1,   -1,   -1,
   -1,   -1,   -1,   99,   -1,   -1,  123,   -1,  125,   -1,
   59,   10,   -1,   -1,   -1,   -1,   40,   41,   -1,   43,
   44,   45,   -1,   -1,   -1,   -1,   -1,  123,   -1,  125,
   34,   35,   -1,   37,   38,   59,   -1,   -1,   -1,   -1,
   -1,   40,   -1,   -1,   43,   -1,   45,   -1,   -1,   -1,
   99,   -1,   -1,  257,  258,  259,  260,  261,  262,   -1,
   59,  265,   -1,   -1,   -1,   -1,   -1,   -1,  272,  273,
  274,  275,  276,  277,  123,   99,  125,   -1,   10,   41,
   42,   43,   -1,   45,   -1,   47,   -1,   -1,   -1,   -1,
  266,  267,  268,  269,  270,  271,  100,  101,   -1,  123,
   99,  125,   -1,   -1,   -1,   10,  110,  111,   40,   41,
   -1,  115,   44,  117,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  123,   -1,  125,   59,   -1,   -1,
   -1,   -1,   94,   -1,   -1,   40,   41,   -1,   -1,   44,
  257,  258,  259,  260,  261,  262,   -1,   -1,  265,   -1,
   -1,   -1,   -1,   -1,   59,  272,  273,  274,  275,  276,
  277,  257,  258,  259,  260,  261,  262,   99,   10,  265,
   42,   43,   -1,   45,   -1,   47,  272,  273,  274,  275,
  276,  277,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  123,   -1,  125,   99,   -1,   -1,   -1,   40,   41,
   -1,   -1,   44,   -1,   -1,   -1,   -1,   -1,  257,  258,
  259,  260,  261,  262,   -1,   -1,  265,   59,  123,   -1,
  125,   -1,   94,  272,  273,  274,  275,  276,  277,   -1,
   -1,   -1,   -1,  257,  258,  259,  260,  261,  262,   -1,
   -1,  265,   -1,   -1,   -1,   -1,   -1,   -1,  272,  273,
  274,  275,  276,  277,   -1,   -1,   -1,   99,  257,  258,
  259,  260,  261,  262,   -1,   -1,  265,   -1,   -1,   10,
   -1,   -1,   -1,  272,  273,  274,  275,  276,  277,   -1,
   -1,  123,   -1,  125,   -1,   41,   42,   43,   -1,   45,
   10,   47,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   40,
   41,   -1,   -1,   44,  266,  267,  268,  269,  270,  271,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   59,   -1,
   40,   41,   -1,   -1,   44,  257,  258,  259,  260,  261,
  262,   -1,   10,  265,   -1,   -1,   -1,   -1,   94,   59,
  272,  273,  274,  275,  276,  277,   -1,   -1,   -1,   -1,
   -1,   -1,  257,  258,  259,  260,  261,  262,   99,   10,
  265,   -1,   40,   41,   -1,   -1,   44,  272,  273,  274,
  275,  276,  277,   -1,   -1,   -1,   -1,   -1,   -1,   99,
   10,   59,  123,   -1,  125,   -1,   -1,   -1,   -1,   40,
   41,   -1,   -1,   44,  266,  267,  268,  269,  270,  271,
   -1,   -1,   -1,  123,   -1,  125,   -1,   -1,   59,   -1,
   40,   -1,   10,   -1,   -1,  257,  258,  259,  260,  261,
  262,   99,   -1,  265,   -1,   -1,   -1,   -1,   -1,   59,
  272,  273,  274,  275,  276,  277,   -1,   -1,   -1,   -1,
   -1,   -1,   40,   -1,   -1,  123,   -1,  125,   99,   -1,
   -1,   -1,   -1,   -1,   10,   -1,   41,   42,   43,   -1,
   45,   59,   47,   -1,   -1,   -1,   -1,   -1,   -1,   99,
   -1,   -1,  123,   -1,  125,   -1,   -1,   10,   -1,   -1,
   -1,   -1,   -1,   -1,   40,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  123,   -1,  125,   -1,   -1,   -1,   -1,
   10,   99,   -1,   59,   -1,   -1,   -1,   40,   -1,   94,
  266,  267,  268,  269,  270,  271,  257,  258,  259,  260,
  261,  262,   -1,   -1,  265,  123,   59,  125,   -1,   -1,
   40,  272,  273,  274,  275,  276,  277,  257,  258,  259,
  260,  261,  262,   99,   -1,  265,   -1,   -1,   -1,   59,
   -1,   -1,  272,  273,  274,  275,  276,  277,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   99,  123,   -1,  125,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,
  258,  259,  260,  261,  262,   -1,   -1,  265,   -1,   99,
  123,   -1,  125,   -1,  272,  273,  274,  275,  276,  277,
   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,  259,  260,
  261,  262,   -1,  123,  265,  125,   -1,   -1,   10,   -1,
   -1,  272,  273,  274,  275,  276,  277,  257,  258,  259,
  260,  261,  262,   -1,   -1,  265,   -1,   -1,   -1,   -1,
   -1,   10,  272,  273,  274,  275,  276,  277,   40,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,
  258,  259,  260,  261,  262,   -1,   -1,  265,   -1,   -1,
   -1,   40,   -1,   -1,  272,  273,  274,  275,  276,  277,
   -1,  266,  267,  268,  269,  270,  271,   -1,   -1,   -1,
   59,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  257,  258,  259,  260,  261,  262,   99,   -1,  265,
   -1,   -1,   -1,   -1,   -1,   -1,  272,  273,  274,  275,
  276,  277,   -1,   -1,  257,  258,  259,  260,  261,  262,
   99,  123,  265,  125,   -1,   -1,   -1,   -1,   -1,  272,
  273,  274,  275,  276,  277,   -1,   -1,  257,  258,  259,
  260,  261,  262,   -1,  123,  265,  125,   -1,   -1,   -1,
   -1,   -1,  272,  273,  274,  275,  276,  277,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,    1,   -1,   -1,   -1,   -1,    6,
   -1,   -1,    9,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   17,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   31,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   39,   -1,   -1,   -1,   -1,   -1,   -1,   46,
   47,   48,   49,   50,   51,   52,   53,   54,   55,   -1,
   57,   -1,   -1,   -1,   -1,   -1,   63,   -1,   -1,   66,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  257,  258,   -1,  260,  261,
  262,   -1,   -1,  265,   -1,   -1,   -1,   -1,   -1,   -1,
  272,  273,  274,  275,  276,  277,   -1,   -1,  257,  258,
  107,  260,  261,  262,   -1,   -1,  265,  114,   -1,   -1,
   -1,   -1,   -1,  272,  273,  274,  275,  276,  277,   -1,
   -1,  128,   -1,   -1,   -1,   -1,   -1,  134,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=277;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,"'\\n'",null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,"'\"'","'#'",null,"'%'","'&'",null,"'('","')'","'*'",
"'+'","','","'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,
null,"';'",null,"'='",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,"'^'",null,null,null,null,"'c'","'d'","'e'",null,null,
null,null,null,null,null,null,"'n'","'o'",null,null,null,"'s'",null,"'u'",null,
null,null,null,null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,"BLTIN","IF","ELSE",
"WHILE","FOR","PRINT","FUNC","PROC","RETURN","EQ","NEQ","GT","GE","LT","LE",
"FUNCTION","PROCEDURE","ARG","NUMBER","VAR","VARCRD",
};
final static String yyrule[] = {
"$accept : list",
"list :",
"list : list '\\n'",
"list : list defn ';'",
"list : list asgn '\\n'",
"list : list stmt '\\n'",
"list : list exp '\\n'",
"list : list asigncoord '\\n'",
"list : list expcoord '\\n'",
"asgn : VAR '=' exp",
"stmt : exp",
"stmt : RETURN exp",
"stmt : procname begin '(' arglist ')'",
"stmt : PRINT exp",
"stmt : asigncoord",
"stmt : expcoord",
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
"defn : PROC procname '(' ')' stmt",
"defn : FUNC procname '(' ')' stmt",
"procname : VAR",
"procname : FUNCTION",
"procname : PROCEDURE",
"arglist :",
"arglist : exp",
"arglist : arglist ',' exp",
"begin :",
"exp : NUMBER",
"exp : VAR",
"exp : ARG",
"exp : procname begin '(' arglist ')'",
"exp : asgn",
"exp : BLTIN '(' exp ')'",
"exp : exp '+' exp",
"exp : exp '-' exp",
"exp : exp '*' exp",
"exp : exp '/' exp",
"exp : exp '^' exp",
"exp : '(' exp ')'",
"exp : exp EQ exp",
"exp : exp NEQ exp",
"exp : exp GT exp",
"exp : exp GE exp",
"exp : exp LT exp",
"exp : exp LE exp",
"asigncoord : VARCRD '=' binstr",
"binstr : binstr instr",
"binstr : 'c'",
"binstr : expcoord",
"expcoord : expcoord '+' expcoord",
"expcoord : expcoord '-' expcoord",
"expcoord : sec",
"sec : 'c'",
"sec : sec instr",
"sec : VARCRD",
"instr : 'e'",
"instr : 'n'",
"instr : 'o'",
"instr : 's'",
"instr : 'u'",
"instr : 'd'",
"instr : '&'",
"instr : '%'",
"instr : '#'",
"instr : '\"'",
};

//#line 254 "hoc6.y"


void defnonly(String s){
	if(!indef)
		System.out.println(s + " usado fuera de la definicion");
}
/*código de soporte*/
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
  boolean indef = true;
  
  public static void main(String args[]) throws IOException {   
	  UIManager UI=new UIManager();
      //UIManager.setInstalledLookAndFeels(new LookAndFeelInfo[0]);
      UI.put("OptionPane.background", new Color(253, 254, 254));
      UI.put("Panel.background", new Color(235, 237, 239));
      Font font = new Font(Font.MONOSPACED, Font.PLAIN, 12);
      Font font2 = new Font(Font.MONOSPACED, Font.PLAIN, 16);
      UIManager.put("OptionPane.messageFont", font);
      UIManager.put("OptionPane.buttonFont", font2);
      UIManager.put("OptionPane.setButtonMargin", false);
      ImageIcon iconoVentana = new ImageIcon("/home/figue/Desktop/HOC_PROYECTOFINAL/icono1.png");  
	  
	  
	  System.out.println("HOC PROYECTO FINAL 'LOGO'");

      Parser yyparser;
      maq.initCode();
          
	  while (true) {
      	System.out.print("Expression: ");
      	maq.newProgram();
		String resp = (String) JOptionPane.showInputDialog(null, "<html><h2>Ingresa el código: </h2></html>", "ROBOT CON LENGUAJE PROPIO", 0, iconoVentana, null, null);
      	if (resp==null) {
      	   resp = "";
      	}else if(resp.equals("")){
           resp = "";
      	}else{
           System.out.println(resp);
      	}
		resp=resp+"\n";
		InputStream read = new ByteArrayInputStream(resp.getBytes(StandardCharsets.UTF_8));
        
      	yyparser = new Parser(new InputStreamReader(read));        
      	yyparser.yyparse();
      
      	System.out.println("ProgBase(): " + maq.getProgBase());
      
      	String elementProgBase = maq.getElementProgBase();
      	int offset = 0;
      
      	if (elementProgBase.equals("STOP"))
        	offset++;
      
      	maq.execute(maq.getProgBase() + offset);
    	}
  }
//#line 674 "Parser.java"
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
//#line 39 "hoc6.y"
{ maq.code("STOP"); System.out.println("function definition");}
break;
case 4:
//#line 40 "hoc6.y"
{ maq.code("STOP"); System.out.println("asignacion normal"); return 1; }
//break;
case 5:
//#line 41 "hoc6.y"
{ maq.code("STOP"); System.out.println("este es un statement"); return 1;}
//break;
case 6:
//#line 42 "hoc6.y"
{ maq.code("printNumber"); System.out.println("expresion de numero"); maq.code("STOP"); return 1;}
//break;
case 7:
//#line 43 "hoc6.y"
{	maq.banderaVar=true;	
						 	maq.code("printVar"); 
							maq.code("STOP"); 
	   						System.out.println("este es un asigncoord");
	   						return 1;
						 }
//break;
case 8:
//#line 49 "hoc6.y"
{	maq.banderaVar=false;
							maq.code("imprime");	
						    maq.code("STOP");
							maq.restablecerBandera();
							System.out.println("este es un expcoord");
							return 1;
						}
//break;
case 9:
//#line 58 "hoc6.y"
{ int numI = maq.code("varPush");
                      maq.code((Cadena) val_peek(2).obj);
                      maq.code("asgVar");                       
                      yyval = new ParserVal(val_peek(0).obj);
                    }
break;
case 10:
//#line 65 "hoc6.y"
{ maq.code("pop"); }
break;
case 11:
//#line 66 "hoc6.y"
{ defnonly("return"); yyval = val_peek(0); maq.code("funcret") ; }
break;
case 12:
//#line 67 "hoc6.y"
{ yyval = val_peek(3); 
                                          maq.code("call"); 
                                          Cadena c = (Cadena) val_peek(4).obj;
                                          Symbol s = new Symbol(c.getCadena(), "", (short) 2, null);
                                          maq.code(s); 
                                          maq.code(val_peek(1).ival + ""); 
        }
break;
case 13:
//#line 74 "hoc6.y"
{ maq.code("printNumber"); 
                    yyval = new ParserVal(val_peek(0).obj); /*dice la direccion a redirirgir (los STOP)
*/
                  }
break;
case 14:
//#line 78 "hoc6.y"
{	System.out.println("nabla");
						maq.banderaVar=true;	
						maq.code("printVar"); 
						maq.code("STOP"); 
						 }
break;
case 15:
//#line 83 "hoc6.y"
{	System.out.println("nabla");
						maq.banderaVar=false;
						maq.code("imprime");	
					    maq.code("STOP");
						maq.restablecerBandera();
						}
break;
case 16:
//#line 89 "hoc6.y"
{ maq.getProg().setElementAt(val_peek(1).obj, (int) val_peek(3).obj + 1);
                              maq.getProg().setElementAt(val_peek(0).obj, (int) val_peek(3).obj + 2); 
                            }
break;
case 17:
//#line 92 "hoc6.y"
{ maq.getProg().setElementAt(val_peek(1).obj, (int) val_peek(3).obj + 1); 
                              maq.getProg().setElementAt(val_peek(0).obj, (int) val_peek(3).obj + 3); 
                            }
break;
case 18:
//#line 95 "hoc6.y"
{  maq.getProg().setElementAt(val_peek(4).obj, (int) val_peek(6).obj + 1);
                                          maq.getProg().setElementAt(val_peek(1).obj, (int) val_peek(6).obj + 2); 
                                          maq.getProg().setElementAt(val_peek(0).obj, (int) val_peek(6).obj + 3);
	                                     }
break;
case 19:
//#line 99 "hoc6.y"
{  maq.getProg().setElementAt(val_peek(7).obj, (int) val_peek(9).obj + 1);
                                                        maq.getProg().setElementAt(val_peek(5).obj, (int) val_peek(9).obj + 2); 
                                                        maq.getProg().setElementAt(val_peek(3).obj, (int) val_peek(9).obj + 3);
                                                        maq.getProg().setElementAt(val_peek(1).obj, (int) val_peek(9).obj + 4);
                                                        maq.getProg().setElementAt(val_peek(0).obj, (int) val_peek(9).obj + 5);
                                                     }
break;
case 20:
//#line 105 "hoc6.y"
{yyval  =  val_peek(1); }
break;
case 21:
//#line 107 "hoc6.y"
{ maq.code("STOP");                    
                    yyval = new ParserVal(val_peek(1).obj);
                  }
break;
case 22:
//#line 111 "hoc6.y"
{ int numI = maq.code("whileCode"); 
                               maq.code("STOP"); maq.code("STOP");
                               yyval = new ParserVal(new Integer(numI));
                             }
break;
case 23:
//#line 116 "hoc6.y"
{ int numI = maq.code("ifCode");
          maq.code("STOP"); maq.code("STOP"); maq.code("STOP");
          yyval = new ParserVal(new Integer(numI));
        }
break;
case 24:
//#line 121 "hoc6.y"
{ int numI = maq.code("forCode");
           maq.code("STOP"); maq.code("STOP"); maq.code("STOP"); maq.code("STOP"); maq.code("STOP");
           yyval = new ParserVal(new Integer(numI));
         }
break;
case 25:
//#line 126 "hoc6.y"
{ maq.code("STOP");
              yyval = new ParserVal(val_peek(0).obj);
            }
break;
case 26:
//#line 130 "hoc6.y"
{ maq.code("STOP");
              yyval = new ParserVal(val_peek(0).obj);
            }
break;
case 27:
//#line 135 "hoc6.y"
{ maq.code("STOP");
           yyval = new ParserVal(new Integer(maq.getProg().size())); 
		  System.out.println("-->| STOP");
         }
break;
case 28:
//#line 140 "hoc6.y"
{ yyval = new ParserVal(new Integer(maq.getProg().size())); System.out.println("en stmlist");}
break;
case 31:
//#line 146 "hoc6.y"
{ 	maq.code("procret"); 
                      					Cadena c = (Cadena) val_peek(3).obj; 
                      					Symbol s = new Symbol(c.getCadena(), "", (short) 2, null);
                      					maq.define(s); 
                      					indef = false; 
                      				}
break;
case 32:
//#line 152 "hoc6.y"
{ 	System.out.println("estamos en una funcion defn");
                     					/* maq.code("procret"); 
*/
                      					Cadena c = (Cadena) val_peek(3).obj; 
                      					Symbol s = new Symbol(c.getCadena(), "", (short) 3, null);
                      					maq.define(s); 
                      					indef = false; 
                      				}
break;
case 36:
//#line 162 "hoc6.y"
{ yyval = new ParserVal(0); }
break;
case 37:
//#line 163 "hoc6.y"
{ yyval = new ParserVal(1);}
break;
case 38:
//#line 164 "hoc6.y"
{ yyval = new ParserVal(val_peek(2).ival + 1);}
break;
case 39:
//#line 166 "hoc6.y"
{  yyval = new ParserVal(maq.getProgP());}
break;
case 40:
//#line 169 "hoc6.y"
{ Numero n = (Numero) val_peek(0).obj; 
                                    int num=maq.code("Numero");
                                    maq.code(n);
									yyval = new ParserVal(new Integer(num));
                                  }
break;
case 41:
//#line 174 "hoc6.y"
{ int num=maq.code("varPush");
                                    maq.code((Cadena) val_peek(0).obj);
                                    maq.code("getVarValue");
									yyval = new ParserVal(new Integer(num));
                                  }
break;
case 42:
//#line 179 "hoc6.y"
{ defnonly("$");          
              yyval = new ParserVal(new Integer(maq.code("arg"))); 
              maq.code(val_peek(0).ival + "");
            }
break;
case 43:
//#line 183 "hoc6.y"
{ yyval = val_peek(3); 
                                          maq.code("call"); 
                                          Cadena c = (Cadena) val_peek(4).obj;
                                          Symbol s = new Symbol(c.getCadena(), "", (short) 2, null);
                                          maq.code(s); 
                                          maq.code(val_peek(1).ival + ""); 
        }
break;
case 44:
//#line 190 "hoc6.y"
{}
break;
case 45:
//#line 191 "hoc6.y"
{ maq.code("bltinPush");
                                    maq.code((Cadena) val_peek(3).obj);
                                    maq.code("bltin");                                    
                                  }
break;
case 46:
//#line 195 "hoc6.y"
{ maq.code("add"); }
break;
case 47:
//#line 196 "hoc6.y"
{ maq.code("sub"); }
break;
case 48:
//#line 197 "hoc6.y"
{ maq.code("mult"); }
break;
case 49:
//#line 198 "hoc6.y"
{ maq.code("div"); }
break;
case 50:
//#line 199 "hoc6.y"
{ maq.code("powN");
                                    maq.code((Cadena) val_peek(0).obj);
                                    maq.code("pow");                                    
                                  }
break;
case 51:
//#line 203 "hoc6.y"
{ yyval = val_peek(1); }
break;
case 52:
//#line 206 "hoc6.y"
{ maq.code("eq");}
break;
case 53:
//#line 207 "hoc6.y"
{ maq.code("neq");}
break;
case 54:
//#line 208 "hoc6.y"
{ maq.code("gt");}
break;
case 55:
//#line 209 "hoc6.y"
{ maq.code("ge");}
break;
case 56:
//#line 210 "hoc6.y"
{ maq.code("lt");}
break;
case 57:
//#line 211 "hoc6.y"
{ maq.code("le");}
break;
case 58:
//#line 216 "hoc6.y"
{
							maq.code("varPush");
                      		maq.code((Cadena) val_peek(2).obj);
                      		maq.code("asgVarCoord"); 
						}
break;
case 59:
//#line 223 "hoc6.y"
{ 	maq.code("suma");		}
break;
case 60:
//#line 224 "hoc6.y"
{	maq.limpiarDireccionesVar(); maq.banderaVar=false; maq.code("comienza");}
break;
case 61:
//#line 225 "hoc6.y"
{	maq.code("concatenarCoordenadas");maq.restablecerBandera();}
break;
case 62:
//#line 227 "hoc6.y"
{ maq.code("sumafinal");	}
break;
case 63:
//#line 228 "hoc6.y"
{ maq.code("restafinal");	}
break;
case 64:
//#line 229 "hoc6.y"
{}
break;
case 65:
//#line 232 "hoc6.y"
{ 	maq.code("comienza"); 	}
break;
case 66:
//#line 233 "hoc6.y"
{ 	maq.code("suma");		}
break;
case 67:
//#line 234 "hoc6.y"
{	
							maq.code("varPush");
                      		maq.code((Cadena) val_peek(0).obj);
                      		maq.code("getVarCoordValue");
						}
break;
case 68:
//#line 241 "hoc6.y"
{	maq.code("este");  		}
break;
case 69:
//#line 242 "hoc6.y"
{	maq.code("norte"); 		}
break;
case 70:
//#line 243 "hoc6.y"
{	maq.code("oeste"); 		}
break;
case 71:
//#line 244 "hoc6.y"
{	maq.code("sur");	  	}
break;
case 72:
//#line 245 "hoc6.y"
{	maq.code("up");	  		}
break;
case 73:
//#line 246 "hoc6.y"
{	maq.code("down");  		}
break;
case 74:
//#line 247 "hoc6.y"
{	maq.code("sureste");	}
break;
case 75:
//#line 248 "hoc6.y"
{	maq.code("noreste");	}
break;
case 76:
//#line 249 "hoc6.y"
{	maq.code("noroeste");	}
break;
case 77:
//#line 250 "hoc6.y"
{	maq.code("suroeste");	}
break;
//#line 1207 "Parser.java"
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
