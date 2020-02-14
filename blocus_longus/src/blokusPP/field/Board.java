package blokusPP.field;

import blokusPP.preset.*;
import blokusPP.poly.*;
import blokusPP.view.*;
import java.util.*;
import gnu.trove.set.hash.*;


/** 
 * Klasse Board, die Move Elemente ueberprueft und in ein neu erzeugtes Spielfeld einfuegt 
 * @author Tobias Luecking
 * @version 1.0
 */

public class Board implements Setting,Viewable{
		  
	/**
	 * Klassenvariablen
	 */

	/** Die Groesse des Spielfeldes*/ 
	private final int SIZE;
	/** Die aktuelle Farbe (Am Anfang Blau) */
	private int COLOR = BLUE;
	/**Der Status des Spielfelds */
	private Status gameStatus = new Status(0);
	/** Das Spielfeld wird als int Array initialisiert */
	private int[][] field ;
	/** Das HashSets fuer alle noch uebrigen Polyominos der Farbe Blau*/ 
	private THashSet<Polyomino> blueMoves = new THashSet<Polyomino>();
	/** Das HashSets fuer alle noch uebrigen Polyominos der Farbe Gelb*/ 
	private THashSet<Polyomino> yellowMoves = new THashSet<Polyomino>();
	/** Das HashSets fuer alle noch uebrigen Polyominos der Farbe Rot*/ 
	private THashSet<Polyomino> redMoves = new THashSet<Polyomino>();
	/** Das HashSets fuer alle noch uebrigen Polyominos der Farbe Gruen*/ 
	private THashSet<Polyomino> greenMoves = new THashSet<Polyomino>();
	/** Variable um letzten Zug zu speichern*/
	public Move lastMove;
	/** Der vorletzte Zug*/
	public Move lastlastMove;
	/** Das letzte gesetzte Polyomino*/
	public Polyomino lastpoly;
	/** Hier soll das vorletzte gesetzte Polyomino gespeichert werden*/
	public Polyomino lastlastpoly;
	/** Hier soll die letzte Farbe des letzten Zuges gespeichert werden*/
	private int lastcolor;
	/** Hier soll die vorletzte Farbe des vorletzten Zuges gespeichert werden*/
	private int lastlastcolor;
	/** Hier soll der Status des letzten Zuges gespeichert werden*/
	private Status lastStatus = new Status(0);
	/** Hier soll der Status des vorletzten Moves gespeichert werden*/
	private Status lastlastStatus = new Status(0);

	/** Methode fuer den Boardviewer
	 * @return gibt neuen BoardViewer des Boards aus
	 */
	public Viewer viewer() {
		return new BoardViewer(this);
	}
	/** Default - Konstruktor - Erstellt ein 2D Array
	 * der Groesse SIZE*SIZE und setzt alle Werte auf
	 * NONE(4) Ausserdem werden alle 21 Figuren in die entsprechenden HashSets
	 * fuer die Farben gespeichert
	 */
	public Board() {
		SIZE = 20;
		this.field  = field;
		field = new int[SIZE][SIZE];
		for(int i = 0; i < SIZE; i++)
			for(int j = 0; j < SIZE; j++) {
				field[i][j] = NONE;
			}
		savePolysToHashSet(blueMoves);
		savePolysToHashSet(yellowMoves);
		savePolysToHashSet(redMoves);
		savePolysToHashSet(greenMoves);
	}
	/**Konstruktor - Analog zu obigem Konstruktor
	 * allerdings mit einer gewuenschten Groesse
	 * @param SIZE2 setzt Groesse des Spielfelds fest
	 */
	public Board(int SIZE2) {
		SIZE = SIZE2;
		this.field  = field;
		field = new int[SIZE][SIZE];
		for(int i = 0; i < SIZE; i++)
			for(int j = 0; j < SIZE; j++) {
				field[i][j] = NONE;
			}
		savePolysToHashSet(blueMoves);
		savePolysToHashSet(yellowMoves);
		savePolysToHashSet(redMoves);
		savePolysToHashSet(greenMoves);
	}

	/** Liefert die Groesse des Spielfeldes
	 * @return Rueckgabe der Groesse des Spielfeldes
	 */
	public int getSize() {
		return SIZE;
	}

	/** Liefert die aktuelle Farbe
	 * @return get Methode zum Abholen der gesetzten Farbe
	 */
	public int getColor(){
		return COLOR;
	}
	/** Liefert den Eintrag des Feldes an der Stelle i,j
	 * @param i Hoehenkoordinate des Feldes
	 * @param j Breitenkoordinate des Feldes
	 * @return getFieldEntry - liefert den Wert an der Stelle i,j im Feld
	 */
	public int getFieldEntry(int i, int j) {
		return field[i][j];
	}
	/**	Liefert die Startecken einer bestimmten Farbe
	 * @param color Gewuenschte Farbe
	 * @return liefert die int Werte der 
	 * entsprechenden Ecken fuer blau,gelb,rot,gruen des Spielfeldes -
	 * Beispiel: Ecke [0][0] (blaue ecke) ist leer,dann wird int Wert 4 fuer
	 * NONE wiedergegeben
	 */
	private int getFieldCorner(int color) {
		if(color == BLUE)
			return field[0][0];
		else if(color == YELLOW)
			return field[0][SIZE-1];
		else if(color == RED)
			return field[SIZE-1][SIZE-1];
		else if(color == GREEN) 
			return field[SIZE-1][0];
		else {
		
			gameStatus.setScoreAll(4);
			return 0;
		}
	}

