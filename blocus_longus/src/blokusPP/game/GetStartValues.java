package blokusPP.game;

import blokusPP.game.*;

import java.io.*;

/**
* Klasse fuer die Eingaben fuer den Spielstart
*@author Clemens Beeken
*/

public class GetStartValues implements PlayerNumbers{

	/** Methode fuer die einzelnen Eingaben. 
	*@param results Enthaelt die zulaessigen Eingaben.
	*@param inputRequest Eingabeaufforderung die ausgegeben wird.
	*@return Index des Strings in results der eingegeben wurde.
	*/
	public static int input(String[] results, String inputRequest) {
		String str = "";
		BufferedReader in = new BufferedReader ( new InputStreamReader(
				System.in));
		while(true) {
			System.out.println(inputRequest);
			try{ str = in.readLine();
			} catch(IOException e) {
				System.out.println("Fehler beim Einlesen");
			}
			for(int i = 0; i < results.length; i++){
				if(str.equals(results[i])){
					return i;
				}
			}
		}		
	}

	/** Fragt den Benutzer ob das Spiel textbasiert sein soll
	*@return Gibt {@code true} zurueck wenn das Spiel textbasiert ablaufen 
			soll. Sonst {@code false}.
	*/
	public static boolean textbased(){
		String[] results = {"t","g"};
		int index = input(results ,"Soll das Spiel text- oder grafikbasiert"+
				" sein?(t/g)");
		if(index == 0){
			return true;
		}
		return false;
	}		
	/** Fragt den Benutzer ob der Debug-Schalter aktiviert sein soll
	*@return Gibt {@code true} zurueck wenn der Debug-Schalter gesetzt wird.
	*/
	public static boolean debugSwitch(){
		String[] results = {"j","n"};
		int index = input(results ,"Soll der Debug-Schalter gesetzt werden?"+
				" (j/n)");
		if(index == 0){
			return true;
		}
		return false;
	}	
	
	/** Fragt den Benutzer welche Spieler mitspielen sollen.
	*@return Enthaelt die Spielertypen. Laenge bestimmt ob Anzahl 2 
		oder 4 ist. In int[][0] stehen die Spielertypen gemaess Playernumbers 
		und in int[][1] steht die Schwierigkeit falls der Spieler ein KI-
		Spieler ist.
	*/

	public static int[][] getPlayers() throws IndexOutOfBoundsException{
		int numberOfPlayers;
		String[] results = {"2","4"};
		int index = input(results ,"Bitte die Anzahl "+
									"der Spieler eingeben(2/4):");
		if(index == 0){
			numberOfPlayers = 2;
		}
		else if(index == 1) {
			numberOfPlayers = 4;
		}
		else{
			System.out.println("Index hat nicht richtigen Eintrag");
			return null;
		}
		int[][] players = new int[numberOfPlayers][2];
		for(int i = 0; i < numberOfPlayers; i++) {
			
			results = new String[] {"R","H","K","W","N"};
			index = input(results ,"Bitte die Art des " + (i+1) + ". Spielers"+
				" eingeben:\n (R)andomplayer/(H)umanplayer/(K)I-Player/(W)eak"+
				" KI-Player/(N)etwork");
			if(0<=index && index < SIZEOFPLAYERS) {
				players[i][0] = index;
				if(index == KIPLAYER) 
					players[i][1] = getDifficulty();
				else
					players[i][1] = 0;
			}
			else{
				throw new IndexOutOfBoundsException("Index hat keinen "+
					"gueltigen Eintrag"); 
			}
		}

		return players;
	}

	/** Fragt den Benutzer wie stark die KI sein soll.
	*@return Schwierigkeitsgrad zwischen 1 und 3.
	*/
	public static int getDifficulty() throws IndexOutOfBoundsException{
		String [] results = new String[] {"1","2","3"};
		int index = input(results ,"Bitte die Schwierigkeit des KI-Spielers"+
			" eingeben (1-3):");
		if(0<=index && index < 3) {
			return index + 1;
		}
		else{
			throw new IndexOutOfBoundsException("Index hat keinen gueltigen"+
				" Eintrag"); 
		}
	}
}
		
		
