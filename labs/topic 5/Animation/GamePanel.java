import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.event.*;
import java.awt.Color;
import javax.swing.*;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;
import java.awt.Image;

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

		loadClips ();

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
		if (keyCode == KeyEvent.VK_UP) {
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

		Image player1 = loadImage("images/player1.png");
		Image player2 = loadImage("images/player2.png");
		Image player3 = loadImage("images/player3.png");
	
		// create animation object and insert frames

		animation = new Animation(this);

		animation.addFrame(player1, 250);
		animation.addFrame(player2, 150);
		animation.addFrame(player1, 150);
		animation.addFrame(player2, 150);
		animation.addFrame(player3, 200);
		animation.addFrame(player2, 150);
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
			playSound.loop();
			gameThread = new Thread(this);
			gameThread.start();
		}
	}

	public void endGame() {					// end the game thread

		if (isRunning) {
			isRunning = false;
			playSound.stop();
		}
	}
}