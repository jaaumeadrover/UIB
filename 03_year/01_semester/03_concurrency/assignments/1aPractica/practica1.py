"""
AUTORS: Jaume Adrover Fernandez, Joan Balaguer Llagostera
ENLLAÇ VIDEO: https://www.youtube.com/watch?v=MEAn0JlOvsg&ab_channel=JoanBalaguer
"""

import threading
import random
import time

# GLOBALS

# CONSTANTS
noms = ["Alba", "Joan", "Jaume", "Marc", "Lluis", "Maria", "Jordi", "Luna", "Victor", "Edu",
        "Pere", "Pep", "Pau", "Aina", "Tomeu", "Tomas", "Lluissa", "Silvia", "Marta", "Julia",
        "Peter", "Xesc", "Xiwei", "Marcos", "Dolors", "Josep", "Miquela", "Daniel", "Axel", "Jude",
        "Montes", "Biza", "Duki", "Aitana", "Messi", "CR7", "Maradona", "Pele", "Nazario", "Arnau"]
estats = ["DEFORA", "DINS", "ESPERANT"] # Possibles estats del director
MAX = 10  # Nombre alumnes per a realitzar festa
RONDES = 3
totEstudiants = 40

# VARIABLES
numEstudiants = 0
estatDirector = estats[0] # Estat actual del director
alumnesEsperant = 0 # Variable que conta el nombre d'alumnes esperant per entrar quan hi ha el professor dins

#SEMAFORS
semaforAlumne = threading.Semaphore(0) # Bloquejar alumnes que no entrin a la sala
semaforDirectorEspera = threading.Semaphore(0) # Bloquejar director en el cas on sala NO FESTA i queda esperant fora
semaforDirectorFesta = threading.Semaphore(0) # Bloquejar director en el cas que hi ha festa a la sala i no pot surtir
mutexVar = threading.Semaphore(1) # Semafor mutex per a protegir la modificació de les variables compartides (numEstudiant i alumnesEsperant)


"""
Mètode que simula el comportament del director, iterant en un bucle de N rondes.
"""
def director():
    global numEstudiants, MAX, totEstudiants, semaforAlumne, semaforDirectorEspera, semaforDirectorFesta, \
        estatDirector, rondes, alumnesEsperant, noms

    for i in range(RONDES):
        time.sleep(0.25) # Simulem un temps de retard del director per començar la ronda
        print("     El Sr. Director comença la ronda")

        # CAS 1: Aula amb nombre d'estudiants correcte (estudien) --> Queda a la porta esperant
        if numEstudiants > 0 and numEstudiants < MAX:
            print("     El director està esperant per entrar. No molesta als que estudien")
            estatDirector = estats[2] # posam l'estat del director a ESPERANT
            semaforDirectorEspera.acquire() # El director demana  permis per entrar (No se li ha de concedir fins que
                                            # numEstudiants == 0 o numEstudiants == MAX)
            if numEstudiants >= MAX: # Cas on alliberen al director i hi ha festa a la sala
                directorFesta()
            else: # Cas on alliberen al director i no hi ha ningú a la sala
                print("     El director veu que no hi ha ningú a la sala d'estudis")
        # CAS 2: Aula supera nombre màxim d'alumnes --> Entra, bloqueja la porta i no pot entrar ningú fins que tots
        #                                               els alumnes hagin sortit de la sala
        elif numEstudiants >= MAX:
            directorFesta()
        # CAS 3: No queda ningú a l'aula --> Surt de la sala
        else:
            print("     El director veu que no hi ha ningú a la sala d'estudis")
        print(f"     El director acaba la ronda {i + 1}/{RONDES}")
        estatDirector = estats[0] # Posam l'estat del director a DEFORA
        contador = alumnesEsperant
        
        #Bucle per alliberar tots els alumnes esperant fora
        for i in range(contador):
                semaforAlumne.release()
                time.sleep(random.uniform(0.05,0.5))
                mutexVar.acquire()
                alumnesEsperant-=1
                mutexVar.release()
        time.sleep(random.uniform(0.005, 0.8)) #Temps que tarda el professor en surtir de la sala

