import threading


THREADS = 2
MAX_COUNT =1000 #1milion
n=0
turn=1

def count():
    global turn,n
    for i in range(MAX_COUNT//THREADS):
        while(turn!=getNum(threading.currentThread().name)):
            pass
        n+=1
    turn=(turn%THREADS)+1

def getNum(str):
    return int(str[7])

def main():
    threads=[]

    for i in range(THREADS):
        t=threading.Thread(target=count)
        threads.append(t)
        t.start()

    for t in threads:
        t.join()
    error=(MAX_COUNT-n)/(MAX_COUNT*100)
    print("Valor Contador:{}, Expected: {}\nError:{}".format(n,MAX_COUNT,error))

main()