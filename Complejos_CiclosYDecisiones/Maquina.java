import java.awt.*;
import java.util.*;
import java.lang.reflect.*;

public class Maquina {
  Stack       pila;  
  Vector      prog;
  int         pc;
  Method      metodo;
  Class       c;
  SymbolTable symbolTable;

  
  void init() { 
  /* Function init */
    String[] callNames = {"exp", "sin", "cos"};
    String[] funcNames = {"exp", "sinus", "cosine"};
    for (int i = 0; i < funcNames.length; i++) {
      symbolTable.install(callNames[i], funcNames[i], null, (short) 2);  
    }
  }
  
  void initCode() {
      pila        = new Stack();      
      prog        = new Vector();
      symbolTable = new SymbolTable();
      init();
   }
  
  void newProgram() {
    prog = new Vector();
  }
  
  int code(Object f) {      
      prog.addElement(f);     // We add f in the program.
      return prog.size() - 1; // Return of the 'line of program'.
   }
  
  public Vector getProg() {
		return prog;
	}
  
  Object pop() { 
    return pila.pop();
  }
  
  void cNumber() {
	  Complejo c = (Complejo) prog.elementAt(pc);
	  pc = pc + 1;                                 // Extra increment in pc.
	  pila.push(c);                                // A Complejo is pushed in the stack.
  }
  
  void varPush() {    
    Cadena c = (Cadena) prog.elementAt(pc);
    pc = pc + 1;                                // Extra increment in pc.
    pila.push(c);                               // A variable is pushed in the stack
  }
  
  void asgVar() {
    Cadena sy   = (Cadena)    pila.pop();       // Get data from stack (symbol).         
    Complejo c  = (Complejo)  pila.pop();       // Get data from stack (data).
    
    Symbol s = symbolTable.lookUpTable(sy.getCadena());                      
    if (s == null) 
      symbolTable.install(sy.getCadena(), null, c, (short) 1);
    else
      symbolTable.update(s, c);
    
    pila.push(c);
  }
  
  void getVarValue() {
    Cadena sym  = (Cadena) pila.pop();          // Get data from stack (symbol).    
    Symbol s    = symbolTable.lookUpTable(sym.getCadena());
    
    if (s == null) 
      System.err.println("Undefined variable");
    else 
      pila.push(s.getData());
  }
  
  void bltinPush() {
    Cadena c = (Cadena) prog.elementAt(pc);
    pc = pc + 1;                              // Extra increment in pc.
    pila.push(c);                             // A bltin function is pushed in the stack.
  }
  
  void bltin() {
    /* Symbol recovery from the stack */
    Cadena sym  = (Cadena) pila.pop();
    Symbol s    = symbolTable.lookUpTable(sym.getCadena());
    
    /* Data recovery from the stack */
    Complejo c  = (Complejo)  pila.pop();

    /* Invocation of the method */
    String fName    = s.getFunctionName();                                    
    Complejo res    = new Complejo(0, 0);
    Functions f     = new Functions(); 
    Class cl        = f.getClass();
    Class[] cArg    = new Class[1];
    cArg[0]         = res.getClass();    
    Object param[]  = new Object[1];

    /* Obtaining the method */
    try {
      Method method   = cl.getMethod(fName, cArg);      
      param[0]        = c;
      res             = (Complejo) method.invoke(f, param);
    } catch (NoSuchMethodException ex) {
    } catch (IllegalAccessException ex) {   
    } catch (InvocationTargetException ex) {
    }
    
    /* Result push in the stack */
    pila.push(res);
    
  }
  
  void printVar() {
    symbolTable.print();
  }
  
  void powN() {
    Cadena cad = (Cadena) prog.elementAt(pc);
    pc = pc + 1;
    pila.push(cad);
  }
  
  void printComplex() {
	  Complejo c = (Complejo) pila.pop();
	  System.out.println("Result: ");
    c.imprimeComplejo();
  }   
  
  void add() {
    Complejo c1, c2;
    c1 = (Complejo) pila.pop();
    c2 = (Complejo) pila.pop();
    pila.push(c1.sumaComplejos(c1, c2));    
  }
  
  void sub() {
    Complejo c1, c2;
    c2 = (Complejo) pila.pop();
    c1 = (Complejo) pila.pop();
    pila.push(c1.restaComplejos(c1, c2));    
  }
  
