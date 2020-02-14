package blokusPP.network;

import blokusPP.player.*;
import blokusPP.preset.*;
import blokusPP.field.*;

import java.rmi.*;
import java.rmi.server.*;
import java.net.MalformedURLException;

/**
* Implementiert die Spieler-Schnittstelle {@link Player}, leitet die Anfragen  
* an den Onlinespieler weiter und faengt RemoteExeptions ab. Falls die 
* Verbindung abbricht, gibt der Spieler auf.
* @author Malin Lachmann
*/
public class NetPlayer extends UnicastRemoteObject implements Player{

	/** 
	* Spieler, der im Netz gefunden wurde 
	*/
	public Player player;
	
	/**
	* Zeigt an, ob die Verbindung verloren gegangen ist.
	*/
	private boolean surrenderNext = false;

    /** Spieler-Konstruktor
    */
	public NetPlayer(Player player) throws RemoteException{
		this.player = player;
	}

	/**
	* Sucht einen Spieler unter dem Namen auf dem Host.
	* @param host Host, auf dem der Spieler gesucht werden soll.
	* @param name Name, unter dem der Spieler registriert ist.
	*/
	public NetPlayer(String host, String name) 
			throws MalformedURLException, RemoteException, NotBoundException{
	
		Net net = new Net();		
		player = net.find(host,name);
		}

	/**
	* Gibt die Zuganfrage an den Onlinespieler weiter. Gibt {@code null} 
	* zurueck, wenn die Verbindung nicht gehalten werden kann.
	* @return Zug, der zurueckgegeben wird.
	*/
	public Move request() throws Exception, RemoteException {
		
		Move move = new Move();
		
		// falls surrenderNext wahr ist, wird aufgegeben
		if (surrenderNext){
			return move;
		}
		
		try{move = player.request();
		}catch ( RemoteException e){
			System.out.println("Die Verbindung ist verloren gegangen.");
		}
		
		return move;
	}


	/**
	* Versucht, den gegnerischen Zug auf das eigene Spielbrett zu setzen. 
	* Ueberprueft, ob eigenes Brett und Hauptbrett den gleichen Status haben.
	* Setzt {@code surrenderNext}, falls die Verbindung nicht mehr 
	* funktioniert. 
	* @param boardStatus Aktueller Status des Hauptbrettes zum Vergleich.
	* @param opponentMove Zug des Gegners.
	*/ 
    public void update(Move opponentMove, Status boardStatus) 
			throws Exception, RemoteException{
		try{
			player.update(opponentMove, boardStatus);
		}catch (RemoteException e){
			System.out.println("Die Verbindung ist verloren gegangen.");
			surrenderNext = true;
		}
	}

	/**
	* Setzt den mit {@code request} angeforderten Zug auf das Brett 
	* des Spielers. Ueberprueft, ob der Status vom eigenen Brett und dem 
	* Hauptbrett uebereinstimmen. Setzt {@code surrenderNext}, falls die 
	* Verbindung nicht mehr funktioniert. 
	* @param boardStatus Aktueller Status des Brettes zum Vergleich.
	*/ 
    public void confirm(Status boardStatus) throws Exception, RemoteException{
    	try{
			player.confirm(boardStatus);
    	}catch (RemoteException e){
    		System.out.println("Die Verbindung ist verloren gegangen.");
			surrenderNext = true;
		}
	}
	/**
	* Versucht, den Onlinespieler zurueckzusetzen. Danach kann ein neues Spiel 
	* begonnen werden. Setzt {@code surrenderNext}, falls die Verbindung 
	* nicht mehr funktioniert. 
	* @param colors Die dem Spieler zugewiesenen Farben.
	*/
    public void reset(int[] colors) throws Exception, RemoteException{
    	try{
			player.reset(colors);
    	}catch (RemoteException e){
    		System.out.println("Die Verbindung ist verloren gegangen.");
			e.printStackTrace ();
			surrenderNext = true;
		}
	}
    // private static ------------------------------------------------
    private static final long serialVersionUID = 1L;

}
