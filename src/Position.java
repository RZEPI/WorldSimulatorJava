public class Position {
    private int x;
    private int y;
    Position(int x, int y){
        setX(x);
        setY(y);
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}
