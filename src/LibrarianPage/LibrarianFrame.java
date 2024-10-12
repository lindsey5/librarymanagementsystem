package LibrarianPage;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import LoginPage.LibrarianLoginPage;
import Objects.CustomButton;
import Objects.DigitalLibraryPage;

public class LibrarianFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JPanel leftPanel;
	private JPanel cardPanel;
	private CardLayout cardLayout;
	private LibrarianPhysicalLibraryPage phy_library;
	private DigitalLibraryPage dig_library;
	private DashboardPanel dashboard;
	
	public LibrarianFrame() {
		cardLayout = new CardLayout();
		cardPanel = new JPanel();
		cardPanel.setLayout(cardLayout);
		
		phy_library = new LibrarianPhysicalLibraryPage(this);
		dig_library = new DigitalLibraryPage();
		dashboard = new DashboardPanel();
		
		this.setExtendedState(MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.getContentPane().setBackground(Color.WHITE);
		this.setLayout(new BorderLayout());
		//this.setUndecorated(true);
		this.setResizable(false);
		makeLeftPanel();
		this.add(cardPanel);
		this.setVisible(true);
		
		cardPanel.add(phy_library, "physical");
		cardPanel.add(dig_library, "digital");
		cardPanel.add(dashboard, "dashboardpanel");
		
	}
	
	public void showPanel(String num) {
		cardLayout.show(cardPanel, num);
	}
	
	private void makeLeftPanel() {
		leftPanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension(250,0));
		leftPanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
		leftPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));
		
		JPanel titlePanel = new JPanel() {
			
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
		titlePanel.setPreferredSize(new Dimension(250,70));
		titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
		
		leftPanel.add(titlePanel);
		
		JLabel titlelbl = new JLabel("Librarian");
		titlelbl.setForeground(Color.WHITE);
		titlelbl.setFont(new Font("Arial",Font.BOLD,25));
		titlePanel.add(titlelbl);
		
		try {
			BufferedImage bookImg  =  ImageIO.read(new File("res/book.png"));
			CustomButton phy_librarybtn = new CustomButton("Physical Library",bookImg);
			phy_librarybtn.setBackground(new Color(237,237,237));
			phy_librarybtn.setBorder(null);
			phy_librarybtn.setPreferredSize(new Dimension(249,50));
			phy_librarybtn.addActionListener(e->{ 
				showPanel("physical");
				phy_library.setPhysicalBooksToTable("All");
			});
			leftPanel.add(phy_librarybtn);
			
			CustomButton dig_librarybtn = new CustomButton("Digital Library",bookImg);
			dig_librarybtn.setBackground(new Color(237,237,237));
			dig_librarybtn.setBorder(null);
			dig_librarybtn.setPreferredSize(new Dimension(249,50));
			dig_librarybtn.addActionListener(e->{ 
				showPanel("digital");
				dig_library.showBooks("All");
				});
			leftPanel.add(dig_librarybtn);
			
			BufferedImage dashboardImg  =  ImageIO.read(new File("res/dashboard.png"));
			CustomButton dashboardbtn = new CustomButton("Dashboard",dashboardImg);
			dashboardbtn.setBackground(new Color(237,237,237));
			dashboardbtn.setBorder(null);
			dashboardbtn.setPreferredSize(new Dimension(249,50));
			dashboardbtn.addActionListener(e-> {
				showPanel("dashboardpanel");
				dashboard.refresh();
				});
			leftPanel.add(dashboardbtn);
			
			BufferedImage logoutImg  =  ImageIO.read(new File("res/logout.png"));
			CustomButton logoutbtn = new CustomButton("Log out",logoutImg);
			logoutbtn.setBackground(new Color(237,237,237));
			logoutbtn.setBorder(null);
			logoutbtn.setPreferredSize(new Dimension(249,50));
			logoutbtn.addActionListener(e-> {
				new LibrarianLoginPage();
				this.dispose();
			});
			leftPanel.add(logoutbtn);
			
			
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		this.add(leftPanel, BorderLayout.WEST);
	}

}
