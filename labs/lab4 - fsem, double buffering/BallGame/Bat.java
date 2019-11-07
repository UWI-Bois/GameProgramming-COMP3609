import java.util.Random;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.event.*;
import java.awt.*;
import java.awt.Image;

public class Bat {

	private static final int XSIZE = 40;		// width of the bat
	private static final int YSIZE = 40;		// height of the bat
	private static final int XSTEP = 7;		// amount of pixels to move in one keystroke
	private static final int YPOS = 585;		// vertical position of the bat

	private JFrame window;
	private Dimension dimension;
	private int x;
	private int y;

	private Image batImage;

	private Color backgroundColor;
	AudioClip hitWallSound = null;

	public Bat (JFrame f) {
		window = f;

		backgroundColor = window.getBackground ();
		loadClips();

		dimension = window.getSize();
		Random random = new Random();
//		System.out.println("BAT\ndim width: " + dimension.width + " xsize: " + XSIZE);
		x = random.nextInt (dimension.width - XSIZE);
		y = YPOS;

		batImage = loadImage("images/bat.gif");
	}

	public Image loadImage (String fileName) {
		return new ImageIcon(fileName).getImage();
	}

	public void draw (Graphics2D g2) {
		g2.drawImage (batImage, x, y, XSIZE, YSIZE, null);
	}

	public Rectangle2D.Double getBoundingRectangle() {
		return new Rectangle2D.Double (x, y, XSIZE, YSIZE);
	}

	public void moveLeft () {

		if (!window.isVisible ()) return;

		// erase();					// no need to erase with background image

		x = x - XSTEP;

		if (x < 0) {					// hits left wall
			x = 0;
			playClip (1);
		}
	}

	public void moveRight () {

		if (!window.isVisible ()) return;

		// erase();					// no need to erase with background image

		x = x + XSTEP;

		if (x + XSIZE >= dimension.width) {		// hits right wall
			x = dimension.width - XSIZE;
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