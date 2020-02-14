package blokusPP.game;

import blokusPP.preset.*;
import blokusPP.field.*;
import blokusPP.player.*;
import blokusPP.output.*;
import blokusPP.paint.*;
import blokusPP.input.*;
import blokusPP.view.*;
import blokusPP.network.*;

import java.util.*;
import java.rmi.*;
import java.net.*;
import java.io.*;

/**  
* Game Klasse laesst ein Spiel gemaess der Spiellogik ablaufen
*@author Clemens Beeken
*/

public class Game implements Setting,PlayerNumbers{

	/**
	* Klassenvariablen
	*/
	
	/**
	* Groesse des Spielfeldes.
	*/
	private int boardSize;
	/**
	* Das Spielbrett.
	*/
	private Board board;
	/**
	* Enthaelt an i-ter Stelle den Spieler von Farbe i.
	*/
	private Player[] players = new Player[GAME_COLORS];
	/**
	* Anzahl der Spieler
	*/
	private int numberOfPlayers;
	/**
	* Die zum Brett zugehoerige Anzeige.
	*/
	private Viewer viewer;
	/**
	* Zum Setzen der Ausgabe.
	*/
	private Output out;
	/**
	* Debug-Ausgabe.
	*/
	private TextOutput outDebug = null;
	/**
	* Zum Setzen der Eingabe.
	*/
	private Requestable in;
	/**
	* Gibt an, ob das Spiel textbasiert ablaeuft.
	*/
	private boolean textbased;
	/**
	* Debug-Schalter.
	*/
	private boolean debugon;
	/** Die Default-Groesse des Spielbretts */
	private static final int SIZE = 20;

	
	
	
	public Game(int[][] playerslist, boolean textbased, boolean debugon) 
	              throws Exception{
		this(SIZE,playerslist, textbased, debugon);
	}

	/** Konstruktor
	*@param size Groesse des Spielfelds.
	*@param playerslist Enthaelt die Spielertypen. Laenge bestimmt ob Anzahl 2 
		oder 4 ist. In int[][0] stehen die Spielertypen gemaess Playernumbers 
		und in int[][1] steht die Schwierigkeit falls der Spieler ein KI-
		Spieler ist.
	*@param textbased Falls true wird Ein- und Ausgabe ueber die Konsole 
		ablaufen.
	*/
	public Game(int size, int[][] playerslist, boolean textbased, boolean debugon)
	              throws Exception{
		boardSize = size;
		board = new Board(size);
		this.textbased = textbased;
		numberOfPlayers = playerslist.length;
		viewer = board.viewer();
		if(textbased){
			this.debugon=false;
			out = new TextOutput(viewer);
			in = new GetMoveConsole();
		}
		else {
			//Info fuer die Grafik welcher Spieler klicken darf.
			boolean[] clicking = new boolean[4];
			for(int i=0; i<playerslist.length ;i++){
				if(playerslist[i][0] == HUMANPLAYER)
					clicking[i] = true;
				else
					clicking[i] = false;
				if(playerslist.length == 2)
					clicking[i+2] = clicking[i];
			}
			in = new MainWindow("blocus_longus",viewer,clicking,
			                        numberOfPlayers);
			out = (MainWindow) in;
			this.debugon = false;
			if(debugon){
				outDebug = new TextOutput(viewer);
				this.debugon = true;		
			}
		}
		setPlayers(playerslist);
	}

	/** Default-Konstruktor mit Standardwerten. Schwacher KI-Spieler spielt 
			gegen interaktiven Spieler. */
	public Game() throws Exception{
		this(SIZE,new int[][]{{WEAK_KIPLAYER,0},{HUMANPLAYER,0}},true, false);
	}

