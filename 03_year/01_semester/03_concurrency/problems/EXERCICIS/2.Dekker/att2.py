from threading import Thread  # type: ignore


THREADS = 2
MAX_COUNT = 1000000 #1milion
n=0
wantx=[]


class Thread(Thread):

    def __init__(self,id):
        super().__init__()
        self.id=id


    def run(self):
        global wantx,n
        for i in range(MAX_COUNT//THREADS):
            while(wantx[(self.id+1)%THREADS]==True):
                print("Esperam en el fil {}".format(self.id))
                pass
            wantx[self.id]=True
            n+=1
            wantx[self.id]=False

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