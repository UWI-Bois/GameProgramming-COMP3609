import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import javax.swing.JPanel;

import javax.swing.JPanel;

public class Ball extends Thread
{
    private int x; // coords
    private int y;

    private int dx; // speed
    private int dy; // speed

    private static final int xSize = 60; // size of ball
    private static final int ySize = 60;

    private JPanel panel;
    Graphics2D g2;
    private Color bgColor; // background color of the panel?
    private Dimension dimension;

    public Ball(JPanel p){
        panel = p;
        Graphics g = panel.getGraphics();
        g2 = (Graphics2D) g;
        bgColor = panel.getBackground();
        x = y = 30; // initial coords
        dx = dy = 5; // initial speed
        dimension = panel.getSize();
        System.out.println("Constructed Ball.");
    }

    // thread teknologee
    @Override
    public void run(){
        System.out.println("trying to run ball.");
        try {
            draw(); // initial draw, needed for move and erase.
            System.out.print("from run ");
            for(int i = 1;i <= 1000; i++){ // thread will tick in this loop, simulating a moving ball
                erase(); // poof d ball
                move(); // shifts x and y (the ball)
                draw(); // draw the moved ball
                sleep(10);
                System.out.println("trying to move ball. - ");
                System.out.print(i);
            }
        } catch (Exception e) {
            System.out.println("oopsie :blush:");
        }
        System.out.println("thread.");
        return;
    }

    public void erase(){
        // this function will get change the ball into the same color as the bg
        g2.setColor(bgColor);
        g2.fill(new Ellipse2D.Double(x,y,xSize,ySize));
        System.out.println("erased ball.");
        return;
    }

    public void draw(){
        // draw a red ball
        g2.setColor(Color.RED);
        g2.fill(new Ellipse2D.Double(x,y,xSize,ySize));
        System.out.println("drew ball.");
        return;
    }

    public void move(){
        System.out.println("trying to move ball.");
        if(!panel.isVisible()) return;
        x = x+dx; // move 
        y = y+dy; 

        //check bounds after the ball moves, and reset the pos if out of bounds
        if(x >= dimension.width || y >= dimension.height){
            System.out.println("reset.");
            x = 20;
            y = 20;
        }
        System.out.println("moved ball.");
        return;
    }
}