import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.*;
import java.awt.image.BufferedImage;

	/**
	    The AnimFlip class is an Animation object that moves around the
	    screen. The Animation object is flipped each time it is drawn.
	*/

	public class AnimFlip extends Animation {

		private ArrayList framesCopy;		// copy of frames for effects

	/**
	    Creates a new, empty Animation, loads the images, and
	    adds them to the Animation. A copy of the Animation frames
	    is also made for the flip effect.
	*/

	public AnimFlip (JFrame f, 
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
	    neccesary as well as its position.
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

		for (int i = 0; i < imHeight/2; i++) {
			for (int j = 0; j < imWidth; j++) {
				int pixel1 = copy.getRGB(j,i);
				int pixel2 = copy.getRGB(j,imHeight-i-1);
				copy.setRGB(j, imHeight-i-1, pixel1);
				copy.setRGB(j, i, pixel2);
			}
		}
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