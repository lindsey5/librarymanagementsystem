package UserPage;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import Objects.ItemEventHandler;

public class StudentSettingsPage extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel container;
	private JLabel title;
	private JLabel studentIDlbl;
	private JLabel usernamelbl, fullnamelbl, courselbl, sectionlbl;
	private JTextField usernametxt, fullnametxt, coursetxt, sectiontxt;
	private JLabel securitylbl;
	private JButton updatebtn, changePassbtn;
	private JFrame frame;
	
	StudentSettingsPage(Student student, JFrame frame){
		this.frame = frame;
		container = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				setOpaque(false);
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);   
				g2.setPaint(getBackground());
			    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
			    g2.setPaint(Color.LIGHT_GRAY);
			    g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 30, 30);
			    g2.drawLine(0, 450, 600, 450);
			    super.paintComponent(g2);
			}
		};
		
		container.setBackground(Color.WHITE);
		container.setBounds(300, 150,600,650);
		container.setLayout(null);
		this.add(container);
		
		title = new JLabel("Settings");
		title.setBounds(50, 20, 150, 50);
		title.setFont(new Font("Arial",Font.BOLD,35));
		container.add(title);
		
		studentIDlbl = new JLabel();
		studentIDlbl.setBounds(50, 100, 400, 30);
		studentIDlbl.setFont(new Font("Arial", Font.BOLD, 20));
		container.add(studentIDlbl);
		
		usernamelbl = new JLabel("Username");
		usernamelbl.setBounds(50, 150, 150, 30);
		usernamelbl.setFont(new Font("Arial", Font.BOLD, 15));
		usernamelbl.setForeground(Color.LIGHT_GRAY);
		container.add(usernamelbl);
		
		usernametxt = new JTextField();
		usernametxt.setBounds(50, 180, 200, 40);
		container.add(usernametxt);
		
		fullnamelbl = new JLabel("Fullname");
		fullnamelbl.setBounds(320, 150, 150, 30);
		fullnamelbl.setFont(new Font("Arial", Font.BOLD, 15));
		fullnamelbl.setForeground(Color.LIGHT_GRAY);
		container.add(fullnamelbl);
		
		fullnametxt = new JTextField();
		fullnametxt.setBounds(320, 180, 200, 40);
		container.add(fullnametxt);
		
		courselbl = new JLabel("Course");
		courselbl.setBounds(50, 250, 150, 30);
		courselbl.setFont(new Font("Arial",Font.BOLD, 15));
		courselbl.setForeground(Color.LIGHT_GRAY);
		container.add(courselbl);
		
		coursetxt = new JTextField();
		coursetxt.setBounds(50, 280, 200, 40);
		container.add(coursetxt);
		
		sectionlbl = new JLabel("Section");
		sectionlbl.setBounds(320, 250, 150, 30);
		sectionlbl.setFont(new Font("Arial",Font.BOLD, 15));
		sectionlbl.setForeground(Color.LIGHT_GRAY);
		container.add(sectionlbl);
		
		sectiontxt = new JTextField();
		sectiontxt.setBounds(320, 280, 200, 40);
		container.add(sectiontxt);
		
		updatebtn = new JButton("Update Info");
		updatebtn.setBounds(50, 380, 150, 30);
		updatebtn.setBackground(Color.RED);
		updatebtn.setForeground(Color.WHITE);
		updatebtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		updatebtn.addActionListener(e->{
			updateInformation(student);
			setInformation(student, studentIDlbl, usernametxt, fullnametxt, coursetxt, sectiontxt);
			updateStudent(student);
		});
		container.add(updatebtn);
		
		securitylbl = new JLabel("Account Security");
		securitylbl.setBounds(50, 480, 200, 30);
		securitylbl.setFont(new Font("Arial",Font.BOLD,20));
		container.add(securitylbl);
		
		changePassbtn = new JButton("Change Password");
		changePassbtn.setBounds(50, 530, 150, 40);
		changePassbtn.setBackground(Color.WHITE);
		//changePassbtn.setFont(new Font("Arial",Font.BOLD,15));
		changePassbtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		changePassbtn.addActionListener(e-> showPinDialog(student));
		container.add(changePassbtn);
		
		setInformation(student, studentIDlbl, usernametxt, fullnametxt, coursetxt, sectiontxt);
		this.setLayout(null);
	}
	
	void setInformation(Student student, JLabel studentIDlbl, JTextField usernametxt, JTextField fullnametxt, JTextField coursetxt, JTextField sectiontxt) {
		studentIDlbl.setText("Student ID: " + student.getID());
		usernametxt.setText(student.getUsername());
		fullnametxt.setText(student.getFullname());
		coursetxt.setText(student.getCourse());
		sectiontxt.setText(student.getSection());
	}
	
	void updateInformation(Student student) {
		student.setUsername(usernametxt.getText());
		student.setFullname(fullnametxt.getText());
		student.setCourse(coursetxt.getText());
		student.setSection(sectiontxt.getText());
	}
	
	void updateStudent(Student student) {
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
			PreparedStatement stmt = Conn.prepareStatement("UPDATE student_accounts SET username=?, fullname=?, course=?, section=? WHERE student_id=?");
			stmt.setString(1, student.getUsername());
			stmt.setString(2, student.getFullname());
			stmt.setString(3, student.getCourse());
			stmt.setString(4, student.getSection());
			stmt.setString(5, student.getID());
			stmt.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Update Successful");
			
	    }catch(SQLException e1) {
	    	e1.printStackTrace();
	    }
	}
	
	void showPinDialog(Student student) {
		JDialog overlayDialog  = new JDialog(frame, "Overlay", true) ;
        overlayDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        overlayDialog.setUndecorated(true);
   	
        JPanel overlayPanel = new JPanel(null);
        overlayPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        overlayPanel.setBackground(Color.WHITE);
        overlayDialog.add(overlayPanel);
        
        JButton closebtn = new JButton("X");
        closebtn.addActionListener(e -> overlayDialog.dispose());
        closebtn.setFont(new Font("Arial",Font.BOLD,17));
        closebtn.setBounds(330, 20, 50, 30);
        closebtn.setBackground(Color.WHITE);
        closebtn.setForeground(Color.BLACK);
        closebtn.setFocusable(false);
        closebtn.setBorder(null);
        overlayPanel.add(closebtn);
        
        JLabel pinlbl = new JLabel("Enter your Pin");
		pinlbl.setBounds(100, 50, 200, 30);
		pinlbl.setForeground(Color.GRAY);
		pinlbl.setFont(new Font("Arial",Font.BOLD,15));
		overlayPanel.add(pinlbl);
		
		MaskFormatter mask;
	    try {
	    	mask = new MaskFormatter("####");
	        mask.setPlaceholderCharacter('0');
	    }catch (ParseException e) {
	        e.printStackTrace();
	        return;
	    }
		
	    JFormattedTextField pintxt = new JFormattedTextField(mask);
		pintxt.setColumns(4);
		pintxt.setBounds(100, 80, 200, 30);
		overlayPanel.add(pintxt);
		
		JButton submitbtn = new JButton("Submit");
	    submitbtn.setBounds(130, 130, 150, 40);
	    submitbtn.setBackground(Color.RED);
	    submitbtn.setForeground(Color.WHITE);
	    submitbtn.addActionListener(e->{
	    	if(Integer.parseInt(pintxt.getText()) == student.getPin()) {
	    		showChangePasswordDialog(student);
	    		overlayDialog.dispose();
	    	}else {
	    		JOptionPane.showMessageDialog(null, "Incorrect Pin","",JOptionPane.WARNING_MESSAGE);
	    	}
	    });
	    overlayPanel.add(submitbtn);
        
        overlayDialog.setSize(400,200);
	    overlayDialog.setLocationRelativeTo(null);
	    overlayDialog.setVisible(true);
	}
	
	void showChangePasswordDialog(Student student) {
		JDialog overlayDialog  = new JDialog(frame, "Overlay", true) ;
        overlayDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        overlayDialog.setUndecorated(true);
   	
        JPanel overlayPanel = new JPanel(null);
        overlayPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        overlayPanel.setBackground(Color.WHITE);
        overlayDialog.add(overlayPanel);
        
        JButton closebtn = new JButton("X");
        closebtn.addActionListener(e -> overlayDialog.dispose());
        closebtn.setFont(new Font("Arial",Font.BOLD,17));
        closebtn.setBounds(280, 20, 50, 30);
        closebtn.setBackground(Color.WHITE);
        closebtn.setForeground(Color.BLACK);
        closebtn.setFocusable(false);
        closebtn.setBorder(null);
        overlayPanel.add(closebtn);
        
        JLabel currentPasslbl = new JLabel("Current Password");
        currentPasslbl.setBounds(50, 50, 150, 30);
        currentPasslbl.setFont(new Font("Arial",Font.BOLD,15));
        overlayPanel.add(currentPasslbl);
        
        JPasswordField currentPasstxt = new JPasswordField();
        currentPasstxt.setBounds(50,80,200,30);
        overlayPanel.add(currentPasstxt);
        
        JCheckBox currentPassCheck = new JCheckBox("Show");
        currentPassCheck.setBounds(260, 80, 70, 30);
        currentPassCheck.setBackground(Color.WHITE);
        currentPassCheck.addItemListener(new ItemEventHandler(currentPassCheck, currentPasstxt));
        overlayPanel.add(currentPassCheck);
        
        JLabel newPasslbl = new JLabel("New Password");
        newPasslbl.setBounds(50, 130, 150, 30);
        newPasslbl.setFont(new Font("Arial",Font.BOLD,15));
        overlayPanel.add(newPasslbl);
        
        JPasswordField newPasstxt = new JPasswordField();
        newPasstxt.setBounds(50,160,200,30);
        overlayPanel.add(newPasstxt);
        
        JCheckBox newPassCheck = new JCheckBox("Show");
        newPassCheck.setBounds(260, 160, 70, 30);
        newPassCheck.setBackground(Color.WHITE);
        newPassCheck.addItemListener(new ItemEventHandler(newPassCheck, newPasstxt));
        overlayPanel.add(newPassCheck);
        
        JLabel confirmNewPasslbl = new JLabel("Confirm New Password");
        confirmNewPasslbl.setBounds(50, 210, 200, 30);
        confirmNewPasslbl.setFont(new Font("Arial",Font.BOLD,15));
        overlayPanel.add(confirmNewPasslbl);
        
        JPasswordField confirmNewPasstxt = new JPasswordField();
        confirmNewPasstxt.setBounds(50,240,200,30);
        overlayPanel.add(confirmNewPasstxt);
        
        JCheckBox confirmNewPassCheck = new JCheckBox("Show");
        confirmNewPassCheck.setBounds(260, 240, 70, 30);
        confirmNewPassCheck.setBackground(Color.WHITE);
        confirmNewPassCheck.addItemListener(new ItemEventHandler(confirmNewPassCheck, confirmNewPasstxt));
        overlayPanel.add(confirmNewPassCheck);
        
        JButton savebtn = new JButton("Save");
        savebtn.setBounds(100,320,150,30);
        savebtn.setBackground(Color.RED);
        savebtn.setForeground(Color.WHITE);
        savebtn.addActionListener(e->{
        	if(String.valueOf(currentPasstxt.getPassword()).isBlank() || String.valueOf(newPasstxt.getPassword()).isBlank() 
        			|| String.valueOf(confirmNewPasstxt.getPassword()).isBlank()) {
        		JOptionPane.showMessageDialog(null, "Fill the blanks","",JOptionPane.WARNING_MESSAGE);
        	}else if(!student.getPassword().equals(String.valueOf(currentPasstxt.getPassword()))) {
        		JOptionPane.showMessageDialog(null, "Current Password is wrong");
        	}else if(!String.valueOf(newPasstxt.getPassword()).equals(String.valueOf(confirmNewPasstxt.getPassword()))) {
        		JOptionPane.showMessageDialog(null, "New Password doesn't match");
        	}else {
        		String password = String.valueOf(newPasstxt.getPassword());
            	student.setPassword(password);
            	changePassword(student.getID(), student.getPassword());
        	}
        	
        });
        overlayPanel.add(savebtn);
        
        overlayDialog.setSize(350,400);
	    overlayDialog.setLocationRelativeTo(null);
	    overlayDialog.setVisible(true);
	}
	
	void changePassword(String student_id, String password) {
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
			PreparedStatement stmt = Conn.prepareStatement("UPDATE student_accounts SET password=? WHERE student_id=?");
			stmt.setString(1, password);
			stmt.setString(2, student_id);
			stmt.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Update Successful");
			
	    }catch(SQLException e1) {
	    	e1.printStackTrace();
	    }
	}
	
	
}
