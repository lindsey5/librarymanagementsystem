package LoginPage;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import AdminPage.AdminFrame;
import Objects.ItemEventHandler;

public class AdminLoginPage extends AbstractLoginPage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AdminLoginPage() {
		super("Admin Login");
		signupbtn.setVisible(false);
	}

	@Override
	void login(String username, String password) {
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
	    	PreparedStatement stmt = Conn.prepareStatement("SELECT * FROM admin_accounts WHERE username=? and password=?");
	    	stmt.setString(1, username);
	    	stmt.setString(2, password);
	    	
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				if(username.equals(rs.getString("username")) && password.equals(rs.getString("password"))) {
					JOptionPane.showMessageDialog(null, "Login Successful");
					new AdminFrame();
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
	
	void openEnterUsernameDialog() {
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
        
        JLabel usernamelbl = new JLabel("Enter username");
        usernamelbl.setBounds(50, 70, 150, 30);
        usernamelbl.setFont(new Font("Arial",Font.BOLD,15));
        overlayPanel.add(usernamelbl);
        
        JTextField usernametxt = new JTextField();
        usernametxt.setBounds(50, 100, 200, 30);
        overlayPanel.add(usernametxt);
        
        JButton submitbtn = new JButton("Submit");
        submitbtn.setBounds(50, 170, 200, 30);
        submitbtn.setFont(new Font("Arial",Font.BOLD,15));
        submitbtn.setBackground(Color.RED);
        submitbtn.setForeground(Color.WHITE);
        submitbtn.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if(isUsernameExist(usernametxt.getText())) {
					try {
						int passCode = Integer.parseInt(JOptionPane.showInputDialog("Enter Passcode"));
						if(passCode == 44561231) {
							overlayDialog.dispose();
							openForgotPassDialog(usernametxt.getText());
						}
					
					}catch(NumberFormatException e1) {
						
					}
	        	
	        	}else {
	        		JOptionPane.showMessageDialog(null, "Username doesn't exist","",JOptionPane.ERROR_MESSAGE);
	        	}
			}
			
		});
        submitbtn.addActionListener(e->{
        	if(isUsernameExist(usernametxt.getText())) {
				try {
					int passCode = Integer.parseInt(JOptionPane.showInputDialog("Enter Passcode"));
					if(passCode == 44561231) {
						overlayDialog.dispose();
						openForgotPassDialog(usernametxt.getText());
					}
				
				}catch(NumberFormatException e1) {
					
				}
        	
        	}else {
        		JOptionPane.showMessageDialog(null, "Username doesn't exist","",JOptionPane.ERROR_MESSAGE);
        	}
        });
        overlayPanel.add(submitbtn);
        
        overlayDialog.setSize(300,250);
        overlayDialog.getRootPane().setDefaultButton(submitbtn);
	    overlayDialog.setLocationRelativeTo(null);
	    overlayDialog.setVisible(true);
		
	}
		
	boolean isUsernameExist(String username) {
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
	    	PreparedStatement stmt = Conn.prepareStatement("SELECT * FROM admin_accounts WHERE username=?");
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
	
	void openForgotPassDialog(String username) {
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
	    submitbtn.requestFocus();
	    submitbtn.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
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
					changePassword(username, newPassword);
				}
			}
			
		});
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
				changePassword(username, newPassword);
				overlayDialog.dispose();
			}
	    	
	    });
	    overlayPanel.add(submitbtn);
          
        
        overlayDialog.setSize(350,400);
	    overlayDialog.setLocationRelativeTo(null);
	    overlayDialog.setVisible(true);
		
	}
	
	void changePassword(String username, String newPassword) {
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
			PreparedStatement stmt = Conn.prepareStatement("UPDATE admin_accounts SET password=? WHERE username=?");
			stmt.setString(1, newPassword);
			stmt.setString(2, username);
			stmt.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Update Successful");
			
	    }catch(SQLException e1) {
	    	e1.printStackTrace();
	    }
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		
		if(e.getSource() == forgotPassbtn) {
			openEnterUsernameDialog();
		}
	
	}

	@Override
	void signup() {}


}
