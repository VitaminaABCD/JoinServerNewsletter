package peer;

import java.io.Serializable;
import java.net.InetSocketAddress;

/**
 *
 * @author Ylenia Trapani, Giulia Giuffrida, Manuela Ramona Fede
 */
public abstract class Snapshot implements Serializable
{
    protected InetSocketAddress ownerPeer;
    
    public abstract void printSnapshot();
    public abstract void updateChannelsState(String str);

    public Snapshot(InetSocketAddress ownerPeer)
    {
        this.ownerPeer = ownerPeer;
    }

    public InetSocketAddress getOwnerPeer()
    {
        return ownerPeer;
    }
    
    
}
