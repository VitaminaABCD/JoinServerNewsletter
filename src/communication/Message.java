package communication;

import java.io.Serializable;
import java.net.InetSocketAddress;

/**
 *
 * @author Ylenia Trapani, Giulia Giuffrida, Manuela Ramona Fede
 */
public abstract class Message<MSG> implements Serializable
{
    private InetSocketAddress sender;
    private InetSocketAddress receiver;
    private MSG body;

    public Message(InetSocketAddress sender, InetSocketAddress receiver, MSG body)
    {
        this.sender = sender;
        this.receiver = receiver;
        this.body = body;
    }

    public InetSocketAddress getSender()
    {
        return sender;
    }

    public void setSender(InetSocketAddress sender)
    {
        this.sender = sender;
    }

    public InetSocketAddress getReceiver()
    {
        return receiver;
    }

    public void setReceiver(InetSocketAddress receiver)
    {
        this.receiver = receiver;
    }

    public MSG getBody()
    {
        return body;
    }

    public void setBody(MSG body)
    {
        this.body = body;
    }
    
    
}