	/**	Liefert die aktuelle Punktzahl einer bestimmten Farbe
	 * @param color gewuenschte Farbe
	 * @return liefert die aktuelle Punktzahl einer Farbe
	 */
	public int getCurrentResult(int color) throws IllegalArgumentException{
		int result = 0;
			if(color < 0 || color > 3)
				throw new IllegalArgumentException("Dem Wert " + color + 
				" ist keine Farbe zugewiesen! Geben sie Werte zwischen 0 - 3 ein");
			for(int i = 0; i < SIZE; i++)
				for(int j = 0; j < SIZE; j++)
					if(field[i][j] == color)
						result++;
		if(giveHashSet(color).size() == 0)
			result+=15;
		return result;
	}

	/** Gibt den Status der Farben aus
	 * @return gibt den aktuellen Status der Farben aus 
	 */
	public Status getStatus() {
		return gameStatus;
	} 
	/** Setzt den aktuellen Status auf einen anderen Wert
	 * @param status Der gewuenschte neue Status
     *
     */
	public void setStatus(Status status){
		gameStatus = new Status(status);
	}

	/** Gibt die Zuege aus, an denen eine Farbe mit mindestens einem Stein
	 * anlegen kann
	 * @param color gewuenschte Farbe
	 * @return liefert alle Felder an die mindestens ein Quadrat an einer
	 * bestimmten Farbe anlegen kann und speichert sie in einem Move Element
	 */
	public Move getPossibleMoves(int color) {
		Move result = new Move();
		// durchlaeuft das Feld
		for(int i = 0; i < SIZE; i++) {
			for(int j = 0; j < SIZE; j++) {
				// Speichert die Stelle i,j als Position
				Position pos2 = new Position(j,i);
				// Wenn feld an der Stelle i,j = 4(NONE) ist und das pos
				// Element einen diagonalen Nachbarn hat, aber keinen direkten
				// Nachbarn, so fuege die Position in den Move ein 
				if(field[i][j] == NONE && sameCornerNeighbours(pos2,color) == true
				   && NotSameNeighbours(pos2,color) == true){
					result.addPosition(pos2);
				}
				// Wenn das pos Element eine Startecke ist, das Feld an der Stelle = 
				// NONE ist und die Punktzahl der entsprechenden Farbe = 0 ist fuege
				// das Position Element in das Move ein 
				else if (isCorner(color,pos2) == true && field[i][j] == NONE 
						 && gameStatus.getScore(color) == 0) {
					result.addPosition(pos2);
					return result;
				}
			}
		}	
		return result;
	}
	/** Justiert eine Position an eine bestimmte andere Zielposition
	 * @param p soll Buchstaben der ersten gesetzten Position in toPosition sein
	 * @param q soll Nummer der ersten gesetzten Position in toPosition sein
	 * @param a soll Nummer des Position Arrays (posarray) in toPosition sein
	 * @param target soll die Zielposition aus toPositions sein
	 * @param posarray soll das Position Array aus toPositions sein
	 * @return gibt die veraenderte Position aus toPositions aus
	 */
	private Position adjustPosition(int p, int q, int a, Position target, Position[] posarray) {
		// In r wird der Buchstabe des Position Arrays an der Stelle a gespeichert
		int r = posarray[a].getLetter();
		// In t wird die Nummer des Position Arrays an der Stelle a gespeichert
		int t = posarray[a].getNumber();
		// l ist die Variable, in der der Abstand oder die Differenz des Buchstaben zur Vergleichsposition
		// gespeichert wird 
		int l = 0;
		// n ist die Variable, in der der Abstand oder die Differenz der Nummer zur Vergleichsposition
		// gespeichert wird 
		int n = 0;
		// In posi soll das "veraenderte" Position Element gespeichert werden*/
		Position posi = new Position(0,0);
		// Wenn der Buchstabe des zu setzenden Pos Elements kleiner als das zu vergleichende
		// Startposition Elements kleiner ist und die Nummer des zu setzenden Pos Elements kleiner
		// oder gleich ist oder umgekehrt, so berechne den Abstand l des Buchstaben und n der Nummer
		// und speichere diese Werte in das finale Position Element
		// die weiteren if's sind aehnlich. 
		if((r < p && t <= q) || (r <= p && t < q)) {
			l = p - r;
			n = q - t;
			posi.setLetter(target.getLetter()-l);
			posi.setNumber(target.getNumber()-n);
		}
		else if((r > p && t >= q) || ( r >= p && t > q)) {
			l = r - p;
			n = t - q;
			posi.setLetter(target.getLetter()+l);
			posi.setNumber(target.getNumber()+n);
		
		}
		else if(r > p && t < q) {
			l = r - p;
			n = q - t;
			posi.setLetter(target.getLetter()+l);
			posi.setNumber(target.getNumber()-n);
		}
		else if(r < p && t > q) {
			l = p - r;
			n = t - q;
			posi.setLetter(target.getLetter()-l);
			posi.setNumber(target.getNumber()+n);
		}
		return posi;
	}

