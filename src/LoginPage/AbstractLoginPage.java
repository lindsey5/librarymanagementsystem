package LoginPage;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import Main.HomePage;
import Objects.ItemEventHandler;
import Objects.RoundRectPasswordField;
import Objects.RoundRectTextField;

abstract public class AbstractLoginPage extends JFrame implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel titlelbl;
	private JLabel usernamelbl, passwordlbl;
	private RoundRectTextField usernametxt;
	private RoundRectPasswordField passwordtxt;
	protected JButton loginbtn, backbtn, signupbtn, forgotPassbtn;
	private JCheckBox passCheckBox;

	public AbstractLoginPage(String title){
		
		titlelbl = new JLabel(title);
		titlelbl.setForeground(Color.BLACK);
		titlelbl.setFont(new Font("Arial",Font.BOLD,30));
		titlelbl.setBounds(50,30,250,50);
		
		usernamelbl = new JLabel("Username");
		usernamelbl.setForeground(Color.GRAY);
		usernamelbl.setFont(new Font("Arial",Font.BOLD,15));
		usernamelbl.setBounds(50,130,100,30);
		
		usernametxt = new RoundRectTextField();
		usernametxt.setBounds(50, 170, 300, 40);
		
		passwordlbl = new JLabel("Password");
		passwordlbl.setForeground(Color.GRAY);
		passwordlbl.setFont(new Font("Arial",Font.BOLD,15));
		passwordlbl.setBounds(50,230,100,30);
		
		passwordtxt = new RoundRectPasswordField();
		passwordtxt.setBounds(50,270,300,40);
		
		passCheckBox = new JCheckBox("Show");
		passCheckBox.setBounds(300, 310, 100, 30);
		passCheckBox.setBackground(Color.WHITE);
		passCheckBox.addItemListener(new ItemEventHandler(passCheckBox, passwordtxt));
		passCheckBox.setFocusable(false);
		
		loginbtn = new JButton("Login");
		loginbtn.setBackground(Color.RED);
		loginbtn.setForeground(Color.WHITE);
		loginbtn.setBounds(210,350,140,40);
		loginbtn.addActionListener(this);
		
		backbtn = new JButton("Back");
		backbtn.setBackground(Color.LIGHT_GRAY);
		backbtn.setForeground(Color.BLACK);
		backbtn.setBounds(50,350,140,40);
		backbtn.addActionListener(this);
		
		signupbtn = new JButton("<html><u>Signup</u></html>");
		signupbtn.setBackground(Color.WHITE);
		signupbtn.setForeground(Color.red);
		signupbtn.setBorder(null);
		signupbtn.setBounds(50,450,140,40);
		signupbtn.setFont(new Font("Arial",Font.PLAIN,15));
		signupbtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		signupbtn.addActionListener(this);
		
		forgotPassbtn = new JButton("<html><u>Forgot Password</u></html>");
		forgotPassbtn.setBackground(Color.WHITE);
		forgotPassbtn.setForeground(Color.red);
		forgotPassbtn.setBorder(null);
		forgotPassbtn.setBounds(220,450,140,40);
		forgotPassbtn.setFont(new Font("Arial",Font.PLAIN,15));
		forgotPassbtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		forgotPassbtn.addActionListener(this);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(420,550);
		this.setLocationRelativeTo(null);
		this.getContentPane().setBackground(Color.WHITE);
		this.setResizable(false);
		this.setLayout(null);
		this.getRootPane().setDefaultButton(loginbtn);
		//this.setUndecorated(true);
		this.add(titlelbl);
		this.add(usernamelbl);
		this.add(usernametxt);
		this.add(passwordlbl);
		this.add(passwordtxt);
		this.add(loginbtn);
		this.add(backbtn);
		this.add(signupbtn);
		this.add(forgotPassbtn);
		this.add(passCheckBox);
		this.setVisible(true);
		
	}
	
	abstract void login(String username, String password);
	abstract void signup();

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == loginbtn) {
			String password = String.valueOf(passwordtxt.getPassword());
			login(usernametxt.getText(), password);
		}
		
		if(e.getSource() == backbtn) {
			this.dispose();
			new HomePage();
		}
		
		if(e.getSource() == signupbtn) {
			signup();
		}
		
	}
	
}
