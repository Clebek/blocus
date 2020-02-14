package blokusPP.player;

import blokusPP.preset.*;
import blokusPP.field.Board;
import blokusPP.poly.*;
import blokusPP.player.BlokusPlayer;

import java.rmi.RemoteException;
import java.rmi.*;
import java.util.Random;
import java.util.HashSet;
import java.util.Iterator;
import gnu.trove.set.hash.THashSet;
/**Klasse zur Dartsellung des Random-Players, der zufaellig Spielzuege generiert
*@author Clara Holzhueter*/
public class RandomPlayer extends BlokusPlayer{

/**Konstruktor, der wie der der Superklasse funktioniert, das heisst der
* speichert die zugewiesenen Farben ab
*@param color zugewiesene Farbe(n)*/
	public RandomPlayer(int[] color){
		super(color);
	}
	
	/**Konstruktor, der wie der der Superklasse funktioniert, das heisst er
	* speichert die zugewiesenen Farben und die Spielbrettgroesse ab.
	@param color zugewiesene Farbe(n)
	@param boardSize zugewiesene Spielbrett-Groesse*/
	public RandomPlayer(int[] color, int boardSize){
		super(color, boardSize);
	}
	
	/**Methode request generiert einen zufaelligen Zug aus der Liste aller
	* gueltigen Zuege und gibt diesen zurueck.
	@return last Move der zufaellig ausgewaehlte Zug wird zurueckgeliefert
	*/
	@Override
	public Move request() throws Exception{
		THashSet<Move> possibleMoves 
		= getBoard().getAllValidMoves(getBoard().getColor());
		if(possibleMoves.size() == 0){
			return (new Move());
		}
				
		
		//hier wird eine Zufallszahl erzeugt. 
		//Diese dient als Index fuer den Zug, der dann zurueckgegeben wird
		Random rand = new Random();
		if(possibleMoves.size() == 0){
		
		}
		int randomIndex = rand.nextInt(possibleMoves.size());
		Iterator<Move> it = possibleMoves.iterator();
		for (int i = 0; i < randomIndex; i++) {
			it.next();
		}
		setLastMove(it.next());
		return getLastMove();
		
	}
}
