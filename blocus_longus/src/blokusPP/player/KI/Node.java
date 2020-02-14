package blokusPP.player.KI;

import java.util.*;
import gnu.trove.set.hash.THashSet;
import blokusPP.field.*; 
import blokusPP.preset.*;
import blokusPP.player.*;

/** 
  * Klasse Node zum Erstellen von Knoten fuer den MoveTree.
  * @author Hanna Holderied
*/
public class Node{
	/** Anzahl der Farben im Spiel */
	static final int GAME_COLORS = 4;
	/** Move data speichert den Move, der zu dem aktuellen Board gefuehrt hat */
	private Move data;
	/** Node parent speichert Referenz aufden Elternknoten des aktuellen Knotens */
	private Node parent;
	/** ArrayList<Node> speichert eine ArrayList mit allen moeglichen Zuegen, 
	 *	die ausgehend von dem aktuellen Board noch gezogen werden koennen */
	private ArrayList<Node> children;
	/** score speichert den ermittelten Score fuer den Knoten */
	private float score;
	/** totalScore speichert den berechneten Score eines Blattes (aus den Scores ermittelt) */
	private float totalScore;
	/** visited speichert fuer die Breitensuche in MoveTree, ob ein Knoten schon besucht wurde */
	private boolean visited;
	/** possibleMoves[] speichert die Anzahl der moeglichen Zuege fuer jede Farbe ausgehend vom 
	 *	aktuellen Knoten */
	private int possibleMoves[];
	/** color speichert, welche Farbe gerade am Zug ist */
	private int color;
	/** polyomino speichert die Anzahl der Steine in einem Polyomino */
	private int polyomino;
	/** board speichert das aktuelle Board */
	private Board board;
	/** iPlayer speichert eine Referenz auf den iPlayer, der die Knoten erstellt */
	private IntelligentPlayer iPlayer;

	/** 
	 *	Getter fuer data
	 *	@return data
	 */
	public Move getData(){
		return data;
	}
	/** 
	 *	Setter fuer data
	 *	@param dat data wird auf dat gesetzt 
	 */
	public void setData(Move dat){
		data = dat;
	}


	/** 
	 *	Getter fuer parent
	 *	@return parent
	 */
	public Node getParent(){
		return parent;
	}
	/** 
	 *	Setter fuer parent
	 *	@param nod parent wird auf nod gesetzt 
	 */
	public void setParent(Node nod){
		parent = nod;
	}


	/** 
	 *	Getter fuer children[position]
	 * 	@param position Gibt die Stelle an, von der aus der children ArrayList gelesen wird
	 *	@return children[position]
	 */
	public Node getChild(int position){
		return children.get(position);
	}
	/** 
	 *	Hinzufuegen eines Knotens nod in das children
	 *	@param nod Neuer Knoten, der children hinzugefuegt wird
	 */
	public void addChild(Node nod){
		children.add(nod);
	}
	/** 
	 *	Getter fuer children
	 *	@return children
	 */
	public ArrayList<Node> getChildrenArray(){
		return children;
	}


	/** 
	 *	Getter fuer score
	 *	@return score
	 */
	public float getScore(){
		return score;
	}
	/** 
	 *	Setter fuer score
	 *	@param sc score wird auf sc gesetzt 
	 */
	public void setScore(float sc){
		score = sc;
	}


	/** 
	 *	Getter fuer totalScore
	 *	@return totalScore
	 */
	public float getTotalScore(){
		return totalScore;
	}
	/** 
	 *	Setter fuer totalScore
	 *	@param sc totalScore wird auf sc gesetzt 
	 */
	public void setTotalScore(float sc){
		totalScore = sc;
	}


	/** 
	 *	Getter fuer visited
	 *	@return visited
	 */
	public boolean getVisited(){
		return visited;
	}
	/** 
	 *	Setter fuer visited
	 *	@param visit visited wird auf visit gesetzt 
	 */
	public void setVisited(boolean visit){
		visited = visit;
	}


	/** 
	 *	Getter fuer possibleMoves[]
	 *	@return possibleMoves[]
	 */
	public int getPossibleMoves(int i){
		return possibleMoves[i];
	}
	/** 
	 *	Setter fuer possibleMoves[i] auf moves
	 *	@param i Index fuer Stelle in possibleMoves[], die auf moves gesetzt wird
	 *	@param moves Anzahl der moves, auf die possibleMoves[i] gesetzt wird
	 */
	public void setPossibleMoves(int i, int moves){
		possibleMoves[i] = moves;
	}


