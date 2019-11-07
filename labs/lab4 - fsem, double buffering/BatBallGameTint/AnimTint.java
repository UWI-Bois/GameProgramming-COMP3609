import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.*;
import java.awt.image.BufferedImage;

	/**
	    The AnimTint class is an Animation object that moves around the
	    screen. A tint is applied to the frames while the Animation moves
	    around. When the amount of tint reaches a maximum value it is 
	    reset to a minimum value.
	*/

	public class AnimTint extends Animation {

		private static float MIN_MIX_VALUE = 0.0f;
							// smallest percentage for tint
		private static float MAX_MIX_VALUE = 0.25f;
							// highest percentage for tint
		private static float MIX_VALUE_CHANGE = 0.01f;
							// amount to change mixValue each time
		private ArrayList framesCopy;		// copy of frames for effects
		private float mixValue;			// percentage tint to apply
		private Color mixColour;		// tint colour

	/**
	    Creates a new, empty Animation, loads the images, and
	    adds them to the Animation. A copy of the Animation frames
	    is also made for the tint effect.
	*/

	public AnimTint (JFrame f, 
		     int x, int y, int dx, int dy, 
		     int xSize, int ySize, 
		     String filename) {
		super(f, x, y, dx, dy, xSize, ySize, filename);

		framesCopy = new ArrayList();

		// load images from image files

		BufferedImage player1 = loadImage("images/player1.png");
		BufferedImage player2 = loadImage("images/player2.png");
		BufferedImage player3 = loadImage("images/player3.png");
	
		// insert frames

		addFrame(player1, 250);
		addFrame(player2, 150);
		addFrame(player1, 150);
		addFrame(player2, 150);
		addFrame(player3, 200);
		addFrame(player2, 150);

		copyAnimation (framesCopy, frames);
		mixValue = MIN_MIX_VALUE;		// start from smallest percentage for tint
		mixColour = new Color (255, 0, 0);	// set tint to this colour

        	start();
    	}

	public void copyAnimation (ArrayList framesCopy, ArrayList frames) {
		framesCopy.clear();
		for (int i=0; i<frames.size(); i++) {
			AnimFrame frame = (AnimFrame)frames.get(i);
			BufferedImage copy = copyImage(frame.image);
			framesCopy.add(copy);
		}
	}

	/**
	    Updates this animation's current image (frame), if
	    neccesary and applies the tint effect to the image.
	*/

	public synchronized void update() {

		super.update();

		updatePosition();

	}


	public void updatePosition() {
		
		x = x + dx; 

		if (x < 0) {
			x = 0;
			dx = dx * -1;
		}
		else
		if (x + xSize > dimension.width) {
			x = dimension.width - xSize;
			dx = dx * -1;

		}

		y = y + dy;
		if (y < 0) {
			y = 0;
			dy = -dy;
		}
		else
		if (y + ySize > dimension.height) {
			y = dimension.height - ySize;
			dy = -dy;
		}

	}

	public void draw (Graphics g) {			// draw the current frame
   		BufferedImage copy = getImage();

		int imWidth = copy.getWidth();
		int imHeight = copy.getHeight();

    		int [] pixels = new int[imWidth * imHeight];
    		copy.getRGB(0, 0, imWidth, imHeight, pixels, 0, imWidth);

    		int alpha, red, green, blue;
		for (int i = 0; i < pixels.length; i++) {
			alpha = (pixels[i] >> 24) & 255;
			red = (pixels[i] >> 16) & 255;
			green = (pixels[i] >> 8) & 255;
			blue = pixels[i]& 255;
			// get the tint color to apply
			int mixRed = mixColour.getRed();
			int mixGreen = mixColour.getGreen();
			int mixBlue = mixColour.getBlue();
			// apply the tint to the red, green, and blue components
			red = (int) (red * (1.0f - mixValue) + mixRed * mixValue);
			green = (int) (green * (1.0f - mixValue) + mixGreen * mixValue);
			blue = (int) (blue * (1.0f - mixValue) + mixBlue * mixValue);

			int newValue = blue | (green << 8) | (red << 16) | (alpha << 24); // what is this?
		}
    		copy.setRGB(0, 0, imWidth, imHeight, pixels, 0, imWidth);	

		g.drawImage(copy, x, y, xSize, ySize, null);

	}


	/**
	    Gets this Animation's current image. Returns null if this
	    animation has no images.
	*/

	public synchronized BufferedImage getImage() {
		if (framesCopy.size() == 0) {
			return null;
		}
		else {
			return (BufferedImage)framesCopy.get(currFrameIndex);
		}
	}


  	// make a copy of the BufferedImage src

	public BufferedImage copyImage(BufferedImage src) {
		if (src == null)
			return null;

		BufferedImage copy = new BufferedImage (src.getWidth(), src.getHeight(),
							BufferedImage.TYPE_INT_ARGB);

    		Graphics g = copy.createGraphics();

    		// copy image

    		g.drawImage(src, 0, 0, null);
    		g.dispose();

    		return copy; 
	}
}