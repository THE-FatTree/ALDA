package shortestPath.Aufgabe2;// O. Bittel;
// 22.02.2017

import java.util.*;

/**
 * Klasse zur Erstellung einer topologischen Sortierung.
 * @author Oliver Bittel
 * @since 22.02.2017
 * @param <V> Knotentyp.
 */
public class TopologicalSort<V> {
    private List<V> ts = new LinkedList<>(); // topologisch sortierte Folge
	// ...

	/**
	 * Führt eine topologische Sortierung für g durch.
	 * @param g gerichteter Graph.
	 */
	public TopologicalSort(DirectedGraph<V> g) {
		topSort(g);
	}

	public List<V> topSort(DirectedGraph<V> g) {

		Map<V, Integer> inDeg = new TreeMap<>();
		Queue<V> q = new LinkedList<>();

		for (V v : g.getVertexSet()) {
			inDeg.put(v, g.getInDegree(v));
			if (inDeg.get(v) == 0)
				q.add(v);
		}
		while(!q.isEmpty()) {
			V v = q.remove();
			ts.add(v);
			for (V w : g.getSuccessorVertexSet(v)) {
				inDeg.put(w, inDeg.get(w) - 1);
				if (inDeg.get(w) == 0)
					q.add(w);
			}
		}
		if (ts.size() != g.getNumberOfVertexes())
			return null;
		else
			return ts;
	}

	/**
	 * Liefert eine nicht modifizierbare Liste (unmodifiable view) zurück,
	 * die topologisch sortiert ist.
	 * @return topologisch sortierte Liste
	 */
	public List<V> topologicalSortedList() {
        return Collections.unmodifiableList(ts);
    }
    

	public static void main(String[] args) {
		DirectedGraph<Integer> g = new AdjacencyListDirectedGraph<>();
		DirectedGraph<String> w = new AdjacencyListDirectedGraph<>();
		g.addEdge(1, 2);
		g.addEdge(2, 3);
		g.addEdge(3, 4);
		g.addEdge(3, 5);
		g.addEdge(4, 6);
		g.addEdge(5, 6);
		g.addEdge(6, 7);
		//g.addEdge(7,1);
		System.out.println(g);

		w.addEdge("Unterhose","Hose");
		w.addEdge("Hose","Gürtel");
		w.addEdge("Hose","Schuhe");
		w.addEdge("Socken","Schuhe");
		w.addEdge("Schuhe","Handschuhe");
		w.addEdge("Pulli","Mantel");
		w.addEdge("Mantel","Schal");
		w.addEdge("Gürtel","Mantel");
		w.addEdge("Unterhemd","Hemd");
		w.addEdge("Hemd","Pulli");
		w.addEdge("Schal","Handschuhe");
		w.addEdge("Mütze","Handschuhe");



		System.out.println(w);

		TopologicalSort<Integer> ts = new TopologicalSort<>(g);
		TopologicalSort<String> ws = new TopologicalSort<>(w);

		if (ts.topologicalSortedList() != null) {
			System.out.println(ts.topologicalSortedList()); // [1, 2, 3, 4, 5, 6, 7]
		}

		if(ws.topologicalSortedList()!= null){
			System.out.println(ws.topologicalSortedList());
		}

		// Hose darf nur mit einem Schal angezogen werden
		w.addEdge("Schal", "Hose");
		TopologicalSort<String> ws2 = new TopologicalSort<>(w);
		if(ws.topologicalSortedList()!= null){
			System.out.println(ws2.topologicalSortedList());
		}

	}
}