	/** Versetzt einen Zug an eine bestimmte Stelle. Dabei wird jede Position des Zuges
	 * an die bestimmte Stelle verschoben und ueberprueft, ob dieser Zug gueltig ist.
	 * Anschliessend werden alle gueltigen verschobenen Zuege in einem HashSet gespeichert
	 * @param move Ein Move Element das an eine bestimmte Stelle verschoben werden soll
	 * @param target die Zielposition, an die das Move verschoben werden soll
	 * @param color die gewuenschte Farbe
	 * @return versetzt ein Poly an eine bestimmte Stelle. Dabei wird jedes Position 
	 * Element zu der Zielposition und abhaengig davon werden die anderen Position Elemente 
	 * gesetzt und in ein neues Move gespeichert und dieses dann in eine HashSet von Moves.
	 * Das wird solange widerholt, bis jedes Position Element einmal zur Zielposition wurde.
	 * Dabei werden moves abgefangen, die ausserhalb des
	 * Spielfelds oder nicht gueltig sind
	 */
	private THashSet<Move> toPosition(Move move, Position target,int color) {
		THashSet<Move> List = new THashSet<Move>();
		Move newmove = new Move();
		int p = 0;
		int q = 0;
		int i = 0;
		int temp = 0;
		// Alle Positionen des Zuges werden in ein Position Array gespeichert
		Position[] posarray = new Position[move.getPolyomino().size()];
		for(Position pos: move.getPolyomino()) {
			posarray[i] = pos;
			i++;
		}
		// 1. for schleife: solange k < als Anzahl der Position Elemente
		for(int k = 0; k < move.getPolyomino().size(); k++) {
		// newmove stellt den Zug dar, der am ende eines Durchlaufs der zweiten for
		// Schleife in das Ergebnis Hashset eingefuegt wird
		newmove = new Move();
		// temp wird benoetigt damit er die while schleife a<k nur einmal ausfuehrt
		temp = k;
			for(int j = k; j < move.getPolyomino().size(); j++) {

				if(j == k) {
					// in p und q wird die Startposition gespeichert. Diese wird 
					// mit den anderen Positionen im Array verglichen
					p = posarray[j].getLetter();
					q = posarray[j].getNumber();
					newmove.addPosition(target);
				}
				if(temp > 0) {
					// Diese for Schleife durchlaeuft alle Positionen im Array 
					// die vor der Startposition kommen
					for(int a = 0; a < k; a++) {
					Position pos = adjustPosition(p,q,a,target,posarray);
					newmove.addPosition(pos);	
					}	
					// temp wieder auf 0 um Endlosschleife zu vermeiden
					temp = 0;
				}

				// Falls alle vorherigen Positions im Array abgearbeitet wurden 
				// oder keine vorherigen existieren macht er analog zu dem vorherigen
				// if weiter
				else if(j != k) {
					newmove.addPosition(adjustPosition(p,q,j,target,posarray));
				}

			}
		// Wenn die Zuege alle gueltig und im Spielfeld sind werden sie in das HashSet
		// eingefuegt.
		if(isInRange(newmove) == true && isValidMove(newmove,color) == true)
			List.add(newmove);	
		}
		return List;
	}
	
	/** Gibt alle gueltigen Zuege einer bestimmten Farbe aus
	 * @param color Die gewuenschte Farbe
	 * @return gibt alle moeglichen Spielzuege 
	 * einer Farbe in einem HashSet aus Moves aus
	 */
	public THashSet<Move> getAllValidMoves(int color) throws IllegalArgumentException {
		if(color < 0 || color > 3)
			throw new IllegalArgumentException("Only values 0-3 allowed!");
		// Dieses Hashset soll alle gueltigen Moves enthalten
		THashSet<Move> ergebnis = new THashSet<Move>();
		Move possiblemoves = new Move();
		possiblemoves = getPossibleMoves(color);
		//Diese for schleife durchlaeuft die Positionen
		// aller potentieller Zuege
		for(Position pos : possiblemoves.getPolyomino()) {
			// Diese for Schleife durchlaeuft alle verbliebenen
			// Polyominos des HashSets der entsprechenden Farbe
			for(Polyomino poly : giveHashSet(color)) {
				// Falls das Polyomino einer der Polyominos entspricht,
				// die nicht gedreht oder gespiegelt werden muessen
				// (1, X und o)
				if(poly.getPolyID() == 1 || poly.getPolyID() == 19 || 
				   poly.getPolyID() == 8) {
					//Erzeuge das gleiche Poly in poly2
					Polyomino poly2 = new Polyomino(poly.getPolyID());
					//Fuege alle gueltigen 
					//verschobenen Zuege mit diesem Polyomino in
					//das Ergebnis HashSet ein.
					for(Move move: toPosition(poly2,pos,color))
						ergebnis.add(move);
				}
				//Falls das Polyomino einem der Polyiominos entspricht,
				//die nur einmal gedreht werden muessen
				else if(poly.getPolyID() == 2 || poly.getPolyID() == 3 ||
					    poly.getPolyID() == 4
					 || poly.getPolyID() == 5) {
					Polyomino poly2 = new Polyomino(poly.getPolyID());
					for(Move move: toPosition(poly2,pos,color))
						ergebnis.add(move);
					poly2.turn();
					for(Move move: toPosition(poly2,pos,color))
						ergebnis.add(move);
				}
				//Falls das Polyomino einem der Polyiominos entspricht,
				//die 3x Gedreht und 3x Gespiegelt und gedrehht werden
				//koennen entspricht
				else if(poly.getPolyID() == 12 || poly.getPolyID() == 7 ||
					    poly.getPolyID() == 17 || poly.getPolyID() == 6 ||
						poly.getPolyID() == 13 || poly.getPolyID() == 20 ||
						poly.getPolyID() == 14 || poly.getPolyID() == 11) {
					Polyomino poly2 = new Polyomino(poly.getPolyID());
					for(Move move: toPosition(poly2,pos,color))
						ergebnis.add(move);
					poly2.turn();
					for(Move move: toPosition(poly2,pos,color))
						ergebnis.add(move);
					poly2.turn();
					for(Move move: toPosition(poly2,pos,color))
						ergebnis.add(move);
					poly2.turn();
					for(Move move: toPosition(poly2,pos,color))
						ergebnis.add(move);
					poly2.mirror();
					for(Move move: toPosition(poly2,pos,color))
						ergebnis.add(move);
					poly2.turn();
					for(Move move: toPosition(poly2,pos,color))
						ergebnis.add(move);
					poly2.turn();
					for(Move move: toPosition(poly2,pos,color))
						ergebnis.add(move);
					poly2.turn();
					for(Move move: toPosition(poly2,pos,color))
						ergebnis.add(move);
				}
				//Falls das Polyomino einem der Polyiominos entspricht,
				//die 1x gedreht und 1x gespiegelt und gedreht werden koennen
				//entspricht.
				else if(poly.getPolyID() == 10 || poly.getPolyID() == 21) {
					Polyomino poly2 = new Polyomino(poly.getPolyID());
					for(Move move: toPosition(poly2,pos,color))
						ergebnis.add(move);
					poly2.turn();
					for(Move move: toPosition(poly2,pos,color))
						ergebnis.add(move);
					poly2.turn();
					poly2.mirror();
					for(Move move: toPosition(poly2,pos,color))
						ergebnis.add(move);
					poly2.turn();
					for(Move move: toPosition(poly2,pos,color))
						ergebnis.add(move);
				}
				//Falls das Polyomino einem der Polyiominos entspricht,
				//die 3x gedreht werden koennen, entspricht.
				else if(poly.getPolyID() == 15 || poly.getPolyID() == 16 ||
						poly.getPolyID() == 9 || poly.getPolyID() == 18) {
					Polyomino poly2 = new Polyomino(poly.getPolyID());
					for(Move move: toPosition(poly2,pos,color))
						ergebnis.add(move);
					poly2.turn();
					for(Move move: toPosition(poly2,pos,color))
						ergebnis.add(move);
					poly2.turn();
					for(Move move: toPosition(poly2,pos,color))
						ergebnis.add(move);
					poly2.turn();
					for(Move move: toPosition(poly2,pos,color))
						ergebnis.add(move);
				}
		 }
		}
		return ergebnis;
	}

