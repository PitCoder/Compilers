//if(i==0) {crdnueva=crda crdnueva+c} else {crdnueva=crdb crdnueva+c}

//while(i<10) { if(((i/2)*2)==i) { crdvar= crdvar + c [so] [so] } else { crdvar=crdvar + c [no] [no] } i=i+1 }
%{
import java.lang.Math;
import java.io.*;
import java.util.StringTokenizer;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.nio.charset.StandardCharsets;
%}

%token BLTIN
%token IF ELSE WHILE FOR PRINT
%token FUNC PROC RETURN
%token EQ NEQ GT GE LT LE
%token FUNCTION PROCEDURE
%token ARG

%token NUMBER
	
%token VAR

%token VARCRD

/* Operators */
%right '='
%left '-' '+'
%left '*' '/' 		//'^'
//%right IF ELSE EQ NEQ GT GE LT LE 

%% 
/* A continuaci?n las reglas gramaticales y las acciones */

list:   
   | list '\n'
   | list defn ';'   { maq.code("STOP"); System.out.println("function definition");}
   | list asgn '\n'  { maq.code("STOP"); System.out.println("asignacion normal"); return 1; }
   | list stmt '\n'  { maq.code("STOP"); System.out.println("este es un statement"); return 1;}
   | list exp '\n'   { maq.code("printNumber"); System.out.println("expresion de numero"); maq.code("STOP"); return 1;}  
   | list asigncoord '\n'{	maq.banderaVar=true;	
						 	maq.code("printVar"); 
							maq.code("STOP"); 
	   						System.out.println("este es un asigncoord");
	   						return 1;
						 }
	| list expcoord '\n'{	maq.banderaVar=false;
							maq.code("imprime");	
						    maq.code("STOP");
							maq.restablecerBandera();
							System.out.println("este es un expcoord");
							return 1;
						}
   ;
   
asgn: VAR '=' exp   { int numI = maq.code("varPush");
                      maq.code((Cadena) $1.obj);
                      maq.code("asgVar");                       
                      $$ = new ParserVal($3.obj);
                    }
      ;
			/*Statements*/
stmt: exp { maq.code("pop"); }
		| RETURN exp { defnonly("return"); $$ = $2; maq.code("funcret") ; } 
      	| procname begin '(' arglist ')' { $$ = $2; 
                                          maq.code("call"); 
                                          Cadena c = (Cadena) $1.obj;
                                          Symbol s = new Symbol(c.getCadena(), "", (short) 2, null);
                                          maq.code(s); 
                                          maq.code($4.ival + ""); 
        } 
		| PRINT exp { maq.code("printNumber"); 
                    $$ = new ParserVal($2.obj); //dice la direccion a redirirgir (los STOP)
                  }

	 	| asigncoord {	System.out.println("nabla");
						maq.banderaVar=true;	
						maq.code("printVar"); 
						maq.code("STOP"); 
						 }
	  	| expcoord	{	System.out.println("nabla");
						maq.banderaVar=false;
						maq.code("imprime");	
					    maq.code("STOP");
						maq.restablecerBandera();
						}
      | while cond stmt end { maq.getProg().setElementAt($3.obj, (int) $1.obj + 1);
                              maq.getProg().setElementAt($4.obj, (int) $1.obj + 2); 
                            }
      | if cond stmt end    { maq.getProg().setElementAt($3.obj, (int) $1.obj + 1); 
                              maq.getProg().setElementAt($4.obj, (int) $1.obj + 3); 
                            }
      | if cond stmt end ELSE stmt end {  maq.getProg().setElementAt($3.obj, (int) $1.obj + 1);
                                          maq.getProg().setElementAt($6.obj, (int) $1.obj + 2); 
                                          maq.getProg().setElementAt($7.obj, (int) $1.obj + 3);
	                                     } 
	  | for '(' inicio ';' cond ';' inc ')' stmt end {  maq.getProg().setElementAt($3.obj, (int) $1.obj + 1);
                                                        maq.getProg().setElementAt($5.obj, (int) $1.obj + 2); 
                                                        maq.getProg().setElementAt($7.obj, (int) $1.obj + 3);
                                                        maq.getProg().setElementAt($9.obj, (int) $1.obj + 4);
                                                        maq.getProg().setElementAt($10.obj, (int) $1.obj + 5);
                                                     }
      | '{' stmtlist '}' {$$  =  $2; }
      ;
cond: '(' exp ')' { maq.code("STOP");                    
                    $$ = new ParserVal($2.obj);
                  }
      ;
while:	WHILE                { int numI = maq.code("whileCode"); 
                               maq.code("STOP"); maq.code("STOP");
                               $$ = new ParserVal(new Integer(numI));
                             }
      ;
if: IF  { int numI = maq.code("ifCode");
          maq.code("STOP"); maq.code("STOP"); maq.code("STOP");
          $$ = new ParserVal(new Integer(numI));
        }
		
for: FOR { int numI = maq.code("forCode");
           maq.code("STOP"); maq.code("STOP"); maq.code("STOP"); maq.code("STOP"); maq.code("STOP");
           $$ = new ParserVal(new Integer(numI));
         }
    ;
