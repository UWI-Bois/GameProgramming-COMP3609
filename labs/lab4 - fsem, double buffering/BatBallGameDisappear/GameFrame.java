import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.applet.Applet;
import java.applet.AudioClip;

public class GameFrame extends JFrame implements Runnable, KeyListener {
  	private static final int NUM_BUFFERS = 2;	// used for page flipping

	private int pWidth, pHeight;     		// dimensions of screen

	private Thread gameThread = null;            	// the thread that controls the game
	private volatile boolean running = false;    	// used to stop the animation thread

	private AnimDisappear animation = null;		// animation sprite for disappearing effect
	private Bat bat;				// bat sprite
	private Ball ball;				// ball sprite
	private Image bgImage;				// background image
	AudioClip playSound = null;			// theme sound

  	// used at game termination
	private boolean finishedOff = false;

	// used by the quit 'button'
	private volatile boolean isOverQuitButton = false;
	private Rectangle quitButtonArea;

	// used by the pause 'button'
	private volatile boolean isOverPauseButton = false;
	private Rectangle pauseButtonArea;
	private volatile boolean isPaused = false;

	// used by the stop 'button'
	private volatile boolean isOverStopButton = false;
	private Rectangle stopButtonArea;
	private volatile boolean isStopped = false;

	// used by the show animation 'button'
	private volatile boolean isOverShowAnimButton = false;
	private Rectangle showAnimButtonArea;
	private volatile boolean isAnimShown = false;

	// used by the pause animation 'button'
	private volatile boolean isOverPauseAnimButton = false;
	private Rectangle pauseAnimButtonArea;
	private volatile boolean isAnimPaused = false;
  
	// used for full-screen exclusive mode  
	private GraphicsDevice device;
	private Graphics gScr;
	private BufferStrategy bufferStrategy;

