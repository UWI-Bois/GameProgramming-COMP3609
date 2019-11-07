import java.util.Random;
import javax.swing.JFrame;
import java.applet.Applet;
import java.applet.AudioClip;

public class Bat extends Sprite {

	AudioClip hitWallSound = null;
	Random random;

	public Bat (JFrame f,
		     int x, int y, int dx, int dy, 
		     int xSize, int ySize, 
		     String filename) {
		super(f, x, y, dx, dy, xSize, ySize, filename);

		random = new Random();
		setPosition();
		loadClips();
	}

	public void setPosition() {
		int x = random.nextInt (dimension.width - xSize);
		setX(x);
	}

	public void update() {
	
	}

	public void moveLeft () {

		if (!window.isVisible ()) return;

		x = x - dx;

		if (x < 0) {					// hits left wall
			x = 0;
			playClip (1);
		}
	}

	public void moveRight () {

		if (!window.isVisible ()) return;

		x = x + dx;

		if (x + xSize >= dimension.width) {		// hits right wall
			x = dimension.width - xSize;
			playClip (1);
		}

	}

	public void loadClips() {

		try {
			hitWallSound = Applet.newAudioClip (
						getClass().getResource("sounds/hitWall.au"));
		}
		catch (Exception e) {
			System.out.println ("Error loading sound file: " + e);
		}

	}

	public void playClip(int index) {

		if (index == 1 && hitWallSound != null)
			hitWallSound.play();
	}

}