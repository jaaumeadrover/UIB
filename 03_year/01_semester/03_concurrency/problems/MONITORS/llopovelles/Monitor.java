/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package llopovelles;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Jaume
 */
public class Monitor {

    private final Lock lock = new ReentrantLock();
    private Condition canEnter = lock.newCondition();
    private Condition canExit = lock.newCondition();
    private int ovellesEsperant;
    private int ovellesRiu;
    private int llopsRiu;

    public Monitor() {
        this.ovellesRiu = 0;
        this.llopsRiu = 0;
        this.ovellesEsperant = 0;
    }

    public void entraRiuOvella(int id) {
        lock.lock();
        System.out.println("HOLA HOSTIA");
        try {
            ovellesEsperant++;
            //si és la primera en arribar i no hi ha ningu al riu
            if (ovellesEsperant < 2) {
                System.out.println("        L'ovella " + id + ": veu que no pot entrar.");
                while (this.ovellesEsperant<2 && this.ovellesRiu==0) {
                    canEnter.await();
                }
            } else {
                System.out.println("        L'ovella " + id + ": veu que pot entrar");
                canEnter.signal();
            }
            this.ovellesEsperant--;
            this.ovellesRiu++;
            Thread.sleep(500);

        } catch (InterruptedException e) {

        } finally {
            lock.unlock();
        }
    }

    public void surtRiuOvella(int id) throws InterruptedException {
        lock.lock();
        try {
            if (this.ovellesRiu == 2 && this.llopsRiu>0) {
                System.out.println("L'ovella " + id + ": veu que no pot partir");
                //no poden sortir fins que no se'n vagin els llops
                while (this.llopsRiu > 0) {
                    this.canExit.await();
                }
            } else{
                System.out.println("L'ovella " + id + ": veu que pot partir");
                this.canExit.signal();
            }
            this.ovellesRiu--;
            System.out.println("L'ovella " + id + "ha partit                LLOPS: "+this.llopsRiu+",OVELLES:"+this.ovellesRiu);
        } catch (Exception e) {

           
        } finally {
            lock.unlock();
        }
    }

    public void surtRiuLlop(int id) {
        this.lock.lock();
        try {
            this.llopsRiu--;
            System.out.println("El llop " + id + " surt del riu.           LLOPS: " + this.llopsRiu + ",OVELLES: " + this.ovellesRiu);
        } finally {
            this.lock.unlock();
        }
    }

    public void entraRiuLlop(int id) {
        this.lock.lock();
        try {
            this.llopsRiu++;
            System.out.println("El llop " + id + " entra del riu.           LLOPS: " + this.llopsRiu + ",OVELLES: " + this.ovellesRiu);
        } finally {
            this.lock.unlock();
        }
    }


}
