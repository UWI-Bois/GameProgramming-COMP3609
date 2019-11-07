import java.awt.EventQueue;
import javax.swing.JFrame;

public class ShootingMissilesEx extends JFrame
{
    public ShootingMissilesEx(){
        initUI();
    }

    private void initUI(){
        add(new Board());

        setSize(700,700);
        setResizable(false);

        setTitle("Shooting Missiles");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            ShootingMissilesEx ex = new ShootingMissilesEx();
            ex.setVisible(true);
        });
        System.out.println("yyeeeett");
    }
}