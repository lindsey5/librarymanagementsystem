package SignUpPage;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import javax.swing.text.MaskFormatter;

import LoginPage.StudentLoginPage;
import Objects.ItemEventHandler;
import Objects.RoundRectPasswordField;
import Objects.RoundRectTextField;
import UserPage.Student;
import UserPage.StudentFrame;

public class StudentSignUpPage extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JLabel title;
	JLabel student_idlbl, usernamelbl, fullnamelbl, passwordlbl, conpasswordlbl, courselbl, sectionlbl, pinlbl;
	RoundRectTextField student_idtxt, usernametxt, fullnametxt, coursetxt, sectiontxt;
	RoundRectPasswordField passwordtxt, conpasswordtxt;
	JCheckBox passCheckBox, conpassCheckBox;
	JFormattedTextField pintxt;
	JButton backbtn, submitbtn;
	
	public StudentSignUpPage(){
		submitbtn = new JButton("Submit");
		submitbtn.setBackground(Color.RED);
		submitbtn.setForeground(Color.WHITE);
		submitbtn.setBounds(350,520,150,40);
		submitbtn.addActionListener(this);
		
		backbtn = new JButton("Back");
		backbtn.setBackground(Color.LIGHT_GRAY);
		backbtn.setForeground(Color.BLACK);
		backbtn.setBounds(120,520,150,40);
		backbtn.addActionListener(this);
		    
		sectiontxt = new RoundRectTextField();
		sectiontxt.setBounds(350, 430, 250, 40);
			
		sectionlbl = new JLabel("Section");
		sectionlbl.setFont(new Font("Arial",Font.BOLD,15));
		sectionlbl.setBounds(350, 400, 100, 30);
		sectionlbl.setForeground(Color.GRAY);
		
		coursetxt = new RoundRectTextField();
		coursetxt.setBounds(20, 430, 250, 40);
		
		courselbl = new JLabel("Course");
		courselbl.setFont(new Font("Arial",Font.BOLD,15));
		courselbl.setBounds(20, 400, 100, 30);
		courselbl.setForeground(Color.GRAY);
		
		conpasswordtxt = new RoundRectPasswordField();
		conpasswordtxt.setBounds(350, 310, 250, 40);
		
		conpassCheckBox = new JCheckBox("Show");
		conpassCheckBox.setBounds(350, 350, 100, 30);
		conpassCheckBox.setBackground(Color.WHITE);
		conpassCheckBox.addItemListener(new ItemEventHandler(conpassCheckBox, conpasswordtxt));
		conpassCheckBox.setFocusable(false);
		
		conpasswordlbl = new JLabel("Confirm Password");
		conpasswordlbl.setFont(new Font("Arial",Font.BOLD,15));
		conpasswordlbl.setBounds(350, 280, 200, 30);
		conpasswordlbl.setForeground(Color.GRAY);
		
		passwordtxt = new RoundRectPasswordField();
		passwordtxt.setBounds(20, 310, 250, 40);
		
		passCheckBox = new JCheckBox("Show");
		passCheckBox.setBounds(20, 350, 100, 30);
		passCheckBox.setBackground(Color.WHITE);
		passCheckBox.addItemListener(new ItemEventHandler(passCheckBox, passwordtxt));
		passCheckBox.setFocusable(false);
		
		passwordlbl = new JLabel("Password");
		passwordlbl.setFont(new Font("Arial",Font.BOLD,15));
		passwordlbl.setBounds(20, 280, 100, 30);
		passwordlbl.setForeground(Color.GRAY);
		
		fullnametxt = new RoundRectTextField();
		fullnametxt.setBounds(350, 220, 250, 40);
		
		fullnamelbl = new JLabel("Fullname");
		fullnamelbl.setFont(new Font("Arial",Font.BOLD,15));
		fullnamelbl.setBounds(350, 190, 100, 30);
		fullnamelbl.setForeground(Color.GRAY);
		
		usernametxt = new RoundRectTextField();
		usernametxt.setBounds(20, 220, 250, 40);
		
		usernamelbl = new JLabel("Username");
		usernamelbl.setFont(new Font("Arial",Font.BOLD,15));
		usernamelbl.setBounds(20, 190, 100, 30);
		usernamelbl.setForeground(Color.GRAY);
		
		student_idtxt = new RoundRectTextField();
		student_idtxt.setBounds(20, 130, 250, 40);
		
		student_idlbl = new JLabel("Student ID");
		student_idlbl.setFont(new Font("Arial",Font.BOLD,15));
		student_idlbl.setBounds(20, 100, 100, 30);
		student_idlbl.setForeground(Color.GRAY);
		
		title = new JLabel("Student Sign Up");
		title.setForeground(Color.BLACK);
		title.setBounds(20, 20, 250, 40);
		title.setFont(new Font("Arial",Font.BOLD,30));
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(650,650);
		this.setLocationRelativeTo(null);
		this.getContentPane().setBackground(Color.WHITE);
		this.setResizable(false);
		this.setLayout(null);
		this.add(submitbtn);
		this.add(backbtn);
		this.add(sectionlbl);
		this.add(sectiontxt);
		this.add(coursetxt);
		this.add(courselbl);
		this.add(conpassCheckBox);
		this.add(conpasswordtxt);
		this.add(conpasswordlbl);
		this.add(passCheckBox);
		this.add(passwordtxt);
		this.add(passwordlbl);
		this.add(fullnametxt);
		this.add(fullnamelbl);
		this.add(usernametxt);
		this.add(usernamelbl);
		this.add(student_idtxt);
		this.add(student_idlbl);
		this.add(title);
		this.setVisible(true);	
	}
	
	void showPinDialog(String studentID, String username, String fullname, String password, String course, String section) {
		JDialog overlayDialog = new JDialog(this, "Overlay", true);
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
        
        pinlbl = new JLabel("Enter your Pin");
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
	    
	    JButton createbtn = new JButton("Create Account");
	    createbtn.setBounds(130, 130, 150, 40);
	    createbtn.setBackground(Color.RED);
	    createbtn.setForeground(Color.WHITE);
	    createbtn.addActionListener(e->{
	    	if(pintxt.getText().equals("0000")) {
				JOptionPane.showMessageDialog(null, "Invalid Pin","",JOptionPane.WARNING_MESSAGE);
			}else {
				int pin = Integer.parseInt(pintxt.getText());
				createAccount(studentID, username, fullname, password, pin, course, section);
			}
	    });
	    overlayPanel.add(createbtn);
		
		pintxt = new JFormattedTextField(mask);
		pintxt.setColumns(4);
		pintxt.setBounds(100, 80, 200, 30);
		overlayPanel.add(pintxt);
        
        overlayDialog.setSize(400,200);
	    overlayDialog.setLocationRelativeTo(null);
	    overlayDialog.setVisible(true);
	}
	
	void createAccount(String studentID, String username, String fullname, String password, int pin, String course, String section) {
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
			PreparedStatement stmt = Conn.prepareStatement("INSERT INTO user_id_table (user_id) VALUES (?)");
			stmt.setString(1, studentID);
			stmt.executeUpdate();
			
			stmt = Conn.prepareStatement("INSERT INTO student_accounts (student_ID, username, fullname, password, pin, course, section) VALUES(?,?,?,?,?,?,?)");
			stmt.setString(1, studentID);
			stmt.setString(2, username);
			stmt.setString(3, fullname);
			stmt.setString(4, password);
			stmt.setInt(5, pin);
			stmt.setString(6, course);
			stmt.setString(7, section);			
			stmt.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Accout Successfully Created");
			new StudentFrame(new Student(studentID, username, password, fullname, course, section, pin));
			this.dispose();
			
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    }
	}
	
	boolean checkIfUsernameHasBeenUsed(String username) {
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
			PreparedStatement stmt = Conn.prepareStatement("SELECT * FROM student_accounts where username=?");
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				return true;
			}
			
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    }
		
		return false;
	}
	
	boolean checkIfStudentIDHasBeenUsed(String studentID) {
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
			PreparedStatement stmt = Conn.prepareStatement("SELECT * FROM student_accounts where student_ID=?");
			stmt.setString(1, studentID);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				return true;
			}
			
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    }
		
		return false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == submitbtn) {
			String password = String.valueOf(passwordtxt.getPassword());
			String conPass = String.valueOf(conpasswordtxt.getPassword());
			if(student_idtxt.getText().isBlank() || usernametxt.getText().isBlank() || fullnametxt.getText().isBlank() || password.isBlank() 
					|| conPass.isBlank() || coursetxt.getText().isBlank() || sectiontxt.getText().isBlank()) {
				JOptionPane.showMessageDialog(null, "Fill the Blanks");
			}else if(checkIfStudentIDHasBeenUsed(student_idtxt.getText())) {
				JOptionPane.showMessageDialog(null, "Student ID already used","",JOptionPane.WARNING_MESSAGE);
			
			}else if(student_idtxt.getText().length() > 20) {
				JOptionPane.showMessageDialog(null, "Student ID is too long","",JOptionPane.WARNING_MESSAGE);
				
			}else if(student_idtxt.getText().length() < 8 ) {
				JOptionPane.showMessageDialog(null, "Student ID is too short","",JOptionPane.WARNING_MESSAGE);
				
			}else if(usernametxt.getText().length() < 8 ) {
				JOptionPane.showMessageDialog(null, "Username is too short","",JOptionPane.WARNING_MESSAGE);
				
			}else if(checkIfUsernameHasBeenUsed(usernametxt.getText())) {
				JOptionPane.showMessageDialog(null, "Username already used","",JOptionPane.WARNING_MESSAGE);
				
			}else if(usernametxt.getText().length() >20) {
				JOptionPane.showMessageDialog(null, "Username is too long","",JOptionPane.WARNING_MESSAGE);
				
			}else if(fullnametxt.getText().length() > 50) {
				JOptionPane.showMessageDialog(null, "Fullname is too long","",JOptionPane.WARNING_MESSAGE);
				
			}else if(password.length() > 20) {
				JOptionPane.showMessageDialog(null, "Password is too long","",JOptionPane.WARNING_MESSAGE);
				
			}else if(coursetxt.getText().length() > 20) {
				JOptionPane.showMessageDialog(null, "Course is too long","",JOptionPane.WARNING_MESSAGE);
				
			}else if(sectiontxt.getText().length() > 10) {
				JOptionPane.showMessageDialog(null, "Section is too long","",JOptionPane.WARNING_MESSAGE);
				
			}else if(!password.equals(conPass)) {
				JOptionPane.showMessageDialog(null, "Password doesn't match","",JOptionPane.WARNING_MESSAGE);
				
			}else {
				showPinDialog(student_idtxt.getText(), usernametxt.getText(), fullnametxt.getText(), password, coursetxt.getText(), sectiontxt.getText());
			}
			
		}
		
		
		if(e.getSource() == backbtn) {
			this.dispose();
			new StudentLoginPage();
		}
		
	}

}
