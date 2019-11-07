import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import javax.swing.JPanel;
import java.util.Random;

public class Ball extends Thread {

   private static final int MAXDIAMETER = 20;
   private static final int MINDIAMETER = 10;

   private JPanel panel;
   private Random random;
   private int x = 50;
   private int y = 100;
   private int diameter = 10;
   private Color colour = Color.RED;
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

      random = new Random();
      x = random.nextInt(dimension.width);	// randomly generate x location
      y = random.nextInt(dimension.height); 	// randomly generate y location

      dx = dy = 2;

      diameter = random.nextInt(MAXDIAMETER - MINDIAMETER) + MINDIAMETER;

      int red, green, blue;

      red = random.nextInt(256);		// randomly generate red [0, 255]
      green = random.nextInt(256);		// randomly generate green [0, 255]
      blue = random.nextInt(256);		// randomly generate blue [0, 255]

      colour = new Color (red, green, blue);
   }

   public void draw () {
      g2.setColor (colour);
      g2.fill (new Ellipse2D.Double (x, y, diameter, diameter));
   }

   public void erase () {
      g2.setColor (backgroundColor);
      g2.fill (new Ellipse2D.Double (x, y, diameter, diameter));
   }

   public void move () {

      if (!panel.isVisible ()) return;

      x = x + dx;
      y = y + dy;

      if (x < 0) {
         x = 0;
         dx = dx * -1;
      }

      if (x + diameter >= dimension.width) {
         x = dimension.width - diameter;
         dx = dx * -1;
      }

      if (y < 0) {
         y = 0;
         dy = dy * -1;
      }

      if (y + diameter >= dimension.height){
         y = dimension.height - diameter;
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
            sleep (20);		// increase value of sleep time to slow down ball
         }
      }
      catch(InterruptedException e) {}
   }

}