package communication;

import java.net.InetSocketAddress;
import peer.Marker;
import peer.Snapshot;

/**
 *
 * @author Ylenia Trapani, Giulia Giuffrida, Manuela Ramona Fede
 */
public class GlobalSnapshotMessage extends Message<Snapshot>
{
    Marker marker;
    
    public GlobalSnapshotMessage(InetSocketAddress sender, 
                                 InetSocketAddress receiver,
                                 Marker marker,
                                 Snapshot body)
    {
        super(sender, receiver, body);
        this.marker = marker;
    }

    public Marker getMarker()
    {
        return marker;
    }
    
}
