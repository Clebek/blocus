package blokusPP.paint.listener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Point;
import java.util.HashSet;

import blokusPP.paint.panel.*;
import blokusPP.paint.listener.*;
import blokusPP.paint.*;
import blokusPP.preset.*;
import blokusPP.field.*;
import blokusPP.view.*;
import blokusPP.game.*;

/**	Die Klasse Gamepanellistener beobachtet ob ein auf das Spielfelt geklickt wurde. Wenn ja wir die genau Klickposition und die Anzahl der Klicks bestimmt.
* 	je nachdem kann ein Zug geloescht oder gesetzt werden. 
*	@author Jonas Klug 
*/   
public class Gamepanellistener extends MouseAdapter implements Coordinates,Setting,PlayerNumbers{

	private DrawJPanel panel;
	private BoardViewer viewer;
	private boolean [] player;
	
	/**	Kronstruktor setzt bekommt eine Referenz auf das Board, Boardviewer sowie einen boolean [] mit den Spielern uebergeben
	*	@param s ist die Refernz zum Spielfeld, viewer um Informationen vom Board zu bekommen, player ist dazu da,
	*	dass nur gezeichnet wird, wenn ein Menschlicher Spieler am Zug ist. 
	*/
	public Gamepanellistener(DrawJPanel s, BoardViewer viewer,boolean[] player){
		super();
		this.panel=s;
		this.viewer=viewer;
		this.player=player;
	}	
	/**	Sobald der Spieler auf das feld Klickt, wird die genaue Positon bestimmt und geprueft ob, er einmal, oder mehr als einmal auf das
	*	Feld geklickt hat. Je nachdem wird dann ein Zug geloescht, oder das Feld mit der entsprechenden farbe gesetzt.
	*/
	public void mouseClicked(MouseEvent e){
			int letter = e.getX()/field;	// Speichert die X Koordinate im Spielfeld. X : Groesse des Feldes um genaue Position im Array zu bekommen
			int number = e.getY()/field;	// Speichert die Y Koordinate im Spielfeld. Y : Groesse des Feldes um genaue Position im Array zu bekommen
				
			if(e.getY()/field>=20){		
				letter=19;
			}	
			if(e.getX()/field>=20){
				number=19;	
			}
		
			if(player[viewer.turn()] == true){
				if(e.getClickCount()>1 &&viewer.getColor(number,letter)==4) {
					
					panel.setdelete(number,letter);
					panel.removeHashSet(letter,number);
					panel.repaint();
				}
				else{		
					if(panel.getColor(number,letter)==4){
						panel.setColor(number,letter,panel.getturn());
						
						panel.setHashSet(new Position(letter,number));
						panel.repaint();
					}		
				}							
			}												
	}

}



