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
Mètode que conté les accions representatives del client maleducat
*/
func clientGangster(ch *amqp.Channel, qSushis amqp.Queue, qPermisos amqp.Queue) {
	// prints iniclia del client gangster
	fmt.Printf("\nBon vespre, venc a sopar de sushi\n")
	fmt.Printf("Ho vull TOT!!!!\n")

	// Primer consumim els missatges de la coa de permisos
	permisos, err := ch.Consume(
		qPermisos.Name, // queue
		"",             // consumer
		true,           // auto-ack
		false,          // exclusive
		false,          // no-local
		false,          // no-wait
		nil,            // args
	)
	failOnError(err, "Failed to register a consumer")

	for d := range permisos {
		// Simulam el temps de seurer-se a la taula
		tempsAleatori := rand.Intn(3000)
		time.Sleep(time.Duration(tempsAleatori) * time.Millisecond)

		missatges, err := ch.QueuePurge(qSushis.Name, false)             // Mètode que ens permet eliminar tots els missatges de la coa de sushis
		failOnError(err, "Failed to delete the queue")                   // Cas d'error
		fmt.Printf("Agafa totes les peces, un total de %d\n", missatges) // Mostram que el gangster agafa tots els sushis que hi hagi en aquell moment
		fmt.Printf("Romp el plat\n")
		break // Sortim del bucle
		fmt.Printf("\npermisos: %d%d\n", d, missatges)
	}
	fmt.Printf("Men vaig")

}

func main() {

	conn, err := amqp.Dial(dial)
	failOnError(err, "Failed to connect to RabbitMQ")
	defer conn.Close()

	ch, err := conn.Channel()
	failOnError(err, "Failed to open a channel")
	defer ch.Close()

	// Declaram la coa de sushis
	qSushis, err := ch.QueueDeclare(
		"plat", // name
		true,   // durable
		false,  // delete when unused
		false,  // exclusive
		false,  // no-wait
		nil,    // arguments
	)
	failOnError(err, "Failed to declare a queue") // Cas d'error

	// Declaram la coa de permisos
	qPermisos, err := ch.QueueDeclare(
		"permisos", // name
		true,       // durable
		false,      // delete when unused
		false,      // exclusive
		false,      // no-wait
		nil,        // arguments
	)
	failOnError(err, "Failed to declare a queue") // Cas d'error

	// Mètode del client gangster
	clientGangster(ch, qSushis, qPermisos)
}
