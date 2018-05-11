import java.util.*;
import java.lang.reflect.*;

public class Machine{
  /*** ELEMENTS OF THE STACK MACHINE ***/
  Stack stack;    //Stack of the machine
  Vector program; //Vector in which we will storage our program a.k.a RAM
  int pc;         //Program counter

  /*** SYMBOL TABLE ***/
  SymbolTable symbolTable;

  /*** NECESSARY OBJECTS TO APPLY REFLECTION ***/
  private Class mclass;
  private Method method;

  /*** CONSTRUCTOR ***/
  public Machine(){

  }

  /***** HOC 4: CODE GENERATION STAGE ***/

  /*** INITIALIZING THE SYMBOL TABLE ***/
  private void init(){
    String[] funcNames = {"geom", "bi", "nbi", "intgr", "derv"};
    String[] callNames = {"geometric", "binomial", "nbinomial", "integral", "derivative"};
    /* Then we install them */
    /* function: install, args: String[] callNames, String[] funcNames, Polynomial null, short 2 */
    for(int i=0; i<callNames.length; i++)
      symbolTable.install(callNames[i], funcNames[i], null, (short) 2);
  }

  /*** INITIALIZING THE STACK MACHINE ***/
  public void initMachine(){
    this.stack = new Stack();
    this.program = new Vector();
    this.symbolTable = new SymbolTable();
    init();
  }

  /*** ADDING CODE TO THE RAM ***/
  public int code(Object c){
    program.addElement(c);
    return program.size()-1;
  }

  /*** PUSH OPERATIONS OVER THE STACK ***/
  /* Function that pushes a polynomial into the stack */
  public void poly(){
    if(stack.empty()){
      Polynomial p = new Polynomial();
      stack.push(p);
    }
    else{
      Polynomial p = new Polynomial((ArrayList<Term>) stack.pop());
      stack.push(p);
    }
  }

  /* Function that add a term to a new list of terms and add it into the stack */
  public void createLT(){
    ArrayList<Term> ts = new ArrayList();
    ts.add((Term) stack.pop());
    stack.push(ts);
  }

  /* Function that add a term to the list of terms and add it into the stack */
  public void addtoLT(){
    ArrayList<Term> ts = (ArrayList<Term>) stack.pop();
    ts.add((Term) stack.pop());
    stack.push(ts);
  }

  /* Function that pushes a term into the stack */
  public void term(){
    Term t = (Term) program.elementAt(pc);
    pc = pc + 1;
    stack.push(t);
  }

  public void number(){
    int n = Integer.parseInt(((InputText) program.elementAt(pc)).getText());
    pc = pc + 1;
    stack.push(n);
  }

  public void numParam(){
    int n = Integer.parseInt(((InputText) program.elementAt(pc)).getText());
    pc = pc + 1;
    stack.push(n);
  }

  /* Function that pushes a variable name into the stack */
  public void varPush(){
    InputText t = (InputText) program.elementAt(pc);
    pc = pc + 1;
    stack.push(t);
  }

  /* Function that pushes a built-in into the stack */
  public void builtinPush(){
    InputText t = (InputText) program.elementAt(pc);
    pc = pc + 1;
    stack.push(t);
  }

  /*** POP OEPRATION OVER THE STACK ***/
  /* We return an object, since we store multple objects in the stack, so we implement a generic one */
  private Object pop(){
    return stack.pop();
  }

  /*** CODE ***/

  /*** CODE FOR ASSIGNING A VALUE TO A VARIABLE ***/
  public void asgnVar(){
    InputText identifier = (InputText) stack.pop();                //Equivalent from obtaining from $1
    Polynomial data = (Polynomial) stack.pop();                    //Equivalent from obtaining from $3
    Symbol symbol = symbolTable.lookUpTable(identifier.getText()); //We verify if the symbol is already defined in the symbol table

    if(symbol == null)
      symbolTable.install(identifier.getText(), null, data, (short) 1);
    else
      symbolTable.update(symbol, data);
  }

  /*** CODE TO RETRIEVE A VALUE FROM A VARIABLE ***/
  public void retvarVal(){
    InputText identifier = (InputText) stack.pop();
    Symbol symbol = symbolTable.lookUpTable(identifier.getText());

    if(symbol != null)
      stack.push(symbol.getData());
    else
      System.out.println("Variable not defined");
  }

