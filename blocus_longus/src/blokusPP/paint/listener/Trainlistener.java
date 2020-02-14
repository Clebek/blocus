package blokusPP.paint.listener;
import java.util.HashSet;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Point;
import java.util.HashSet;

import blokusPP.view.*;
import blokusPP.paint.panel.*;
import blokusPP.paint.listener.*;
import blokusPP.paint.*;
import blokusPP.preset.*;
import blokusPP.field.*;
import blokusPP.view.*;


/**	Die Klasse Trainlistener, beobachtet ob ein Spieler auf den Button "Zug ueberpruefen und beenden" gedrueckt hat.
*	 Wenn ja, wird ein Iterator ueber den HashSet der 
*	geklickten Feldern erstellt und an das Mainwindow weitergeben 
*	@author Jonas Klug
*/ 
public class Trainlistener implements Coordinates,ActionListener{
	
	private DrawJPanel panel;
	private MainWindow main;
	
	/** 	Konstruktor bekommt Instanzen der Spielfeldes, sowie des Haupfensters uebergeben
	*	@param panel Referenz auf das DrawJpanel, main Referenz auf das Mainwindow
	*	@param main Referenz auf das Hauptfenster
	*/
	public Trainlistener(DrawJPanel panel, MainWindow main){
		super();
		this.panel=panel;
		this.main=main;
	}

	/** 	sobald der Button "Zug ueberpruefen und beenden" gedrueckt wurde, wir ein Iterator<Postion> erzeugt und mit den Elementen
	*	des Hashsets des Spielfeldes. Danach, wird der zug an das Hauptfenster uergeben.
	*/
	public void actionPerformed(ActionEvent e) {
          	Iterator<Position> element=panel.getIteratorHashSet();
          	main.setHashSet(panel.getHashSet());
          	main.setwait(false);
      	}
      	





}