	/** 
	 *	Getter fuer color
	 *	@return color
	 */
	public int getColor(){
		return color;
	}
	/** 
	 *	Setter fuer color
	 *	@param col color wird auf col gesetzt 
	 */
	public void setColor(int col){
		color = col;
	}


	/** 
	 *	Getter fuer polyomino
	 *	@return polyomino
	 */
	public int getPolyomino(){
		return polyomino;
	}
	/** 
	 *	Setter fuer polyomino
	 *	@param size polyomino wird auf size gesetzt 
	 */
	public void setPolyomino(int size){
		polyomino = size;
	}


	/** 
	 *	Getter fuer board
	 *	@return board
	 */
	public Board getBoard(){
		return board;
	}
	/** 
	 *	Setter fuer board
	 *	@param brd board wird auf dat gesetzt 
	 */
	public void setBoard(Board brd){
		board = brd;
	}

	
	/** 
	 *	Getter fuer iPlayer
	 *	@return iPlayer
	 */
	public IntelligentPlayer getiPlayer(){
		return iPlayer;
	}


	/** 
	 *	Konstruktor Node(IntelligentPlayer) setzt alle Attribute auf default-Werte,
	 *	nur iPlayer wird auf den uebergebenen iPlayer gesetzt
	 *	@param iPlayer IntelligentPlayer, auf den der iPlayer des neuen Objekts gesetzt wird
	 */
	public Node(IntelligentPlayer iPlayer){
		data = null;
		parent = null;
		children = new ArrayList<Node>();
		score = 0;
		totalScore = 0;
		visited = false;
		polyomino = 0;
		possibleMoves = new int[GAME_COLORS];		
		this.iPlayer = iPlayer;
		board = iPlayer.getBoard();
		color = board.getColor();
	}

	/**	
	 *	Konstruktor Node(Node, Node) ist ein Kopier-Konstruktor, der einen neuen Knoten
	 *	anhand der Attribute des uebergebenen Knotens erzeugt
	 *	@param node	Knoten, von dem eine beinahe identische Kopie erzeugt werden soll
	 *	@param parent Knoten, auf den parent des neuen Objekts gesetzt werden soll
	 */
	public Node(Node node, Node parent){
		data = node.getData();
		this.parent = parent;
		children = new ArrayList<Node>();
		for(int i = 0; i < node.getChildrenArray().size(); i++){
			addChild(node.getChildrenArray().get(i));
		}
		score = node.getScore();
		totalScore = node.getTotalScore();
		visited = node.getVisited();
		polyomino = node.getPolyomino();
		possibleMoves = new int[GAME_COLORS];
		for(int i = 0; i < GAME_COLORS; i++){
			possibleMoves[i] = node.possibleMoves[i];
		}
		iPlayer = node.getiPlayer();
		board = iPlayer.getBoard();
		color = board.getColor();

	}


	//Konstruktor zum Setzen von data
	/**	
 	 *	Konstruktor Node(Move, Node, boolean) zum Erzeugen eines Knotens, dessen 
	 *	Attribute data und parent auf die uebergebenen Atrribute gesetzt werden
	 *	@param data Setzt data des neuen Knotens auf data
	 *	@param parent Setzt parent des neuen Knotens auf parent
	 *	@param firstIteration Zeigt an, ob der Knoten das Kind(true) oder 
	 *		   KindesKind(false) ist
	 */
	public Node(Move data, Node parent, boolean firstIteration){
		visited = false;
		this.data = data;
		this.parent = parent;
		this.iPlayer = parent.getiPlayer();
		board = parent.getBoard().cloneBoard();

		boolean ownColor = false;
		for(int i = 0; i < iPlayer.getColorsSize(); i++){
			if(board.getColor() == iPlayer.getColor(i)){
				ownColor = true;
			}
		}
		children = new ArrayList<Node>();
		board.addMove(data);
		color = board.getColor();
		possibleMoves = new int[GAME_COLORS];
		//Setzen der possibleMoves durch die Methode calculateMoves
		calculatePossibleMoves();
		score = preCalculateScore(ownColor);
		//In jedem Kindeskind wird der totalScore berechnet (also in jedem Blatt), 
		//indem der Score von dem Blatt von dem des Parent-Nodes abgezogen wird
		//Hintergrund: der eigene Score soll maximiert werden, der Gegner maximiert
		// seinen Score ebenso, also muss nach dem Minimax-Prinzip am Ende der Zug
		// ausgewaehlt werden, der einen hohen totalScore (eigene Zuege minus 
		//gegnerische Zuege) hat 
		if(!firstIteration){
			if(ownColor){
				totalScore = parent.getScore() + getScore();
			}
			else{
				totalScore = parent.getScore() - getScore();
			}
		}
	}


