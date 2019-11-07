import java.util.ArrayList;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.image.BufferedImage;

	/**
	    The AnimDisappear class is an Animation object that slowly
	    disappears and then reappears and the effect is repeated.
	*/

	public class AnimDisappear extends Animation {

		private ArrayList framesCopy;		// copy of frames for effects
		private int alpha, alphaChange;		// alpha value (for alpha transparency byte)

	/**
	    Creates a new, empty Animation, loads the images, and
	    adds them to the Animation. A copy of the Animation frames
	    is also made for the disappearing effect.
	*/

	public AnimDisappear (JFrame f, 
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
		alpha = 255;				// set to 255 (fully opaque)
		alphaChange = 5;			// set to 15

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
	    neccesary and applies the disappearing effect to the image.
	*/

	public synchronized void update() {
//		System.out.print("update??");
		super.update();
		alpha = alpha - alphaChange;

		if (alpha <= 0)	{
			copyAnimation(framesCopy, frames);
			alpha = 255;
		}
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