import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.Random;

import java.util.Random;

import javax.swing.JPanel;

import javax.swing.JPanel;

public class Ball extends Thread
{
    private int x; // coords
    private int y;
    private int diameter;

    private int dx; // speed
    private int dy; // speed

    // private static final int xSize = 60; // size of ball
    // private static final int ySize = 60;
    private static final int maxD = 60; // size of ball
    private static final int minD = 30;

    private JPanel panel;
    Graphics2D g2;
    private Color bgColor; // background color of the panel?
    private Color color;
    private Dimension dimension;
    private Random random;

    public Ball(JPanel p){
        panel = p;
        Graphics g = panel.getGraphics();
        g2 = (Graphics2D) g;
        bgColor = panel.getBackground();
        // x = y = 30; // initial coords
        dx = dy = 2; // initial speed
        dimension = panel.getSize();
        random = new Random();
        x = random.nextInt(dimension.width); // random coords
        y = random.nextInt(dimension.height);
        diameter = random.nextInt(maxD - minD) + minD; // epic rand range teknologee;
        color = new Color(
            random.nextInt(256),
            random.nextInt(256),
            random.nextInt(256)
        );
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
        g2.fill(new Ellipse2D.Double(x,y,diameter,diameter));
        System.out.println("erased ball.");
        return;
    }

    public void draw(){
        // draw a red ball
        g2.setColor(color);
        g2.fill(new Ellipse2D.Double(x,y,diameter,diameter));
        System.out.println("drew ball.");
        return;
    }

    public void move(){
        System.out.println("trying to move ball.");
        if(!panel.isVisible()) return;
        x = x+dx; // move 
        y = y+dy; 

        //check bounds after the ball moves, and reset the pos if out of bounds
        if( x + diameter >= dimension.width || y + diameter >= dimension.height){
            //System.out.println("flip vel.");
            dx = dx * -1;
            dy = dy * -1;
        }
        if(x < 0){
            x = 0;
            dx = dx * -1;
        }
        if(y < 0){
            y = 0;
            dy = dy * -1;
        }
        System.out.println("moved ball.");
        return;
    }
}