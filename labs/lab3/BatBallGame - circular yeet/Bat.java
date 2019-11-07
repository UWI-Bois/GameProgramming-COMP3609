import java.util.Random;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
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


   AudioInputStream audioIn;
   Clip audioClip;
   private boolean opened = false;

	public Bat (JPanel p) {
		panel = p;
		Graphics g = panel.getGraphics ();
		g2 = (Graphics2D) g;
		backgroundColor = panel.getBackground ();
		loadClip();

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

		x = x - XSTEP;

		if (x < 0) {
			x = 0;
			playClip();
		}
	}

	public void moveRight () {

		if (!panel.isVisible ()) return;

		x = x + XSTEP;

		if (x + XSIZE >= dimension.width) {
			x = dimension.width - XSIZE;
			playClip();
		}
	}

	public void processKey(KeyEvent e) {

		int keyCode = e.getKeyCode();

		if (keyCode == KeyEvent.VK_LEFT) {
			erase();
			moveLeft();
			draw();
		}
		else
		if (keyCode == KeyEvent.VK_UP) {
			erase();
			moveRight();
			draw();
		}
	}

    public void loadClip() {
        File audioFile = new File("SonicBoom.wav");
        try {
            // Set up audio stream with respect to our audio file
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            // Get the format of the file (wav, au ...)
            AudioFormat format = audioStream.getFormat();
            // Convert the file into a Clip
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            // Store the file in a Clip variable
            audioClip = (Clip) AudioSystem.getLine(info);
            // Open the audio stream
            audioClip.open(audioStream);

            FloatControl gainControl =
                (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10.0f); // Reduce volume by 10 decibels

            // Keep track of whether the resources are opened or not
   //         opened = true;

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

    public void playClip() {

       if (opened){
    	audioClip.setMicrosecondPosition(0);
         audioClip.start();
       }
    }
}