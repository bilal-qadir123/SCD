package crime.rec.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;
import java.awt.event.*;

public class ViewCriminal extends JFrame implements ActionListener {

    private JTable table;
    private JTextField searchField;
    private JButton search, print, update, back;
    private Conn c; // Reusing a single connection instance
    
    ViewCriminal() {
        c = new Conn(); // Initialize the connection once
        
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        
        table = new JTable();
        
        JLabel searchlbl = new JLabel("Search by Case Id");
        searchlbl.setBounds(20, 20, 150, 20);
        add(searchlbl);
        
        searchField = new JTextField();
        searchField.setBounds(180, 20, 150, 20);
        add(searchField);

        loadData();
        
        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(0, 100, 900, 600);
        add(jsp);
        
        search = new JButton("Search");
        search.setBounds(20, 70, 80, 20);
        search.addActionListener(this);
        add(search);
        
        print = new JButton("Print");
        print.setBounds(120, 70, 80, 20);
        print.addActionListener(this);
        add(print);
        
        update = new JButton("Update");
        update.setBounds(220, 70, 80, 20);
        update.addActionListener(this);
        add(update);
        
        back = new JButton("Back");
        back.setBounds(320, 70, 80, 20);
        back.addActionListener(this);
        add(back);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/img4.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1120, 630, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(5, 5, 1120, 630);
        add(image);
        
        setSize(900, 700);
        setLocation(300, 100);
        setVisible(true);
    }

    private void loadData() {
        new LoadCriminalsThread(c).start(); // Passing the connection object
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == search) {
            String caseId = searchField.getText();
            if (caseId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a case ID.");
                return;
            }
            new SearchCriminalThread(c, caseId).start(); // Passing the connection object
        } else if (ae.getSource() == print) {
            try {
                table.print();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == update) {
            String caseId = searchField.getText();
            if (caseId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a case ID.");
                return;
            }
            setVisible(false);
            new UpdateCriminal(caseId);
        } else {
            setVisible(false);
            new Home();
        }
    }

    // Thread to load all criminals
    class LoadCriminalsThread extends Thread {
        private Conn c; // Conn object passed as a parameter

        public LoadCriminalsThread(Conn c) {
            this.c = c; // Using the shared connection
        }

        @Override
        public void run() {
            try {
                ResultSet rs = c.s.executeQuery("SELECT * FROM criminal");
                SwingUtilities.invokeLater(() -> table.setModel(DbUtils.resultSetToTableModel(rs)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Thread to search criminals by case ID
    class SearchCriminalThread extends Thread {
        private Conn c;
        private final String caseId;

        public SearchCriminalThread(Conn c, String caseId) {
            this.c = c;
            this.caseId = caseId;
        }

        @Override
        public void run() {
            try {
                PreparedStatement pstmt = c.c.prepareStatement("SELECT * FROM criminal WHERE caseId = ?");
                pstmt.setString(1, caseId);
                ResultSet rs = pstmt.executeQuery();
                SwingUtilities.invokeLater(() -> table.setModel(DbUtils.resultSetToTableModel(rs)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ViewCriminal::new);
    }
}
