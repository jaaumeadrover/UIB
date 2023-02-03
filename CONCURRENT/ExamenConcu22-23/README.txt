Plantejament:

1)Cotxe: procés que per entrar al pont ha de superar les següents condicions:
	1.1) No hi ha cotxes al pont
	1.2) No espera la ambulància
	1.3) NORD: no hi ha >=cotxes sud
	1.4) SUD: no hi ha >cotxes nord
2)Ambulancia: la ambulància només espera si hi ha cotxes al pont.
3)MonitorPont: mètodes DemanaAccesNord,demanaAccesSud,demanaAccesAmbulancia,surtVehicle
4)Main: res a explicar

Explicacions:
En cas de que la quantitat de cotxes sigui igual a nord/sud, he prioritzat els que venen de la direcció sud.

Conclusions:
Hagues pogut parametritzar el mètode d'acces(cotxes) amb un enter ja que els prints i els bucles són
pràcticament iguals, no ho he fet per falta de temps i per no sebre parametritzar la condicio
de la quantitat dels cotxes(a lo millor un mètode boolean?).