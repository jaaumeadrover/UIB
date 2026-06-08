/*
INTEGRANTS DEL GRUP: Joan Balaguer Llagostera,Jaume Adrover Fernández
*/

cls:-write('\e[2J'), gotoXY(0,0).
gotoXY(X,Y):-write('\e['),write(X),write(";"),write(Y),write("H").

colorsValids([negre,vermell,verd,groc,blau,lila,cel]).

color(negre):-write("\e[1;90m").
color(vermell):-write("\e[1;91m").
color(verd):-write("\e[1;92m").
color(groc):-write("\e[1;93m").
color(blau):-write("\e[1;94m").
color(lila):-write("\e[1;95m").
color(cel):-write("\e[1;96m").
color(blanc):-write("\e[1;97m").



%EXERCICI 1

%Escriu i retorna el nonograma passat per paràmetre
escriuNonograma([]).
escriuNonograma([Fila|Resto]):-
    write(Fila), nl,
    escriuNonograma(Resto).



%EXERCICI 2

%Dibuixa el Nonograma a les posicions (X,Y)->(Columnes,Files)
mostraNonograma([],_,_,_,_).
mostraNonograma(MATRIU,Files,Columnes,IncFiles,IncColumnes):-
    cls,
    mostraNono(MATRIU,Files,Columnes,IncFiles,IncColumnes).

% Mostra un nonograma per pantalla
mostraNono([],_,_,_,_).
mostraNono([X|L],Files,Columnes,IncFiles,IncColumnes):-
        gotoXY(Columnes,Files),
        mostraFilaNono(X,Files,Columnes,IncFiles,IncColumnes),
        Files2 is Files+IncFiles,
        mostraNono(L,Files2,Columnes,IncFiles,IncColumnes).

% Mostra una fila del nonograma
mostraFilaNono([],_,_,_,_).
mostraFilaNono([X|L],Files,Columnes,IncFiles,IncColumnes):-
        gotoXY(Columnes,Files),
        color(X),
        write("X"),
        Columnes2 is Columnes+IncColumnes,
        mostraFilaNono(L,Files,Columnes2,IncFiles,IncColumnes).



%EXERCICI 3

%Construir un nonograma	aleatori a partir de una llista de colors, un número de files i	un número de columnes.
ferNonograma([],_,_,[]).
ferNonograma(_,0,_,[]).
ferNonograma(Colors,Files,Columnes,[Actual|Resta]):-
        ferFila(Colors,Columnes,Actual),
        Files2 is Files-1,
        ferNonograma(Colors,Files2,Columnes,Resta).

%Genera una fila aleatoria del Nonograma
ferFila(_,0,[]).
ferFila(Colors,N,[Actual|Resta]):-
    random_member(Actual,Colors),
    N1 is N-1,
    ferFila(Colors,N1,Resta).


%EXERCICI 4

%Mètode que ens descriu un Nonograma amb pistes per files i columnes. Retorna matriu [PistaFiles,PistaColumnes].
descriuNonograma(M,M1):-
    descriuNonograma2(M,M1),
    nl,
    format_matrix(M1,"Pistes"),
    nl.

%Extreu pistes primer de files dins L1 i de columnes dins L2
descriuNonograma2(M,[L1|L2]):-
    treuPistes(M,L1),
    transpose(M,M1),
    treuPistes(M1,L2).

%Treim pistes de fila en fila
treuPistes([],[]).
treuPistes([X|L1],[Y|L2]):-
    extreu(X,Y),
    treuPistes(L1,L2).

%Possibles casos a trobar en una fila
extreu([],[]).
extreu([X|L1],[[seguits,X,1]|L2]):-  %CAS ON NOMÉS HI HA 1 SEGUIT
    vegades(X,[X|L1],1),
    extreu(L1,L2).

extreu([X|L1],[[seguits,X,N]|L2]):-  %CAS ON HI HA N seguits
    vegades(X,[X|L1],N),        
    seguits(N,X,[X|L1]),
    !,
    borrar(X,[X|L1],L3),
    extreu(L3,L2).

extreu([X|L1],[[noseguits,X,N]|L2]):- %CAS NO SEGUITS
    vegades(X,[X|L1],N),
    !,
    borrar(X,[X|L1],L3),
    extreu(L3,L2).


%Nombre de vegades que apareix un caràcter en una seqüència
vegades(_,[],0).
vegades(C,[X|Xs],N):-
    C \= X,
    vegades(C,Xs,N).

vegades(C,[C|Xs],N):-
    vegades(C,Xs,N1),
    N is N1+1.


%Nombre de repeticions concatenades de un determinat caràcter
seguits(_, _, []):-fail.
seguits(N, X, [X|L1]):-casSeguits(N, X, [X|L1]), !.
seguits(N, X, [_|L1]):-seguits(N, X, L1).

