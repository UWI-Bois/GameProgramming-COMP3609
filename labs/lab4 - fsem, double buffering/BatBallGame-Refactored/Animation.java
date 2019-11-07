import java.awt.Image;
import java.util.ArrayList;
import java.awt.Graphics;
import javax.swing.*;
import java.util.Random;

/**
    The Animation class manages a series of images (frames) and
    the amount of time to display each frame.
*/
public class Animation extends Sprite {

    private ArrayList frames;			// collection of images for animation frames
    private int currFrameIndex;			// current frame being displayed
    private long animTime;			// time that the animation has run for already
    private long startTime;			// start time of the animation
    private long totalDuration;			// total duration of the animation
    private Random random;

    /**
        Creates a new, empty Animation.
    */

	public Animation (JFrame f, 
		     int x, int y, int dx, int dy, 
		     int xSize, int ySize, 
		     String filename) {
		super(f, x, y, dx, dy, xSize, ySize, filename);

        	frames = new ArrayList();
        	totalDuration = 0;
		random = new Random();
        	start();
    	}


    /**
        Adds an image to the animation with the specified
        duration (time to display the image).
    */

    public synchronized void addFrame(Image image, long duration)
    {
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
            animTime += elapsedTime;			// add elapsed time to amount of time animation has run for
            if (animTime >= totalDuration) {		// if the time animation has run for > total duration
                animTime = animTime % totalDuration;	//    reset time animation has run for
                currFrameIndex = 0;			//    reset current frame to first frame
            }

            while (animTime > getFrame(currFrameIndex).endTime) {
                currFrameIndex++;			// set frame corresponding to time animation has run for
            }
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


    /**
        Gets this Animation's current image. Returns null if this
        animation has no images.
    */
    public synchronized Image getImage() {
        if (frames.size() == 0) {
            return null;
        }
        else {
            return getFrame(currFrameIndex).image;
        }
    }

    public void draw (Graphics g) {			// draw the current frame
        g.drawImage(getImage(), x, y, xSize, ySize, null);
    }

    public int getNumFrames() {				// find out how many frames in animation
	return frames.size();
    }

    private AnimFrame getFrame(int i) {
        return (AnimFrame)frames.get(i);
    }

    private class AnimFrame {

        Image image;
        long endTime;

        public AnimFrame(Image image, long endTime) {
            this.image = image;
            this.endTime = endTime;
        }
    }
}
