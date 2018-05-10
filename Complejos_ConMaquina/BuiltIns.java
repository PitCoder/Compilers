import java.lang.reflect.*;

public class BuiltIns {
  private Functions f     = new Functions();
  private Complejo res    = new Complejo(0,0);

  public void executeBuiltIn(String fName, Complejo c) {
    Class cl        = f.getClass();
    Class[] cArg    = new Class[1];
    cArg[0]         = c.getClass();    
    Object param[]  = new Object[1];

    try {
      Method method   = cl.getMethod(fName, cArg);
      System.out.println("I will execute method: " + method);
      param[0]        = c.getClass();
      res             = (Complejo) method.invoke(f, c);
    } catch (NoSuchMethodException ex) {
    } catch (IllegalAccessException ex) {   
    } catch (InvocationTargetException ex) {
    }
  }

}