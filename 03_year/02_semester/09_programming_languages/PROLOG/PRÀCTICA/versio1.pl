
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