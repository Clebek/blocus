package blokusPP.paint;

import blokusPP.preset.*;
import java.awt.*;

/**	Das Interface Koordinaten liefert alle noetigen Variablen zur Ausrichtung der einzelnen Komponenten auf dem MainWindow sowie fuer die einzelnen Panel
*	@author Jonas Klug
*/ 
public interface Coordinates {
	 
	// Groesse des Hauptfenster auf der x-Achse
	int	xmain =1400;	
	// Groesse des Hauptfenster auf der y-Achse
	int	ymain= 1000;	
	
	int 	xexit=300;
	int 	yexit=300;
	// von wo begonnen wird zu zeichnen ( Ist nicht mehr relevant)	 
	int beginn= 50; 
	// Groesse eines Kaestchen/ Rechteck z.B. 30x30
	int field=30; 
	// Wie viele Felder/ Rechtecke gibt es	
 	int size =20;	
 	// Groesse eines Kaestchen/Rechteck des MiniDrawPanel
 	int minfield =15; 
 	// Wie viele Kaestchen auf der X-Achse gezeichnet werden (Fuer MiniDrawPanel)
	int sizex = 23;	
	// Wie viele Kaestchen auf der Y-Achse gezeichnet werden	(Fuer MiniDrawPanel)
	int sizey = 17;	

	Color lightblue = new Color(30,144,255);
	Color lightyellow = new Color(246,248,0);
	Color lightred = new Color(255,65,57);
	Color lightgreen=new Color(0,209,0);

}
