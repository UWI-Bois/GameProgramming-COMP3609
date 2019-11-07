import java.util.Random;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.*;
import java.awt.Image;

public class Bat {

	private static final int XSIZE = 50;		// width of the bat
	private static final int YSIZE = 50;		// height of the bat
	private static final int XSTEP = 5;		// amount of pixels to move in one keystroke
	private static final int YPOS = 375;		// vertical position of the bat

	private GamePanel panel;
	private Dimension dimension;
	private int x;
	private int y;

	private Image batImage;

	Graphics2D g2;
	private Color backgroundColor;

	public Bat (GamePanel p) {
		panel = p;
		Graphics g = panel.getGraphics ();
		g2 = (Graphics2D) g;
		backgroundColor = panel.getBackground ();

		dimension = panel.getSize();
		Random random = new Random();
		x = random.nextInt (dimension.width - XSIZE);
		y = YPOS;

		int imageNo = random.nextInt(3) + 1;
		String filename = "bat" + imageNo + ".png";
		batImage = panel.loadImage(filename);
	}

	public void draw (Graphics2D g2) {
		g2.setColor (Color.DARK_GRAY);

		g2.drawImage (batImage, x, y, XSIZE, YSIZE, null);
		
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
		}

	}

	public void moveRight () {

		if (!panel.isVisible ()) return;

		// erase();					// no need to erase with background image

		x = x + XSTEP;

		if (x + XSIZE >= dimension.width) {		// hits right wall
			x = dimension.width - XSIZE;
		}

	}

}