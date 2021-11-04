/*
Participantes:
    -Jaume Adrover Fernandez
    -Marc Cañellas Gomez
    -Joan Balaguer Llagostera
*/

#include "my_lib.h"

/*
 * Función: my_strlen
 * -----------------------
 *  Calcula el número de caracteres de una palabra.
 *
 *  str: cadena de caracteres
 *
 *  devuelve: un int que nos indica el número de caracteres
 *
*/
size_t my_strlen(const char *str){
    size_t len = 0;
    int i = 0;

    while (str[i] != '\0')
    { 
        i++;
        len++;
    }
    return len;
}

/*
 * Función: my_strcmp
 * -----------------------
 *  Compara el código ASCII de dos cadenas de caracteres.
 *
 *  str1 & str2: cadenas de caracteres
 *
 *  Devuelve: un int que nos indica la diferencia del valor
 *  de las dos cadenas de caracteres
*/
int my_strcmp(const char *str1, const char *str2){
    //variable entera que retornaremos en caso de que tener cadenas distintas
    int tot = 0;
    int i = 0;
    
    /* Bucle con el qual llevaremos a cabo la comparación de los códigos 
    ascII de cada caracter */
    while (str1[i] || str2[i]){
        tot = ((int)str1[i] - (int)str2[i]);
        if (tot != 0){
            return tot;
        }
        i++;
    }
    return 0;
}

/*
 * Función: my_strcpy
 * -----------------------
 *  Copia una cadena de caracteres en otra que nos pasarán por parámetro.
 *
 *  dest: cadenas de caracteres donde se copiará la otra caden
    src: cadena que contiene los caracteres que copiaremos en dest
 *
 *  Devuelve: dest (cadena de caracteres)
*/
char *my_strcpy(char *dest, const char *src){
    int i = 0;

    while (src[i]){

        dest[i] = src[i];
        i++;
    }
    
    //añadimos el caracter nulo al final de la cadena
    dest[i] = '\0';

    return dest;
}


/*
 * Función: my_strncpy
 * -----------------------
 *  Copia n caracteres de una cadena en otra que nos pasarán por parámetro.
 *
 *  dest: cadenas de caracteres donde se copiará la otra caden
 *  src: cadena que contiene los caracteres que copiaremos en dest
 *  n: int que contiene el número de caracteres a copiar
 * 
 *  Devuelve: dest(cadena de caracteres)
*/
char *my_strncpy(char *dest, const char *src, size_t n){
    int i = 0;

    for (; i < n; i++){

        dest[i] = src[i];
    }

    return dest;
}

/*
 * Función: my_strcat.
 * -------------------
 *  Añade la cadena apuntada por src a la cadena apuntada por dst.
 * 
 *  dest: es el puntero a la cadena destino
 *  src: apunta a la cadena a concatenar.
 *
 *  Devuelve: el puntero dest
*/
char *my_strcat(char *dest, const char *src){
    //DECLARACIONES
    int i = strlen(dest);
    int n = 0;


    for (; n < strlen(src); i++, n++){
        dest[i] = src[n];
    }

    //devolvemos la cadena dest
    dest[i] = '\0';
    return dest;
}

/*
 * Función: my_strchr
 * -------------------
 *  Escanea la cadena apuntada por str buscando la primera 
 *  aparición del carácter c. 
 * 
 *  str: apunta a la cadena a escanear.
 *  c: es el carácter buscado.
 *
 *  Devuelve: el puntero a la primera aparición del caracter c
 *  o NULL si el carácter no se ha encontrado
*/
char *my_strchr(const char *str, int c){
    //contador para saber la posición de la primera 'c'
    int i = 0;
    const char *ptr = str;

    /*bucle que incrementa el contador de caracteres por cada vez
    que lo completamos (por cada letra de la cadena), y mientras el
    caracter sea diferente a 'c' continuaremos en el bucle*/
    while ((int)str[i] != c)
    {
        i++;
        //si i es mayor a la longitud de str, entonces significa que 
        //la cadena no contiene 'c'y devolvemos NULL
        if (i > strlen(str))
        {
            return NULL;
        }
    }

    //devolvemos el puntero del caracter que se encuentra en la
    //posición i
    ptr = &(str[i]);
    return (char *)ptr;
}


