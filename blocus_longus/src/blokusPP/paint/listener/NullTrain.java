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

/**	Klasse NullTrain, hat eine Referenz auf das Mainwindow. Der Listener beobachtet ob der Pausieren-Button gedrueckt wird. Falls ja, setzt er
*	entsprechende Variablen auf dem Mainwindow. 
*	@author Jonas Klug
*/ 
public class NullTrain implements Coordinates,ActionListener{
	
	private MainWindow main;
	
	/**	Konstrukor mit Referenz auf das MainWindow.
	*/
	public NullTrain(MainWindow main){
		super();
		this.main=main;
	}
	/** Sobald der Button Pausieren gedrueckt wurde, wird auf dem MainWindow die Hilfsvariable auf einsgestzt,
	*wodurch ein leerer Move erzeugt wird und somit die Farbe nicht mehr weiter spielen kann.
	*/
	public void actionPerformed(ActionEvent e){
          	main.setHelp(1);
          	main.setwait(false);
         }	
      	
}      	
