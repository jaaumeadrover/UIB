package main

import (
	"fmt"       // o log para printear
	"math/rand" // Para generación de numeros aleatorios
	"time"      // sleep
)

// Constantes
const (
	Clientes     = 4 //Clientes que piden al servidor
	Repeticiones = 2 //Numero de veces de la accion

	// Control de velocidades
	cliente_max_time = +1000
	cliente_min_time = -1000

	//Posibles tipos de las peticiones:
	CONSULTAR = 1
	DEPOSITAR = 2
	DEDINS    = 3
	DEFORA    = 4
)

// Estructuras
type Resposta struct {
	money int
}

type Peticion struct {
	id    int //Id del proceso
	tipo  int //Estado de peticion
	c     chan int
	money int
	// En algunos casos puede ser interesante pasarle un canal en la petición
}

// Variables
var (
	peticionServer  = make(chan Peticion)   //Canal de peticiones al servidor que hacen los clientes
	okClientes      [Clientes]chan Resposta // Respuestas del servidor a los clientes individualmente
	saldo           = 0
	cliente_nombres = [Clientes]string{"cli1", "cli2", "cli3", "cli4"} // En caso de que los clientes tengan nombres, se accede a este array
)

// Servidor
func servidor() {
	var clients [Clientes]Peticion
	var exit bool = false
	//Tots els clients estan fora
	for i := 0; i < Clientes; i++ {
		clients[i].tipo = 4
	}

	for {
		peticion := <-peticionServer
		clients[peticion.id] = peticion

		switch peticion.tipo {
		case CONSULTAR:
			fmt.Println("******* Servidor: Cliente, puede observar el saldo")
			//Mirar condicion a aceptar la peticion
			for i := 0; i < Clientes; i++ {
				if clients[i].tipo == DEDINS {
					exit = true
				}
			}
			if !exit {
				okClientes[peticion.id] <- Resposta{} //Damos la confirmacion al cliente
				//enviam saldo disponible
				peticion.c <- saldo
			}

		case DEPOSITAR:
			fmt.Println("******* Servidor: Cliente, puede modificar el saldo")
			if peticion.money < 0 {
				if saldo < peticion.money {
					fmt.Printf("No hi ha suficients diners per extreure")
				}
			}
			//Mirar condicion a aceptar la peticion
			//Mirar condicion a aceptar la peticion
			for i := 0; i < Clientes; i++ {
				if clients[i].tipo == DEDINS {
					exit = true
				}
			}
			if !exit {
				fmt.Println("******* Servidor: Cliente, puede modificar el saldo")
				saldo += peticion.money
				okClientes[peticion.id] <- Resposta{} //Damos la confirmacion al cliente
			}
		case DEFORA:
			fmt.Println("******* Servidor: client pot sortir de la interfície.")
			clients[peticion.id].tipo = DEFORA
			okClientes[peticion.id] <- Resposta{} //Damos la confirmacion al cliente
		}
	}
}

func cliente(id int, done chan int) {
	myChan := make(chan int)
	//Inicilizacion del canal de espera personal de cada cliente
	okClientes[id] = make(chan Resposta)

	//Variables
	velocidad := cliente_min_time + rand.Intn(cliente_max_time-cliente_min_time)

	//Funciones
	consultar := func() {
		fmt.Printf("****** %s: Pide Consultar saldo\n", cliente_nombres[id])

		//Mandamos peticion
		peticionServer <- Peticion{id, CONSULTAR, myChan, 0}
		//Esperamos a que nos confirme
		<-okClientes[id]
		diners := <-myChan
		fmt.Printf("El client ha vist que hi ha %d diners al compte", diners)
	}

	depositar := func() {
		fmt.Printf("****** %s: Pide Modificar saldo\n", cliente_nombres[id])

		//Mandamos peticion
		peticionServer <- Peticion{id, DEPOSITAR, myChan, velocidad}
		//Esperamos a que nos confirme

		<-okClientes[id]
		fmt.Printf("El client ha modifcat el saldo correctament")
	}

	salir := func() {
		fmt.Printf("****** %s: Pide Accion 3\n", cliente_nombres[id])

		//Mandamos peticion
		peticionServer <- Peticion{id, DEFORA, myChan, 0}
		//Esperamos a que nos confirme
		<-okClientes[id]
	}

	//Metodo para esperar
	wait := func(wait_time int) {
		time.Sleep(time.Duration(wait_time) * time.Second)
	}

	//Acciones que hace el Cliente
	for i := 0; i < Repeticiones; i++ {
		depositar()
		wait(2)
		consultar()
		salir()
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
