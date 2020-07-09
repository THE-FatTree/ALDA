public class TelVerbindung {

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

}
