import java.awt.EventQueue;
import javax.swing.JFrame;

public class SwingTimerEx extends JFrame {

    public SwingTimerEx() {

        initUI();
    }

    private void initUI() {

        add(new Board());
/* 	The order of the setResizable() and pack() methods is important.
 *	The setResizable() changes the insets of the frame on some platforms;
 *	calling this method after the pack() method might lead to incorrect results—the star would not go precisely into the right-bottom border of the window. */

        setResizable(false); // sets whether the frame can be resized.
        pack(); //causes the window to be sized to fit the preferred size and layouts of its children.


        setTitle("Star");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            SwingTimerEx ex = new SwingTimerEx();
            ex.setVisible(true);
        });
    }
}
