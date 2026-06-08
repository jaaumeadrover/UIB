


aplanar([],[]).
aplanar([X|L1],L2):-
    is_list(X),
    aplanar(X,L3),
    aplanar(L1,L4),
    append(L3,L4,L2).
aplanar([X|L1],[X|L3]):-
    aplanar(L1,L3).





agafar(1,[X|_],X).
agafar(N,[_|Xs],E):-
    N1 is N-1,
    agafar(N1,Xs,E).





rotardreta([],[]).
rotardreta(L,[Y|L1]):-
    darrer(L,Y),
    rdc(L,L1).

darrer([X],X).
darrer([X|Xs],L):-
    darrer([Xs],L).

rdc([X],[]).
rdc([X|Xs],[X|L1]):-
    rdc(Xs,L1).
    


rotaresquerra(L,[L1|Y]):-
    primer(L,Y),
    totMenosPrimer(L,L1).

primer([X|Xs],X).

totMenosPrimer([X|Xs],Xs).
    


sumarparells(L,S):-
    sumarAux(L,0,S).

sumarAux([],N,0).
sumarAux([X|Xs],N,S):- %cas parell
    even_number(N),
    N1 is N+1,
    sumarAux(Xs,N1,S1),
    S is S1+X.

sumarAux([X|Xs],N,S):-
    odd_number(N),
    N1 is N+1,
    sumarAux(Xs,N1,S).

even_number(N) :-
    N mod 2 =:= 0.

odd_number(N) :-
    \+ even_number(N).



maximoLista([X],X).
maximoLista([X|Xs],MAX):-
    maximoLista(Xs,MAX1),
    maximo(X,MAX1,MAX).
    
maximo(X,Y,MAX):-
    X>=Y,
    MAX is X.

maximo(X,Y,MAX):-
    X<Y,
    MAX is Y.

copia([],[]).
copia([X|Xs],[X|L2]):-
    copia(Xs,L2).


resta([],[],[]).
resta(X,Y,[L1|L2]):-
    agafaPrimer(X,N1,X2),
    agafaPrimer(Y,N2,Y2),
    L1 is N1-N2,
    resta(X2,Y2,L2).

agafaPrimer([X|Xs],X,Xs).


%EXERCICI PARTICIO EXAMEN 2010-2011
particio(_, _, [], []).
particio(L, N, [L1|L2], []):-
    L1 =< N,
    contains(L1, L),
    particio(L, N, L2, []).
particio(L, N, [], [UP1|UP2]):-
    UP1 > N,
    contains(UP1, L),
    particio(L, N, [], UP2).






particio(L, N, [L1|L2], [UP1|UP2]):-
    L1 =< N,
    contains(L1, L),
    UP1 > N,
    contains(UP1, L),
    particio(L, N, L2, UP2).

contains( X,[X|_]).
contains(Y,[_|Xs]):-
    contains( Y,Xs).



comprimir([],[]).
comprimir([X|Xs],[L1|L2]):-
    compta(X,Xs,N,Resta),
    N1 is N+1,
    unir(N1,X,L1),
    comprimir(Resta,L2).

unir([],[],[]).
unir(X,C,Result):-
    atom_concat(X,C,Result).

compta(_,[],0,[]).
compta(C,[X|Xs],0,[X|Xs]):- %diferents
    X \= C.
compta(C,[C|Xs],N,Resta):-
    compta(C,Xs,N1,Resta),
    N is N1+1.










aparicions([],X,0).
aparicions([X|Xs],X,N):-
    aparicions(Xs,X,N1),
    N is N1+1.

aparicions([X|Xs],Y,N):-
    aparicions(Xs,Y,N).




%TRANSPOSTA MATRIU


transposta([[]|_],[]).
transposta(M,[M1|M2]):-
    primers(M,M1,M3),
    transposta(M3,M2).

primers([],[],[]).
primers([[A1|A2]|Resta] , [A1|L2]   ,   [A2|L3]):-
    primers(Resta,L2,L3).






america(peru).
america(quebec).
europa(roma).
europa(suecia).
solucio :-
Homes = [pep, biel, tofol, miquel, lluis],
permutacio([oceania, peru, quebec, roma, suecia], Ciutats),
/* Enunciat 1*/
posicio(pep, Homes, P1),
america(X),
posicio(X, Ciutats, P1),
permutacio([5, 10, 15, 20, 30], Durades),
posicio(10, Durades, P1),
/* Enunciat 2 */
posicio(miquel, Homes, P2),
posicio(roma, Ciutats, P2),
/* Enunciat 3 */
posicio(5, Durades, P3),
posicio(quebec, Ciutats, P3),
/* Enunciat 4 */
permutacio([joana, magdalena, antonia, maria,teresa], Dones),
posicio(antonia, Dones, P4),
posicio(30, Durades, P4),
europa(X1),
posicio(X1, Ciutats, P41),
P4 \= P41,
posicio(lluis, Homes, P42),
P4 \= P42,
/* Enunciat 5 */
posicio(joana, Dones, P5),
posicio(peru, Ciutats, P5),
/* Enunciat 6 */
posicio(teresa, Dones, P6),
posicio(tofol, Homes, P6),
europa(X2),
posicio(X2, Ciutats, P6),
/* Enunciat 7 */
posicio(maria, Dones, P7),
posicio(15, Durades, P7),
europa(X3),
posicio(X3, Ciutats, P7),
write(Homes),nl,write(Dones),nl, write(Ciutats),nl, write(Durades).

insertar(E,L,[E|L]).
insertar(E,[X|Y],[X|Z]) :-
insertar(E,Y,Z).
permutacio([],[]).
permutacio([X|Y],Z) :-
permutacio(Y,L),
insertar(X,L,Z).
posicio(Element, [Element|_], 1).
posicio(Element, [_|Tail], Posicio) :-
posicio(Element, Tail, Pos1),
Posicio is Pos1 + 1.






invertir([X],X).
invertir([X|Xs],[L1|X]):-
    invertir(Xs,L1).



equal(X,X).
not(P) :- P, !, fail.
not(P).