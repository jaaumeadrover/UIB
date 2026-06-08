package main

import (
	"fmt"
	"math"
	"math/rand"
	"time"
)

const (
	CLIENTS = 3
	DIPOSIT = iota
	CONSULTA
	DEDINS
)

type Empty struct{}
type Request struct {
	c      chan int
	id     int
	status int
	money  int
}

func provider(provider chan Request) {
	saldo := 0

	for {
		p := <-provider
		switch p.status {
		case DEPOSIT:
			if ningu {
				p.c <- cantActual
			}

		case CONSULTA:
		}
	}
}
canals asincron

func client(id int, provider chan Request, done chan Empty) {
	myChan := make(chan int)
	fmt.Printf("\nHola soc el client %d \n", id)
	pensar := func() {
		time.Sleep(50 * time.Millisecond)
	}
	dipositar := func() {
		min := -1000
		max := 1000
		aDipositar := rand.Intn(max-min) + min
		if aDipositar < 0 {
			fmt.Printf("Client %d: vull extreure %d diners del compte.", id, math.Abs(float64(aDipositar)))
		} else {
			fmt.Printf("Client %d: vull dipositar %d diners dins el compte.", id, aDipositar)
		}
		provider <- Request{c: myChan, id: id, status: DIPOSIT, money: aDipositar}
		<-myChan
		fmt.Printf("Client %d: operació completada.", id)
	}
	consultar := func() {
		//demanam solicitud al proveidor
		fmt.Printf("\nClient %d: vull consultar el saldo.\n", id)
		provider <- Request{c: myChan, id: id, status: CONSULTA, money: 0}
		diners := <-myChan
		fmt.Printf("Client %d: veig que hi ha %d diners al banc", id, diners)

	}
	random := rand.Intn(5)
	for i := 0; i < random; i++ {
		pensar()
		dipositar()
		consultar()
	}
}
func main() {
	fmt.Println("INICI SIMULACIÓ")
}
