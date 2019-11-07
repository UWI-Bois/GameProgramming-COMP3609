import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import java.util.Random;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.io.IOException;

import javax.sound.sampled.*;



public class Ball extends Thread {

   private static final int XSIZE = 10;
   private static final int YSIZE = 10;
   private static final int DY = 10;

   private JPanel panel;
   private Bat bat;
   private Random random;
   private int x;
   private int y;

   private boolean opened = false;
   Graphics2D g2;
   private Color backgroundColor;
   private Dimension dimension;

   AudioInputStream audioIn;
   Clip audioClip;

   public Ball(JPanel p, Bat b) {
      panel = p;
      bat = b;
      Graphics g = panel.getGraphics ();
      g2 = (Graphics2D) g;
      dimension = panel.getSize();
      backgroundColor = panel.getBackground ();

      random = new Random();
      x = random.nextInt(dimension.width - XSIZE);	// randomly generate x location
      y = 0;										// set y to top of the panel

      loadClip();
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

   public void move () {

      if (!panel.isVisible ()) return;

      y = y + DY;

      Rectangle2D.Double rectBall = getBoundingRectangle();
      Rectangle2D.Double rectBat = bat.getBoundingRectangle();
      Boolean batHitsBall = rectBall.intersects(rectBat);

      if (batHitsBall || y + YSIZE > dimension.height) {
         if (batHitsBall) {
            playClip();  							// play clip if bat hits ball
         }
         else {
            										// play clip if ball falls out at bottom
         }

         try {										// take a rest if bat hits ball or
            Thread.sleep (2000); 					//  ball falls out of play.
         }
         catch (InterruptedException e) {};

         x = random.nextInt(dimension.width - XSIZE);
         											// randomly generate x location
         y = 0;										// set y to top of the panel
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
            opened = true;

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

    public void run () {
      try {
     	draw ();
        while (true) {
            erase();
	        move ();
            draw();
            sleep (200);		// increase value of sleep time to slow down ball
         }
      }
      catch(InterruptedException e) {}
   }

}