package blokusPP.poly;
import blokusPP.preset.*;
import java.util.HashSet;
import java.util.Iterator;
import java.lang.Exception;
import java.util.ArrayList;

/**Klasse zur Darstellung und Veraenderung eines Polyominos, wie in der Aufgabenstellung beschrieben.Es gibt 21 verschiedene Figuren.
*@author Clara Holzhueter*/
public class Polyomino extends Move{
	
	/** Anzahl der Steine, die ein Polyomino besitzt.*/
	int posNumber = 0;	
	
	/**Anzahl der Polyominos, die noch verfuegbar(noch nicht gesetzt sind). Am Anfang auf 21 gesetzt*/
	int polyNumber = 21;
	
	/**Identifikation eines Polyominos, wie in der Projektbeschreibung( 1 bis 5, verschiedene Buchstaben).Default ist 0(kein Polyomino).*/
	int polyID = 0;
	
	
	/**Names des Polyominos, wie er in der Projektbeschreibung steht*/
	char polyName = '0';
	
	/**Liste aller noch verfuegbaren Polyominos(wird mit default-Konstruktor initialisiert).*/
	ArrayList<Polyomino> polyList = new ArrayList<Polyomino>();


	/** Konstruktor der polyList initialisiert und jedes Polyomino erzegut*/
	public Polyomino(){
		for (int i = 1; i <= polyNumber; i++){
			polyList.add(new Polyomino(i));
		}
	}
	
