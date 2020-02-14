package blokusPP.input;
import blokusPP.preset.*;
import blokusPP.field.*;

import java.io.*;
import java.lang.*;
import java.util.*;

/** Textbasierte Eingabe 
*@author Clemens Beeken
*/

public class GetMoveConsole implements Requestable{
	

	/** Default-Konstruktor */
	public GetMoveConsole(){
	
	}


	/** 
	* Erstellt einen Zug aus einem String.
	*@param str Muss String der Form "A,1 A,2 B,1 B,2" bzw. "0" fuer den leeren
		 Zug sein.
	*@return Erstellter Zug
	*/
	private Move createMove(String str) throws InputMismatchException, 
			NumberFormatException {
		Move move = new Move();

		if(str.equals("0")){
			return null;
		}

		String[] comps = str.split("\\s+");//Nach whitespaces trennen


		for(int i = 0; i< comps.length ;i++) {
			int num;
			char c;
			String[] numbers = comps[i].split(",");
			if(numbers.length != 2 || numbers[0].length() != 1)
				throw new InputMismatchException();
			num = Integer.parseInt(numbers[1]);
			c = numbers[0].charAt(0);
			Position pos = new Position(c - 'A', num - 1);
			move.addPosition(pos);
		}
		return move;
	}

	/**
	* Implementierte Methode vom Interface Requestable, nimmt Eingabe vom 
		Benutzer und erstellt daraus einen Zug.
	*@return Erstellter Zug
	*/
	public Move deliver() throws Exception{
		while(true){
			System.out.println("Bitte einen Move in der Form \"A,1 A,2 B,1 " +  
				"B,2\" eingeben, \"0\" steht fuer den leeren Zug:");
			BufferedReader in = new BufferedReader ( 
					new InputStreamReader(System.in));
			String str;
			try{ str = in.readLine();
			} catch(IOException e) {
				str = "";
				System.out.println("Fehler beim Einlesen");
			}
			str = str.replace("\"","");
			try{ return createMove(str);
			} catch (Exception e) {
				System.out.println("Fehler, bitte nochmal versuchen.");
			}
		}
		
	}
}



