import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import java.util.Random;
import java.awt.Image;

public class Ball {

	private static final int XSIZE = 20;
	private static final int YSIZE = 20;
	private static final int DY = 10;

	private GamePanel panel;
	private Bat bat;
	private Random random;
	private int x;
	private int y;
	private Image ballImage;

	Graphics2D g2;
	private Color backgroundColor;
	private Dimension dimension;

	public Ball (GamePanel p) {
		panel = p;
		Graphics g = panel.getGraphics ();
		g2 = (Graphics2D) g;
		dimension = panel.getSize();
		backgroundColor = panel.getBackground ();

		random = new Random();
		setPosition();					// set initial position of ball

		ballImage = panel.loadImage ("ball.gif");
	}

	public void setPosition () {
		x = random.nextInt(dimension.width - XSIZE);	// randomly generate x location
		y = random.nextInt(100);			// set y somewhere from 0..99
	}

	public void draw (Graphics2D g2) {
		g2.setColor (Color.CYAN);
	
		g2.drawImage(ballImage, x, y, XSIZE, YSIZE, null);
	}

	public void erase (Graphics2D g2) {
		g2.setColor (backgroundColor);
		g2.fill (new Ellipse2D.Double (x, y, XSIZE, YSIZE));
	}

	public Rectangle2D.Double getBoundingRectangle() {
		return new Rectangle2D.Double (x, y, XSIZE, YSIZE);
	}

	public boolean batHitsBall () {
		return false;
/*
		Rectangle2D.Double rectBall = getBoundingRectangle();
		Rectangle2D.Double rectBat = bat.getBoundingRectangle();
		
		if (rectBall.intersects(rectBat))
			return true;
		else
			return false;

*/
	}

	public boolean isOffScreen () {

		if (y + YSIZE > dimension.height)
			return true;
		else
			return false;
	}


	public void move () {

		if (!panel.isVisible ()) return;
	
		// erase();					// no need to erase with background image


		y = y + DY;

		boolean hitBat = batHitsBall();

		if (hitBat || isOffScreen()) {
			if (hitBat) {
								// play clip if bat hits ball
			}
			else {					// play clip if ball falls out at bottom
				
			}

			try {					// take a rest if bat hits ball or
				Thread.sleep (2000);		//   ball falls out of play.
			}
			catch (InterruptedException e) {};

			setPosition ();				// re-set position of ball
		}
	}
}