	/**	
	 *	Methode add(THashSet<Move>) fuegt an den Knoten, mit dem sie aufgerufen wird,
	 *	jedes Element aus dem uebergebenen THashSet<Move> als Kind ein.
	 *	Vorgehen: Zuerst werden die Kinder (alle moeglichen Zuege desKnotens) 
	 *	eingefuegt (uebergebener Parameter)
	 *	Diese Kinder werden sortiert nach ihrem Score, der in dem Konstruktor berechnet wird
	 *	Es werden nur die Kinder mit den besten Scores behalten
	 *	Fuer die uebrigen Kinder werden die Kindeskinder (die naechsten moeglichen Zuege) 
	 *	bestimmt und eingespeichert
	 *	Nur das beste Kindeskind wird gespeichert
	 *	@param mov Ein THashSet<Move>, dessen Elemente als Kinder des aufrufenden Objekts 
	 *	eingefuegt werden
	 */
	public void add(THashSet<Move> mov){
		//possibleMoves berechnen
		if(getPossibleMoves(0) == 0 || getPossibleMoves(1) == 0 || getPossibleMoves(2) == 0 || getPossibleMoves(3) == 0){
			calculatePossibleMoves();
		}
		//Iterator ueber alle moeglichen Zuege, die als Kinder in den Baum eingefuegt werden
		Iterator<Move> it = mov.iterator();
		while(it.hasNext()){
			addChild(new Node(it.next(), this, true));
		}

		//Innere Klasse Comparator<Node>
		//Ermoeglicht das Sortieren von Nodes mithilfe eines Comparator-Objekts
		final Comparator<Node> comparator = new Comparator<Node>(){
			//Die Methode compare wird ueberschrieben, darin wird angegeben, welche Objekte 
			//verglichen werden sollen
			@Override
			public int compare(Node node1, Node node2){
				//Neue Float-Objekte werden erzeugt, da compareTo(Object) mit Typ Objekten 
				//(nicht-primitiven Datentypen) 
				//aufgerufen werden muss
				Float scoreNode1 = new Float(node1.getScore());
				Float scoreNode2 = new Float(node2.getScore());
				//da absteigende Reihenfolge der zu sortierenden Liste gewuenscht: den 
				//zweiten Node mit dem ersten vergleichen
				return scoreNode2.compareTo(scoreNode1);
			}
		};
		Collections.sort(getChildrenArray(), comparator);

		//Anzahl der Kinder beschraenken, damit der Baum nicht zu gross wird
		//uebergebener Parameter steht fuer die Anzahl der Kinder, die noch erhalten bleiben sollen
		deleteChildren(2);

		//For-Schleife fuer das Hinzufuegen der Kindeskinder des Root-Nodes
		for(int i = 0; i < getChildrenArray().size(); i++){
			Node child = getChild(i);
			//Fuer jedes Kind die moeglichen naechsten Kinder (Kindeskinder) in einem 
			//THashSet zwischenspeichern
			THashSet<Move> moves = child.getBoard().getAllValidMoves(child.getBoard().getColor());
	        Iterator<Move> itOpponent = moves.iterator();
			//max speichert den maximalen Score, den eines der Kindeskinder erreicht
			float max = Float.NEGATIVE_INFINITY;
			//maxNode speichert den Knoten (Kindeskind) mit dem maximalen Score
			Node maxNode = null;
			//tempNode zum temporaeren Zwischenspeichern des maxNodes
			Node tempNode = null;
    	    while(itOpponent.hasNext()){
        	    Move temp = itOpponent.next();
				//das Kindeskind wird als neuer Knoten eingespeichert
				child.addChild(new Node(temp, child, false));
				//Wenn das Kindeskind einen Score groesser als max hat, werden max und 
				//tempNode aktualisiert
				if(child.getChildrenArray().get(child.getChildrenArray().size() -1).getScore() > max){
					max = child.getChildrenArray().get(child.getChildrenArray().size() -1).getScore();
					tempNode = child.getChildrenArray().get(child.getChildrenArray().size() -1);
				}
    	    }
			if(tempNode != null) {
				//in maxNode den tempNode kopieren (neuen Knoten erstellen)
				maxNode = new Node(tempNode, child);
				//den gesamten ChildrenArray loeschen
				child.getChildrenArray().clear();
				//als einziges Kind den maxNode einfuegen
				child.getChildrenArray().add(maxNode);
			}
		}
	}


