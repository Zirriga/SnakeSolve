package SnakeGame;

public class Point {
    private int x;
    private int y;

    public Point(int y, int x) {
        setXY(y, x);
    }

    public int getX() {
        return x;
    }

    public int getY() {
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

    @Override
    public String toString() {
        return y + "-" + x;
    }

    @Override
    public int hashCode() {
        return y * 100 + x;
    }
}
