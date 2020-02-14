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
public class NextButton extends JButton{
	//	Eindeutige Identifikatiosnummer
	private static final long serialVersionUID = -1113582265865921794L;
	/**	Konstruktor bekommt einen Namen, sowie eine Referenz auf Das Hauptfenster und das Drawpanel uebergeben
	*	@param name Beschriftung des Buttons
	*	@param panel Referenz aud das DrawJPanel
	*	@param main Referenz auf das Haupt JFrame
	*/
	public NextButton(String name,DrawJPanel panel, MainWindow main){
		super(name);
		setSize(new Dimension(20,20));
		this.addActionListener(new Trainlistener(panel, main));
	}
	
		
}
