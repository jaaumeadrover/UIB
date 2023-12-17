function sol=SustitucionRegresiva(A,b)

%%-----------------------------------------------------------------------%%
%Dada una matriz de coeficientes triangular y una matriz de t�rminos
%independientes (Cada columna representa un sistema distinto), resuelve el
%sistema de forma regresiva, con el procedimiento obvio.
%-------------------------------------------------------------------------%
%Entradas: A matriz de coeficientes triangular superior
%          b matriz de t�rminos independientes (Cada columna representa un
%          sistema distinto)
%-------------------------------------------------------------------------%
%Salidas:  c matriz de soluciones de los sistemas (Cada columna es soluci�n
%          de cada sistema
%%-----------------------------------------------------------------------%%

%Sacamos la dimension del sistema y preparamos el vector de soluciones
[nfil,ncol]=size(b);
sol=zeros(nfil,ncol);
%entramos en una iteracion para cada sistema (columna de b):
for l=1:ncol
%Calculamos el �ltimo t�rmino de la soluci�n:
sol(nfil,l)=b(nfil,l)/A(nfil,nfil);
%Aplicamos el m�todo regresivo de forma matricial para hallar cada t�rmino:
for i=nfil-1:-1:1
    sol(i,l)=(1/A(i,i))*(b(i,l)-A(i,i+1:nfil)*sol(i+1:nfil,l));
end
end