	/** Erstellt die Spieler.
	*@param playerslist Enthaelt die Spielertypen. Laenge bestimmt ob Anzahl 2 
		oder 4 ist. In int[][0] stehen die Spielertypen gemaess Playernumbers 
		und in int[][1] steht die Schwierigkeit falls der Spieler ein KI-
		Spieler ist.
	*/
	private void setPlayers(int[][] playerslist) throws MalformedURLException, 
	  RemoteException, NotBoundException, IllegalArgumentException{		
		for(int i = 0; i < numberOfPlayers; i++) {
			switch(playerslist[i][0]) {
				case RANDOMPLAYER:
					if(numberOfPlayers == 2){
						int[] color = {i,i+2};
						players[i] = new RandomPlayer(color,boardSize);
						players[i+2] = players[i];
					}
					else{
						int[] color = {i};
						players[i] = new RandomPlayer(color,boardSize);
					}	
					break;
				case HUMANPLAYER:
					if(numberOfPlayers == 2){
						int[] color = {i,i+2};
						players[i] = new InteractivePlayer(color,boardSize,in);
						players[i+2] = players[i];
					}
					else{
						int[] color = {i};
						players[i] = new InteractivePlayer(color,boardSize,in);
					}			
					break;
				case KIPLAYER:
					if(numberOfPlayers == 2){
						int[] color = {i,i+2};
						players[i] = new IntelligentPlayer(color,
						                            playerslist[i][1]);
						players[i+2] = players[i];
					}
					else{
						int[] color = {i};
						players[i] = new IntelligentPlayer(color,
						                            playerslist[i][1]);
					}	
					break;
				case WEAK_KIPLAYER:
					if(numberOfPlayers == 2){
						int[] color = {i,i+2};
						players[i] = new SimpleKIPlayer(color,boardSize);
						players[i+2] = players[i];
					}
					else{
						int[] color = {i};
						players[i] = new SimpleKIPlayer(color,boardSize);
					}	
						
					break;
				case NETWORKPLAYER:
					//Zum Finden eines Netzwerkspielers (man hat das Hauptboard)
				    if(numberOfPlayers == 2){
				        BufferedReader console = new BufferedReader(
				                            new InputStreamReader(System.in)); 
				        String host = "";
				        String name = "";
				        try{
				            System.out.println("Namen eingeben");
				            name= console.readLine();
				        } catch(IOException e) {
				            System.out.println("Unerwarteter schwerwiegender " + 
								"Fehler beim Einlesen des Namens aufgetreten. "+ 
								"Programm wird beendet, bitte neu starten.");  
							System.exit(-1);
				        }
				        try{
				            System.out.println("Host eingeben");
				            host= console.readLine();
				        } catch(IOException e) {
				            System.out.println("Unerwarteter schwerwiegender "+
								"Fehler beim Einlesen des Hosts aufgetreten. "+ 
								"Programm wird beendet, bitte neu starten.");  
							System.exit(-1);
				        }
				        
				        
						int[] color = {i,i+2};
						players[i] = new NetPlayer(host,name);
						players[i+2] = players[i];
					}
					else{BufferedReader console = new BufferedReader(
					                        new InputStreamReader(System.in)); 
				        String host = "";
				        String name = "";
				        try{
				            System.out.println("Namen eingeben");
				            name= console.readLine();
				        } catch(IOException e) {
				            System.out.println("Unerwarteter schwerwiegender "+
								"Fehler beim Einlesen des Namens aufgetreten."+ 
								"Programm wird beendet, bitte neu starten.");  
							System.exit(-1);
				        }
				        try{
				            System.out.println("Host eingeben");
				            host= console.readLine();
				        } catch(IOException e) {
				            System.out.println("Unerwarteter schwerwiegender "+
								"Fehler beim Einlesen des Hosts aufgetreten. "+ 
								"Programm wird beendet, bitte neu starten.");  
							System.exit(-1);
				        }
				        
				        
						int[] color = {i};
						players[i] = new NetPlayer(host,name);
					}	
	
			}
		}
		return;
	}
		
