package spiel.gui.Point;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class PointPanel extends JPanel implements MouseListener {
    private int x;
    private int y;
    private ArrayList<PointListener> Listener = new ArrayList<>();

    public PointPanel(){
        Border solidBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
        setBorder(solidBorder);
        addMouseListener(this);
    }

    public void setPoint(int x ,int y){
        this.x = x;
        this.y = y;
    }
    public void addListener(PointListener p){
        if (p == null) return;
        Listener.add(p);
    }

    public void miss(){
        setBackground(new Color(224, 76, 76));
        repaint();
    }

    public void hit(){
        setBackground(new Color(116, 207, 116));
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!Listener.isEmpty()){
            for (PointListener p : Listener){
                p.PointEvent(new PointEvent(this, x, y));
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {
        Border solidBorder = BorderFactory.createLineBorder(Color.BLUE, 2);
        setBorder(solidBorder);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Border solidBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
        setBorder(solidBorder);
    }
}
