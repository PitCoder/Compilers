#define IM1 40 // Pin de Control 1 para Motor 1
#define IM2 42 // Pin de Control 2 para Motor 1
#define IM3 44 // Pin de Control 1 para Motor 2
#define IM4 46 // Pin de Control 2 para Motor 2

#define TRIG 3  // Pin del trigger del sensor ultrasonico
#define ECHO 2  // Pin del echo del sensor ultrasonico

#define DELAY 250
#define DELAY_VUELTA 400
/* MAC de HC05: 98:D3:32:71:03:AE */

void setup() {
  Serial.begin(9600);   // Inicializacion para el BT

  /* Configuracion de pines */
  pinMode(IM1, OUTPUT);
  pinMode(IM2, OUTPUT);
  pinMode(IM3, OUTPUT);
  pinMode(IM4, OUTPUT);

  pinMode(TRIG, OUTPUT);
  pinMode(ECHO, INPUT);
}

void loop() {
  if (Serial.available() > 0) {  // Hay conexion BT disponible
    int data = Serial.read();  // Se lee el valor
    realizaAccion(data); // Se realiza la accion correspondiente
    Serial.flush();
  }
}

/* Funciones personales */
void realizaAccion(int accion) {
  int distancia;
  switch (accion) {
    case 'n':
      adelante();
      Serial.println("Adelante");
      break;
    case 'e':
      derecha();
      Serial.println("Derecha");
      break;
    case 's':
      atras();
      Serial.println("Atras");
      break;
    case 'o':
      izquierda();
      Serial.println("Izquierda");
      break;
    case 'd':
      distancia = getDistancia();
      Serial.println(distancia);
      break;
    default:
      Serial.println(accion);
      break;
  }
}
/* Accion 1: Ir adelante */
void adelante() {
  configuracionMotores(HIGH, LOW, HIGH, LOW); // Configuracion de giros de los motores
  delay(DELAY);
  detiene();
}

/* Accion 2: Ir a la derecha */
void derecha() {
  configuracionMotores(HIGH, LOW, LOW, LOW);  // Configuracion de giros de los motores
  delay(DELAY_VUELTA);
  detiene();
}

/* Accion 3: Ir a atras */
void atras() {
  configuracionMotores(LOW, HIGH, LOW, HIGH);  // Configuracion de giros de los motores
  delay(DELAY);
  detiene();
}

/* Accion 4: Ir a la izquierda */
void izquierda() {
  configuracionMotores(LOW, LOW, HIGH, LOW);  // Configuracion de giros de los motoress
  delay(DELAY_VUELTA);
  detiene();
}

/* Accion 5: Calculo de distancia */
int getDistancia() {
  long duration;
  int distance;

  digitalWrite(TRIG, LOW);
  delayMicroseconds(2);
  digitalWrite(TRIG, HIGH);
  delayMicroseconds(10);
  digitalWrite(TRIG, LOW);

  duration = pulseIn(ECHO, HIGH);

  distance = (duration * 0.034) / 2;
  return (int) distance;
}

/* Funciones de ayuda */
void configuracionMotores(int valM1A, int valM1B, int valM2A, int valM2B) {
  /* Configuracion Motor 1*/
  digitalWrite(IM1, valM1A);
  digitalWrite(IM2, valM1B);

  /* Configuracion Motor 2 */
  digitalWrite(IM3, valM2A);
  digitalWrite(IM4, valM2B);
}

void detiene() {
  configuracionMotores(LOW, LOW, LOW, LOW);
}




