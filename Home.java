package crime.rec.management.system;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Home extends JFrame implements ActionListener{
	
	JButton view, add, update, remove;

	
	Home() {
		
		setLayout(null);
		
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/home.jpg"));
		Image i2 = i1.getImage().getScaledInstance(1120, 630, Image.SCALE_DEFAULT);
		ImageIcon i3 = new ImageIcon(i2);
		
		
		JLabel image = new JLabel(i3);
		image.setBounds(0, 0, 1120, 630);
		add(image);
		
		JLabel heading = new JLabel("CRIME RECORD MANAGEMENT SYSTEM");
		heading.setBounds(200, 30, 1120,50);
		heading.setFont(new Font("CALIBRI", Font.BOLD, 40));
		image.add(heading);
		
		
		JLabel heading1 = new JLabel("HOME ->");
		heading1.setBounds(200, 120, 1120,50);
		heading1.setFont(new Font("CALIBRI", Font.BOLD, 40));
		image.add(heading1);
		
		
		
		add = new JButton("Add New Criminal");
		add.setBounds(500, 200, 220, 60); 
        add.setFont(new Font("CALIBRI", Font.BOLD, 20));
        add.setBackground(Color.BLACK); 
        add.setForeground(Color.WHITE);
		add.addActionListener(this);
		image.add(add);
		
		view = new JButton("View Crime Rec");
		view.setBounds(500, 280, 220, 60); 
        view.setFont(new Font("CALIBRI", Font.BOLD, 20));
        view.setBackground(Color.BLACK); 
        view.setForeground(Color.WHITE);
		view.addActionListener(this);
		image.add(view);
		
		update = new JButton("Update Record");
		update.setBounds(500, 360, 220, 60); 
        update.setFont(new Font("CALIBRI", Font.BOLD, 20));
        update.setBackground(Color.BLACK); 
        update.setForeground(Color.WHITE);
		update.addActionListener(this);
		image.add(update);
		
		remove = new JButton("Remove Record");
		remove.setBounds(500, 440, 220, 60); 
        remove.setFont(new Font("CALIBRI", Font.BOLD, 20));
        remove.setBackground(Color.BLACK);
        remove.setForeground(Color.WHITE);
		remove.addActionListener(this);
		image.add(remove);
		
		
		
		 int contentPaneWidth = image.getWidth();
	        int contentPaneHeight = image.getHeight();
	        int buttonWidth = 200; 
	        int buttonHeight = 40; 

	        add.setBounds((contentPaneWidth - buttonWidth) / 2, contentPaneHeight / 2 - 20, buttonWidth, buttonHeight);
	        view.setBounds((contentPaneWidth - buttonWidth) / 2, contentPaneHeight / 2 + 30, buttonWidth, buttonHeight);
	        update.setBounds((contentPaneWidth - buttonWidth) / 2, contentPaneHeight / 2 + 80, buttonWidth, buttonHeight);
	        remove.setBounds((contentPaneWidth - buttonWidth) / 2, contentPaneHeight / 2 + 130, buttonWidth, buttonHeight);
		
		
		
		setSize(1120, 630);
		setLocationRelativeTo(null);
		setVisible(true);
		
	}
	
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == add) {
			setVisible(false);
			new AddCriminal();
			
		} else if (ae.getSource() == view) {
			setVisible(false);
			new ViewCriminal();
		
		} else if (ae.getSource() == update) {
			setVisible(false);
			new ViewCriminal();
		} else {
			setVisible(false);
            new DeleteCriminal();
		}
			
		}
	public static void main(String[] args) {
		new Home();
	}
}
