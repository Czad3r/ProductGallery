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
    private JPanel JPanel_Price;
    private JPanel JPanel_AddDate;
    private JPanel JPanel_Picture;
    private JPanel JPanel_Name;
    private JPanel JPanel_ID;
    private JPanel JPanel_DaneKontener;
    private JPanel JPanel_DrugiKontener;
<<<<<<< HEAD
    private JTable JTable;
    private JScrollPane scrollPane1;
    private JPanel JPanel_Buttony;
    private JButton chooseImageButton;
    private JPanel JPanel_Buttony2;
    private JButton deleteButton;
    private JButton firstButton;
=======
>>>>>>> 094cf3748cccaad0496383968bcc66e7d14fc538

    public Gallery() {
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        setSize(1000,500);
        setTitle("Product Gallery");
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

        //Sekcja tabeli
        String[] columnNames = {"ID", "NAME","PRICE","ADD DATE"};
        Object[][] data = {};
        JTable = new JTable(data, columnNames);
        JTable.setFillsViewportHeight(true);
        scrollPane1 = new JScrollPane(JTable);
    }
}


