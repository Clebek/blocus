package blokusPP.view;

import blokusPP.preset.*;
import blokusPP.field.*;
import blokusPP.poly.*;

import java.util.*;
import gnu.trove.set.hash.THashSet;

/**	
 *	Klasse BoardViewer implementiert das Interface Viewer
 *	Das Board erzeugt einen Viewer-Objekt, mit dessen Hilfe auf bestimmte Parameter
 *	zugegriffen werden kann, um diese auszugeben
 *	@author Hanna Holderied
 */
public class BoardViewer implements Viewer{
	/** board speichert eine Referenz auf das Spielbrett, auf dem gespielt wird */
	private Board board;


	/** 
	 *	Konstruktor fuer einen BoardViewer, der ein Board uebergeben bekommt
	 *	@param board in this.board wird eine Referenz auf das uebergebene Board gespeichert
	 */
	public BoardViewer(Board board){
		this.board = board;
	}


	/**
	 *	Getter fuer die toString-Methode des Boards
	 *	@return Ein String, der die String-Repraesentation des Boards enthaelt
	 */
	public String getBoardString(){
		return board.toString();
	}

	
	/**
	 *	Getter fuer die toString-Methode des Boards
	 *	@return Ein String, der die String-Repraesentation des Boards enthaelt
	 */
	public THashSet<Polyomino> getAllPossiblePolys(int color){
		return board.giveHashSet(turn());
	}


	/**
	 *	Methode turn() gibt zurueck, welche Farbe gerade am Zug ist
	 *	@return Farbe, auf dessen Zug das Board wartet
	 */
	public int turn(){
		return board.getColor();
	}


	/**
	 *	Getter getColor(int, int) gibt den Eintrag auf dem Board mit den Ḱoordinaten (letter, number) zurueck
	 *	@param letter x-Koordinate des Boards
	 *	@param number y-Koordinate des Boards
	 *	@return Der Eintrag an der Stelle (letter, number) des Boards
	 */
	public int getColor(int letter, int number){
		return board.getFieldEntry(letter, number);
	}


	/**
	 *	Getter getStatus() gibt den aktuellen Status des Boards zurueck
	 *	@return Status des Boards
	 */
	public Status getStatus(){
		return board.getStatus();
	}


	/**
	 *	Getter getSize() gibt das Attribut "SIZE" des boards zurueck
	 *	@return SIZE des Boards
	 */
	public int getSize(){
		return board.getSize();
	}




	/**
	 *	Getter getCurrentResult(int) gibt die Punktzahl der uebergebenen Farbe color zurueck
	 *	@param color Farbe, dessen Punkzahl ermittelt werden soll
	 *	@return Punkzahl der Farbe color
	 */
	public int getCurrentResult(int color){
		return board.getCurrentResult(color);
	}


	/**
	 *	Methode giveHashSet(int) gibt die verbliebenen Polyominos der uebergebenen Farbe zurueck
	 *	@param color Gibt an, von welcher Farbe die verbliebenen Polyominos zureuckgegeben werden sollen
	 *	@return THastSet<Polyomino> mit allen verbliebenen Polyominos einer Farbe
	 */
	public THashSet<Polyomino> giveHashSet(int color){
		return board.giveHashSet(color);
	} 		 
}