	/**	
	 *	Methode deleteChildren(int) loescht alle Kinder/Knoten bis auf die ersten int 
	 *	Knoten aus children
	 *	@param number Anzahl der Kinder, die uebrig bleiben sollen
	 */
	private void deleteChildren(int number){
		ArrayList<Node> tempList = new ArrayList<Node>();
		if(getChildrenArray().size() > number){
			for(int i = 0; i < number; i++){
				tempList.add(getChild(i));
			}
			getChildrenArray().clear();
			for(int i = 0; i < number; i++){
				getChildrenArray().add(tempList.get(i));
			}
		}
	}

	
	/**	
	 *	Methode calculatePossibleMoves(), die das Attribut possibleMoves[] fuer jede Farbe
	 *	mit der Anzahl der moeglichen Zuege setzt
	 */
	public void calculatePossibleMoves(){
		for(int i = 0; i < Setting.GAME_COLORS; i++){
			setPossibleMoves(i, getBoard().getAllValidMoves(i).size());
		}
	}


	/**
	 *	Methode preCalculateScore(boolean) bestimmt die Werte, mit denen die Methode 
	 *	calculateScore(int, int, int) aufgerufen wird
	 *	@param ownColor Gibt an, ob die Farbe des zu berechnenden Knoten zu der Farbe 
	 *		   des Spielers gehoert, fuer den der Zug ermittelt werden soll
	 *	@return gibt den ermittelten Score zurueck
	 */
	public float preCalculateScore(boolean ownColor){
		int parentMovesOpponent = 0;
		int parentMovesOwn = 0;
		int movesOpponent = 0;
		int movesOwn = 0;

		//factorAttackOwnColor der verhindert, dass sich eigene Farben nicht zu sehr 
		//selbst behindern
		double factorAttackOwnColor = 1.5;


		if(ownColor){
			//parent- und MovesOpponent berechnen: fuer jede gegnerische Farbe die Anzahl 
			//der Zuege addieren
			for(int i = 0; i < iPlayer.getOpponentColorsSize(); i++){
				parentMovesOpponent += getParent().getPossibleMoves(iPlayer.getOpponentColor(i));
				movesOpponent += getPossibleMoves(iPlayer.getOpponentColor(i));
			}
			//parent- und MovesOwn berechnen: fuer jede gegnerische Farbe die Anzahl 
			//der Zuege addieren
			for(int i = 0; i < iPlayer.getColorsSize(); i++){
				parentMovesOwn += getParent().getPossibleMoves(iPlayer.getColor(i));
			}
			//Damit sich die eigenen Farben auch gegenseitig attackieren (nicht komplett 
			//aufeinander Ruecksicht nehmen und keinen Zug spielen, der einer anderen 
			//eigenen Farbe Zuege blockiert), wird ownMoves nicht in der Schleife berechnet,
			//sondern ausserhalb nur fuer die aktuell spielende Farbe
			//Damit sich die Farben jedoch nicht zu sehr blockieren, wird der Wert mit 
			//factorAttackColor multipliziert
			movesOwn += factorAttackOwnColor * getPossibleMoves(parent.getBoard().getColor());
			float score = calculateScore(movesOpponent - parentMovesOpponent, movesOwn 
												- parentMovesOwn, data.getPolyomino().size());
			return score;
		}


		//In der naechsten Iteration sind gegnerische und eigene Zuege vertauscht
		else{
			//Auch beim Gegner kann man nicht davon ausgehen, dass sich die Spielzuege 
			//gar nicht aneinander annaehern
			//Ausserdem soll, wenn der Gegner nach dem getesteten Zug keinen einzigen Zug
			// mehr uebrig hat, dieser getestete Zug gespielt werden, da er eine Farbe aus 
			//dem Spiel wirft
			//Auch wenn der Gegner nur noch eine Position hat, an die er anlegen koennte, 
			//soll der Zug gespielt werden, der dies bewirkt


			//das else darf nur einmal durchlaufen werden, da nur die eigene Farbe in die 
			//movesOwn mit reingezaehlt werden soll
			boolean elseDone = false;
			for(int i = 0; i < iPlayer.getOpponentColorsSize(); i++){
            	parentMovesOwn += parent.getPossibleMoves(iPlayer.getOpponentColor(i));
				if((getPossibleMoves(iPlayer.getOpponentColor(i)) ==  0 || 
				board.getPossibleMoves(iPlayer.getOpponentColor(i)).getPolyomino().size() == 1) 
				&& board.getStatus().getScore(iPlayer.getOpponentColor(i)) == 0){
					movesOwn -= 100000;
				}
				else if(elseDone == false){
					movesOwn += factorAttackOwnColor * getPossibleMoves(parent.getBoard().getColor());
					elseDone = true;
				}
	        }

			for(int i = 0; i < iPlayer.getColorsSize(); i++){
    	        parentMovesOpponent += parent.getPossibleMoves(iPlayer.getColor(i));
				if((getPossibleMoves(iPlayer.getColor(i)) == 0 || 
				board.getPossibleMoves(iPlayer.getColor(i)).getPolyomino().size() == 1) && 
				board.getStatus().getScore(iPlayer.getColor(i)) == 0){
					movesOpponent -= 100000;
				}
				else{
					movesOpponent += getPossibleMoves(iPlayer.getColor(i));
				}
	        }
			float score = calculateScore(movesOpponent - parentMovesOpponent, movesOwn - 
											parentMovesOwn, data.getPolyomino().size());
			return score;
		}
	}

