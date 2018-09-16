package productGallery;

import org.jdatepicker.impl.*;
import javax.swing.*;
import java.util.Properties;


public class Gallery extends JFrame {
    private JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JDatePickerImpl datePicker;

    public Gallery() {
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        setSize(1000,500);
    }

    public static void main(String[] args) {
        new Gallery();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        SqlDateModel model = new SqlDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model,p);
        datePicker = new JDatePickerImpl(datePanel,new JDateFormatter()); //JDatePickerImpl
    }
}


