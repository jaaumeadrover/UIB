 /**
 * Práctica Final: EL PROBLEMA DELS FILOSOFS
 * 
 * Autores: 
 * - Jaume Adrover Fernandez 
 * - Joan Balaguer Llagostera
 * - Marc Cañellas Gómez
 */

// Només fem servir el nucli app_cpu per simplicitat
// i tenint en compte que alguns esp32 són unicore
// unicore    -> app_cpu = 0
// 2 core  -> app_cpu = 1 (prog_cpu = 0)

#if CONFIG_FREERTOS_UNICORE
  static const BaseType_t app_cpu = 0;
#else
  static const BaseType_t app_cpu = 1;
#endif
//#define INCLUDE_vTaskSuspend    1    //ja està posat per defecte sino descomentar es el temps de espera  
                                      //infinit als semàfors

/*************************** Variables Globals i definicions **************************************/

#define NUM_OF_PHILOSOPHERS 5                         //Nombre de filòsofs
#define MAX_NUMBER_ALLOWED (NUM_OF_PHILOSOPHERS - 1)  // Màxim nombre de filòsofs a l'habitació  (un menys que el total per evitar deadlock)
#define ESPERA 200  //interval d'espera de vTaskDelay

#define FINITO
#define DEADLOCK
#define PROTECTED_SERIAL

enum { TASK_STACK_SIZE = 2048 }; 

static SemaphoreHandle_t bin_sem; // Espera a que los parametros sean leidos
static SemaphoreHandle_t done_sem;  // Notifica al main cuando una tasca finaliza
static SemaphoreHandle_t taula; // Semfor per controlar el nombre de filosofs asseguts a la taula
static SemaphoreHandle_t palillo[NUM_OF_PHILOSOPHERS]; //Array de palillos (semafors)

char buf[50];
/*************************** Tasques **************************************/

//Tasca menjar
void menjar(void *p) {

  int num;

  // Copiam el nombre de filosof i incrementam, el contador del semafor
  num = *(int *)p;
  xSemaphoreGive(bin_sem);

  // El filosof intenta entrar a la taula 
  sprintf(buf, "FILOSOF %i: TOC TOC", num);
  Serial.println(buf);
  
  // Demana permis al semafor taula
  xSemaphoreTake(taula, portMAX_DELAY);
  sprintf(buf, "FILOSOF %i: |ENTRA|", num);
  Serial.println(buf);
  
  // Agafam el palillo de l'esquerra
  xSemaphoreTake(palillo[num], portMAX_DELAY);
  sprintf(buf, "FILOSOF %i: AGAFA PALILLO ESQUERRA", num);
  Serial.println(buf);

  // Add some delay to force deadlock
  vTaskDelay(random(0,ESPERA));

  // Agafam el palillo de la dreta
  xSemaphoreTake(palillo[(num+1)%NUM_OF_PHILOSOPHERS], portMAX_DELAY);
  sprintf(buf, "FILOSOF %i: AGAFA PALILLO DRET", num);
  Serial.println(buf);

  //Pensant abans de menjar
  vTaskDelay(random(0,ESPERA));

  // Menjar
  sprintf(buf, "FILOSOF %i: MENJANT", num);
  Serial.println(buf);
  vTaskDelay(random(0,ESPERA));

  // Deixam el palillo dret
  xSemaphoreGive(palillo[(num+1)%NUM_OF_PHILOSOPHERS]);
  sprintf(buf, "FILOSOF %i: HA TORNAT EL PALILLO DRERT", num);
  Serial.println(buf);

  // Deixam el palillo esquerra
  xSemaphoreGive(palillo[num]);
  sprintf(buf, "FILOSOF %i: HA TORNAT EL PALILLO ESQUERRA", num);
  Serial.println(buf);

  //El filosof notifica al semafor taula que ha acabat i surt
  xSemaphoreGive(taula);
  sprintf(buf, "FILOSOF %i: |SURT|", num);
  Serial.println(buf);

  //Notificam al main que ha finalitzat la tasca i la eliminam
  xSemaphoreGive(done_sem);
  vTaskDelete(NULL);
}


void setup() {

  //char task_name[20];
  
   // Configuram el Serial
  Serial.begin(115200);

  vTaskDelay(random(0,ESPERA));
  Serial.println();
  Serial.println("---Practica Final: EL PROBLEMA DELS FILOSOFS---");

  // Cream els semafors
  bin_sem = xSemaphoreCreateBinary();
  done_sem = xSemaphoreCreateCounting(NUM_OF_PHILOSOPHERS, 0);
  taula = xSemaphoreCreateCounting(MAX_NUMBER_ALLOWED, 4);
  for (int i = 0; i < NUM_OF_PHILOSOPHERS; i++) {
    palillo[i] = xSemaphoreCreateMutex();
  }

  // Cream els filosofs
  for (int i = 0; i < NUM_OF_PHILOSOPHERS; i++) {
    sprintf(buf, "@FILOSOF %i", i);
    Serial.println(buf);
    xTaskCreatePinnedToCore(menjar,
                            buf,
                            TASK_STACK_SIZE,
                            (void *)&i,
                            1,
                            NULL,
                            app_cpu);
    xSemaphoreTake(bin_sem, portMAX_DELAY);
  }


  // Esperam fins que els filosofs acabin de mejar
  for (int i = 0; i < NUM_OF_PHILOSOPHERS; i++) {
    xSemaphoreTake(done_sem, portMAX_DELAY);
  }

  // Say that we made it through without deadlock
  Serial.println("Done! No deadlock occurred!");

}

void loop() {
  // put your main code here, to run repeatedly:

}
