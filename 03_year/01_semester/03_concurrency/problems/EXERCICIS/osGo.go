package main

import (
	"fmt"
	"runtime"
	"time"
)

const (
	Procs    = 4
	ABELLES  = 10
	EatCount = 5
	POT      = 10

	Hungry     = 1
	DoneEating = 2
	Produir    = 3
)

type Empty struct{}

type RequestOs struct {
	c      chan Empty
	status int
}
type RequestAbella struct {
	c      chan int
	status int
}

func pot(channel chan RequestOs, channel2 chan RequestAbella) {
	var capacitat int = POT

	canEat := func(m RequestOs) {
		fmt.Println("SOLICITUD OS")
		if capacitat == 0 {
			capacitat = POT
			m.c <- Empty{}
		}
	}

	canAdd := func(m RequestAbella) {
		if capacitat > 0 {
			capacitat = capacitat - 1
			m.c <- capacitat
		}
	}
	for {
		select {

		case missatgeOs := <-channel: //l'0s vol menjar
			canEat(missatgeOs)
		case missatgeAbella := <-channel2: //l'abella vol produir
			canAdd(missatgeAbella)
		}
	}

}

func Os(done chan Empty, provider chan RequestOs) {
	myChan := make(chan Empty)

	eat := func() {
		fmt.Println("L'ós vol menjar...")
		provider <- RequestOs{c: myChan, status: Hungry} //enviam solicitud al proveidor
		<-myChan
		fmt.Println("L'os ha començat a menjar.")
		time.Sleep(100 * time.Millisecond)
		fmt.Println("L'os ha acabat de menjar!")

	}
	for i := 0; i < EatCount; i++ {
		eat()
	}
}
func Abella(id int, done chan Empty, provider chan RequestAbella) {
	myChan := make(chan int)

	produce := func() {
		fmt.Println("L'abella %v vol produir.", id)
		provider <- RequestAbella{c: myChan, status: Produir} //enviam solicitud al proveidor
		capacitat := <-myChan
		fmt.Println("L'abella %v afegeix al pot:(%v)/%v.", id, (POT - capacitat), POT)
		time.Sleep(100 * time.Millisecond)
	}

	for i := 0; i < (POT / ABELLES); i++ {
		produce()
	}
}

func main() {
	runtime.GOMAXPROCS(Procs)
	done := make(chan Empty, 1)

	canalOs := make(chan RequestOs)
	canalAbelles := make(chan RequestAbella)
	go pot(canalOs, canalAbelles)
	go Os(done, canalOs)
	//
	for i := 0; i < ABELLES; i++ {
		go Abella(i, done, canalAbelles)
	}

	// Wait for bees and pooh
	for i := 0; i < ABELLES; i++ {
		<-done
	}
	<-done

	fmt.Printf("DONE\n")
}
