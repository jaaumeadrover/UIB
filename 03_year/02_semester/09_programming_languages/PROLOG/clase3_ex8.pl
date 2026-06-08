#Vermella no és tòxic

veri:-
    VM is 0,          
    toxic([L,B]),
    toxic([VM,G]),
    toxic([B,T]),
    toxic([L,G]),
    toxic([VM,T]),
    toxic([VD,B]),
    write([VM,L,B,G,T,VD]),
    nl,
    fail.

    
toxic([X,Y]):-
    X=0,
    Y=1.

toxic([X,Y]):-
X=1,Y=0.



