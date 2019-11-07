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

      if (x < 0) {				// ball hits left side of panel
         x = 0;
         dx = dx * -1;
      }

      if (x + XSIZE >= dimension.width) {	// ball hits right side of panel
         x = dimension.width - XSIZE;
         dx = dx * -1;
      }

      if (y < 0) {				// ball hits top of panel
         y = 0;
         dy = dy * -1;
      }

      if (y + YSIZE >= dimension.height){	// balls hits bottom of panel
         y = dimension.height - YSIZE;
         dy = dy * -1;
      }

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