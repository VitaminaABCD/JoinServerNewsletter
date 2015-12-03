package peer;

import communication.Forwarder;
import communication.JSMessage;
import communication.Message;
import communication.OperationMessage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.TreeMap;
import java.util.Map;
import static communication.OperationMessage.getRecordForMessage;
import static peer.Peer.peersAreEqual;

/**
 * Server Handler con ACK
 * @author Ylenia Trapani, Giulia Giuffrida, Manuela Ramona Fede
 */

//Questo thread gestisce solamente Peers o il Join Server.
class ServerHandler implements Runnable
{
    private InetSocketAddress myInetSocketAddress;
    private HashSet<InetSocketAddress> myNeighbours;
    private Socket incomingSocket;
    
    private TimeStamp myTimeStamp;
    private Newsletter news; 
    private State stato;
    private ArrayList<OperationMessage> messageBuffer;
    private Logger logger;
    
    
    public ServerHandler(InetSocketAddress myInetSocketAddress,
                         Socket incomingSocket, 
                         HashSet<InetSocketAddress> myNeighbours, 
                         TimeStamp myTimeStamp,
                         Newsletter news,
                         State stato,
                         ArrayList<OperationMessage> messageBuffer,
                         Logger logger)
    {
        this.myInetSocketAddress = myInetSocketAddress;
        this.myNeighbours = myNeighbours;
        this.incomingSocket = incomingSocket;
        this.myTimeStamp = myTimeStamp;
        this.news = news;
        this.stato = stato;
        this.messageBuffer = messageBuffer;
        this.logger = logger;
    }

    @Override
    public void run()
    {
       
        
        try (ObjectInputStream in = new ObjectInputStream(incomingSocket.getInputStream()))
        {
            Message m = (Message) in.readObject();
            processMessage(m);
        }
        catch (IOException | ClassNotFoundException ex)
        {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void processMessage(Message m)
    {
        if (m instanceof JSMessage)  
            updateNeighbours(((JSMessage) m).getNeighbours());
        else if (m instanceof OperationMessage) 
            processOperationMessage((OperationMessage) m);

        else 
            System.err.println("FORMATO DEL MESSAGGIO NON RICONOSCIUTO PER IL PROCESSAMENTO!");
    }
    
    synchronized private void updateNeighbours(HashSet<InetSocketAddress> group)
    {
        myNeighbours.addAll(group);
        System.out.println("I miei vicini sono: ");
        System.out.println(myNeighbours);
    }

    private void processOperationMessage(OperationMessage m)
    {
        switch (m.getOperationType())
        {
            
            case WRITE:
                receiveOperationMessage(m);
                break;
            default:
                System.err.println("OPERAZIONE INVALIDA!");
        }
    }

    private void receiveOperationMessage(OperationMessage m)
    {
        if (myTimeStamp.isHappenedBefore(m.getSenderTimeStamp()))
        {
            deliverOperationMessage(m);
            
        }
    }


    private void deliverOperationMessage(OperationMessage m)
    {
        TimeStamp tmp = m.getSenderTimeStamp();
        myTimeStamp.updateTl();
        
        switch (m.getOperationType())
        {
            case WRITE:
                
                break;
            default:
                System.err.println("FORMATO DELL'OPERAZIONE NON RICONOSCIUTO!");
        }
    }
}

        
