// O. Bittel;
// 28.02.2019

package shortestPath;


import shortestPath.Aufgabe2.*;
import sim.SYSimulation;

import java.sql.SQLOutput;
import java.util.*;
// ...

/**
 * Kürzeste Wege in Graphen mit A*- und Dijkstra-Verfahren.
 *
 * @param <V> Knotentyp.
 * @author Oliver Bittel
 * @since 27.01.2015
 */
public class ShortestPath<V> {

    SYSimulation sim = null;

    Map<V, Double> dist; // Distanz für jeden Knoten
    Map<V, V> pred; // Vorgänger für jeden Knoten

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
     *
     * @param g Gerichteter Graph
     * @param h Heuristik. Falls h == null, werden kürzeste Wege nach
     *          dem Dijkstra-Verfahren gesucht.
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
     *
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
     *
     * @param s Startknoten
     * @param g Zielknoten
     */
    public void searchShortestPath(V s, V g) {
        // ...
        start = s;
        ziel = g;

        for (var v : graph.getVertexSet()) {
            dist.put(v, Double.POSITIVE_INFINITY);
            pred.put(v, null);
        }

        LinkedList<V> kl = new LinkedList<>();
        dist.replace(s, 0.0);
        kl.add(s);

        while (!kl.isEmpty()) {
            V v = null;
            double min = Double.POSITIVE_INFINITY;
            if(heuristic == null) { // DIJKSTRA
                for (var m : kl) {
                    if (min > dist.get(m)) {
                        min = dist.get(m);
                        v = m;
                    }
                }
            } else {                // A*
                for (var m : kl) {
                    if (min > dist.get(m) + heuristic.estimatedCost(m, g)) {
                        min = dist.get(m) + heuristic.estimatedCost(m, g);
                        v = m;
                    }
                }
            }
            System.out.println(v);
            kl.remove(v);

            if(sim != null){
                sim.visitStation((int) v);
            }

            assert v != null;
            if(v.equals(g)) {
                return;
            }

            for(var w : graph.getSuccessorVertexSet(v)){
                if(dist.get(w) == Double.POSITIVE_INFINITY) {
                    kl.add(w);
                }
                if(dist.get(v) + graph.getWeight(v, w) < dist.get(w)){
                    pred.put(w, v);
                    dist.put(w, dist.get(v) + graph.getWeight(v, w));
                }
            }
        }
    }

    /**
     * Liefert einen kürzesten Weg von Startknoten s nach Zielknoten g.
     * Setzt eine erfolgreiche Suche von searchShortestPath(s,g) voraus.
     *
     * @return kürzester Weg als Liste von Knoten.
     * @throws IllegalArgumentException falls kein kürzester Weg berechnet wurde.
     */
    public List<V> getShortestPath() {
        List<V> order = new LinkedList<>();
        order.add(ziel);
        V tmp = ziel;
        while(!tmp.equals(start)){
            tmp = pred.get(tmp);
            order.add(tmp);
        }
        Collections.reverse(order);
        return order;
    }

    /**
     * Liefert die Länge eines kürzesten Weges von Startknoten s nach Zielknoten g zurück.
     * Setzt eine erfolgreiche Suche von searchShortestPath(s,g) voraus.
     *
     * @return Länge eines kürzesten Weges.
     * @throws IllegalArgumentException falls kein kürzester Weg berechnet wurde.
     */
    public double getDistance() {

        return dist.get(ziel);
    }

}
