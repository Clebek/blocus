package blokusPP.player;

import blokusPP.preset.*;
import blokusPP.field.Board;
import blokusPP.poly.*;

import java.rmi.RemoteException;
import java.rmi.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import gnu.trove.set.hash.THashSet;

/**Klasse zur Darstellung des leicheteren KI-Spielers mit folgender Strategie:
 * Falls ein Zug das Spiel beendet und man gewinnt, wird dieser Zug gesetzt.
 * Falls der Gegner im naechsten Zug gewinnen kann wird dieser Zug nicht
 * gesetzt. Andernfalls wird aus den uebrigen Zuegen mit hoechster Summe an 
 * Punkten, die der Stein bringt und der Anzahl an Diagonalelementen, die der 
 * Zug abdeckt, zufaellig einer ausgewaehlt. Dies entspricht den in der Projektbeschreibung 
 * gestellten Anforderungen. 
 * @author Malin Lachmann und Clara Holzhueter
 */ 
public class SimpleKIPlayer extends BlokusPlayer implements Setting{
    /** Menge der Diagonalelemente des Spielfeldes.
	 */
	private HashSet<Position> diagonal = new HashSet<Position>();
	/** Zaehler fuer die Anzahl der Zuege.
	 */
	private int countMoves;

	/**Anzahl der Zuege bei denen ein Gewinn noch nicht moeglich ist, da nicht 
	genuegend Steine auf dem Feld liegen.
	*/
	private int winNotPossible = 3;


