// O. Bittel;
// 28.02.2019

package scotlandYard;

import sim.SYSimulation;
import java.util.*;
/**
 * Kürzeste Wege in Graphen mit A*- und Dijkstra-Verfahren.
 * @author Oliver Bittel
 * @since 27.01.2015
 * @param <V> Knotentyp.
 */
public class ShortestPath<V> {
	
	SYSimulation sim = null;
	
	Map<V,Double> dist; // Distanz für jeden Knoten
	Map<V,V> pred; // Vorgänger für jeden Knoten
	V start;
	V ziel;
	DirectedGraph<V> graph;
	Heuristic<V> heuristic;

	/**
	 * Konstruiert ein Objekt, das im Graph g k&uuml;rzeste Wege 
	 * nach dem A*-Verfahren berechnen kann.
	 * Die Heuristik h schätzt die Kosten zwischen zwei Knoten ab.
	 * Wird h = null gewählt, dann ist das Verfahren identisch 
	 * mit dem Dijkstra-Verfahren.
	 * @param g Gerichteter Graph
	 * @param h Heuristik. Falls h == null, werden kürzeste Wege nach
	 * dem Dijkstra-Verfahren gesucht.
	 */
	public ShortestPath(DirectedGraph<V> g, Heuristic<V> h) {
		graph = g;
		heuristic = h;
		dist = new TreeMap<>();
		pred = new TreeMap<>();
	}

	/**
	 * Diese Methode sollte nur verwendet werden, 
	 * wenn kürzeste Wege in Scotland-Yard-Plan gesucht werden.
	 * Es ist dann ein Objekt für die Scotland-Yard-Simulation zu übergeben.
	 * <p>
	 * Ein typische Aufruf für ein SYSimulation-Objekt sim sieht wie folgt aus:
	 * <p><blockquote><pre>
	 *    if (sim != null)
	 *       sim.visitStation((Integer) v, Color.blue);
	 * </pre></blockquote>
	 * @param sim SYSimulation-Objekt.
	 */
	public void setSimulator(SYSimulation sim) {
		this.sim = sim;
	}

	/**
	 * Sucht den kürzesten Weg von Starknoten s zum Zielknoten g.
	 * <p>
	 * Falls die Simulation mit setSimulator(sim) aktiviert wurde, wird der Knoten,
	 * der als nächstes aus der Kandidatenliste besucht wird, animiert.
	 * @param s Startknoten
	 * @param g Zielknoten
	 */
	public void searchShortestPath(V s, V g) {
		shortestPath(s, g, graph, dist, pred);
	}

	private boolean shortestPath(V s, V g, DirectedGraph<V> graph, Map<V, Double> dist, Map<V, V> pred) {
		LinkedList<V> k1 = new LinkedList<>();

		start = s;
		ziel = g;

		for (V v: graph.getVertexSet()) {
			dist.put(v, Double.POSITIVE_INFINITY);
			pred.put(v, null);
		}

		dist.put(s, 0.0);
		k1.add(s);

		while (!k1.isEmpty()) {
			V v = s;
			double minimalDistance = Double.POSITIVE_INFINITY;

			for (var m : k1) {
				if (heuristic == null) {
					if (dist.get(m) < minimalDistance) {
						minimalDistance = dist.get(m);
						v = m;
					}
				} else {
					if ((dist.get(v) + heuristic.estimatedCost(v, g)) < minimalDistance) {
						minimalDistance = dist.get(v) + heuristic.estimatedCost(v, g);
						v = m;
					}
					if (m.equals(g)) {
						k1.add(m);
						return true;
					}
				}
			}

			k1.remove(v);
			V v2 = v;

			k1.add(v);

			for (V w: graph.getSuccessorVertexSet(v2)) {
				if (dist.get(w) == Double.POSITIVE_INFINITY)
					k1.add(w);
				if (dist.get(v2) + graph.getWeight(v2, w) < dist.get(w)) {
					pred.put(w, v2);
					dist.put(w, dist.get(v) + graph.getWeight(v, w));
				}
			}
		}
		return false;
	}

	/**
	 * Liefert einen kürzesten Weg von Startknoten s nach Zielknoten g.
	 * Setzt eine erfolgreiche Suche von searchShortestPath(s,g) voraus.
	 * @throws IllegalArgumentException falls kein kürzester Weg berechnet wurde.
	 * @return kürzester Weg als Liste von Knoten.
	 */
	public List<V> getShortestPath() {
		List<V> order = new LinkedList<>();
		order.add(ziel);
		V tmp = ziel;
		while (!tmp.equals(start)) {
			tmp = pred.get(tmp);
			order.add(tmp);
		}
		Collections.reverse(order);
		return order;
	}

	/**
	 * Liefert die Länge eines kürzesten Weges von Startknoten s nach Zielknoten g zurück.
	 * Setzt eine erfolgreiche Suche von searchShortestPath(s,g) voraus.
	 * @throws IllegalArgumentException falls kein kürzester Weg berechnet wurde.
	 * @return Länge eines kürzesten Weges.
	 */
	public double getDistance() {
		return dist.get(ziel);
	}

}
