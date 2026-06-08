package main

import (
	"fmt"
	"time"
)

const (
	POT      int = 20
	ABELLES  int = 10
	MENJADES int = 3
)

type Empty struct{}

func Abella(id int, jar chan int, os chan Empty, done chan bool) {
	fmt.Printf("Hola soc l'abella %d\n", id)
	for i := 0; i < (MENJADES*POT)/ABELLES; i++ {
		time.Sleep(100 * time.Millisecond) //simulam el que tarda a venir
		n := <-jar
		fmt.Printf("Hola soc l'abella %d\n", id)
		time.Sleep(100 * time.Millisecond) //simulam el que tarda a produir
		n = n + 1
		fmt.Printf("L'abella %d ha produit %d/%d.\n", id, n, POT)
		if n < POT {
			jar <- n
		} else {
			fmt.Printf("L'abella %d va a despertar l'os.\n", id)
			os <- Empty{}
		}

	}
	done <- true
}

func Os(channel chan Empty, jar chan int, done chan bool) {
	for i := 0; i < MENJADES; i++ {
		fmt.Println("L'ós esta esperant per menjar")
		<-channel //l'os espera per menjar
		time.Sleep(100 * time.Millisecond)
		jar <- 0
		fmt.Println("L'ós ha menjat")
	}
	done <- true
}
func main() {
	pot := make(chan int, 1)
	canalOs := make(chan Empty, 1)
	done := make(chan bool, 1)

	pot <- 0
	go Os(canalOs, pot, done)
	for i := 0; i < ABELLES; i++ {
		go Abella(i, pot, canalOs, done)
	}

	for i := 0; i < ABELLES+1; i++ {
		<-done
	}

}