	/** Gibt den Gewinner aus
	 * @return Gibt den Gewinner als int Wert aus
	 */
	public int getWinner() {
		if(getCurrentResult(BLUE) > getCurrentResult(YELLOW) && getCurrentResult(BLUE)
		 > getCurrentResult(RED) && getCurrentResult(BLUE) > getCurrentResult(GREEN))
			return BLUE;
		else if(getCurrentResult(YELLOW) > getCurrentResult(BLUE) &&
			    getCurrentResult(YELLOW) > getCurrentResult(RED) && getCurrentResult(YELLOW)
				 > getCurrentResult(GREEN)) 
			return YELLOW;
		else if(getCurrentResult(RED) > getCurrentResult(BLUE) && getCurrentResult(RED) > 
				getCurrentResult(YELLOW) && getCurrentResult(RED) > getCurrentResult(GREEN))
			return RED;
		else if(getCurrentResult(GREEN) > getCurrentResult(BLUE) && getCurrentResult(GREEN) 
				> getCurrentResult(YELLOW) && getCurrentResult(GREEN) > getCurrentResult(RED))
			return RED;
		else
			return NONE;
	}

	/** Versetzt einen Zug in die A1 Ecke.
	 * @param move Der Zug, der in die obere linke Ecke verschoben wird
	 * @return setzt den Zug in die obere linke Ecke, damit man mit Polyos vergleichen kann
	 */
	private Move toOriginal(Move move) {
		Move original = new Move();
		int s = 0;
		int l = -1;
		int n = -1;
		for(Position pos : move.getPolyomino()) {
		// speichert in l und n den Buchstaben und die Nummer
		// der ersten durchlaufenen Position 
		if(s == 0) {
			l = pos.getLetter();
			n = pos.getNumber();
		}
		// Vergleicht die im naechsten Durchlauf treffende Position
		// mit der in l und n gespeicherten Position, wenn diese kleiner ist
		// so setze l auf diesen Buchstaben
		// analog dazu mit der Nummer 
		if(pos.getLetter() < l) {
			l= pos.getLetter();
		}
		if(pos.getNumber() < n) {
			n= pos.getNumber();
		}
		// setze s auf 1, damit die erste If bedingung nicht mehr ausgefuehrt wird
		s = 1;
		}
		// speichere den ermittelten kleinsten Buchstaben und die kleinste Nummer in
		// einer Position 
		// Durchlaeuft den Zug erneut und berechnet die Differenz mit dem 
		// ermittelten Minimun und speichert diese Position in dem
		// auszugebenden Zug 
		for(Position pos : move.getPolyomino()) {
			Position pos2 = new Position(pos.getLetter()-l,pos.getNumber()-n);
			original.addPosition(pos2);
		}
		return original;
	}

	/** Gibt die verbleibenden verfuegbaren Polyiominos einer Farbe aus
	 * @param color die gewuenschte Farbe
	 * @return Rueckgabe des HashSet ueber die verbleibenden Polys der entsprechenden Farbe 
	 */
	public THashSet<Polyomino> giveHashSet(int color) throws IllegalArgumentException{
		if(color == BLUE)
			return blueMoves;
		else if(color == YELLOW)
			return yellowMoves;
		else if(color == RED)
			return redMoves;
		else if(color == GREEN)
			return greenMoves;
		else
			throw new IllegalArgumentException("Geben Sie int Werte zwischen 0-3 ein!");
	}
	/** Setzt die aktuelle Farbe auf einen anderen Wert
	 * @param color Gewuenschte Farbe
	 * Set Methode zum setzen der aktuellen Farbe auf eine andere
	 */
	private void setColor(int color){
		COLOR = color;
	}

