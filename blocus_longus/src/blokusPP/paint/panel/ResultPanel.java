package blokusPP.paint.panel;
import javax.swing.*;
import java.awt.*;

import blokusPP.view.*;
import blokusPP.preset.*;



/**	Die Klasse ResultPanel Zeichnet auf ein Panel die einzelnen Ergebnisse des Spiels 
*	@author Jonas Klug
*/
public class ResultPanel extends JPanel implements Setting{
	//	Eindeutige Identifikatiosnummer
	private static final long serialVersionUID = -1113582265865921791L;
	private BoardViewer viewer;
	private int numberplayers;
	/**	 Konstruktor bekommt ein referenz auf den BoardViewer, sowie die Anzahl der Spieler
	*	@param viewer Referenz auf den BoardViewer
	*	@param numberplayers speichert die Anzahl der Spieler
	*/	
	public ResultPanel(BoardViewer viewer,int numberplayers){
		super();
		this.viewer=viewer;
		this.numberplayers=numberplayers;
	}
	/**	Zeichnte je nach Spieleranzahl das Ergebnis. Gleiche punktzahlen werden beruecksichtigt.
	*/	
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		int playerblue=viewer.getCurrentResult(BLUE);
		int playeryellow=viewer.getCurrentResult(YELLOW);
		int playerred=viewer.getCurrentResult(RED);
		int playergreen=viewer.getCurrentResult(GREEN);
		int playertwo=playeryellow+playergreen;
		int playerone=playerblue+playerred;
		
		//	Falls 2 Spieler spielen
		if(numberplayers==2){
		if(playertwo==playerone){
			g.drawString("Drawn/Unentschieden",10,20);
			g.drawString("Ersterspieler (blau+rot):"+playerblue+"+"+playerred+"= "+playerone,10,40);
			g.drawString("Zweiterspieler(gelb+gruen): "+playeryellow+"+"+playergreen+"= "+playertwo,10,60);
		}	
		if(playertwo>playerone){
			g.drawString("Gewinner Spieler 2",10,20);
			g.drawString("Ersterspieler (blau+rot):"+playerblue+"+"+playerred+"= "+playerone,10,40);
			g.drawString("Zweiterspieler(gelb+gruen): "+playeryellow+"+"+playergreen+"= "+playertwo,10,60);
		}
		if(playertwo<playerone){
			g.drawString("Gewinner Spieler 1",10,10);
			g.drawString("Ersterspieler (blau+rot):"+playerblue+"+"+playerred+"= "+playerone,10,40);
			g.drawString("Zweiterspieler(gelb+gruen): "+playeryellow+"+"+playergreen+"= "+playertwo,10,60);	
		}
		}
		//	Falls 4 Spieler spieler
		if(numberplayers==4){
			int [] winner=new int[4];
			winner[0]=playerblue;
			winner[1]=playeryellow;
			winner[2]=playerred;
			winner[3]=playergreen;
			String [] colors= new String[4];
			colors[0]= "Spieler Blau:";
			colors[1]= "Spieler Gelb:";
			colors[2]= "Spieler Rot:";
			colors[3]= "Spieler Gruen:";
			for(int j=1;j<4;j++){
				if(winner[0]<winner[j]){
					int help= winner[j];
					winner[j]=winner[0];
					winner[0]=help;
					String co = colors[j];
					colors[j]=colors[0];
					colors[0]=co;
				}
			}	
			if(winner[0]==winner[1] || winner[0]==winner[2] || winner[0]==winner[3]){
					g.drawString(colors[0]+" "+winner[0],10,20);	
					g.drawString(colors[1]+" "+winner[1],10,40);	
					g.drawString(colors[2]+" "+winner[2],10,60);
					g.drawString(colors[3]+" "+winner[3],10,80);
					g.drawString("DRAW : Kein eindeutiger Gewinner!",10,100);
			}
			else{						
				g.drawString(colors[0]+" "+winner[0]+" GEWINNER",10,20);	
				g.drawString(colors[1]+" "+winner[1],10,40);	
				g.drawString(colors[2]+" "+winner[2],10,60);
				g.drawString(colors[3]+" "+winner[3],10,80);
			}			
		}
			
	}



}