casSeguits(1, X, [X|_]).
casSeguits(N1, X, [X|L1]):-N2 is N1-1, casSeguits(N2, X, L1), !.


%Borram un caràcter de una determinada seqüencia
borrar(_,[],[]).
borrar(X,[X|L1],L2):- borrar(X,L1,L2),!.
borrar(X,[_|L1],L2):- borrar(X,L1,L2).

%Fer la transposta de una matriu, agafam el primer de cada vector i retornam la resta
transposta([], []).
transposta([[]|_], []).
transposta(Matrix, [FirstCol|TransposedRest]) :-
    extract_heads(Matrix, FirstCol, RemainingRows),
    transposta(RemainingRows, TransposedRest).

%Extreure els caps de totes les files
extract_heads([], [], []).
extract_heads([[Head|Tail]|Rows], [Head|Heads], [Tail|RemainingRows]) :-
    extract_heads(Rows, Heads, RemainingRows).




%EXERCICI 5

%Mostrar pistes en forma horitzontal, és a dir, lectura en row-major-order de una matriu 
mostraPistesHorizontals([], _, _, _, _).
mostraPistesHorizontals([L|Desc], Fila, Col, FInc, CInc):-drawChar(L, Fila, Col, FInc), Y is Fila+CInc, mostraPistesHorizontals(Desc, Y, Col, FInc, CInc).

%Mostrar pistes en vertical, és a dir, en column-major-order de una matriu
mostraPistesVerticals(Desc, Fila, Col, FInc, CInc):-transpose(Desc, L), mostraPistesHorizontals(L, Fila, Col, FInc, CInc).


%Metodes per a dibuixar un determinat caràcter en els següents casos 
drawChar([], _, _, _).
drawChar([[seguits, C, 1]|L1], Fila, Columna, Inc):-gotoXY(Columna, Fila), color(C), write(1), X1 is Columna+Inc, drawChar(L1, Fila, X1, Inc).
drawChar([[seguits, C, N]|L1], Fila, Columna, Inc):-N>1, color(C), X2 is Columna-1, gotoXY(X2, Fila), write('<'), write(N), write('>'), X1 is Columna+Inc, drawChar(L1, Fila, X1, Inc).
drawChar([[no_seguits, C, N]|L1], Fila, Columna, Inc):-gotoXY(Columna, Fila), color(C), write(N), X1 is Columna+Inc, drawChar(L1, Fila, X1, Inc).


%EXERCICI 6 

%Inserir un caràcter en la seqüència determinada
insereix(E, L, [E|L]).
insereix(E, [X|Y], [X|Z]):- insereix(E, Y, Z).

%Crear les permutacions d,una llista determinada
permut([], []).
permut([X|Y], Z):- permut(Y, L), insereix(X, L, Z).

%Metode per a extreure tots els colors que pertanyen a una fila
extreuColor(List, Result) :-
  extreuColor(List, [], Result).

extreuColor([], Acc, Acc).
extreuColor([[_, _, 0]|L1], Acc, Result) :-
  extreuColor(L1, Acc, Result).
extreuColor([[_, C, 1]|L1], Acc, Result) :-
  extreuColor(L1, [C|Acc], Result).
extreuColor([[_, C, N1]|L1], Acc, Result) :-
  N1 > 1,
  N2 is N1 - 1,
  extreuColor([[_, C, N2]|L1], [C|Acc], Result).

%Metode per a comprovar que es compleixin les condicions establertes anteriorment
esCorrecte([], _).
esCorrecte([[seguits, C, 1]|L1], LC):-vegades(C, LC, 1), !, esCorrecte(L1, LC).
esCorrecte([[seguits, C, N]|L1], LC):-seguits(N, C, LC), !, esCorrecte(L1, LC).
esCorrecte([[no_seguits, C, N]|L1], LC):-not(seguits(N, C, LC)), !, esCorrecte(L1, LC).

%Comprova si es compleixen les condicions per columnes
comprovaVertical([], _).
comprovaVertical([X|L1], [N|Nono]):- esCorrecte(X, N), comprovaVertical(L1, Nono).

%Metode que extreu un vector de colors des de les pistes. Posteriorment crea permutacions fins que es compleix la condició actual
buscaNono([],[]):-!.
buscaNono([X|L1],[Y|L2]):- extreuColor(X, C), permut(C, Y), esCorrecte(X, Y), buscaNono(L1, L2).

buscaNono2(Files,Nono,NonoT):- buscaNono(Files,Nono),transposta(Nono,NonoT).

%Metode per resoldre el Nonograma a partir d,una matriu amb les pistes de files i columnes. Posteriorment mostra la solució formatejada
resolNonograma([Files,Columnes], Nono):- buscaNono2(Files, Nono,NonoT), comprovaVertical(Columnes, NonoT),nl,format_matrix(Nono, "Nonograma"),nl.

