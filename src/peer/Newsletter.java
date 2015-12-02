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
    private TimeStamp myTimeStamp;

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
    
    synchronized String getNews()
    {
        String dato = null;
        
        for(Map.Entry<InetSocketAddress, String> e : operationsMap.entrySet()) 
            dato += e.getValue();
            
            //dato.equals(e.getValue());
        
        return dato;
    }
    
        synchronized void write(InetSocketAddress id_addr, String dato)
    {
        if (operationsMap.containsKey(id_addr)) //ritorna true se la mappa continene l'indirizzo
        {
            String temp = operationsMap.get(id_addr);
            operationsMap.put(id_addr, temp + dato);
        }
        else
            operationsMap.put(id_addr, dato);        
    }
    
//    synchronized void withdraw(InetSocketAddress id_addr, String dato)
//    {
//        write(id_addr, dato);
//    }
    
}
