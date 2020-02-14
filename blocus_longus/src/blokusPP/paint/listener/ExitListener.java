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


/**Die Klasse ExitListener, erzeugt einen Listener der beim druecken eines ExitButton ein Ereigniss ausloest und das Progarmm beendet.
*@author Jonas Klug*/ 
public class ExitListener implements Coordinates,ActionListener{

		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}






}		
