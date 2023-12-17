/*
nivelA.c - Adelaida Delgado (adaptación de nivel3.c)
Cada nivel incluye la funcionalidad de la anterior.
nivel A: Muestra el prompt, captura la línea de comandos, 
la trocea en tokens y chequea si se trata de comandos internos 
(cd, export, source, jobs, fg, bg o exit). Si son externos los ejecuta con execvp()
*/

#define _POSIX_C_SOURCE 200112L

#define DEBUGN1 0 //parse_args()
#define DEBUGN3 0 //execute_line()

#define PROMPT_PERSONAL 1 // si no vale 1 el prompt será solo el carácter de PROMPT

#define RESET_FORMATO "\033[0m"
#define NEGRO_T "\x1b[30m"
#define NEGRO_F "\x1b[40m"
#define GRIS "\x1b[94m"
#define ROJO_T "\x1b[31m"
#define VERDE_T "\x1b[32m"
#define AMARILLO_T "\x1b[33m"
#define AZUL_T "\x1b[34m"
#define MAGENTA_T "\x1b[35m"
#define CYAN_T "\x1b[36m"
#define BLANCO_T "\x1b[97m"
#define NEGRITA "\x1b[1m"

#define COMMAND_LINE_SIZE 1024
#define ARGS_SIZE 64
#define N_JOBS 24 // cantidad de trabajos permitidos

char const PROMPT = '$';

#include <errno.h>  //errno
#include <stdio.h>  //printf(), fflush(), fgets(), stdout, stdin, stderr, fprintf()
#include <stdlib.h> //setenv(), getenv()
#include <string.h> //strcmp(), strtok(), strerror()
#include <unistd.h> //NULL, getcwd(), chdir()
#include <sys/types.h> //pid_t
#include <sys/wait.h>  //wait()
#include <signal.h> //signal(), SIG_DFL, SIG_IGN, SIGINT, SIGCHLD
#include <fcntl.h> //O_WRONLY, O_CREAT, O_TRUNC
#include <sys/stat.h> //S_IRUSR, S_IWUSR

int check_internal(char **args);
int internal_cd(char **args);
int internal_export(char **args);
int internal_source(char **args);
int internal_jobs(char **args);
int internal_bg(char **args);
int internal_fg(char **args);

char *read_line(char *line);
int parse_args(char **args, char *line);
int execute_line(char *line);

//métodos añadidos por nosotros
void initJL();
void reaper();
void ctrlc();
void ctrlz();
void removeChar(char *args,char c);
int is_background(char **args);
int jobs_list_add(pid_t pid, char status, char *cmd);
int jobs_list_find(pid_t pid);
int jobs_list_remove(int pos);
int is_output_redirection (char **args);

//Variables añadidas por nosotros
int n_pids = 1;

static char mi_shell[COMMAND_LINE_SIZE]; //variable global para guardar el nombre del minishell

//static pid_t foreground_pid = 0;
struct info_process {
	pid_t pid;
	char status;
	char cmd[COMMAND_LINE_SIZE];
};
static struct info_process jobs_list[N_JOBS]; //Tabla de procesos. La posición 0 será para el foreground

void imprimir_prompt();

int check_internal(char **args) {
    if (!strcmp(args[0], "cd"))
        return internal_cd(args);
    if (!strcmp(args[0], "export"))
        return internal_export(args);
    if (!strcmp(args[0], "source"))
        return internal_source(args);
    if (!strcmp(args[0], "jobs"))
        return internal_jobs(args);
    if (!strcmp(args[0], "bg"))
        return internal_bg(args);
    if (!strcmp(args[0], "fg"))
        return internal_fg(args);
    if (!strcmp(args[0], "exit"))
        exit(0);
    return 0; // no es un comando interno
}

int internal_cd(char **args) {
    printf("[internal_cd()→ comando interno no implementado]\n");
    return 1;
} 

int internal_export(char **args) {
    printf("[internal_export()→ comando interno no implementado]\n");
    return 1;
}

int internal_source(char **args) {
    printf("[internal_source()→ comando interno no implementado]\n");
    return 1;
}