  void mult() {
    Complejo c1, c2;
    c1 = (Complejo) pila.pop();
    c2 = (Complejo) pila.pop();
    pila.push(c1.multiplicaComplejos(c1, c2));    
  }
  
  void div() {
    Complejo c1, c2;
    c2 = (Complejo) pila.pop();
    c1 = (Complejo) pila.pop();
    pila.push(c1.divideComplejos(c1, c2));    
  }
  
  void pow() {
    Cadena powN = (Cadena)    pila.pop();
    Complejo c  = (Complejo)  pila.pop();
    pila.push(c.pow(c, powN));
  }
      
  void execute(int offset) {    
      String inst;
      /*System.out.println(".:: Generated Program ::. ");
      System.out.println("Program Size: = " + prog.size());
      for (pc = offset; pc < prog.size(); pc = pc + 1) {
      	System.out.println("PC = " + pc + ". Instruction: " + prog.elementAt(pc));
      }*/
    
		  pc = offset;      
      while (true) {         
			  try {
          if (pc >= prog.size())
            break; 
          
          inst = (String) prog.elementAt(pc);         // We get the instruction at pc.
          // System.out.println("Inst: " + inst);
          if (inst.equals("STOP"))                    // The whole program has been executed.
            break;
          
          pc = pc + 1;                                // Normal Increment.         
          c = this.getClass();
          metodo = c.getDeclaredMethod(inst, null);   // Get method of this class.
          metodo.invoke(this, null);                  
			  } catch(NoSuchMethodException e){
				  System.out.println("No method " + e);
        } catch(InvocationTargetException e){
				  e.printStackTrace();
        } catch(IllegalAccessException e){
				  System.out.println(e);
        }
		}
	} 
  
  
  /* Code of while */
  void whileCode(){
    boolean d;
    int savepc = pc;    
    execute(savepc + 2);	/* condition */
    d = ((Boolean)pila.pop()).booleanValue();
    
    while (d) {      
      execute(((Integer)prog.elementAt(savepc)).intValue()); // statement      
      execute(savepc + 2); // condition
      d = ((Boolean)pila.pop()).booleanValue();      
    }
    
    pc = ((Integer)prog.elementAt(savepc + 1)).intValue();
   }
  
  /* Code of if */
  void ifCode(){
    boolean d;    
    int savepc = pc;
    
    execute(savepc + 3); /* Condition */        
    d=((Boolean)pila.pop()).booleanValue();
    
    if (d) {       
      execute(((Integer)prog.elementAt(savepc)).intValue()); /* stm1 */
    } else if (!prog.elementAt(savepc + 1).toString().equals("STOP")) {
      execute(((Integer)prog.elementAt(savepc + 1)).intValue()); /* stm2 */
    }
    
    pc = ((Integer)prog.elementAt(savepc + 2)).intValue();
   }

   /* Conditional operations functions */
  void eq() {
    Complejo c1, c2;
    c1 = (Complejo) pila.pop();
    c2 = (Complejo) pila.pop();

    boolean r = c1.equals(c1, c2);
    pila.push(r);
  }

  void neq() {
    Complejo c1, c2;
    c1 = (Complejo) pila.pop();
    c2 = (Complejo) pila.pop();

    boolean r = c1.equals(c1, c2);
    pila.push(!r);
  }
  
  void gt() {
    Complejo c1, c2;
    c2 = (Complejo) pila.pop();
    c1 = (Complejo) pila.pop();    
    pila.push(c1.greaterThan(c1, c2));
  }
  
  void ge() {
    Complejo c1, c2;
    c2 = (Complejo) pila.pop();
    c1 = (Complejo) pila.pop();    
    pila.push(c1.greaterOrEqualsThan(c1, c2));
  }
  
  void lt() {
    Complejo c1, c2;
    c2 = (Complejo) pila.pop();
    c1 = (Complejo) pila.pop();    
    pila.push(c1.lessThan(c1, c2));
  }
  
  void le() {
    Complejo c1, c2;
    c2 = (Complejo) pila.pop();
    c1 = (Complejo) pila.pop();    
    pila.push(c1.lessOrEqualsThan(c1, c2));
  }
}