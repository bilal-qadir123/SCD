package crime.rec.management.system;
import java.awt.*;

import javax.swing.*;
import javax.swing.JButton.*;
import java.awt.event.*;

public class Splash extends JFrame implements ActionListener {
	
	JButton clickHere,click;
	
	Splash() {
		
		getContentPane().setBackground(Color.BLACK);
		setLayout(null);
		
		JLabel heading = new JLabel("      WELCOME TO "
				+"\n CRIME RECORD MANAGEMENT SYSTEM");
		heading.setBounds(0, 30, 1200,50);
		heading.setFont(new Font("serif", Font.PLAIN, 40));
		heading.setForeground(Color.WHITE);
		add (heading);
		
		
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/img2.jpg"));
		Image i2 = i1.getImage().getScaledInstance(1100, 500, Image.SCALE_DEFAULT);
		ImageIcon i3 = new ImageIcon(i2);
		JLabel image = new JLabel(i3);
		image.setBounds(50, 100, 1050, 500);
		add(image);
		
		clickHere = new JButton("Go to login page");
        clickHere.setBounds(400, 100, 300, 70);
        clickHere.setBackground(Color.BLACK);
        clickHere.setForeground(Color.WHITE);
        clickHere.setFont(new Font("Arial", Font.BOLD, 25));
        clickHere.addActionListener(this);
        image.add(clickHere);
        
        
        click = new JButton("Sign up");
        click.setBounds(400, 300, 300, 70);
        click.setBackground(Color.BLACK);
        click.setForeground(Color.WHITE);
        click.setFont(new Font("Arial", Font.BOLD, 25));
        click.addActionListener(this);
        image.add(click);
        
		
		
		setSize(1170, 650);
		setLocationRelativeTo(null);
		setVisible(true);
		
		while(true) {
			heading.setVisible(false);
			try {
				Thread.sleep(500);
			}catch(Exception e) {
			
			}
			heading.setVisible(true);
			try {
				Thread.sleep(500);
			}catch(Exception e) {
			
			}
		}	
		
	}
	
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == clickHere) {
			setVisible(false);
			new Login();
			
		} else {
			setVisible(false);
            new Register();
		}
			
		}
	
	public static void main(String args[]) {
		new Splash();
	}

}
