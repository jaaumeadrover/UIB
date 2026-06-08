package main

import (
	"fmt"       // o log para printear
	"math/rand" // Para generación de numeros aleatorios
	"time"      // sleep
)

// Constantes
const (
	Ovelles      = 3 //Clientes que piden al servidor
	Llops        = 6
	Repeticiones = 1 //Numero de veces de la accion

	// Control de velocidades
	cliente_max_time = 5
	cliente_min_time = 1

	//Posibles tipos de las peticiones:
	ESPERANT_ENTRAR = 1
	RIU             = 2
	FORA            = 3
	ESPERANT_SORTIR = 4
)

// Estructuras
type Empty struct{}

type Peticion struct {
	id   int  //Id del proceso
	tipo int  //Estado de peticion
	llop bool //true si és llop

	// En algunos casos puede ser interesante pasarle un canal en la petición
}

// Variables
var (
	peticionServer = make(chan Peticion) //Canal de peticiones al servidor que hacen los clientes
	okClientes     [Ovelles]chan Empty   // Respuestas del servidor a los clientes individualmente
	//cliente_nombres = [Clientes]string{"cli1", "cli2", "cli3", "cli4", "cli5", "cli6", "cli7"} // En caso de que los clientes tengan nombres, se accede a este array
)

// Servidor
func servidor() {
	var peticions [Ovelles]Peticion
	var llops int = 0
	var ovellesRiu int = 0
	var ovellesEsperant int = 0
	var ovellesEsperantFora int = 0
	for {
		peticion := <-peticionServer
		switch peticion.tipo {
		//petició per entrar al riu
		case RIU:
			if !peticion.llop {
				ovellesEsperant++
				peticions[peticion.id].tipo = ESPERANT_ENTRAR
				//Situació perillosa
				if ovellesEsperant == 2 && llops > 0 {
					ovellesEsperant--
					ovellesRiu++
					peticions[peticion.id].tipo = RIU
					fmt.Printf("**** El pastor fa passar a la ovella %d per baixar al riu.		Llops = %d  Ovelles= %d   RAPID!!!\n", peticion.id, llops, ovellesRiu)
					okClientes[peticion.id] <- Empty{} //Damos la confirmacion al cliente
					//miram si altres ovelles poden passar
					for i := 0; i < Ovelles; i++ {
						if peticions[i].tipo == ESPERANT_ENTRAR {
							if ovellesEsperant >= 2 || llops == 0 {
								fmt.Printf("**** El pastor fa passar a la ovella %d per baixar al riu.		Llops = %d  Ovelles= %d   RAPID!!!\n", i, llops, ovellesRiu)
								ovellesEsperant--
								ovellesRiu++
								peticions[i].tipo = RIU
								okClientes[i] <- Empty{}
							}
						}
					}
				} else {
					if ovellesRiu >= 2 || llops == 0 {
						fmt.Printf("**** El pastor dona permís a la ovella %d per baixar al riu.		Llops = %d  Ovelles= %d\n", peticion.id, llops, ovellesRiu)
						ovellesEsperant--
						ovellesRiu++
						okClientes[peticion.id] <- Empty{}
					}
				}
			} else {
				//cas llop passa al riu
				llops++
				//no necessita permís
			}

		case FORA:
			if !peticion.llop {
				ovellesEsperantFora++
				peticions[peticion.id].tipo = ESPERANT_SORTIR
				//situació perillosa, queden 2 ovelles i hi ha llops
				if ovellesRiu == 2 && llops > 0 {
					if ovellesEsperantFora == 2 {
						fmt.Printf()
					} else {
						fmt.Printf("---- El pastor fa esperar a la ovella %d.			Llops = %d  Ovelles = %d", peticion.id, llops, ovellesRiu)
					}
				} else {

				}
			} else {
				llops--
				fmt.Printf(" 		El llop %d se'n va							Llops = %d  Ovelles = %d", peticion.id, llops, ovellesRiu)
			}
			//Mirar condicion a aceptar la peticion
			okClientes[peticion.id] <- Empty{} //Damos la confirmacion al cliente

		}
	}
}

func ovella(id int, done chan int) {
	//Inicilizacion del canal de espera personal de cada cliente
	okClientes[id] = make(chan Empty)

	//Variables
	velocidad := cliente_min_time + rand.Intn(cliente_max_time-cliente_min_time)

	//Funciones
	anarRiu := func() {
		fmt.Printf("L'ovella %d vol baixar al riu a beure\n", id)

		//Mandamos peticion
		peticionServer <- Peticion{id, RIU, false}
		//Esperamos a que nos confirme
		<-okClientes[id]
	}

	sortirRiu := func() {
		fmt.Printf("****** %s: Pide Accion 2\n", cliente_nombres[id])

		//Mandamos peticion
		peticionServer <- Peticion{id, FORA, false}
		//Esperamos a que nos confirme
		<-okClientes[id]
	}

	//Metodo para esperar
	wait := func(wait_time int) {
		time.Sleep(time.Duration(wait_time) * time.Second)
	}

	//Acciones que hace el Cliente
	for i := 0; i < Repeticiones; i++ {
		anarRiu()
		wait(velocidad)
		accion3()
		wait(velocidad)
	}
	done <- id
}

func llop(id int, done chan int) {

	//Funciones
	anarRiu := func() {
		fmt.Printf("		Pide Accion 1\n", id)

		//Mandamos peticion
		peticionServer <- Peticion{id, RIU, true}

	}
	sortirRiu := func() {
		fmt.Printf(" 		El llop %d se'n va", id)
	}
	//Metodo para esperar
	wait := func(wait_time int) {
		time.Sleep(time.Duration(wait_time) * time.Second)
		//Mandamos peticion
		peticionServer <- Peticion{id, FORA, true}
	}

	fmt.Printf("		Aquest és el llop %d", id)
	//Acciones que hace el Cliente
	for i := 0; i < Repeticiones; i++ {
		anarRiu()
		wait(3)
		sortirRiu()
	}
	done <- id
}

func main() {
	rand.Seed(time.Now().UnixNano())

	done := make(chan int) //Sincrono
	//done := make(chan Vacio, 1) //Asincrono

	for i := 0; i < Clientes; i++ {
		go ovella(i, done)
	}
	go servidor()

	for i := 0; i < Clientes; i++ {
		nombre := cliente_nombres[<-done]
		fmt.Printf("*** %s:  Se va\n", nombre)
	}

	fmt.Printf("End\n")
}
