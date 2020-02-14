package blokusPP.player;

import blokusPP.preset.*;
import blokusPP.field.Board;
import blokusPP.poly.*;
import blokusPP.input.GetMoveConsole;
import blokusPP.player.BlokusPlayer;

import java.rmi.RemoteException;
import java.rmi.*;
import java.util.HashSet;
import gnu.trove.set.hash.THashSet;
/**Klasse zur Darstellung des Interaktiven Spielers. 
*Dieser hat sein eigenes Spielbrett, auf dem er seine Zuege
* und die des Gegners setzt.
*Er fordert ueber ein Eingabeobjekt einen Zug vom Nutzer an.
@author Clara Holzhueter*/ 
public class InteractivePlayer extends BlokusPlayer{

/**Eingabeobjekt zum Anfordern von Zuegen.Alle Spieler nutzen dasselbe Eingabeobjekt */
	private  Requestable in;

	/**Konstruktor. Funktionert, wie der der Superklasse,
	*das heisst er speichert die zugewiesene(n) Farbe(n) ab.
	*Zusaetzlich wird auch noch das Eingabeovjekt initialisiert.
	@param color zugewiesene Farbe
	@param in Eingabeobjekt*/
	public InteractivePlayer(int[] color, Requestable in){
		super(color);
		this.in =in;
	}
	
	/**Konstruktor. Funktionert, wie der der Superklasse, 
	*das heisst es werden die Farbe(n) und die Spielbrett-Groesse abgespeichert.
	*Zusaetzlich wird auch noch das Eingabeovjekt initialisiert.
	@param color zugewiesene Farbe
	@param boardSize Spielbrettgroesse*/
	public InteractivePlayer(int[] color,int boardSize, Requestable in){
		super(color, boardSize);
		this.in =in;		
	}

	/**Methode fordet vom Nutzer einen Move ueber das Eingabeobjekt an 
	*und gibt diesen zurueck.
	*@return lastMove der angforderte Zug*/
	@Override
	public Move request() throws Exception, RemoteException{
		boolean isValid = false;
		while( isValid == false){
			setLastMove (in.deliver());
			THashSet<Move> moves 
			= getBoard().getAllValidMoves(getBoard().getColor());
			if(moves.contains(getLastMove()) || getLastMove() == null){
				isValid = true;
			}
		}
		return getLastMove();
	}
	
	


}
