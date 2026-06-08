package main

import (
	"fmt"       // o log para printear
	"math/rand" // Para generación de numeros aleatorios
	"time"      // sleep
)

// Constantes
const (
	Clientes     = 7 //Clientes que piden al servidor
	Repeticiones = 2 //Numero de veces de la accion
	CADIRES      = 4
	// Control de velocidades
	cliente_max_time = 5
	cliente_min_time = 1

	//Posibles tipos de las peticiones:
	WAITING = 1
	SITIING = 2
	HUNGRY  = 3
	WORKING = 4
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
	peticionServer = make(chan Peticion) //Canal de peticiones al servidor que hacen los clientes
	okClientes     [Clientes]chan Empty  // Respuestas del servidor a los clientes individualmente

	cliente_nombres = [Clientes]string{"Savi", "Rondinaire", "Mudet", "Feliç", "Dormilega", "Vergonyos", "Guzman"} // En caso de que los clientes tengan nombres, se accede a este array
)

// Servidor
func servidor() {
	var status [Clientes]Peticion
	//init array nans with treballant
	for i := 0; i < Clientes; i++ {
		status[i].tipo = WORKING
	}

	for {
		peticion := <-peticionServer

		switch peticion.tipo {

		//Cas en que hi ha una petició per seure
		case WAITING:
			fmt.Println("******* Servidor: Confirma Accion 1")
			status[peticion.id].tipo = WAITING
			//Mirar condicion a aceptar la peticion
			if(){

				status[peticion.id].tipo = HUNGRY
			}
			okClientes[peticion.id] <- Empty{} //Damos la confirmacion al cliente
		//Cas en que hi ha una petició per menjar
		case HUNGRY:
			fmt.Println("******* Servidor: Confirma Accion 2")
			//Mirar condicion a aceptar la peticion
			okClientes[peticion.id] <- Empty{} //Damos la confirmacion al cliente

		//Cas en que s'ha acabat de menjar i es vol partir
		case WORKING:
			fmt.Println("******* Servidor: Confirma Accion 3")
			//Mirar condicion a aceptar la peticion
			okClientes[peticion.id] <- Empty{} //Damos la confirmacion al cliente
		}
	}
}

func cliente(id int, done chan int) {
	//Inicilizacion del canal de espera personal de cada cliente
	okClientes[id] = make(chan Empty)

	//Variables
	velocidad := cliente_min_time + rand.Intn(cliente_max_time-cliente_min_time)

	//Funciones
	accion1 := func() {
		fmt.Printf("****** %s: Pide Accion 1\n", cliente_nombres[id])

		//Mandamos peticion
		peticionServer <- Peticion{id, Estado1}
		//Esperamos a que nos confirme
		<-okClientes[id]
	}

	accion2 := func() {
		fmt.Printf("****** %s: Pide Accion 2\n", cliente_nombres[id])

		//Mandamos peticion
		peticionServer <- Peticion{id, Estado2}
		//Esperamos a que nos confirme
		<-okClientes[id]
	}

	accion3 := func() {
		fmt.Printf("****** %s: Pide Accion 3\n", cliente_nombres[id])

		//Mandamos peticion
		peticionServer <- Peticion{id, Estado3}
		//Esperamos a que nos confirme
		<-okClientes[id]
	}

	//Metodo para esperar
	wait := func(wait_time int) {
		time.Sleep(time.Duration(wait_time) * time.Second)
	}

	//Acciones que hace el Cliente
	for i := 0; i < Repeticiones; i++ {
		accion1()
		wait(velocidad)

		accion2()
		wait(velocidad)

		accion3()
		wait(velocidad)
	}
	done <- id
}

func main() {
	rand.Seed(time.Now().UnixNano())

	done := make(chan int) //Sincrono
	//done := make(chan Vacio, 1) //Asincrono

	for i := 0; i < Clientes; i++ {
		go cliente(i, done)
	}
	go servidor()

	for i := 0; i < Clientes; i++ {
		nombre := cliente_nombres[<-done]
		fmt.Printf("*** %s:  Se va\n", nombre)
	}

	fmt.Printf("End\n")
}
