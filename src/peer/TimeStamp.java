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
    
public class TimeStamp implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private int ts;
    private int N_PEER;

    public TimeStamp() {
        
        ts=0;
    }

    public TimeStamp(int N_PEER, int ts) {
        
        this.N_PEER=N_PEER;
        this.ts=ts; 
       
    }

    public synchronized int getTs() {
        return ts;
    }
    
    public synchronized void updateTs(){
        ts++;
    } 
    
    public synchronized void updateTs(int value){
        ts= (Math.max(ts, value))+1;
    }
    
}
