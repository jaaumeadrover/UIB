function sol=SustitucionRegresiva(A,b)

%%-----------------------------------------------------------------------%%
%Dada una matriz de coeficientes triangular y una matriz de términos
%independientes (Cada columna representa un sistema distinto), resuelve el
%sistema de forma regresiva, con el procedimiento obvio.
%-------------------------------------------------------------------------%
%Entradas: A matriz de coeficientes triangular superior
%          b matriz de términos independientes (Cada columna representa un
%          sistema distinto)
%-------------------------------------------------------------------------%
%Salidas:  c matriz de soluciones de los sistemas (Cada columna es solución
%          de cada sistema
%%-----------------------------------------------------------------------%%

%Sacamos la dimension del sistema y preparamos el vector de soluciones
[nfil,ncol]=size(b);
sol=zeros(nfil,ncol);
%entramos en una iteracion para cada sistema (columna de b):
for l=1:ncol
%Calculamos el último término de la solución:
sol(nfil,l)=b(nfil,l)/A(nfil,nfil);
%Aplicamos el método regresivo de forma matricial para hallar cada término:
for i=nfil-1:-1:1
    sol(i,l)=(1/A(i,i))*(b(i,l)-A(i,i+1:nfil)*sol(i+1:nfil,l));
end
end