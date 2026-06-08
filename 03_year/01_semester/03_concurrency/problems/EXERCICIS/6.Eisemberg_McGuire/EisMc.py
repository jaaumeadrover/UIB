from threading import Thread
import random

estats=("IDLE","WAITING","ACTIVE")
THREADS=2
MAX_COUNT = 100000
flags=[]
turn=0
n=0

class Thread(Thread):

    def __init__(self,id):
        super().__init__()
        self.id=id


    def run(self):
        global flags,turn,n
        end=False

        while(end==False):
            #flags[i]=WAITING
            flags[self.id]=estats[1]
            index=turn
            while(index!=self.id):
                if(flags[index]!= estats[0]):
                    index=turn
                else:
                    index=(index+1)%THREADS
            
            flags[self.id]=estats[2]

            index=0
            while((index<THREADS)and((index==self.id)or (flags[index!=estats[2]]))):
                index=index+1
            if ((index>=THREADS) and ((turn==self.id)or(flags[turn]==estats[0]))):
                end=True
        #start of critical section
        turn=self.id
        for i in range(MAX_COUNT//THREADS):
            n+=1
        #end of critical section
        index=(turn+1)%THREADS

        while(flags[index]==estats[0]):
            index=(index+1)%THREADS
        turn=index

        flags[self.id]=estats[0]
        
def main():
    global flags
    threads=[]

    turn=random.randint(0,THREADS-1)
    #Inicialitzam array de flags amb IDLE
    for i in range(THREADS):
        flags.append(estats[0])

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