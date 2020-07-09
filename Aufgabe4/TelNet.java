import java.util.*;

public class TelNet {

    int lbg;
    Map<TelKnoten, Integer> knoten;
    List<TelVerbindung> optTelNet;

    public TelNet(int lbg) {
        this.lbg = lbg;
        this.knoten = new HashMap<>();
        this.optTelNet = new LinkedList<>();
    }

    // Fügt einen neuen Telefonknoten mit Koordinate (x,y) dazu.
    public boolean addTelKnoten(int x, int y) {
        TelKnoten k = new TelKnoten(x, y);
        if (knoten.containsKey(k)){
            return false;
        } else {
            knoten.put(k, knoten.size());
            return true;
        }
    }

    // Berechnet ein optimales Telefonnetz als minimal aufspannenden Baum mit dem Algorithmus von Kruskal.
    public boolean computeOptTelNet() {
        UnionFind forest = new UnionFind(knoten.size());
        PriorityQueue<TelVerbindung> telVerbindungen = new PriorityQueue<>(knoten.size(), Comparator.comparing(x -> x.c));
        optTelNet = new LinkedList<>();

        // Füge Telefonverbindung hinzu, falls kosten <= lgb
        for (var a : knoten.entrySet()) {
            for (var e : knoten.entrySet()) {
                if (a.equals(e)) {
                    continue;
                }
                int kosten = Math.abs(a.getKey().x - e.getKey().x) + Math.abs(a.getKey().y - e.getKey().y);
                if (kosten <= lbg) {
                    TelVerbindung t = new TelVerbindung(a.getKey(), e.getKey(), kosten);
                    telVerbindungen.add(t);
                }
            }
        }

        // Alle Kanten werden mit ihren Gewichten in einer Prioritätsliste gespeichert,
        // so dass effizient auf die Kante mit dem kleinsten Gewicht zugegriffen werden kann.

        while (forest.size() != 1 && !telVerbindungen.isEmpty()) { // Solange der Wald noch mehr als ein Baum enthält und es noch Kanten gibt
            TelVerbindung s = telVerbindungen.poll(); // Wähle Kante mit kleinstem Gewicht, die 2 Bäume aus dem Wald verbindet
            int t1 = forest.find(knoten.get(s.u));    // forest.find(u) liefert denjenigen Baum zurück, in dem u enhalten ist.
            int t2 = forest.find(knoten.get(s.v));

            if (t1 != t2) {
                forest.union(t1, t2); // Vereinigen die Bäume zu einem Baum
                optTelNet.add(s);     //
            }
        }

        return !telVerbindungen.isEmpty() || forest.size() == 1;
    }

    // Zeichnet das gefundene optimale Telefonnetz mit der Größe xMax*yMax in ein Fenster.
    public void drawOptTelNet(int xMax, int yMax) {
        StdDraw.clear();
        //  Knoten
        StdDraw.setPenRadius(0.009);
        StdDraw.setPenColor(StdDraw.BLUE);

        for (var e : knoten.keySet()) {
            StdDraw.point(e.x / (double) xMax, e.y / (double) yMax);
        }

        // Verbindungen
        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(StdDraw.BLACK);

        for (var e : optTelNet) {
            StdDraw.line(e.u.x / (double) xMax, e.u.y / (double) yMax, e.u.x / (double) xMax, e.v.y / (double) yMax);
            StdDraw.line(e.u.x / (double) xMax, e.v.y / (double) yMax, e.v.x / (double) xMax, e.v.y / (double) yMax);
        }
        StdDraw.show();
    }

    // Fügt n zufällige Telefonknoten zum Netz dazu mit x-Koordinate aus [0,xMax] und y-Koordinate aus [0,yMax].
    public void generateRandomTelNet(int n, int xMax, int yMax) {
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            int rand_int1 = rand.nextInt(xMax);
            int rand_int2 = rand.nextInt(yMax);
            addTelKnoten(rand_int1, rand_int2);
        }
    }

    // Liefert ein optimales Telefonnetz als Liste von Telefonverbindungen zurück.
    public List<TelVerbindung> getOptTelNet() {
        return optTelNet;
    }

    // Liefert die Gesamtkosten eines optimalen Telefonnetzes zurück.
    public int getOptTelNetKosten() {
        int gesamtkosten = 0;
        for (var verbindung : optTelNet) {
            gesamtkosten += verbindung.c;
        }
        return gesamtkosten;
    }

    // Lieder die Anzahl der Knoten des Telefonnetzes zurück
    public int size(){
        return knoten.size();
    }

    @Override
    public String toString() {
        return "TelNet{" +
                "lbg=" + lbg +
                ", knoten=" + knoten +
                ", optTelNet=" + optTelNet +
                '}';
    }

    public static void main(String args[]) {
        TelNet telNet = new TelNet(7);

        telNet.addTelKnoten(1, 1);
        telNet.addTelKnoten(3, 1);
        telNet.addTelKnoten(4, 2);
        telNet.addTelKnoten(3, 4);
        telNet.addTelKnoten(2, 6);
        telNet.addTelKnoten(4, 7);
        telNet.addTelKnoten(7, 5);
        telNet.computeOptTelNet();
        telNet.drawOptTelNet(7, 7);
        System.out.println("Kosten: " + telNet.getOptTelNetKosten());

       /* TelNet random = new TelNet(100);
        random.generateRandomTelNet(1000,1000,1000);


        random.computeOptTelNet();

        System.out.println("Kosten: " + random.getOptTelNetKosten());
        random.drawOptTelNet(1000, 1000);*/


    }
}
