import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import javax.swing.JPanel;

public class Ball extends Thread {

   private static final int XSIZE = 10;
   private static final int YSIZE = 10;

   private JPanel panel;
   private int x;
   private int y;
   private int dx;
   private int dy;

   Graphics2D g2;
   private Color backgroundColor;
   private Dimension dimension;

   public Ball(JPanel p) {
      panel = p;
      Graphics g = panel.getGraphics ();
      g2 = (Graphics2D) g;
      dimension = panel.getSize();
      backgroundColor = panel.getBackground ();
      x = y = 10;
      dx = dy = 2;
   }

   public void draw () {
      g2.setColor (Color.RED);
      g2.fill (new Ellipse2D.Double (x, y, XSIZE, YSIZE));
   }

   public void erase () {
      g2.setColor (backgroundColor);
      g2.fill (new Ellipse2D.Double (x, y, XSIZE, YSIZE));
   }

   public void move () {

      if (!panel.isVisible ()) return;

      x = x + dx;
      y = y + dy;

	  // check if x or y is outside the panel
	  // NB: dimension.width is the width of the panel, and
	  //     dimension.height is the height of the panel
   }

   public void run () {
      try {
	draw ();
        for (int i = 1; i <= 1000; i++) {
            erase();
	    move ();
            draw();
            sleep (10);		// increase value of sleep time to slow down ball
         }
      }
      catch(InterruptedException e) {}
   }

}