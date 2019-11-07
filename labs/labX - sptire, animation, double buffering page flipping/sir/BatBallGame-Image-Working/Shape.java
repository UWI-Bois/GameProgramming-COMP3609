import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import java.util.Random;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Image;

public class Shape {

	private static final int XSIZE = 20;
	private static final int YSIZE = 30;
	private static final int DY = 10;

	private GamePanel panel;
	private Bat bat;
	private Random random;
	private int x;
	private int y;
	private Image playerImage;
	private int playerWidth;
	private int playerHeight;

	Graphics2D g2;
	private Dimension dimension;

	public Shape (GamePanel p) {
		panel = p;
		Graphics g = panel.getGraphics ();
		g2 = (Graphics2D) g;
		dimension = panel.getSize();

		random = new Random();
		setPosition();
	}

	public void setPosition () {
		x = random.nextInt(dimension.width - XSIZE);	// randomly generate x location
		y = random.nextInt(dimension.height - YSIZE);	// randomly generate y location
	}

	public void drawLine (Graphics2D g2) {			// draws a random line starting from (x, y)
		int x2, y2;

		x2 = random.nextInt(dimension.width);
		y2 = random.nextInt(dimension.height);

		g2.setColor (Color.BLACK);
		g2.drawLine (x, y, x2, y2);

	}

	public void drawEllipse (Graphics2D g2) {		// draws an ellipse starting from (x, y)

		g2.setColor (Color.RED);
		g2.fill (new Ellipse2D.Double (x, y, XSIZE, YSIZE));

	}

	public void drawRectangle (Graphics2D g2) {		// draws a rectangle starting from (x, y)

		g2.setColor (Color.CYAN);
		g2.fill (new Rectangle2D.Double (x, y, XSIZE, YSIZE));

	}

	public void draw (Graphics2D g2) {			// draws a line, ellipse, or rectangle
		int shapeType;
	
		shapeType = random.nextInt(3);			// randomly choose between shape to draw

		if (shapeType == 0) 
			drawLine (g2);
		else
		if (shapeType == 1)
			drawEllipse (g2);
		else
			drawRectangle (g2);
	}

}