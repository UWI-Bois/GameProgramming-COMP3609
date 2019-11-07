import javafx.scene.shape.Ellipse;
import java.awt.*;
import javax.swing.JComponent;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
public class RectangleComponent extends JComponent
{
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        Rectangle2D.Double rec1 = new Rectangle2D.Double(10, 10, 60, 45);
        g2.draw(rec1);
            g2.translate(25, 30);
        g2.draw(rec1);
            g2.translate(25, 40);
        g2.draw(rec1);
            g2.translate(25, 50);

    }
}
