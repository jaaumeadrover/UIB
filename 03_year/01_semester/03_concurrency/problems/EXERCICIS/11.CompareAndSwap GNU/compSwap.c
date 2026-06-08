#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>

#define NUM_THREADS      4
#define MAX_COUNT 10000000

int counter = 0;
int mutex=0;

struct tdata {
    int tid;
};

void lock(){
    while(!__sync_bool_compare_and_swap (&mutex,0,1)){
    }
}

void unlock(){
    mutex=0;
}

void *count(void *ptr) {
    long i, max = MAX_COUNT/NUM_THREADS;
    int tid = ((struct tdata *) ptr)->tid;

    for (i=0; i < max; i++) {
        lock();
        counter += 1;
        unlock();
    }
    printf("End %d counter: %d\n", tid, counter);
}

int main (int argc, char *argv[]) {
    pthread_t threads[NUM_THREADS];
    int rc, i;
    struct tdata id[NUM_THREADS];

    for(i=0; i<NUM_THREADS; i++){
        id[i].tid = i;
        rc = pthread_create(&threads[i], NULL, count, (void *) &id[i]);
    }

    for(i=0; i<NUM_THREADS; i++){
        pthread_join(threads[i], NULL);
    }

    printf("Counter value: %d Expected: %d\n", counter, MAX_COUNT);
    return 0;
}