package peer;

import java.net.InetSocketAddress;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Ylenia Trapani, Giulia Giuffrida, Manuela Ramona Fede
 */
public class Newsletter
{
    private TreeMap<InetSocketAddress, String> operationsMap;

    public Newsletter()
    {
        operationsMap = new TreeMap<>(new Comparator<InetSocketAddress>()
        {
            @Override
            public int compare(InetSocketAddress o1, InetSocketAddress o2)
            {                
                if(!o1.getHostString().equalsIgnoreCase(o2.getHostString()))
                {
                    return o1.getHostString().compareToIgnoreCase(o2.getHostString());
                }
                else
                {
                    return Integer.compare(o1.getPort(), o2.getPort());
                }
            }
        });
    }
    
    /*synchronized void deposit(InetSocketAddress id_addr, double amount)
    {
        if (operationsMap.containsKey(id_addr))
        {
            double temp = operationsMap.get(id_addr);
            operationsMap.put(id_addr, temp + amount);
        }
        else
            operationsMap.put(id_addr, amount);        
    }
    
    synchronized void withdraw(InetSocketAddress id_addr, double amount)
    {
        deposit(id_addr, -amount);
    }
    */
    synchronized String getNews()
    {
        String dato = null;
        
        for(Map.Entry<InetSocketAddress, String> e : operationsMap.entrySet())
            dato += e.getValue();
        
        return dato;
    }

    
}
