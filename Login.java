package crime.rec.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {
    JTextField tfusername;
    JButton login, back;
    JPasswordField tfpassword; 
    private boolean loginSuccess = false;
    Conn conn = new Conn();

    Login() {
        getContentPane().setBackground(Color.GRAY);
        setLayout(null);

        JLabel lblusername = new JLabel("Username");
        lblusername.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblusername.setBounds(200, 20, 100, 30);
        add(lblusername);

        tfusername = new JTextField();
        tfusername.setFont(new Font("Tahoma", Font.PLAIN, 32));
        tfusername.setBounds(400, 20, 200, 30);
        add(tfusername);

        JLabel lblpassword = new JLabel("Password");
        lblpassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblpassword.setBounds(200, 70, 100, 30);
        add(lblpassword);

        tfpassword = new JPasswordField();
        tfpassword.setBounds(400, 70, 200, 30);
        add(tfpassword);

        login = new JButton("LOGIN");
        login.setBounds(300, 140, 150, 30);
        login.setBackground(Color.BLACK);
        login.setForeground(Color.WHITE);
        login.addActionListener(this);
        add(login);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/img2.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1120, 630, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(5, 5, 1120, 630);
        add(image);

        setSize(1000, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == login) {
            final String username = tfusername.getText();
            final String password = new String(tfpassword.getPassword());

            login.setEnabled(false); // Disable the login button to prevent multiple clicks

            LoginThread loginThread = new LoginThread(username, password); // Create a new thread
            loginThread.start(); // Start the thread
        }
    }

    // New Thread class that extends Thread and overrides run method
    class LoginThread extends Thread {
        private final String username;
        private final String password;

        public LoginThread(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        public void run() {
            Conn connectionInstance = new Conn();
            try (Connection c = connectionInstance.c;
                 PreparedStatement pstmt = c.prepareStatement("SELECT * FROM register WHERE username = ? AND password = ?")) {

                pstmt.setString(1, username);
                pstmt.setString(2, password);
                ResultSet rs = pstmt.executeQuery();

                loginSuccess = rs.next();
            } catch (SQLException e) {
                e.printStackTrace();
                loginSuccess = false;
            } finally {
                SwingUtilities.invokeLater(() -> {
                    login.setEnabled(true); // Re-enable the login button

                    if (loginSuccess) {
                        setVisible(false); // Hide the login screen
                        new Home(); // Open the home screen after successful login
                    } else {
                        JOptionPane.showMessageDialog(Login.this, "Invalid username or password");
                    }
                });
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login());
    }
}
