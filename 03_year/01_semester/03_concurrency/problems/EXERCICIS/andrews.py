import threading

N=5
H=10

n=0
semaforOs=threading.Semaphore(0)
semaforAbella=threading.Semaphore(1)

def productor(id):
    global n
    while(1):
        semaforAbella.acquire()
        print("L'abella {} produeix i afegeix al pot: {}".format(id,n))
        n=n+1
        if(n==H):
            print("hola")
            semaforOs.release()
        else:
            semaforAbella.release()


def consumidor():
    global n
    print("Hola sóc l'ós")
    end=False
    while(1):
        semaforOs.acquire()
        print("L'ós menja")
        n=0
        semaforAbella.release()



def main():
    threads = []

    for i in range(1):
        c = threading.Thread(target=consumidor)
        threads.append(c)

    for i in range(N):
        p = threading.Thread(target=productor,args=(i,))
        threads.append(p)

    # Start all threads
    for t in threads:
        t.start()

    # Wait for all threads to complete
    for t in threads:
        t.join()

    print("End")


if __name__ == "__main__":
    main()
