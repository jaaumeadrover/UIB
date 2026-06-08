from threading import Thread  # type: ignore


THREADS = 2
MAX_COUNT = 100000 #1milion
n=0
wantx=[]
turn=1


class Thread(Thread):

    def __init__(self,id):
        super().__init__()
        self.id=id


    def run(self):
        global wantx,turn,n
        otherId=((self.id+1)%THREADS)
        nextTurn=((self.id)%(THREADS+1))+1

        #preprotocol
        wantx[self.id]=True
        if(wantx[otherId]==True):
            if(turn==nextTurn):
                wantx[self.id-1]=False
                while (turn!=self.id):
                    pass
                wantx[self.id]=True
            while(wantx[otherId]==True):
                pass
        #seccio critica
        for i in range(MAX_COUNT//THREADS):
            n+=1
        #postprotocol
        wantx[self.id]=False
        turn=nextTurn

def main():
    global wantx
    threads=[]

    #Inicialitzam array de booleans
    for i in range(THREADS):
        wantx.append(False)
    #Inicialitzam els fils
    for j in range(THREADS):
        t=Thread(j)
        threads.append(t)
        t.start()

    for t in threads:
        t.join()
    error=((MAX_COUNT-n)/(MAX_COUNT))*100
    print("Valor Contador:{}, Expected: {}\nError:{}%".format(n,MAX_COUNT,error))

main()