"""
Funció per el tractament del CAS 2
"""
def directorFesta():
    global numEstudiants, MAX, totEstudiants, semaforAlumne, semaforDirectorEspera, semaforDirectorFesta, \
        estatDirector, rondes, alumnesEsperant, noms

    estatDirector = estats[1] # Posam l'estat del director a DINS
    print("     El director esta dins la sala d'estudi: S'HA ACABAT LA FESTA!!!")
    semaforDirectorFesta.acquire()  # Director bloquejat fins que el darrer alumne el deixi sortir

"""
Mètode que simula el comportament d'un alumne. 
"""
def alumne(nom):
    global numEstudiants, MAX, totEstudiants, semaforAlumne, semaforDirectorEspera, semaforDirectorFesta, \
        estatDirector, rondes, alumnesEsperant, noms

    time.sleep(random.uniform(1.0, 3.0)) # Simulam un temps d'espera en arribar a la porta
    if (estatDirector == estats[1]): # Si l'estat del director == DINS
        mutexVar.acquire() # utilitzam el mutex per controlar l'accés a la variable alumnesEsperant
        alumnesEsperant+=1
        mutexVar.release()
        semaforAlumne.acquire()  # L'estudiant queda bloquejat fins que el director deixa entrar a la sala
        time.sleep(random.uniform(0.5, 1.0)) # Temps que tarda l'alumne en entrar a l'aula

    mutexVar.acquire() # L'alumne demana l'accés per modificar la variable compartida numEstudiants
    numEstudiants += 1
    print(f"{nom} entra a la sala d'estudi, nombre d'estudiants: {numEstudiants}")
    if numEstudiants >= MAX: # superam nombre alumnes permesos
        print(f"{nom}: FESTA!!!!")
        if estatDirector == estats[2]: # Si el professor està esperant
            print(f"{nom}: Alerta que ve el director!!!!!!!")
            semaforDirectorEspera.release() # Deixa entrar el director a la sala
    else:
        print(f"{nom} estudia")
    mutexVar.release() # L'alumne amolla l'accés per modificar la variable compartida numEstudiants
    time.sleep(random.uniform(2.0, 4.0)) # Simulem que l'estudiant està a la sala una quantitat de temps

    # L'estudiant està cansat i vol sortir de la sala
    mutexVar.acquire() # L'alumne demana l'accés per modificar la variable compartida numEstudiants
    numEstudiants -= 1
    if numEstudiants == 0: # Si era el darrer alumne a l'aula...
        if estatDirector == estats[2]: # 1. ...i el director està ESPERANT a fora la sala
            print(f"{nom}: Sr. Director, pot entrar, no queda ningú a la sala")
            semaforDirectorEspera.release() # Alliberam al director
        elif estatDirector == estats[1]: # 2. ...i el director esta DINS la sala
            print(f"{nom}: Adeu Sr. Director, es queda sol")
            semaforDirectorFesta.release() # Alliberam al director
    print(f"{nom} surt de la sala d'estudi, nombre d'estudiants: {numEstudiants}")
    mutexVar.release() # L'alumne amolla l'accés per modificar la variable compartida numEstudiants


def main():
    global numEstudiants, MAX, totEstudiants, semaforAlumne, semaforDirectorEspera, semaforDirectorFesta, \
        estatDirector, rondes, alumnesEsperant, noms

    fils = []
    # Inicialitzam el director
    dir = threading.Thread(target=director)
    fils.append(dir)

    # Inicialitzam els alumnes
    for i in range(totEstudiants):
        e = threading.Thread(target=alumne, args=(noms[i],))
        fils.append(e)

    print("SIMULACIÓ DE LA SALA D'ESTUDI")
    print(f"Nombre total d'estudiants {totEstudiants}")
    print(f"Nombre màxim d'estudiants {MAX}\n")

    # Executam tots els fils
    for t in fils:
        t.start()

    # Esperam a que acabin tots els processos
    for t in fils:
        t.join()

    print("SIMULACIÓ ACABADA")


if __name__ == '__main__':
    main()
