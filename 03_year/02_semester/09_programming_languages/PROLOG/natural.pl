natural(0).

natural(X):-natural(Y), X is Y+1,write(X).