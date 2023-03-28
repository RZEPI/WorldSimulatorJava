public class ColorM {
    private int r;
    private int g;
    private int b;
    public ColorM(){
        this.r = 255;
        this.g = 255;
        this.b = 255;
    }
    public ColorM(int r, int g, int b)
    {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }

}