	public GameFrame () {
		super("Bat and Ball Game: Full Screen Exclusive Mode");

		initFullScreen();

		// create game sprites

		bat = new Bat (this, 0, 585, 7, 0, 40, 40, "images/bat.gif");
		ball = new Ball (this, bat, 0, 0, 0, 10, 20, 20, "images/ball.gif");   					animation = new AnimDisappear (this, 20, 50, 15, 15, 250, 250, "images/player1.png");

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				testMousePress(e.getX(), e.getY()); 
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				testMouseMove(e.getX(), e.getY()); 
			}
		});

		addKeyListener(this);			// respond to key events

		// specify screen areas for the buttons
		//  leftOffset is the distance of a button from the left side of the window

		int leftOffset = (pWidth - (5 * 150) - (4 * 20)) / 2;
		pauseButtonArea = new Rectangle(leftOffset, pHeight-60, 150, 40);

		leftOffset = leftOffset + 170;
		stopButtonArea = new Rectangle(leftOffset, pHeight-60, 150, 40);

		leftOffset = leftOffset + 170;
		showAnimButtonArea = new Rectangle(leftOffset, pHeight-60, 150, 40);

		leftOffset = leftOffset + 170;
		pauseAnimButtonArea = new Rectangle(leftOffset, pHeight-60, 150, 40);

		leftOffset = leftOffset + 170;
		quitButtonArea = new Rectangle(leftOffset, pHeight-60, 150, 40);

		loadImages();
		loadClips();
		startGame();
	}

	private void initFullScreen() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		device = ge.getDefaultScreenDevice();

		setUndecorated(true);	// no menu bar, borders, etc.
		setIgnoreRepaint(true);	// turn off all paint events since doing active rendering
		setResizable(false);	// screen cannot be resized
		
		if (!device.isFullScreenSupported()) {
			System.out.println("Full-screen exclusive mode not supported");
			System.exit(0);
		}

		device.setFullScreenWindow(this); // switch on full-screen exclusive mode

		// we can now adjust the display modes, if we wish

		showCurrentMode();

		pWidth = getBounds().width;
		pHeight = getBounds().height;

		try {
			createBufferStrategy(NUM_BUFFERS);
		}
		catch (Exception e) {
			System.out.println("Error while creating buffer strategy " + e); 
			System.exit(0);
		}

		bufferStrategy = getBufferStrategy();
	}

	// this method creates and starts the game thread

	private void startGame() { 
		if (gameThread == null || !running) {
			gameThread = new Thread(this);
			gameThread.start();
			playSound.loop();
		}
	}
    
	/* This method handles mouse clicks on one of the buttons
	   (Pause, Stop, Show Anim, Pause Anim, and Quit).
	*/

	private void testMousePress(int x, int y) {

		if (isStopped && !isOverQuitButton) 	// don't do anything if game stopped
			return;

		if (isOverStopButton) {			// mouse click on Stop button
			isStopped = true;
			isPaused = false;
		}
		else
		if (isOverPauseButton) {		// mouse click on Pause button
			isPaused = !isPaused;     	// toggle pausing
		}
		else
		if (isOverShowAnimButton && !isPaused) {// mouse click on Show Anim button
			if (isAnimShown)		// make invisible if visible
		 		isAnimShown = false;
			else {				// make visible if invisible
				isAnimShown = true;
				isAnimPaused = false;	// always animate when making visible
			}
		}
		else
		if (isOverPauseAnimButton) {		// mouse click on Pause Anim button
			isAnimPaused = !isAnimPaused;	// toggle pausing
		}
		else if (isOverQuitButton) {		// mouse click on Quit button
			running = false;		// set running to false to terminate
		}
  	}


	/* This method checks to see if the mouse is currently moving over one of
	   the buttons (Pause, Stop, Show Anim, Pause Anim, and Quit). It sets a
	   boolean value which will cause the button to be displayed accordingly.
	*/

	private void testMouseMove(int x, int y) { 
		if (running) {
			isOverPauseButton = pauseButtonArea.contains(x,y) ? true : false;
			isOverStopButton = stopButtonArea.contains(x,y) ? true : false;
			isOverShowAnimButton = showAnimButtonArea.contains(x,y) ? true : false;
			isOverPauseAnimButton = pauseAnimButtonArea.contains(x,y) ? true : false;
			isOverQuitButton = quitButtonArea.contains(x,y) ? true : false;
		}
	}

	// implementation of KeyListener interface

	public void keyPressed (KeyEvent e) {

		int keyCode = e.getKeyCode();
         
		if ((keyCode == KeyEvent.VK_ESCAPE) || (keyCode == KeyEvent.VK_Q) ||
             	   (keyCode == KeyEvent.VK_END)) {
           		running = false;		// user can quit anytime by pressing
			return;				//  one of these keys (ESC, Q, END)
         	}	

		if (bat == null || isPaused || isStopped)		
			// don't do anything if either condition is true
			return;

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


	// implmentation of MousePressedListener interface

	// implmentation of MouseMotionListener interface

	// The run() method implements the game loop.

	public void run() {

		running = true;
		try {
			while (running) {
	  			gameUpdate();     
	      			screenUpdate();
				Thread.sleep(200);
			}
		}
		catch(InterruptedException e) {};

		finishOff();
	}


	// This method updates the game objects (animation and ball)

	private void gameUpdate() { 

		if (!isPaused) {
			if (isAnimShown && !isAnimPaused &&!isStopped)
				animation.update();
			if (!isStopped)
				ball.update();
		}
  	}


	// This method updates the screen using double buffering / page flipping

	private void screenUpdate() { 

		try {
			gScr = bufferStrategy.getDrawGraphics();
			gameRender(gScr);
			gScr.dispose();
			if (!bufferStrategy.contentsLost())
				bufferStrategy.show();
			else
				System.out.println("Contents of buffer lost.");
      
			// Sync the display on some systems.
			// (on Linux, this fixes event queue problems)

			Toolkit.getDefaultToolkit().sync();
		}
		catch (Exception e) { 
			e.printStackTrace();  
			running = false; 
		} 
	}

	/* This method renders all the game entities to the screen: the
	   background image, the buttons, ball, bat, and the animation.
	*/

	private void gameRender(Graphics gScr){
 
		gScr.drawImage (bgImage, 0, 0, pWidth, pHeight, null);
							// draw the background image

		drawButtons(gScr);			// draw the buttons

		gScr.setColor(Color.black);

		ball.draw((Graphics2D)gScr);		// draw the ball

		bat.draw((Graphics2D)gScr);		// draw the bat

		if (isAnimShown)			// draw the animation
			animation.draw((Graphics2D)gScr);
					
		if (isStopped)				// display game over message
			gameOverMessage(gScr);
	}

	/* This method draws the buttons on the screen. The text on a button
	   is highlighted if the mouse is currently over that button AND if
	   the action of the button can be carried out at the current time.
	*/

	private void drawButtons (Graphics g) {
		Font oldFont, newFont;

		oldFont = g.getFont();		// save current font to restore when finished
	
		newFont = new Font ("TimesRoman", Font.ITALIC + Font.BOLD, 18);
		g.setFont(newFont);		// set this as font for text on buttons

    		g.setColor(Color.black);	// set outline colour of button

		// draw the pause 'button'

		g.setColor(Color.BLACK);
		g.drawOval(pauseButtonArea.x, pauseButtonArea.y, 
			   pauseButtonArea.width, pauseButtonArea.height);

		if (isOverPauseButton && !isStopped)
			g.setColor(Color.WHITE);
		else
			g.setColor(Color.RED);	

		if (isPaused && !isStopped)
			g.drawString("Paused", pauseButtonArea.x+45, pauseButtonArea.y+25);
		else
			g.drawString("Pause", pauseButtonArea.x+55, pauseButtonArea.y+25);

		// draw the stop 'button'

		g.setColor(Color.BLACK);
		g.drawOval(stopButtonArea.x, stopButtonArea.y, 
			   stopButtonArea.width, stopButtonArea.height);

		if (isOverStopButton && !isStopped)
			g.setColor(Color.WHITE);
		else
			g.setColor(Color.RED);

		if (isStopped)
			g.drawString("Stopped", stopButtonArea.x+40, stopButtonArea.y+25);
		else
			g.drawString("Stop", stopButtonArea.x+60, stopButtonArea.y+25);

		// draw the show animation 'button'

		g.setColor(Color.BLACK);
		g.drawOval(showAnimButtonArea.x, showAnimButtonArea.y, 
			   showAnimButtonArea.width, showAnimButtonArea.height);

		if (isOverShowAnimButton && !isPaused && !isStopped)
			g.setColor(Color.WHITE);
		else
			g.setColor(Color.RED);
      		g.drawString("Show Anim", showAnimButtonArea.x+35, showAnimButtonArea.y+25);

		// draw the pause anim 'button'

		g.setColor(Color.BLACK);
		g.drawOval(pauseAnimButtonArea.x, pauseAnimButtonArea.y, 
			   pauseAnimButtonArea.width, pauseAnimButtonArea.height);

		if (isOverPauseAnimButton && isAnimShown && !isPaused && !isStopped)
			g.setColor(Color.WHITE);
		else
			g.setColor(Color.RED);

		if (isAnimShown && isAnimPaused && !isStopped)
			g.drawString("Anim Paused", pauseAnimButtonArea.x+30, pauseAnimButtonArea.y+25);
		else
			g.drawString("Pause Anim", pauseAnimButtonArea.x+35, pauseAnimButtonArea.y+25);

		// draw the quit 'button'

		g.setColor(Color.BLACK);
		g.drawOval(quitButtonArea.x, quitButtonArea.y, 
			   quitButtonArea.width, quitButtonArea.height);
		if (isOverQuitButton)
			g.setColor(Color.WHITE);
		else
			g.setColor(Color.RED);

		g.drawString("Quit", quitButtonArea.x+60, quitButtonArea.y+25);
		g.setFont(oldFont);		// reset font

	}

	// displays a message to the screen when the user stops the game

	private void gameOverMessage(Graphics g) {
		
		Font font = new Font("SansSerif", Font.BOLD, 24);
		FontMetrics metrics = this.getFontMetrics(font);

		String msg = "Game Over. Thanks for playing!";

		int x = (pWidth - metrics.stringWidth(msg)) / 2; 
		int y = (pHeight - metrics.getHeight()) / 2;

		g.setColor(Color.BLUE);
		g.setFont(font);
		g.drawString(msg, x, y);

	}

	/* This method performs some tasks before closing the game.
	   The call to System.exit() should not be necessary; however,
	   it prevents hanging when the game terminates.
	*/

	private void finishOff() { 
    		if (!finishedOff) {
			finishedOff = true;
			restoreScreen();
			System.exit(0);
		}
	}

	/* This method switches off full screen mode. The display
	   mode is also reset if it has been changed.
	*/

	private void restoreScreen() { 
		Window w = device.getFullScreenWindow();
		
		if (w != null)
			w.dispose();
		
		device.setFullScreenWindow(null);
	}

	// This method provides details about the current display mode.

	private void showCurrentMode() {
		DisplayMode dm = device.getDisplayMode();
		System.out.println("Current Display Mode: (" + 
                           dm.getWidth() + "," + dm.getHeight() + "," +
                           dm.getBitDepth() + "," + dm.getRefreshRate() + ")  " );
  	}
	
	public void loadImages() {

		bgImage = loadImage("images/background.jpg");

	}

	public Image loadImage (String fileName) {
		return new ImageIcon(fileName).getImage();
	}

	public void loadClips() {

		try {
			playSound = Applet.newAudioClip (
					getClass().getResource("sounds/background.wav"));

		}
		catch (Exception e) {
			System.out.println ("Error loading sound file: " + e);
		}

	}

	public void playClip (int index) {

		if (index == 1 && playSound != null)
			playSound.play();

	}

}

