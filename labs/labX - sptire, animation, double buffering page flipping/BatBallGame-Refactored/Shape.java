import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import java.util.Random;
import java.applet.AudioClip;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.io.IOException;

import javax.sound.sampled.*;

public class Shape
{

    private static final int XSIZE = 20;
    private static final int YSIZE = 30;
    private static final int DY = 10;

    private GamePanel panel;
    private Bat bat;
    private Random random;
    private int x,y,playerWidth, playerHeight;
    private Image playerImage;
    
    Graphics2D g2;
    private Dimension dimension;

    public Shape(GamePanel p){
        panel = p;
        Graphics g = panel.getGraphics();
        g2 = (Graphics2D) g;
        dimension = panel.getSize();

        random = new Random();
        setPosition();
    }

    public void setPosition(){
        x = random.nextInt(bound.dimension.width - XSIZE);
        y = random.nextInt(bound.dimension.height - YSIZE);
    }

    public void drawLine(Graphics2D g2){
        int x2, y2;

        x2 = random.nextInt(dimension.width);
        y2 = random.nextInt(dimension.height);
        
        g2.setColor(Color.BLACK);
        g2.drawLine(x,y,x2,y2);
    }

    public void drawRectangle(Graphics2D g2){
        g2.setColor(Colot.CYAN);
        g2.fill(new Rectangle2D.Double(x,y,XSIZE,YSIZE));
    }

    public void drawEllipse(Graphics2D g2){
        g2.setColor(Colot.CYAN);
        g2.fill(new Rectangle2D.Double(x,y,XSIZE,YSIZE));
    }

    public void draw(Graphics){
        int width, height;
        g2.setColor(Color.CYAN);
        width = (int) playerWidth/2;
        height = (int) playerHeight . 2;

        g2.drawImage(playerImage, x, y, width, height, null);
    }
} 