/**
   Demo for 02 - Blinky

   Toggles the LED on and off in its own task/thread.

   Date: December 3, 2020
   Author: Shawn Hymel
   Modificado por Tomeu Estrany
   License: 0BSD
*/

// Use only core 1 for demo purposes
#if CONFIG_FREERTOS_UNICORE
static const BaseType_t app_cpu = 0;
#else
static const BaseType_t app_cpu = 1;
#endif

// AquÃ­ se define la macro LED_BUILTIN = 21 que es el pin
// que usaremos nosotros para el led cuando usemos el puerto serie
// ya que nuestro esp32 solo tiene un led interno (el 1) e interfiere con el
// puerto serie.

//#define LED_BUILTIN 21
#define LED_BUILTIN 21
// Pins
static const int led_pin = LED_BUILTIN;
// LED rates
static const int rate_1 = 500; //ms
static const int rate_2 = 323; //ms

// Our task: blink an LED
void toggleLED_1(void *parameter) {
  while (1) {
    digitalWrite(led_pin, HIGH);
    vTaskDelay(rate_1 / portTICK_PERIOD_MS);
    digitalWrite(led_pin, LOW);
    vTaskDelay(rate_1 / portTICK_PERIOD_MS);
  }
}
// Our task: blink an LED
void toggleLED_2(void *parameter) {
  while (1) {
    digitalWrite(led_pin, HIGH);
    vTaskDelay(rate_2 / portTICK_PERIOD_MS);
    digitalWrite(led_pin, LOW);
    vTaskDelay(rate_2 / portTICK_PERIOD_MS);
  }
}





void setup() {

  // Configure pin
  pinMode(led_pin, OUTPUT);

  // Task to run forever
  xTaskCreatePinnedToCore(  // Use xTaskCreate() in vanilla FreeRTOS
    toggleLED_1,    // Function to be called
    "Toggle LED", // Name of task
    1024,         // Stack size (bytes in ESP32, words in FreeRTOS)
    NULL,         // Parameter to pass to function
    1,            // Task priority (0 to configMAX_PRIORITIES - 1)
    NULL,         // Task handle
    app_cpu);     // Run on one core for demo purposes (ESP32 only)

  xTaskCreatePinnedToCore(toggleLED_2, "Toggle 2", 1024, NULL, 1, NULL, app_cpu);
  //xTaskCreatePinnedToCore(changeRate, "Change Freq", 1024, NULL, 1, NULL, app_cpu);
  vTaskDelete ( NULL );
  // If this was vanilla FreeRTOS, you'd want to call vTaskStartScheduler() in
  // main after setting up your tasks.
}

void loop() {
  // Do nothing

  // setup() and loop() run in their own task with priority 1 in core 1
  // on ESP32
}
