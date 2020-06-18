// O. Bittel;
// 19.03.2018

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.Set;

/**
 * Implementierung von DirectedGraph mit einer doppelten TreeMap 
 * für die Nachfolgerknoten und einer einer doppelten TreeMap 
 * für die Vorgängerknoten. 
 * <p>
 * Beachte: V muss vom Typ Comparable&lt;V&gt; sein.
 * <p>
 * Entspicht einer Adjazenzlisten-Implementierung 
 * mit schnellem Zugriff auf die Knoten.
 * @author Oliver Bittel
 * @since 19.03.2018
 * @param <V> Knotentyp.
 */
public class AdjacencyListDirectedGraph<V> implements DirectedGraph<V> {
    // doppelte Map für die Nachfolgerknoten:
    private final Map<V, Map<V, Double>> succ = new TreeMap<>(); 
    
    // doppelte Map für die Vorgängerknoten:
    private final Map<V, Map<V, Double>> pred = new TreeMap<>(); 

    private int numberEdge = 0;

	/**
	 * Fügt neuen Knoten zum Graph dazu.
	 * @param v Knoten
	 * @return true, falls Knoten noch nicht vorhanden war.
	 */
	@Override
	public boolean addVertex(V v) {
		if(!containsVertex(v)) {
			pred.put(v, new TreeMap<>());
			succ.put(v, new TreeMap<>());
			return true;
		}else{
			return false;
		}
    }

	/**
	 * Fügt neue Kante mit Gewicht weight zum Graph dazu.
	 * Falls einer der beiden Knoten noch nicht im Graphen vorhanden ist,
	 * dann wird er dazugefügt.
	 * Falls die Kante schon vorhanden ist, dann wird das Gewicht
	 * mit weight überschrieben.
	 * @param v Startknoten
	 * @param w Zielknoten
	 * @param weight Gewicht
	 * @return true, falls Kante noch nicht vorhanden war.
	 */
    @Override
    public boolean addEdge(V v, V w, double weight) {

		addVertex(v);
		addVertex(w);

		if (!containsEdge(v, w)) {
			succ.get(v).put(w, weight);
			pred.get(w).put(v, weight);
			numberEdge++;
			return true;
		}

		succ.get(v).put(w, weight);
		return false;
    }

	/**
	 * Fügt neue Kante (mit Gewicht 1) zum Graph dazu.
	 * Falls einer der beiden Knoten noch nicht im Graphen vorhanden ist,
	 * dann wird er dazugefügt.
	 * Falls die Kante schon vorhanden ist, dann wird das Gewicht
	 * mit 1 überschrieben.
	 * @param v Startknoten
	 * @param w Zielknoten
	 * @return true, falls Kante noch nicht vorhanden war.
	 */
    @Override
    public boolean addEdge(V v, V w) {

    		addVertex(v);
    		addVertex(w);

			pred.get(v).put(w,1.0);
			succ.get(w).put(v,1.0);
			numberEdge++;
			return true;
    }
	/**
	 * Prüft ob Knoten v im Graph vorhanden ist.
	 * @param v Knoten
	 * @return true, falls Knoten vorhanden ist.
	 */
    @Override
    public boolean containsVertex(V v) {
		if(pred.containsKey(v) && succ.containsKey(v)){
			return true;
		}else{
			return false;
		}
    }

	/**
	 * Prüft ob Kante im Graph vorhanden ist.
	 * @param v Startknoten
	 * @param w Endknoten
	 * @return true, falls Kante vorhanden ist.
	 */
    @Override
    public boolean containsEdge(V v, V w) {
    	if(pred.get(v).containsKey(w) && succ.get(w).containsKey(v)){
    		return true;
		}else{
    		return false;
		}
    }

	/**
	 * Liefert Gewicht der Kante zurück.
	 * @param v Startknoten
	 * @param w Endknoten
	 * @throws IllegalArgumentException falls die Kante nicht existiert.
	 * @return Gewicht der Kante.
	 */
    @Override
		public double getWeight(V v, V w) {
    	if(!containsEdge(v,w)){
    		throw new IllegalArgumentException();
		}
       return pred.get(v).get(w);
    }

	/**
	 * Liefert Eingangsgrad des Knotens v zurück.
	 * Das ist die Anzahl der Kanten mit Zielknoten v.
	 * @param v Knoten
	 * @throws IllegalArgumentException falls Knoten v
	 * nicht im Graph vorhanden ist.
	 * @return Knoteneingangsgrad
	 */
    @Override
    public int getInDegree(V v) {
		return succ.get(v).size();
    }

