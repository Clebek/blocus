package blokusPP.network;

import blokusPP.player.*;
import blokusPP.preset.*;
import blokusPP.field.*;
import blokusPP.output.*;
import blokusPP.paint.*;

import java.rmi.*;
import java.net.*;

/** 
 * Erweiterung des {@link NetPlayer}.
 * Betrachten eines Spielers, der im Netzwerk angeboten wurde 
 * und auf einer anderen Instanz spielt.
 * @author Malin Lachmann
 */
public class ONetPlayer extends NetPlayer{
		
	/**
	 * Ausgabemedium, das das Spiel des angebotenen Spielers auf diesem  
	 * Rechner anzeigt.
	 */
	protected Viewer viewer;
	
	/**
	 * Brett 
	 */
	protected Board board;
	
	/**
	 * Speichert den letzten gespielten Zug.
	 */
	private Move move;
	
	/**
	 * Host, unter dem der Spieler angemeldet ist.
	 */
	protected String host;
	
	/**
	 * Name, unter dem der Spieler angeboten ist.
	 */
	protected String name;
	
	/**
	 * Ausgabe auf Bildschirm.
	 */
	private Output out;	
	/** 
	 * Erzeugt einen ONetPlayer.
	 * @param nplayer angebotener Spieler.
	 * @param nboard Brett des angebotenen Spielers.
	 * @param nhost	 Host, auf dem der Spieler angeboten ist.
	 * @param nname	 Name, unter dem der Spieler angeboten ist.
	 * @param nout  Ausgabeklasse, auf der das Spiel ausgegeben werden soll
	 */
	public ONetPlayer(Player nplayer, Board nboard, String nhost, 
			String nname, Output nout ) throws RemoteException, Exception {
		super(nplayer);
		host = nhost;
		name = nname;
		board = nboard;
		viewer = board.viewer();
		out = nout;
		out.printGame();
	}
	
	/**Erweiterung der request Methode. 
	 * @return Zug, der gespielt werden soll.
	 */
	public Move request() throws Exception, RemoteException{
		move = super.request();
		return move;
	}
	
	/**
	 * Erweiterung der confirm-Methode um die Anzeige auf print.
	 * Wenn das Spiel gewonnen oder verloren ist, wird der Spieler aus der 
	 * rmiregistry abgemeldet.
	 * @param boardStatus Status des Bordes
	 */
	public void confirm(Status boardStatus) throws Exception, RemoteException{
		
		// die confirm an den Spieler weitergeben und Fehler abfangen
		super.confirm(boardStatus);
		
		// Anzuzeigendes Brett aktualisieren. 
		if (board.isValidMove(move,board.getColor()) ){
			board.addMove(move);
		}
		out.printGame();
		
		// Spieler abmelden, falls das Spiel zu Ende ist.
		if (board.getStatus().isGameOver() ){
			try{
				Net.unbind(host,name);
			}catch(RemoteException e){
				System.out.println("Verbindungsproblem, Spieler kann nicht" + 
					" abgemeldet werden. Spieler wird geschlossen");
				System.exit(-1);
			}
		}
	}
	
	/**
	 * Erweiterung der update-Methode um die Anzeige auf print.
	 * Wenn das Spiel gewonnen oder verloren wurde, wird der Spieler aus der 
	 * rmiregistry abgemeldet.
	 * @param opponentMove letzter Zug des Gegners
	 * @param boardStatus Status des Bordes nach dem letzten Zug des Gegners
	 */  
	public void update(Move opponentMove, Status boardStatus) 
			throws Exception, RemoteException{
		// die Updates an den Spieler weitergeben und Fehler abfangen
		super.update(opponentMove, boardStatus);
		
		// Anzuzeigendes Brett aktualisieren. 
		board.addMove(opponentMove);
		out.printGame();
		
		// Spieler abmelden, falls das Spiel zu Ende ist.
		if (board.getStatus().isGameOver() ){
			try{
				Net.unbind(host,name);
			}catch(RemoteException e){
				System.out.println("Verbindungsproblem, Spieler kann nicht" +
					" abgemeldet werden. Spieler wird geschlossen");
				System.exit(-1);
			}
		}
	}
    // private static ------------------------------------------------
    private static final long serialVersionUID = 1L;
}
