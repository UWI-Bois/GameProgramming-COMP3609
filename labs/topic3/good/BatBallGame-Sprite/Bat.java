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
import java.awt.Image;

public class Bat extends Sprite{

	private static final int XSIZE = 40;		// width of the bat
	private static final int YSIZE = 40;		// height of the bat
	private static final int XSTEP = 7;		// amount of pixels to move in one keystroke
	private static final int YSTEP = 7;		// amount of pixels to jump in one keystroke
	private static final int YPOS = 535;		// vertical position of the bat

	Graphics2D g2;
	private GamePanel panel;
	private Dimension dimension;
	private Color backgroundColor;

	// private int x;
	// private int y;

	private Image batLeft;
	private Image batRight;
	private boolean facingLeft;
	private boolean facingRight;
	AudioClip hitWallSound = null;

	public Bat (GamePanel p) {
		panel = p;
		Graphics g = panel.getGraphics ();
		g2 = (Graphics2D) g;
		backgroundColor = panel.getBackground ();
		dimension = panel.getSize();

		//super.panel = p;
        //super.Graphics g = panel.getGraphics ();
        //super.g2 = (Graphics2D) g;
        //super.backgroundColor = panel.getBackground ();
		//super.dimension = panel.getSize();

		loadClips();

		
		Random random = new Random();
		super.x = random.nextInt (dimension.width - XSIZE);
		super.y = YPOS;

		batLeft = panel.loadImage("batLeft.gif");
		batRight = panel.loadImage("batRight.gif");

		facingLeft = false;
		facingRight = true;
	}

	public void draw (Graphics2D g2) {
		g2.setColor (Color.DARK_GRAY);

		if (facingLeft)
			g2.drawImage (batLeft, x, y, XSIZE, YSIZE, null);
		else
			g2.drawImage (batRight, x, y, XSIZE, YSIZE, null);

		
		//g2.fill (new Rectangle2D.Double (x, y, XSIZE, YSIZE));
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

		facingLeft = true;
		facingRight = false;
	}

	public void moveRight () {

		if (!panel.isVisible ()) return;

		// erase();					// no need to erase with background image

		x = x + XSTEP;

		if (x + XSIZE >= dimension.width) {		// hits right wall
			x = dimension.width - XSIZE;
			playClip (1);
		}

		facingRight = true;
		facingLeft = false;
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