nono([[lila, vermell, vermell], [verd, vermell, vermell], [lila, verd, vermell], [verd, vermell, vermell], [lila, vermell, vermell], [verd, verd, vermell]]).



pistes([[[[seguits, lila, 1], [seguits, vermell, 2]], [[seguits, verd, 1], [seguits, vermell, 2]], [[seguits, lila, 1], [seguits, verd, 1], [seguits, vermell, 1]], [[seguits, verd, 1], [seguits, vermell, 2]], [[seguits, lila, 1], [seguits, vermell, 2]], [[seguits, verd, 2], [seguits, vermell, 1]]], [[[no_seguits, lila, 3], [no_seguits, verd, 3]], [[no_seguits, vermell, 4], [no_seguits, verd, 2]], [[seguits, vermell, 6]]]]).

avalua3:-cls,pistes(P), nono(Nono), P=[H,V],
         N1 is 3*3+2, mostraPistesVerticals(V,1,N1,1,3), Fila is 4, 
         mostraPistesHorizontals(H,Fila,2,1,3).

prova3:- pistes(P),resolNonograma(P,Nono),mostraNonograma(Nono,4,11,1,3).

%UTILS

%Visualitzar una matriu correctament donat un string per paràmetre
format_matrix(Matrix, Label) :-
    format("~s:~n", [Label]),
    format_matrix_rows(Matrix).

format_matrix_rows([]).
format_matrix_rows([Row|Rest]) :-
    format_row(Row),
    format("~n", []), % Add a newline character at the end of each row
    format_matrix_rows(Rest).

format_row([]) :-
    format("~n", []).
format_row([Cell]) :-
    format("~w~n", [Cell]).
format_row([Cell|Rest]) :-
    format("~w ", [Cell]),
    format_row(Rest).

%Transposada d matriu
first([], []).
first([[]|_], []).
first([[X|_]|L2], [X|L3]):-first(L2, L3).

follow([], []).
follow([[]|_], []).
follow([[_|L1]|L2], [L1|L3]):-follow(L2, L3).

transpose([[]|_], []).
transpose(L1, [L3|L5]):-first(L1, L3), follow(L1, L4), transpose(L4, L5).

/* TESTING: part on s'introdueixen totes les sentencies de prova
            per a poder provar el correcte funcionament de la pràctica.


    ·Exercici 1: 

    escriuNonograma([[verd,lila,vermell,vermell],[blau,verd,blau,blau],[lila, blau,verd,verd],[verd,blau,vermell, verd]]).

    ·Exercici 2:
                
    mostraNonograma([[verd,lila,vermell,vermell],[blau,verd,blau,blau],[lila, blau,verd,verd],[verd,blau,vermell, verd]],3,5,1,3).

    ·Exercici 3:

    ferNonograma([verd,blau,vermell],3,4,Nono).

    ·Exercici 4:

    descriuNonograma([[verd,lila,vermell,vermell],[blau,verd,blau,blau],[lila, blau,verd,verd],[verd,blau,vermell, verd]],L).

    ·Exercici 5:

    mostraPistesHorizontals([
 [[seguits, verd, 1], [seguits, lila, 1], [seguits, vermell, 2]],
[[no_seguits, blau, 3], [seguits, verd, 1]],
[[seguits, lila, 1], [seguits, blau, 1], [seguits, verd, 2]],
[[no_seguits, verd, 2], [seguits, blau, 1], [seguits, vermell, 1]]
],20,10,3,3).



    mostraPistesVerticals([
 [[seguits, verd, 1], [seguits, lila, 1], [seguits, vermell, 2]],
[[no_seguits, blau, 3], [seguits, verd, 1]],
[[seguits, lila, 1], [seguits, blau, 1], [seguits, verd, 2]],
[[no_seguits, verd, 2], [seguits, blau, 1], [seguits, vermell, 1]]
],20,10,3,3).


    ·Exercici 6:

    resolNonograma([
 [
 [[seguits, verd, 1], [seguits, lila, 1], [seguits, vermell, 2]],
[[no_seguits, blau, 3], [seguits, verd, 1]],
[[seguits, lila, 1], [seguits, blau, 1], [seguits, verd, 2]],
[[no_seguits, verd, 2], [seguits, blau, 1], [seguits, vermell, 1]]
],
[
[[no_seguits, verd, 2], [seguits, blau, 1], [seguits, lila, 1]],
[[seguits, lila, 1], [seguits, verd, 1], [seguits, blau, 2]],
[[no_seguits, vermell, 2], [seguits, blau, 1], [seguits, verd, 1]],
[[seguits, vermell, 1], [seguits, blau, 1], [seguits, verd, 2]]
]
],Nono).

*/