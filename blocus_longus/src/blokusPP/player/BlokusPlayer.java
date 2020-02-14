package blokusPP.player;

import blokusPP.preset.*;
import blokusPP.field.Board;
import blokusPP.poly.*;
import java.rmi.RemoteException;
import java.rmi.*;


/**Klasse zur Darstellung des Spielers. Dieser hat sein eigenes Spielbrett 
*auf dem er seine Zuege und die des Gegners setzt.Hier sind update, 
*confirm und reset implementiert
@author Clara Holzhueter*/
public abstract class BlokusPlayer implements Player{

	/**internes Brett des Spielers*/
	private Board board;
	/**zugewiesene Farben des Spieler*/
	private int[] colors;
	/**Variable zum abspeichern des letzten eigenen Zuges*/
	private Move lastMove = null;
	
	/** Farben des Gegners.*/
	private int[] oppColors;
	
final static int GAME_COLORS = 4;
	


	/**Konstruktor, der die Objektvariablen intialisiert
	*@param colors Int-Array mit den Zahlen die zu zugewiesenen Farben enthalten
	*/
	public BlokusPlayer(int[] colors){
		board = new Board();
		this.colors = new int[colors.length];
		for ( int i = 0; i < colors.length; i++){
			this.colors[i] = colors[i];
		}
		calculateOpponentColors();

		//Farben werden eingetragen
		initColors(colors);

	}
	
	
	/**Konstruktor, der die Objektvariablen intialisiert
	*@param colors Int-Array mit den Zahlen die zu zugewiesneen Farben enthalten. 
	@param boardSize Groesse des Spielfeldes
	*/
	public BlokusPlayer(int[] colors, int boardSize){
		board = new Board(boardSize);
		this.colors = new int[colors.length];
		//Farben werden eingetragen
		initColors(colors);

	}
	/**initialisiert das Array colors mit den zugewiesenen Farben
	*@param colors Array mit int-Werten der Zahlen*/
	private void initColors(int[] colors){
		for(int i = 0; i< colors.length; i++){
			this.colors[i] = colors[i];
		}
	
	}
	//* Methode, einen Move erzeugt und zurueckliefert */
	public abstract Move request() throws Exception;
	
	/**Methode die den mit request geforderten und in lastMove abgespeicherten letzten Zug 
	*im Spielbrett des Spielers setzt und prueft ob der Status des eigenen Bretts 
	*und der des Spielbretts uebereinstimmen.
	*@param boardStatus Status des Boards, wird hier zum Vergleich der Spielbretter uebergeben
	*/
    public void confirm(Status boardStatus) throws Exception, RemoteException{
    		board.addMove(lastMove);
    		//falls Statui verschieden sind, wird eine Meldung ausgegeben.
    	if( (board.getStatus().equals(boardStatus)) == false){
    		throw new Exception
    		("Internes Spielerboard und echtes Spielbrett stimmen nicht mehr ueberein, deshalb musste das Spiel leider beendet werden. Bitte starten sie das Spiel erneut");
    	}
    	}
    	

    
/**Methode update setzt den gegnerischen Zug auf sein eingenes Spielbrett 
*und prueft ob das eigene Brett und das Haupt-Brett den gleichen Status haben.
*@param opponentMove gegnerisher Zug der auf Spielfeld gesetzt werden soll
* @param boardStatus status der HauptSpielbretts, 
*dieht zm Vergleich und zur Kontrolle ob die Bretter identisch sind*/
   public void update(Move opponentMove, Status boardStatus) throws Exception{
   	board.addMove(opponentMove);
 
   	//falls Stati verschieden wird eine Exception geworfen
   	if ((board.getStatus().equals(boardStatus)) == false){
   		throw new Exception
   		("Internes Spielerboard und echtes Spielbrett stimmen nicht mehr ueberein, deshalb musste das Spiel leider beendet werden. Bitte starten sie das Spiel erneut");
   	}
   	
   
    }
/** Methode zum Zuruecksetzten eines Spielers. Danach kann ein neues Spiel begonnen werden
*@param colors Aray mit den zugeweiesen Farben
*/
    public void reset(int[] colors) throws Exception, RemoteException{
    	board = new Board();
    	lastMove = null;
    	this.colors = new int[colors.length];
    	initColors(colors);
     
    }
    /**Getter fuer die Farben der Spieleres
    @return colors die Farben mit denen der Spieler spielt*/
    public int[] getColors(){
    	return colors;
    }
    
    /**Methode zum Setzen von lastMoove*/
	public void setLastMove(Move mov){
		lastMove = mov;
	}
	/**Methode zum Setzen von board*/
	public Board getBoard() {
		return board;
	}
	
	
	/**	
	 *	Methode, die den Wert an der Stelle i von opponentColors[] zurueck gibt
	 *	@param i Index
	 *	@return Wert von opponentColors[] an der Stelle i
	 */
	public int getOpponentColor(int i) throws IllegalArgumentException{
		if(i > oppColors.length){
			throw new IllegalArgumentException
			("Zugriff auf einen Index, der nicht in dem Array der gegnerischen Farben existiert!");
		}
		return oppColors[i];
	}	

	/**
	 *	Methode, die die Anzahl der gegnerischen Farben zurueck gibt
	 *	@return Anzahl der Elemente in opponentColors[]
	 */	
	public int getOpponentColorsSize(){
		return oppColors.length;
	}

	/**
	 *	Methode, die die Farbe an der Stelle i 
	 *eigener Farben als int-Wert zurueck gibt
	 *	@return	int-Wert aus colors[] an der Stelle i
	 */
	public int getColor(int i) throws IllegalArgumentException{
		if(i > colors.length || i < 0){
			throw new IllegalArgumentException("Zugriff ausserhalb des Arrays colors[].");
		}
		return colors[i];
	}
	/**
	 *	Methode getColorsSize(), die die Anzahl der Elemente in colors[] zurueckliefert
	 *	@return Anzahl der Elemente in colors
	 */
	public int getColorsSize(){
		return colors.length;
	}
	
	/**Methode die lastMove zurueckliefert
	@return lastMove in lastMove abgeseichertet Zug*/
	public Move getLastMove(){
		return lastMove;
	}
	
	
		/** 
	 *	Methode calculateOpponentColors ermittelt die
	 * gegnerischen Farben und speichert sie im Int-Array 
	 *	getOpponentColors() ab
	 */	 	
	private void calculateOpponentColors(){
		//Neuer int-Array der Laenge der Gesamtfarbenanzahl im Spiel minus der Anzahl der eigenen Farben
		oppColors = new int[(GAME_COLORS-colors.length)];
		//Boolean-Variablen fuer alle 4 Farben
		boolean blue = true;
		boolean green = true;
		boolean red = true;
		boolean yellow = true;
		//Die eigenen Farben durchgehen
		//fuer jede vorkommende Farbe die entsprechende boolean-Variable auf false setzen
		for(int i = 0; i < colors.length; i++){
			switch(colors[i]){
				case 0:		blue = false;
							break;
				case 1:		yellow = false;
							break;
				case 2:		red = false;
							break;
				case 3:		green = false;
							break;
			}
		}
		//opponentColors setzen: fuer jede Variable, 
		//die noch true ist, wird opponentColors gesetzt
		for(int i = 0; i < oppColors.length; i++){
			if(red){
				oppColors[i] = 2;
				red = false;
			}
			else if(blue){
				oppColors[i] = 0;
				blue = false;
			}
			else if(green){
				oppColors[i] = 3;
				green = false;
			}
			else if(yellow){
				oppColors[i] = 1;
				yellow = false;
			}
		}
	}
}