	/** Wickelt ein Spiel ab. */
	public void runGame() throws Exception{

		while(!board.getStatus().isGameOver()){

				int i = board.getColor();															
			
				out.printGame();
				if(debugon){
					outDebug.printGame();
				}

				Move move = new Move();
                 boolean validMove =false;
                 while(!validMove){
				try{ move = players[i].request();
				} catch(Exception e) {
				 	System.out.println(e.getMessage());
					e.printStackTrace();
					return;			
				}
					validMove = true;
					try{ board.addMove(move); 
					} catch(RuntimeException e) { 
						System.out.println("Fehler beim Hinzufuegen des Zugs,"+ 
													" Spiel wird beendet.");
						System.exit(-1);
					} catch(Exception e) { 
						System.out.println(e.getMessage());
						e.printStackTrace();
						validMove = false;
					}
				}
				try{ 
					players[i].confirm(board.getStatus());
					for(int j = i+1;j < i +numberOfPlayers;j++){
						players[(j)%numberOfPlayers].update(move,
						                            board.getStatus());
					}
				} catch(RemoteException e){
					System.out.println(e.getMessage());
					e.printStackTrace();
					return;
				} catch(Exception e){
					System.out.println(e.getMessage());
					e.printStackTrace();
					return;
				} 
				String strMove = "Der zuletzt gespielte Zug: " + move + "\n\n";
				if(debugon){
					outDebug.textOutput(strMove);				
				}
		}

		if(textbased){
			int res1;
			int res2;
			out.printGame();
			System.out.println("Das Spiel ist aus, Endstand:");
			//Berechnung des Endstand:
			if(numberOfPlayers == 2){
				res1 = board.getCurrentResult(0) + board.getCurrentResult(2);
				res2 = board.getCurrentResult(1) + board.getCurrentResult(3);
				if(res1 > res2){
					System.out.println("1. Platz: Blau und Rot mit " + 
									res1 + " Punkten!");
					System.out.println("2. Platz: Gelb und Gruen mit " + 
									res2 + " Punkten!");
				}
				else if(res2 > res1){
					System.out.println("1. Platz: Gelb und Gruen mit " + 
									res2 + " Punkten!");
					System.out.println("2. Platz: Blau und Rot mit " + 
									res1 + " Punkten!");
				}
				else{
					System.out.println("Gleichstand! Gelb und Gruen mit " + res2 
					+ " Punkten und Blau und Rot mit " + res1 + "Punkten!");
				}
			}
			//Bei 4 Spielern mit -1 (ungueltiger Zahl) initialisieren
			else if(numberOfPlayers == 4){
				int rank1Points = -1;
				int rank2Points = -1;
				int rank3Points = -1;
				int rank4Points = -1;
				int rank1Player = -1;
				int rank2Player = -1;
				int rank3Player = -1;
				int rank4Player = -1;
				int allResults[] = {board.getCurrentResult(0), board.getCurrentResult(1), 
						board.getCurrentResult(2), board.getCurrentResult(3)};

				for(int i = 0; i < 4; i++){
					if(allResults[i] > rank1Points){
						rank1Player = i;
						rank1Points = allResults[i];
					}
				}

				for(int i = 0; i < 4; i++){
					if(i != rank1Player){
						if(allResults[i] > rank2Points){
							rank2Player = i;
							rank2Points = allResults[i];
						}
					}
				}
			

				for(int i = 0; i < 4; i++){	
					if(i != rank1Player && i != rank2Player){
						if(allResults[i] > rank3Points){
							rank3Player = i;
							rank3Points = allResults[i];
						}
					}
				}

				for(int i = 0; i < 4; i++){
					if(i != rank1Player && i != rank2Player && i != rank3Player){
						if(allResults[i] > rank4Points){
							rank4Player = i;
							rank4Points = allResults[i];
						}
					}
				}

				System.out.print("1.Platz: ");
				if(rank1Player == 0) System.out.print("Blau ");
				else if(rank1Player == 1) System.out.print("Gelb ");
				else if(rank1Player == 2) System.out.print("Rot ");
				else System.out.print("Gruen");
				System.out.println(" mit " + rank1Points + " Punkten!");

				System.out.print("2.Platz: ");
				if(rank2Player == 0) System.out.print("Blau ");
				else if(rank2Player == 1) System.out.print("Gelb ");
				else if(rank2Player == 2) System.out.print("Rot ");
				else System.out.print("Gruen");
				System.out.println(" mit " + rank2Points + " Punkten!");


				System.out.print("3.Platz: ");
				if(rank3Player == 0) System.out.print("Blau ");
				else if(rank3Player == 1) System.out.print("Gelb ");
				else if(rank3Player == 2) System.out.print("Rot ");
				else System.out.print("Gruen");
				System.out.println(" mit " + rank3Points + " Punkten!");

				System.out.print("4.Platz: ");
				if(rank4Player == 0) System.out.print("Blau ");
				else if(rank4Player == 1) System.out.print("Gelb ");
				else if(rank4Player == 2) System.out.print("Rot ");
				else System.out.print("Gruen");
				System.out.println(" mit " + rank4Points + " Punkten!");
			}
		}
		else{System.out.println("Das Spiel ist aus.");
			out.printGame();
			MainWindow exit = (MainWindow) out;
			exit.exit();
		}
	}		
}

