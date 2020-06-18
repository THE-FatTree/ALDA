// O. Bittel;
// 22.02.2017

import java.util.*;

/**
 * Klasse für Tiefensuche.
 *
 * @author Oliver Bittel
 * @since 22.02.2017
 * @param <V> Knotentyp.
 */
public class DepthFirstOrder<V> {

    private final List<V> preOrder = new LinkedList<>();
    private final List<V> postOrder = new LinkedList<>();
    private final DirectedGraph<V> myGraph;
    private int numberOfDFTrees = 0;
	// ...
    Set<V> besucht = new TreeSet<>();
    Set<V> a = new HashSet<>();

    List<V> b;
    Map<Integer,Set<V>> comp = new TreeMap<>();

    /**
     * Führt eine Tiefensuche für g durch.
     *
     * @param g gerichteter Graph.
     */
    public DepthFirstOrder(DirectedGraph<V> g) {
        myGraph = g;

        //Startobjekte erzeugen.
        a = myGraph.getVertexSet();
        b = new LinkedList<>(a);

        /*Für jedes Objekt dass noch nicht in der BesucherListe
        visitDF ausführen. Also 1 , danach 3 */
        for (int i = 0; i < b.size() - 1; i++) {
            if (!besucht.contains(b.get(i))) {
                numberOfDFTrees++;
                visitDF(b.get(i), g, besucht);
            }
        }
    }

    void visitDF(V v, DirectedGraph<V> g, Set<V> besucht) {

        besucht.add(v);

        preOrder.add(v);

        for (var e : g.getSuccessorVertexSet(v)) {
            if (!besucht.contains(e)) {
                visitDF(e, g, besucht);

            }
        }
        postOrder.add(v);
    }

    /**
     * Liefert eine nicht modifizierbare Liste (unmodifiable view) mit einer
     * Pre-Order-Reihenfolge zurück.
     *
     * @return Pre-Order-Reihenfolge der Tiefensuche.
     */
    public List<V> preOrder() {
        return Collections.unmodifiableList(preOrder);
    }

    /**
     * Liefert eine nicht modifizierbare Liste (unmodifiable view) mit einer
     * Post-Order-Reihenfolge zurück.
     *
     * @return Post-Order-Reihenfolge der Tiefensuche.
     */
    public List<V> postOrder() {
        return Collections.unmodifiableList(postOrder);
    }

    /**
     *
     * @return Anzahl der Bäume des Tiefensuchwalds.
     */
    public int numberOfDFTrees() {
        return numberOfDFTrees;
    }

    public static void main(String[] args) {
        DirectedGraph<Integer> g = new AdjacencyListDirectedGraph<>();
        g.addEdge(1, 2);
        g.addEdge(2, 5);
        g.addEdge(5, 1);
        g.addEdge(2, 6);
        g.addEdge(3, 7);
        g.addEdge(4, 3);
        g.addEdge(4, 6);
        //g.addEdge(7,3);
        g.addEdge(7, 4);

        DepthFirstOrder<Integer> dfs = new DepthFirstOrder<>(g);
        System.out.println(dfs.numberOfDFTrees());	// 2
        System.out.println(dfs.preOrder());		// [1, 2, 5, 6, 3, 7, 4]
        System.out.println(dfs.postOrder());		// [5, 6, 2, 1, 4, 7, 3]

    }
}