inicio: exp { maq.code("STOP");
              $$ = new ParserVal($1.obj);
            }
      ;
inc:    exp { maq.code("STOP");
              $$ = new ParserVal($1.obj);
            }
      ;

end:     { maq.code("STOP");
           $$ = new ParserVal(new Integer(maq.getProg().size())); 
		  System.out.println("-->| STOP");
         }
    ;
stmtlist: 			{ $$ = new ParserVal(new Integer(maq.getProg().size())); System.out.println("en stmlist");}
		| stmtlist '\n' 
	    | stmtlist stmt
				;

/* Functions y procedures */
defn:    PROC procname '(' ')' stmt { 	maq.code("procret"); 
                      					Cadena c = (Cadena) $2.obj; 
                      					Symbol s = new Symbol(c.getCadena(), "", (short) 2, null);
                      					maq.define(s); 
                      					indef = false; 
                      				} 
        | FUNC procname '(' ')' stmt { 	System.out.println("estamos en una funcion defn");
                     					// maq.code("procret"); 
                      					Cadena c = (Cadena) $2.obj; 
                      					Symbol s = new Symbol(c.getCadena(), "", (short) 3, null);
                      					maq.define(s); 
                      					indef = false; 
                      				} 
          ;
procname: VAR | FUNCTION | PROCEDURE ;

arglist:  /* nada */         { $$ = new ParserVal(0); }
          | exp              { $$ = new ParserVal(1);}
          | arglist ',' exp  { $$ = new ParserVal($1.ival + 1);}
   ;
begin:  /* nada */          {  $$ = new ParserVal(maq.getProgP());}
   ;

exp:  NUMBER                      { Numero n = (Numero) $1.obj; 
                                    int num=maq.code("Numero");
                                    maq.code(n);
									$$ = new ParserVal(new Integer(num));
                                  }
     | VAR                        { int num=maq.code("varPush");
                                    maq.code((Cadena) $1.obj);
                                    maq.code("getVarValue");
									$$ = new ParserVal(new Integer(num));
                                  } 
	| ARG  { defnonly("$");          
              $$ = new ParserVal(new Integer(maq.code("arg"))); 
              maq.code($1.ival + "");
            }
     | procname begin '(' arglist ')' { $$ = $2; 
                                          maq.code("call"); 
                                          Cadena c = (Cadena) $1.obj;
                                          Symbol s = new Symbol(c.getCadena(), "", (short) 2, null);
                                          maq.code(s); 
                                          maq.code($4.ival + ""); 
        } 
	| asgn   {}       
	| BLTIN '(' exp ')'           { maq.code("bltinPush");
                                    maq.code((Cadena) $1.obj);
                                    maq.code("bltin");                                    
                                  }
     | exp '+' exp                { maq.code("add"); }  
     | exp '-' exp                { maq.code("sub"); }
     | exp '*' exp                { maq.code("mult"); }
     | exp '/' exp                { maq.code("div"); }
     | exp '^' exp                { maq.code("powN");
                                    maq.code((Cadena) $3.obj);
                                    maq.code("pow");                                    
                                  }
     | '(' exp ')'                { $$ = $2; }

			/* Conditionals operations */
     | exp EQ exp                 { maq.code("eq");}
     | exp NEQ exp                { maq.code("neq");}
     | exp GT exp                 { maq.code("gt");}
     | exp GE exp                 { maq.code("ge");}
     | exp LT exp                 { maq.code("lt");}
     | exp LE exp                 { maq.code("le");}
    ;
	
/*gram?tica de coordenadas*/

asigncoord:	VARCRD '=' binstr	{
							maq.code("varPush");
                      		maq.code((Cadena) $1.obj);
                      		maq.code("asgVarCoord"); 
						}
		;
			
binstr: binstr instr	{ 	maq.code("suma");		}
		| 'c'			{	maq.limpiarDireccionesVar(); maq.banderaVar=false; maq.code("comienza");}
		| expcoord 		    {	maq.code("concatenarCoordenadas");maq.restablecerBandera();}
		;
expcoord: 	  expcoord '+' expcoord	{ maq.code("sumafinal");	}
		| expcoord '-' expcoord 	{ maq.code("restafinal");	}
		| sec			{}
		;
		
sec:	 'c'			{ 	maq.code("comienza"); 	}
		| sec instr		{ 	maq.code("suma");		}
		| VARCRD		{	
							maq.code("varPush");
                      		maq.code((Cadena) $1.obj);
                      		maq.code("getVarCoordValue");
						}		
		;

instr:    'e'			{	maq.code("este");  		}
		| 'n'			{	maq.code("norte"); 		}
		| 'o'			{	maq.code("oeste"); 		}
		| 's'			{	maq.code("sur");	  	}
		| 'u'			{	maq.code("up");	  		}
		| 'd'			{	maq.code("down");  		}
		| '&'			{	maq.code("sureste");	}
		| '%'			{	maq.code("noreste");	}
		| '#'			{	maq.code("noroeste");	}
		| '"'			{	maq.code("suroeste");	}
		;
	

%%

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