  /*** CODE TO BUILD A BUILT-IN ***/
  public void builtin(){
    /* We retrieve the symbol from the stack */
    InputText identifier = (InputText) stack.pop();
    /* We look on the table for the name */
    Symbol symbol =  symbolTable.lookUpTable(identifier.getText());
    /* Then we retrieve the number of parameters of the function */
    int numparam = (int) stack.pop();
    Class[] args = new Class[numparam];
    Object[] params = new Object[numparam];
    Polynomial p = new Polynomial();

    for(int i=(numparam-1); i>=0; i--){
      //System.out.println(stack.peek().getClass());
      if(stack.peek().getClass().equals(Integer.class)){
        args[i] = int.class;
        params[i] = (int) stack.pop();
      }
      else{
        args[i] = p.getClass();
        params[i] = (Polynomial) stack.pop();
      }
    }

    /* Invocation of the the respective method */
    String funcName = symbol.getFuncName();
    Polynomial result = new Polynomial();
    Functions functions = new Functions();
    Class fclass = functions.getClass();

    try{
      Method mbuiltin = fclass.getMethod(funcName, args);
      result = (Polynomial) mbuiltin.invoke(functions, params);
    }
    catch (NoSuchMethodException e) {
      System.out.println("Such method is not defined " + e);
    }
    catch (IllegalAccessException e) {
      System.out.println(e);
    }
    catch (InvocationTargetException e) {
      e.printStackTrace();
    }
    stack.push(result);
  }

  /*** CODE FOR BASIC OPERATIONS FUNCTIONS ***/
  /* Addition operation code */
  public void addition(){
    Polynomial a,b;
    b = (Polynomial) stack.pop();
    a = (Polynomial) stack.pop();
    Sum s = new Sum();
    stack.push(s.sum(a,b));
  }
  /* Substraction operation code */
  public void substraction(){
    Polynomial a,b;
    b = (Polynomial) stack.pop();
    a = (Polynomial) stack.pop();
    Sub sb = new Sub();
    stack.push(sb.sub(a,b));
  }
  /* Multiplication operation code */
  public void multiplication(){
    Polynomial a,b;
    b = (Polynomial) stack.pop();
    a = (Polynomial) stack.pop();
    Mul mul = new Mul();
    stack.push(mul.multiplyTwo(a,b));
  }
  /* Division operation code */
  public void division(){
    Polynomial a,b;
    Polynomial[] result;
    b = (Polynomial) stack.pop();
    a = (Polynomial) stack.pop();
    Div div = new Div();
    result = div.division(b,a);
    stack.push(result[0]);
  }
  /* Module operation code */
  public void module(){
    Polynomial a,b;
    Polynomial[] result;
    b = (Polynomial) stack.pop();
    a = (Polynomial) stack.pop();
    Div div = new Div();
    result = div.division(b,a);
    stack.push(result[1]);
  }
  /* Power operation code */
  public void power(){
    Polynomial a;
    int n;
    n = (int) stack.pop();
    a = (Polynomial) stack.pop();
    Pow pow = new Pow();
    stack.push(pow.power(a,n));
  }

  /*** CODE TO PRINT THE RESULT VARIABLES OR A POLYNOMIAL ***/
  public void printAsignment(){
    System.out.print("Result: ");
    symbolTable.print();
    System.out.print("\nExpression: ");
  }

  public void printPolynomial(){
    Polynomial result = (Polynomial) stack.pop();
    result.reduce();
    System.out.println("Result: " + result.toString() + "\n");
    System.out.print("Expression: ");
  }

  /***** HOC 4: CODE EXECUTION STAGE ***/
  public void execute(boolean deletecode){
    /* If true we remove the prevoious program */
    if(deletecode){
        /* We obtain the index of the last instruction Generated */
        int limit = program.indexOf("STOP");
        /* We iterate the vector and we remove form the first element until its empty */
        for(int i=0; i<=limit; i++)
          program.remove(0);
    }

    String instruction;
    System.out.println(".:: Generated Program ::. ");
    System.out.println("Program Size: = " + program.size());
    /* Were we print the program counter, plus the instruction assigned to that pc */
    for (pc = 0; pc < program.size(); pc = pc + 1) {
      if(program.elementAt(pc).getClass() == InputText.class){
        instruction = ((InputText) program.elementAt(pc)).getText();
        System.out.println("PC = " + pc + ". Instruction: " + instruction);
      }
      else{
        System.out.println("PC = " + pc + ". Instruction: " + program.elementAt(pc));
      }
    }
    System.out.println("\n\n.:: Executing Program ::. ");
    /* We start the exection of the program */
    pc = 0;

    boolean execute = true;
    while(execute){
      /* We will try to execute the code defined by the functions above */
      try{
        //System.out.println("Executing pc: " + pc);
        if(program.elementAt(pc).getClass() == InputText.class)
          instruction = ((InputText) program.elementAt(pc)).getText();
        else
          instruction = (String) program.elementAt(pc);

        if(instruction.equals("STOP")){
          execute = false;
        }
        else{
            /* We increment the program counter */
            pc = pc + 1;
            mclass = this.getClass();
            method = mclass.getMethod(instruction, null);
            method.invoke(this, null);
        }
      }
      catch(NoSuchMethodException e){
        System.out.println("Such method is not defined " + e);
      } catch(InvocationTargetException e){
        e.printStackTrace();
      } catch(IllegalAccessException e){
        System.out.println(e);
      }
    }
  }
}
