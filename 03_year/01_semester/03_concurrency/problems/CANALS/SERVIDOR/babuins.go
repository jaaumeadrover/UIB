package main

import (
	"fmt"       // o log para printear
	"math/rand" // Para generación de numeros aleatorios
	"time"      // sleep
)

// Constantes
const (
	Babuins      = 5 //Babuins que piden al servidor
	Repeticiones = 3 //Numero de veces de la accion
	MAX          = 3
	// Control de velocidades
	cliente_max_time = 5
	cliente_min_time = 1

	//Posibles tipos de las peticiones:
	WAITING     = 1
	CROSSED     = 2
	MIDDLE_ROPE = 3
)

// Estructuras
type Empty struct{}

type Peticion struct {
	id   int //Id del proceso
	tipo int //Estado de peticion

	// En algunos casos puede ser interesante pasarle un canal en la petición
}

// Variables
var (
	peticionServer = make(chan Peticion) //Canal de peticiones al servidor que hacen los Babuins
	okBabuins      [Babuins]chan Empty   // Respuestas del servidor a los Babuins individualmente

	cliente_nombres = [Babuins]string{"Babui 1", "Babui 2", "Babui 3", "Babui 4", "Babui 5"} // En caso de que los Babuins tengan nombres, se accede a este array
)

// Servidor
func servidor() {
	corda := 0
	var status [Babuins]Peticion
	for i := 0; i < Babuins; i++ {
		status[i].tipo = CROSSED
	}
	potCreuar := func() bool {
		return corda < MAX
	}
	entraCorda := func(id int) {
		corda += 1
		fmt.Printf("******* Babuí més vell: pots passar babuí %d. A la corda n'hi ha %d \n", id+1, corda)
		okBabuins[id] <- Empty{} //Damos la confirmacion al cliente
	}
	comprovPendents := func() {
		fmt.Printf("He entrat a comprovPendents: corda: %d \n", corda)
		for i := corda; i < MAX; i++ {
			for j := 0; j < Babuins; j++ {
				if status[j].tipo == WAITING {
					fmt.Printf("Comprov: deix entrar a %d\n", j)
					entraCorda(j)
					status[j].tipo = MIDDLE_ROPE
					j = Babuins
				}
			}

		}
	}
	for {
		peticion := <-peticionServer
		status[peticion.id].tipo = peticion.tipo

		switch peticion.tipo {
		case WAITING:
			fmt.Printf("\n******* Babuí més vell: ara mir si pots passar babuí %d \n", peticion.id+1)
			//Mirar condicion a aceptar la peticion
			if potCreuar() {
				entraCorda(peticion.id)
				status[peticion.id].tipo = MIDDLE_ROPE
			}

		case CROSSED:
			corda -= 1
			fmt.Printf("\n******* Babuí més vell: M'alegr de que haguis passat babuí %d. A la corda queden %d \n", peticion.id+1, corda)
			//miram si hi ha solicituds emmagatzemades
			comprovPendents()
			okBabuins[peticion.id] <- Empty{} //Damos la confirmacion al cliente
		}
	}
}

func cliente(id int, done chan int) {
	//Inicilizacion del canal de espera personal de cada cliente
	okBabuins[id] = make(chan Empty)

	//Variables
	velocidad := cliente_min_time + rand.Intn(cliente_max_time-cliente_min_time)

	//Funciones
	consultar := func() {
		fmt.Printf("****** %s: puc passar?\n", cliente_nombres[id])

		//Mandamos peticion
		peticionServer <- Peticion{id, WAITING}
		//Esperamos a que nos confirme
		<-okBabuins[id]
	}

	acabar := func() {
		fmt.Printf("****** %s: me'n vaig, gràcies per tot\n", cliente_nombres[id])

		//Mandamos peticion
		peticionServer <- Peticion{id, CROSSED}
		//Esperamos a que nos confirme
		<-okBabuins[id]
	}
	//Metodo para esperar
	wait := func(wait_time int) {
		time.Sleep(time.Duration(wait_time) * time.Second)
	}
	fmt.Printf("BON DIA som el babuí %d i vaig cap al nord\n", id+1)
	//Acciones que hace el Cliente
	for i := 0; i < Repeticiones; i++ {
		consultar()
		wait(velocidad) //temps q tarda a creuar la corda
		acabar()
		wait(velocidad)
	}
	done <- id
}

func main() {
	rand.Seed(time.Now().UnixNano())

	done := make(chan int) //Sincrono
	//done := make(chan Vacio, 1) //Asincrono

	for i := 0; i < Babuins; i++ {
		go cliente(i, done)
	}
	go servidor()

	for i := 0; i < Babuins; i++ {
		nombre := cliente_nombres[<-done]
		fmt.Printf("*** %s:  Se va\n", nombre)
	}

	fmt.Printf("End\n")
}
