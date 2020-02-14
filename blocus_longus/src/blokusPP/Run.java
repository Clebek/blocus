package blokusPP;

import blokusPP.game.*;
import blokusPP.network.*;

/** 
* Run-Klasse von blocuslongus
*@author Clemens Beeken
*/

public class Run implements PlayerNumbers{

	/** Klassenvariablen
	*/
	/**Enthaelt Nummern gemaess PlayerNumbers, bei KI-Player mit Schwierigkeit 
	       in player[i][1].
	*/
	static int[][] player;

	/** Gibt an ob das Spiel textbasiert ablaeuft.
	*/
	static boolean textbased = true;

	/** Gibt an ob der Debug-Schalter gesetzt ist.
	*/
	static boolean debugon = false;

	/**
	* main Methode erstellt und startet Game mit den Werten von GetStartValues. 
	*@param args Kommandozeilen Argumente.
	*
	*/
	public static void main(String args[]) {
		if(args.length == 0){
			System.out.println("Auf der Kommandozeile \"(d)efault\" bzw." + 
					"\"(c)ustomized\" oder \"(n)etwork\"  uebergeben.");
			return;
		}
		else if(args[0].equals("d")){
			player = new int[][]{{2,1},{1,0}};
			Game game;
			try{
				game = new Game(player,textbased, false);
			} catch(Exception e){
				System.out.println("Fehler bei der Spielgenerierung, Spiel"+
					" wird beendet.");
				return;
			}
			try{
				game.runGame();	
			} catch(Exception e){
				System.out.println("Fehler bei der Spielausfuehrung, Spiel "+
					"wird beendet.");
				return;
			}
		}
		else if(args[0].equals("c")){
			Game game;
			try{
				player = GetStartValues.getPlayers();
				textbased = GetStartValues.textbased();
				if(!textbased){
					debugon=GetStartValues.debugSwitch();	
				}
			} catch(Exception e){
				System.out.println("Fehler bei der Eingabe, Spiel wird "+
					"beendet.");
				return;
			}
			try{
				game = new Game(player,textbased,debugon);
			} catch(Exception e){
				System.out.println("Fehler bei der Spielgenerierung, Spiel "+
					"wird beendet.");
				return;
			}
			try{
				game.runGame();	
			} catch(Exception e){
				System.out.println("Fehler bei der Spielausfuehrung, Spiel "+
					"wird beendet.");
				return;
			}		
		}
		else if(args[0].equals("n")){
			try{
			ExecutableNet.offer();	
			} catch(Exception e){
				System.out.println("Fehler bei der Spielgenerierung, Spiel "+
					"wird beendet.");
				return;
			}
		}
		else
			System.out.println("Auf der Kommandozeile \"(d)efault\" bzw."+ 
					"\"(c)ustomized\" oder \"(n)etwork\"  uebergeben.");
	}
}
