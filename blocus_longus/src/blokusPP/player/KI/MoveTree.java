package blokusPP.player.KI;

import java.util.*;
import blokusPP.preset.*;
import java.util.concurrent.*;
import blokusPP.player.*;

/**
 *	Klasse MoveTree zum Erstellen von Baeumen, die die moeglichen Spielzuege 
 *	enthalten Funktionen zum Blattes mit dem Spielzugs (dem besten Score) 
 *	und zum Updaten des Root-Nodes
 * 	@author Hanna Holderied
 */	
public class MoveTree{
	/** Referenz auf den iPlayer, von dem aus der MoveTree erstellt wurde */
	private IntelligentPlayer iPlayer;

	/** Root-Node des MoveTrees */
	private Node root;

	/**
	 *	Getter fuer den Root-Node
	 * 	@return RootNode root des Objekts MoveTree, mit dem die Funktion aufgerufen wurde
	 */
	public Node getRoot(){
		return root;
	}
	/**	
	 *	Setter des RootNodes auf den uebergebenen Knoten node
	 *  @param node setzt den Root-Node root auf node
	 */
	public void setRoot(Node node){
		root = node;
	}


	/** 
	 *	Konstruktor fuer einen MoveTree, der als Parameter einen IntelligentPlayer 
	 *	uebergeben bekommt
	 * 	@param player Attribut iPlayer wird auf player gesetzt
	 */
	public MoveTree(IntelligentPlayer player){
		//Als Root-Node wird ein neuer Knoten erzeugt, der ebenfalls den player 
		//uebergeben bekommt
		root = new Node(player);
		iPlayer = player;
	}


	/** 
	 *	Methode updateRootNode(Move) setzt einen neuen Root-Node, nachdem ein Zug 
	 *	(vom Gegner oder von einem selbst) gespielt wurde
	 *	@param mov soll der neue Root-Node werden
	 */	
	public void updateRootNode(Move mov){
		//Variable rootNodeUpdated gibt an, ob der uebergebene Move in dem Baum gefunden wurde
		boolean rootNodeUpdated = false;
		//for-Schleife ueber die Kinder des Root-Nodes
		for(int i = 0; i < getRoot().getChildrenArray().size(); i++){
			//mov gefunden, wenn ein Kindeskind (ist ein Blatt) des Root-Nodes gleich dem mov ist
			//dieses Kindeskind wird dann als neuer Root gesetzt
			if(getRoot().getChildrenArray().get(i).getData().equals(mov)){	
				//Kopier-Konstruktor von Node aufgerufen, um den gefundenen Knoten zu kopieren
				//Die Eltern des neuen RootNodes werden auf "null" gesetzt, damit der Garbage 
				//Collector effektiver arbeiten kann
				root = new Node(getRoot().getChildrenArray().get(i), null);
				//alle Kinder werden aus dem ChildrenArray des Root-Nodes geloescht
				getRoot().getChildrenArray().clear();
				//fuer den RootNode wird calculateMoves() aufgerufen
				rootNodeUpdated = true;
			}
		}
		//falls mov nicht im Baum enthalten, wird ein neuer Root-Node erstellt
		if(!rootNodeUpdated){
			root = new Node(iPlayer);
		}
	}


	/**
	 * 	Methode selectBestLeaf() ruft die Methode selectBestLeaf(Node) auf, die 
	 *	das Blatt mit dem besten Score ermittelt
	 * 	@return	Blatt mit dem besten Score
	 */	
	public Node selectBestLeaf(){
		return selectBestLeaf(getRoot());
	}

	/** 
	 *  Methode selectBestLeaf(Node) durchsucht den MoveTree mithilfe einer 
	 *	Breitensuche, um das Blatt mit dem besten Score zu ermitteln
	 *  @param nod der Knoten, von dem aus alle Kinder bis zu ihren Blaettern 
	 *		   durchsucht werden sollen
	 *  @return der Knoten mit dem besten Score
	 */
	private Node selectBestLeaf(Node nod){
		//maxScore zum Speichern des bis dahin maximal gefundenen Scores
		//maxScore mit dem minimal moeglichen Wert fuer Float belegen, damit die 
		//gefundenen Scores groesser sind als der Anfangswert
		float maxScore = Float.NEGATIVE_INFINITY;
		//maxNode zum Speichern des Knotens mit dem bis dahin besten Score
		Node maxNode = null;

		//queue ist eine Queue zum Speichern der noch nicht besuchten Knoten des Baums
		ConcurrentLinkedQueue<Node> queue = new ConcurrentLinkedQueue<Node>();

		//Alle Knoten wieder auf visited == false setzen
		//Grund: Ziel ist, einen Teilbaum fuer den naechsten Zug wieder zu verwenden, 
		//dafuer muessen die visited-Werte zurueck gesetzt werden
		setNodesToUnvisited(nod);
	
		//Aktuellen Knoten in die Queue einfuegen und visited = true setzen
		queue.add(nod);
		nod.setVisited(true);
		//Solange noch Knoten in der Queue: einen Knoten ausgeben und dessen Kindknoten 
		//in die Queue hinzufuegen
		while(!(queue.isEmpty())){
			Node poppedNode = queue.poll();
			//Wenn ein Blatt erreicht ist: Ueberpruefen, ob ein neuer MaxScore erreicht wurde
			if(poppedNode.getChildrenArray().isEmpty()){ 
				//Wenn neuer MaxScore: maxScore und maxNode aktualisieren
				if(maxScore < poppedNode.getTotalScore()){
					maxScore = poppedNode.getTotalScore();
					maxNode = poppedNode;
				}
			}
			//Alle Knoten hinzufuegen in die Queue hinzufuegen, die noch nicht besucht wurden
			for(int i = 0; i < poppedNode.getChildrenArray().size(); i++){
				if(!(poppedNode.getChild(i).getVisited())){
					queue.add(poppedNode.getChild(i));
					poppedNode.getChild(i).setVisited(true);
				}
			}
		}
		return maxNode;
	} 



	/** 
	 *	Methode setNodesToUnvisited(Node) setzt alle Knoten des Baums ab dem ueber-
	 *	gebenen Knoten auf visited = false.
	 *	@param nod Knoten, ab dem alle Knoten auf visited = false gesetzt werden
	 */
	private void setNodesToUnvisited(Node nod){
		Stack<Node> stack = new Stack<Node>();
		for(int i = 0; i < nod.getChildrenArray().size(); i++){
			stack.push(nod.getChild(i));
		}
		while(!(stack.empty())){
			Node poppedNode = stack.pop();
			if(poppedNode.getVisited()){
				poppedNode.setVisited(false);
			}
			setNodesToUnvisited(poppedNode);
		}
	}
}
