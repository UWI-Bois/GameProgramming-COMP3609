import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/* We move a star that from the upper-left corner to the right-bottom corner. */
public class Board extends JPanel implements ActionListener {

/* Five constants are defined.
 * The first two constants are the board width and height.
 * The third and fourth are the initial coordinates of the star.
 * The last one determines the speed of the animation. */
    private final int B_WIDTH = 350;
    private final int B_HEIGHT = 350;
    private final int INITIAL_X = -40;
    private final int INITIAL_Y = -40;
    private final int DELAY = 25;

    private Image star;
    private Timer timer;
    private int x, y;

    public Board() {

        initBoard();
    }


/* In the loadImage() method we create an instance of the ImageIcon class. This object will be drawn on the board. */
    private void loadImage() {
        ImageIcon ii = new ImageIcon("star.png");
        star = ii.getImage(); // The getImage() method will return the the Image object from this class.
    }

    private void initBoard() {

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

        loadImage();

        x = INITIAL_X;
        y = INITIAL_Y;

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
/* Custom painting is done in the paintComponent() method. Note that we also call the paintComponent() method of its parent.
 * The actual painting is delegated to the drawStar() method.*/
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawStar(g);
    }


/*	In the drawStar() method, we draw the image on the window with the usage of the drawImage() method.
 * 	The Toolkit.getDefaultToolkit().sync() synchronises the painting on systems that buffer graphics events.
 * 	Without this line, the animation might not be smooth on Linux.*/

    private void drawStar(Graphics g) {

        g.drawImage(star, x, y, this);
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
/* The actionPerformed() method is repeatedly called by the timer.
 *	Inside the method, we increase the x and y values of the star object.
 *	Then we call the repaint() method which will cause the paintComponent() to be called.
 *	This way we regularly repaint the Board thus making the animation.*/
    public void actionPerformed(ActionEvent e) {

        x += 1;
        y += 1;

        if (y > B_HEIGHT) {

            y = INITIAL_Y;
            x = INITIAL_X;
        }

        repaint();
    }
}
