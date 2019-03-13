#SERVIDOR
import socket
import bluetooth

def cargaDiccionario(dataSource):
  arch = open(dataSource, 'r')
  contenido = arch.read()
  lineas = contenido.splitlines()
  diccionario = {}
  for linea in lineas:
    item = linea.split(',')
    diccionario[item[0].strip()] = item[1].strip()

  return diccionario

def recepcionDatos(socket_cliente):
  peticion = ""

  while 1:
    mensaje = socket_cliente.recv(65500) 

    for i in mensaje:
      if(i == '&'):
        break;
      else:
        peticion = peticion + i

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
        return ordenes[peticion]
      else:
        print "Orden incorrecta"
        socket_cliente.send("Datos recibidos.\n")

""" Main """ 
# Carga de datos al diccionario 'ordenes'
ordenes = cargaDiccionario('tokens.txt')
server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server.bind(("", 9001))
server.listen(1)
# Socket de BT
btAddr = "98:D3:32:71:03:AE"                          # Direccion del modulo BT
port = 1                                              # Se trabaja por default con el puerto 1
socketBT = bluetooth.BluetoothSocket(bluetooth.RFCOMM)  # Se crea socket BT

while 1:
  # Conexión con Java
  print "Esperando cliente."
  socket_cliente, datos_cliente = server.accept()
  print "Usuario conectado: " + str(datos_cliente)

  # Conexion con Arduino
  try:
    socketBT.connect((btAddr, port))                      # Se conecta el BT
    peticion = recepcionDatos()
    if (peticion != None):                                # Peticion correcta

    else:
      break
  except IOError, exc:
    print 'Alguien mas esta usando el BT'             # No se pudo realizar la conexion