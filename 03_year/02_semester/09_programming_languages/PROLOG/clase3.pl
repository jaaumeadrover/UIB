suma():- 
         sumaDigit(0,S,S,C,R1),
         sumaDigit(R1,E,O,N,R2),
         sumaDigit(R2,R,D,I,R3),
         sumaDigit(R3,T,0,C,0),
         T\==0,
         C\==0,
         diferents([T,R,E,S,D,O,C,I,N]), 
         write([T,R,E,S,D,O,S,C,I,N,C]),
         nl,
         fail.
         



diferents([]).
diferents([X|L]):- not(member(X,L)),diferents(L).

digit(X):- between(0,9,X).


sumaDigit(R,X,Y,Z,R1):-
    residu(R),digit(X),digit(Y),
    T is R+X+Y,
    R1 is T // 10,
    Z is T mod 10.


residu(0).
residu(1).