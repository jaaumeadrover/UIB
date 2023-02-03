"""

ClauPercepcio:
    POSICIO = 0
    OLOR = 1
    PARETS = 2
"""
"""
CONSTANTS PER MOURE I ELS REUS COSTOS
    COST_DESPL=1
    COST_ESPERAR=0.5
    COST_BOTAR=6
"""
from ia_2022 import entorn
from practica1 import joc
from practica1.entorn import ClauPercepcio, AccionsRana, Direccio

COST_ESPERAR = 0.5
COST_BOTAR = 6
COST_DESPL = 1

class Estat:
    def __init__(self,posPizza,posAgent, parets, pes=0, pare=None):
        self.__posicioAg = posAgent
        self.__posPizza = posPizza
        self.__parets = parets
        self.__pes = pes
        self.__pare = pare

    def __eq__(self, other):
        return self.__posicioAg == other.getPosAg()

    def __hash__(self):
        return hash(tuple(self.__posicioAg))

    @property
    def pare(self):
        return self.__pare

    @pare.setter
    def pare(self, value):
        self.__pare = value

    def calculaHeuristica(self):
        sum = 0
        for i in range(2):
            sum += abs(self.__posPizza[0] - self.__posicioAg['Miquel'][i])
        return self.__pes + sum

    def es_valid(self):
        claus=list(self.__posicioAg.keys())
        # mirar si hi ha parets
        if self.__posicioAg in self.__parets:
            print('Hi ha paret')
            return False

        return (self.__posicioAg['Miquel'][0]<=7)and(self.__posicioAg['Miquel'][0]>=0)\
               and(self.__posicioAg['Miquel'][1]<=7)and(self.__posicioAg['Miquel'][1]>=0)

    def es_meta(self):
        print("POS AGENT: "+str(self.__posicioAg['Miquel']))
        return (self.__posicioAg['Miquel'][0] == self.__posPizza[0])and(self.__posicioAg['Miquel'][1] == self.__posPizza[1])


    def getPosAg(self):
        return self.__posicioAg

    """Això es necessari?????"""


    def generaFills(self):
        movs = {"ESQUERRE": (-1, 0), "DRETA": (+1, 0), "DALT": (0, -1), "BAIX": (0, +1)}
        claus = list(movs.keys())
        fills = []

        """
        Cas 1: Moviments de desplaçament a caselles adjacents.
        """
        for i, m in enumerate(movs.values()):
            coords = [sum(tup) for tup in zip(self.__posicioAg['Miquel'], m)]
            coord = {'Miquel': coords}
            #coords=[(0,0)]
            cost = self.calculaHeuristica() + COST_DESPL
            actual = Estat(self.__posPizza,coord, self.__parets, cost,
                           (self, (AccionsRana.MOURE, Direccio.__getitem__(claus[i]))))
            if (actual.es_valid()):
                fills.append(actual)

        """
        Cas 2: Moviments de desplaçament de 2 caselles en caselles
        """
        movs = {"ESQUERRE": (-2, 0), "DRETA": (+2, 0), "DALT": (0, -2), "BAIX": (0, +2)}
        for i, m in enumerate(movs.values()):
            coords = [sum(tup) for tup in zip(self.__posicioAg['Miquel'], m)]
            coord={'Miquel':coords}
            cost = self.calculaHeuristica() + COST_BOTAR
            actual = Estat(self.__posPizza,coord, self.__parets, cost,
                           (self, (AccionsRana.BOTAR, Direccio.__getitem__(claus[i]))))
            if (actual.es_valid()):
                fills.append(actual)
        return fills


class Rana(joc.Rana):
    def __init__(self, *args, **kwargs):
        super(Rana, self).__init__(*args, **kwargs)
        self.__closed = None
        self.__oberts = None
        self.__accions = None
        self.__jumping = 0

    def pinta(self, display):
        pass

    def _cerca(self, estat: Estat):
        self.__oberts = []
        self.__closed = set()

        self.__oberts.append(estat)
        actual = None

        while len(self.__oberts) > 0:
            actual = self.__oberts[0]
            self.__oberts = self.__oberts[1:]

            if actual in self.__closed:
                continue

            if not actual.es_valid():
                self.__closed.add(actual)
                continue

            estats_fills = actual.generaFills()

            if actual.es_meta():
                break

            for estat_f in estats_fills:
                self.__oberts.append(estat_f)

            self.__closed.add(actual)

        if actual.es_meta():
            accions = []
            iterador = actual

            while iterador.pare is not None:
                pare, accio = iterador.pare
                accions.append(accio)
                iterador = pare

            self.__accions = accions


    def actua(
            self, percep: entorn.Percepcio
    ) -> entorn.Accio | tuple[entorn.Accio, object]:
        percepcions=percep.to_dict()
        claus=list(percepcions.keys())
        estat = Estat(percep[claus[0]],percep[claus[1]],percep[claus[2]])

        if self.__accions is None:
            self._cerca(estat=estat)

        print(self.__accions)
        if self.__accions:
            if(self.__jumping>0): #esta botant
                self.__jumping-=1
                return AccionsRana.ESPERAR
            else:
                acc = self.__accions.pop(0)
                print("Acció"+str(acc))
                if(acc[0]==AccionsRana.BOTAR):
                    self.__jumping=2
                return acc[0],acc[1]
        else:
            return AccionsRana.ESPERAR



