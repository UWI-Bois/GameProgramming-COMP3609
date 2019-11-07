import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import java.util.Random;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Image;

public class Ball {

	private static final int XSIZE = 20;
	private static final int YSIZE = 20;
	private static final int DY = 10;

	private JFrame window;
	private Bat bat;
	private Random random;
	private int x;
	private int y;
	private Image ballImage;

	AudioClip hitBatSound;
	AudioClip fallOffSound;

	Graphics2D g2;
	private Color backgroundColor;
	private Dimension dimension;

	public Ball (JFrame f, Bat b) {
		window = f;
		bat = b;

		dimension = window.getSize();
		backgroundColor = window.getBackground ();

		random = new Random();
		setPosition();					// set initial position of ball

		ballImage = loadImage ("images/ball.gif");
		loadClips();
	}

	public Image loadImage (String fileName) {
		return new ImageIcon(fileName).getImage();
	}

	public void setPosition () {
//		System.out.println("BALL\ndim width: " + dimension.width + " xsize: " + XSIZE);
		x = random.nextInt(dimension.width - XSIZE);	// randomly generate x location
		y = 0;						// set y to top of the panel
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

		Rectangle2D.Double rectBall = getBoundingRectangle();
		Rectangle2D.Double rectBat = bat.getBoundingRectangle();
		
		if (rectBall.intersects(rectBat))
			return true;
		else
			return false;
	}

	public boolean isOffScreen () {

		if (y + YSIZE > dimension.height)
			return true;
		else
			return false;
	}


	public void update () {

		if (!window.isVisible ()) return;
	
		// erase();					// no need to erase with background image


		y = y + DY;

		boolean hitBat = batHitsBall();

		if (hitBat || isOffScreen()) {
			if (hitBat) {
				playClip (1);			// play clip if bat hits ball
			}
			else {					// play clip if ball falls out at bottom
				playClip (2);
			}

			try {					// take a rest if bat hits ball or
				Thread.sleep (2000);		//   ball falls out of play.
			}
			catch (InterruptedException e) {};

			setPosition ();				// re-set position of ball
		}
	}

	public void loadClips() {

		try {

			hitBatSound = Applet.newAudioClip (
					getClass().getResource("sounds/hitBat.au"));

			fallOffSound = Applet.newAudioClip (
					getClass().getResource("sounds/fallOff.au"));

		}
		catch (Exception e) {
			System.out.println ("Error loading sound file: " + e);
		}

	}

	public void playClip (int index) {

		if (index == 1 && hitBatSound != null)
			hitBatSound.play();
		else
		if (index == 2 && fallOffSound != null)
			fallOffSound.play();

	}

}