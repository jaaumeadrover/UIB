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

type Empty struct{}

/*
Mètode cuiner que dur a terme les accions pròpies d'aquest
*/
func cuiner(ch *amqp.Channel, err error, qSushis amqp.Queue, qPermisos amqp.Queue, done chan Empty) {
	rand.Seed(time.Now().UnixNano())
	var tipus [3]string = [3]string{"niguiri de salmó", "sashimi de tonyina", "maki de cranc"} // Tipus de sushi
	var max int = 0
	var c1 int = rand.Intn(10 - max)
	max = max + c1
	var c2 int = rand.Intn(10 - max)
	max = max + c2
	var c3 int = 10 - c2 - c1

	var cant [3]int = [3]int{c1, c2, c3} // Assignem les quantitats de cada sushi

	// Prints inicial del cuiner
	fmt.Printf("El cuiner de sushi ja és aquí\n")
	fmt.Printf("El cuiner prepararà un plat amb:\n")
	for i := 0; i < 3; i++ {
		fmt.Printf("%d peces de %s \n", cant[i], tipus[i])
	}
	fmt.Printf("\n")

	// Per a cada tipus de sushi fiquem a la coa de sushis la quantitat corresponent a cada un
	for i := 0; i < 3; i++ {
		cantitat := cant[i] // Quantitat corresponent de cada un dels tipus de sushi
		for j := 0; j < cantitat; j++ {
			// Publicam el missatge del tipus de sushi que estem tractant ara mateix dins la coa de sushis
			err = ch.Publish(
				"",           // exchange
				qSushis.Name, // routing key
				false,        // mandatory
				false,        // immediate
				amqp.Publishing{
					ContentType: "text/plain",
					Body:        []byte(tipus[i]),
				})
			failOnError(err, "Failed to publish a message")    // Cas d'error
			log.Printf(" [x] Posa dins al plat  %s", tipus[i]) // Print on es mostra el tipus de sushi que s'acaba de ficar al plat
			time.Sleep(500 * time.Millisecond)                 // Simulam un retard constant entre inserir un sushi i l'altre
		}
	}

	var n int = cant[0] + cant[1] + cant[2] // Nombre total de sushis que hem ficat (nombre de permisos que ficarem)
	for j := 0; j < n; j++ {
		// Publicam un permis a la coa de permisos per cada sushi que hem inserit a la coa de sushis
		err = ch.Publish(
			"",             // exchange
			qPermisos.Name, // routing key
			false,          // mandatory
			false,          // immediate
			amqp.Publishing{
				ContentType: "text/plain",
				Body:        []byte("Permis"),
			})
		failOnError(err, "Failed to publish a message") // Cas d'error
	}
	fmt.Printf("Podeu menjar!")
	done <- Empty{}
}

func main() {

	done := make(chan Empty, 1)

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
	failOnError(err, "Failed to declare a queue")

	// Declaram la coa de permisos
	qPermisos, err := ch.QueueDeclare(
		"permisos", // name
		true,       // durable
		false,      // delete when unused
		false,      // exclusive
		false,      // no-wait
		nil,        // arguments
	)
	failOnError(err, "Failed to declare a queue")

	// Mètode cuiner
	cuiner(ch, err, qSushis, qPermisos, done)
	<-done
}