	/*Konstruktor, der das zur Nummer(1 bis 21) gehoerige Polyomino erzeugt. Die Koordinaten sind ausgehend von Eckke (0,0)
	*@param polyNumber Nummer der zu erzeugenden Figur( von 1 bis 21)
*/
	public Polyomino(int polyNumber) throws IllegalArgumentException{
                super();
		if( polyNumber <= 0 || polyNumber > 21){
			throw new IllegalArgumentException(" Nur Zahl zwischen 1 und 21 erlaubt!");
		}
 
		Position[] pos = new Position[5];
		//Position (0,0) ist default-maessig gesetzt, da die meisten Polyominos diese Position beinhalten.
		pos[0] = new Position( 0,0);
		polyID = polyNumber;
		
		switch (polyNumber){
			case 1:
				posNumber = 1;
				polyName = '1';
				break;
				
			case 2: pos[1] = new Position(0,1);
				posNumber = 2;
				polyName = '2';
				break;

			case 3: pos[1] = new Position(0,1);
				pos[2] = new Position(0,2);
				posNumber = 3;
				polyName = '3';
				break;
				
			case 4: pos[1] = new Position(0,1);
				pos[2] = new Position(0,2);
				pos[3] = new Position(0,3);
				posNumber = 4;
				polyName = '4';
				break;
				
			case 5: pos[1] = new Position(0,1);
				pos[2] = new Position(0,2);
				pos[3] = new Position(0,3);
				pos[4] = new Position(0,4);
				posNumber = 5;
				polyName = '5';
				break;
				
			case 6: pos[1] = new Position(0,1);
				pos[2] = new Position(1,1);
				posNumber = 3;
				polyName = 'v';
				break;

			case 7: pos[1] = new Position(0,1);
				pos[2] = new Position(0,2);
				pos[3] = new Position(1,2);
				posNumber = 4;
				polyName = 'l';
				break;
				
			case 8: pos[1] = new Position(0,1);
				pos[2] = new Position(1,0);
				pos[3] = new Position(1,1);
				posNumber = 4;
				polyName = 'o';
				break;
				
			case 9: pos[1] = new Position(1,0);
				pos[2] = new Position(2,0);
				pos[3] = new Position(1,1);
				posNumber = 4;
				polyName = 't';
				break;
				
			case 10: pos[1] = new Position(1,0);
				pos[2] = new Position(1,1);
				pos[3] = new Position(2,1);
				posNumber = 4;
				polyName = 'z';
				break;
				
			case 11: pos[0] = new Position(1,0);
				pos[1] = new Position(2,0);
				pos[2] = new Position(0,1);
				pos[3] = new Position(1,1);
				pos[4] = new Position(1,2);
				posNumber = 5;
				polyName = 'F';
				break;
				
			case 12: pos[1] = new Position(0,1);
				pos[2] = new Position(0,2);
				pos[3] = new Position(0,3);
				pos[4] = new Position(1,3);
				posNumber = 5;
				polyName = 'L';
				break;
				
			case 13: pos[0] = new Position(1,0);
				pos[1] = new Position(0,1);
				pos[2] = new Position(0,2);
				pos[3] = new Position(0,3);
				pos[4] = new Position(1,1);
				posNumber = 5;
				polyName = 'N';
				break;
				
			case 14: pos[1] = new Position(1,0);
				pos[2] = new Position(0,1);
				pos[3] = new Position(1,1);
				pos[4] = new Position(0,2);
				posNumber = 5;
				polyName = 'P';
				break;
				
			case 15: pos[1] = new Position(1,0);
				pos[2] = new Position(2,0);
				pos[3] = new Position(1,1);
				pos[4] = new Position(1,2);
				posNumber = 5;
				polyName = 'T';
				break;

				
			case 16: pos[1] = new Position(2,0);
				pos[2] = new Position(0,1);
				pos[3] = new Position(1,1);
				pos[4] = new Position(2,1);
				posNumber = 5;
				polyName = 'U';
				break;
				
			case 17: pos[1] = new Position(0,1);
				pos[2] = new Position(0,2);
				pos[3] = new Position(1,2);
				pos[4] = new Position(2,2);
				posNumber = 5;
				polyName = 'V';
				break;
				
			case 18: pos[1] = new Position(0,1);
				pos[2] = new Position(1,1);
				pos[3] = new Position(1,2);
				pos[4] = new Position(2, 2);
				posNumber = 5;
				polyName = 'W';
				break;
				
			case 19: pos[0] = new Position(0,1);
				pos[1] = new Position(1,0);
				pos[2] = new Position(1,1);
				pos[3] = new Position(2,1);
				pos[4] = new Position(1,2);
				posNumber = 5;
				polyName = 'X';
				break;
				
			case 20: pos[0] = new Position(0,1);
				pos[1] = new Position(1,0);
				pos[2] = new Position(1,1);
				pos[3] = new Position(1,2);
				pos[4] = new Position(1,3);
				posNumber = 5;
				polyName = 'Y';
				break;
				
			case 21: pos[1] = new Position(1,0);
				pos[2] = new Position(1,1);
				pos[3] = new Position(1,2);
				pos[4] = new Position(2,2);
				posNumber = 5;
				polyName = 'Z';
				break;

			default: posNumber = 0;
		}
//Eintragen der ausgewaehlten Positionen ind das Polyomino.
		for(int i = 0; i< posNumber ; i++){
			super.getPolyomino().add( pos[i]);
		}
	}
	/* Objektmethode die ein Polyomino spiegelt. Spiegelachse ist die y-Achse bzw die seitliche Spielbrettkante.letter wird auf -letter abgebildet.
*/
	public void mirror(){

		HashSet<Position> polyomino = this.getPolyomino();
		Iterator<Position> it =  polyomino.iterator();
		Position[] tmp = new Position[this.posNumber];
		Position tmpPos;
		int x;
		int i = 0;
		//spiegeln
		while( it.hasNext()){
			tmpPos = it.next();
			x = tmpPos.getLetter();
			x = x * (-1);
			tmp[i] = new Position(x, tmpPos.getNumber());
			i++;
		}polyomino.clear();
		
		moveIntoBoard(tmp);

		for( int k = 0; k< tmp.length; k++){
			polyomino.add( tmp[k]);
		}
	}
	/** Objektmethode zum Drehen eines Polyominos im Uhrzeigersinn. Letter wird auf -number und number auf letter abgebildet.
	*/
        public void turn(){
                HashSet<Position> polyomino = this.getPolyomino();
                Iterator<Position> it =  this.getPolyomino().iterator();
                Position[] tmp = new Position[this.posNumber];
                Position tmpPos;
                int x;
                int y;
                int i = 0;

			//Drehen.
                while( it.hasNext()){
                        tmpPos = it.next();
                        x = tmpPos.getLetter();
                        y = tmpPos.getNumber();
                        y = y* (-1);
                        tmp[i] = new Position(y, x);
                        i++;
                }polyomino.clear();
      		//verschieben in das Spielbrett
                moveIntoBoard(tmp);

		  for( int k = 0; k< tmp.length; k++){
                        polyomino.add( tmp[k]);

                }

        }

