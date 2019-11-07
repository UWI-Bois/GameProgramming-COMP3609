import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.Random;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.event.*;
import java.awt.*;
import java.awt.Image;

public class Sprite
{
    protected int x;
    protected int y;
    
    protected int width;
    protected int height;
    protected boolean visible;
    protected Image image;


    public Sprite(){
        this.x = x;
        this.y = y;


        visible = true;
        System.out.println("sprite created!");
    }

    protected void loadImage(String imageName){
        ImageIcon ii = new ImageIcon(imageName);
        this.image = ii.getImage();
    }

    public void getImageDimensions(){
        // width
        // height
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public int getWidth() {

        return this.width;
    }

    public int getHeight() {

        return this.height;
    }

    public Image getImage(){
        return this.image;
    }

    public boolean isVisible(){
        return this.visible;
    }

    public void setVisible(boolean v){
        this.visible = v;
    }

}