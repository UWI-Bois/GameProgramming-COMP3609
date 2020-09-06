import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.event.*;
import java.awt.Color;
import javax.swing.*;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements
					Runnable,
					KeyListener {

	private Bat bat = null;
	private Ball ball = null;
	private Animation animation = null;
	private long animStart;

	AudioClip playSound = null;

	private Thread gameThread;
	boolean isRunning;

	private Image bgImage;

	public GamePanel () {

		setBackground(Color.CYAN);
		addKeyListener(this);			// respond to key events
		setFocusable(true);
    		requestFocus();    			// the GamePanel now has focus, so receives key events

//		loadClips ();

		gameThread = null;
		isRunning = false;

		loadImages();

	}

	// implementation of Runnable interface

	public void run () {
		try {
			isRunning = true;
			while (isRunning) {
				gameUpdate();
				gameRender();
				Thread.sleep (200);	// increase value of sleep time to slow down ball
			}
		}
		catch(InterruptedException e) {}
	}

	// implementation of KeyListener interface

	public void keyPressed (KeyEvent e) {

		if (bat == null)
			return;

		int keyCode = e.getKeyCode();

		if (keyCode == KeyEvent.VK_LEFT) {
			bat.moveLeft();
		}
		else
		if (keyCode == KeyEvent.VK_RIGHT) {
			bat.moveRight();
		}
	}

	public void keyReleased (KeyEvent e) {

	}

	public void keyTyped (KeyEvent e) {

	}

	public void loadClips() {

		try {
			playSound = Applet.newAudioClip (
					getClass().getResource("Background.wav"));

		}
		catch (Exception e) {
			System.out.println ("Error loading sound file: " + e);
		}

	}

	public void playClip (int index) {

		if (index == 1 && playSound != null)
			playSound.play();

	}

	public Image loadImage (String fileName) {
		return new ImageIcon(fileName).getImage();
	}

	public void loadImages() {

		bgImage = loadImage("images/background.jpg");

		Image stripImage = loadImage("images/kaboom.gif");

		int imageWidth = (int) stripImage.getWidth(null) / 6;
		int imageHeight = stripImage.getHeight(null);

		System.out.println("imgWidth: " + imageWidth);
		System.out.println("imgHeight: " + imageHeight);

		animation = new Animation(this);

		for (int i=0; i<6; i++) {
/*
			BufferedImage frameImage = new BufferedImage (imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
*/
			BufferedImage frameImage = GraphicsEnvironment.getLocalGraphicsEnvironment().
				getDefaultScreenDevice().getDefaultConfiguration().
				createCompatibleImage(imageWidth, imageHeight);

			Graphics2D g = (Graphics2D) frameImage.getGraphics();

			g.drawImage(stripImage,
					0, 0, imageWidth, imageHeight,
					i*imageWidth, 0, (i*imageWidth)+imageWidth, imageHeight,
					null); 
					/*drawImage(Image img, 
					int dx1, int dy1, int dx2, int dy2, 
					int sx1, int sy1, int sx2, int sy2, 
					ImageObserver observer)
					
					*/
					//Draws as much of the specified area of the specified image as is currently available, scaling it on the fly to fit inside the specified area of the destination drawable surface.

			animation.addFrame(frameImage, 50);
		}
	}

	public void gameUpdate () {
		animation.update();
		ball.move();
	}

	public void gameRender () {				// draw the game objects

		Graphics2D g2 = (Graphics2D) getGraphics();	// get the graphics context for the panel
		g2.drawImage(bgImage, 0, 0, 700, 500, null);	// draw the background image
		animation.draw(g2);				// draw the animation
		ball.draw(g2);					// draw the ball
		bat.draw(g2);					// draw the bat
	}

	public void startGame() {				// initialise and start the game thread

		if (gameThread == null) {
			isRunning = true;
			bat = new Bat (this);
			ball = new Ball (this, bat);
//			playSound.loop();
			gameThread = new Thread(this);
			gameThread.start();
		}
	}

	public void endGame() {					// end the game thread

		if (isRunning) {
			isRunning = false;
//			playSound.stop();
		}
	}
}