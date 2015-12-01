package communication;

import java.net.InetSocketAddress;
import java.util.HashSet;

/**
 *
 * @author Ylenia Trapani, Giulia Giuffrida, Manuela Ramona Fede
 */
public class JSMessage extends Message<String>
{
    private HashSet<InetSocketAddress> neighbours;

    public JSMessage(InetSocketAddress sender, 
                    InetSocketAddress receiver,
                    String body, 
                    HashSet<InetSocketAddress> neighbours)
    {
        super(sender, receiver, body);
        this.neighbours = neighbours;
    }
    
    //Getter and Setters

    public HashSet<InetSocketAddress> getNeighbours()
    {
        return neighbours;
    }

    public void setNeighbours(HashSet<InetSocketAddress> neighbours)
    {
        this.neighbours = neighbours;
    }
    
    
}
