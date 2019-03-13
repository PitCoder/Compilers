import java.awt.*;
import java.util.*;
import java.lang.reflect.*;


import java.lang.Math;
import java.io.*;
import java.util.StringTokenizer;
import java.util.ArrayList;
import javax.swing.*;

public class Maquina {
  Stack       pila;
  Stack       marcos;
  Vector      prog;
  int         pc;
  Method      metodo;
  Class       c;
  SymbolTable symbolTable;
  boolean     returning;
  int         progBase;
  int         progP;
  SocketCliente socket;
  int banderaOp=0;
  int bandera=0;
  int index = 0;
  int index2 = 0;
  int indiceInicio = 0;
  
  	ArrayList<String> Direcciones = new ArrayList<String>();
	ArrayList<String> DireccionesVar = new ArrayList<String>();
	ArrayList<String> DireccionesFinal = new ArrayList<String>();
	////
	ArrayList<String> TipoOperacion = new ArrayList<String>();
	ArrayList<String> ContenidoVariables = new ArrayList<String>();
	ArrayList<String> signoVariables = new ArrayList<String>();
  
  	public boolean banderaVar=false;
	public int banderaLimpiar=0;
  
  void init() { 
   //Function init 
    String[] callNames = {"exp", "sin", "cos"};
    String[] funcNames = {"exp", "sinus", "cosine"};
    for (int i = 0; i < funcNames.length; i++) {
      symbolTable.install(callNames[i], funcNames[i], null, (short) 2);
    }
	  symbolTable.install("DIST", null, new Numero(5.55f), (short) 1);
  }
  
