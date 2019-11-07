import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import javax.swing.JPanel;

public class Ball {

   private static final int XSIZE = 10;
   private static final int YSIZE = 10;

   private JPanel panel;
   private int x;
   private int y;

   Graphics2D g2;
   private Color backgroundColor;

   public Ball(JPanel p) {
      panel = p;
      Graphics g = panel.getGraphics ();
      g2 = (Graphics2D) g;
      backgroundColor = panel.getBackground ();
      x = y = 10;
   }

   public void draw () {
      g2.setColor (Color.RED);
      g2.fill (new Ellipse2D.Double (x, y, XSIZE, YSIZE));
   }

   public void erase () {
      g2.setColor (backgroundColor);
      g2.fill (new Ellipse2D.Double (x, y, XSIZE, YSIZE));
   }

}