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

public class Player {

	private static final int XSIZE = 20;
	private static final int YSIZE = 20;
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
	private Color backgroundColor;
	private Dimension dimension;

	public Player (GamePanel p) {
		panel = p;
		Graphics g = panel.getGraphics ();
		g2 = (Graphics2D) g;
		dimension = panel.getSize();
		backgroundColor = panel.getBackground ();

		random = new Random();

		playerImage = panel.loadImage ("player1.png");
		playerWidth = playerImage.getWidth(null);
		playerHeight = playerImage.getHeight(null);
		setPosition();
	}

	public void setPosition () {
		x = random.nextInt(dimension.width - playerWidth);	// randomly generate x location
		y = random.nextInt(dimension.height - playerHeight);			// set y somewhere from 0..99
	}

	public void draw (Graphics2D g2) {
		int width, height;

		g2.setColor (Color.CYAN);
	
		width = (int) playerWidth / 2;
		height = (int) playerHeight / 2;

		g2.drawImage(playerImage, x, y, width, height, null);
	}

}