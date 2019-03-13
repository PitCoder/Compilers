package SocketCliente2;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Principal {
        public static void main(String[] args) {
            SocketCliente socket = new SocketCliente();
        BufferedReader br2 = new BufferedReader (new InputStreamReader(System.in));
        socket.conectarServidor();
        while(true){
            System.out.println("Escribe un mensaje para enviar o \"FINALIZADO&/CERRAR&\" para terminar.");
            System.out.print("Mensaje: ");
            String msj = "";
            try{
                msj = br2.readLine();
            }catch (Exception e){
                e.printStackTrace();
            }
            socket.enviarMensaje(msj);
            if(msj.compareToIgnoreCase("FINALIZADO")==0 || msj.compareToIgnoreCase("CERRAR")==0){
                System.out.println("Termina la aplicacion");
                socket.cerrarSocket();
                break;
            }else{
                String recibido = socket.recibirMensaje();
            }
            
        }
        System.out.println("Socket cerrado, finalizando programa.");
    }
}
