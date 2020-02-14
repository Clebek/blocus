package blokusPP.player;

import blokusPP.preset.*;
import blokusPP.field.*;
import blokusPP.player.*;
import blokusPP.player.KI.*;

import java.util.*;
import java.rmi.*;
import java.io.*;

/**
 * 	Klasse IntelligentPlayer, die Methoden zur Evaluation der Zuege hat (Score-Berechnung).
 *	Die Rechenzeit fuer einen Zug ist tlw sehr hoch, was damit zu tun hat, dass das Board nicht
 *	komplett optimiert ist. Die Methode, die alle gueltigen Zuege zurueckliefert, koennte noch
 *	weiter verbessert werden, damit liesse sich die Rechenzeit pro Zug minimieren.
 *	Grundprinzip: MoveTree wird mit bis Tiefe von 3 (inklusive Root-Node) mit allen gueltigen
 *	Zuegen gefuellt. Der beste Zug nach den Kriterien Anzahl der eigenen Zuege, Anzahl der 	
 *	blockierten Zuege und Groesse des Polyominos ausgewaehlt (die Gewichtung wird ueber Faktoren
 *	gesteuert). Dafuer wird ein Score aus den 3 Kriterien gebildet, aus dem Baum in Tiefe 2 (mit
 *	Root Node) werden die Zuege mit bestem Score gespeichert und dessen Kinder eigefuegt.
 *	Nun werden diesen besten Kindern wieder alle moeglichen Zuege als Kinder hinzugefuegt und
 *	deren Scores berechnet.
 *	Am Ende wird der Zug ausgewaehlt, der (eigenen Score) - (gegnerischen Score) maximiert und 
 *	auf das Board gesetzt
 *	@author Hanna Holderied
 */	
public class IntelligentPlayer extends BlokusPlayer{
	/** Groesse des gelegten Polyominos */
	private float factorSize;
	/** Anzahl der geblocken Zuege des Gegners */
	private float factorBlock;
	/** Anzahl der eigenen Zuege */
	private float factorTurns;
	/** MoveTree tree als Attribut eines IntelligentPlayers */
	private MoveTree tree;
	/** String zum Speichern des Namens der Datei mit den zur Schwierigkeit passenden Faktoren */
	private String nameOfFactorFile;
	/** Rundenzaehler */
	private int counter = 0;


	/**
	 *	Getter fuer factorBlock
	 *	@return factorBlock Faktor fuer die Anzahl der blockierten Zuege
	 */
	public float getFactorBlock(){
		return factorBlock;
	}

	/**
	 *	Getter fuer factorSize
	 *	@return factorSize Faktor fuer die Groesse des Polyominos
	 */
	public float getFactorSize(){
		return factorSize;
	}
	/**
	 *	Getter fuer factorTurns
	 *	@return factorTurns Faktor fuer die Anzahl der eigenen Zuege
	 */
	public float getFactorTurns(){
		return factorTurns;
	}

	/**
	 *	Methode request() liefert ausgehend vom aktuellen Spielstand den besten zu 
	 *	ermittelnden Zug zurueck
	 *	@return der Zug mit dem besten Scoring, der ermittelt werden konnte
	 *	@exception IOException Exception vom Einlesen der Faktoren
	 */	
	public Move request() throws IOException{
		//Rundenzaehler inkrementieren, wenn color wieder die erste eigene Farbe ist
		Move mov;
		if(getColor(0) == getBoard().getColor()){
			counter++;
		}
		//Die passenden Faktoren aus der entsprechenden Datei einlesen
		readFactorFile(nameOfFactorFile);
		//Die ersten 2 Zuege direkt einlesen
		if(counter < 3){
			mov = firstMoves(counter);
			setLastMove(mov);
		}
		else{
			//Den Baum aufbauen und die Kinderknoten an den Root hinzufuegen
			tree.getRoot().add(getBoard().getAllValidMoves(getBoard().getColor()));
			Node temp = tree.selectBestLeaf();
			mov = temp.getTurn();
			setLastMove(mov);
			tree.updateRootNode(mov);
		}
		return mov;
	}



