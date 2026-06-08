solucio:-
    digit(A),digit(B),digit(C),
    7 is A*B-C,
    digit(D),digit(E),
    2 is D*4/E,
    digit(F),digit(G),digit(H),
    7 is F-G+H,
    7 is A+D+F,
    4 is B-4+G,
    7 is C-E+H,
    write([A,B,C,D,E,F,G,H]).

digit(N) :- between(1, 9, N).


Sumar([],0).
sumar([X|L],N):-
                SUMAR(L,N1),
                N is N1+X

MAXIM([X],X).
MAXIM([X,y|L],x):- x>y,MAXIM([X|L],M).
MAXIM([y|L],M):-MAXIM(L,M).

Permutacio([],[]).
Permutacio(E,[X|Y],[X,Z]):-Permutacio(Y,L),
      -inserir(X,L,Z).