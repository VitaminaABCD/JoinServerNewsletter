package peer;

import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * @author Ylenia Trapani, Giulia Giuffrida, Manuela Ramona Fede
 */
public class State implements Serializable
{
    private LinkedList<String> history;

    public State()
    {
        this.history = new LinkedList<>();
    }
    
    private State (State s)
    {
        this.history = new LinkedList<>(s.history);
    }
    
    synchronized protected void recordOperation(String record)
    {
        history.add(record);
    }
    
    protected void printInfrastacstrur()
    {
        for(String record: history)
            System.out.println(record);
    }
    
    synchronized public State getCopy()
    {
        return new State(this);
    }
}
