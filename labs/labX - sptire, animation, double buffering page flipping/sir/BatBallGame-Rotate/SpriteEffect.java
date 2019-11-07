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
import java.awt.geom.AffineTransform;
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

	Graphics2D g2;
	private Color backgroundColor;
	AudioClip hitWallSound = null;

	float angle, angleChange;			// angle controls the amount of rotation

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

		angle = 10;				// set to 10 degrees
		angleChange = 10;			// change to angle each time in update()

		spriteImage = loadImage("transparent.png");
	}

	public void draw (Graphics2D g2) {

		int width, height;

		width = spriteImage.getWidth();		// find width of image
		height = spriteImage.getHeight();	// find height of image

		BufferedImage dest = new BufferedImage (width, height,
							BufferedImage.TYPE_INT_ARGB);

    		Graphics2D g2d = dest.createGraphics();

   		AffineTransform origAT = g2d.getTransform(); 
							// save original transform
  
    		// rotate the coordinate system of the destination image around its center
    
		AffineTransform rotation = new AffineTransform(); 
    		rotation.rotate(Math.toRadians(angle), width/2, height/2); 
    		g2d.transform(rotation); 

    		g2d.drawImage(spriteImage, 0, 0, null);	// copy in the image

    		g2d.setTransform(origAT);    		// restore original transform

    		g2.drawImage(dest, x, y, XSIZE, YSIZE, null);

   		g2d.dispose();
	}

	public Rectangle2D.Double getBoundingRectangle() {
		return new Rectangle2D.Double (x, y, XSIZE, YSIZE);
	}

	public void update() {				// modify angle of rotation
	
		angle = angle + angleChange;

		if (angle >= 360)			// reset to 10 degrees if 360 degrees reached
			angle = 10;

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

}