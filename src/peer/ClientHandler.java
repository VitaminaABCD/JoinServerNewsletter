package peer;

import communication.Forwarder;
import communication.GlobalSnapshotMessage;
import communication.Message;
import communication.OperationMessage;
import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ylenia Trapani, Giulia Giuffrida, Manuela Ramona Fede
 */
public class ClientHandler implements Runnable
{
    private HashSet<InetSocketAddress> myNeighbours;
    private TimeStamp myTimeStamp;
    private Newsletter news; 
    private InetSocketAddress myInetSocketAddress;
    private State stato;
    private int globalSnapshotCounter;
    final private TreeMap<Marker, Recorder> markerMap;
    private Logger logger;

    public ClientHandler(InetSocketAddress myInetSocketAddress, 
                         HashSet<InetSocketAddress> myNeighbours, 
                         TimeStamp myTimeStamp, 
                         Newsletter news,
                         State stato,
                         TreeMap<Marker, Recorder> markMap,
                         Logger logger)
    {
        this.myInetSocketAddress = myInetSocketAddress;
        this.myNeighbours = myNeighbours;
        this.myTimeStamp = myTimeStamp;
        this.news = news;
        this.stato = stato;
        this.globalSnapshotCounter = 0;
        this.markerMap = markMap;
        this.logger = logger;
    }
    
    @Override
    public void run()
    {
        Scanner scanner = new Scanner(System.in);
        int choice;
        
        while (true)
        {
            printMenu();
            choice = scanner.nextInt();
            switch(choice)
            {
                case 1:
                   //readNews();
                    break;
                case 2:
                    //writeDato();
                    break;
                case 3:
                    printNews();
                    break;
                case 4:
                    takeGlobalSnapshot();
                    break;
                case 5:
                    printLog();
                    break;
                case 6:
                    printNeighbours();
                    break;
                default:
                    System.out.println("Comando non contemplato!");
            }
        }
    }
    
    public void printMenu()
    {
        System.out.println("--------------MENU--------------");
        System.out.println("\n\t1) ");
        System.out.println("\t2) Scrivi dato");
        System.out.println("\t3) Stampa le notizie");
        System.out.println("\t4) Global Snapshot");
        System.out.println("\t5) Stampa Log");
        System.out.println("\t6) Stampa Peer Vicini\n");
        System.out.println("--------------------------------");
        System.out.println("\nInserisci il comando:");
    }

    
    /*Si deve sincronizzare sull'Hashset dei vicini
    private void causalOrderMulticast(OperationMessage.OperationType operationType,
                                      double amount)
    {
        
        myVectorClock.updateVectorClock();
        for(InetSocketAddress receiver: myNeighbours)
        {
            Message m = new OperationMessage(myInetSocketAddress, 
                                             receiver, 
                                             myVectorClock, 
                                             operationType, 
                                             amount);
            Forwarder.sendMessage(m);
        }
        
    }
    
    private void deposit()
   
        double amount = getAmount();
        causalOrderMulticast(OperationMessage.OperationType.DEPOSIT, amount);
        conto.deposit(myInetSocketAddress, amount);
        String record = "[PEER: " + myInetSocketAddress +
                         " deposita " + amount + "]";
        //Logger.getLogger(Peer.class.getName()).log(Level.INFO, record);
        logger.log(Level.INFO, record);
    }
    
    private void withdraw()
    {
        //Possiamo avere saldo negativo.
        //Non c'è controllo sul quantitativo che possiamo prelevare;
        double amount = getAmount();
        causalOrderMulticast(OperationMessage.OperationType.WITHDRAW, amount);
        conto.withdraw(myInetSocketAddress, amount);
        String record = "[PEER: " + myInetSocketAddress +
                         " preleva " + amount + "]";
        Logger.getLogger(Peer.class.getName()).log(Level.INFO, record);
    }
    
*/
        private String getDato()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("> Dato: ");
        return scanner.next();
    }

    private void printNews()
    {
        System.out.println("> ECCO I DATI : " + news.getNews());
    }

    private void takeGlobalSnapshot()
    {
        State frozenState = stato.getCopy();
        LocalSnapshot mySnapshot = new LocalSnapshot(myInetSocketAddress, frozenState);
        GlobalSnapshot globalSnapshot = new GlobalSnapshot(myInetSocketAddress);
        globalSnapshot.addSnapshot(mySnapshot);
        
        Recorder recorder = new Recorder(globalSnapshot);
        Marker marker = new Marker(myInetSocketAddress, globalSnapshotCounter++);
        
        synchronized(markerMap)
        {
            markerMap.put(marker, recorder);
        }
        
        floodMarker(marker);
        
    }

    private void printLog()
    {
        stato.printInfrastacstrur();
    }
    
    private void printNeighbours()
    { 
        System.out.println("{");
        for (InetSocketAddress isa : myNeighbours)
            System.out.println("Indirizzo: " + isa.getAddress() + " - Porta: " + isa.getPort());
        System.out.println("}");
    }
    
    private void floodMarker(Marker marker)
    {
        for (InetSocketAddress isa : myNeighbours)
        {
            GlobalSnapshotMessage gsm = new GlobalSnapshotMessage(myInetSocketAddress, 
                                                                  isa, 
                                                                  marker, 
                                                                  null);
            Forwarder.sendMessage(gsm);
        }
    }
}
