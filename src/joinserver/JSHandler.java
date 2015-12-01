package joinserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
 
import communication.*;


/**
 *
 * @author Ylenia Trapani, Giulia Giuffrida, Manuela Ramona Fede
 */
class JSHandler implements Runnable
{
    private volatile TreeMap<InetSocketAddress, HashSet<InetSocketAddress>> netMap;
    private InetSocketAddress joiningPeerInetSocketAddress;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Socket clientSocket;

    public JSHandler(Socket clientSocket, TreeMap<InetSocketAddress, HashSet<InetSocketAddress>> netMap)
    {
        try
        {
            this.clientSocket = clientSocket;
            this.netMap = netMap;
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
        }
        catch (IOException ex)
        {
            Logger.getLogger(JSHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run()
    {
        try
        {
            joiningPeerInetSocketAddress = (InetSocketAddress) in.readObject();
            
            addPeer();
        }
        catch (IOException | ClassNotFoundException ex)
        {
            Logger.getLogger(JSHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            if(!clientSocket.isClosed())
            {
                try
                {
                    clientSocket.close();
                }
                catch (IOException ex)
                {
                    Logger.getLogger(JSHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
    }
    
    private synchronized void addPeer()
    {         
         if (!netMap.containsKey(joiningPeerInetSocketAddress))
         {
             HashSet<InetSocketAddress> neighbours = new HashSet<>();
             
             neighbours.addAll(netMap.keySet());
             updateOtherNeighbours();
             netMap.put(joiningPeerInetSocketAddress, neighbours);
             
             sendNetworkToAll();
         }
    }

    private synchronized void updateOtherNeighbours()
    {
        for (Map.Entry<InetSocketAddress, HashSet<InetSocketAddress>> entry : netMap.entrySet())
        {
            HashSet<InetSocketAddress> entryNeighbours = entry.getValue();
            entryNeighbours.add(joiningPeerInetSocketAddress);
            entry.setValue(entryNeighbours);
        }
    }

    private synchronized void sendNetworkToAll()
    {
        for(InetSocketAddress peerInetSocketAddress: netMap.keySet())
        {
            sendNetworkToPeer(peerInetSocketAddress);
        }
    }

    private synchronized void sendNetworkToPeer(InetSocketAddress peerInetSocketAddress)
    {
        Socket peerSocket = null;
        Message m = null;
        String body;
        InetSocketAddress sender = new InetSocketAddress(JoinServer.SERVER_PORT);
        
        try 
        {
            if(peerInetSocketAddress.equals(joiningPeerInetSocketAddress))
            { 
                this.out.writeObject(netMap.get(joiningPeerInetSocketAddress));
            }
            else
            {
                peerSocket = new Socket(peerInetSocketAddress.getAddress(), peerInetSocketAddress.getPort());
                ObjectOutputStream out = new ObjectOutputStream(peerSocket.getOutputStream());
                
                HashSet<InetSocketAddress> neighbours = netMap.get(peerInetSocketAddress);
                body = "Vicini aggiornati";
                m = new JSMessage(sender, peerInetSocketAddress, body, neighbours);
                out.writeObject(m);
            }
            
        }
        catch (IOException ex)
        {
            Logger.getLogger(JSHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            if((peerSocket != null) && !peerSocket.isClosed())
            {
                try
                {
                    peerSocket.close();
                }
                catch (IOException ex)
                {
                    Logger.getLogger(JSHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
}
