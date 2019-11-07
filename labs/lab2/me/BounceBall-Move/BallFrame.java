import java.awt.event.*;
import javax.swing.*;

public class BallFrame extends JFrame implements ActionListener
{
    private JPanel ballPanel;
    private Ball ball;

    public BallFrame(){
        // make a custom jframe for the ball teknologee
        setSize(600,600);
        setTitle("Balls");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ballPanel = new JPanel();
        add(ballPanel, "Center");

        JPanel buttonPanel = new JPanel();

        JButton createB = new JButton("create Ball");
        createB.addActionListener(this);
        buttonPanel.add(createB);

        JButton drawB = new JButton("draw Ball");
        drawB.addActionListener(this);
        buttonPanel.add(drawB);

        JButton eraseB = new JButton("erase Ball");
        eraseB.addActionListener(this);
        buttonPanel.add(eraseB);
        
        JButton moveB = new JButton("move Ball");
        moveB.addActionListener(this);
        buttonPanel.add(moveB);

        JButton closeB = new JButton("close Ball");
        closeB.addActionListener(this);
        buttonPanel.add(closeB);

        add(buttonPanel, "South");
        System.out.println("Constructed BallFrame.");
    }

    public void actionPerformed(ActionEvent e){
        String command = e.getActionCommand();

        if(command.equals("create Ball")){
            ball = new Ball(ballPanel);
            ball.draw();
            System.out.println("created ball");
        }
        else if(command.equals("erase Ball") && ball != null){
            ball.erase();
            ball = null;
            System.out.println("erased ball");
        }
        else if(command.equals("draw Ball")){
            //ball = new Ball(ballPanel);
            ball.draw();
            System.out.println("drew ball");
        }
        else if(command.equals("close Ball")){
            ballPanel.setVisible(false);
            System.out.println("closed ball");
            System.exit(0);
        }
        else if(command.equals("move Ball") && ball != null){
            ball.run();
            //System.out.println("moving ball");
            //sleep(10);
            System.out.println("moved ball");
        }
    }

    
}