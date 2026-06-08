    comentari1([a,_,_]).
    comentari1([c,_,_]).
    #Comentari2
    comentari2([a,_,_]).
    comentari2([X,_,_]):- X\=a,X=b.
    #Comentari4
    comentari3([X,_,a]):- X\=c.
    comentari3([a,_,_]).
    comentari3([_,a,_]).
    #Comentari4
    comentari4([_,b,_]).
    comentari4([_,a,_]).

tresPrimers(L):-
    permutation([a,b,c],L),
    comentari1(L),
    comentari2(L),
    comentari3(L),
    comentari4(L).


#Exemple 6

#x+1=y-1
#y+1=2(X-1)