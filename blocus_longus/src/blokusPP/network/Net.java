package blokusPP.network;

import java.rmi.*;
import java.net.*;
import java.rmi.registry.*;
import blokusPP.preset.*;

/**
 * Klasse zum Anbieten und Finden von Onlinespielern ueber die RMI
 * @author Malin Lachmann
 */
public class Net{

    /** 
     * Methode zum Anbieten eines Onlinespielers.
     * @param player Spieler, der angeboten werden soll
     * @param host rmi-host
     * @param name Name, unter dem der Spieler angeboten werden soll.
     */
    public void offer(Player player, String host, String name ) 
    		throws MalformedURLException, RemoteException  {
    	try {
		Naming.rebind ( "rmi://" + host + "/" + name , player );
    		System.out.println ( " Player ( " + name + " ) bereit " );
		} catch (MalformedURLException ex) {
		    System.out.println("Es sind Probleme mit der URL aufgetreten, " + 
		                  "das Spiel wird beendet.");
			System.exit(-1);
		} catch (RemoteException ex) {
			System.out.println("Die Kommunikation ist fehlgeschlagen, " + 
	                        "das Spiel wird beendet.");
			System.exit(-1);
		}
    }

    /** 
     * Methode zum Finden eines Onlinespielers.
     * @param host Host, auf dem der Spieler registriert ist.
     * @param name Name des Spielers, auf den zugegriffen werden soll.
     * @return Referenz auf den Spieler, der gefunden wurde.
     */
    public Player find(String host, String name)
    		throws MalformedURLException, RemoteException, NotBoundException {
        Player player = null ;
        try {
			player = (Player) Naming . lookup ( "rmi://" + host + "/" + name );
        	System.out.println ( " Spieler ( " + name + " ) gefunden " ); 
		} catch (Exception ex) {
			System.out.println("Der Spieler wurde nicht gefunden, das Spiel " + 
			                  "wird beendet.");
			System.exit(-1);
		}
        return player ;
    }
        
    /** Methode zum Abmelden eines Onlinespielers.
     * @param host Host, auf dem der Spieler registriert ist.
     * @param name Name des Spielers.
     */   
        
    public static void unbind( String host , String name ) 
    		throws MalformedURLException, RemoteException, NotBoundException{
    	Naming.unbind( "rmi://" + host + "/" + name );
    	System.out.println ( " Spieler ( " + name + " ) abgemeldet " );
    }
}

