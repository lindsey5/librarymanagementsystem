package UserPage;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import Objects.SearchField;
import Objects.CustomScrollBarUI;

public class UserPhysicalLibraryPage extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JPanel booksPanel;
	int booksPanelHeight;
	SearchField searchField;
	JButton searchbtn;
	
	public UserPhysicalLibraryPage(){
		searchbtn = new JButton("Search");
		searchbtn.setBounds(390, 50, 100, 30);
		searchbtn.setBackground(Color.WHITE);
		searchbtn.setForeground(Color.RED);
		searchbtn.setBorder(BorderFactory.createLineBorder(Color.RED));
		searchbtn.setFont(new Font("Arial",Font.BOLD,15));
		this.add(searchbtn);
		
		searchField = new SearchField("Search Book");
		searchField.setBounds(70,50,300,30);
		this.add(searchField);
		
		booksPanel = new JPanel();
		booksPanel.setBackground(Color.WHITE);
		booksPanel.setLayout(new FlowLayout(FlowLayout.LEFT,90,30));
		
		JScrollPane booksScrollPane = new JScrollPane();
		booksScrollPane.setBounds(70,150,1100,700);
		//booksScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		booksScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		booksScrollPane.getVerticalScrollBar().setUnitIncrement(10);
		booksScrollPane.getVerticalScrollBar().setBlockIncrement(100);
		booksScrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI(Color.GRAY, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.LIGHT_GRAY));
		booksScrollPane.setBorder(null);
		booksScrollPane.setViewportView(booksPanel);
		this.add(booksScrollPane);
		
		this.setLayout(null);
		
		showBooks("All");
		
		searchbtn.addActionListener(e->{
			 searchResults(searchField.getText());
			 searchField.setText("Search Book");
			 searchField.setForeground(Color.LIGHT_GRAY);
		});
	}
	
	
	void searchResults(String searchStr) {
		booksPanel.removeAll();
		booksPanel.revalidate();
		booksPanel.repaint();
		
		booksPanelHeight = 400;
		booksPanel.setPreferredSize(new Dimension(1020, booksPanelHeight));
		
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
			PreparedStatement stmt = Conn.prepareStatement("SELECT * FROM physical_books WHERE category LIKE ? OR title LIKE ? OR author LIKE ?");
			stmt.setString(1, "%" + searchStr + "%");
			stmt.setString(2, "%" + searchStr + "%");
			stmt.setString(3, "%" + searchStr + "%");
			int i = 1;
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				if(i == 1) {
					JLabel label = new JLabel("Result for \""+searchStr+"\"");
					label.setPreferredSize(new Dimension(1000,30));
					label.setFont(new Font("Arial",Font.BOLD,25));
					booksPanel.add(label);
					i+=1;
				}
				
				String book_title = rs.getString("title");
				String author = rs.getString("author");
				String available = rs.getString("available");
				
				JPanel panel = new JPanel();
				panel.setPreferredSize(new Dimension(250,360));
				//panel.setBackground(Color.WHITE);
				panel.setLayout(null);
				booksPanel.add(panel);
				
				JLabel bookImagelbl = new JLabel();
				bookImagelbl.setBounds(40,30, 170, 180);
				panel.add(bookImagelbl);
				
				byte[] bookImg = rs.getBytes("image");
				ImageIcon imageIcon  = new ImageIcon(bookImg);
				Image bookImage = imageIcon.getImage().getScaledInstance(bookImagelbl.getWidth(), bookImagelbl.getHeight(), java.awt.Image.SCALE_AREA_AVERAGING);
				imageIcon = new ImageIcon(bookImage);
				bookImagelbl.setIcon(imageIcon);
				
				JLabel book_titlelbl = new JLabel(book_title);
				book_titlelbl.setBounds(0, 220, 250, 30);
				book_titlelbl.setHorizontalAlignment(SwingConstants.CENTER);
				book_titlelbl.setFont(new Font("Arial",Font.BOLD,15));
				panel.add(book_titlelbl);
				
				JLabel authorlbl = new JLabel("By: " + author);
				authorlbl.setBounds(0, 270, 250, 30);
				authorlbl.setHorizontalAlignment(SwingConstants.CENTER);
				authorlbl.setFont(new Font("Arial",Font.BOLD,15));
				panel.add(authorlbl);
				
				JLabel availablelbl = new JLabel("Available: " + available);
				availablelbl.setBounds(0, 320, 250, 30);
				availablelbl.setHorizontalAlignment(SwingConstants.CENTER);
				availablelbl.setFont(new Font("Arial",Font.BOLD,15));
				panel.add(availablelbl);
			}
		}catch(SQLException e) {
	    	e.printStackTrace();
	    }
		
		int numColumns = 3;
		int panelHeight = 400;
		booksPanelHeight = (booksPanel.getComponentCount() / numColumns + 1) * panelHeight + 130;
		booksPanel.setPreferredSize(new Dimension(booksPanel.getWidth(), booksPanelHeight));
	}
	
	public void showBooks(String category) {
		booksPanel.removeAll();
		booksPanel.revalidate();
		booksPanel.repaint();
		
		booksPanelHeight = 400;
		booksPanel.setPreferredSize(new Dimension(1020, booksPanelHeight));
		
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
	    	PreparedStatement stmt = null;
	    	if(category.equals("All")) {
	    		stmt = Conn.prepareStatement("SELECT * FROM physical_books");
	    	}else {
	    		stmt = Conn.prepareStatement("SELECT * FROM physical_books WHERE category=?");
	    		stmt.setString(1, category);
	    	}
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				String book_title = rs.getString("title");
				String author = rs.getString("author");
				String available = rs.getString("available");
				
				JPanel panel = new JPanel();
				panel.setPreferredSize(new Dimension(250,360));
				//panel.setBackground(Color.WHITE);
				panel.setLayout(null);
				booksPanel.add(panel);
				
				JLabel bookImagelbl = new JLabel();
				bookImagelbl.setBounds(40,30, 170, 180);
				panel.add(bookImagelbl);
				
				byte[] bookImg = rs.getBytes("image");
				ImageIcon imageIcon  = new ImageIcon(bookImg);
				Image bookImage = imageIcon.getImage().getScaledInstance(bookImagelbl.getWidth(), bookImagelbl.getHeight(), java.awt.Image.SCALE_AREA_AVERAGING);
				imageIcon = new ImageIcon(bookImage);
				bookImagelbl.setIcon(imageIcon);
				
				JLabel book_titlelbl = new JLabel(book_title);
				book_titlelbl.setBounds(0, 220, 250, 30);
				book_titlelbl.setHorizontalAlignment(SwingConstants.CENTER);
				book_titlelbl.setFont(new Font("Arial",Font.BOLD,15));
				panel.add(book_titlelbl);
				
				JLabel authorlbl = new JLabel("By: " + author);
				authorlbl.setBounds(0, 270, 250, 30);
				authorlbl.setHorizontalAlignment(SwingConstants.CENTER);
				authorlbl.setFont(new Font("Arial",Font.BOLD,15));
				panel.add(authorlbl);
				
				JLabel availablelbl = new JLabel("Available: " + available);
				availablelbl.setBounds(0, 320, 250, 30);
				availablelbl.setHorizontalAlignment(SwingConstants.CENTER);
				availablelbl.setFont(new Font("Arial",Font.BOLD,15));
				panel.add(availablelbl);
			}
			
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    }
		
		int numColumns = 3;
		int panelHeight = 400;
		booksPanelHeight = (booksPanel.getComponentCount() / numColumns + 1) * panelHeight + 90;
		booksPanel.setPreferredSize(new Dimension(booksPanel.getWidth(), booksPanelHeight));
	}
	
	void checkIfOverDue(String borrowed_by) {
		
		try {
		    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb", "root", "");
		    PreparedStatement stmt = conn.prepareStatement("SELECT * FROM history JOIN borrowed_phy_books ON borrowed_phy_books.borrowed_id = history.borrowed_id "
		    		+ "WHERE borrowed_by=? AND date_returned IS NULL");
		    stmt.setString(1, borrowed_by);
		    ResultSet rs = stmt.executeQuery();

		    while(rs.next()) {
		        Timestamp borrowedTimestamp = rs.getTimestamp("history.date_borrowed");
		        LocalDateTime borrowedDate = borrowedTimestamp.toLocalDateTime();

		        int hoursAllowed = rs.getInt("hours");

		        LocalDateTime expectedReturnTime = borrowedDate.plusHours(hoursAllowed);
		        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

		        String formattedDate = expectedReturnTime.format(dateFormatter);
		        String formattedTime = expectedReturnTime.format(timeFormatter);

		        LocalDateTime currentTimestamp = LocalDateTime.now();

		        long borrowedDuration = ChronoUnit.HOURS.between(borrowedDate, currentTimestamp);

		        if (borrowedDuration > hoursAllowed) {
		            String warningMessage = String.format(
		                    "Warning: The book with ID #%d is overdue. It was due on Date: %s and Time: %s. It is now %d hour/s overdue",
		                    rs.getInt("book_id"),
		                    formattedDate,
		                    formattedTime,
		                    borrowedDuration - hoursAllowed
		            );

		            // Display the warning message using JOptionPane
		            JOptionPane.showMessageDialog(null, warningMessage, "Book Overdue Warning", JOptionPane.WARNING_MESSAGE);
		        }
		    }

		} catch (SQLException e) {
		    e.printStackTrace();
		}

	}
	

}
