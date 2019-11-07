import java.awt.geom.Rectangle2D;
import javax.swing.JFrame;
import java.util.Random;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Image;

public class Ball extends Sprite {

	private Bat bat;
	private Random random;

	AudioClip hitBatSound;
	AudioClip fallOffSound;

	public Ball (JFrame f, Bat b, 
		     int x, int y, int dx, int dy, 
		     int xSize, int ySize, 
		     String filename) {
		super(f, x, y, dx, dy, xSize, ySize, filename);
		bat = b;
		random = new Random();
		setPosition();
		loadClips();
	}

	public void setPosition () {
		int x = random.nextInt(dimension.width - xSize);
		setX(x);					// set initial position of ball
		setY(0);
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

		if (y + ySize > dimension.height)
			return true;
		else
			return false;
	}


	public void update () {

		if (!window.isVisible ()) return;
	
		y = y + dy;

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