/*
 * Función: my_stack_init
 * -----------------------
 *  Inicializa la pila, con el primer nodo apuntando a NULL i con el
 *  campo de datos a NULL también
 *
 *  size: tamaño de los datos que contendrá cada nodo de la pila.
 * 
 *  Devuelve: un puntero con los datos del nodo extraído o
 *  NULL si la pila está vacía. 
*/

struct my_stack *my_stack_init(int size){

    //reservamos espacio en la memoria para la pila
    struct my_stack *pila = malloc(sizeof(struct my_stack));
    //inicializamos la pila de tamaño size
    pila->size = size;
    //inicializamos el top de la pila en NULL
    pila->top = NULL;

    //devolvemos la pila creada
    return pila;
}

/*
 * Función: my_stack_push
 * -----------------------
 *  Introducimos dentro de la pila un nuevo nodo,
 *  que se ubicará en en el top(top of pile), es decir
 *  arriba de todo.
 *
 *  stack: pila que va a recibir el nodo.
 *  data: puntero que contiene los datos del nodo
 *  a introducir.
 * 
 *
 *  Devuelve: un int que nos indica si se ha realizado
 *  correctamente el push (1=EXIT_SUCCES,0=EXT_FAILURE).
 *
*/
int my_stack_push(struct my_stack *stack, void *data){

    //condicional para saber si la pila está vacía
    if ((stack != NULL) && (stack->size > 0)){
        //reservamos espacio
        struct my_stack_node *nodo = malloc(sizeof(nodo));
        //cambiamos atributos del nuevo nodo
        nodo->data=data;
        nodo->next=stack->top;
        stack->top=nodo;

        return 0;//EXIT_SUCCESS
    }
    return -1; //EXIT_FAILURE
}

/*
 * Función: my_stack_pop
 * -----------------------
 *  Extraemos de la pila el nodo que se encuentra en top,
 *  el cual va a apuntar al nodo anterior al eliminado.
 *
 *  stack: pila de la cual vamos a extraer un nodo.
 * 
 *  Devuelve: un puntero con los datos del nodo extraído o
 *  NULL si la pila está vacía. 
*/
void *my_stack_pop (struct my_stack *stack){

    if(stack->top!=NULL){
        struct my_stack_node *nodoSUP =stack->top;
        stack->top=nodoSUP->next;
        void *datos=nodoSUP->data;
        free(nodoSUP);

        return datos;
    }
    return NULL;
}

/*
 * Función: my_stack_len
 * -----------------------
 *  Devuelve un número entero que representa la
 *  longitud de la pila, es decir, el número de nodos que contiene.
 *
 *  stack: pila de la cual vamos a contabilizar los elementos.
 * 
 *  Devuelve: el número de elementos dentro de la pila. 
*/
int my_stack_len(struct my_stack *stack){
    
    int n = 0;
    struct my_stack_node *nodo = stack->top;
    //bucle while que recorre la pila nodo por nodo
    while (nodo != NULL){
        //apuntamos al siguiente nodo
        nodo = nodo->next;
        //aumentamos el contador
        n++;
    }
    //devolvemos el número de nodos
    return n;
}

/*
 * Función: my_stack_purge
 * -----------------------
 *  Libera el espacio que se había reservado en la memoria para la pila
 *
 *  stack: pila de la cual vamos a contabilizar los elementos.
 * 
 *  Devuelve: mediante la variable n, el numero de bytes liberados
*/
int my_stack_purge(struct my_stack *stack){
    //numero de bytes acumulados
    int n = sizeof(struct my_stack);
    struct my_stack_node *nodo = stack->top;
    
    //bucle que recorre la pila nodo a nodo
    while (nodo != NULL){
        //sumamos los bytes que ocupan los nodos
        n += sizeof(struct my_stack_node);
        //sumamos los bytes que ocupa el tipo de datos guardado en los nodos
        n += stack->size;
        //liberamos el espacio que ocupa cada nodo
        free(nodo);
        nodo = nodo->next;
    }

    //liberamos el espacio que ocupa la pila
    free(stack);
    //devolvemos el número de bytes necesarios
    return n;
}


