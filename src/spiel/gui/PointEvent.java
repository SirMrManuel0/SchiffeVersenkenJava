package spiel.gui;

public class PointEvent {
    private PointPanel source;
    private int x;
    private int y;

    public PointEvent(PointPanel source, int x, int y){
        this.source = source;
        this.x = x;
        this.y = y;
    }

    public PointPanel getSource(){return source;}
    public int getX() {return x;}

    public int getY() {return y;}
}
