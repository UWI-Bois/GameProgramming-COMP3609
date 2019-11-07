import java.util.ArrayList;
import java.awt.Graphics;
import javax.swing.*;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

	/**
	    The Animation class manages a series of images (frames) and
	    the amount of time to display each frame.
	*/

	public class Animation extends Sprite {

    	protected ArrayList frames;		// collection of images for animation frames
    	protected int currFrameIndex;		// current frame being displayed
    	protected long animTime;		// time that the animation has run for already
    	protected long startTime;		// start time of the animation
    	protected long totalDuration;		// total duration of the animation

	/**
	    Creates a new, empty Animation object.
	*/

	public Animation (JFrame f, 
		     int x, int y, int dx, int dy, 
		     int xSize, int ySize, 
		     String filename) {
		super(f, x, y, dx, dy, xSize, ySize, filename);

        	frames = new ArrayList();
		totalDuration = 0;

        	start();
    	}


	/**
	    Adds an image to the animation with the specified
	    duration (time to display the image).
	*/

	public synchronized void addFrame(BufferedImage image, long duration) {
        	totalDuration += duration;
        	frames.add(new AnimFrame(image, totalDuration));
	}


	/**
	    Starts this animation over from the beginning.
	*/

	public synchronized void start() {
		animTime = 0;				// reset time animation has run for to zero
		currFrameIndex = 0;			// reset current frame to first frame
		startTime = System.currentTimeMillis();	// reset start time to current time
	}


	/**
	    Updates this animation's current image (frame), if
	    neccesary.
	*/

	public synchronized void update() {
		long currTime = System.currentTimeMillis();	// find the current time
		long elapsedTime = currTime - startTime;	// find how much time has elapsed since last update
		startTime = currTime;				// set start time to current time

		if (frames.size() > 1) {
			animTime += elapsedTime;		// add elapsed time to amount of time animation has run for
			if (animTime >= totalDuration) {	// if the time animation has run for > total duration
				animTime = animTime % totalDuration;	
								//    reset time animation has run for
				currFrameIndex = 0;		//    reset current frame to first frame
			}

			while (animTime > getFrame(currFrameIndex).endTime) {
				currFrameIndex++;		// set frame corresponding to time animation has run for
			}
		}
	
	}

	/**
	    Gets this Animation's current image. Returns null if this
	    animation has no images.
	*/

	public synchronized BufferedImage getImage() {
		if (frames.size() == 0) {
			return null;
		}
	        else {
			return (BufferedImage)frames.get(currFrameIndex);
		}
	}

	public void draw (Graphics g) {				// draw the current frame
		g.drawImage(getImage(), 0, 0, null);
	}

	public int getNumFrames() {				// find out how many frames in animation
		return frames.size();
	}

	public AnimFrame getFrame(int i) {
		return (AnimFrame)frames.get(i);
	}

	public class AnimFrame {

		BufferedImage image;
		long endTime;

		public AnimFrame(BufferedImage image, long endTime) {
			this.image = image;
			this.endTime = endTime;
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

}
