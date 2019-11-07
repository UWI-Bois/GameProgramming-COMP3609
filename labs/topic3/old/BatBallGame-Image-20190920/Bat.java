import java.util.Random;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.event.*;
import java.awt.*;

public class Bat {

	private static final int XSIZE = 30;		// width of the bat
	private static final int YSIZE = 10;		// height of the bat
	private static final int XSTEP = 5;		// amount of pixels to move in one keystroke
	private static final int YPOS = 350;		// vertical position of the bat

	private JPanel panel;
	private Dimension dimension;
	private int x;
	private int y;

	Graphics2D g2;
	private Color backgroundColor;
	AudioClip hitWallSound = null;

	public Bat (JPanel p) {
		panel = p;
		Graphics g = panel.getGraphics ();
		g2 = (Graphics2D) g;
		backgroundColor = panel.getBackground ();
		loadClips();

		dimension = panel.getSize();
		Random random = new Random();
		x = random.nextInt (dimension.width - XSIZE);
		y = YPOS;
	}

	public void draw (Graphics2D g2) {
		g2.setColor (Color.DARK_GRAY);
		g2.fill (new Rectangle2D.Double (x, y, XSIZE, YSIZE));
	}

	public void erase (Graphics2D g2) {
		g2.setColor (backgroundColor);
		g2.fill (new Rectangle2D.Double (x, y, XSIZE, YSIZE));
	}

	public Rectangle2D.Double getBoundingRectangle() {
		return new Rectangle2D.Double (x, y, XSIZE, YSIZE);
	}

	public void moveLeft () {

		if (!panel.isVisible ()) return;

		// erase();					// no need to erase with background image

		x = x - XSTEP;

		if (x < 0) {					// hits left wall
			x = 0;
			playClip (1);
		}

	}

	public void moveRight () {

		if (!panel.isVisible ()) return;

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
						getClass().getResource("hitWall.au"));
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