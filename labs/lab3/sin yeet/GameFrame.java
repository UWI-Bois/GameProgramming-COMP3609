import java.awt.event.*;
import javax.swing.*;

public class GameFrame extends JFrame
			implements ActionListener,
			KeyListener {

	private JPanel gamePanel;
	private Bat bat = null;
	private Ball ball = null;

	public GameFrame () {
		setSize (500, 500);
		setTitle ("Bat and Ball Game");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		gamePanel = new JPanel ();
		add (gamePanel, "Center");

		gamePanel.setFocusable (true);
		gamePanel.requestFocus();

		gamePanel.addKeyListener (this);

		JPanel buttonPanel = new JPanel ();

		JButton createB = new JButton ("Start Game");
		createB.addActionListener (this);
		buttonPanel.add (createB);

		JButton closeB = new JButton ("Close");
		closeB.addActionListener (this);
		buttonPanel.add (closeB);
		add (buttonPanel, "South");

	}

	// implementation of ActionListener interface for GameFrame

	public void actionPerformed (ActionEvent e) {
		String command = e.getActionCommand ();

		if (command.equals ("Start Game")) {
			gamePanel.requestFocus();
			bat = new Bat (gamePanel);
			ball = new Ball (gamePanel, bat);
			bat.draw ();
			ball.start ();
		}
		else
		if (command.equals ("Close")) {
			gamePanel.setVisible (false);
			System.exit (0);
		}
	}

	// implementation of KeyListener interface for gamePanel

	public void keyPressed (KeyEvent e) {
		if (bat != null)
			bat.processKey (e);
	}

	public void keyReleased (KeyEvent e) {

	}

	public void keyTyped (KeyEvent e) {

	}

}
