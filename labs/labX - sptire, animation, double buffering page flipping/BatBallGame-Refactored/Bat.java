import java.util.Random;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import java.applet.AudioClip;
import java.awt.event.*;
import java.awt.*;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.io.IOException;

import javax.sound.sampled.*;

public class Bat {

	private static final int XSIZE = 30;		// width of the bat
	private static final int YSIZE = 10;		// height of the bat
	private static final int XSTEP = 5;		// amount of pixels to move in one keystroke
	private static final int YPOS = 350;		// vertical position of the bat

	private JPanel panel;
	private Dimension dimension;
	private int x;
	private int y;

	Graphics2D g2;
	private Color backgroundColor;
	Clip hitBatSound = null;

	public Bat (JPanel p) {
		panel = p;
		Graphics g = panel.getGraphics ();
		g2 = (Graphics2D) g;
		backgroundColor = panel.getBackground ();
		loadClips();

		dimension = panel.getSize();
		Random random = new Random();
		x = random.nextInt (dimension.width - XSIZE);
		y = YPOS;
	}

	public void draw () {
		g2.setColor (Color.GREEN);
		g2.fill (new Rectangle2D.Double (x, y, XSIZE, YSIZE));
	}

	public void erase () {
		g2.setColor (backgroundColor);
		g2.fill (new Rectangle2D.Double (x, y, XSIZE, YSIZE));
	}

	public Rectangle2D.Double getBoundingRectangle() {
		return new Rectangle2D.Double (x, y, XSIZE, YSIZE);
	}

	public void moveLeft () {

		if (!panel.isVisible ()) return;

		erase();

		x = x - XSTEP;

		if (x < 0) {					// hits left wall
			x = 0;
			playClip (1);
		}

	}

	public void moveRight () {

		if (!panel.isVisible ()) return;

		erase();

		x = x + XSTEP;

		if (x + XSIZE >= dimension.width) {		// hits right wall
			x = dimension.width - XSIZE;
			playClip (1);
		}

	}

	public void loadClips() {
        File audioFile = new File("swoosh.wav");
        try {
            // Set up audio stream with respect to our audio file
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            // Get the format of the file (wav, au ...)
            AudioFormat format = audioStream.getFormat();
            // Convert the file into a Clip
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            // Store the file in a Clip variable
            hitBatSound = (Clip) AudioSystem.getLine(info);
            // Open the audio stream
            hitBatSound.open(audioStream);

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

	}

}