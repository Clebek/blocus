package blokusPP.paint.panel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*; 
import java.util.HashSet;
import java.util.*;
import blokusPP.paint.panel.*;
import blokusPP.paint.listener.*;
import blokusPP.paint.*;
import blokusPP.preset.*;
import blokusPP.field.*;
import blokusPP.view.*;
import blokusPP.poly.*;
import gnu.trove.set.hash.*;

/**	Klasse MiniWindow erbt von JPanel und implementiert das interface Koordinaten. 
*	Es wird ein Minispielfeld erzeugt, auf dem dann die Steine angezeigt werden, die 
*	der Spieler noch zur verfuegung hat.
*	@author Jonas Klug
*/ 
public class MiniDrawPanel extends JPanel implements Coordinates{
	//	Zum Beobachten von Ereignissen auf dem Board 
	private BoardViewer viewer; 
	//	Eindeutige Identifikatiosnummer
	private static final long serialVersionUID = -1113582265865921789L;
	
	//	Iterator ueber die verfuegbaren Polyominios
	private Iterator<Polyomino> element;
	
	/**	Konstruktor bekommt eine Referenz auf den BoardViewer
	*	@param viewer Referenz auf den BoardViewer
	*/
	public MiniDrawPanel(BoardViewer viewer){
		super();
		this.viewer=viewer;
	}
	
