
from entorn import *

def main():
    movs={"ESQUERRE":(0,-1),"DRETA":(0,+1),"DALT": (+1,0),"BAIX": (0,-1)}
    claus=list(movs.keys())
    hola=(2,0)
    dictat={'Miquel':[2,0]}
    accio=AccionsRana.MOURE

    if accio not in AccionsRana:
        print('Error')
main()