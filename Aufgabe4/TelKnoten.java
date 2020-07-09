import java.util.Objects;

public class TelKnoten {

    int x;
    int y;

    public TelKnoten(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "TelKnoten{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public int hashCode(){
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object o){
        if (o instanceof TelKnoten) {
            if (this.x == ((TelKnoten) o).x && this.y == ((TelKnoten)o).y)
                return true;
        }
        return false;
    }
}
