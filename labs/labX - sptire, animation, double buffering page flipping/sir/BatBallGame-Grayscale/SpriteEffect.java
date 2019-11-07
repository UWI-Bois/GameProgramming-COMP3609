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

	private static final int XSIZE = 80;		// width of the bat
	private static final int YSIZE = 80;		// height of the bat
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

	int time, timeChange;				// to control when the image is grayed
	boolean originalImage, grayImage, sepiaImage;

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

		time = 0;				// range is 0 to 10
		timeChange = 1;				// set to 1
		originalImage = false;
		grayImage = false;
		sepiaImage = true;

		spriteImage = loadImage("transparent.png");
		copy = copyImage(spriteImage);		//  copy original image

	}

	public int toGray (int pixel) {

  		int alpha, red, green, blue, gray;
		int newPixel;

		alpha = (pixel >> 24) & 255;
		red = (pixel >> 16) & 255;
		green = (pixel >> 8) & 255;
		blue = pixel & 255;

		// Calculate the value for gray

		gray = (red + green + blue) / 3;

		// Set red, green, and blue channels to gray

		red = green = blue = gray;

		newPixel = blue | (green << 8) | (red << 16) | (alpha << 24);
		return newPixel;
	}

	public int toSepia (int pixel) {
	
 		int alpha, red, green, blue;
		int newRed, newGreen, newBlue;
		int newPixel;

		alpha = (pixel >> 24) & 255;
		red = (pixel >> 16) & 255;
		green = (pixel >> 8) & 255;
		blue = pixel & 255;

		// Calculate the new values to get sepia

		newRed = (int) (0.393 * red + 0.769 * green + 0.189 * blue);
		newGreen = (int) (0.349 * red + 0.686 * green + 0.168 * blue);
		newBlue = (int) (0.272 * red + 0.534 * green + 0.131 * blue);

		newRed = Math.min(255, newRed);
		newGreen = Math.min(255, newGreen);
		newBlue = Math.min(255, newBlue);

		// Set red, green, and blue channels

		newPixel = newBlue | (newGreen << 8) | (newRed << 16) | (alpha << 24);
		return newPixel;
	}

	public void draw (Graphics2D g2) {

/*
		if (time < 10) {			// no change; draw copy and return
			g2.drawImage(copy, x, y, XSIZE, YSIZE, null);
			return;
		}
							// change from gray to colour or colour to gray
*/
		copy = copyImage(spriteImage);		//  copy original image

		if (originalImage) {			// draw copy (already in colour) and return
			g2.drawImage(copy, x, y, XSIZE, YSIZE, null);
			return;
		}
		
							// change to gray/sepia and then draw
		int imWidth = copy.getWidth();
		int imHeight = copy.getHeight();

    		int [] pixels = new int[imWidth * imHeight];
    		copy.getRGB(0, 0, imWidth, imHeight, pixels, 0, imWidth);

    		int alpha, red, green, blue, gray;

		for (int i=0; i<pixels.length; i++) {
			if (grayImage)
				pixels[i] = toGray(pixels[i]);
			else
			if (sepiaImage)
				pixels[i] = toSepia(pixels[i]);
		}
  
    		copy.setRGB(0, 0, imWidth, imHeight, pixels, 0, imWidth);	

		g2.drawImage(copy, x, y, XSIZE, YSIZE, null);

	}

	public Rectangle2D.Double getBoundingRectangle() {
		return new Rectangle2D.Double (x, y, XSIZE, YSIZE);
	}

	public void update() {				// modify time and change the effect if necessary
	
		time = time + timeChange;

		if (time < 10) {
			originalImage = false;
			grayImage = false;
			sepiaImage = true;
		}
		else
		if (time < 20) {
			originalImage = true;
			grayImage = false;
			sepiaImage = false;
		}
		else 
		if (time < 30) {
			originalImage = false;
			grayImage = true;
			sepiaImage = true;
		}
		else if (time < 40) {
			originalImage = true;
			grayImage = false;
			sepiaImage = false;
		}
		else {		
			originalImage = false;
			grayImage = false;
			sepiaImage = true;
			time = 0;
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