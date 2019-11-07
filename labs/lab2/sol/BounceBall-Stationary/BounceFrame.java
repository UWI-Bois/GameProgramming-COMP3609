import java.awt.event.*;
import javax.swing.*;

public class BounceFrame extends JFrame
			 implements ActionListener {

   private JPanel ballPanel;
   private Ball ball;

   public BounceFrame () {
      setSize (500, 400);
      setTitle ("Bouncing Ball");

      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

/*    The following code does same thing as setDefaultCloseOperation():

      addWindowListener(new WindowAdapter ()
         {  public void windowClosing(WindowEvent e) {
               System.exit (0);
            }
         } );

*/
      ballPanel = new JPanel ();
      add (ballPanel, "Center");

      JPanel buttonPanel = new JPanel ();

      JButton createB = new JButton ("Create Ball");
      createB.addActionListener (this);
      buttonPanel.add (createB);

      JButton drawB = new JButton ("Draw");
      drawB.addActionListener (this);
      buttonPanel.add (drawB);

      JButton eraseB = new JButton ("Erase");
      eraseB.addActionListener (this);
      buttonPanel.add (eraseB);

      JButton closeB = new JButton ("Close");
      closeB.addActionListener (this);
      buttonPanel.add (closeB);

      add (buttonPanel, "South");
   }

   public void actionPerformed (ActionEvent e) {
      String command = e.getActionCommand ();

      if (command.equals ("Create Ball")) {
	 		ball = new Ball (ballPanel);
         ball.draw();
      }
      else
      if (command.equals ("Draw") && ball != null) {
         ball.draw();
      }
      else
      if (command.equals ("Erase") && ball != null) {
         ball.erase();
      }
      else
      if (command.equals ("Close")) {
         ballPanel.setVisible (false);
         System.exit (0);
      }
   }

}