	/** Prueft ob der Zug im Feld ist
	 * @param move Der Zug der ueberprueft werden soll
	 * @return prueft ob alle Positionen im Zug sich innerhalb des Spielfeldes befinden
	 */
	private boolean isInRange(Move move) {
		for(Position pos : move.getPolyomino())
			if(isInRange(pos) == false)
				return false;
		return true;
	}

	/** Prueft ob Position im Feld ist
	 * @param pos Das Position Element was ueberprueft werden soll
	 * @return Rueckgabe von true wenn sich die bestimmte Position im Spielfeld befindet
	 */
	private boolean isInRange(Position pos) {
		if(pos.getLetter() < 0 || pos.getLetter() > SIZE-1 || pos.getNumber() < 0 || 
		   pos.getNumber() > SIZE-1)
			return false;
		return true;
	}

	/** Prueft ob eine Position direkte Nachbarn hat
	 * @return Rueckgabe von true, falls alle direkten Nachbarn der Positionen im Zug nicht
	 * dieselbe Farbe haben
	 */
	private boolean NotSameNeighbours(Position pos,int color) {
		// Setze die Nachbarn in neue Position Elemente
		Position top = new Position(pos.getLetter(),pos.getNumber()-1);
		Position right = new Position(pos.getLetter()+1, pos.getNumber());
		Position left = new Position(pos.getLetter()-1,pos.getNumber());
		Position bottom = new Position(pos.getLetter(),pos.getNumber()+1);
		if(isInRange(top) == true && field[pos.getNumber()-1][pos.getLetter()] == color){

			return false;
		}
		else if(isInRange(right) == true && field[pos.getNumber()][pos.getLetter()+1] == color){

			return false;
		}
		else if(isInRange(left) == true && field[pos.getNumber()][pos.getLetter()-1] == color){

			return false;
		}
		else if(isInRange(bottom) == true && field[pos.getNumber()+1][pos.getLetter()] == color){

			return false;
		}

		return true;
	}

	/** Prueft ob eine Position einen diagonalen Nachbarn hat
	 * @return liefert true, falls an einer diagonalen ecke der Positionen
	 * eine gleiche Farbe vorkommt
	 */
	private boolean sameCornerNeighbours(Position pos,int color) {

		Position topright = new Position(pos.getLetter()+1,pos.getNumber()-1);
		Position bottomright = new Position(pos.getLetter()+1, pos.getNumber()+1);
		Position bottomleft = new Position(pos.getLetter()-1,pos.getNumber()+1);
		Position topleft = new Position(pos.getLetter()-1,pos.getNumber()-1);

		if(isInRange(topright) == true && field[pos.getNumber()-1][pos.getLetter()+1] == color)
			return true;
		else if(isInRange(bottomright) == true && field[pos.getNumber()+1][pos.getLetter()+1] == color)
			return true;
		else if(isInRange(bottomleft) == true && field[pos.getNumber()+1][pos.getLetter()-1] == color)
			return true;
		else if(isInRange(topleft) == true && field[pos.getNumber()-1][pos.getLetter()-1] == color)
			return true;

		return false;
	}

	/** Prueft ob eine Position der Startecke der bestimmten Farbe entspricht
	 * @param color gewuenschte Farbe
	 * @param pos die zu ueberpruefende Position
	 * @return Rueckgabe von true wenn Position Element abhaengig von der Farbe zur dazugehoerigen
	 * Ecke passt
	 */
	private boolean isCorner(int color, Position pos) {
		if(color == BLUE && pos.getLetter() == 0 && pos.getNumber() == 0)
			return true;
		else if (color == YELLOW && pos.getLetter() == SIZE-1 && pos.getNumber() == 0)
			return true;
		else if (color == RED && pos.getLetter() == SIZE-1 && pos.getNumber() == SIZE-1)
			return true;
		else if (color == GREEN && pos.getLetter() == 0 && pos.getNumber() == SIZE-1)
			return true;
		else
			return false;
	}

