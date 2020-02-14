package blokusPP.paint.panel;

import javax.swing.*;
import java.awt.*;


import blokusPP.paint.*;

/**Klasse ZahlenPanel erzeugt die Zahlen, welche die Y-Achse des DrawPanel beschriften
*@author Jonas Klug*/ 
public class NumberPanel extends JPanel implements Coordinates{
	//	Eindeutige Identifikatiosnummer
	private static final long serialVersionUID = -1113582265865921793L;
	/** 	Konstruktor setzt Hintergrund mit gruener Farbe
	*/
	public NumberPanel(){
		super();
		setBackground(Color.green); 
	}
	
	/** Zeichnet auf ein Panel Zahlen von 1 bis 20. Zur Beschriftung der Y-Achse
	*/
	protected void paintComponent(Graphics g){
	super.paintComponent(g);
    		
		int zahl =1;
    		String s;
    		s=""+zahl;
    		for(int f=1;f<=size;f++){
    			g.drawString(s,0,f*field); // Beschriftung der Y-Achse mit 1-20
			zahl++;
			s=""+zahl;
		}

	}


}
