import threading
from datetime import datetime
import random
import time

THREADS = 2
MAXCOUNT = 20
want = [0,0]
last = 0
n = 0
timeInici = [0,0]
timeFi = [0,0]


def thread():
    global want, MAXCOUNT, n, timeInici, timeFi, last
    #assignació de nom = Thread 1....Thread n
    #id = caràcter 7
    id = int(threading.current_thread().name[7])
    altreThread = (id % THREADS) + 1
    #tenim en compte que si és el primer thread, serà la posició 0 dels arrays
    id = id - 1
    altreThread = altreThread- 1

    timeInici[id] = datetime.now()

    for i in range(int(MAXCOUNT/THREADS)):
        #simulam espera abans d'entrar al lock
        time.sleep(random.uniform(0,5))
        #peterson
        want[id] = 1
        last = id
        while(want[altreThread] == 1 and last == id):
            continue
        #SC
        #El temps i el print també pertanyen a la seccio crítica (les variables del print podrien no correspondre sino)
        ct = datetime.now().strftime("%H:%M:%S.%f")
        n = n+1 
        print("Porta " + str(id), ": " + str(i+1), " entrades de " + str(n), "Temps: " + str(ct))
        want[id] = 0

    timeFi[id] = datetime.now()

def main():
    threads = []
    for i in range(THREADS):
        # Create new threads
        t = threading.Thread(target=thread)
        threads.append(t)
        t.start() # start the thread

    # Wait for all threads to complete
    for t in threads:
        t.join()

    print("Entrades totals: " + str(n))
    t0 = timeFi[0] - timeInici[0]
    print("Temps mig porta 1: " + str(t0))
    t1 = timeFi[1] - timeInici[1]
    print("Temps mig porta 2: " + str(t1))




if __name__ == "__main__":
    main()