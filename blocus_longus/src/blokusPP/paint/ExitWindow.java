package blokusPP.paint;

import blokusPP.view.*;
import blokusPP.paint.panel.*;
import blokusPP.paint.*;
import javax.swing.*;
import java.awt.*;


/**	Klasse ExitWindow erzeugt ein neues Jframe mit dem Ergebnis der einzelnen Spieler, sowie einem ExitButton zum beenden des
*	Programms
*	@author Jonas Klug
*/ 


public class ExitWindow extends JFrame implements Coordinates{
	 private static final long serialVersionUID = -1113582265865921788L;
	/**	Konstruktor bekommt eine Referenz auf den BordViewer, sowie die Anzahl der Spieler uebergeben
	*	@param viewer wird eine Referenz auf den uebergebene BoardViewer gespeichert
	*	@param numberplayers wird die Anzahl der Spieler uebergeben
	*/ 
	
	public ExitWindow(BoardViewer viewer,int numberplayers){
		super("Ergebnis");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setSize(xexit, yexit);
		
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		add(new ResultPanel(viewer,numberplayers));
		add(new ExitButton("Spiel Beenden"));
		setVisible(true);	
	} 
}	
