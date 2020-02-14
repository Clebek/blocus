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

/**	Klasse NextButton erzeugt einen Button um den eingegeben Zug zu ueberpruefen
*	@author Jonas Klug
*/ 
public class EndButton extends JButton{
	//	Eindeutige Identifikatiosnummer
	 private static final long serialVersionUID = -1113582265865921797L;

	/**	 Konstruktor bekommt eine Referenz auf das Hauptfenster
	*	@param main ist eine Referenz auf das Hauptfenster
	*/	
	public EndButton(MainWindow main){
		super("Pausieren");
		setSize(new Dimension(20,20));
		this.addActionListener(new NullTrain(main));
	}
	
		
}
