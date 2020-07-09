public class TelVerbindung implements Comparable<TelVerbindung> {

    int c; // Verbindungskosten
    TelKnoten u; // Anfangsknoten
    TelKnoten v; // Endknoten

    public TelVerbindung(TelKnoten u, TelKnoten v, int c) {
        this.u = u;
        this.v = v;
        this.c = c;
    }

    @Override
    public String toString() {
        return "TelVerbindung{" +
                "c=" + c +
                ", u=" + u +
                ", v=" + v +
                '}';
    }

    @Override
    public int compareTo(TelVerbindung o) {
        if (o == null)
            throw new IllegalArgumentException();
        return c - o.c;
    }

}