int internal_jobs(char **args) {

    for(int i=1;i<n_pids;i++){
        printf("\n[%d] %d   %c      %s\n",i,jobs_list[i].pid,jobs_list[i].status, jobs_list[i].cmd);
    }
    return 1;
    #if DEBUGN1 
        printf("[internal_jobs()→ Esta función mostrará el PID de los procesos que no estén en foreground]\n");
    #endif
    return 1;
}

/*
Método que implementa el comando interno fg
*/
int internal_fg(char **args) {
    int pos=*args[1]-48;//código ASCII a número (-48)
    //Si pos >= n_pids o pos=0, entonces mostramos error
    if((pos >=n_pids) || (pos==0)){
        fprintf(stderr,"fg %d: no existe ese trabajo\n",pos);
        return -1;
    }
    //Enviar a jobs_list[pos].pid la señal SIGCONT (si su status es ‘D’)
    if(jobs_list[pos].status == 'D'){
        //enviamos la señal y lo mostramos
        kill(jobs_list[pos].pid,SIGCONT);
        fprintf(stderr,"[internal_fg()->Señal 18(SIGCONT) enviada a [%d] %d  (%s)]\n",pos,jobs_list[pos].pid,jobs_list[pos].cmd);
    }
    
    //Copiar los datos de jobs_list[pos] a jobs_list[0]
    //eliminar del cmd el &
    removeChar(jobs_list[pos].cmd,'&');
    fprintf(stderr,"%s",jobs_list[pos].cmd);

    //cambiamos el estado
    jobs_list[pos].status='E';
    //pasamos el proceso a foreground
    jobs_list[0].pid=jobs_list[pos].pid;
    jobs_list[0].status=jobs_list[pos].status;
    strcpy(jobs_list[0].cmd,jobs_list[pos].cmd);
    //eliminamos els proceso de la lista
    jobs_list_remove(pos);
    //fprintf(stderr, "CMD: %s", jobs_list[0].cmd);
    
    //Mientras haya un proceso en ejecución en foreground ejecutar un pause()
    while(jobs_list[0].pid > 0){
        pause();
    }
    
    #if DEBUGN1 
        printf("[internal_fg()→ Esta función enviará un trabajo detenido al foreground reactivando su ejecución, o uno del background al foreground ]\n");
    #endif
    return 1;
}

/*
 Método que implementa el comando interno bg
*/
int internal_bg(char **args) {
    int pos=*args[1]-48;//Conversión de ASCII a Entero
    //Si pos >= n_pids o pos=0, entonces mostramos error
    if((pos >=n_pids) || (pos==0)){
        fprintf(stderr,"bg %d: no existe ese trabajo.\n",pos);
        return 1;//EXIT_FAILURE
    }

    //Si el status de jobs_list[pos] es 'E' entonces error
    if(jobs_list[pos].status == 'E'){
        fprintf(stderr,"bg %d:el trabajo ya está en segundo plano\n",pos);
        return 1;//EXIT_FAILURE
    }
    
    //cambiamos el status
    jobs_list[pos].status = 'E';
    //añadir & a cmd mediante la función strchr
    char *cmdAUX=strchr(jobs_list[pos].cmd,'\0');
    *(cmdAUX)='&';
    *(cmdAUX+1)='\0';
    //mandamos la señal SIGCONT al pid que toca y lo mostramos
    kill(jobs_list[pos].pid,SIGCONT);
    printf("[internal_bg()→ Señal 18(SIGCONT) enviada correctamente al proceso con pid %d]\n", jobs_list[pos].pid);
    printf("[%d]   PID: %d    Status: %c    CMD: %s", pos, jobs_list[pos].pid, jobs_list[pos].status, jobs_list[pos].cmd);

    #if DEBUGN1 
        printf("[internal_bg()→ Esta función reactivará un proceso detenido para que siga ejecutándose pero en segundo plano]\n");
    #endif
    return 1;
}

void imprimir_prompt() {
#if PROMPT_PERSONAL == 1
    printf(NEGRITA ROJO_T "%s" BLANCO_T ":", getenv("USER"));
    printf(AZUL_T "MINISHELL" BLANCO_T "%c " RESET_FORMATO, PROMPT);
#else
    printf("%c ", PROMPT);

#endif
    fflush(stdout);
    return;
}


