/*
	AUTORS: Jaume Adrover Fernandez i Joan Balaguer Llagostera
	ENLLAÇ VIDEO: https://www.youtube.com/watch?v=TzAFLIMRGpQ&ab_channel=JoanBalaguer
*/

package main

import (
	"fmt"
	"log"
	"math/rand"
	"time"

	amqp "github.com/rabbitmq/amqp091-go"
)

const (
	dial = "amqp://guest:guest@localhost:5672/"
)

func failOnError(err error, msg string) {
	if err != nil {
		log.Fatalf("%s: %s", msg, err)
	}
}

/*
Mètode que conté les accions pròpies que realitza el client educat
*/
func clientEducat(ch *amqp.Channel, qSushis amqp.Queue, qPermisos amqp.Queue) {
	rand.Seed(time.Now().UnixNano())
	min := 5
	max := 15
	var peces int = rand.Intn(max-min) + min // Nombre de peces que voldrà el client (sempre en demanarà un mínim de 5)

	// prints inicial del client indicant quantes peces vol
	fmt.Printf("\nBon vespre, venc a sopar de sushi\n")
	fmt.Printf("Avui menjaré %d peces\n\n", peces)

	// Executarem el codi d'extracció de missatges tantes vegades com sushis hagi demanat el client
	for i := 0; i < peces; i++ {
		mssg, _, err := ch.Get("permisos", true)            // Extreim i guardam a mssg els missatges que hi han a la coa de permisos
		failOnError(err, "Failed to get a channel message") // Cas d'error

		// Bucle amb el qual garatitzarem que els clients no mengin fins que el cuiner hagi inserit permisos dins la coa de permisos.
		// Fins que el contingut dels missatges de la coa de permisos sigui diferent de null, no podrem començar a extreure missatges de la coa de missatges
		for mssg.Body == nil {
			mssg, _, err = ch.Get("permisos", true)             // actualitzem el valor de la variable mssg per veure si ja hi ha permisos a la coa de permisos
			failOnError(err, "Failed to get a channel message") // Cas d'error
		}

		// Simulem un temps aleatori
		tempsAleatori1 := rand.Intn(3000)
		time.Sleep(time.Duration(tempsAleatori1) * time.Millisecond)

		mssg1, _, err1 := ch.Get("plat", true)               // Extreiem un missatge de la coa de sushis
		failOnError(err1, "Failed to get a channel message") // Cas d'error

		log.Printf("He agafat un %s\n", mssg1.Body) // Print mostrant quina peça ha agafat

		Queue, err := ch.QueueInspect(qSushis.Name)       // Mètode que ens permet saber quina quantitat de missatges que queden dins la coa de permisos
		failOnError(err, "Failed to connect to RabbitMQ") // Cas d'error
		fmt.Printf("Al plat queden %d\n", Queue.Messages) // Print per mostrar la quantitat de sushis que queden al plat (missatges restants a la coa de sushis)

		// Simulem un temps aleatori per fer veure que consumeix la peça de sushi
		tempsAleatori := rand.Intn(3000)
		time.Sleep(time.Duration(tempsAleatori) * time.Millisecond)

	}
	fmt.Printf("Ja estic ple, bona nit")

}

func main() {

	conn, err := amqp.Dial(dial)
	failOnError(err, "Failed to connect to RabbitMQ")
	defer conn.Close()

	ch, err := conn.Channel()
	failOnError(err, "Failed to open a channel")
	defer ch.Close()

	// Declarem la coa de sushis
	qSushis, err := ch.QueueDeclare(
		"plat", // name
		true,   // durable
		false,  // delete when unused
		false,  // exclusive
		false,  // no-wait
		nil,    // arguments
	)
	failOnError(err, "Failed to declare a queue") // Cas d'error

	// Declarem la coa de permisos
	qPermisos, err := ch.QueueDeclare(
		"permisos", // name
		true,       // durable
		false,      // delete when unused
		false,      // exclusive
		false,      // no-wait
		nil,        // arguments
	)
	failOnError(err, "Failed to declare a queue") // Cas d'error

	// Ens permet dir que els missatges des de les coes als consumidors s'enviin de 1 amb 1
	err = ch.Qos(
		1,     // prefetch count
		0,     // prefetch size
		false, // global
	)
	failOnError(err, "Failed to set QoS") // Cas d'error

	// Mètode clientEducat
	clientEducat(ch, qSushis, qPermisos)
}
