import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import java.util.Random;
import java.applet.AudioClip;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.io.IOException;

import javax.sound.sampled.*;

public class Ball {

	private static final int XSIZE = 10;
	private static final int YSIZE = 10;
	private static final int DY = 10;

	private JPanel panel;
	private Bat bat;
	private Random random;
	private int x;
	private int y;

	Clip hitBatSound;
	Clip fallOffSound;

	Graphics2D g2;
	private Color backgroundColor;
	private Dimension dimension;

	public Ball (JPanel p, Bat b) {
		panel = p;
		bat = b;
		Graphics g = panel.getGraphics ();
		g2 = (Graphics2D) g;
		dimension = panel.getSize();
		backgroundColor = panel.getBackground ();

		random = new Random();
		setPosition();					// set initial position of ball

		loadClips();
	}

	public void setPosition () {
		x = random.nextInt(dimension.width - XSIZE);	// randomly generate x location
		y = 0;						// set y to top of the panel
	}

	public void draw () {
		g2.setColor (Color.RED);
		g2.fill (new Ellipse2D.Double (x, y, XSIZE, YSIZE));
	}

	public void erase () {
		g2.setColor (backgroundColor);
		g2.fill (new Ellipse2D.Double (x, y, XSIZE, YSIZE));
	}

	public Rectangle2D.Double getBoundingRectangle() {
		return new Rectangle2D.Double (x, y, XSIZE, YSIZE);
	}

	public boolean batHitsBall () {

		Rectangle2D.Double rectBall = getBoundingRectangle();
		Rectangle2D.Double rectBat = bat.getBoundingRectangle();

		if (rectBall.intersects(rectBat))
			return true;
		else
			return false;
	}

	public boolean isOffScreen () {

		if (y + YSIZE > dimension.height)
			return true;
		else
			return false;
	}


	public void move () {

		if (!panel.isVisible ()) return;

		erase();

		y = y + DY;

		boolean hitBat = batHitsBall();

		if (hitBat || isOffScreen()) {
			if (hitBat) {
				playClip (1);			// play clip if bat hits ball
			}
			else {					// play clip if ball falls out at bottom
				playClip (2);
			}

			try {					// take a rest if bat hits ball or
				Thread.sleep (2000);		//   ball falls out of play.
			}
			catch (InterruptedException e) {};

			setPosition ();				// re-set position of ball
		}
	}

	public void loadClips() {
        File audioFile1 = new File("swoosh.wav");
        File audioFile2 = new File("throw.wav");
        try {
            // Set up audio stream with respect to our audio file
            AudioInputStream audioStream1 = AudioSystem.getAudioInputStream(audioFile1);
            AudioInputStream audioStream2 = AudioSystem.getAudioInputStream(audioFile2);
            // Get the format of the file (wav, au ...)
            AudioFormat format1 = audioStream1.getFormat();
            AudioFormat format2 = audioStream2.getFormat();
            // Convert the file into a Clip
            DataLine.Info info1 = new DataLine.Info(Clip.class, format1);
            DataLine.Info info2 = new DataLine.Info(Clip.class, format2);
            // Store the file in a Clip variable
            hitBatSound = (Clip) AudioSystem.getLine(info1);
            fallOffSound = (Clip) AudioSystem.getLine(info2);
            // Open the audio stream
            hitBatSound.open(audioStream1);
			fallOffSound.open(audioStream2);

            // Keep track of whether the resources are opened or not
     //       opened = true;

        } catch (UnsupportedAudioFileException uafe) {
            System.out.println("The specified audio file is not supported.");
            uafe.printStackTrace();
        } catch (LineUnavailableException lue) {
            System.out.println("Audio line for playing back is unavailable.");
            lue.printStackTrace();
        } catch (IOException ioe) {
            System.out.println("Error playing the audio file.");
            ioe.printStackTrace();
        }

	}

	public void playClip (int index) {

		if (index == 1 && hitBatSound != null)
			hitBatSound.start();

		else
		if (index == 2 && fallOffSound != null)
			fallOffSound.start();

	}

}