	/**	
	 *	Methode calculateScore(int, int, int), die einen Score ermittelt fuer einen Knoten.
	 *	Jeder uebergebene Parameter wird mit einem Faktor multipliziert, diese aendern 
	 *	sich je nach Rundenzahl und Schwierigkeitsgrad
	 *	Somit kann die KI auf verschiedene Spielphasen unterschiedlich reagieren 
	 *	(aggressiv, zurueckhaltender etc)
	 *	@param opponent Anzahl der gegnerischen Zuege (aktuell gegnerische Zuege minus 
	 *		   gegnerische Zuege der Eltern) jeweils aller eigenen Farben
	 *	@param self Anzahl der eigenen Zuege (aktuell eigene Zuege minus eigene Zuege der Eltern) 
	 *	@param polyominoSize Groesse des Polyominos, der einen Zug ausfuehrt
	 *	@return Der jeweils ermittelte Score fuer einen Zug
	 */	
	public float calculateScore(int opponent, int self, int polyominoSize){
		float score = 0;

		score += (opponent * -getiPlayer().getFactorBlock());
		score += (self * getiPlayer().getFactorTurns());
		score += (polyominoSize * getiPlayer().getFactorSize());

		return score;
	}


	/**
	 *	Methode getTurn() ermittelt den Knoten, der zu dem Knoten mit dem hoechsten Score fuehrte
	 *	Dafuer wird der Baum "hochgelaufen", bis die Eltern oder die Grosseltern null sind
	 *	@return Der Move des Knotens, der zu dem Blatt mit dem hoechsten Score fuehrte
	 */
	public Move getTurn(){
		Node actualMove = this;
		while(actualMove.getParent() != null){
			if(actualMove.getParent().getParent() == null){
				break;
			}
			actualMove = actualMove.getParent();
		}
		return actualMove.getData();
	}


	/**
	 *	Ueberschriebene Methode toString()
	 *  @return Gibt die String-Repraesentation eines Knotens zurueck
	 */
	@Override
	public String toString(){
		String output = "Node::toString\n\tKnoten: " +  getData() + "\n";
		if(getParent() == null){
			output += "\tParent: null\n";
		}
		if(getParent() != null){
			output += "\tParent Data: " + getParent().getData() + "\n";
		}
		if(getChildrenArray().isEmpty()){
			output += "\tChildren: empty";
		}
		output += "Score: " + getScore();
		output += "\n";
		return output;
	}
}

