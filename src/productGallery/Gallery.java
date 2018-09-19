package productGallery;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import org.jdatepicker.impl.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.DriverManager;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;


public class Gallery extends JFrame {
    private JPanel panel1;
    private JTextField txt_ID;
    private JTextField txt_Name;
    private JTextField txt_Price;
    private JDatePickerImpl txt_AddDate;
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
    private JButton btn_ChooseImage;
    private JPanel JPanel_Buttony2;
    private JButton btn_Delete;
    private JButton firstButton;
    private JPanel JPanel_Buttony3;
    private JLabel labelImage;
    private JButton btn_Insert;
    private JButton btn_Update;
    private String imagePath;
    private int lastID;

    public Gallery() {
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        setSize(1000, 500);
        setTitle("Product Gallery");
        //getLastIDFromDB(); Metoda do poprawienia


        btn_ChooseImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser file = new JFileChooser(System.getProperty("user.home") + System.getProperty("file.separator") + "Pictures");


                FileNameExtensionFilter filter = new FileNameExtensionFilter("*.images", "jpg", "png");
                file.addChoosableFileFilter(filter);

                int result = file.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = file.getSelectedFile();
                    imagePath = selectedFile.getAbsolutePath();
                    labelImage.setIcon(resizeImage(imagePath, null));
                } else {
                    System.out.println("No File Selected");
                }
            }
        });

        btn_Insert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (checkInputs() && imagePath != null) {
                    if (!txt_ID.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "ID is automatically added, don't fill it!");
                        return;
                    }
                    Connection con = getConnection();
                    if (con != null) {
                        try (InputStream img = new FileInputStream(new File(imagePath))) {


                            PreparedStatement ps = (PreparedStatement) con.prepareStatement("INSERT INTO przedmiotyallegro(name,price,add_date,image)" + "VALUES(?,?,?,?)");
                            ps.setString(1, txt_Name.getText());
                            ps.setString(2, txt_Price.getText());

                            Date selectedDate = (Date) txt_AddDate.getModel().getValue();
                            String addDate = selectedDate.toString();
                            ps.setString(3, addDate);

                            //InputStream img = new FileInputStream(new File(imagePath)) I've replaced that line by try with resources
                            ps.setBlob(4, img);// Needed to change MySql config: max_allowed_packet greater than default and innodb_log_file_size


                            ps.executeUpdate();
                            JOptionPane.showMessageDialog(null, "Succesful insert!");
                            con.close();
                            txt_ID.setEditable(true);
                        } catch (Exception e1) {
                            JOptionPane.showMessageDialog(null, e1.getMessage());
                        }
                    }


                } else {
                    JOptionPane.showMessageDialog(null, "At least one field isn't correct filled");
                }
            }
        });

        btn_Update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkInputs() && imagePath != null) {
                    if (txt_ID.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "You have to type correct ID!!!");
                        return;
                    }
                    try (InputStream img = new FileInputStream(imagePath)) {
                        Connection con = getConnection();

                        String UpdateQuery = "UPDATE przedmiotyallegro SET name=? ,price=? ,add_date=?, image=? WHERE id=?";

                        PreparedStatement ps = (PreparedStatement) con.prepareStatement(UpdateQuery);
                        ps.setString(1, txt_Name.getText());
                        ps.setString(2, txt_Price.getText());

                        Date selectedDate = (Date) txt_AddDate.getModel().getValue();
                        String addDate = selectedDate.toString();
                        ps.setString(3, addDate);
                        ps.setBlob(4, img);

                        ps.setInt(5, Integer.parseInt(txt_ID.getText()));

                        ps.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Succesful update!");
                        con.close();

                    } catch (FileNotFoundException e1) {
                        JOptionPane.showMessageDialog(null, "Image file not found!");
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage());
                    }


                } else JOptionPane.showMessageDialog(null, "One or more field is empty and/or incorrect");
            }
        });

        btn_Delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!txt_ID.getText().isEmpty()) {
                    try {
                        Connection con = getConnection();
                        String deleteQuery = "DELETE FROM przedmiotyallegro WHERE id=?";
                        PreparedStatement ps = (PreparedStatement) con.prepareStatement(deleteQuery);
                        int id = Integer.parseInt(txt_ID.getText());
                        ps.setInt(1, id);
                        ps.executeUpdate();
                        con.close();
                        JOptionPane.showMessageDialog(null, "Succesfully deleted record!");

                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage());
                    }
                } else JOptionPane.showMessageDialog(null, "Enter correct ID product to delete!!!");
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
        txt_AddDate = new JDatePickerImpl(datePanel, new JDateFormatter()); //JDatePickerImpl

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
            //JOptionPane.showMessageDialog(null, "Connected");
            return con;

        } catch (SQLException e) {
            //e.printStackTrace();
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

    /**
     * @return True if all inputs are corrects.
     */
    public boolean checkInputs() {
        if (txt_Name.getText().isEmpty() || txt_Price.getText().isEmpty() || txt_AddDate.getModel().getValue() == null)
            return false;
        try {
            Float.parseFloat(txt_Price.getText());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void getLastIDFromDB() {
        Connection con = getConnection();
        String query = "SELECT id FROM `przedmiotyallegro` ORDER BY id DESC LIMIT 1";
        try {
            PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
            ps.execute();

            JOptionPane.showMessageDialog(null, lastID);
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}


