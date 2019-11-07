import java.awt.event.*;
import javax.swing.*;

public class GameFrame extends JFrame implements ActionListener {

	private GamePanel gamePanel;

	public GameFrame () {
		setSize (700, 500);
		setTitle ("Bat and Ball Game with Images");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		gamePanel = new GamePanel ();			// create the Game Panel
		add (gamePanel, "Center");

		gamePanel.setFocusable (true);
		gamePanel.requestFocus();

		JPanel buttonPanel = new JPanel ();

		JButton backgroundB = new JButton ("Add Background");
		backgroundB.addActionListener (this);
		buttonPanel.add (backgroundB);

		JButton batB = new JButton ("Add Bat");
		batB.addActionListener (this);
		buttonPanel.add (batB);

		JButton ballB = new JButton ("Add Ball");
		ballB.addActionListener (this);
		buttonPanel.add (ballB);

		JButton playerB = new JButton ("Add Player");
		playerB.addActionListener (this);
		buttonPanel.add (playerB);

		JButton shapesB = new JButton ("Add Shape");
		shapesB.addActionListener (this);
		buttonPanel.add (shapesB);

		JButton saveB = new JButton ("Save to PNG");
		saveB.addActionListener (this);
		buttonPanel.add (saveB);

		JButton closeB = new JButton ("Close");
		closeB.addActionListener (this);
		buttonPanel.add (closeB);

		add (buttonPanel, "South");

	}

	// implementation of ActionListener interface for GameFrame

	public void actionPerformed (ActionEvent e) {
		String command = e.getActionCommand ();

		if (command.equals ("Add Background")) {
			gamePanel.requestFocus();
			gamePanel.addBackground();
		}
		else
		if (command.equals ("Add Bat")) {
			gamePanel.requestFocus();
			gamePanel.addBat();
		}
		else
		if (command.equals ("Add Ball")) {
			gamePanel.requestFocus();
			gamePanel.addBall();
		}
		else
		if (command.equals ("Add Player")) {
			gamePanel.requestFocus();
			gamePanel.addPlayer();
		}
		else
		if (command.equals ("Add Shape")) {
			gamePanel.requestFocus();
			gamePanel.addShape();
		}
		else
		if (command.equals ("Save to PNG")) {
			gamePanel.savePNG();
		}
		else
		if (command.equals ("Close")) {
			setVisible (false);
			System.exit (0);
		}
	}
}