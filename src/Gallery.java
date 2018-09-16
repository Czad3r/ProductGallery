import javax.swing.*;
import java.awt.*;

public class Gallery extends JFrame {
    private JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;

    public Gallery() {
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(1000,500);
    }

    public static void main(String[] args) {
        new Gallery().setVisible(true);
    }
}


