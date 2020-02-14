package blokusPP.output;

import blokusPP.preset.*;
import blokusPP.field.*;
import blokusPP.view.*;
import blokusPP.poly.*;

import java.util.*;
import gnu.trove.set.hash.THashSet;


/** 
 *	Klasse TextOutput implementiert das Interface Output und dient als Textoutput fuer das Konsolenspiel
 *	@author Hanna Holderied
 */
public class TextOutput implements Output{
	/** Als Attribut hat TextOutput einen Boardviewer */
	BoardViewer viewer;

	
	/** 
	 *	Konstruktor fuer ein TextOutput-Objekt, das einen Viewer uebergeben bekommt
	 *	@param viewer Uebergebener Viewer, ueber den indirekt auf Attribute des Boards zugegriffen werden koennen
	 *	@exception Exception Exception wird geworfen, wenn viewer keine Instanz von BoardViewer ist
	 */
	public TextOutput(Viewer viewer) throws Exception{
		//Casten von Viewer viewer nach BoardViewer
		if(viewer instanceof BoardViewer){
			this.viewer = (BoardViewer) viewer;
		}
		else{
			throw new Exception("Inkompatibles Element uebergeben.");
		}
	}


	/** 
	 *	Methode textOutput(String) ermoeglicht die Ausgabe von jedem beliebigen uebergebenem String
	 *	@param s Auszugebender String
	 */
	public void textOutput(String s){
		System.out.println(s);
	}


	/**
	 *	Methode printPoly() gibt die noch verbliebenen Polyominos aus
	 */
	public void printPoly(){
		THashSet<Polyomino> polys = viewer.getAllPossiblePolys(viewer.turn());
		Iterator<Polyomino> it = polys.iterator();
		while(it.hasNext()){
			System.out.println(it.next());
		}
	}


	/**
	 *	Methode printScores() gibt fuer jede Farbe die aktuelle Punktzahl aus
	 */
	public void printScores(){
		System.out.println("Spielstand:");
		System.out.println("Blau: " + viewer.getCurrentResult(0));
		System.out.println("Gelb: " + viewer.getCurrentResult(1));
		System.out.println("Rot: " + viewer.getCurrentResult(2));
		System.out.println("Gruen: " + viewer.getCurrentResult(3) + "\n");
	}



	/**
	 *	Methode printGame() gibt ein Spielbrett aus, auf dem die aktuelle ziehende Farbe, 
	 *	das Spielbrett, die Punktzahlen und die noch verbleibenden Polys der ziehenden
	 *	Farbe zu sehen sind
	 */
	public void printGame(){
		System.out.println("Aktueller Status der einzelnen Farben: " + viewer.getStatus() + "\n\nAktuell am Zug: ");
		switch(viewer.turn()){
			case 0: System.out.println("Blau\n");
					break;
			case 1: System.out.println("Gelb\n");
					break;
			case 2: System.out.println("Rot\n");
					break;
			case 3: System.out.println("Gruen\n");
					break;
		}

		System.out.println("Spielbrett:\n");
		System.out.print(viewer.getBoardString());

		printScores();

		System.out.println("Die verbleibenden Polyominos:");
		printPoly();
		System.out.println();
	}

}
