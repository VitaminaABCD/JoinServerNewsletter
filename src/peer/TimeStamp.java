/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peer;

import java.io.Serializable;

/**
 *
 * @author Ylenia Trapani, Giulia Giuffrida, Manuela Ramona Fede
 */

public class TimeStamp implements Serializable, Comparable {

    private static final long serialVersionUID = 1L;

    private int tl;
    private int N_PEER; //PID


    public TimeStamp(int N_PEER) {

        this.N_PEER = N_PEER;//PID
        this.tl = 0;

    }

    public synchronized int getTl() {
        return tl;
    }
    
        public synchronized int getPID() {
        return N_PEER;
    }

    public synchronized void updateTl() {
        tl++;
    }
    
    public synchronized String printTs(){
    
    return("TimeStamp: " +getTl()+
                            "\nPID: "+getPID());
    
    }
   

//    public synchronized void updateTs(int value){
//        ts= (Math.max(ts, value))+1;
//    }
    
    @Override
    public int compareTo(Object o) {
//non torna mai zero perchè abbiamo deciso di fare un confronto usando anche il pid
        
        TimeStamp ts = (TimeStamp) o;
        int diff = this.tl - ts.tl;
        if (diff == 0) {
            return this.N_PEER - ts.N_PEER;
        }
        return diff;

    }

    synchronized boolean isHappenedBefore(TimeStamp ts) 
    {
        return false;

    }
    
    
    
}
