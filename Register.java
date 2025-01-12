package crime.rec.management.system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Register extends JFrame implements ActionListener {
    JPanel contentPane;
    JTextField firstname;
    JTextField lastname;
    JTextField email;
    JTextField username;
    JTextField mob;
    JPasswordField passwordField;
    JButton register, back;

    // Regular expressions for validation
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String MOBILE_PATTERN = "^\\+92[0-9]{10}$";

    public Register() {
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        getContentPane().setBackground(Color.WHITE);

        JLabel lblNewUserRegister = new JLabel("REGISTRATION");
        lblNewUserRegister.setFont(new Font("Times New Roman", Font.BOLD, 42));
        lblNewUserRegister.setBounds(362, 52, 325, 50);
        contentPane.add(lblNewUserRegister);

        JLabel lblFirstName = new JLabel("First Name");
        lblFirstName.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblFirstName.setBounds(58, 152, 124, 43);
        contentPane.add(lblFirstName);

        JLabel lblLastName = new JLabel("Last Name");
        lblLastName.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblLastName.setBounds(58, 243, 124, 29);
        contentPane.add(lblLastName);

        JLabel lblEmailAddress = new JLabel("Email Address");
        lblEmailAddress.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblEmailAddress.setBounds(58, 324, 147, 36);
        contentPane.add(lblEmailAddress);

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblUsername.setBounds(542, 159, 99, 29);
        contentPane.add(lblUsername);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblPassword.setBounds(542, 245, 99, 24);
        contentPane.add(lblPassword);

        JLabel lblMobileNumber = new JLabel("Mobile Number");
        lblMobileNumber.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblMobileNumber.setBounds(542, 329, 139, 26);
        contentPane.add(lblMobileNumber);

        firstname = new JTextField();
        firstname.setFont(new Font("Tahoma", Font.PLAIN, 32));
        firstname.setBounds(214, 151, 228, 50);
        contentPane.add(firstname);
        firstname.setColumns(10);

        lastname = new JTextField();
        lastname.setFont(new Font("Tahoma", Font.PLAIN, 32));
        lastname.setBounds(214, 235, 228, 50);
        contentPane.add(lastname);
        lastname.setColumns(10);

        email = new JTextField();
        email.setFont(new Font("Tahoma", Font.PLAIN, 32));
        email.setBounds(214, 320, 228, 50);
        contentPane.add(email);
        email.setColumns(10);

        username = new JTextField();
        username.setFont(new Font("Tahoma", Font.PLAIN, 32));
        username.setBounds(707, 151, 228, 50);
        contentPane.add(username);
        username.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Tahoma", Font.PLAIN, 32));
        passwordField.setBounds(707, 235, 228, 50);
        contentPane.add(passwordField);

        mob = new JTextField();
        mob.setFont(new Font("Tahoma", Font.PLAIN, 32));
        mob.setBounds(707, 320, 228, 50);
        contentPane.add(mob);
        mob.setColumns(10);

        register = new JButton("Register");
        register.setFont(new Font("Arial", Font.BOLD, 25));
        register.setBounds(250, 400, 150, 40);
        register.addActionListener(this);
        register.setBackground(Color.BLACK);
        register.setForeground(Color.WHITE);
        contentPane.add(register);

        back = new JButton("Go back");
        back.setFont(new Font("Arial", Font.BOLD, 25));
        back.setBounds(450, 400, 150, 40);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.addActionListener(this);
        contentPane.add(back);

        contentPane.setLayout(null);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/img2.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1120, 630, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(5, 5, 1120, 630);
        contentPane.add(image);

        setSize(1120, 630);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == register) {
            String firstName = firstname.getText();
            String lastName = lastname.getText();
            String emailId = email.getText();
            String userName = username.getText();
            String mobileNumber = mob.getText();
            String password = new String(passwordField.getPassword());
    
            // Check if any field is empty
            if (firstName.isEmpty() || lastName.isEmpty() || emailId.isEmpty() ||
                userName.isEmpty() || password.isEmpty() || mobileNumber.isEmpty()) {
                JOptionPane.showMessageDialog(register, "Please fill in all required fields.");
                return;
            }
    
            // Validate email
            if (!isValidEmail(emailId)) {
                JOptionPane.showMessageDialog(register, "Please enter a valid email address.");
                return;
            }
    
            // Validate mobile number
            if (!isValidMobile(mobileNumber)) {
                JOptionPane.showMessageDialog(register, "Mobile number must start with '+92' and have 12 characters.");
                return;
            }
    
            // Disable the register button
            register.setEnabled(false);
    
            // Start a new thread for registration
            RegisterThread registerThread = new RegisterThread(firstName, lastName, userName, password, emailId, mobileNumber);
            registerThread.start();
        } else if (ae.getSource() == back) {
            setVisible(false);
            new Login();
        }
    }

    // Method to validate email
    public boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Method to validate mobile number
    public boolean isValidMobile(String mobile) {
        Pattern pattern = Pattern.compile(MOBILE_PATTERN);
        Matcher matcher = pattern.matcher(mobile);
        return matcher.matches();
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> {
            new Register();
        });
    }

    private class RegisterThread extends Thread {
        private String firstName, lastName, userName, password, emailId, mobileNumber;
    
        public RegisterThread(String firstName, String lastName, String userName, String password, String emailId, String mobileNumber) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.userName = userName;
            this.password = password;
            this.emailId = emailId;
            this.mobileNumber = mobileNumber;
        }
    
        @Override
        public void run() {
            try {
                Conn conn = new Conn();
                String query = "INSERT INTO register VALUES('" + firstName + "','" + lastName + "','" + userName + "','" + password + "','" + emailId + "','" + mobileNumber + "')";
                conn.s.executeUpdate(query);
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(Register.this, "You are now registered");
                    register.setEnabled(true); // Re-enable the button
                    setVisible(false);
                    new Login();
                });
            } catch (Exception e) {
                e.printStackTrace();
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(Register.this, "Error registering user: " + e.getMessage());
                    register.setEnabled(true); // Re-enable the button
                });
            }
        }
    }
}