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

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class SpriteEffect {

	private static final int XSIZE = 60;		// width of the bat
	private static final int YSIZE = 60;		// height of the bat
	private static final int XSTEP = 7;		// amount of pixels to move in one keystroke
	private static final int YPOS = 300;		// vertical position of the bat

	private GamePanel panel;
	private Dimension dimension;
	private int x;
	private int y;

	private BufferedImage spriteImage;		// image for sprite effect
	private BufferedImage copy;			// copy of image

	Graphics2D g2;
	private Color backgroundColor;
	AudioClip hitWallSound = null;

	int alpha, alphaChange;				// alpha value (for alpha transparency byte)

	public SpriteEffect (GamePanel p) {
		panel = p;
		Graphics g = panel.getGraphics ();
		g2 = (Graphics2D) g;
		backgroundColor = panel.getBackground ();
		loadClips();

		dimension = panel.getSize();
		Random random = new Random();
		x = random.nextInt (dimension.width - XSIZE);
		y = YPOS;

		alpha = 255;				// set to 255 (fully opaque)
		alphaChange = 5;			// set to 15

		spriteImage = loadImage("transparent.png");
		copy = copyImage(spriteImage);		// make copy of image for disappearing effect

	}

	public void draw (Graphics2D g2) {

		int imWidth = copy.getWidth();
		int imHeight = copy.getHeight();

    		int [] pixels = new int[imWidth * imHeight];
    		copy.getRGB(0, 0, imWidth, imHeight, pixels, 0, imWidth);

    		int red, green, blue, newValue;
		int pixelAlpha;

		for (int i=0; i<pixels.length; i++) {
			pixelAlpha = (pixels[i] >> 24) & 255;
			red = (pixels[i] >> 16) & 255;
			green = (pixels[i] >> 8) & 255;
			blue = pixels[i] & 255;

			if (pixelAlpha != 0) {
				newValue = blue | (green << 8) | (red << 16) | (alpha << 24);
				pixels[i] = newValue; 
			}
		}
  
    		copy.setRGB(0, 0, imWidth, imHeight, pixels, 0, imWidth);	

		g2.drawImage(copy, x, y, XSIZE, YSIZE, null);

	}

	public Rectangle2D.Double getBoundingRectangle() {
		return new Rectangle2D.Double (x, y, XSIZE, YSIZE);
	}

	public void update() {				// modify alpha
	
		alpha = alpha - alphaChange;

		if (alpha <= 0)	{
			copy = copyImage(spriteImage);			
			alpha = 255;
		}

	}

	public void moveLeft () {

		if (!panel.isVisible ()) return;

		// erase();				// no need to erase with background image

		x = x - XSTEP;

		if (x < 0) {				// hits left wall
			x = 0;
			playClip (1);
		}
	}

	public void moveRight () {

		if (!panel.isVisible ()) return;

		// erase();				// no need to erase with background image

		x = x + XSTEP;

		if (x + XSIZE >= dimension.width) {	// hits right wall
			x = dimension.width - XSIZE;
			playClip (1);
		}

	}

	public BufferedImage loadImage(String filename) {
		BufferedImage bi = null;

		File file = new File (filename);
		try {
			bi = ImageIO.read(file);
		}
		catch (IOException ioe) {
			System.out.println ("Error opening file transparent.png " + ioe);
		}
		return bi;
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

  	// make a copy of the BufferedImage src

	public BufferedImage copyImage(BufferedImage src) {
		if (src == null)
			return null;

		BufferedImage copy = new BufferedImage (src.getWidth(), src.getHeight(),
							BufferedImage.TYPE_INT_ARGB);

    		Graphics2D g2d = copy.createGraphics();

    		// copy image
    		g2d.drawImage(src, 0, 0, null);
    		g2d.dispose();

    		return copy; 
	  }

}