	/**Konstruktor. Funktionert, wie der der Superklasse und initialisiert das 
	 * Array mit den Diagonalelementen des Spielbretts.
	 * @param color Dem Spieler zugeteilte Farben.
	 */
	public SimpleKIPlayer(int[] color){
		super(color);
		try{
			initDiagonal(diagonal);
		}catch(Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		countMoves = 0;
	}
	
	/**Konstruktor. Funktionert, wie der der Superklasse und initialisiert das 
	 * Array mit den Diagonalelementen des Spielbretts.
	 * @param color Dem Spieler zugeteilte Farben.
	 * @param boardsize Spielfeldgroesse.
	 */
	public SimpleKIPlayer(int[] color, int boardsize){
		super(color, boardsize);
		try{
			initDiagonal(diagonal);
		}catch(Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	/**Methode zum Initialisieren des Hashsets von Positionen mit den 
	 * Diagonalelementen.
	 * @param diagonal Hashset, in das die Diagonalelemente gespeichert werden.
	 */
	public void initDiagonal(HashSet<Position> diagonal)throws Exception{
		Position tmp;
		boolean added;
		for( int i = 0; i< getBoard().getSize() -1; i++){
			tmp = new Position(i,i);
			added = diagonal.add(tmp);
			if(added == false){
				throw new Exception("Diagonalelemente konnten nicht" + 
													" eingefuegt werden");
			}
			
			tmp = new Position(i, getBoard().getSize() -1 -i);
			added = diagonal.add(tmp);
			if(added == false){
				throw new Exception("Diagonalelemente konnten nicht"+
													" eingefuegt werden");
			}
			
		}
	}
	

	/**Methode generiert Zuege und liefert diese zurueck.
	 * @return Bester Zug nach Auswahlkriterien fuer die leichte KI.
	 */
	public Move request() throws Exception, RemoteException{
	
		THashSet<Move> possibleMoves=getBoard().getAllValidMoves(getBoard().getColor());
		THashSet<Move> badMoves = new THashSet<Move>();
		Iterator<Move> itMoves;
		Move bestMoveAlternative = null;
		
		//falls Gewinn moeglich wird der gewinnende Zug zurueckgeliefert
		//wird nur ausgefuehrt, wenn ein Sieg schon moeglich ist.
		if(countMoves > winNotPossible){
			Move tmp = victoryPossible(possibleMoves);
			if (tmp!= null){
				setLastMove (tmp);
				return getLastMove();
			}
		}
		

		//Bewertung durchfuehren
		int maxscore = maxScore(possibleMoves);
		THashSet<Move> bestMoves = possibleMoves; //speichert die besten Zuege
		
		//alle Zuege mit schlechterer Bewertung loeschen
		deleteAllBadMoves(bestMoves, maxscore, badMoves);
		THashSet<Move> selectedMove = new THashSet<Move>();

		//Falls der Gegner im naechsten Zug nicht gewinnen kann, weil zu wenig
		//Zuege auf dem Brett sind, wird dies auch nicht ueberprueft.
		if(countMoves < winNotPossible){
			Random rand = new Random();
			int randomIndex = rand.nextInt(bestMoves.size());
			itMoves = bestMoves.iterator();
			//Auswahl eines zufaelligen Zuges aus der Menge der besten Zuege.
			for (int i = 0; i < randomIndex -1; i++) {
				itMoves.next();
			}
			setLastMove (itMoves.next());
			bestMoveAlternative = new Move(getLastMove().getPolyomino());
			countMoves ++;
			return getLastMove();		
		}
		
		else{ boolean firstTimeHere = true;
			do{	
				while(bestMoves.size()!=0){
					//Zufallszahl generieren und den Zug auswaehlen
					Random rand = new Random();
					int randomIndex = rand.nextInt(bestMoves.size());
					itMoves = bestMoves.iterator();
					for (int i = 0; i < randomIndex -1; i++) {
						itMoves.next();
					}
					setLastMove(itMoves.next());
					if(firstTimeHere){
						firstTimeHere = false;
						bestMoveAlternative = new Move(getLastMove().getPolyomino());
					}
					selectedMove.add(getLastMove());
					
					//Ueberpruefe, ob der zufaellig ausgewaehlte Zug zum 
					//Sieg des Gegners im naechsten Zug fuehrt.
			
						avoidOpponentsVictory(selectedMove);
						if(selectedMove.size()!=0){			
							//Wenn nicht, gib diesen zurueck
							countMoves ++;
							return getLastMove();			
						}
						else{	
							//sonst loesche diesen Zug aus der Menge der besten
							selectedMove.clear();	
							bestMoves.remove(getLastMove());
							
						}
						
				}
				//Wenn alle Zuege mit bisher bester Bewertung zum Sieg des 
				//Gegners fuehren, finde alle Zuege mit zweitbester Bewertung
				//und iteriere ueber alle Zuege. 
				bestMoves = badMoves;
				maxscore = maxScore(bestMoves);
				badMoves = new THashSet<Move>(); 
				deleteAllBadMoves(bestMoves, maxscore, badMoves); 
			
				
			}while(badMoves.size()!=0);
			//Wenn keiner den Sieg des Gegners verhindert, gib auf.
			return bestMoveAlternative;		
		}
			
	}
		
	
	/**
	 * Methode, die testet, ob mit dem naechsten Zug das Spiel beendet und 
	 * gewonnen werden kann.
	 * @param possibleMoves THashSet der moeglichen Zuege des Spielers.
	 * @return Zug, mit dem das Spiel sofort gewonnen wird, 
	     {@code null}, falls es einen solchen nicht gibt.
	 */
	private Move victoryPossible(THashSet<Move> possibleMoves){
		Iterator<Move> itMoves = possibleMoves.iterator();
		Move tmpMove;
		Status current = new Status(getBoard().getStatus());			
		while( itMoves.hasNext()){
			tmpMove = itMoves.next();
			// Durchlaufe alle moeglichen Zuege und setze sie aufs Brett.
			if(!getBoard().getStatus().isGameOver()){
	
				getBoard().addMove(tmpMove);
			}
			else{	
				break;
			}
			
			//Wenn dann das Spiel vorbei ist, pruefe, wer Sieger ist.
			if(getBoard().getStatus().isGameOver()){
				if(getColorsSize() ==1){							
					//Bei 4 Spielern: Gewinnt der aktuelle Spieler mit dem Zug?
					if(getBoard().getWinner() == getColor(0)){
						getBoard().deleteMove(tmpMove);
						getBoard().setStatus(current);
						return tmpMove;
					}
				 }
				else{ 										
					//Bei 2 Spielern werden die Punkte der Farben addiert.
					int ownscore= 0;
					int opponentscore = 0;
					for(int i=0; i < getColorsSize(); i++){
						ownscore=ownscore + getBoard().getCurrentResult(getColor(i));
						opponentscore=opponentscore + 
									getBoard().getCurrentResult((getColor(i)+1) % 
									(getColorsSize() *2));	
					}
					if(ownscore>opponentscore){
					    getBoard().deleteMove(tmpMove);
					    getBoard().setStatus(current);
						return tmpMove;
					
					}
				}					
			}
			//Falls der Zug nicht zum Sieg fuehrt, nimm ihn vom Brett.
			
			getBoard().deleteMove(tmpMove);
			getBoard().setStatus(current);
		}
		//Falls kein Zug zum Sieg fuehrt, gib null zurueck.
		return null;
	}
	
	/**
	 * Methode loescht Zuege aus der Menge der uebergebenen Zuege, mit denen 
	 * der naechste Gegner im naechsten Zug gewinnen kann.
	 * @param possibleMoves THashSet der moeglichen Zuege des Spielers.
	 */	
	private void avoidOpponentsVictory(THashSet<Move> possibleMoves){
		//Funktioniert wie victoryPossible, nur dass zusaetzlich zum eigenen
		//Zug auch alle des Gegners gesetzt werden, die Gewinnabfrage sich
		//auf den Gegner bezieht und dann beide Zuege wieder vom Brett
		//genommen werden. 
		Iterator<Move> itMoves = possibleMoves.iterator();
		THashSet<Move> oppMoves;
		Iterator<Move> itOppMoves;
		Move tmpMove;		
		Move tmpOpp;
		boolean removed;
		Status current = new Status(getBoard().getStatus());
		
		while( itMoves.hasNext()){
			tmpMove = itMoves.next();
			if(!getBoard().getStatus().isGameOver()){
				getBoard().addMove(tmpMove);
			}
			else{
				break;
			}
			int opponent = getBoard().getColor();
			oppMoves = getBoard().getAllValidMoves(opponent);
			itOppMoves = oppMoves.iterator();

			removed = false;
			
			while(itOppMoves.hasNext() && removed == false){
				tmpOpp = itOppMoves.next();
				if(!getBoard().getStatus().isGameOver()){
					getBoard().addMove(tmpOpp);
				}
				else{
					break;
				}
			
				if(getBoard().getStatus().isGameOver()){
					if(getColorsSize() ==1){							
						if(getBoard().getWinner() == opponent) {
						//Wenn der Gegner nach tmpMove gewinnt, loesche ihn.		
						itMoves.remove();
						//Weitere Zuege des Gegners muessen dann nicht 
						//betrachtet werden.
						removed = true;						
						}
				 	}
					else{ 											
						int ownscore= 0;
						int opponentscore = 0;
						for(int i=0; i < getColorsSize(); i++){
							ownscore=ownscore + 
										getBoard().getCurrentResult(getColor(i));
							opponentscore=opponentscore + 
										getBoard().getCurrentResult((getColor(i)+1) %
										(getColorsSize() *2));
						}
						if(ownscore<opponentscore){
							itMoves.remove();
							removed = true;
						}
					}					
				}
				getBoard().deleteMove(tmpOpp);
				getBoard().setStatus(current);
			}
			getBoard().deleteMove(tmpMove);
			getBoard().setStatus(current);

		}
	}
	/**Methode, die hoechste Bewertung der Zuege herausfindet.
	 * @param possibleMoves THashSets aller moeglichen Zuege.
	 * @return Bewertung des besten Zugs 
	 */
	private int maxScore(THashSet<Move> possibleMoves){
		Iterator<Move> itMoves = possibleMoves.iterator();
		Iterator<Position> itDia;
		Move tmpMove;
		Position tmpPos;
		int pieces;				//Anzahl der kleinen Quadrate des Zugs 
		int diagElements; 		//zaehlt die Diagonalelemente des Zugs
		int maxscore = 0;
		HashSet<Position> posSet;
		
		while( itMoves.hasNext()){
			diagElements = 0;
			tmpMove = itMoves.next();
			posSet = tmpMove.getPolyomino();
			pieces = posSet.size();
			itDia = diagonal.iterator();
			//Durchlaufe alle Diagonalelemente
			while(itDia.hasNext()){
				if( posSet.contains(itDia.next())){
					//und zaehle, ob und wie oft der jeweilige Zug eins abdeckt
					diagElements++;
				}
			}
			//Finde den am hoechsten bewerteten Zug
			if(maxscore < pieces+diagElements){
				maxscore = pieces+diagElements;
			}
						
		}
		return maxscore;
	}
	/**Methode, die alle Zuege mit nicht maximaler Bewertung aus dem uebergebenen
	 *   THashSet loescht. 
	 * @param possibleMoves HashSet aller moeglichen Zuege, enthaelt bei 
	    Rueckgabe nur noch Zuege mit maximaler Bewertung
	 * @param maxscore maximale Bewertung der moeglichen Zuege.
	 * @param badMoves HashSet, in das die Methode alle Zuege schreibt, die sie
	     	aus den moeglichen Zuegen loescht.	
	 */
	private void deleteAllBadMoves(THashSet<Move> possibleMoves, int maxscore, 
									THashSet<Move> badMoves){
		//Funktioniert wie maxScore
		THashSet<Move> tmpPossibleMoves = new THashSet<Move>();
		Iterator<Move> itMoves = possibleMoves.iterator();
		Iterator<Position> itDia;
		Move tmpMove;
		int pieces;
		int diagElements = 0;
		Position tmp;
		HashSet<Position> posSet;
		
		while( itMoves.hasNext()){
			diagElements = 0;
			tmpMove = itMoves.next();
			posSet = tmpMove.getPolyomino();
			pieces = posSet.size();
			itDia = diagonal.iterator();
			while(itDia.hasNext()){
				tmp = itDia.next();
				if( posSet.contains(tmp)){
					diagElements++;
				}
			}
			if(maxscore > (pieces + diagElements)){
				badMoves.add(tmpMove);
				//speichere Zuege mit schlechterer Bewertung als maxscore in
				// der Menge badMoves und loesche sie aus den possibleMoves
				itMoves.remove();
			}
		}
	
	}
	
	
	

}
