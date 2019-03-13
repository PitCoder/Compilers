#define E1 4 // Pin Enable para Motor 1
#define E2 5 // Pin Enable para Motor 2

#define IM1 34 // Pin de Control 1 para Motor 1
#define IM2 32 // Pin de Control 2 para Motor 1
#define IM3 36 // Pin de Control 1 para Motor 2
#define IM4 38 // Pin de Control 2 para Motor 2

#define LUZD 50 // Pin de Led

int indiceVelocidad = 2;
int velocidades [5] = {0, 159, 191, 223, 255};

/* MAC de HC05:  98:D3:32:71:03:AE */
/* MAC2 de HC05: 98:D3;32:21:3D:DC */

void setup() {
  Serial.begin(9600);   // InicializaciÃ³n para el BT
  
  /* ConfiguraciÃ³n de pines */
  pinMode(E1, OUTPUT);
  pinMode(E2, OUTPUT);

  pinMode(IM1, OUTPUT);
  pinMode(IM2, OUTPUT);
  pinMode(IM3, OUTPUT);
  pinMode(IM4, OUTPUT); 
}

void loop() {
 if (Serial.available() > 0) {  // Hay conexion BT disponible
  int data = Serial.read();  // Se leen los datos   
  realizaAccion(data); // Se realiza la accion correspondiente      
 }
}

/* Funciones personales */
void realizaAccion(int accion) {
 // Serial.print(accion);
  //Serial.println("X");
  /* ASCII
    0: Comienza, 1: Adelante, 2: Derecha
    3: Atras, 4: Izquierda, 5: SpeedUp
    6: Speed Down, 7: Enciende Lu<, 8: Apaga Luz
  */
  switch(accion) {
    case 110 :
      adelante();
      break;
    case 101 :
      derecha();
      break;
    case 115 :
      atras();
      break;
    case 111 :
      izquierda();
      break;
    case 117 :
      aumentaVelocidad();
      break;
    case 100 :
      decrementaVelocidad();
      break;
    case 97 :
      prendeLuces();
      break;
    case 98 :
      apagaLuces();
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
  delay(250);
  Serial.println("DELAY");
  detiene();
}

/* AcciÃ³n 2: Ir a la derecha */
void derecha() {
  velocidadCero();
  configuracionMotores(HIGH, LOW, LOW, LOW);  // ConfiguraciÃ³n de giros de los motores
  ponerVelocidad();                           // Se establece la velocidad a tener los motores  
  delay(250);
  detiene();
}

/* AcciÃ³n 3: Ir a atrÃ¡s */
void atras() {
  velocidadCero();
  configuracionMotores(LOW, HIGH, LOW, HIGH);  // ConfiguraciÃ³n de giros de los motores
  ponerVelocidad();                           // Se establece la velocidad a tener los motores  
  delay(250);
  detiene();
}

/* AcciÃ³n 4: Ir a la izquierda */
void izquierda() {
  velocidadCero();
  configuracionMotores(LOW, LOW, HIGH, LOW);  // ConfiguraciÃ³n de giros de los motores
  ponerVelocidad();                           // Se establece la velocidad a tener los motores  
  delay(250);
  detiene();
}

/* AcciÃ³n 5: Aumento de velocidad */
void aumentaVelocidad() {
  if (indiceVelocidad <= 3) {  // VerificaciÃ³n para que no se salga del tamaÃ±o del arreglo
    indiceVelocidad++;        // Aumento del Ã­ndice
  }
}

/* AcciÃ³n 6: Decremento de velocidad */
void decrementaVelocidad() {
  if (indiceVelocidad > 0) {  // VerificaciÃ³n para que no se salga del tamaÃ±o del arreglo
    indiceVelocidad--;        // Decremento del Ã­ndice
  }
}

/* AcciÃ³n 7: Prender las luces delanteras */
void prendeLuces() {
  digitalWrite(LUZD, HIGH); // Se pone '1' en el pin LUZD 
}

/* AcciÃ³n 8: Apagar las luces delanteras */
void apagaLuces() {
  digitalWrite(LUZD, LOW);  // Se pone '0' en el pin LUZD
}


/* Funciones de ayuda */
void ponerVelocidad() {
  analogWrite(E1, velocidades[indiceVelocidad]); // Run at full speed
  analogWrite(E2, velocidades[indiceVelocidad]); // Run at full speed
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

