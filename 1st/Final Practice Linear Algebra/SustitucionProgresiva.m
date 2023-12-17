function sol=SustitucionProgresiva(A,b)

%%-----------------------------------------------------------------------%%
%Dada una matriz de coeficientes triangular inferior y una matriz de
%términos independientes (Cada columna representa un sistema distinto),
%resuelve el sistema de forma progresiva, con el procedimiento obvio.
%-------------------------------------------------------------------------%
%Entradas: A matriz de coeficientes triangular inferior
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
%Calculamos el primer término de la solución:
sol(1,l)=b(1,l)/A(1,1);
%Aplicamos el método regresivo de forma matricial para hallar cada término:
for i=2:nfil
    sol(i,l)=(1/A(i,i))*(b(i,l)-A(i,1:i-1)*sol(1:i-1,l));
end
end