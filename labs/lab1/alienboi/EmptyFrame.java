import javax.swing.JFrame;
import javax.swing.SwingUtilities;
public class EmptyFrame extends JFrame
{
   public EmptyFrame(){
      setTitle("alien boi");
      setSize(500, 500);
      setLocationRelativeTo(null);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
   }
   public static void main(String[] args)
   {
      JFrame frame = new JFrame();

      frame.setSize(500, 500);
      frame.setTitle("My Frame");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      //frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
      //FaceComponent face = new FaceComponent();
      //frame.add(face);
      frame.setVisible(true);
   }
}
