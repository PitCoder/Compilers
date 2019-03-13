import bluetooth

def conversionDatos(datos):
	# Se dividen en tokens, dependiendo de su separador
	separador = ';'
	tokens = datos.split(";")
	nuevosTokens = []

	for token in tokens:
		nuevoToken = convierteDato(token)
		nuevosTokens.append(str(nuevoToken))

	return ";".join(nuevosTokens) + ";"

def convierteDato(token):
	tokensVal = ["c", "n", "e", "s", "o", "u", "d", "t", "r", "l", "b"]
	i = 0
	for tokenVal in tokensVal:
		if (token is tokenVal):
			return i
		i += 1

	return -1

""" Main """
btAddr = "98:D3:32:71:03:AE"													# Direccion del modulo BT
port = 1																							# Se trabaja por default con el puerto 1

sock = bluetooth.BluetoothSocket( bluetooth.RFCOMM )	# Se crea socket BT

while (True):
  try:
    sock.connect((btAddr, port))											# Se conecta el BT						    
    data = input('Data: ')														# Aqui estara la entrada del socket
    data = conversionDatos(data)
    sock.send(str(data));															# Se envian los datos
    break;
  except IOError, exc:
    print 'Alguien mas esta usando el BT'							# No se pudo realizar la conexion