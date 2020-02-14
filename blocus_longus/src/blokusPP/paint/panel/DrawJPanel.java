package blokusPP.paint.panel;

import java.util.HashSet;
import blokusPP.paint.panel.*;
import blokusPP.paint.listener.*;
import blokusPP.paint.*;
import blokusPP.preset.*;
import blokusPP.field.*;
import blokusPP.view.*;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*; 
/**	Klasse DrawPanel, zeichnet das eigentlich Spielfeld. Das Interface Coordinates liefert die noetige Groesse zum Zeichnen der einzelnen Rechtecke.
*	Jedes Rechteck wird in einem 2D Array gepeichert.
*	@author Jonas Klug*/ 
public class DrawJPanel extends JPanel implements Coordinates,Setting{
	//	Eindeutige Identifikatiosnummer
	private static final long serialVersionUID = -1113582265865921798L;
	//	Referenz auf das Hauptfenster
	private MainWindow main;
	//	HashSet mit Positionen der angekickten Felder 
	private HashSet<Position> pos = new HashSet<Position>();
	//	20x20 grosses Array
 	public int [][] rectangle;
 	//	Refernz zum Boardviewer 		
 	private BoardViewer viewer;
 	//	Postion in Richtung der Buchstaben
 	private int letter;
 	//	Postion in Richtung der Zahlen
 	private int number;
 	//	Anzahl der Spieler
 	private boolean [] player;
 	
 	/**Konstruktor der die Objektvariablen intialisiert
 	*@param viewer zum Daten vom Board abzufragen, main um eine Referenz auf den 
 	*JFrame zu haben, player speichert die Menschlcihen Spieler*/
 	public DrawJPanel(BoardViewer viewer, MainWindow main,boolean [] player ){
		super();
		this.player=player;
		this.viewer=viewer;
		this.main=main;
		// Es wird zusaetzlich ein 20x20 grosse Matrix zum speichern der einzelnen Farben
		rectangle =new int[size][size];	
		for(int f=0;f<size;f++){
    			for(int i=0;i<size;i++){
    				rectangle[f][i]=viewer.getColor(f,i);
    			}
    	}		
		Gamepanellistener listener =new Gamepanellistener(this,viewer,player);
		addMouseListener(listener);	// Erzeugt einen Listener der das komplette Spielfeld Beobachtet
	}
		

	/**Zeichnet ein 20x20 grosses Feld (Hauptspielfeld) in den jeweiligen Farben*/
	protected synchronized void paintComponent(Graphics g){
    		super.paintComponent(g);
   			Graphics2D g2 =(Graphics2D)g;
	   	
   			for(int f=0;f<size;f++){
    				for(int i=0;i<size;i++){	
    					if(rectangle[f][i]==4){
   						g2.setColor(Color.black);	
   						g2.drawRect(field*i,field*f,field,field);
   					}
   					if(rectangle[f][i]==3){
   						g2.setColor(lightgreen);	
   						g2.fillRect(field*i,field*f,field,field);
   						g2.setColor(Color.black);
   					}
   					if(rectangle[f][i]==2){
   						g2.setColor(lightred);	
   						g2.fillRect(field*i,field*f,field,field);
   						g2.setColor(Color.black);
   					}
   					if(rectangle[f][i]==0){
   						g2.setColor(lightblue);	
   						g2.fillRect(field*i,field*f,field,field);
   						g2.setColor(Color.black);
   					}	
   					if(rectangle[f][i]==1){
   						g2.setColor(lightyellow);	
   						g2.fillRect(field*i,field*f,field,field);
   						g2.setColor(Color.black);
   					}	
   				}
   			}
	}	 	
   	
   	
   	
   	
   	/** Gibt die aktuelle Farbe die am Zug ist zurueck
   	*@return int wert*/	
   	public int getturn(){
   		return viewer.turn();
   	}	
   	/** Gibt die Farbe an einer bestimmten Position in der Matrix gespeichert ist
   	*@param number Richtung in Y-Achse
	*@param letter letter Richtung in X-Achse
   	*/
   	public int getColor(int number, int letter){
   		return rectangle[number][letter];
   	}
   	/**	Setzt entsprechende Farbe in die Matrix
   	*	@param number Richtung in Y-Achse
   	*	@param letter Richtung in X-Achse
   	*	@param color entsprechende Farbe
   	*/ 
   	public void setColor(int number, int letter, int color){
   		this.rectangle[number][letter]=color;
   	}	
    		
   	
   	// Gibt die bevorzeugte Groesse des Panel zurueck	 	
	public Dimension getPreferredSize() {
	        return new Dimension(500, 200);
	}
   	
   	/** Zum setzten des Hashsets
 	*@param pos enthaelt eine Objekt der Klasse Position*/
 	public void setHashSet(Position pos){
 		this.pos.add(pos);
 	}
 	/** Loescht den Hashset*/
 	public void clearHashSet(){
 		pos.clear();
 	}
 	/**entfernt eine Position vom Hashset
 	*@param number
	*@param letter
 	*/
 	public void removeHashSet(int number, int letter){
 		Position vergleich =new Position(number,letter);
 		pos.remove(vergleich);
 	}
 	/** Gibt einen Iterator ueber alle Elemente im Hashset
 	*return Iterator<Position>
 	*/
 	public Iterator<Position> getIteratorHashSet(){
 		return pos.iterator();
 	}
 	/** Gibt einen Hashset mit Positionen zurueck*/
 	public HashSet<Position> getHashSet(){
 		return pos;
 	}
 	/**Loescht eine Farbe in der Matrix*/
 	public void setdelete(int number, int letter){
 		rectangle[number][letter]=4;
 	}

}





