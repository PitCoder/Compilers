#SERVIDOR
import socket
import bluetooth

def convierteDato(token):
    tokensVal = ["c", "n", "e", "s", "o", "u", "d", "t", "r", "l", "b", "z"]
    i = 0
    for tokenVal in tokensVal:
        if (token is tokenVal):
            return i
        i += 1
    return -1

server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server.bind(("192.168.1.69", 9002))
server.listen(1)
peticion = ""
ordenes = { 'comienza' : "c", 'norte' : "n", 'este' : "e", 'sur' : "s", 'oeste' : "o" , 'ultrasonico' : "d"}
ordenes2 = { 'c' : "c", 'n' : "n", 'e' : "e", 's' : "s", 'o' : "o" , 'u' : "u", 'd' : "d", 't' : "t", 'f' : "r", 'l' : "l", 'x' : "b"}
btAddr = "98:D3:32:21:3D:DC"                                                    # Direccion del modulo BT
port = 1                                                                                            # Se trabaja por default con el puerto 1

sock = bluetooth.BluetoothSocket( bluetooth.RFCOMM )    # Se crea socket BT
try:
  sock.connect((btAddr, port))
  while 1:
    print "Esperando cliente."
    socket_cliente, datos_cliente = server.accept()
    print "Usuario conectado: " + str(datos_cliente)

    while 1:
      mensaje = socket_cliente.recv(65500) 

      for i in mensaje:
        if(i == '&'):
          break;
        else:
          peticion= peticion + i

      if ("FINALIZADO" == peticion):
        print "Se ha concluido el envio de datos."
        print "Cliente desconectado: " + str(datos_cliente) + "\n"
        #socket_cliente.send("OK.")
        socket_cliente.close()
        resp = ""
        break

      elif ("CERRAR" == peticion):
        print "Cerrando servidor"
        #socket_cliente.send("Servidor finalizado.")
        socket_cliente.close()
        resp = "CERRAR"
        break

      else:
        print "\nLos datos recibidos son: " + str(peticion)

        if ordenes.has_key(peticion):
          print ordenes[peticion]
          #data = convierteDato(ordenes[peticion])  
          #print 'data: ' + str(data) + "-"
          print ">>>>>>>>>>>>>>>>>" + str(ordenes[peticion])
          sock.send(ordenes[peticion]);  

          recDa = "a"
          rcvDat = ""
          while ord(recDa) != 10:
            recDa = sock.recv(1)
            print "===" + str(recDa)
            rcvDat += recDa
          print 'Rcv: ' + rcvDat

        elif ordenes2.has_key(peticion):
          print ordenes2[peticion]
          #data = convierteDato(ordenes[peticion])  
          #print 'data: ' + str(data) + "-"
          print ">>>>>>>>>>>>>>>>>" + str(ordenes2[peticion])
          sock.send(ordenes2[peticion]);  

          recDa = "a"
          rcvDat = ""
          while ord(recDa) != 10:
            recDa = sock.recv(1)
            print "===" + str(recDa)
            rcvDat += recDa
          print 'Rcv: ' + rcvDat        
        
        else:
          print "Orden incorrecta"
        
        socket_cliente.send(str(rcvDat)+"\n")
        peticion = ""
                    
    if("CERRAR"==resp):
      print "Cerrando el servidor...."
      break
  
    peticion = ""
except IOError, exc:
  print 'Alguien mas esta usando el BT'
