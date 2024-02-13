package spiel.gui.Point;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class PointPanel extends JPanel implements MouseListener {
    private final Color BACKGROUND_COLOR = new Color(8,164,164);
    private final Color BORDER_HOVER_COLOR = new Color(247,91,91);
    private final Color MISS_COLOR = Color.RED;
    private final Color HIT_COLOR = Color.GREEN;
    private final String SHIP_PATH = "resources/schiff.png";
    private final String CANNON_PATH = "resources/kanone.png";
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
        setForeground(MISS_COLOR);
        repaint();
    }

    public void hit(){
        setForeground(HIT_COLOR);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (getForeground().equals(HIT_COLOR)){
            g.setColor(BACKGROUND_COLOR);
            g.fillRect(0,0, getWidth(), getHeight());
            ImageIcon image = new ImageIcon(SHIP_PATH);
            int imageX = (getWidth() - image.getIconWidth()) / 2;
            int imageY = (getHeight() - image.getIconHeight()) / 2;
            g.drawImage(image.getImage(), imageX, imageY, null);
        } else if (getForeground().equals(MISS_COLOR)) {
            setBackground(BACKGROUND_COLOR);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!Listener.isEmpty()){
            for (PointListener p : Listener){
                p.PointEvent(new PointEvent(this, x, y));
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        Border solidBorder = BorderFactory.createLineBorder(BORDER_HOVER_COLOR, 2);
        setBorder(solidBorder);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Cursor cursor = toolkit.createCustomCursor(new ImageIcon(CANNON_PATH).getImage(), new Point(0, 0), "Kanone");

        setCursor(cursor);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Border solidBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
        setBorder(solidBorder);
        setCursor(Cursor.getDefaultCursor());
    }
}