	/**
	 *	Methode firstMoves(int) enthaelt die ersten Zuege hart codiert, 
	 *	damit etwas Rechenzeit eingespart wird
	 *	@param counter Anzahl der Runde, nach der der Zug ausgewaehlt wird
	 *	@return Zug, der gespielt werden soll
	 */
	private Move firstMoves(int counter){
		Move mov = new Move();
		if(getBoard().getColor() == 0){
			switch(counter){
				case 1: mov.addPosition(new Position(0,0));
						mov.addPosition(new Position(0,1));
						mov.addPosition(new Position(0,2));
						mov.addPosition(new Position(0,3));
						mov.addPosition(new Position(1,2));
						break;
				case 2: mov.addPosition(new Position(2,3));
						mov.addPosition(new Position(2,4));
						mov.addPosition(new Position(3,4));
						mov.addPosition(new Position(3,5));
						mov.addPosition(new Position(4,5));
						break;
			}
		}
		else if(getBoard().getColor() == 1){
			switch(counter){
				case 1: mov.addPosition(new Position(16,0));
						mov.addPosition(new Position(17,0));
						mov.addPosition(new Position(18,0));
						mov.addPosition(new Position(19,0));
						mov.addPosition(new Position(17,1));
						break;
				case 2: mov.addPosition(new Position(16,2));
						mov.addPosition(new Position(15,2));
						mov.addPosition(new Position(15,3));
						mov.addPosition(new Position(14,3));
						mov.addPosition(new Position(14,4));
						break;
			}

		}
		else if(getBoard().getColor() == 2){
			switch(counter){
				case 1: mov.addPosition(new Position(16,19));
						mov.addPosition(new Position(17,19));
						mov.addPosition(new Position(18,19));
						mov.addPosition(new Position(19,19));
						mov.addPosition(new Position(17,18));
						break;
				case 2: mov.addPosition(new Position(16,17));
						mov.addPosition(new Position(15,17));
						mov.addPosition(new Position(15,16));
						mov.addPosition(new Position(14,16));
						mov.addPosition(new Position(14,15));
						break;
			}

		}
		else{
			switch(counter){
				case 1: mov.addPosition(new Position(0,19));
						mov.addPosition(new Position(0,18));
						mov.addPosition(new Position(0,17));
						mov.addPosition(new Position(0,16));
						mov.addPosition(new Position(1,17));
						break;
				case 2: mov.addPosition(new Position(2,16));
						mov.addPosition(new Position(2,15));
						mov.addPosition(new Position(3,15));
						mov.addPosition(new Position(3,14));
						mov.addPosition(new Position(4,14));
						break;
			}
		}
		return mov;
	}



	/** 
	 *	Methode update(Move, Status) ruft die update-Methode der Superklasse auf und 
	 *	fuehrt zusaetzlich als update im IntelligentPlayer noch ein Update des RootNodes aus
	 *	@param opponentMove Move des Gegners, der vom board uebergeben wird und in das eigene
	 *	 Board eingetragen wird
	 * 	@param boardStatus Status nach dem Zug des Gegners, wird dem eigenen Spielbrett
 	 *	uebergeben
	 *	@exception Exception Zeigt Fehler an, falls update(Move, Status) fehlschlaegt
	 */
	public void update(Move opponentMove, Status boardStatus) throws Exception{
		super.update(opponentMove, boardStatus);
		tree.updateRootNode(opponentMove);
	}

	/**	
	 * 	Konstruktor fuer ein IntelligentPlayer-Objekt, dessen eigene Spielfarben und die 
	 *	Schwierigkeitsstufe uebergeben wird
	 *  @param colors Int-Array mit den eigenen Spielfarben
	 *	@param difficulty Wert zum Einstellen der Schwierigkeit
	 */	
	public IntelligentPlayer(int[] colors, int difficulty){
		super(colors);
		//Schwierigkeitsgrad setzen
		setDifficulty(difficulty);
		//den MoveTree erzeugen
		tree = new MoveTree(this);
	}


	//Methode zum Einlesen der Faktoren aus der entsprechenden Datei
	/**
	 *	Methode readFactorFile liest die Faktoren der entsprechenden Runde aus einer 
	 *	Datei ein
	 *	@param fileName Name der Datei, aus der eingelesen werden soll
	 *	@exception IOException Zeigt Fehler beim Einlesen der Faktoren an
	 */
	private void readFactorFile(String fileName) throws IOException{
		BufferedReader br = null;

		//Beginn einlesen
		try{
			//Da aus jar eingelesen wird, wird kein FileReader, sondern 
			//getResourceAsStream(String) genutzt
			InputStream in = this.getClass().getResourceAsStream(fileName);
			br = new BufferedReader(new InputStreamReader(in));
			
			String line;
			while((line = br.readLine()) != null){
				String[] temp = line.split("\t");
				//Falls erste Zahl einer Zeile mit der aktuellen Rundenzahl uebereinstimmt, 
				//werden die Faktoren eingelesen
				if(Integer.parseInt(temp[0]) == counter){
					factorBlock = Integer.parseInt(temp[1]);
					factorTurns = Integer.parseInt(temp[2]);
					factorSize = Integer.parseInt(temp[3]);
					break;
				}
			}
		}
		finally{
			try{
				br.close();
			} 
			catch(Exception e){
				System.out.println("Fehler beim Schliessen des BufferedReaders!");
			}
		}
	}


	/**	
	 *	Methode setDifficulty(int) waehlt eine Datei mit entsprechenden Faktoren aus, 
	 *	die die Schwierigkeit anpassen
	 *	@param difficulty Enthaelt die Schwierigkeitsstufe
	 *	@exception IllegalArgumentException Zeigt ungueltige Eingabe der Schwierigkeit an 
	 */
	private void setDifficulty(int difficulty) throws IllegalArgumentException{
		if(difficulty == 1){
			nameOfFactorFile = "/blokusPP/player/KI/KIfactors/factorsDifficulty1.txt";
		}
		else if(difficulty == 2){
			nameOfFactorFile = "/blokusPP/player/KI/KIfactors/factorsDifficulty2.txt";
		}
		else if(difficulty == 3){
			nameOfFactorFile = "/blokusPP/player/KI/KIfactors/factorsDifficulty3.txt";
		}
		else{
			throw new IllegalArgumentException("Ungueltige Eingabe! Es gibt nur " + 
												"Schwierigkeitsstufen von 1 bis 3!");
		}
	}
}
