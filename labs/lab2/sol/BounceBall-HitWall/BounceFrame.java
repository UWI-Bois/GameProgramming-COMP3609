import java.awt.event.*;
import javax.swing.*;

public class BounceFrame extends JFrame
			 	         implements ActionListener {

   private JPanel ballPanel;

   public BounceFrame () {
      setSize (500, 400);
      setTitle ("Bouncing Ball");

      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      ballPanel = new JPanel ();
      add (ballPanel, "Center");

      JPanel buttonPanel = new JPanel ();

      JButton startB = new JButton ("Create Ball");
      startB.addActionListener (this);
      buttonPanel.add (startB);

      JButton closeB = new JButton ("Close");
      closeB.addActionListener (this);
      buttonPanel.add (closeB);

      add (buttonPanel, "South");

   }

   public void actionPerformed (ActionEvent e) {
      String command = e.getActionCommand ();

      if (command.equals ("Create Ball")) {
         Ball ball = new Ball (ballPanel);
         ball.start ();
      }
      else
      if (command.equals ("Close")) {
         ballPanel.setVisible (false);
         System.exit (0);
      }
   }

}
