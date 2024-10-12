package LoginPage;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import Objects.ItemEventHandler;
import SignUpPage.TeacherSignUpPage;
import UserPage.Teacher;
import UserPage.TeacherFrame;

public class TeacherLoginPage extends AbstractLoginPage implements ForgotPasswordInterface{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TeacherLoginPage(){
		super("Teacher Login");
	}     

	@Override
	void login(String username, String password) {
		
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
	    	PreparedStatement stmt = Conn.prepareStatement("SELECT * FROM teacher_accounts WHERE username=? and password=?");
	    	stmt.setString(1, username);
	    	stmt.setString(2, password);
	    	
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				if(username.equals(rs.getString("username")) && password.equals(rs.getString("password"))) {
					JOptionPane.showMessageDialog(null, "Login Successful");
					Teacher teacher = getTeacher(rs);
					new TeacherFrame(teacher);
					this.dispose();
				}else {
					JOptionPane.showMessageDialog(null, "Login Denied","",JOptionPane.ERROR_MESSAGE);
				}
				
			}else {
				JOptionPane.showMessageDialog(null, "Login Denied","",JOptionPane.ERROR_MESSAGE);
			}

	    }catch(SQLException e) {
	    	e.printStackTrace();
	    }	
		
	}
	
	private Teacher getTeacher(ResultSet rs) throws SQLException {
	    String teacherID = rs.getString("teacher_id");
	    String username = rs.getString("username");
	    String password = rs.getString("password");
	    String fullname = rs.getString("fullname");
	    String department = rs.getString("department");
	    int pin = rs.getInt("pin");
	    return new Teacher(teacherID, username, password, fullname, department, pin);
	}

	@Override
	void signup() {
		new TeacherSignUpPage();
		this.dispose();
	}

	@Override
	public void openForgotPassDialog(String id) {
		JDialog overlayDialog  = new JDialog(this, "Overlay", true) ;
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
        
        JLabel newPasswordlbl = new JLabel("New Password");
        newPasswordlbl.setBounds(50, 80, 200, 30);
        newPasswordlbl.setFont(new Font("Arial",Font.BOLD,15));
        overlayPanel.add(newPasswordlbl);
        
        JPasswordField newPasswordtxt = new JPasswordField();
        newPasswordtxt.setBounds(50, 110, 200, 30);
        overlayPanel.add(newPasswordtxt);
        
        JCheckBox newPassCheck = new JCheckBox("Show");
        newPassCheck.setBounds(260, 110, 70, 30);
        newPassCheck.setBackground(Color.WHITE);
        newPassCheck.setFocusable(false);
        newPassCheck.addItemListener(new ItemEventHandler(newPassCheck, newPasswordtxt));
        overlayPanel.add(newPassCheck);
        
        JLabel conNewPasswordlbl = new JLabel("Confirm New Password");
        conNewPasswordlbl.setBounds(50, 180, 250, 30);
        conNewPasswordlbl.setFont(new Font("Arial",Font.BOLD,15));
        overlayPanel.add(conNewPasswordlbl);
        
        JPasswordField conNewPasswordtxt = new JPasswordField();
        conNewPasswordtxt.setBounds(50, 210, 200, 30);
        overlayPanel.add(conNewPasswordtxt);
        
        JCheckBox conNewPassCheck = new JCheckBox("Show");
        conNewPassCheck.setBounds(260, 210, 70, 30);
        conNewPassCheck.setBackground(Color.WHITE);
        conNewPassCheck.setFocusable(false);
        conNewPassCheck.addItemListener(new ItemEventHandler(conNewPassCheck, conNewPasswordtxt));
        overlayPanel.add(conNewPassCheck);
        
        JButton submitbtn = new JButton("Submit");
	    submitbtn.setBounds(100, 300, 150, 30);
	    submitbtn.setBackground(Color.RED);
	    submitbtn.setForeground(Color.WHITE);
	    submitbtn.addActionListener(e->{
	    	String newPassword = String.valueOf(newPasswordtxt.getPassword());
			String conNewPass = String.valueOf(conNewPasswordtxt.getPassword());
			
			if(newPassword.isBlank() || conNewPass.isBlank()) {
				JOptionPane.showMessageDialog(null, "Fill The Blanks","",JOptionPane.ERROR_MESSAGE);
				
			}else if(newPassword.length() > 20) {
				JOptionPane.showMessageDialog(null, "Password is too long","",JOptionPane.ERROR_MESSAGE);
				
			}else if(newPassword.length() < 8) {
				JOptionPane.showMessageDialog(null, "Password is too short","",JOptionPane.ERROR_MESSAGE);
				
			}else if(!newPassword.equals(conNewPass)) {
				JOptionPane.showMessageDialog(null, "Password doesn't match","",JOptionPane.ERROR_MESSAGE);
			}else {
				changePassword(id, newPassword);
				overlayDialog.dispose();
			}
	    	
	    });
	    overlayPanel.add(submitbtn);
          
        
        overlayDialog.setSize(350,400);
	    overlayDialog.setLocationRelativeTo(null);
	    overlayDialog.getRootPane().setDefaultButton(submitbtn);
	    overlayDialog.setVisible(true);
		
	}

	@Override
	public void openEnterIDDialog() {
		JDialog overlayDialog  = new JDialog(this, "Overlay", true) ;
        overlayDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        overlayDialog.setUndecorated(true);
   	
        JPanel overlayPanel = new JPanel(null);
        overlayPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        overlayPanel.setBackground(Color.WHITE);
        overlayDialog.add(overlayPanel);
        
        JButton closebtn = new JButton("X");
        closebtn.addActionListener(e -> overlayDialog.dispose());
        closebtn.setFont(new Font("Arial",Font.BOLD,17));
        closebtn.setBounds(240, 20, 50, 30);
        closebtn.setBackground(Color.WHITE);
        closebtn.setForeground(Color.BLACK);
        closebtn.setFocusable(false);
        closebtn.setBorder(null);
        overlayPanel.add(closebtn);
        
        JLabel studentIDlbl = new JLabel("Enter Student ID");
        studentIDlbl.setBounds(50, 70, 150, 30);
        studentIDlbl.setFont(new Font("Arial",Font.BOLD,15));
        overlayPanel.add(studentIDlbl);
        
        JTextField studentIDtxt = new JTextField();
        studentIDtxt.setBounds(50, 100, 200, 30);
        overlayPanel.add(studentIDtxt);
        
        JButton submitbtn = new JButton("Submit");
        submitbtn.setBounds(50, 170, 200, 30);
        submitbtn.setFont(new Font("Arial",Font.BOLD,15));
        submitbtn.setBackground(Color.RED);
        submitbtn.setForeground(Color.WHITE);
        submitbtn.addActionListener(e->{
        	if(isIDExist(studentIDtxt.getText())) {
        		overlayDialog.dispose();
        		openEnterPinDialog(studentIDtxt.getText());
        	}else {
        		JOptionPane.showMessageDialog(null, "Student ID doesn't exist","",JOptionPane.ERROR_MESSAGE);
        	}
        });
        overlayPanel.add(submitbtn);
        
        overlayDialog.setSize(300,250);
        overlayDialog.getRootPane().setDefaultButton(submitbtn);
	    overlayDialog.setLocationRelativeTo(null);
	    overlayDialog.setVisible(true);
		
	}

	@Override
	public void openEnterPinDialog(String id) {
		JDialog overlayDialog  = new JDialog(this, "Overlay", true) ;
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
	    submitbtn.requestFocus();
	    submitbtn.addActionListener(e->{
		    if(isPinCorrect(id, Integer.parseInt(pintxt.getText()))) {
		    	overlayDialog.dispose();
		    	openForgotPassDialog(id);
		    }else {
		    	JOptionPane.showMessageDialog(null, "Incorrect Pin","",JOptionPane.ERROR_MESSAGE);
		    }
	    	
	    });
	    overlayPanel.add(submitbtn);
        
	    overlayDialog.getRootPane().setDefaultButton(submitbtn);
        overlayDialog.setSize(400,200);
	    overlayDialog.setLocationRelativeTo(null);
	    overlayDialog.setVisible(true);
		
	}

	@Override
	public boolean isIDExist(String id) {
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
	    	PreparedStatement stmt = Conn.prepareStatement("SELECT * FROM teacher_accounts WHERE teacher_id=?");
	    	stmt.setString(1, id);
	    	
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
	public boolean isPinCorrect(String id, int pin) {
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
	    	PreparedStatement stmt = Conn.prepareStatement("SELECT * FROM teacher_accounts WHERE teacher_id=? and pin=?");
	    	stmt.setString(1, id);
	    	stmt.setInt(2, pin);
	    	
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
	public void changePassword(String id, String newPassword) {
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
			PreparedStatement stmt = Conn.prepareStatement("UPDATE teacher_accounts SET password=? WHERE teacher_id=?");
			stmt.setString(1, newPassword);
			stmt.setString(2, id);
			stmt.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Update Successful");
			
	    }catch(SQLException e1) {
	    	e1.printStackTrace();
	    }
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		if(e.getSource() == this.forgotPassbtn){
			openEnterIDDialog();
		}
		
	}

}
