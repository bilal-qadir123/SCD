package crime.rec.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

public class DeleteCriminal extends JFrame implements ActionListener {

    JTextField searchField, lblname, lblphone, lbljailterm;
    JButton delete, back;

    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(Image backgroundImage) {
            this.backgroundImage = backgroundImage;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    DeleteCriminal() {
        setLayout(null);

        ImageIcon backgroundIcon = new ImageIcon(ClassLoader.getSystemResource("icons/img5.jpg"));
        Image backgroundImage = backgroundIcon.getImage();

        setContentPane(new BackgroundPanel(backgroundImage));
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        Font labelFont = new Font("Arial", Font.BOLD, 18);
        Font textFieldFont = new Font("Arial", Font.PLAIN, 18);
        Font buttonFont = new Font("Arial", Font.BOLD, 20);

        JLabel labelcaseId = new JLabel("Case Id");
        labelcaseId.setBounds(50, 50, 150, 30);
        labelcaseId.setFont(labelFont);
        add(labelcaseId);

        searchField = new JTextField();
        searchField.setBounds(200, 50, 200, 30);
        add(searchField);

        JLabel labelname = new JLabel("Name");
        labelname.setBounds(50, 100, 100, 30);
        add(labelname);

        lblname = new JTextField();
        lblname.setBounds(200, 100, 200, 30);
        lblname.setFont(textFieldFont);
        lblname.setEditable(false);
        add(lblname);

        JLabel labelphone = new JLabel("Phone");
        labelphone.setBounds(50, 150, 100, 30);
        add(labelphone);

        lblphone = new JTextField();
        lblphone.setBounds(200, 150, 200, 30);
        lblphone.setFont(textFieldFont);
        lblphone.setEditable(false);
        add(lblphone);

        JLabel labeljailterm = new JLabel("JailTerm");
        labeljailterm.setBounds(50, 200, 100, 30);
        add(labeljailterm);

        lbljailterm = new JTextField();
        lbljailterm.setBounds(200, 200, 200, 30);
        lbljailterm.setFont(textFieldFont);
        lbljailterm.setEditable(false);
        add(lbljailterm);

        searchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (Conn conn = new Conn();
                     PreparedStatement pstmt = conn.getConnection().prepareStatement("SELECT * FROM criminal WHERE caseId = ?")) {
                    pstmt.setString(1, searchField.getText().trim());
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) {
                        lblname.setText(rs.getString("name"));
                        lblphone.setText(rs.getString("phone"));
                        lbljailterm.setText(rs.getString("jailterm"));
                    } else {
                        JOptionPane.showMessageDialog(null, "Record not found.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        delete = new JButton("Delete Record");
        delete.setBounds(50, 300, 200, 40);
        delete.setFont(buttonFont);
        delete.setBackground(Color.BLACK);
        delete.setForeground(Color.WHITE);
        delete.setBorder(BorderFactory.createLineBorder(Color.CYAN));
        delete.addActionListener(this);
        add(delete);

        back = new JButton("Back");
        back.setBounds(280, 300, 200, 40);
        back.setFont(buttonFont);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.setBorder(BorderFactory.createLineBorder(Color.CYAN));
        back.addActionListener(this);
        add(back);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/del.png"));
        Image i2 = i1.getImage().getScaledInstance(300, 320, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(350, 0, 600, 400);
        add(image);

        setSize(900, 700);
        setLocation(300, 150);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == delete) {
            String caseId = searchField.getText().trim();
            if (!caseId.isEmpty()) {
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this record?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    new DatabaseThread(caseId).start();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a Case ID to delete.");
            }
        } else if (ae.getSource() == back) {
            setVisible(false);
            new Home();
        }
    }

    private class DatabaseThread extends Thread {
        private String caseId;
    
        public DatabaseThread(String caseId) {
            this.caseId = caseId;
        }
    
        @Override
        public void run() {
            synchronized (DatabaseThread.class) {
                try (Conn conn = new Conn();
                     PreparedStatement pstmt = conn.getConnection().prepareStatement("DELETE FROM criminal WHERE caseId = ?")) {
                    pstmt.setString(1, caseId);
                    int rowsAffected = pstmt.executeUpdate();
                    SwingUtilities.invokeLater(() -> {
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(DeleteCriminal.this, "Record deleted successfully.");
                            setVisible(false);
                            new Home();
                        } else {
                            JOptionPane.showMessageDialog(DeleteCriminal.this, "Failed to delete the record.");
                        }
                    });
                } catch (SQLException e) {
                    e.printStackTrace();
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(DeleteCriminal.this, "Error deleting record: " + e.getMessage());
                    });
                }
            }
        }
    }

    public static void main(String[] args) {
        new DeleteCriminal();
    }
}