  void initCode() {
      pila        = new Stack();
    marcos      = new Stack();
      prog        = new Vector();
      symbolTable = new SymbolTable();
    
    progBase    = 0;
    progP       = progBase;
    returning   = false;
    
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
  
  public int getProgP() {
    return progP;
  }
  
  public int getProgBase() {
    return progBase;
  }
  
  Object pop() {
    if (!pila.isEmpty())
    return pila.pop();
    else
    {System.out.println("pila vacia!");
     return null;
    }
  }
  
  void Numero() {
	  Numero c = (Numero) prog.elementAt(pc);
	  pc = pc + 1;                                 // Extra increment in pc.
	  pila.push(c);                                // A Numero is pushed in the stack.
  }
  
  void varPush() {
    Cadena c = (Cadena) prog.elementAt(pc);
    pc = pc + 1;                                // Extra increment in pc.
    pila.push(c);                               // A variable is pushed in the stack
  }
  
  void asgVar() {
    Cadena sy   = (Cadena)    pila.pop();       // Get data from stack (symbol).    
    System.out.println(sy);
    Numero c  = (Numero)  pila.pop();       // Get data from stack (data).
    System.out.println(c);
    Symbol s = symbolTable.lookUpTable(sy.getCadena());                      
    if (s == null) 
      symbolTable.install(sy.getCadena(), null, c, (short) 1);
    else
      symbolTable.update(s, c);
  }
  
  void asgVarCoord() {    //variable coordenada
        Cadena sy = (Cadena) pila.pop();       // Get data from stack (symbol).     
        System.out.println(sy);
        Coordenada coord = (Coordenada) pila.pop();       // Get data from stack (data as Coord).
        System.out.println(coord);
		    System.out.println("en asgVar DV="+DireccionesVar);
        Symbol s = symbolTable.lookUpTable(sy.getCadena());
		
        if (s == null) {
            symbolTable.install2(sy.getCadena(), coord, DireccionesVar);
        } else {
            symbolTable.installExistente(s.getSymbolName(), coord, DireccionesVar);
        }
    }
  
  void getVarValue() {
    Cadena sym  = (Cadena) pila.pop();          // Get data from stack (symbol).
    System.out.println(sym);
    Symbol s    = symbolTable.lookUpTable(sym.getCadena());
    
    if (s == null) 
      System.err.println("Undefined variable");
    else {
	  System.out.println("El nombre de la variable es: " + s.getSymbolName());
	  if(s.getSymbolName().equals("DIST")){
	  	socket = new SocketCliente();
		socket.conectarServidor();
		socket.enviarMensaje("ultrasonico");  
 		String recibido = socket.recibirMensaje();
		System.out.println("La distancia obtenida es: " + recibido);
		float distancia = (float) Double.parseDouble(recibido);
		s.setData(new Numero(distancia));
		pila.push(s.getData());
		socket.enviarMensaje("FINALIZADO");
		socket.cerrarSocket();  
		 
	  }	
      else
	  	pila.push(s.getData());
	}
  }
  
  void getVarCoordValue() {
        Cadena sym = (Cadena) pila.pop();          // Get data from stack (symbol).
     	System.out.println(sym);
        Symbol s = symbolTable.lookUpTable(sym.getCadena());
		//banderaVar=false;
        if (s == null) {
            System.err.println("Undefined variable");
        } else {
            pila.push(s.getData2());
			System.out.println("Antes de concatenar="+Direcciones);
			concatenaDirecc(s.getCoordenadas());
            System.out.println("Esta variable tiene las siguientes coordenadas: "+s.getCoordenadas());
        }
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
    Numero c  = (Numero)  pila.pop();

    /* Invocation of the method */
    String fName    = s.getFunctionName();                                    
    Numero res    = new Numero(0);
    Functions f     = new Functions(); 
    Class cl        = f.getClass();
    Class[] cArg    = new Class[1];
    cArg[0]         = res.getClass();    
    Object param[]  = new Object[1];

    /* Obtaining the method */
    try {
      Method method   = cl.getMethod(fName, cArg);      
      param[0]        = c;
      res             = (Numero) method.invoke(f, param);
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
  
  void printNumber() {
	  Numero c = (Numero) pila.pop();
	  System.out.println("Result: ");
    c.imprimeNumero();
  }
  
  void add() {
    Numero c1, c2;
    c1 = (Numero) pila.pop();
    c2 = (Numero) pila.pop();
    pila.push(new Numero(c1.real+c2.real));    
  }
  
  void sub() {
    Numero c1, c2;
    c2 = (Numero) pila.pop();
    c1 = (Numero) pila.pop();
    pila.push(new Numero(c1.real-c2.real));    
  }
  
  void mult() {
    Numero c1, c2;
    c1 = (Numero) pila.pop();
    c2 = (Numero) pila.pop();
    pila.push(new Numero(c1.real*c2.real));    
  }
  
  void div() {
    Numero c1, c2;
    c1 = (Numero) pila.pop();
    c2 = (Numero) pila.pop();
    pila.push(new Numero(c1.real/c2.real));    
  }
  
  void pow() {
    Cadena powN = (Cadena)    pila.pop();
    Numero c  = (Numero)  pila.pop();
    pila.push(c);
  }
 
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
  void execute(int offset) {
	String inst;
	String tipoOp;  
	
	System.out.println(banderaVar);
    System.out.println("el offset es de: "+offset);
    System.out.println("size stack: "+prog.size());
    

    System.out.println(".:: Generated Program ::. ");
    System.out.println("Program Size: = " + prog.size());
    for (pc = 0; pc < prog.size(); pc = pc + 1) {
		System.out.println("PC = " + pc + ". Instruction: " + prog.elementAt(pc));
		if(banderaVar == false){
				try{
					String aux = (String) prog.elementAt(pc); 
					tipoOp = aux;
				}catch(ClassCastException e){
					String aux = "" + prog.elementAt(pc);
					tipoOp = aux;
					//System.out.println("Nombre var: " + tipoOp);
					if(tipoOp.startsWith("C")){
						//Obtener info variable
						Cadena sym = (Cadena)prog.elementAt(pc);
						//System.out.println(sym);
						Symbol s = symbolTable.lookUpTable(sym.getCadena());
						if (s == null) {
							System.err.println("Undefined variable");
						} else {
							if(s.getCoordenadas()!=null){
								ContenidoVariables.add(s.getCoordenadas());
								System.out.println("Esta variable tiene las siguientes coordenadas: "+s.getCoordenadas());
							}
						}
					}	
				}
				if(tipoOp.equals("sumafinal") || tipoOp.equals("restafinal"))
					TipoOperacion.add(tipoOp);
				if(tipoOp.equals("getVarCoordValue") && bandera==1){
					signoVariables.add("sumafinal");
					bandera=0;
				}
				if(tipoOp.equals("getVarCoordValue")){
					bandera=1;
				}
				if(bandera==1 && ( tipoOp.equals("sumafinal") || tipoOp.equals("restafinal") )){
					signoVariables.add(tipoOp);
					bandera=0;
				}
				
					
		}
    }
    //System.out.println("========================================TipoOperacion"+TipoOperacion);
	//System.out.println("========================================signoVariables"+signoVariables);
    //String inst;
	pc = offset;
      //pc=0;
    while (true) {         
		try {          
			if (pc >= prog.size())
            	break; 
          
			inst = (String) prog.elementAt(pc);         // We get the instruction at pc.
			//System.out.println("===========================");
          	System.out.println("------------------>>>Inst a ejecutar: " + inst);
			//System.out.println("===========================");
			//System.out.println("Bandera Var: " + banderaVar);
			if (inst.equals("STOP")){                    // The whole program has been executed.
				System.out.println("------------------>>>STOP, termina el programa.");
				if(banderaVar == false){
					banderaOp=0;
					bandera=0;
  					index = 0;
  					index2 = 0;
  					indiceInicio = 0;
					TipoOperacion.clear();
					signoVariables.clear();
					ContenidoVariables.clear();
				}
				break;
			}
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//System.out.println("INDEX: " + index);
			//System.out.println("INDEX2: " + index2);
			
			if(inst.equals("getVarCoordValue") && banderaVar == false){
				//System.out.println("Estoy en getVarCoordValue");
				socket = new SocketCliente();
				socket.conectarServidor();
				String cadena="";
				if(signoVariables.get(index2).equals("sumafinal")){
					cadena = ContenidoVariables.get(index2);
					//System.out.println(cadena);
					//System.out.println(cadena.length());
					for(int i = 0 ; i< cadena.length(); i++){
						if(i%2==0){
							System.out.println("Dato enviando: "+cadena.charAt(i)+"");
							socket.enviarMensaje(cadena.charAt(i)+"");
							String recibido = socket.recibirMensaje();
						}
					}	
				}
				else{
					cadena = ContenidoVariables.get(index2);
					//System.out.println(cadena);
					//System.out.println(cadena.length());
					for(int i = 0 ; i< cadena.length(); i++){
						if(i%2==0){
							socket.enviarMensaje(cadena.charAt(i)+"");
							String recibido = socket.recibirMensaje();			
						}
					}
				}
				socket.enviarMensaje("FINALIZADO");
				socket.cerrarSocket();
				index2++;
			}
			
			
			if(banderaVar == false){
				if(inst.equals("comienza") || inst.equals("norte") || inst.equals("sur") || inst.equals("este") || inst.equals("oeste") || inst.equals("up") || inst.equals("down")){
					socket = new SocketCliente();
					socket.conectarServidor();
					if(indiceInicio==0){
						socket.enviarMensaje(inst);
						String recibido = socket.recibirMensaje();
						indiceInicio++;
					}
					else{
						if(inst.equals("comienza")){
							//System.out.println("Hay un comienza....");
							//System.out.println("========================"+TipoOperacion);
							if(TipoOperacion.isEmpty()){	
								banderaOp=0;
							}
							else{
								//System.out.println("Hay una operacion de tipo:" + TipoOperacion.get(index));
								if(TipoOperacion.get(index).equals("sumafinal")){
									banderaOp=0;
								}
								else{
									banderaOp=1;
								}
								//System.out.println("La bandera está en el estado : "+banderaOp);
								index++;
							}
						}
						else{
							if(banderaOp == 0){
								socket.enviarMensaje(inst);
							}
							else{
								switch(inst){
										case "norte":
											socket.enviarMensaje("sur");
											break;
										case "sur":
											socket.enviarMensaje("norte");
											break;
										case "este":
											socket.enviarMensaje("oeste");
											break;
										case "oeste":
											socket.enviarMensaje("este");
											break;
										default:
											socket.enviarMensaje(inst);
											break;
								}
							}
							String recibido = socket.recibirMensaje();
						}
					}
					socket.enviarMensaje("FINALIZADO");
					socket.cerrarSocket();
						
				}	
			}
			//System.out.println("INDEX después del proceso: " + index);
			//System.out.println("INDEX2 después del proceso: " + index2);
			
			
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			pc = pc + 1;                                // Normal Increment.         
			c = this.getClass();
			metodo = c.getDeclaredMethod(inst, null);   // Get method of this class.
			metodo.invoke(this, null); 
          
		} catch(NoSuchMethodException e){
				System.out.println("No method " + e);
        } catch(InvocationTargetException e){
				  e.printStackTrace();
        } catch(IllegalAccessException e){
				e.printStackTrace();
        }
		}
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////   
  /* Code of while */
  void whileCode(){
    boolean d;
    int savepc = pc;    
    execute(savepc + 2);	/* condition */
    d = ((Boolean)pila.pop()).booleanValue();
    System.out.println("desde whileCode d es:"+d);
    while (d) {      
      execute(((Integer)prog.elementAt(savepc)).intValue()); // statement      
      execute(savepc + 2); // condition
      d = ((Boolean)pila.pop()).booleanValue();  
      System.out.println("(2) desde whileCode d es:"+d);
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
  
  
  /* Code of for */
  void forCode(){
    boolean d;
    int savepc = pc;    
    execute(((Integer)prog.elementAt(savepc)).intValue()); // Init of var
    execute(((Integer)prog.elementAt(savepc + 1)).intValue());	/* condition */
    d = ((Boolean)pila.pop()).booleanValue();
    
    while (d) {      
      execute(((Integer)prog.elementAt(savepc + 3)).intValue()); // statement
      execute(((Integer)prog.elementAt(savepc + 2)).intValue()); // increment
      execute(((Integer)prog.elementAt(savepc + 1)).intValue());	/* condition */
      
      d = ((Boolean)pila.pop()).booleanValue();      
    }
    
    pc = ((Integer)prog.elementAt(savepc + 4)).intValue();
   }

  
   /* Conditional operations functions */
  void eq() {
    Numero c1, c2;
    c1 = (Numero) pila.pop();
    c2 = (Numero) pila.pop();

    boolean r = c1.equals(c1, c2);
    pila.push(r);
  }

  void neq() {
    Numero c1, c2;
    c1 = (Numero) pila.pop();
    c2 = (Numero) pila.pop();

    boolean r = c1.equals(c1, c2);
    pila.push(!r);
  }
  
  void gt() {
    Numero c1, c2;
    c2 = (Numero) pila.pop();
    c1 = (Numero) pila.pop();    
    pila.push(c1.greaterThan(c1, c2));
  }
  
  void ge() {
    Numero c1, c2;
    c2 = (Numero) pila.pop();
    c1 = (Numero) pila.pop();    
    pila.push(c1.greaterOrEqualsThan(c1, c2));
  }
  
  void lt() {
    Numero c1, c2;
    c2 = (Numero) pila.pop();
    c1 = (Numero) pila.pop();    
    pila.push(c1.lessThan(c1, c2));
  }
  
  void le() {
    Numero c1, c2;
    c2 = (Numero) pila.pop();
    c1 = (Numero) pila.pop();    
    pila.push(c1.lessOrEqualsThan(c1, c2));
  }
  
  /* Functions */
  void procret(){
    Marco m = (Marco) marcos.peek();
    
    ret();
  }
  
  void define(Symbol s){
    s.defn    = progBase;
    symbolTable.install(s);
    progBase  = prog.size();
   }
  
  void ret() {
    Marco m = (Marco) marcos.peek();
    
    for (int i = 0; i< m.nargs; i++)
       pila.pop();  // Se quitan los argumentos de la pila
    
    pc = m.retpc;  // Direccion de retorno
    marcos.pop();  // Se quita de la pila de llamadas
    returning = true;
   }	
  
  void call(){
    Symbol s;
    Marco m;
                
    s = (Symbol)prog.elementAt(pc);
    s = symbolTable.lookUpTable(s.getSymbolName());      
    m = new Marco(s, pc + 2, pila.size() - 1, Integer.parseInt((String)prog.elementAt(pc+1)));
    marcos.push(m);  
    
    int offset = 0;
    if (((String)prog.elementAt(s.defn)).equals("STOP"))
      offset++;
    
    execute(s.defn + offset);
    returning = false;
   }
  
  int getarg(){
      Marco m   = (Marco) marcos.peek();      
      int nargs = Integer.parseInt((String) prog.elementAt(pc));
      pc = pc + 1;
      if(nargs > m.nargs)
         System.out.println(m.s.getSymbolName() +" argumentos insuficientes");
      return m.argn + nargs - m.nargs;
   }
  
   void arg() {      
      Numero c;
      c = (Numero) pila.elementAt(getarg());
      pila.push(c);
   }
  
   void funcret(){
      Numero c;
      c = (Numero) pila.pop();
      ret();
      //pila.push(o);
      pila.push(c);
   }
  
  String getElementProgBase() {
    return (String) prog.elementAt(progBase);
  }
  
  /*Métodos de las coordenadas*/
  ///////////////////////////////////////////////////////////////////////////////////////////////////
	void concatenarCoordenadas(){
		DireccionesVar.clear();
		System.out.println("Direcciones Final expresión final: "+DireccionesFinal);
		for(int i=0;i<DireccionesFinal.size();i++){
			DireccionesVar.add(DireccionesFinal.get(i)+",");
		}
		System.out.println("Direcciones Var después de la concatenación en la expresión final: "+DireccionesVar);
		
		DireccionesFinal.clear();
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////

  
  void printCoord() {
        Coordenada c = (Coordenada) pila.pop();
        System.out.println("Resultado: x=" + c.getX() + " y=" + c.getY());
    }

    void limpiarDireccionesVar() {
		System.out.println("cleaned");
        DireccionesVar.clear();
    }

    void concatenaDirecc(String datos) {
		banderaLimpiar=1;
		String[] stringSplit = datos.split(",");
        for (int i = 0; i < stringSplit.length; i++)
            Direcciones.add(stringSplit[i] + "");
		System.out.println("Despues de concatenar a Direcciones la variable = "+Direcciones);
    }

    void imprimeVar() {
		String nombre=(String) pila.pop();
		Coordenada c=(Coordenada) pila.pop();
        System.out.println("Coordenadas finales de la variable : x:" + c.getX() + " y:" + c.getY());
        /*Lienzo ejex = new Lienzo(DireccionesVar);
        JFrame ventana = new JFrame("Ruta de variable");
        ventana.getContentPane().add(ejex);
        ventana.setSize(500, 500);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setVisible(true);
        ventana.pack();*/
		
    }

//============================================================================================
    void imprime() {
        //JPanel panel = new JPanel(new GridLayout());    //debe ponerse GridLayout, sino no se mostrará
        Coordenada c = (Coordenada) pila.pop();
        System.out.println("Coordenadas finales: x:" + c.getX() + " y:" + c.getY());
        //Lienzo ejex = null;
        //ejex = new Lienzo(DireccionesFinal);      		//--------------------->>>>>>>>    Revisar		(DireccionesFinalk)<<<  
        System.out.println("Lienzo recibe: " + DireccionesFinal);
        /*JFrame ventana = new JFrame("Ruta final");
        //ventana.getContentPane().add(ejex);     //agregamos a ventana Lienzo, que hereda Canvas
        panel.add(ejex);
        ventana.getContentPane().add(panel);
        ventana.setSize(2000, 2000);
        
        
        JScrollPane paneScr = new JScrollPane(panel);
        ventana.add(paneScr);
        //ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //comentado para que al cerrar el frame no se cierre la aplicación
        
        
        ventana.setVisible(true);
        ventana.pack();*/
    }

    void suma() {
		Coordenada c2=(Coordenada) pila.pop();
		Coordenada c1=(Coordenada) pila.pop();
        Coordenada res = new Coordenada(0, 0);
        res.x = c1.x + c2.x;
        res.y = c1.y + c2.y;
        pila.push(res);
		System.out.println("Direcciones en la suma de las coordenadas: " + Direcciones);
		System.out.println("Direcciones en la suma de las coordenadas: " + DireccionesVar);
    }

    void comienza() {
        Coordenada c = new Coordenada(0, 0);
        //Direcciones.clear();
        if (banderaLimpiar == 0) {
            DireccionesFinal.removeAll(DireccionesFinal);
            Direcciones.removeAll(Direcciones);
            banderaLimpiar++;
        }
        if (banderaVar) {
            DireccionesVar.add("c,");
        } else {
            Direcciones.add("c");
        }
        pila.push(c);
    }

    void norte() {
        Coordenada Coord = new Coordenada(0, 1);
        if (banderaVar) {
            DireccionesVar.add("n,");
			System.out.println("var added");
        } else {
            Direcciones.add("n");
			System.out.println("var not added");
        }
        pila.push(Coord);
    }

    void sur() {
        Coordenada Coord = new Coordenada(0, -1);
        if (banderaVar) {
            DireccionesVar.add("s,");
        } else {
            Direcciones.add("s");
        }
        pila.push(Coord);
    }

    void este() {
        Coordenada Coord = new Coordenada(1, 0);
        if (banderaVar) {
            DireccionesVar.add("e,");
        } else {
            Direcciones.add("e");
        }
        pila.push(Coord);
    }

    void oeste() {
        Coordenada Coord = new Coordenada(-1, 0);
        if (banderaVar) {
            DireccionesVar.add("o,");
        } else {
            Direcciones.add("o");
        }
        pila.push(Coord);
    }

    void noreste() {
        Coordenada Coord = new Coordenada(1, 1);
        if (banderaVar) {
            DireccionesVar.add("ne,");
        } else {
            Direcciones.add("ne");
        }
        pila.push(Coord);
    }

    void noroeste() {
        Coordenada Coord = new Coordenada(-1, 1);
        if (banderaVar) {
            DireccionesVar.add("no,");
        } else {
            Direcciones.add("no");
        }
        pila.push(Coord);
    }

    void sureste() {
        Coordenada Coord = new Coordenada(1, -1);
        if (banderaVar) {
            DireccionesVar.add("se,");
        } else {
            Direcciones.add("se");
        }
        pila.push(Coord);
    }

    void suroeste() {
        Coordenada Coord = new Coordenada(-1, -1);
        if (banderaVar) {
            DireccionesVar.add("so,");
        } else {
            Direcciones.add("so");
        }
        pila.push(Coord);
    }

    void setBanderaVariable(boolean valor) {
        this.banderaVar = valor;
    }
	
	public void restablecerBandera(){
		banderaLimpiar=0;
	}

    void up() {
        Coordenada Coord = new Coordenada(0, 0);
        if (banderaVar) {
            DireccionesVar.add("u,");
        } else {
            Direcciones.add("u");
        }
        pila.push(Coord);
    }

    void down() {
        Coordenada Coord = new Coordenada(0, 0);
        if (banderaVar) {
            DireccionesVar.add("d,");
        } else {
            Direcciones.add("d");
        }
        pila.push(Coord);
    }

    void sumafinal() {
		Coordenada c2=(Coordenada) pila.pop();
		Coordenada c1=(Coordenada) pila.pop();
        Coordenada res = new Coordenada(0, 0);
        res.x = c1.x + c2.x;
        res.y = c1.y + c2.y;
        //Mandar a el arraylistFinal
        for (int i = 0; i < Direcciones.size(); i++) {
            if (i == 0 && Direcciones.get(i).equals("c")) {
                DireccionesFinal.clear();
                DireccionesFinal.add(Direcciones.get(i));
            }

            if (!Direcciones.get(i).equals("c")) {
                DireccionesFinal.add(Direcciones.get(i));
            }
        }
        pila.push(res);
    }

    void restafinal() {
		Coordenada c2=(Coordenada) pila.pop();
		Coordenada c1=(Coordenada) pila.pop();
        int bandera = 0;
        Coordenada res = new Coordenada(0, 0);
        res.x = c1.x - c2.x;
        res.y = c1.y - c2.y;
        //Mandar a el arraylistFinal
        for (int i = 0; i < Direcciones.size(); i++) {
            if (i == 0 && Direcciones.get(i).equals("c")) {
                DireccionesFinal.clear();
                DireccionesFinal.add(Direcciones.get(i));
            }
            if (!Direcciones.get(i).equals("c") && bandera == 1) {
                DireccionesFinal.add(Direcciones.get(i));
            }
            if (Direcciones.get(i).equals("c")) {
                bandera++;
            }
            if (bandera == 2) {		//faltan las negaciones para ne,no,se,etc
                if (Direcciones.get(i).equals("n")) 
                    DireccionesFinal.add("s");          
                if (Direcciones.get(i).equals("s")) 
                    DireccionesFinal.add("n");                
                if (Direcciones.get(i).equals("e")) 
                    DireccionesFinal.add("o");                
                if (Direcciones.get(i).equals("o")) 
                    DireccionesFinal.add("e");                
            }
        }
        pila.push(res);
    }

    void execute(boolean delete) {
		int index = 0;
		int bandera=0;
		int index2 = 0;
		System.out.println("ddd");
		System.out.println("BanderaVar : " + banderaVar);
		if(banderaVar == false){
			socket = new SocketCliente();
			socket.conectarServidor();
		}
		
        /* First delete previous code */
        if (delete) {
            int limit = prog.indexOf("STOP");
            for (int i = 0; i <= limit; i++) // Remove until limit is reached.
            {
                prog.remove(0);
            }
        }

        String inst;
		String tipoOp;
        System.out.println(".:: Generated Program ::. ");
        System.out.println("Program Size: = " + prog.size());
		for (pc = 0; pc < prog.size(); pc = pc + 1) {
            System.out.println("PC = " + pc + ". Instruction: " + prog.elementAt(pc));
			System.out.println("HOLA");
			if(banderaVar == false){
				try{
					String aux = (String) prog.elementAt(pc); 
					tipoOp = aux;
				}catch(ClassCastException e){
					String aux = "" + prog.elementAt(pc);
					tipoOp = aux;
					//System.out.println("Nombre var: " + tipoOp);
					if(tipoOp.startsWith("C")){
						//Obtener info variable
						Cadena sym = (Cadena)prog.elementAt(pc);
						//System.out.println(sym);
						Symbol s = symbolTable.lookUpTable(sym.getCadena());
						if (s == null) {
							System.err.println("Undefined variable");
						} else {
							ContenidoVariables.add(s.getCoordenadas());
							System.out.println("Esta variable tiene las siguientes coordenadas: "+s.getCoordenadas());

						}
					}
					
				}
				if(tipoOp.equals("sumafinal") || tipoOp.equals("restafinal"))
					TipoOperacion.add(tipoOp);
				if(tipoOp.equals("getVarCoordValue")){
					bandera=1;
				}
				if(bandera==1 && ( tipoOp.equals("sumafinal") || tipoOp.equals("restafinal") )){
					signoVariables.add(tipoOp);
					bandera=0;
				}
					
			}
        }
		
		System.out.println(TipoOperacion);
		System.out.println(signoVariables);

        pc = 0;
        while (true) {
            try {
                inst = (String) prog.elementAt(pc);         // We get the instruction at pc.
                if (inst.equals("STOP")) // The whole program has been executed.
                {
					if(banderaVar == false){
						socket.enviarMensaje("FINALIZADO");
						socket.cerrarSocket();
						banderaOp=0;
						TipoOperacion.clear();
						signoVariables.clear();
						ContenidoVariables.clear();
					}		
                    break;
                }
				/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				if(inst.equals("comienza") || inst.equals("norte") || inst.equals("sur") || inst.equals("este") || inst.equals("oeste") || inst.equals("up") || inst.equals("down")){
					
					if(banderaVar == false){
						if(pc==0){
							socket.enviarMensaje(inst);
							String recibido = socket.recibirMensaje();
						}
						else{
							if(inst.equals("comienza")){
								System.out.println("Hay un comienza");
								System.out.println("Hay una operacion de tipo:" + TipoOperacion.get(index));
								if(TipoOperacion.get(index).equals("sumafinal")){
									banderaOp=0;
								}
								else{
									banderaOp=1;
								}
								System.out.println("La bandera está en el estado : "+banderaOp);
								index++;
							}
							else{
								if(banderaOp == 0){
									socket.enviarMensaje(inst);
								}
								else{
									switch(inst){
										case "norte":
											socket.enviarMensaje("sur");
											break;
										case "sur":
											socket.enviarMensaje("norte");
											break;
										case "este":
											socket.enviarMensaje("oeste");
											break;
										case "oeste":
											socket.enviarMensaje("este");
											break;
										default:
											socket.enviarMensaje(inst);
											break;
									}
								}
								String recibido = socket.recibirMensaje();
							}
						}
						
					}
				}
				String cadena="";
				if(inst.equals("getVarCoordValue")){
					if(signoVariables.get(index2).equals("sumafinal")){
						cadena = ContenidoVariables.get(index2);
						System.out.println(cadena);
						System.out.println(cadena.length());
						for(int i = 0 ; i< cadena.length(); i++){
							if(i%2==0){
								System.out.println("Dato enviando: "+cadena.charAt(i)+"");
								socket.enviarMensaje(cadena.charAt(i)+"");
								String recibido = socket.recibirMensaje();
							}
						}
						
					}
					else{
						cadena = ContenidoVariables.get(index2);
						System.out.println(cadena);
						System.out.println(cadena.length());
						for(int i = 0 ; i< cadena.length(); i++){
							if(i%2==0){
								socket.enviarMensaje(cadena.charAt(i)+"");
								String recibido = socket.recibirMensaje();			
							}
						}
					}
					//String recibido = socket.recibirMensaje();
					index2++;
				}
				
				
				
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				
                pc = pc + 1;                                // Normal Increment.         
                c = this.getClass();
                metodo = c.getDeclaredMethod(inst, null);   // Get method of this class.
                metodo.invoke(this, null);
            } catch (NoSuchMethodException e) {
                System.out.println("No method " + e);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                System.out.println(e);
            }
        }
		Direcciones.clear();
    }

}
