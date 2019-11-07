import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Dimension;
import java.awt.event.*;
import java.awt.Color;
import javax.swing.*;
import java.awt.geom.Rectangle2D;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.geom.Ellipse2D;


public class GamePanel extends JPanel implements Runnable {

	private Bat bat;
	private Ball ball;
	private Player player;

	private Thread gameThread;
	boolean isRunning;

	private Image bgImage;

	private BufferedImage image;			// back buffer

	public GamePanel () {

		setBackground(Color.CYAN);
		setFocusable(true);
    		requestFocus();    			// the GamePanel now has focus, so receives key events
		gameThread = null;
		isRunning = false;

        	bgImage = loadImage ("background.jpg");
		
		image = new BufferedImage (700, 500, BufferedImage.TYPE_INT_RGB);
							// create image for back buffer

	}

	// implementation of Runnable interface

	public void run () {
		try {
			isRunning = true;
			while (isRunning) {
				//gameUpdate();		// don't update anything, just for viewing
				gameRender();
				Thread.sleep (200);	// increase value of sleep time to slow down ball
			}
		}
		catch(InterruptedException e) {}
	}

	public Image loadImage (String fileName) {
		return new ImageIcon(fileName).getImage();
	}

	public void addBackground () {
		Graphics2D imageContext = (Graphics2D) image.getGraphics();
		imageContext.drawImage(bgImage, 0, 0, 700, 500, null);
		Graphics2D g2 = (Graphics2D) getGraphics();
		g2.drawImage(image, 0, 0, 700, 500, null);
		g2.dispose();
	}

	public void addBat () {
		Graphics2D imageContext = (Graphics2D) image.getGraphics();
		Bat bat = new Bat(this);
		bat.draw(imageContext);
		Graphics2D g2 = (Graphics2D) getGraphics();
		g2.drawImage(image, 0, 0, 700, 500, null);
		g2.dispose();
	}

	public void addBall () {
		Graphics2D imageContext = (Graphics2D) image.getGraphics();
		Ball ball = new Ball(this);
		ball.draw(imageContext);
		Graphics2D g2 = (Graphics2D) getGraphics();
		g2.drawImage(image, 0, 0, 700, 500, null);
		g2.dispose();
	}

	public void addPlayer () {

		Graphics2D imageContext = (Graphics2D) image.getGraphics();
		Player player = new Player(this);
		player.draw(imageContext);
		Graphics2D g2 = (Graphics2D) getGraphics();
		g2.drawImage(image, 0, 0, 700, 500, null);
		g2.dispose();

	}

	public void addShape () {
		Graphics2D imageContext = (Graphics2D) image.getGraphics();
		Shape shape = new Shape (this);
		shape.draw (imageContext);
		Graphics2D g2 = (Graphics2D) getGraphics();
		g2.drawImage(image, 0, 0, 700, 500, null);
		g2.dispose();

	}

	public void savePNG () {

		try {
			File outputfile = new File("screen.png");
			ImageIO.write(image, "png", outputfile);
		} 
		catch (IOException e) {
 			System.out.println ("Error saving to PNG File: " + e);
		}
	}

	public void gameUpdate () {
		ball.move();
	}

	public void gameRender () {				// draw the game objects

		Graphics2D g2 = (Graphics2D) getGraphics();	// get the graphics context for the panel
		g2.drawImage(bgImage, 0, 0, 700, 500, null);	// draw the background image
		ball.draw(g2);					// draw the ball
		bat.draw(g2);					// draw the bat
	}	

	public void startGame() {				// initialise and start the game thread 

		if (gameThread == null) {
			isRunning = true;
			bat = new Bat (this);
			ball = new Ball (this);
			//playSound.loop();
			gameThread = new Thread(this);
			gameThread.start();
		}
	}

	public void endGame() {					// end the game thread

		if (isRunning) {
			isRunning = false;
		}
	}
}