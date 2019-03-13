package SocketCliente;

import java.net.*;
import java.io.*;

public class Cliente {
    public static void main(String[] args) {
        try{
            InetAddress dir=null;
            String host="localhost";
            int pto=9001;
            try{
                dir=InetAddress.getByName(host);
            }catch (UnknownHostException u){
                System.out.println("Dirección no valida");
            }//catch
            Socket cl = new Socket(dir,pto);
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(cl.getOutputStream()));
            BufferedReader br = new BufferedReader(new InputStreamReader(cl.getInputStream()));
            BufferedReader br2 = new BufferedReader (new InputStreamReader(System.in));
            while(true){
                System.out.println("Escribe un mensaje para enviar o \"FINALIZADO&/CERRAR&\" para terminar: ");
                System.out.print("Mensaje: ");
                String msj = br2.readLine();
		pw.println(msj+"&");
		pw.flush();
                if(msj.compareToIgnoreCase("FINALIZADO")==0 || msj.compareToIgnoreCase("CERRAR")==0){
                    System.out.println("Termina la aplicacion");
                    pw.close();
                    br.close();
                    cl.close();
                    break;
		}else{
                    String eco=br.readLine();
                    System.out.println("Mensaje recibido: " + eco);
		}//else
            }
            System.out.println("Socket cerrado, finalizando programa.");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