	/** Setzt die letzten gespielten Polyominos und entfernt ein bestimmtes Polyomino aus dem 
	 * HashSet
	 * @param move Der zu ueberpruefende Zug
	 * @param die gewuenschte Farbe
	 * @return liefert True, falls der angegebene Zug einem verfuegbaren Poly entspricht und
	 * entfernt ihn anschliessend aus dem HashSet
	 */
	private void setPolysAndRemove(int color, Polyomino poly2, Polyomino poly) {
		lastlastpoly = lastpoly;
		lastpoly = poly2;
		giveHashSet(color).remove(poly);
	}
	/** Prueft ob ein Zug ein verfuegbarer Stein ist.
	 * @param move Der zu ueberpruefende Zug
	 * @param color die gewuenschte Farbe
	 * @return liefert True, falls der angegebene Zug einem verfuegbaren Poly entspricht und
	 * entfernt ihn anschliessend aus dem HashSet
	 */
	private boolean isValidShape(Move move,int color) throws IllegalArgumentException {
		if (color < 0 || color > 3)
			throw new IllegalArgumentException("Only values 0-3 allowed!");
		// Der Zug wird in die obere Linke Ecke gesetzt*/
		move = toOriginal(move);
		// Durchlaeuft alle verbleibenden Polyominos
		// der entsprechenden Farbe und entfernt einen gueltigen aus
		// dem HashSet. Hier wird wie bei getAllValidMoves darauf
		// geachtet, dass nicht zu oft gedreht oder gespiegelt wird
		for(Polyomino poly : giveHashSet(color)) {
			if(poly.getPolyID() == 1 || poly.getPolyID() == 19 || poly.getPolyID() == 8) {
				Polyomino poly2 = new Polyomino(poly.getPolyID());
				if(poly2.equalsMove(move)){
					setPolysAndRemove(color,poly2,poly);
					return true;
				}
			}
			else if(poly.getPolyID() == 2 || poly.getPolyID() == 3 || poly.getPolyID() == 4 || poly.getPolyID() == 5) {
				Polyomino poly2 = new Polyomino(poly.getPolyID());
				if(poly2.equalsMove(move)){
					setPolysAndRemove(color,poly2,poly);
					return true;
				}
				poly2.turn();
				if(poly2.equalsMove(move)) {
					setPolysAndRemove(color,poly2,poly);
					return true;
				}
			}
			else if(poly.getPolyID() == 12 || poly.getPolyID() == 7 || poly.getPolyID() == 17|| poly.getPolyID() == 6 ||
					poly.getPolyID() == 13 || poly.getPolyID() == 20 || poly.getPolyID() == 14 || poly.getPolyID() == 11) {
				Polyomino poly2 = new Polyomino(poly.getPolyID());
				if(poly2.equalsMove(move)){
					setPolysAndRemove(color,poly2,poly);
					return true;
				}
				poly2.turn();
				if(poly2.equalsMove(move)) {
					setPolysAndRemove(color,poly2,poly);
					return true;
				}
				poly2.turn();
				if(poly2.equalsMove(move)) {
					setPolysAndRemove(color,poly2,poly);
					return true;
				}
				poly2.turn();
				if(poly2.equalsMove(move)) {
					setPolysAndRemove(color,poly2,poly);
					return true;
				}
				poly2.turn();
				poly2.mirror();
				if(poly2.equalsMove(move)) {
					setPolysAndRemove(color,poly2,poly);
					return true;
				}
				poly2.turn();
				if(poly2.equalsMove(move)) {
					setPolysAndRemove(color,poly2,poly);
					return true;
				}
				poly2.turn();
				if(poly2.equalsMove(move)) {
					setPolysAndRemove(color,poly2,poly);
					return true;
				}
				poly2.turn();
				if(poly2.equalsMove(move)) {
					setPolysAndRemove(color,poly2,poly);
					return true;
				}
			}
			else if(poly.getPolyID() == 10 || poly.getPolyID() == 21) {
				Polyomino poly2 = new Polyomino(poly.getPolyID());
				if(poly2.equalsMove(move)){
					setPolysAndRemove(color,poly2,poly);
					return true;
				}
				poly2.turn();
				if(poly2.equalsMove(move)){
					setPolysAndRemove(color,poly2,poly);
					return true;
				}
				poly2.turn();
				poly2.mirror();
				if(poly2.equalsMove(move)){
					setPolysAndRemove(color,poly2,poly);
					return true;
				}
				poly2.turn();
				if(poly2.equalsMove(move)){
					setPolysAndRemove(color,poly2,poly);
					return true;
				}
			}
			else if(poly.getPolyID() == 15 || poly.getPolyID() == 16 || poly.getPolyID() == 9 ||
					poly.getPolyID() == 18) {
				Polyomino poly2 = new Polyomino(poly.getPolyID());
				if(poly2.equalsMove(move)){
					setPolysAndRemove(color,poly2,poly);
					return true;
				}
				poly2.turn();
				if(poly2.equalsMove(move)){
					setPolysAndRemove(color,poly2,poly);
					return true;
				}
				poly2.turn();
				if(poly2.equalsMove(move)){
					setPolysAndRemove(color,poly2,poly);
					return true;
				}
				poly2.turn();
				if(poly2.equalsMove(move)){
					setPolysAndRemove(color,poly2,poly);
					return true;
				}
			}
		}
		return false;
	}

	/** Fuegt ein Polyomino wieder in den HashSet
	 * der verbliebenen Polyominos ein.
	 * @param poly das einzufuegende Polyomino
	 * @param color die entsprechende Farbe
	 * fuegt das Polyomino in das entsprechende	
	 * HashSet einer Farbe ein 
	 */
	public void addToShape(Polyomino poly, int color) {
		giveHashSet(color).add(poly);
	}
		
