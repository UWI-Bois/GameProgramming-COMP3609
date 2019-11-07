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
	private BufferedImage copy;

	Graphics2D g2;
	private Color backgroundColor;
	AudioClip hitWallSound = null;

	int time, timeChange;				// time to control the teleporting effect

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

		time = 0;				// set to 0
		timeChange = 2;				// set to 2

		spriteImage = loadImage("transparent.png");
		copy = copyImage(spriteImage);		// make copy of image for teleporting effect

	}

	public void draw (Graphics2D g2) {

		if (time == 10)
			eraseImageParts(copy, 11);
		else
		if (time == 20)
			eraseImageParts(copy, 7);
		else
		if (time == 30)
			eraseImageParts(copy, 5);
		else
		if (time == 40)
			eraseImageParts(copy, 3);
		else
		if (time == 50)
			eraseImageParts(copy, 2);
		else
		if (time == 60)
			eraseImageParts(copy, 1);
		else
		if (time == 70)
			copy = copyImage(spriteImage);

		g2.drawImage(copy, x, y, XSIZE, YSIZE, null);

	}

	public Rectangle2D.Double getBoundingRectangle() {
		return new Rectangle2D.Double (x, y, XSIZE, YSIZE);
	}

	public void update() {				// modify time
	
		time = time + timeChange;

		if (time > 70)			
			time = 0;

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

 	// Change some of the image's pixels to 0's, which will become 
     	//  transparent in an image with an alpha channel, black otherwise.

  	public void eraseImageParts(BufferedImage im, int spacing) {

    		if (im == null) {
      			System.out.println("eraseImageParts: input image is null");
      			return;
    		}

    		int imWidth = im.getWidth();
    		int imHeight = im.getHeight();

    		int [] pixels = new int[imWidth * imHeight];
    		im.getRGB(0, 0, imWidth, imHeight, pixels, 0, imWidth);

    		int i = 0;
    		while (i < pixels.length) {
      			pixels[i] = 0;    // make transparent (or black if no alpha)
      			i = i + spacing;
		}
  
    		im.setRGB(0, 0, imWidth, imHeight, pixels, 0, imWidth);
  	}

}