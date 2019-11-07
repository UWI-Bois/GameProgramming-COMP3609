

import javafx.scene.shape.Ellipse;

import java.awt.*;
import javax.swing.JComponent;

import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

public class FaceComponent extends JComponent
{
   // override the paint() method from JComponent to draw our stuff
   public void paint(Graphics g){
      Graphics2D g2 = (Graphics2D) g;
      // head - a big ellipse
      Ellipse2D.Double head = new Ellipse2D.Double(105,10,100,150);
      g2.draw(head);
      // eyes - lines
      Line2D.Double e1 = new Line2D.Double(125,50,145,70);
      Line2D.Double e2 = new Line2D.Double(185,50,165,70);
      g2.draw(e1);
      g2.draw(e2);
      // noses?? - ellipses??
      Ellipse2D.Double n1 = new Ellipse2D.Double(150,60,10,10);
      Ellipse2D.Double n2 = new Ellipse2D.Double(150,80,10,10);
      Ellipse2D.Double n3 = new Ellipse2D.Double(150,100,10,10);
      // g2.draw(n1);
      // g2.draw(n2);
      // g2.draw(n3); // .fill does the same thing as .draw, just applies a solid color
      g2.setPaint(Color.RED);
         g2.fill(n1);
      g2.setPaint(Color.GREEN);
         g2.fill(n2);
         g2.fill(n3);
      // mouth - arc
      Arc2D.Double mouth = new Arc2D.Double(128, 125,50,15,90,235,Arc2D.CHORD);
      g2.setPaint(Color.MAGENTA);
      g2.fill(mouth);
      // epic font tekonologee
      Font f = new Font("Monospaced", Font.PLAIN, 24);
      g2.setFont(f);
      g2.setColor(Color.blue);
      g2.drawString("Y e e t", 75, 200);
   }
}