	/**	gibt ein Iterator von den verfuegbaren Polyominos einer Farbe zurueck
	*	@return Iterator 
	*/
	public Iterator<Polyomino> getPolys(){
		THashSet<Polyomino> a=viewer.giveHashSet(viewer.turn());
		return a.iterator();	
	}
	/** 	Zeichnet eine kleines Feld mit den verfuegbaren Polyominos, an festgelegten Positionen
	*/
	protected void paintComponent(Graphics g){
    		super.paintComponent(g);
    		Graphics2D g2 =(Graphics2D)g;	
    		for(int i=0;i<sizex;i++){
    			for(int f=0;f<sizey;f++){	
					g2.drawRect(minfield*i,minfield*f,minfield,minfield);
			}	
		}
		this.element=getPolys();
		if(viewer.turn()==0)
			g2.setColor(lightblue);
		if(viewer.turn()==1)
			g2.setColor(lightyellow);	
		if(viewer.turn()==2)
			g2.setColor(lightred);	
		if(viewer.turn()==3)
			g2.setColor(lightgreen);	
				
		while(element.hasNext()){
			int f = element.next().getPolyID();
			
			if(f==1)
				g2.fillRect(minfield*2,minfield*1,minfield,minfield);
			if(f==2){
				g2.fillRect(minfield*5,minfield*1,minfield,minfield);
				g2.fillRect(minfield*6,minfield*1,minfield,minfield);	
			}	
			if(f==3){
				g2.fillRect(minfield*13,minfield*1,minfield,minfield);
				g2.fillRect(minfield*14,minfield*1,minfield,minfield);
				g2.fillRect(minfield*15,minfield*1,minfield,minfield);		
			}	
			if(f==4){
				g2.fillRect(minfield*10,minfield*5,minfield,minfield);
				g2.fillRect(minfield*11,minfield*5,minfield,minfield);	
				g2.fillRect(minfield*12,minfield*5,minfield,minfield);
				g2.fillRect(minfield*13,minfield*5,minfield,minfield);
			}
			if(f==5){
				g2.fillRect(minfield*20,minfield*11,minfield,minfield);
				g2.fillRect(minfield*20,minfield*12,minfield,minfield);	
				g2.fillRect(minfield*20,minfield*13,minfield,minfield);
				g2.fillRect(minfield*20,minfield*14,minfield,minfield);
				g2.fillRect(minfield*20,minfield*15,minfield,minfield);
			}
			if(f==6){
				g2.fillRect(minfield*9,minfield*1,minfield,minfield);
				g2.fillRect(minfield*10,minfield*1,minfield,minfield);	
				g2.fillRect(minfield*10,minfield*2,minfield,minfield);
			}
			if(f==7){
				g2.fillRect(minfield*17,minfield*4,minfield,minfield);
				g2.fillRect(minfield*17,minfield*5,minfield,minfield);	
				g2.fillRect(minfield*16,minfield*5,minfield,minfield);
				g2.fillRect(minfield*15,minfield*5,minfield,minfield);
			}
			if(f==8){
				g2.fillRect(minfield*1,minfield*4,minfield,minfield);
				g2.fillRect(minfield*2,minfield*4,minfield,minfield);	
				g2.fillRect(minfield*1,minfield*5,minfield,minfield);
				g2.fillRect(minfield*2,minfield*5,minfield,minfield);
			}	
			if(f==10){
				g2.fillRect(minfield*20,minfield*4,minfield,minfield);
				g2.fillRect(minfield*21,minfield*4,minfield,minfield);	
				g2.fillRect(minfield*20,minfield*5,minfield,minfield);
				g2.fillRect(minfield*19,minfield*5,minfield,minfield);
			}
			if(f==9){
				g2.fillRect(minfield*6,minfield*4,minfield,minfield);
				g2.fillRect(minfield*5,minfield*5,minfield,minfield);	
				g2.fillRect(minfield*6,minfield*5,minfield,minfield);
				g2.fillRect(minfield*7,minfield*5,minfield,minfield);
			}
			if(f==11){
				g2.fillRect(minfield*11,minfield*13,minfield,minfield);
				g2.fillRect(minfield*12,minfield*14,minfield,minfield);	
				g2.fillRect(minfield*12,minfield*13,minfield,minfield);
				g2.fillRect(minfield*12,minfield*12,minfield,minfield);
				g2.fillRect(minfield*13,minfield*12,minfield,minfield);
			}
			if(f==11){
				g2.fillRect(minfield*11,minfield*13,minfield,minfield);
				g2.fillRect(minfield*12,minfield*14,minfield,minfield);	
				g2.fillRect(minfield*12,minfield*13,minfield,minfield);
				g2.fillRect(minfield*12,minfield*12,minfield,minfield);
				g2.fillRect(minfield*13,minfield*12,minfield,minfield);
			}
			if(f==12){
				g2.fillRect(minfield*1,minfield*8,minfield,minfield);
				g2.fillRect(minfield*1,minfield*9,minfield,minfield);	
				g2.fillRect(minfield*2,minfield*9,minfield,minfield);
				g2.fillRect(minfield*3,minfield*9,minfield,minfield);
				g2.fillRect(minfield*4,minfield*9,minfield,minfield);
			}
			if(f==13){
				g2.fillRect(minfield*14,minfield*9,minfield,minfield);
				g2.fillRect(minfield*15,minfield*9,minfield,minfield);	
				g2.fillRect(minfield*15,minfield*8,minfield,minfield);
				g2.fillRect(minfield*16,minfield*8,minfield,minfield);
				g2.fillRect(minfield*17,minfield*8,minfield,minfield);
			}
			if(f==14){
				g2.fillRect(minfield*1,minfield*12,minfield,minfield);
				g2.fillRect(minfield*1,minfield*13,minfield,minfield);	
				g2.fillRect(minfield*1,minfield*14,minfield,minfield);
				g2.fillRect(minfield*2,minfield*13,minfield,minfield);
				g2.fillRect(minfield*2,minfield*14,minfield,minfield);
			}
			if(f==15){
				g2.fillRect(minfield*7,minfield*7,minfield,minfield);
				g2.fillRect(minfield*7,minfield*8,minfield,minfield);	
				g2.fillRect(minfield*7,minfield*9,minfield,minfield);
				g2.fillRect(minfield*8,minfield*9,minfield,minfield);
				g2.fillRect(minfield*6,minfield*9,minfield,minfield);
			}
			if(f==16){
				g2.fillRect(minfield*8,minfield*12,minfield,minfield);
				g2.fillRect(minfield*9,minfield*12,minfield,minfield);	
				g2.fillRect(minfield*8,minfield*13,minfield,minfield);
				g2.fillRect(minfield*8,minfield*14,minfield,minfield);
				g2.fillRect(minfield*9,minfield*14,minfield,minfield);
			}
			if(f==17){
				g2.fillRect(minfield*10,minfield*7,minfield,minfield);
				g2.fillRect(minfield*10,minfield*8,minfield,minfield);	
				g2.fillRect(minfield*10,minfield*9,minfield,minfield);
				g2.fillRect(minfield*11,minfield*9,minfield,minfield);
				g2.fillRect(minfield*12,minfield*9,minfield,minfield);
			}
			if(f==18){
				g2.fillRect(minfield*5,minfield*12,minfield,minfield);
				g2.fillRect(minfield*6,minfield*12,minfield,minfield);	
				g2.fillRect(minfield*5,minfield*13,minfield,minfield);
				g2.fillRect(minfield*4,minfield*13,minfield,minfield);
				g2.fillRect(minfield*4,minfield*14,minfield,minfield);
			}
			if(f==19){
				g2.fillRect(minfield*16,minfield*12,minfield,minfield);
				g2.fillRect(minfield*16,minfield*13,minfield,minfield);	
				g2.fillRect(minfield*16,minfield*14,minfield,minfield);
				g2.fillRect(minfield*15,minfield*13,minfield,minfield);
				g2.fillRect(minfield*17,minfield*13,minfield,minfield);
			}
			if(f==20){
				g2.fillRect(minfield*19,minfield*1,minfield,minfield);
				g2.fillRect(minfield*18,minfield*2,minfield,minfield);	
				g2.fillRect(minfield*19,minfield*2,minfield,minfield);
				g2.fillRect(minfield*20,minfield*2,minfield,minfield);
				g2.fillRect(minfield*21,minfield*2,minfield,minfield);
			}
			if(f==21){
				g2.fillRect(minfield*21,minfield*7,minfield,minfield);
				g2.fillRect(minfield*21,minfield*8,minfield,minfield);	
				g2.fillRect(minfield*20,minfield*8,minfield,minfield);
				g2.fillRect(minfield*19,minfield*8,minfield,minfield);
				g2.fillRect(minfield*19,minfield*9,minfield,minfield);
			}
		}
		
																	
	}		
	
}

	

