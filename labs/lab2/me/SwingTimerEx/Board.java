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

// move a star from upper left to bottom right;
public class Board extends JPanel implements ActionListener
{   
    private final int bWidth = 350;
    private final int bHeight = 350;
    private final int initialX = -40;
    private final int initialY = -40;
    private final int delay = 25;

    private Image star;
    private Timer timer;
    private int x, y;

    //construcotr
    public Board(){
        initBoard();
    }

    // load image from ImageIcon
    private void loadImage(){
        ImageIcon ii = new ImageIcon("star.png");
        star = ii.getImage();
    }

    private void initBoard(){
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(bWidth, bHeight));

        loadImage();

        x = initialX;
        y = initialY;

        timer = new Timer(delay, this);
        timer.start();
    }

    // custom painting is done. this calls paintCompoenent(), the actual painting is delegated to drawStar()
    public void paintCompoenent(Graphics g){
        super.paintComponent(g);
        drawStar(g);
    }

    private void drawStar(Graphics g){
        g.drawImage(star,x,y,this);
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e){
        x+=1;
        y+=1;

        if(y > bHeight){
            y = initialY;
            x = initialX;
        }

        repaint();
    }
}