/*
 * Función: my_stack_write
 * -----------------------
 *  Escribe todo el contenido de la pila, en un fichero.
 *
 *  stack: pila la cual escribiremos en el fichero.
 *  filename: path al fichero donde escribiremos la pila.
 * 
 *  Devuelve: un entero que nos indica el numero de elementos
 *  almacenados (retorna -1 en caso de error).
*/
int my_stack_write (struct my_stack *stack, char *filename){
    //Inicializamos el contador de bytes a -1 por si la apertura del fichero no
    //funcionase correctamente, devolver dicho numero
    int bytes = -1;

    //Creamos una copia de la pila actual que nos han pasado por parámetro
    struct my_stack *aux = my_stack_init(stack->size); 
    struct my_stack_node *nodoStack = stack->top;

    //Copiar la pila original en la pila auxiliar en orden inverso
    while(nodoStack!=NULL){
        my_stack_push(aux,nodoStack->data);
        nodoStack= nodoStack->next;
    }

    //Declaramos un nodo y lo inicializamos al nodo top
    struct my_stack_node *nodo = aux->top;

    //Abrimos el fichero, que nos retorna un numero que nos indica si 
    //la apertura ha ido bien o no 
    int fichero = open(filename, O_WRONLY | O_CREAT | O_TRUNC, S_IRUSR | S_IWUSR);

    /* Realizamos un control de errores para saber si la apertura 
    se ha realizado correctamente */

    //Si el entero retornado de la función open és un 0, significa que 
    //la operación no se ha realizado correctamente
    if(fichero < 0){
        perror("Error");
        return bytes;
    }

    //escribimos el size de la pila mediante la función write(fichero, 
    //dirección del dato a escribir, tamaño del dato a escribir)
    bytes = write(fichero, &aux->size, sizeof(int));

    //Realizaremos un bucle para escribir nodo a nodo dentro del fichero
    while(nodo != NULL){
        //escribimos registro a registro (12bytes)
        bytes += write(fichero, nodo->data, stack->size);
        nodo = nodo->next;
    }

    //Cerramos el fichero y realizamos un contro de errores para saber 
    //si se han producido errores
    int cierre = close(fichero);

    if(cierre == -1){
        perror("Error");
        return -1;
    }

    //Control de errores a consecuencia de las escrituras
    if(bytes == -1){
        perror("Error");
        return bytes;
    }else{
        //Retornamos el numero de elementos
        return bytes / aux->size; 
    }
}

/*
 * Función: my_stack_read
 * -----------------------
 *  Lee los datos de la pila almacenados en el fichero.
 *
 *  filename: path del fichero de donde queremos leer.
 * 
 *  Devuelve: la pila que hemos creado a partir de
 *  la información del fichero (retorna NULL en caso de error).
*/
struct my_stack *my_stack_read (char *filename){
    
    int size;
    struct my_stack *stack;
    void *data; 

    // Abrimos el fichero
    int fichero = open(filename, O_RDONLY);

    // Control de errores
    if (fichero < 0){
        perror("Error");
        return NULL;
    }

    //leemos el size de la pila contenida dentro del fichero
    read(fichero, &size, sizeof(int));

    //inicializamos la pila que creamos
    stack = my_stack_init(size);
    //reservamos espacio para el primer dato de la pila
    data = malloc(size);
    //si la función malloc nos retorna NULL, la operación no 
    //se ha realizado con exito
    if(data == NULL){
        perror("Error");
        return data;
    }

    //Bucle para restaurar los nodos
    while (read(fichero, data, size) > 0){
        //Reservamos memoria para el data
        my_stack_push(stack, data);
        data = malloc(size);
        if (data == NULL){
            perror("Error");
            return NULL;
        }
    }

    //Cerramos el fichero y realizamos el control de errores
    int cierre = close(fichero);
    if(cierre < 0){
        perror("Error");
        return NULL;
    }

    //liberamos la memória usada
    free(data);
    return stack;
}
