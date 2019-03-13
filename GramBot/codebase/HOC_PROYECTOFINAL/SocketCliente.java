import java.net.*;
import java.io.*;

public class SocketCliente {
    InetAddress dir=null;
    String host="192.168.1.69";
    int pto=9002;
    Socket cl;
    PrintWriter pw;
    BufferedReader br;
    BufferedReader br2;
    
    public SocketCliente (){
    }
    
    public void conectarServidor(){
        try{
            dir=InetAddress.getByName(host);
        }catch (UnknownHostException u){
            System.out.println("Dirección no valida");
        }//catch
        try{
            cl = new Socket(dir,pto);
            pw = new PrintWriter(new OutputStreamWriter(cl.getOutputStream()));
            br = new BufferedReader(new InputStreamReader(cl.getInputStream()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void enviarMensaje(String msj){
        pw.println(msj+"&");
	pw.flush();
    }
    
    public String recibirMensaje(){
        try{
            String cadenaRecibida = br.readLine();
            System.out.println("Mensaje recibido: " + cadenaRecibida);
            return cadenaRecibida;
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }
    
    public void cerrarSocket(){
        try{
            pw.close();
            br.close();
            cl.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