char *read_line(char *line) {
    
    imprimir_prompt();
    char *ptr=fgets(line, COMMAND_LINE_SIZE, stdin); // leer linea
    if (ptr) {
        // ELiminamos el salto de línea (ASCII 10) sustituyéndolo por el \0
        char *pos = strchr(line, 10);
        if (pos != NULL){
            *pos = '\0';
        } 
	}  else {   //ptr==NULL por error o eof
        printf("\r");
        if (feof(stdin)) { //se ha pulsado Ctrl+D
            fprintf(stderr,"Bye bye\n");
            exit(0);
        }   
    }
    return ptr;
}

int parse_args(char **args, char *line) {
    int i = 0;

    args[i] = strtok(line, " \t\n\r");
    #if DEBUGN1 
        fprintf(stderr, GRIS "[parse_args()→ token %i: %s]\n" RESET_FORMATO, i, args[i]);
    #endif
    while (args[i] && args[i][0] != '#') { // args[i]!= NULL && *args[i]!='#'
        i++;
        args[i] = strtok(NULL, " \t\n\r");
        #if DEBUGN1 
            fprintf(stderr, GRIS "[parse_args()→ token %i: %s]\n" RESET_FORMATO, i, args[i]);
        #endif
    }
    if (args[i]) {
        args[i] = NULL; // por si el último token es el símbolo comentario
        #if DEBUGN1 
            fprintf(stderr, GRIS "[parse_args()→ token %i corregido: %s]\n" RESET_FORMATO, i, args[i]);
        #endif
    }
    return i;
}

/*
Método con el qual ejecutaremos los comandos externos que nos 
vaya introduciendo el usuario
*/
int execute_line(char *line) {
    char *args[ARGS_SIZE];
    pid_t pid;//, status; necesario?
    char command_line[COMMAND_LINE_SIZE];
    int bkg;

    //copiamos la línea de comandos sin '\n' para guardarlo en el array de structs de los procesos
    memset(command_line, '\0', sizeof(command_line)); 
    strcpy(command_line, line); //antes de llamar a parse_args() que modifica line

    if (parse_args(args, line) > 0) {
        if (check_internal(args)) {
            return 1;
        }
        //llamamos a la funcion is_background() para analizar si la linea de comandos hay un &
        bkg = is_background(args); //si el comando tiene un & --> 1     else ---> 0
        pid=fork();//creamos un proceso hijo

        if(pid==0){//hijo
            //Si el hijo recibe la señal SIGCHLD, va a realizar la acción por defecto (finalizar)
            signal(SIGCHLD,SIG_DFL);
            //Si el hijo recibe una señal de SIGINT, va a ignorarla
            signal(SIGINT, SIG_IGN);
            //Si el hijo recibe una señal SIGTSTP, la ignorará
            signal(SIGTSTP, SIG_IGN);

            fprintf(stderr, "[execute_line() --> PID hijo %d (%s)]\n",getpid(),command_line);
            is_output_redirection (args);
            execvp(args[0],args);
            fprintf(stderr, "[execute_line() --> El comando no se ha ejecutado correctamente]\n");
            exit(-1);

        }else if (pid>0) {//padre
            //le recordamos al padre que si se produce ctr+c, se ejecuta ctr+z o un hijo finaliza, ejecutemos el manejador 
            //ctrlc, ctrlz y repaer, respectivamente 
            signal(SIGINT, ctrlc);
            signal(SIGTSTP,ctrlz);
            signal(SIGCHLD,reaper);

            fprintf(stderr, "[execute_line() --> PID Padre %d (%s)]\n",getpid(),mi_shell);
            
            if(!bkg){//Foreground
            //actualizamos el pid con el pid del proceso hijo
            jobs_list[0].pid = pid;
            //actualizamos el status a 'E'
            jobs_list[0].status = 'E';
            //actualizamos el cmd con el proceso hijo
            strcpy(jobs_list[0].cmd, command_line);

            while(jobs_list[0].pid > 0){ //mientras haya un hijo ejecutandose en primer plano (foreground)
                pause();
            }
            }else{//proceso en background
                    jobs_list_add(pid, 'E', command_line); 
            }
            return 1;
            
        }else{
            //ERROR FORK
            perror("ERROR AL REALIZAR EL FORK ");
            return -1;
        }
        
        #if DEBUGN3
            fprintf(stderr, GRIS "[execute_line()→ PID padre: %d]\n" RESET_FORMATO, getpid());
        #endif
    }
    return 0;
}