	/** Prueft ob ein Zug gueltig ist
	 * @param move der zu ueberpruefende Zug
	 * @param color die entsprechende Farbe
	 * @return isValidMove prueft ob ein Zug gueltig ist
	 */
	public boolean isValidMove(Move move,int color) throws IllegalArgumentException {
		if(color < 0 || color > 3)
			throw new IllegalArgumentException("This color is not valid! Only Values 0-3 allowed");
		// Ein leerer Zug soll auch gueltig sein
		if(move.equals(new Move()))
			return true;
		// Durchlaeuft die Positionen des Zuges 
		for(Position pos : move.getPolyomino())
			// falls das Feld an der Stelle der Position bereits mit einer Farbe besetzt ist
			// oder diese Position einen gleichen direkten Nachbarn hat gebe false aus
			if(field[pos.getNumber()][pos.getLetter()] != NONE || NotSameNeighbours(pos,color) == false)
				return false;	
		// Durchlaeuft Positionen des Zuges 
		for(Position pos : move.getPolyomino()){
			//Falls der Zug ein Position Element enthaelt 
			// das eine Startecke der aktuellen Farbe ist und 
			// die jeweilige Ecke im Spielfeld leer ist oder
			// diese Position einen direkten diagonalen Nachbarn hat liefere true
			if((isCorner(color,pos) == true && getFieldCorner(color) == NONE) || sameCornerNeighbours(pos,color) == true ){
				return true;
			}	
		}

		return false;	
	}
	/** Fuegt einen Zug in das Spielfeld ein
	 * @param mov der gewuenschte Zug
	 * fuegt ein Move Element in das Spielfeld ein
	 */
	private void addToField(Move mov) throws IllegalArgumentException {
		for(Position pos : mov.getPolyomino())
		field[pos.getNumber()][pos.getLetter()] = getColor();
	}
	/** Entfernt einen Zug aus dem Feld und setzt die Farben,
	 * Stati und Zuege zurueck.
	 * @param mov der gewuenschte Zug
	 * Entfernt den letzten Zug aus dem Feld
	 */
	public void deleteMove(Move mov) {
		// Wenn der zu loeschende Zug dem letzten Zug
		// entspricht 
		if(mov == lastMove) {
			// Durchlaeuft die Positionen des letzten
			// Zuges und setzt die Werte im Feld auf NONE 
			for(Position pos3 : lastMove.getPolyomino())
				field[pos3.getNumber()][pos3.getLetter()] = NONE;
			// Fuegt den geloeschten Zug als Polyomino
			// wieder in das HashSet der verbleibenden Polys ein
			addToShape(lastpoly,lastcolor);
			// die Farbe wird auf die letzte Farbe gesetzt,
			 // da diese ja jetzt wieder an der Reihe ist
			setColor(lastcolor);
			// Setzt die letzte Farbe auf die vorletzte,
			// falls direkt nach dem loeschen nochmal geloescht wird
			lastcolor = lastlastcolor;
			lastlastcolor = -13;
			// Analog zu der letzten Farbe
			lastpoly = lastlastpoly;
			lastlastpoly = null;
			lastMove = lastlastMove;
			lastlastMove = null;
			//lastStatus= lastlastStatus;
			//lastlastStatus.setScoreAll(1);		
			}
		else {
			throw new IllegalArgumentException("Dieser Zug ist kein letzter oder vorletzter Move!");
		}
	}


	/** Speichert die letzten gespielten Zuege in Klassenvariablen
	 * @param mov der gewuenschte Zug
	 * @param color die gewuenschte Farbe
	 * @param status Status nach gewuenschtem Zug
	 * Speichert den letzten und vorletzten Zug in
	 * Klassenvariable 
	 */
	public void saveLatestMoves(Move mov, int color, Status status) {
		lastlastcolor = lastcolor;
		lastcolor = color;
		lastlastMove = lastMove;
		lastMove = mov;
	}

	/**
	 * Methode addMove prueft ob ein eingelesener Zug gueltig ist,
	 * falls ja wird er in das Spielfeld eingesetzt
	 */
	public void addMove(Move mov) throws IllegalArgumentException {
		Move emptyMove = new Move();
		// Falls das Spiel zuende ist liefere Exception 
		if(gameStatus.isGameOver() == true){
			saveLatestMoves(mov,getColor(), getStatus());
		}
		// Falls der einzufuegende Zug leer ist, setze
		// die Farbe auf Pause und rufe nextColor() auf
		else if(mov == null || mov.equals(emptyMove)) {
			gameStatus.setScore(getColor(),1);
			// speichere letzte Farbe, letzten Status und letzten Zug
			saveLatestMoves(mov,getColor(), getStatus());
			nextColor();
		}
		// Falls der Zug gueltig ist fuege in Feld ein und rufe nextcolor auf
		else if(isValidMove(mov,getColor()) == true && isInRange(mov) == true
			    && isValidShape(mov,getColor()) == true) {	
			addToField(mov);
			saveLatestMoves(mov,getColor(), getStatus());
			nextColor();
		}
		// Falls der Zug ungueltig ist oder nicht im Spielfeld oder das Polyomino
		// nicht verfuegbar ist setze Status auf Error und werfe Exception
		else if(isValidMove(mov,getColor()) == false || isInRange(mov) == false ||
				isValidShape(mov,getColor()) == false) {
			throw new IllegalArgumentException("Illegal Move!" + mov + "" + 
			getAllValidMoves(getColor()));	
		}
	}
	/** Gibt die Farbe nach der aktuellen Farbe aus
	 * @param color gewuenschte Farbe
	 * @return Gibt die Farbe nach der angegebenen Farbe wieder
	 */
	public int nextColor(int color) throws IllegalArgumentException {
		if(color < 0 || color > 3)
			throw new IllegalArgumentException(
			"This color is not valid! OnlyValues 0-3 are allowed");	
		return (color+1)%4;
	}
	/**
	 * Setzt die Reihenfolge der Farben fort - abhaengig von pausierenden Farben
	 */
	private void nextColor() throws IllegalArgumentException{
		// Wenn eine Farbe keine gueltigen Zuege mehr machen kann
		// so setze Status auf Pause 
		for(int i = 0; i < 4; i++)
		if(getAllValidMoves(i).size() == 0){
			gameStatus.setScore(i,PAUSE);
			lastlastStatus = lastStatus;
			lastStatus = getStatus();
		
		}
		
		// Falls die naechsten drei Farben pausieren, so ueberspringe sie
		if(gameStatus.getScore((getColor()+1)%4) == 1 && gameStatus.getScore((getColor()+2)%4) == 1 &&
			gameStatus.getScore((getColor()+3)%4) == 1)  {
			setColor((getColor()+4)%4);
			return;
		}

		// wenn die 2 naechsten Farben pausieren,ueberspringe sie
		if(gameStatus.getScore((getColor()+1)%4) == 1 && gameStatus.getScore((getColor()+2)%4) == 1)  {

			setColor((getColor()+3)%4);
			return;
		}
		// Wenn eine naechste Farbe pausiert, ueberspringe sie
		if(gameStatus.getScore((getColor()+1)%4) == 1 ) {

			setColor((getColor()+2)%4);
			return;
		}
			//Ansonsten setze Farbe auf naechste
			setColor((getColor()+1)%4);	
	}

