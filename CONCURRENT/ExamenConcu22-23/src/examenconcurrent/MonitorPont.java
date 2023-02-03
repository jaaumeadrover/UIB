package examenconcurrent;

/**
 * AUTOR: Jaume Adrover Fernández. CLASSE: MonitorPont DATA CREACIÓ: 10/01/2023
 *
 * FUNCIONALITAT: classe MonitorPont que encapsula totes les operacions i dades
 * necessàries per a la correcta sincronització entre processos.
 *
 * SUPOSICIONS: he utilitzat com a plantilla aquest repositori:
 * https://github.com/PlatanosVerdes/Concurrent-Templates .
 *
 */
public class MonitorPont {
    //ATRIBUTS DADES
    private final int NORD = 0;
    private final int SUD = 1;
    private int[] cotxesEsperant;//[0] per als cotxes nord, [1] per als cotxes sud
    private int cotxesDintre;
    private boolean esperaAmbulancia;

    /*
    Constructor del monitor.
     */
    public MonitorPont() {
        cotxesDintre = 0;
        esperaAmbulancia = false;
        cotxesEsperant=new int[2];
        for (int i = 0; i < cotxesEsperant.length; i++) {
            cotxesEsperant[i] = 0;
        }
    }

    /*
    Mètode per a demanar accés des de un vehicle corrent, sense prioritat alguna
    com l'ambulància.
     */
    public synchronized void demanaAccesNord(int id) throws InterruptedException {
            cotxesEsperant[NORD]++;
            System.out.println("El cotxe " + id + " espera a l'entrada " + Direccio.NORD + ", esperen al " + Direccio.NORD + ": " + cotxesEsperant[NORD]);
            //Esperam si: (1)Hi ha cotxes al pont, (2) L'ambulància espera i (3) Hi ha igual o més cotxes al sud
            while (cotxesDintre > 0 || cotxesEsperant[SUD] >= cotxesEsperant[NORD] || esperaAmbulancia) {
                this.wait();
            }
            cotxesEsperant[NORD]--;
            System.out.println("El cotxe " + id + " és al pont, esperan al " + Direccio.NORD + ": " + cotxesEsperant[NORD]);
            cotxesDintre++;
        
    }
    /*
    Mètode per a demanar accés des de un vehicle corrent, sense prioritat alguna
    com l'ambulància.
    */
    public synchronized void demanaAccesSud(int id) throws InterruptedException {
            cotxesEsperant[SUD]++;
            System.out.println("        El cotxe "+id+" espera a l'entrada"+ Direccio.SUD+", esperen al SUD: " + cotxesEsperant[SUD]);
            //Esperam si: (1)Hi ha cotxes al pont, (2) L'ambulància espera i (3) Hi ha més cotxes al nord
            while (cotxesDintre > 0 || cotxesEsperant[NORD] > cotxesEsperant[SUD] || esperaAmbulancia) {
                this.wait();
            }
            cotxesEsperant[SUD]--;
            System.out.println("        El cotxe " + id + " és al pont, esperen al SUD: " + cotxesEsperant[SUD]);
            cotxesDintre++;
        
    }
    

    /*
    Mètode per a demanar acces des de la ambulància, aquesta només pot esperar
    si hi ha cotxes dintre el pont ja que té preferència davant la resta.
     */
    public synchronized void demanaAccesAmbulancia(int id) throws InterruptedException {
        esperaAmbulancia=true;
        while (cotxesDintre > 0) {
            System.out.println("++++Ambulància " + id + " espera per entrar");
            this.wait();
        }
        this.cotxesDintre++;
        System.out.println("AMBULANCIA "+id+" és al pont");
    }

    /*
    Mètode per a que un cotxe surti, decrementant el contador de vehicles que hi ha dins el pont.
    Farem un notifyAll per a despertar tots els processos, tot i que només surtiran del bucle d'espera els que complesquin 
    les condicions necessàries.
    */
    public synchronized void surtVehicle(int id) {
        cotxesDintre--;
        System.out.println("---->El vehicle " + id + " surt del pont");
        if(id==1010){
            this.esperaAmbulancia=false;//si surt la ambulància,avisam que deixa de esperar
        }
        this.notifyAll();
    }
}
