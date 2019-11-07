import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import java.util.*;
import java.awt.*;

public class SpaceShip extends Sprite 
{

    private int dx;
    private int dy;
    private int w;
    private int h;

    public SpaceShip(int x, int y) {
        super(x,y);
        initSpaceShip();
    }

    public void move() {
        x += dx;
        y += dy;
    }

    public int getWidth() {

        return w;
    }

    public int getHeight() {

        return h;
    }


    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = -2;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 2;
        }

        if (key == KeyEvent.VK_UP) {
            dy = -2;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 2;
        }
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_UP) {
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 0;
        }
    }

    public void fire(){
        missiles.add(new Missile(x + width, y + height / 2));
    }
}
