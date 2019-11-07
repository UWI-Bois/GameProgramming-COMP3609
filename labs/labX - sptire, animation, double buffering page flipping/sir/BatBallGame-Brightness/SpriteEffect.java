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
import java.lang.Math;

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

	int brightness, brightnessChange;		// to alter the brightness of the image

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

		brightness = 0;				// range is -50% to 100%
		brightnessChange = 5;			// set to 5

		spriteImage = loadImage("transparent.png");

	}

	public void draw (Graphics2D g2) {

		copy = copyImage(spriteImage);		// make copy of image for brightness effect

		int imWidth = copy.getWidth();
		int imHeight = copy.getHeight();

    		int [] pixels = new int[imWidth * imHeight];
    		copy.getRGB(0, 0, imWidth, imHeight, pixels, 0, imWidth);

    		int alpha, red, green, blue;

		for (int i=0; i<pixels.length; i++) {
			alpha = (pixels[i] >> 24) & 255;
			red = (pixels[i] >> 16) & 255;
			green = (pixels[i] >> 8) & 255;
			blue = pixels[i] & 255;

			// Calculate the brightness as a percentage of the original

			red = red + (brightness * red) / 100;
			green = green + (brightness * green) / 100;
			blue = blue + (brightness * blue) / 100;

			// Check the boundaries for 8-bit RGB components

			red = Math.min(Math.max(0, red), 255);
			green = Math.min(Math.max(0, green), 255);
			blue = Math.min(Math.max(0, blue), 255);

			pixels[i] = blue | (green << 8) | (red << 16) | (alpha << 24);

		}
  
    		copy.setRGB(0, 0, imWidth, imHeight, pixels, 0, imWidth);	

		g2.drawImage(copy, x, y, XSIZE, YSIZE, null);

	}

	public Rectangle2D.Double getBoundingRectangle() {
		return new Rectangle2D.Double (x, y, XSIZE, YSIZE);
	}

	public void update() {				// modify brightness (-50% to 100%)
	
		brightness = brightness + brightnessChange;

		if (brightness > 100) {
			brightness = 100;
			brightnessChange = -1 * brightnessChange;
		}
		else
		if (brightness < -50) {
			brightness = -50;
			brightnessChange = -1 * brightnessChange;
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