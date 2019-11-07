import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import javax.swing.JPanel;

import javax.swing.JPanel;

public class Ball
{
    private int x; // coords
    private int y;

    private static final int xSize = 60; // size of ball
    private static final int ySize = 60;

    private JPanel panel;
    Graphics2D g2;
    private Color bgColor; // background color of the panel?

    public Ball(JPanel p){
        panel = p;
        Graphics g = panel.getGraphics();
        g2 = (Graphics2D) g;
        bgColor = panel.getBackground();
        x = y = 10; // initial coords
    }

    public void erase(){
        // this function will get change the ball into the same color as the bg
        g2.setColor(bgColor);
        g2.fill(new Ellipse2D.Double(x,y,xSize,ySize));
        return;
    }

    public void draw(){
        // draw a red ball
        g2.setColor(Color.RED);
        g2.fill(new Ellipse2D.Double(x,y,xSize,ySize));
        return;
    }
}