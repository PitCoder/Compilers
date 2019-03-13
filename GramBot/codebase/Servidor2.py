#SERVIDOR
import socket
import time
server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server.bind(("192.168.1.69", 9002))
server.listen(1)
peticion = ""
ordenes = { 'comienza' : "c", 'norte' : "n", 'este' : "e", 'sur' : "s", 'oeste' : "o", "ultrasonico": 'd' }
ordenes2 = { 'c' : "c", 'n' : "n", 'e' : "e", 's' : "s", 'o' : "o" , 'u' : "d" }
while 1:
        print "================================================================================"
        print "Esperando cliente " + str(time.strftime("%I:%M:%S"))
        
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
                        info = ordenes[peticion]
                        if("d" == info):
                           distancia = int(raw_input("Ingresa la distancia : "))
                           print "La distancia a enviar es: " + str(distancia)
                           socket_cliente.send(str(distancia)+"\n")
                        else:
                           socket_cliente.send("Dato recibido.\n")
                    elif ordenes2.has_key(peticion):
                        print ordenes2[peticion]
                        socket_cliente.send("Dato recibido.\n")
                    else:
                        print "Orden incorrecta"
                        socket_cliente.send("Dato recibido.\n")
                    peticion = ""
                  
        if("CERRAR"==resp):
                print "Cerrando el servidor...."
                break
        peticion = ""

print "Servidor finalizado....\n"
    

