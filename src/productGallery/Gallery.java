package productGallery;

import com.mysql.jdbc.Connection;
import org.jdatepicker.impl.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;
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
    private JTable JTable;
    private JScrollPane scrollPane1;
    private JPanel JPanel_Buttony;
    private JButton btnChooseImage;
    private JPanel JPanel_Buttony2;
    private JButton deleteButton;
    private JButton firstButton;
    private JPanel JPanel_Buttony3;
    private JLabel labelImage;

    public Gallery() {
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        setSize(1000, 500);
        setTitle("Product Gallery");


        btnChooseImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser file = new JFileChooser(System.getProperty("user.home") + System.getProperty("file.separator")+ "Pictures");


                FileNameExtensionFilter filter = new FileNameExtensionFilter("*.images", "jpg", "png");
                file.addChoosableFileFilter(filter);

                int result = file.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = file.getSelectedFile();
                    String path = selectedFile.getAbsolutePath();
                    labelImage.setIcon(resizeImage(path, null));
                } else {
                    System.out.println("No File Selected");
                }
            }
        });
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
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new JDateFormatter()); //JDatePickerImpl

        //Sekcja tabeli
        String[] columnNames = {"ID", "NAME", "PRICE", "ADD DATE"};
        Object[][] data = {};
        JTable = new JTable(data, columnNames);
        JTable.setFillsViewportHeight(true);
        scrollPane1 = new JScrollPane(JTable);
    }

    public Connection getConnection() {
        Connection con = null;
        try {
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/products", "root", "");
            JOptionPane.showMessageDialog(null, "Connected");
            return con;

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Not connected");
            return null;
        }

    }

    /**
     * @param imagePath
     * @param pic
     * @return Resized image from source.
     */
    public ImageIcon resizeImage(String imagePath, byte[] pic) {
        ImageIcon myImg = null;
        if (imagePath != null) {
            myImg = new ImageIcon(imagePath);
        } else {
            myImg = new ImageIcon(pic);
        }
        Image img = myImg.getImage();
        Image img2 = img.getScaledInstance(labelImage.getWidth(), labelImage.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon finalImg = new ImageIcon(img2);
        return finalImg;
    }
}


