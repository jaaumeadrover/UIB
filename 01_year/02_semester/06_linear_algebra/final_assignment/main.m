%Declaraciones variables
L=1;
T=10;
I=15;
J=50;
h=L/I;
k=T/J;
alpha=k/h;
f= @(x) sin(pi*x); %f(x)=sin(pi+x);
g= @(x) 1+0*x; %g(x)=1;
vector_x=linspace(0,L,I+1);%Vector de valores X, DE O a L, en I+1 intervalos
%de misma longitud.
g_0=g(vector_x);
u_0=f(vector_x)';
vector_t=linspace(0,T,J+1);%Vector de valores T, DE O a T, en J+1 intervalos
%de misma longitud.

U=zeros(I+1,J+1);%Inicializamos la matriz de 16x51 con todo zeros
U(:,1)=f(vector_x)';%Ponemos en la primera columna la imagen
%del vector_x en f(x)

%Construimos la matriz del sistema que nos servir� para calcular 
%las columnas posteriores
matriu_sistema=zeros(I-1,I-1);
%Bucle inicializando con los valores correspondientes
for j=1:I-2
    matriu_sistema(j,j)=(1+alpha^2)/2;
    matriu_sistema(j,j+1)=-(alpha^2)/4;
    matriu_sistema(j+1,j)=-(alpha^2)/4;
end
matriu_sistema(I-1,I-1)=(1+alpha^2)/2;

%Descomposici�n LU por pivotaje maximal
[LOW,UP,P]=DescLU(matriu_sistema);
%ForwardSubst para calcular el primer valor W_0
[Y1]=SustitucionProgresiva(LOW,P*u_0(2:I));
%BackwardSubst para calcular el primer valor W_0
w_0=SustitucionRegresiva(UP,Y1);

%Calculamos la columna -1 para poder calcular la segunda a partir de esta
%y la primera
u_menos1=zeros(I+1,1);
u_menos1(2:I)=(1/2).*w_0-(g_0(2:I).*k)';
U=[u_menos1,U];

%Bucle de tratamiento para sacar las columnas restantes
%Cogemos las dos columnas anteriores y calcularemos la siguiente
for l=3:J+2
    %Descomposicion LU de la matriz del sistema
    [LOW2,UP2,P]=DescLU(matriu_sistema);
    %Inicializamos las dos columnas a utilizar para calcular la siguiente
    u_kmenos2=U(:,l-2);
    u_kmenos1=U(:,l-1);
    u_k=U(:,l);
    %ForwardSubst 
    [Y2]=SustitucionProgresiva(LOW2,P*u_kmenos1(2:I));
    %BackwardSubst
    w=SustitucionRegresiva(UP2,Y2);
    %Calculamos la columna U_k
    u_k(2:I)=w-u_kmenos2(2:I);
    U(:,l)=u_k;
end
U(:,1)=[];

%Bucle en el cual vamos visualizando en un gr�fico vector a vector,
%simulando la cuerda en movimiento
for j=1:J+1
    plot(vector_x,U(:,j))
    ylim([-2,2])
    pause(k);
end

%Mostramos por consola nustra matriz U
disp("Matriz U:")
disp(U)
%Mostramos por consola nuestra matriz U transpuesta
disp("Matriz U transpuesta:")
disp(U')