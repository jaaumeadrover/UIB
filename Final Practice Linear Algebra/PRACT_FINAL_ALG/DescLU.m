function [L,U,P]=DescLU(A)
%%
%%-----------------------------------------------------------------------%%
%Aplica el m�todo de la descomposici�n LU, teniendo en cuenta que siempre
%existe almenos para PA, siendo P una matriz de permutaci�n.
%-------------------------------------------------------------------------%
%Entradas:  A matriz cuadrada no singular de coeficientes
%-------------------------------------------------------------------------%
%Salidas:   L matriz triangular inferior de la descomposici�n
%           U matriz triangular superior de la descomposici�n
%           P matriz de permutaciones que se aplica a A
%-------------------------------------------------------------------------%
%%

%Como siempre, sacamos la dimensi�n de A y obligamos a que sea cuadrada:

[nfil,ncol]=size(A);
if nfil~=ncol
    L = []; U = []; P = [];
    return
end

%Empezamos definiendo variables sin valor donde almacenaremos la soluci�n

n=nfil;
P = eye(n);
U=zeros(n);
L=zeros(n);
pos1=1;

%Empezamos a definir los primeros valores antes del m�todo recursivo (El
%primer elemento de L, luego el primero de U (con o sin permutaci�n) y la
%primera columna de L:

L(1,1)=1;

%Planteamos la posibilidad de que el primer elemento de a sea 0, con la 
%permutaci�n correspondiente, y el elemento (1,1) de U es el (1,1) de A

if A(1,1)==0
    for v1=nfil:-1:2
        if A(v1,1)~=0
            pos1=v1; break
        end
    end
    P([1 pos1],:)=P([pos1 1],:);
    A=P*A;
end
U(1,1)=A(1,1);

%Y finalmente la primera columna de L:

for k=2:n
    L(k,1)=A(k,1)/A(1,1);
end

%Empezamos el m�todo recursivo sobre las columnas:

for col=2:n
    
    %Calculamos los elementos de cada columna de U
    
    for filU=1:col
        U(filU,col)=A(filU,col)-L(filU,1:filU-1)*U(1:filU-1,col);
    end
    
    %Aqu� consideramos el caso de que el elemento de la diagonal de U valga
    %0, y procederemos a realizar una permutaci�n:
    
    if U(col,col)==0
        ind=n;
        %Creamos un bucle del que no salga hasta que halle una permutaci�n
        %v�lida, y si A cumple los requisitos de no singular, la va a
        %hallar siempre:
    while U(col,col)==0
        Pk=eye(n);
        
        %Planteamos las permutaciones tomando la �ltima fila en adelante, y
        %dada la permutaci�n, cambiamos A y L provisionales y calculamos
        %los elementos de U, y comprobamos que con �se sea v�lido:
        
        Pk([col ind],:)=Pk([ind col],:);
        Aprov=Pk*A;
        Lprov=Pk*L;
        ind=ind-1;
        U(col,col)=Aprov(col,col)-Lprov(col,1:col-1)*U(1:col-1,col);
    end
    
    %Una vez salga del while, sabremos que �sa permutaci�n nos vale, por
    %tanto la efectuamos sobre  A y L, y la efectuamos tambi�n en P para ir
    %guard�ndola:
    
     P=Pk*P;
     L=Pk*L;
     A=Pk*A;
    end
    
    %Finalmente calculamos los elementos de L de �sa columna y los
    %elementos de la diagonal de L:
    
    for filL=col+1:n
        L(filL,col)=(A(filL,col)-(L(filL,1:col-1)*U(1:col-1,col)))/U(col,col);
    end
    L(col,col)=1;
end