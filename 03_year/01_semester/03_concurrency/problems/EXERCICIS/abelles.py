from threading import Thread


N=5

class Abella(Thread):

    def __init__(self,id):
        super().__init__()
        self.id=id

    def run(self):
        print("hola soc l'abella {}".format(self.id))






def main():
    threads = []

    for i in range(N):
        p = Abella(i)
        threads.append(p)

    # Start all threads
    for t in threads:
        t.start()

    # Wait for all threads to complete
    for t in threads:
        t.join()

    print("End")

main()