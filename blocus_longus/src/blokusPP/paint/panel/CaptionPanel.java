package blokusPP.paint.panel;

import javax.swing.*;
import java.awt.*;

import blokusPP.paint.*;

/** 	Die Klasse CaptionPanel erzeugt eine Grafik und schreibt von A-T alle Buchstaben aus
*	@author Jonas Klug
*/
public class CaptionPanel extends JPanel implements Coordinates{
	 private static final long serialVersionUID = -1113582265865921797L;

	/**	Konstruktor erzeugt ein Panel mit der Beschriftung der x-Achse
	*/
	public CaptionPanel(){
		super();
		setBackground(Color.red);  
	}	
	
	
	/**	paintComponent bekommt ein Grafikobjekt uebergeben und schreibt darauf Strings von A-T
	*/
	protected void paintComponent(Graphics g){
		super.paintComponent(g);	
    		char abc=65;	
    		String s="";
    							
		for(int pos=0;pos<size;pos++){
			s=""+abc;
			g.drawString(s,pos*field,field);
			abc++;
		}
	}		


}
