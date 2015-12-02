package communication;

import java.net.InetSocketAddress;
import peer.TimeStamp;

/**
 *
 * @author Ylenia Trapani, Giulia Giuffrida, Manuela Ramona Fede
 */

public class OperationMessage extends Message<String>
{   
    private TimeStamp senderTimeStamp;
    private OperationType operationType;
    
    public OperationMessage(InetSocketAddress sender, 
                            InetSocketAddress receiver, 
                            TimeStamp senderTimeStamp,
                            OperationType operationType,
                            String body)
    {
        super(sender, receiver, body);
        this.senderTimeStamp = senderTimeStamp;
        this.operationType = operationType;
    }
    
    static public String getRecordForMessage(OperationMessage message)
    {
        OperationMessage.OperationType type = message.getOperationType();
        String record = null;
        
       switch (type)
        {
            //case READ:

            case WRITE:
                record = "[PEER: " + message.getSender() +
                         " Scrivi " + message.getBody() + "]";
                break;
            default:
                record = "FORMATO MESSAGGIO NON RICONOSCIUTO";
        }
        
        return record;
    }

    public TimeStamp getSenderTimeStamp()
    {
        return senderTimeStamp;
    } 
    
    public enum OperationType
   {
      //READ,
      WRITE;
   }

    public OperationType getOperationType()
    {
       return operationType;
    }
    
    
}


