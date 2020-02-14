package blokusPP.paint;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints.*;
import java.awt.*;

import java.util.HashSet;
import blokusPP.output.*;
import blokusPP.view.*;
import blokusPP.paint.panel.*;
import blokusPP.paint.listener.*;
import blokusPP.paint.*;
import blokusPP.preset.*;
import blokusPP.field.*;

/**	Die Klasse MainWindow, erzeugt alle Komponenten (MiniDrawPanel,DrawPanel,BeschriftunsPanel,ZahlenPanel) fuer das Spiel und setzt Sie auf das JFrame.
* 	Die einzelnen Panel werden dann durch ein GridBagLayout auf dem JFrame geordnet. 
*	@author Jonas Klug*/ 
public class MainWindow extends JFrame implements Coordinates,Requestable,Output,Setting {
	
	private int help;
	private BoardViewer viewer;
	private HashSet<Position> pos = new HashSet<Position>();
	private ScorePanel score;
	private MiniDrawPanel mini;
	private DrawJPanel picture;
	private boolean wait;
	private boolean [] player;
	private int numberplayers;
	//	Eindeutige Identifikatiosnummer
	private static final long serialVersionUID = -1113582265865921787L;
	/*	Konstruktor, erstellt ein JFrame mit den festgelegten Groessen aus der Schnittstelle Koordinaten und setzt ein GridBagLayout. 
	*	Danach werden einzelne Panel erzeugt und ueber ein GridBagConstraints Objekt,auf dem JFrame angeordnet
	* 	@param name, Name des fensters
	*	@param viewer speichert einer Referenz auf den BoardViewer
	*	@param player, uebergibt einen boolean Array in dem gespeichert ist, welche Farbe von menschlichen
	*	Spielern gespielt werden
	*	@param numberplayers, gibt die Anzahl der Spierler an 2/4
	*/
	public MainWindow(String name, Viewer viewer, boolean[] player, int numberplayers){
		super(name);			
		if(viewer instanceof BoardViewer){
			this.viewer = (BoardViewer) viewer;
		}
		this.player=player;
		this.help=0;
		this.numberplayers=numberplayers;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Setzt die Groesse (Variablen aus Schnittstelle Koordinaten		
		setSize(xmain, ymain);		
		setLayout();
	}
	
		

	/** erzeugt die einzelnen Panel und ordnet sie durch ein GridBagLayout auf dem JFrame*/
	public void setLayout(){
		//___Setzten des Layouts
		// erzeugt ein neues GridBagLaout 
		GridBagLayout gbl = new GridBagLayout();
			
		setLayout(gbl);					
	
	
		CaptionPanel abc =new CaptionPanel();	
		// GridBagConstraints Objekt bekommt alle noetigen Informationen zur Ausrichtung 
		GridBagConstraints a = new GridBagConstraints();	
		// Welche Zelle von X Achse aus
		a.gridx=2; 
		// Welche Zelle von y Achse aus	 
		a.gridy=0;  	
		//Fuellt die komplette Breite aus
		a.gridwidth=GridBagConstraints.REMAINDER;	
		// Groesse der Zelle in x Richtung ->
		a.ipadx=field*20;		
		// Groesse der Zelle in yRichtung 
		a.ipady=field;		
		 // Breite der Zelle im Verhaeltnis zu anderen Komponenten in x Richtung / Gewichtung 	
		a.weightx = 0;         
		 // Breite der Zelle im Verhaeltnis zu anderen Komponenten in y Richtung / Gewichtung           
  		a.weighty = 0; 			 
  		//  Ausabstand der Zelle zu den anderen Zellen an
  		a.insets = new Insets(0, 0, 0, 0);  
		// Textausrichtung innerhalb der Zelle
		a.anchor=GridBagConstraints.LINE_END;	
		// Fuegt die neue Zelle in das Layout mit den Informationen die in GridBagConstraints gespeichert sind	
		gbl.setConstraints(abc, a);
		 // Einfuegen auf das JFrame
		add(abc);		
		
		
		MiniDrawPanel mini = new MiniDrawPanel(viewer);
		this.mini = mini;
		GridBagConstraints b = new GridBagConstraints();
		b.gridx=0;
		b.gridy=1;
		b.ipady=minfield*sizey;
		b.ipadx=minfield*sizex;
		gbl.setConstraints(mini,b);
		add(mini);
		
		
		NumberPanel zahlen = new NumberPanel();
		GridBagConstraints d = new GridBagConstraints();
		d.gridx=1;
		d.gridy=1;
		d.ipady=field*20;
		d.ipadx=field;
		d.insets = new Insets(1, 50, 1, 1);
		d.weightx = 0;                    
  		d.weighty = 0;
  		d.anchor = GridBagConstraints.EAST; 
		gbl.setConstraints(zahlen,d);
		add(zahlen);
	
	
		DrawJPanel picture = new DrawJPanel(viewer,this,player);
		this.picture= picture;
		GridBagConstraints e = new GridBagConstraints();
		e.insets = new Insets(0, 0, 0, 0);
		e.gridx=2;
		e.gridy=1;
		e.ipadx= field*20; 
		e.ipady=field*20;
		gbl.setConstraints(picture,e);
		add(picture);
	
	
		ScorePanel da =new ScorePanel(viewer);
		this.score=da;
		GridBagConstraints f = new GridBagConstraints();
		f.insets = new Insets(0, 0, 0, 0);
		f.gridx=2;
		f.gridy=2;
		f.ipadx= field*20; 
		f.ipady=field*3;
		gbl.setConstraints(score,f);
		add(score);
		
		
		EndButton pause =new EndButton(this);
		GridBagConstraints v = new GridBagConstraints();
		v.gridx=0;
		v.gridy=2;
		v.insets = new Insets(0, 0, 0, 0);
		gbl.setConstraints(pause,v);
		add(pause);
		
		NextButton zugbeenden = new NextButton("Zug ueberpruefen und beenden", picture, this);
		GridBagConstraints c = new GridBagConstraints();
		c.gridx=1;
		c.gridy=2;
		c.insets = new Insets(0, 0, 0, 0);
		gbl.setConstraints(zugbeenden,c);
		add(zugbeenden);
	
		
		setVisible(true);	
					
	}

	/**	Setzt die Punkte der einzelnen Farben auf dem dem ScorePanel
	*/ 
	public void setScorer(){
			score.setScoreblue(viewer.getCurrentResult(0));
			score.setScoreyellow(viewer.getCurrentResult(1));
			score.setScorered(viewer.getCurrentResult(2));
			score.setScoregreen(viewer.getCurrentResult(3));
	}	
	/**	Setzt die den uebergeben, typiesierten Hasset
	*	@param pos HashSet<Position> mit den einzelnen Positionen
	*/	
	public void setHashSet(HashSet<Position> pos){
		this.pos=pos;
	}	
	/** 	Liefert einen gesetzten Zug an die Main zurueck
	*	@return a liefert ein Move Objekt mit den eingegeben Positionen des Spielers
	*/ 
	public Move deliver() throws Exception{
		this.wait =true;
		while(wait==true){
			Thread.sleep(1000);
		}
		Move a = new Move(pos);
		if(help ==1){
			score.setPause(viewer.turn());
			a=null;
			this.help=help-1;
		}
	
		
		pos.clear();
		printGame();
		return(a);
			
	}
	/**	Ruft ein neus JFrame auf, das Spiel beendet*/
	public void exit(){
		ExitWindow exit=new ExitWindow(viewer, numberplayers);
	}
	
	/**	 Zum setzten der booleischen Variable wait
	*	 @param	wait mit true oder false
	**/ 
	public void setwait(boolean wait){
		this.wait=wait;
	}	
	
	/**	 Aktualiesiert die einzelnen Komponenten auf dem MainWindow.
	*/ 
	public void printGame(){
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				picture.setColor(i,j,viewer.getColor(i,j));
			}
		}
		mini.repaint();
		setScorer();		
		picture.repaint();
			
		score.repaint();
		Status end =viewer.getStatus();
		try{Thread.sleep(100);
		} catch (InterruptedException e) {
  			Thread.currentThread().interrupt();
		}
		
	}	
	/**	Setzt die Hilfsvariable help
	*	@param help , Hilfsvariable 
	*/
	public void setHelp(int help){
		this.help=help;
	}
	
}