	/**Methode die ein Polyomino nach dem Drehen oder Spiegeln in das Spielfeld reinschiebt
	*@param tmp Array mit den Positionen, die verschoben werden sollen*/
	private void moveIntoBoard(Position[] tmp){
		
		int min = 0;
		int letter;
		int number;
		//zunaechst wird die kleinste x-Koordinate(Letter) gesucht
          for( int j = 0; j< tmp.length - 1; j++){
			if(tmp[j+1].getLetter() < min){
  				min = tmp[j+1].getLetter();
               }
       	}
       	
       	// Dann wird der Stein um min verschoben
        	if(min < 0){
 			min = min *(-1);
 			for(int j = 0; j < tmp.length; j++){
     			letter = tmp[j].getLetter();
          		letter = letter + min;
       			number = tmp[j].getNumber();
             		tmp[j] = (new Position(letter,number));
      		}

		}

	
	}
        
	/**ToString-Methode zu Test-Ausgabe der Superklasse
	*@return Stringrepaesentation eins Polyominos*/
	public String toString(){
		String s = "" + polyName;
		return s;
	}

	
	/** equals-Methode zum vergelichen zweier Polyominos. Es wird ueber die PolyID verglichen
	*@param poly zu vergleichendes Polyomino*/
	public boolean equals(Polyomino poly){
		if( poly == null){
			return false;
		}
		
		if( poly.polyID == this.polyID){
			return true;
		}
		
		return false;
	}
	
	
	/** Methode zum vergleichen eines Polyominos mit einem Move-Objekt
	*@param move zu Vergleichendes Move Objekt
	*/
	public boolean equalsMove(Move move){
		HashSet<Position> moveSet = move.getPolyomino();
		HashSet<Position> polySet = this.getPolyomino();
		if( moveSet.size() != polySet.size()){
			return false;
		}
		Position tmp;
		Iterator<Position> it = polySet.iterator();
		//Es wird kontrolliert, ob das POlyomino die gleichen Positionen, wie der Zug besitzt
		while(it.hasNext()){
			tmp = it.next();
			if( moveSet.contains(tmp) == false){
				return false;
			}
		}return true;
	
	}
	
	/**Get-Methode fuer PolyNumber
	*@return die Nummber des Polyominos*/
	public int getPolyNumber(){
		return polyNumber;
	}
	/**Methode, die die Poly-Identifikationsnummer zurueckgibt*/
	public int getPolyID(){
		return polyID;
	}
	/**Methode, die die Anzahl der Steine eines Polyominos zurueckgibt
	*@return posNumber Anzahl der Steine, die ein Polyomino enthaelt */
	public int getPosNumber(){
		return posNumber;	
	}
	
	/**Methode, die den Polomino-Namen, wie er in der Projektbeschreibung ist, zurueckgibt
	*@return polyName Name de Polyominos*/
	public char getPolyName(){
		return polyName;
	}
	/**Methode, die die Liste der noch verfuegbaren Polyominos zurueckgibt
	*@return polyList Liste der noc verfuegbaren Polys*/
	public ArrayList<Polyomino> getRemainingPolys(){
		return polyList;
	}
	/**Methode zum Entfernen eines Polyominos auf der Liste der noch verfuegbaren Polyominos
	*@param poly das zu loeschende Polyomino*/
	public void removePoly(Polyomino poly){
		boolean removed = polyList.remove(poly);
		polyNumber --;
	
	}

   private static final long serialVersionUID = 1L;
}
