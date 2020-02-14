package blokusPP.network;

import blokusPP.preset.*;
import blokusPP.field.*;
import blokusPP.player.*;
import blokusPP.game.*;
import blokusPP.output.*;
import blokusPP.input.*;
import blokusPP.paint.*;

import java.io.*;
import java.rmi.registry.*;
import java.rmi.*;
import java.net.*;
import java.util.Scanner;

/**
 * textbasierte Auswahl zum Anbieten eines Onlinespielers
 * @author Malin Lachmann
 */ 
public class ExecutableNet {
			
	/**
	 * Bietet einen Spieler im Netzwerk an.
	 */
	 
	public static void offer() throws Exception{
	    Board board = new Board();
	    /**Anzubietener Spieler*/
	    Player	player;
		/** Spielerzahl*/ 
		int numberOfPlayers;
	    /** Eingabemedium */ 
	    Requestable requestable;
	    /** Ausgabemedium */
	    Viewer	view = board.viewer();
		//** Outputmedium*/
		Output out;
	    /** Farbe des anzubietene Spielers */
	    int []	color;
	    /** Name, unter dem der Spieler angeboten wird */
	    String name;
	
	    boolean url = true;
		//rmiregistry starten
		try {
			LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
		} catch (RemoteException ex) {
			System.out.println("RMI-Registry konnte nicht gestartet werden," + 
			                    " das Spiel wird beendet.");
			System.exit(-1);
		}
		
		Scanner sc;		
		try {
			sc = new Scanner(System.in);
		} catch (Exception e ){
			System.out.println("Probier nochmal.");
			sc = null;
		}

		// Auswahl der Spielerzahl
		System.out.println(	"Bitte die Anzahl der Spieler eingeben(2/4)");		

		numberOfPlayers =  sc.nextInt();	
		color = new int[4/numberOfPlayers];

		// Auswahl der Farbe
		if(numberOfPlayers == 2){
			System.out.println(	"Welche Farbe moechtest du spielen? \n" +
							"0 -- Blau und Rot \n"+
							"1 -- Gelb und Gruen");		

			color[0] = sc.nextInt();	
			color[1] = color[0]+numberOfPlayers;
		}

		if(numberOfPlayers == 4){
			System.out.println(	"Welche Farbe moechtest du spielen? \n" +
							"0 -- Blau \n"+
							"1 -- Gelb \n" +
							"2 -- Rot \n"+ 
							"3 -- Gruen");		

			color[0] = sc.nextInt();
		}

        // Auswahl des Spielertyps
        int index;    
		int playertype;           
        int difficulty=0;       //Schwierigkeit fuer den KI-Spieler
		
		String[] results = new String[] {"R","H","K","W"};
			index = GetStartValues.input(results,"Bitte die Art des  Spielers"+
				" eingeben:\n (R)andomplayer/(H)umanplayer/(K)I-Player/(W)eak"+
				" KI-Player");
			if(0<=index && index < PlayerNumbers.SIZEOFPLAYERS-1) {
				playertype = index;
				if(playertype == PlayerNumbers.KIPLAYER) {
					difficulty = GetStartValues.getDifficulty();
				}
			}
			else{
				playertype = -1;
				System.out.println("Keine gueltige Eingabe, das Spiel wird beendet.");
				System.exit(-1);
			}

							
							
		// Auswahl des Eingabemediums
		boolean textbased = GetStartValues.textbased(); 	//textbased = true,
		if(textbased){							 //falls Eingabe ueber Konsole
			requestable = new GetMoveConsole();
			out = new TextOutput(view);
		}
		else { 		
			boolean[] clicking = new boolean[4];
			if(color[0] == 0){
				clicking[0] = true;
				clicking[2] = true;
			}
			else if(color[0] == 1){
				clicking[1] = true;
				clicking[3] = true;
			}
			requestable = new MainWindow("blocus_longus",view, 
										clicking, numberOfPlayers);
			out = (MainWindow) requestable;
		}


		switch (playertype){
		    case 0: player = new RandomPlayer(color);
				break;
			case 1:
				player = new InteractivePlayer(color, requestable);
				break;
			case 2:
				player = new IntelligentPlayer(color, difficulty);
				break;
			case 3:
				player = new SimpleKIPlayer(color);
				break;
			//Wenn der Spielertyp falsch: auf null setzen
			default: 
				player = null; 					
		}
		
		// Name des Spielers auswaehlen
		System.out.println("Gib einen Namen fuer den Spieler ein," + 
								" den du anbieten moechtest.");
		BufferedReader console = new BufferedReader
											(new InputStreamReader(System.in));
			url = true;
			do{
				try {
					name = console.readLine();
				}catch (Exception e ){
					System.out.println("Versuch's nochmal.");
					name = null;
				}
			}while (name == null);
			Net net = new Net();
			// Spieler anbieten, bei Fehler neuen Namen erfragen
			try{
				net.offer(new ONetPlayer(player, board,"localhost", name, out),
										 "localhost", name);
			}catch(MalformedURLException e){
				System.out.println("Mit diesem Namen kann leider kein " +   
				            "Spieler erzeugt werden. Das Spiel wird beendet.");
			}catch(Exception e){
			    System.out.println("Ein Fehler ist aufgetreten, das Spiel " + 
			                  "wird beendet.");
				System.exit(-1);
			}

    }		
    
}		
			
			
