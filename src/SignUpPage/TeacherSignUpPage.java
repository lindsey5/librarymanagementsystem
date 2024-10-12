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
import java.util.Random;

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

import LoginPage.TeacherLoginPage;
import Objects.ItemEventHandler;
import Objects.RoundRectPasswordField;
import Objects.RoundRectTextField;
import UserPage.Teacher;
import UserPage.TeacherFrame;

public class TeacherSignUpPage extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel title, usernamelbl, fullnamelbl, departmentlbl, passwordlbl, conpasswordlbl;
	private RoundRectTextField usernametxt, fullnametxt, departmenttxt;
	private RoundRectPasswordField passwordtxt, conpasswordtxt;
	private JCheckBox passCheckBox, conpassCheckBox;
	private JButton submitbtn, backbtn;
	
	public TeacherSignUpPage() {
		
		title = new JLabel("Teacher Sign Up");
		title.setForeground(Color.BLACK);
		title.setBounds(20, 20, 250, 40);
		title.setFont(new Font("Arial",Font.BOLD,30));
		
		usernamelbl = new JLabel("Username");
		usernamelbl.setFont(new Font("Arial",Font.BOLD,15));
		usernamelbl.setBounds(20, 100, 100, 30);
		usernamelbl.setForeground(Color.GRAY);
		
		usernametxt = new RoundRectTextField();
		usernametxt.setBounds(20, 130, 250, 40);
		
		fullnamelbl = new JLabel("Fullname");
		fullnamelbl.setFont(new Font("Arial",Font.BOLD,15));
		fullnamelbl.setBounds(20, 190, 100, 30);
		fullnamelbl.setForeground(Color.GRAY);
		
		fullnametxt = new RoundRectTextField();
		fullnametxt.setBounds(20, 220, 250, 40);
		
		departmenttxt = new RoundRectTextField();
		departmenttxt.setBounds(350, 220, 250, 40);
		
		departmentlbl = new JLabel("Department");
		departmentlbl.setFont(new Font("Arial",Font.BOLD,15));
		departmentlbl.setBounds(350, 190, 100, 30);
		departmentlbl.setForeground(Color.GRAY);
		
		passwordlbl = new JLabel("Password");
		passwordlbl.setFont(new Font("Arial",Font.BOLD,15));
		passwordlbl.setBounds(20, 280, 100, 30);
		passwordlbl.setForeground(Color.GRAY);
		
		passwordtxt = new RoundRectPasswordField();
		passwordtxt.setBounds(20, 310, 250, 40);
		
		passCheckBox = new JCheckBox("Show");
		passCheckBox.setBounds(20, 350, 100, 30);
		passCheckBox.setBackground(Color.WHITE);
		passCheckBox.addItemListener(new ItemEventHandler(passCheckBox, passwordtxt));
		passCheckBox.setFocusable(false);
		
		conpasswordlbl = new JLabel("Confirm Password");
		conpasswordlbl.setFont(new Font("Arial",Font.BOLD,15));
		conpasswordlbl.setBounds(350, 280, 200, 30);
		conpasswordlbl.setForeground(Color.GRAY);
		
		conpasswordtxt = new RoundRectPasswordField();
		conpasswordtxt.setBounds(350, 310, 250, 40);
		
		conpassCheckBox = new JCheckBox("Show");
		conpassCheckBox.setBounds(350, 350, 100, 30);
		conpassCheckBox.setBackground(Color.WHITE);
		conpassCheckBox.addItemListener(new ItemEventHandler(conpassCheckBox, conpasswordtxt));
		conpassCheckBox.setFocusable(false);
		
		submitbtn = new JButton("Submit");
		submitbtn.setBackground(Color.RED);
		submitbtn.setForeground(Color.WHITE);
		submitbtn.setBounds(350,450,150,40);
		submitbtn.addActionListener(this);
		
		backbtn = new JButton("Back");
		backbtn.setBackground(Color.LIGHT_GRAY);
		backbtn.setForeground(Color.BLACK);
		backbtn.setBounds(120,450,150,40);
		backbtn.addActionListener(this);
			
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(650,600);
		this.setLocationRelativeTo(null);
		this.getContentPane().setBackground(Color.WHITE);
		this.setResizable(false);
		this.setLayout(null);
		this.add(title);
		this.add(usernamelbl);
		this.add(usernametxt);
		this.add(fullnamelbl);
		this.add(fullnametxt);
		this.add(departmentlbl);
		this.add(departmenttxt);
		this.add(passwordlbl);
		this.add(passwordtxt);
		this.add(passCheckBox);
		this.add(conpasswordlbl);
		this.add(conpasswordtxt);
		this.add(conpassCheckBox);
		this.add(backbtn);
		this.add(submitbtn);
		this.setVisible(true);
	}
	
	boolean checkIfUsernameHasBeenUsed(String username) {
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
			PreparedStatement stmt = Conn.prepareStatement("SELECT * FROM teacher_accounts where username=?");
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
	
	void showPinDialog(String username, String fullname, String password, String department) {
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
		
		JButton createbtn = new JButton("Create Account");
	    createbtn.setBounds(130, 130, 150, 40);
	    createbtn.setBackground(Color.RED);
	    createbtn.setForeground(Color.WHITE);
	    createbtn.addActionListener(e->{
	    	if(pintxt.getText().equals("0000")) {
				JOptionPane.showMessageDialog(null, "Invalid Pin","",JOptionPane.WARNING_MESSAGE);
			}else {
				int pin = Integer.parseInt(pintxt.getText());
				createAccount(username, fullname, password, pin, department);
			}
	    });
	    overlayPanel.add(createbtn);
        
        overlayDialog.setSize(400,200);
	    overlayDialog.setLocationRelativeTo(null);
	    overlayDialog.setVisible(true);
	}
	
	void createAccount(String username, String fullname, String password, int pin, String department) {
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
			PreparedStatement stmt;
			int num = 0;
			Random random = new Random();
			
			do {
				num= random.nextInt(900000) + 100000;
				
			}while(checkIfTeacherIDHasBeenUsed(num));
			
			String teacherID = String.valueOf(num);
				
			stmt = Conn.prepareStatement("INSERT INTO user_id_table (user_id) VALUES (?)");
			stmt.setString(1, teacherID);
			stmt.executeUpdate();
			
			stmt = Conn.prepareStatement("INSERT INTO teacher_accounts (teacher_ID, username, fullname, password, pin, department) VALUES(?,?,?,?,?,?)");
			stmt.setString(1, teacherID);
			stmt.setString(2, username);
			stmt.setString(3, fullname);
			stmt.setString(4, password);
			stmt.setInt(5, pin);
			stmt.setString(6, department);
			stmt.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Accout Successfully Created");
			new TeacherFrame(new Teacher(teacherID, username, password, fullname, department, pin));
			this.dispose();
			
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    }
	}
	
	boolean checkIfTeacherIDHasBeenUsed(int teacherID) {
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
			PreparedStatement stmt = Conn.prepareStatement("SELECT * FROM teacher_accounts where teacher_ID=?");
			stmt.setInt(1, teacherID);
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
			String conPassword = String.valueOf(conpasswordtxt.getPassword());
			
			if(usernametxt.getText().isBlank() || fullnametxt.getText().isBlank() || departmenttxt.getText().isBlank() || password.isBlank() || conPassword.isBlank() ) {
				JOptionPane.showMessageDialog(null, "Fill the Blanks","",JOptionPane.WARNING_MESSAGE);
				
			}else if(usernametxt.getText().length() > 20) {
				JOptionPane.showMessageDialog(null, "Username is too long","",JOptionPane.WARNING_MESSAGE);
				
			}else if(usernametxt.getText().length() < 8) {
				JOptionPane.showMessageDialog(null, "Username is too short","",JOptionPane.WARNING_MESSAGE);
				
			}else if(password.length() < 8) {
				JOptionPane.showMessageDialog(null, "Password is too short","",JOptionPane.WARNING_MESSAGE);
				
			}else if(fullnametxt.getText().length() > 30) {
				JOptionPane.showMessageDialog(null, "Fullname is too long","",JOptionPane.WARNING_MESSAGE);
				
			}else if(departmenttxt.getText().length() > 10) {
				JOptionPane.showMessageDialog(null, "Department is too long","",JOptionPane.WARNING_MESSAGE);
				
			}else if(checkIfUsernameHasBeenUsed(usernametxt.getText())) {
				JOptionPane.showMessageDialog(null, "Username has been used","",JOptionPane.WARNING_MESSAGE);
				
			}else if(!password.equals(conPassword)) {
				JOptionPane.showMessageDialog(null, "Password doesn't match","",JOptionPane.WARNING_MESSAGE);
				
			}else {
				showPinDialog(usernametxt.getText(), fullnametxt.getText(), password, departmenttxt.getText());
			}
		}
		
		if(e.getSource() == backbtn) {
			this.dispose();
			new TeacherLoginPage();
		}
		
	}

}
