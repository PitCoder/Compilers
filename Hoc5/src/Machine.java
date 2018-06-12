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
    String[] funcNames = {"cbi", "geom", "bi", "nbi", "intgr", "derv"};
    String[] callNames = {"coefbinomial","geometric", "binomial", "nbinomial", "integral", "derivative"};
    /* Then we install them */
    /* function: install, args: String[] callNames, String[] funcNames, Polynomial null, short 2 */
    for(int i=0; i<callNames.length; i++)
      symbolTable.install(callNames[i], funcNames[i], (Polynomial) null, (short) 2);
  }

  /*** INITIALIZING THE STACK MACHINE ***/
  public void initMachine(){
    this.stack = new Stack();
    this.program = new Vector();
    this.symbolTable = new SymbolTable();
    init();
  }

  /*** STARTING A NEW PROGRAM VECTOR ***/
  void newProgram() {
    this.program = new Vector();
  }

  /*** ADDING CODE TO THE RAM ***/
  public int code(Object c){
    this.program.addElement(c);
    return this.program.size()-1;
  }

  /*** RETURNING THE PROGRAM VECTOR ***/
  public Vector getProgram() {
    return this.program;
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
    Integer n = Integer.parseInt(((InputText) program.elementAt(pc)).getText());
    pc = pc + 1;
    stack.push(n);
  }

  public void numParam(){
    Integer n = Integer.parseInt(((InputText) program.elementAt(pc)).getText());
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
    VerifyType vf = new VerifyType();
    InputText identifier = (InputText) stack.pop();                //Equivalent from obtaining from $1
    Object obj = stack.pop();

    if(vf.verify(obj).equals("int")){
        int data = (int) obj; //Equivalent from obtaining from $3
        Symbol symbol = symbolTable.lookUpTable(identifier.getText()); //We verify if the symbol is already defined in the symbol table
        if(symbol == null)
          symbolTable.install(identifier.getText(), null, data, (short) 1);
        else
          symbolTable.update(symbol, data);
    }
    else{
        Polynomial data = (Polynomial) obj; //Equivalent from obtaining from $3}
        Symbol symbol = symbolTable.lookUpTable(identifier.getText()); //We verify if the symbol is already defined in the symbol table
        if(symbol == null)
          symbolTable.install(identifier.getText(), null, data, (short) 1);
        else
          symbolTable.update(symbol, data);
    }
  }

  /*** CODE TO RETRIEVE A VALUE FROM A VARIABLE ***/
  public void retvarVal(){
    InputText identifier = (InputText) stack.pop();
    Symbol symbol = symbolTable.lookUpTable(identifier.getText());

    if(symbol != null)
      if(symbol.getIData() == null){
        stack.push(symbol.getPData());
      }
      else{
        stack.push(symbol.getIData());
      }

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
    Integer numparam = (Integer) stack.pop();
    Class[] args = new Class[numparam];
    Object[] params = new Object[numparam];
    Polynomial p = new Polynomial();

    for(int i=(numparam-1); i>=0; i--){
      //System.out.println(stack.peek().getClass());
      if(stack.peek().getClass().equals(Integer.class)){
        args[i] = Integer.class;
        params[i] = (Integer) stack.pop();
      }
      else{
        args[i] = p.getClass();
        params[i] = (Polynomial) stack.pop();
      }
    }

    /* Invocation of the the respective method */
    String funcName = symbol.getFuncName();
    Functions functions = new Functions();
    Class fclass = functions.getClass();
    VerifyType vf = new VerifyType();

    try{
      Method mbuiltin = fclass.getMethod(funcName, args);
      Object obj = mbuiltin.invoke(functions, params);
      if(vf.verify(obj).equals("polynomial")){
        Polynomial result = (Polynomial) obj;
        stack.push(result);
      }
      else{
        Integer result = (Integer) obj;
        stack.push(result);
      }
    }
    catch (NoSuchMethodException e) {
      e.printStackTrace();
    }
    catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    catch (InvocationTargetException e) {
      e.printStackTrace();
    }
  }

  /*** CODE FOR BASIC RELATIONAL CONDITIONS ***/
  public void equal(){
    VerifyType vf = new VerifyType();

    String typea, typeb;
    boolean result;
    Object objb = stack.pop();
    Object obja = stack.pop();

    if(vf.verify(obja).equals("int") && vf.verify(objb).equals("int")) {
      Integer a = (Integer) obja;
      Integer b = (Integer) objb;
      if(a == b)
        result = true;
      else
        result = false;
      stack.push(result);
    }
    else{
      System.err.println("Error invalid operator for the given types...");
    }
  }

  public void distinct(){
    VerifyType vf = new VerifyType();

    String typea, typeb;
    boolean result;
    Object objb = stack.pop();
    Object obja = stack.pop();

    if(vf.verify(obja).equals("int") && vf.verify(objb).equals("int")) {
      Integer a = (Integer) obja;
      Integer b = (Integer) objb;
      if(a != b)
        result = true;
      else
        result = false;
      stack.push(result);
    }
    else{
      System.err.println("Error invalid operator for the given types...");
    }
  }

  public void greater(){
    VerifyType vf = new VerifyType();

    String typea, typeb;
    boolean result;
    Object objb = stack.pop();
    Object obja = stack.pop();

    if(vf.verify(obja).equals("int") && vf.verify(objb).equals("int")) {
      Integer a = (Integer) obja;
      Integer b = (Integer) objb;
      if(a > b)
        result = true;
      else
        result = false;
      stack.push(result);
    }
    else{
      System.err.println("Error invalid operator for the given types...");
    }
  }

  public void lesser(){
    VerifyType vf = new VerifyType();

    String typea, typeb;
    boolean result;
    Object objb = stack.pop();
    Object obja = stack.pop();

    if(vf.verify(obja).equals("int") && vf.verify(objb).equals("int")) {
      Integer a = (Integer) obja;
      Integer b = (Integer) objb;
      if(a < b)
        result = true;
      else
        result = false;
      stack.push(result);
    }
    else{
      System.err.println("Error invalid operator for the given types...");
    }
  }

  public void greaterEq(){
    VerifyType vf = new VerifyType();

    String typea, typeb;
    boolean result;
    Object objb = stack.pop();
    Object obja = stack.pop();

    if(vf.verify(obja).equals("int") && vf.verify(objb).equals("int")) {
      Integer a = (Integer) obja;
      Integer b = (Integer) objb;
      if(a >= b)
        result = true;
      else
        result = false;
      stack.push(result);
    }
    else{
      System.err.println("Error invalid operator for the given types...");
    }
  }

  public void lesserEq(){
    VerifyType vf = new VerifyType();

    String typea, typeb;
    boolean result;
    Object objb = stack.pop();
    Object obja = stack.pop();

    if(vf.verify(obja).equals("int") && vf.verify(objb).equals("int")) {
      Integer a = (Integer) obja;
      Integer b = (Integer) objb;
      if(a <= b)
        result = true;
      else
        result = false;
      stack.push(result);
    }
    else{
      System.err.println("Error invalid operator for the given types...");
    }
  }

  /*** CODE FOR BASIC OPERATIONS FUNCTIONS ***/
  /* Addition operation code */
  public void addition(){
    VerifyType vf = new VerifyType();
    Object objb = stack.pop();
    Object obja = stack.pop();

    if(vf.verify(obja).equals("int") && vf.verify(objb).equals("int")) {
      Integer a,b;
      a = (Integer) obja;
      b = (Integer) objb;
      stack.push(a + b);
    }
    else if(vf.verify(obja).equals("int") && vf.verify(objb).equals("polynomial")){
      Integer x = (Integer) obja;
      Polynomial a,b;
      a = new Polynomial();
      a.addTerm(new Term(x,1,0));
      b = (Polynomial) objb;
      Sum s = new Sum();
      stack.push(s.sum(a,b));
    }
    else if(vf.verify(obja).equals("polynomial") && vf.verify(objb).equals("int")){
      Integer x = (Integer) objb;
      Polynomial a,b;
      b = new Polynomial();
      b.addTerm(new Term(x,1,0));
      a = (Polynomial) obja;
      Sum s = new Sum();
      stack.push(s.sum(a,b));
    }
    else{
      Polynomial a,b;
      a = (Polynomial) obja;
      b = (Polynomial) objb;
      Sum s = new Sum();
      stack.push(s.sum(a,b));
    }
  }
  /* Substraction operation code */
  public void substraction(){
    VerifyType vf = new VerifyType();
    Object objb = stack.pop();
    Object obja = stack.pop();

    if(vf.verify(obja).equals("int") && vf.verify(objb).equals("int")) {
      Integer a,b;
      a = (Integer) obja;
      b = (Integer) objb;
      stack.push(a - b);
    }
    else if(vf.verify(obja).equals("int") && vf.verify(objb).equals("polynomial")){
      Integer x = (Integer) obja;
      Polynomial a,b;
      a = new Polynomial();
      a.addTerm(new Term(x,1,0));
      b = (Polynomial) objb;
      Sub sb = new Sub();
      stack.push(sb.sub(a,b));
    }
    else if(vf.verify(obja).equals("polynomial") && vf.verify(objb).equals("int")){
      Integer x = (Integer) objb;
      Polynomial a,b;
      b = new Polynomial();
      b.addTerm(new Term(x,1,0));
      a = (Polynomial) obja;
      Sub sb = new Sub();
      stack.push(sb.sub(a,b));
    }
    else{
      Polynomial a,b;
      a = (Polynomial) obja;
      b = (Polynomial) objb;
      Sub sb = new Sub();
      stack.push(sb.sub(a,b));
    }
  }
  /* Multiplication operation code */
  public void multiplication(){
    VerifyType vf = new VerifyType();
    Object objb = stack.pop();
    Object obja = stack.pop();

    if(vf.verify(obja).equals("int") && vf.verify(objb).equals("int")) {
      Integer a,b;
      a = (Integer) obja;
      b = (Integer) objb;
      stack.push(a * b);
    }
    else if(vf.verify(obja).equals("int") && vf.verify(objb).equals("polynomial")){
      Integer x = (Integer) obja;
      Polynomial a,b;
      a = new Polynomial();
      a.addTerm(new Term(x,1,0));
      b = (Polynomial) objb;
      Mul mul = new Mul();
      stack.push(mul.multiplyTwo(a,b));
    }
    else if(vf.verify(obja).equals("polynomial") && vf.verify(objb).equals("int")){
      Integer x = (Integer) objb;
      Polynomial a,b;
      b = new Polynomial();
      b.addTerm(new Term(x,1,0));
      a = (Polynomial) obja;
      Mul mul = new Mul();
      stack.push(mul.multiplyTwo(a,b));
    }
    else{
      Polynomial a,b;
      a = (Polynomial) obja;
      b = (Polynomial) objb;
      Mul mul = new Mul();
      stack.push(mul.multiplyTwo(a,b));
    }
  }
  /* Division operation code */
  public void division(){
    VerifyType vf = new VerifyType();
    Object objb = stack.pop();
    Object obja = stack.pop();

    if(vf.verify(obja).equals("int") && vf.verify(objb).equals("int")) {
      Integer a,b;
      b = (Integer) objb;
      a = (Integer) obja;
      stack.push(a / b);
    }
    else if(vf.verify(obja).equals("int") && vf.verify(objb).equals("polynomial")){
      Integer x = (Integer) obja;
      Polynomial a,b;
      Polynomial[] result;
      a = new Polynomial();
      a.addTerm(new Term(x,1,0));
      b = (Polynomial) objb;
      Div div = new Div();
      result = div.division(b,a);
      stack.push(result[0]);
    }
    else if(vf.verify(obja).equals("polynomial") && vf.verify(objb).equals("int")){
      Integer x = (Integer) objb;
      Polynomial a,b;
      Polynomial[] result;
      b = new Polynomial();
      b.addTerm(new Term(x,1,0));
      a = (Polynomial) obja;
      Div div = new Div();
      result = div.division(b,a);
      stack.push(result[0]);
    }
    else{
      Polynomial a,b;
      Polynomial[] result;
      a = (Polynomial) obja;
      b = (Polynomial) objb;
      Div div = new Div();
      result = div.division(b,a);
      stack.push(result[0]);
    }
  }
  /* Module operation code */
  public void module(){
    VerifyType vf = new VerifyType();
    Object objb = stack.pop();
    Object obja = stack.pop();

    if(vf.verify(obja).equals("int") && vf.verify(objb).equals("int")) {
      Integer a,b;
      a = (Integer) obja;
      b = (Integer) objb;
      stack.push(a % b);
    }
    else if(vf.verify(obja).equals("int") && vf.verify(objb).equals("polynomial")){
      int x = (Integer) obja;
      Polynomial a,b;
      Polynomial[] result;
      a = new Polynomial();
      a.addTerm(new Term(x,1,0));
      b = (Polynomial) objb;
      Div div = new Div();
      result = div.division(b,a);
      stack.push(result[1]);
    }
    else if(vf.verify(obja).equals("polynomial") && vf.verify(objb).equals("int")){
      Integer x = (Integer) objb;
      Polynomial a,b;
      Polynomial[] result;
      b = new Polynomial();
      b.addTerm(new Term(x,1,0));
      a = (Polynomial) obja;
      Div div = new Div();
      result = div.division(b,a);
      stack.push(result[1]);
    }
    else{
      Polynomial a,b;
      Polynomial[] result;
      a = (Polynomial) obja;
      b = (Polynomial) objb;
      Div div = new Div();
      result = div.division(b,a);
      stack.push(result[1]);
    }
  }
  /* Power operation code */
  public void power(){
    VerifyType vf = new VerifyType();
    Object objb = stack.pop();
    Object obja = stack.pop();

    if(vf.verify(obja).equals("int") && vf.verify(objb).equals("int")) {
      double a,b;
      a = ((int) obja) / ((double) 1);
      b = ((int) objb) / ((double) 1);
      stack.push((int)Math.pow(a,b));
    }
    else if(vf.verify(obja).equals("polynomial") && vf.verify(objb).equals("int")){
      Polynomial p;
      Integer n;
      n = (Integer) objb;
      p = (Polynomial) obja;
      Pow pow = new Pow();
      stack.push(pow.power(p,n));
    }
    else{
      System.err.println("Error invalid operator for the given types...");
    }
  }

  /*** CODE TO PRINT THE RESULT VARIABLES OR A POLYNOMIAL ***/
  public void printAsignment(){
    System.out.print("Result: ");
    symbolTable.print();
    //System.out.print("\nExpression: ");
  }

  public void printExpression(){
    VerifyType vf = new VerifyType();
    Object obj = stack.pop();

    if(vf.verify(obj).equals("int")) {
      Integer result = (Integer) obj;
      System.out.println("Result (Integer): " + result + "\n");
    }
    else {
      Polynomial result = (Polynomial) obj;
      result.reduce();
      System.out.println("Result (Polynomial): " + result.toString() + "\n");
    }
  }


  /* Code of while */
  public void whileCode(){
    boolean decision;
    int savepc = this.pc;
    execute(savepc + 2);	/* condition */
    decision = ((Boolean)stack.pop()).booleanValue();

    while(decision) {
      execute(((Integer)program.elementAt(savepc)).intValue()); // statement
      execute(savepc + 2); // condition
      decision = ((Boolean)stack.pop()).booleanValue();
    }

    pc = ((Integer)program.elementAt(savepc + 1)).intValue();
   }

   /* Code of if */
   public void ifCode(){
     boolean decision;
     int savepc = pc;

     execute(savepc + 3); /* Condition */
     decision = ((Boolean)stack.pop()).booleanValue();

     if(decision){
       execute(((Integer)program.elementAt(savepc)).intValue()); /* stm1 */
     }
     else if(!program.elementAt(savepc + 1).toString().equals("STOP")) {
       execute(((Integer)program.elementAt(savepc + 1)).intValue()); /* stm2 */
     }
     pc = ((Integer)program.elementAt(savepc + 2)).intValue();
    }

  /***** HOC 4: CODE EXECUTION STAGE ***/
  /*** Method overloading ***/
  public void execute(int offset){
    this.pc = offset;
    String instruction;

    if(offset == 0){
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
    }

    this.pc = offset;
    boolean execute = true;
    /*** Loop forever ***/
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

  public void execute(boolean deletecode, int offset){
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