/*
Método para resetear los valores de Job_list[0]
*/
void initJL(){
    //Ponemos el pid a 0
    jobs_list[0].pid = 0;
    //Ponemos el status a N
    jobs_list[0].status='N';
    //Ponemos todo \0 en el cmd de jobs list
    memset(jobs_list[0].cmd,'\0',sizeof(jobs_list[0].cmd));
    return;
}

/*
Manejador para la señal SIGCHLD (señal enviada a un proceso cuando uno de sus procesos hijos termina)
*/
void reaper(){
    signal(SIGCHLD,reaper);
    pid_t ended;
    int status;

    //Analizamos el valor que devuelve waitpid para saber que hijo termina
    while ((ended=waitpid(-1, &status, WNOHANG)) > 0) {
        if(ended==jobs_list[0].pid) {
        fprintf(stderr, "\n[reaper() --> Proceso hijo %d (%s) enterrado]\n", jobs_list[0].pid, jobs_list[0].cmd);
        jobs_list[0].pid=0;
        jobs_list[0].status='F';
        memset(jobs_list[0].cmd,'\0',sizeof(jobs_list[0].cmd));
      }else{
        int pos=jobs_list_find(ended);
        fprintf(stderr,"[reaper()→ Proceso hijo %d en background(%s) finalizado con exit code 0].\n",ended,jobs_list[pos].cmd);
        fprintf(stderr,"Terminado PID %d(%s) en jobs_list[%d] con status %d\n",ended,jobs_list[pos].cmd,pos,status);
        jobs_list_remove(pos);
      }
    }
    return;
}

/*
Manejador para la señal SIGINT (Ctrl+C)
*/
void ctrlc(){
    signal(SIGINT,ctrlc);

    //mientras haya un hijo ejecutandose en primer plano (foreground) y no es un minishell
    fprintf(stderr, "\n[ctrlc()→ Soy el proceso con PID %d (%s), el proceso en foreground es %d (%s)]\n",getpid(),mi_shell,jobs_list[0].pid,jobs_list[0].cmd);

    if(jobs_list[0].pid > 0){
        if(strcmp(jobs_list[0].cmd,mi_shell)){
            //enviamos la señal SIGTERM al comando hijo que se esté ejecutando en primer plano
            kill(jobs_list[0].pid,SIGTERM);
            fprintf(stderr, "[ctrlc()→ Señal 15(SIGTERM) enviada a %d(%s) por %d(%s)]",jobs_list[0].pid,jobs_list[0].cmd,getpid(),mi_shell);
        }else{
            fprintf(stderr, "[ctrlc()→ Señal SIGTERM NO enviada debido a que el proceso en foreground es el shell]\n");
        }
    }else{
        fprintf(stderr, "[ctrlc()→ Señal SIGTERM no enviada debido a que no hay proceso en foreground]\n");
    }
    printf("\n");
    fflush(stdout);

}

/*
Método main del Mini_shell
*/
void ctrlz(){
    signal(SIGTSTP, ctrlz);

    fprintf(stderr,"\n[ctrlz()->Soy el proceso con PID %d,el proceso en foreground es %d(%s)]",getpid(),jobs_list[0].pid,jobs_list[0].cmd);
    if (jobs_list[0].pid>0){
        if(strcmp(jobs_list[0].cmd,mi_shell)){
            //detener proceso(enviar señal SIGSTOP)
            kill(jobs_list[0].pid, SIGSTOP);
            //Notificamos por pantalla
            fprintf(stderr, "\n[ctrlz()→ Señal 19(SIGSTOP) enviada a %d(%s) por %d (%s)]\n", jobs_list[0].pid, jobs_list[0].cmd, getpid(),mi_shell);
            //actualizamos el estatus de el proceso en foreground
            jobs_list[0].status = 'D';
            //añadimos el proceso en foreground a la lista de procesos
            jobs_list_add(jobs_list[0].pid,jobs_list[0].status,jobs_list[0].cmd);
            //Reseteamos los datos de proceso en foreground
            jobs_list[0].pid = 0;
            jobs_list[0].status = 'N';
            memset(jobs_list[0].cmd,'\0',sizeof(jobs_list[0].cmd));
        }else{
            fprintf(stderr, "\n[ctrlz()→ Señal SIGSTOP no enviada debido a que el proceso en foreground es el shell]");
        }
    }else{
        fprintf(stderr, "\n[ctrlz()→ Señal SIGSTOP no enviada debido a que no hay proceso en foreground]");
    }
    
}