	/**
	 * Liefert Ausgangsgrad des Knotens v zurück.
	 * Das ist die Anzahl der Kanten mit Quellknoten v.
	 * @param v Knoten
	 * @throws IllegalArgumentException falls Knoten v
	 * nicht im Graph vorhanden ist.
	 * @return Knotenausgangsgrad
	 */
    @Override
    public int getOutDegree(V v) {
		return pred.get(v).size();
    }

	/**
	 * Liefert eine nicht modifizierbare Sicht (unmodifiable view)
	 * auf die Menge aller Knoten im Graph zurück.
	 *
	 * @return Knotenmenge
	 */
	@Override
    public Set<V> getVertexSet() {
		return Collections.unmodifiableSet(succ.keySet()); // nicht modifizierbare Sicht
    }

	/**
	 * Liefert eine nicht modifizierbare Sicht (unmodifiable view) auf
	 * die Menge aller Vorgängerknoten von v zurück.
	 * Das sind alle die Knoten, von denen eine Kante zu v führt.
	 * @param v Knoten
	 * @throws IllegalArgumentException falls Knoten v
	 * nicht im Graph vorhanden ist.
	 * @return Knotenmenge
	 */
    @Override
    public Set<V> getPredecessorVertexSet(V v) {
		return Collections.unmodifiableSet(succ.get(v).keySet());
    }

	/**
	 * Liefert eine nicht modifizierbare Sicht (unmodifiable view) auf
	 * die Menge aller Nachfolgerknoten von v zurück.
	 * Das sind alle die Knoten, zu denen eine Kante von v führt.
	 * @param v Knoten
	 * @throws IllegalArgumentException falls Knoten v
	 * nicht im Graph vorhanden ist.
	 * @return Knotenmenge
	 */
    @Override
    public Set<V> getSuccessorVertexSet(V v) {
		return Collections.unmodifiableSet(pred.get(v).keySet());
    }

	/**
	 * Liefert Anzahl der Knoten im Graph zurück.
	 * @return Knotenzahl.
	 */
    @Override
    public int getNumberOfVertexes() {
		return succ.size();
    }

	/**
	 * Liefert Anzahl der Kanten im Graph zurück.
	 * @return Kantenzahl.
	 */
    @Override
    public int getNumberOfEdges() {
		return numberEdge;
    }

	/**
	 * Erzeugt einen invertierten Graphen,
	 * indem jede Kante dieses Graphens in umgekehrter Richtung abgespeichert wird.
	 * @return invertierter Graph
	 */
	@Override
    public 
	DirectedGraph<V> invert() {
		DirectedGraph<V> invert = new AdjacencyListDirectedGraph<>();

		for (var e: this.pred.keySet()) {
			for (var d: pred.get(e).keySet()) {
				invert.addEdge( d, e);
			}
		}

		return invert;
	}

	
	@Override
	public String toString() {
		for (var e: pred.keySet()) {
			for (var d: pred.get(e).entrySet())
				System.out.println(e + " --> " + d.getKey() + " weight = " + d.getValue());
		}

		return "";
	}
	
	
	public static void main(String[] args) {
		DirectedGraph<Integer> g = new AdjacencyListDirectedGraph<>();
		g.addEdge(1,2);
		g.addEdge(2,5);
		g.addEdge(5,1);
		g.addEdge(2,6);
		g.addEdge(3,7);
		g.addEdge(4,3);
		g.addEdge(4,6);
		g.addEdge(7,4);
		
		
		System.out.println(g.getNumberOfVertexes());	// 7
		System.out.println(g.getNumberOfEdges());		// 8
		System.out.println(g.getVertexSet());	// 1, 2, ..., 7
		System.out.println(g);
			// 1 --> 2 weight = 1.0 
			// 2 --> 5 weight = 1.0
			// 2 --> 6 weight = 1.0
			// 3 --> 7 weight = 1.0
			// ...
		
		System.out.println("");
		System.out.println(g.getOutDegree(2));				// 2
		System.out.println(g.getSuccessorVertexSet(2));	// 5, 6
		System.out.println(g.getInDegree(6));				// 2
		System.out.println(g.getPredecessorVertexSet(6));	// 2, 4
		
		System.out.println("");
		System.out.println(g.containsEdge(1,2));	// true
		System.out.println(g.containsEdge(2,1));	// false
		System.out.println(g.getWeight(1,2));	// 1.0	
		g.addEdge(1, 2, 5.0);
		System.out.println(g.getWeight(1,2));	// 5.0	
		
		System.out.println("");
		System.out.println(g.invert());
			// 1 --> 5 weight = 1.0
			// 2 --> 1 weight = 1.0
			// 3 --> 4 weight = 1.0 
			// 4 --> 7 weight = 1.0
			// ...
			
		Set<Integer> s = g.getSuccessorVertexSet(2);
		System.out.println(s);
		//s.remove(5);	// Laufzeitfehler! Warum?
	}
}
