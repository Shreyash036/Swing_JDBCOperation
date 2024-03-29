package testpkg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Swingfouroperation implements ActionListener {
    JFrame frame;
    JTextField t1;
    JTextField t2;

    JButton btn1;
    JButton btn2;
    JButton btn3;
    JButton btn4;

    JLabel label1;
    JLabel label2;

    Connection con;

    Swingfouroperation() {
        frame = new JFrame("Frame demo");
        frame.setSize(500, 300);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));

        label1 = new JLabel("Enter roll_no: ");
        inputPanel.add(label1);
        t1 = new JTextField(15);
        inputPanel.add(t1);

        label2 = new JLabel("Enter name: ");
        inputPanel.add(label2);
        t2 = new JTextField(15);
        inputPanel.add(t2);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        btn1 = new JButton("Insert");
        buttonPanel.add(btn1);

        btn2 = new JButton("Update");
        buttonPanel.add(btn2);

        btn3 = new JButton("Delete");
        buttonPanel.add(btn3);

        btn4 = new JButton("Select");
        buttonPanel.add(btn4);

        btn1.addActionListener(this);
        btn2.addActionListener(this);
        btn3.addActionListener(this);
        btn4.addActionListener(this);

        frame.add(inputPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Establish database connection
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/employetaable", "root", "root123");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error connecting to database: " + ex.getMessage());
        }
    }

    public void actionPerformed(ActionEvent ae) {
        String t = ae.getActionCommand();

        if (t.equals("Insert")) {
            try {
                String qry = "insert into etable values (?,?)";
                PreparedStatement ps = con.prepareStatement(qry);

                int r = Integer.parseInt(t1.getText());
                String n = t2.getText();
                ps.setInt(1, r);
                ps.setString(2, n);
                ps.execute();
                JOptionPane.showMessageDialog(frame, "Data inserted successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            }
        } else if (t.equals("Update")) {
            try {
                String qry = "update etable set name=? where rollno=?";
                PreparedStatement ps = con.prepareStatement(qry);

                String n = t2.getText();
                int r = Integer.parseInt(t1.getText());
                ps.setString(1, n);
                ps.setInt(2, r);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(frame, "Data updated successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            }
        } else if (t.equals("Delete")) {
            try {
                String qry = "delete from etable where rollno=?";
                PreparedStatement ps = con.prepareStatement(qry);

                int r = Integer.parseInt(t1.getText());
                ps.setInt(1, r);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(frame, "Data deleted successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            }
        } else if (t.equals("Select")) {
            try {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("select * from etable");
                StringBuilder result = new StringBuilder();
                while (rs.next()) {
                    result.append("Roll No: ").append(rs.getInt(1)).append(", Name: ").append(rs.getString(2)).append("\n");
                }
                JOptionPane.showMessageDialog(frame, result.toString());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new Swingfouroperation();
    }
}
