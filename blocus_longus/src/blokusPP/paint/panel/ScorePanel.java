package blokusPP.paint.panel;

import blokusPP.paint.*;
import javax.swing.*;
import java.awt.*;
import blokusPP.paint.*;
import blokusPP.view.*;


/**	Die Klasse ScorePanel erzeugt ein Objekt auf dem die einzelnen Punkte der jeweiligen Farbe gezeichnet werden 
*	@author Jonas Klug
*/
public class ScorePanel extends JPanel implements Coordinates{
	//	Eindeutige Identifikatiosnummer
	private static final long serialVersionUID = -1113582265865921790L;
	//	Referenz zum BoardViewer
	private BoardViewer viewer;
	//	Punkte Farbe blau
	private int scoreblue =0;
	//	Punkte Farbe Gelb
	private int scoreyellow= 0;
	//	Punkte Farbe Rot
	private int scorered= 0;
	//	Punkte Farbe Gruen
	private int scoregreen= 0;
	// 	Int Array mit Spielern die pausieren
	private int [] pause;

	/*	Konstruktor bekommt ein BoardViewer uebergeben und setzt alle Spieler auf nicht pausierend	
	*	@param viewer, Referenz auf BoardViewer
	*/
	public ScorePanel(BoardViewer viewer){
		super();
		this.viewer=viewer;
		this.pause=new int[4];
		for(int i=0;i<4;i++){
			pause[i]=0;
		}	
	}
	
	/*	Default-Konstruktor
	*/
	public ScorePanel(){
		super();
	}
	/*	Setzt blaue Farbe
	*	@param blue, speichert Punkte von blau
	*/
	public void setScoreblue(int blue){
		this.scoreblue=blue;	
	}	
	/*	Setzt gelb Farbe
	*	@param gelb, speichert Punkte von gelb
	*/
	public void setScoreyellow(int yellow){
		this.scoreyellow=yellow;
	}
	/*	Setzt rot Farbe
	*	@param rot, speichert Punkte von rot
	*/
	public void setScorered(int red){
		this.scorered=red;	
	}
	/*	Setzt gruen Farbe
	*	@param gruen, speichert Punkte von gruen
	*/
	public void setScoregreen(int green){
		this.scoregreen=green;
	}
	/*	Setzt eine Referenz auf den BoardViewer
	*	@param viewer, Referenz auf den BoardViewer
	*/
	public void setViewer(BoardViewer viewer){
		this.viewer=viewer;
	}	
	/*	Falls ein spieler pausiert, wird er auf 1 gesetzt
	*	@param	i sagt welcher spieler pausiert.
	*/
	public void setPause(int i){
		pause[i]=1;
	}		 
	/*	Zeichnet den Score der einzelnen Farben. Falls eine Farbe pausiert, wird dies mit in das
	*	Panel gezeichnet. Abstaende der einzelnen Komponenten sind fest gesetzt
	*/
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 =(Graphics2D)g;		
		g2.setColor(lightblue);
		g2.fillRect(0,0,field*4,field*2);
		g2.setColor(lightyellow);
		g2.fillRect(field*4,0,field*4,field*2);
		g2.setColor(lightred);
		g2.fillRect(field*8,0,field*4,field*2);
		g2.setColor(lightgreen);
		g2.fillRect(field*12,0,field*4,field*2);
		
		
		g2.setColor(Color.BLACK);
		String s=""+scoreblue;
		if(pause[0]==1)
			g2.drawString(s+" pausiert",0,field);
		else
			g2.drawString(s,0,field);
		s=""+scoreyellow;
		if(pause[1]==1)
			g2.drawString(s+" pausiert",field*4,30);
		else
			g2.drawString(s,field*4,30);
		s=""+scorered;
		if(pause[2]==1)
			g2.drawString(s+" pausiert",field*8,30);
		else
			g2.drawString(s,field*8,30);
		s=""+scoregreen;
		if(pause[3]==1)
			g2.drawString(s+" pausiert",field*12,30);
		else
			g2.drawString(s,field*12,30);	
	}


}