	/** Speichert die 21 Polyominos in die entsprechenden HashSets fuer die Farben
	 * @param Liste HashSet wo die 21 Polyominos gespeichert werden sollen
	 * Elementen
	 */
	private void savePolysToHashSet(THashSet<Polyomino> Liste) {

		Polyomino Polyomino1 = new Polyomino(1);
		Polyomino Polyomino2 = new Polyomino(2);
		Polyomino Polyomino3 = new Polyomino(3);
		Polyomino Polyomino4 = new Polyomino(4);
		Polyomino Polyomino5 = new Polyomino(5);
		Polyomino Polyomino6 = new Polyomino(6);
		Polyomino Polyomino7 = new Polyomino(7);
		Polyomino Polyomino8 = new Polyomino(8);
		Polyomino Polyomino9 = new Polyomino(9);
		Polyomino Polyomino10 = new Polyomino(10);
		Polyomino Polyomino11 = new Polyomino(11);
		Polyomino Polyomino12 = new Polyomino(12);
		Polyomino Polyomino13 = new Polyomino(13);
		Polyomino Polyomino14 = new Polyomino(14);
		Polyomino Polyomino15 = new Polyomino(15);
		Polyomino Polyomino16 = new Polyomino(16);
		Polyomino Polyomino17 = new Polyomino(17);
		Polyomino Polyomino18 = new Polyomino(18);
		Polyomino Polyomino19 = new Polyomino(19);
		Polyomino Polyomino20 = new Polyomino(20);
		Polyomino Polyomino21 = new Polyomino(21);
		Liste.add(Polyomino1);
		Liste.add(Polyomino2);
		Liste.add(Polyomino3);
		Liste.add(Polyomino4);
		Liste.add(Polyomino5);
		Liste.add(Polyomino6);
		Liste.add(Polyomino7);
		Liste.add(Polyomino8);
		Liste.add(Polyomino9);
		Liste.add(Polyomino10);
		Liste.add(Polyomino11);
		Liste.add(Polyomino12);
		Liste.add(Polyomino13);
		Liste.add(Polyomino14);
		Liste.add(Polyomino15);
		Liste.add(Polyomino16);
		Liste.add(Polyomino17);
		Liste.add(Polyomino18);
		Liste.add(Polyomino19);
		Liste.add(Polyomino20);
		Liste.add(Polyomino21);
	}
	/** Gibt eine Stringrepraesentation des Spielfeldes aus
	 * @return Gibt das entsprechende Spielfeld in einem String aus
	 */
	@Override
	public String toString() {
		String ausg="\t";
		for(int i = 'A'; i < 'A' + getSize(); i++){
				ausg = ausg + (char) i + " ";
		
			}
			ausg = ausg + "\n\n";

			for(int i = 0; i < getSize(); i++){
				ausg = ausg + (i+1) + "\t";
				for(int j = 0; j < getSize(); j++){
					if(getFieldEntry(i,j) == 4)
						ausg = ausg + "-" + " ";
					else
					ausg = ausg + getFieldEntry(i,j) + " ";
				}
				ausg = ausg + "\n";
			}
		return ausg;
	}

	/** Kopiert ein Spielfeld
	 * @return gibt das kopierte Spielbrett aus
	 */
	public Board cloneBoard() {
		Board newboard = new Board(getSize());
		// Durchlaueft die Feldelemente des neuen Spielbretts
		// und setzt sie auf die Werte des Originals 
		for(int i = 0; i < getSize(); i++) 
			for(int j = 0; j < getSize(); j++) 
					newboard.field[i][j] = getFieldEntry(i,j);
		// Die aktuelle Farbe des neuen Bretts
		// wird auf die des Originals gesetzt
		newboard.COLOR = getColor();
		// Der Status des neuen Bretts
		// wird auf die des Originals gesetzt
		newboard.setStatus(new Status(getStatus()));
		// Die verbleibenden Polyominos des Originals werden
		// auf das neue Brett kopiert 
		newboard.blueMoves = new THashSet<Polyomino>();
		Iterator<Polyomino> it = blueMoves.iterator();
		while(it.hasNext()){
			newboard.blueMoves.add(it.next());
		}
		it = redMoves.iterator();
		newboard.redMoves = new THashSet<Polyomino>();
		while(it.hasNext()){
			newboard.redMoves.add(it.next());
		}
		it = yellowMoves.iterator();
		newboard.yellowMoves = new THashSet<Polyomino>();
		while(it.hasNext()){
			newboard.yellowMoves.add(it.next());
		}
		it = greenMoves.iterator();
		newboard.greenMoves = new THashSet<Polyomino>();
		while(it.hasNext()){
			newboard.greenMoves.add(it.next());
		}
		return newboard;	
	}
}
