public class Point {
    private int x;
    private int y;

    public Point(int y, int x) {
        setXY(y, x);
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    void setXY(int y, int x) {
        this.y = y;
        this.x = x;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj instanceof Point) {
            Point other = (Point) obj;
            return other.getX() == this.getX() && other.getY() == this.getY();
        }
        return false;
    }
}