/*
Método para eliminar un caracter que nos pasan por parámetro
*/
void removeChar(char *args,char c){
    int i = 0;
    int j = 0;

    while (args[i]){
        if (args[i] != c){
            args[j] = args[i];
            j++;
        }

        i++;
    }

    args[j] = '\0';

    return;
}

/*
Método que detecta el caracter & de un comando y lo substituye por  /0
*/
int is_background(char **args){
    int i=0;

    while(args[i]!=NULL){

        if(!strcmp(args[i],"&")){
            args[i]=NULL;
            return 1;
        }
        i++;
    }
    return 0;
    
} 

/*
Método para añadir un procesoa la losta de procesos en background
*/
int jobs_list_add(pid_t pid, char status, char *cmd){

    if(n_pids < N_JOBS){
        jobs_list[n_pids].pid = pid;
        jobs_list[n_pids].status = status;
        strcpy(jobs_list[n_pids].cmd,cmd);
        fprintf(stderr,"[%d] %d   %c    %s\n",n_pids, pid,status, cmd);
        n_pids++;
        return 1;
    }else{
        fprintf(stderr,"HAS LLEGADO AL NÚMERO MÁXIMO DE PROCESOS PERMITIDOS.");
        return -1;
    }
    return 0;
}


/*
Método para encontrar el proceso en la posición pos de la lista de procesos
*/
int jobs_list_find(pid_t pid){
    
    for(int i=1;i<N_JOBS;i++){
        if(jobs_list[i].pid==pid){
            return i;//posición del indice encontrado
        }
    }
    return -1;//no encontrado
}

/*
Método para eliminar el proceso de la posición pos de la lista
*/
int jobs_list_remove(int pos){

    //Control d errores
    if (pos>0 && pos<N_JOBS) {
    //reset jobs_list
    jobs_list[pos].pid = 0;
    jobs_list[pos].status = 'N';
    memset(jobs_list[pos].cmd,'\0',sizeof(jobs_list[pos].cmd));
    //decrementamos contador
    n_pids--;
    
    //si aún quedan procesos
    if(n_pids>0){
        jobs_list[pos].pid = jobs_list[n_pids].pid;
        jobs_list[pos].status = jobs_list[n_pids].status;
        strcpy(jobs_list[pos].cmd, jobs_list[n_pids].cmd);
    } 
    return 0;//SUCCESS

    }else{
        fprintf(stderr,"Posición incorrecta!");
        return 1;//FAILURE
    }

}

/*
Función booleana que recorrerá la lista de argumentos buscando un token ‘>’, 
seguido de otro token que será un nombre de fichero.
*/
int is_output_redirection (char **args){  
    int outp = 0;
    int args_path;

    for (int i = 0; (args[i] != NULL && outp == 0); i++){
        if (strcmp(args[i], ">") == 0){
            if (strlen(args[i + 1]) > 1){
                args_path = i + 1;
                outp = 1;
                args[i] = NULL;
            }
        }
    }

    if (outp == 1){
        int fd = open(args[args_path], O_WRONLY | O_CREAT | O_TRUNC, S_IRUSR | S_IWUSR);
        dup2(fd, 1);
        close(fd);
    }

    return outp;
}

/*
Método main del Mini_shell
*/
int main(int argc, char *argv[]) {
    //Indicamos al padre que si llegan estas señales, debemos ejecutar
    //los manejadores de señales que hemos programado (reaper y ctrlc)
    signal(SIGCHLD,reaper);
    signal(SIGINT, ctrlc);
    signal(SIGTSTP, ctrlz);

    char line[COMMAND_LINE_SIZE];
    memset(line, 0, COMMAND_LINE_SIZE);

    //Introducimos dentro de mi_shell el nombre del programa
    strcpy(mi_shell,argv[0]);
    

    //Inicializamos jobs_list[0]
    initJL();

    while (1) {
        if (read_line(line)) { // !=NULL
            execute_line(line);
        }
    }
    return 0;
}