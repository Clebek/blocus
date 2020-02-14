package blokusPP.paint.panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Point;

import blokusPP.paint.panel.*;
import blokusPP.paint.listener.*;
import blokusPP.paint.*;
import blokusPP.preset.*;
import blokusPP.field.*;

/**	Die Klasse ExitButton, erzeugt einen Button der beim druecken das Progarmm beendet
*	@author Jonas Klug
*/ 
public class ExitButton extends JButton{
	 //	Eindeutige Identifikatiosnummer
	 private static final long serialVersionUID = -1113582265865921795L;
	
	/**	Konstruktor erzeugt einen Button zum beenden des Spiels
	*	@param name bekommt einen Namen fuer den Button uebergeben
	*/
	public ExitButton(String name){
		super(name);
		setSize(new Dimension(20,20));
		this.addActionListener(new ExitListener());
	}
	
		
}
