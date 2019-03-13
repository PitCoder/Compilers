#define E1 2 // Pin Enable para Motor 1
#define E2 3 // Pin Enable para Motor 2

#define IM1 30 // Pin de Control 1 para Motor 1
#define IM2 28 // Pin de Control 2 para Motor 1
#define IM3 32 // Pin de Control 1 para Motor 2
#define IM4 34 // Pin de Control 2 para Motor 2

#define TEMP      A0    
#define LUZD      46
#define CLAXON    47
#define CAM       48

int indiceVelocidad = 2;
int velocidades [5] = {0, 159, 191, 223, 255};

/* MAC de HC05: 98:D3:32:71:03:AE */

// defines pins numbers
const int trigPin = 9;
const int echoPin = 10;

// defines variables
long duration;
int distance;

void setup() {
  Serial.begin(9600);   // InicializaciÃ³n para el BT
  
  /* ConfiguraciÃ³n de pines */
  pinMode(E1, OUTPUT);
  pinMode(E2, OUTPUT);

  pinMode(IM1, OUTPUT);
  pinMode(IM2, OUTPUT);
  pinMode(IM3, OUTPUT);
  pinMode(IM4, OUTPUT); 

  pinMode(TEMP, INPUT);
  pinMode(LUZD, OUTPUT);
  pinMode(CLAXON, OUTPUT);
  pinMode(CAM, OUTPUT);

  digitalWrite(CLAXON, LOW);
  digitalWrite(CAM, HIGH);  // Para que no estÃ© grabando en todo momento

  /* Sensor ultrasónico */
  pinMode(trigPin, OUTPUT); // Sets the trigPin as an Output
  pinMode(echoPin, INPUT); // Sets the echoPin as an Input
}

void loop() {
  digitalWrite(trigPin, LOW);
  delayMicroseconds(2);
  
  // Sets the trigPin on HIGH state for 10 micro seconds
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);
  
  // Reads the echoPin, returns the sound wave travel time in microseconds
  duration = pulseIn(echoPin, HIGH);
  
  // Calculating the distance
  distance = duration * 0.034 / 2;

  if (distance > 15)
    adelante();
  else
    derecha();
  
  /*
 if (Serial.available() > 0) {  // Hay conexion BT disponible
  int data = Serial.read();  // Se leen los datos   
  realizaAccion(data); // Se realiza la accion correspondiente      
 }
 */
}

/* Funciones personales */
void realizaAccion(int accion) {
 // Serial.print(accion);
  //Serial.println("X");
  /* ASCII
    0: Comienza, 1: Adelante, 2: Derecha
    3: AtrÃ¡s, 4: Izquierda, 5: Prende Luz
    6: Apaga Luz, 7: Toma foto, 8: SpeedUp
    9: SpeedDown, 10: Claxon
  */
  switch(accion) {
    case 110:
      adelante();
      break;
    case 2:
      derecha();
      break;
    case 3:
      atras();
      break;
    case 4:
      izquierda();
      break;
    case 5:  
      prendeLuces();
      break;
    case 6:
      apagaLuces();
      break;
    case 7:
      tomaFoto();
      break;
    case 8:
      aumentaVelocidad();
      break;
    case 9:
      decrementaVelocidad();
      break;
    case 10:
      tocaClaxon();
      break;
    case 11:
      float temp;
      temp = analogRead(TEMP);
      temp = (5.0 * temp * 100.0) / 1024.0;
      Serial.println("Temp: " + String(temp));
      break;
    default:
      Serial.println("X");
      break;
  }
}
/* AcciÃ³n 1: Ir adelante */
void adelante() {
  velocidadCero();
  configuracionMotores(HIGH, LOW, HIGH, LOW); // ConfiguraciÃ³n de giros de los motores
  ponerVelocidad();                           // Se establece la velocidad a tener los motores    
  delay(2000);
  Serial.println("DELAY");
  detiene();
}

/* AcciÃ³n 2: Ir a la derecha */
void derecha() {
  velocidadCero();
  configuracionMotores(HIGH, LOW, LOW, LOW);  // ConfiguraciÃ³n de giros de los motores
  ponerVelocidad();                           // Se establece la velocidad a tener los motores  
  delay(2000);
  detiene();
}

/* AcciÃ³n 3: Ir a atrÃ¡s */
void atras() {
  velocidadCero();
  configuracionMotores(LOW, HIGH, LOW, HIGH);  // ConfiguraciÃ³n de giros de los motores
  ponerVelocidad();                           // Se establece la velocidad a tener los motores  
  delay(2000);
  detiene();
}

/* AcciÃ³n 4: Ir a la izquierda */
void izquierda() {
  velocidadCero();
  configuracionMotores(LOW, LOW, HIGH, LOW);  // ConfiguraciÃ³n de giros de los motores
  ponerVelocidad();                           // Se establece la velocidad a tener los motores  
  delay(2000);
  detiene();
}

/* AcciÃ³n 5: Prender las luces delanteras */
void prendeLuces() {
  digitalWrite(LUZD, HIGH); // Se pone '1' en el pin LUZD 
}

/* AcciÃ³n 6: Apagar las luces delanteras */
void apagaLuces() {
  digitalWrite(LUZD, LOW);  // Se pone '0' en el pin LUZD
}

/* AcciÃ³n 7: Tomar fotografÃ­a */
void tomaFoto() {
  digitalWrite(CAM, LOW);   // Pulso bajo para tomar foto
  delay(300);               // Retardo requerido para la toma de la foto.
  digitalWrite(CAM, HIGH);  // Regresa a estado normal
}

/* AcciÃ³n 8: Aumento de velocidad */
void aumentaVelocidad() {
  if (indiceVelocidad < 4) {  // VerificaciÃ³n para que no se salga del tamaÃ±o del arreglo
    indiceVelocidad++;        // Aumento del Ã­ndice
  }
}

/* AcciÃ³n 9: Decremento de velocidad */
void decrementaVelocidad() {
  if (indiceVelocidad > 0) {  // VerificaciÃ³n para que no se salga del tamaÃ±o del arreglo
    indiceVelocidad--;        // Decremento del Ã­ndice
  }
}

/* AcciÃ³n 10: Tocar el claxÃ³n */
void tocaClaxon() {
  digitalWrite(CLAXON, HIGH); // Se pone '1' en el pin CLAXON
  delay(800);                 // Retardo para permitir la escucha
  digitalWrite(CLAXON, LOW);  // Se pone '0' en el pin CLAXON
}

/* Funciones de ayuda */
void ponerVelocidad() {
  analogWrite(E1, velocidades[4]); // Run at full speed
  analogWrite(E2, velocidades[4]); // Run at full speed
}

void configuracionMotores(int valM1A, int valM1B, int valM2A, int valM2B) {
  /* ConfiguraciÃ³n Motor 1*/
  digitalWrite(IM1, valM1A);
  digitalWrite(IM2, valM1B);

  /* ConfiguraciÃ³n Motor 2 */
  digitalWrite(IM3, valM2A);
  digitalWrite(IM4, valM2B);
}

void velocidadCero() {
  analogWrite(E1, 0);
  analogWrite(E2, 0);
}
void detiene() {
  configuracionMotores(LOW, LOW, LOW, LOW);
}

