package Main;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import LoginPage.AdminLoginPage;
import LoginPage.LibrarianLoginPage;
import LoginPage.StudentLoginPage;
import LoginPage.TeacherLoginPage;

public class HomePage extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JPanel backgroundPanel;
	JLabel titlelbl;
	private JButton studentbtn, librarianbtn, teacherbtn;
	
	public HomePage(){
		
		backgroundPanel = new JPanel() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				setOpaque(false);
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setPaint(new GradientPaint(0, 0,new Color(102,0,0),getWidth(), getHeight(), new Color(255,0,0)));
				g2.fillRect(0, 0, getWidth(), getHeight());
				super.paintComponent(g);
			}
		};
		backgroundPanel.setBackground(Color.RED);
		backgroundPanel.setLayout(null);
		
		titlelbl = new JLabel("<html>Library Management System</html>");
		titlelbl.setFont(new Font("Arial",Font.BOLD,45));
		titlelbl.setForeground(Color.WHITE);
		titlelbl.setBounds(30,50,350,170);
		backgroundPanel.add(titlelbl);
		
		JLabel logo = new JLabel();
		logo.setBounds(100, 270, 180, 180);
		ImageIcon logoIcon = new ImageIcon("res/tcu_logo.jpg");
		Image logoImg = logoIcon.getImage().getScaledInstance(logo.getWidth(), logo.getHeight(), java.awt.Image.SCALE_AREA_AVERAGING);
		logoIcon = new ImageIcon(logoImg);
		logo.setIcon(logoIcon);
		backgroundPanel.add(logo);
		
		JLabel adminlbl = new JLabel("<html><u>Login As Admin</u></html>");
		adminlbl.setBounds(130, 470, 150, 30);
		adminlbl.setForeground(Color.WHITE);
		adminlbl.setFont(new Font("Arial",Font.BOLD,15));
		adminlbl.setFocusable(false);
		adminlbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		adminlbl.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				try {
					int pin = Integer.parseInt(JOptionPane.showInputDialog("Enter Pin"));
					if(pin==55555555) {
						new AdminLoginPage();
						dispose();
					}else {
						JOptionPane.showMessageDialog(null, "Invalid Pin", "", JOptionPane.ERROR_MESSAGE);
					}
				}catch(NumberFormatException e1) {
					
				}
				
			}
			
		});
		backgroundPanel.add(adminlbl);
		
		
		JPanel panel = new JPanel();
		panel.setBounds(370, 50, 370, 450);
		panel.setBackground(new Color(0,0,0,100));
		panel.setLayout(new FlowLayout(FlowLayout.CENTER,100,70));
		backgroundPanel.add(panel);
		
		studentbtn = new JButton("Login as Student");
		studentbtn.setPreferredSize(new Dimension(150,40));
		studentbtn.setBorder(null);
		studentbtn.setBackground(Color.RED);
		studentbtn.setForeground(Color.WHITE);
		studentbtn.setFont(new Font("Arial",Font.BOLD,15));
		studentbtn.setFocusable(false);
		studentbtn.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		studentbtn.addActionListener(this);
		panel.add(studentbtn);
		
		
		librarianbtn = new JButton("Login as Librarian");
		librarianbtn.setPreferredSize(new Dimension(150,40));
		librarianbtn.setBorder(null);
		librarianbtn.setBackground(Color.RED);
		librarianbtn.setForeground(Color.WHITE);
		librarianbtn.setFont(new Font("Arial",Font.BOLD,15));
		librarianbtn.setFocusable(false);
		librarianbtn.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		librarianbtn.addActionListener(this);
		panel.add(librarianbtn);
		
		teacherbtn = new JButton("Login as Teacher");
		teacherbtn.setPreferredSize(new Dimension(150,40));
		teacherbtn.setBorder(null);
		teacherbtn.setBackground(Color.RED);
		teacherbtn.setForeground(Color.WHITE);
		teacherbtn.setFont(new Font("Arial",Font.BOLD,15));
		teacherbtn.setFocusable(false);
		teacherbtn.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		teacherbtn.addActionListener(this);
		panel.add(teacherbtn);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(800,600);
		this.setLocationRelativeTo(null);
		this.setBackground(Color.WHITE);
		this.setResizable(false);
		this.add(backgroundPanel);
		//this.setUndecorated(true);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == studentbtn) {
			new StudentLoginPage();
			this.dispose();
		}
		
		if(e.getSource() == librarianbtn) {
			new LibrarianLoginPage();
			this.dispose();
		}
		
		if(e.getSource() == teacherbtn) {
			new TeacherLoginPage();
			this.dispose();
		}
		
	}
}
