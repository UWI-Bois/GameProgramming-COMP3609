 

import javax.swing.JFrame;
import java.awt.EventQueue;

public class SwingTimerEx extends JFrame
{
    public SwingTimerEx(){
        initUI();
    }

    private void initUI(){
        add(new Board());

        setResizable(false); // sets whether the frame can be resized.
        pack(); // causes the window to be sized to fit the preferred size and layouts of its chicldre.

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