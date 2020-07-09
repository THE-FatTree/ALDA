public class UnionFind {

    int[] p;

    public UnionFind(int n) {
        p = new int[n];
        for (int i = 0; i < n; i++) {
            p[i] = -1;
        }
    }

    int find(int e) {
        while (p[e] >= 0) // e ist keine Wurzel
            e = p[e];
        return e;
    }

    public void union(int s1, int s2) {
        if (p[s1] >= 0 || p[s2] >= 0) // s1 und s2 müssen Repräsentanten einer Menge sein
            return;
        if (s1 == s2) // Falls s1 und s2 dieselbe Menge ist, dann mache nichts
            return;

        if (-p[s1] < -p[s2]) // Höhe von s1 < Höhe von s2
            p[s1] = s2;
        else {
            if (-p[s1] == -p[s2]) // Höhe von s1 erhöht sich um 1
                p[s1]--;
            p[s2] = s1;
        }
    }

    public int size() {
        int size = 0;
        for (int i : p) {
            if (i < 0) { // Immer wenn Wurzeln dabei sind (als negative Zahl notiert)
                size++;
            }
        }
        return size;
    }

    private void prettyPrint() {
        System.out.print("e:  \t");
        for (int i = 0; i < p.length; i++) {
            System.out.print(" " + i + "\t");
        }
        System.out.print("\np[e]:\t");
        for (int value : p) {
            if(value >= 0) System.out.print(" ");
            System.out.print(value + "\t");
        }
        System.out.println();
    }

    public static void main(String[] args) {

        UnionFind test = new UnionFind(14);

        test.p[0] = -2;
        test.p[1] = 0;
        test.p[2] = 0;
        test.p[3] = -1;
        test.p[4] = 6;
        test.p[5] = -2;
        test.p[6] = -4;
        test.p[7] = 4;
        test.p[8] = 5;
        test.p[9] = 4;
        test.p[10] = 6;
        test.p[11] = 6;
        test.p[12] = 7;
        test.p[13] = 11;

        test.prettyPrint();

        int x = test.find(9);
        System.out.println("Element 9 hat Wurzel: " + x);
        System.out.println("Menge an Partitionen: " + test.size());

        test.union(3, 5);
        System.out.println("Union(3, 5)");
        test.prettyPrint();
        System.out.println("Menge an Partitionen: " + test.size());
        test.union(0, 5);
        System.out.println("Union(0, 5)");
        test.prettyPrint();
        System.out.println("Menge an Partitionen: " + test.size());
        test.union(0, 6);
        System.out.println("Untion(0, 6)");
        test.prettyPrint();

        System.out.println();
        System.out.println("Menge an Partitionen: " + test.size());

    }
}

