package LibrarianPage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.JTextField;


import Objects.AbstractPhysicalLibrary;

public class LibrarianPhysicalLibraryPage extends AbstractPhysicalLibrary {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JButton borrowbtn;


	public LibrarianPhysicalLibraryPage(JFrame frame) {
		super(frame);
		
		borrowbtn = new JButton("Borrow");
		borrowbtn.setBounds(900, 600, 200, 30);
		borrowbtn.setBackground(Color.RED);
		borrowbtn.setForeground(Color.WHITE);
		borrowbtn.setVisible(false);
		borrowbtn.addActionListener(e->{
			showBorrowDialog();
		});
		this.add(borrowbtn);
	}
	
	void showBorrowDialog() {
		JDialog overlayDialog = new JDialog(frame, "Overlay", true);
        overlayDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        overlayDialog.setUndecorated(true);
   	
        JPanel overlayPanel = new JPanel(null);
        overlayPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        overlayPanel.setBackground(Color.WHITE);
        overlayDialog.add(overlayPanel);
        
        JButton closebtn = new JButton("X");
        closebtn.addActionListener(e -> overlayDialog.dispose());
        closebtn.setFont(new Font("Arial",Font.BOLD,17));
        closebtn.setBounds(230, 20, 50, 30);
        closebtn.setBackground(Color.WHITE);
        closebtn.setForeground(Color.BLACK);
        closebtn.setFocusable(false);
        closebtn.setBorder(null);
        overlayPanel.add(closebtn);
        
        JLabel idlbl = new JLabel("Enter User ID");
        idlbl.setBounds(50, 70, 150, 30);
        idlbl.setFont(new Font("Arial",Font.BOLD,15));
        idlbl.setForeground(Color.LIGHT_GRAY);
        overlayPanel.add(idlbl);
        
        JTextField idtxt = new JTextField();
        idtxt.setBounds(50, 100, 200, 30);
        overlayPanel.add(idtxt);
        
        JLabel hourslbl = new JLabel("How many Hours");
        hourslbl.setBounds(50,150,150,30);
        hourslbl.setFont(new Font("Arial",Font.BOLD,15));
        hourslbl.setForeground(Color.LIGHT_GRAY);
        overlayPanel.add(hourslbl);
        
        JTextField hourstxt = new JTextField();
        hourstxt.setBounds(50, 180, 200, 30);
        hourstxt.addKeyListener(new KeyAdapter() {
        	@Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume(); // Stop the event from being processed
                }
            }
        });
        overlayPanel.add(hourstxt);
        
        JLabel quantitylbl = new JLabel("Quantity");
        quantitylbl.setBounds(50,230,150,30);
        quantitylbl.setFont(new Font("Arial",Font.BOLD,15));
        quantitylbl.setForeground(Color.LIGHT_GRAY);
        overlayPanel.add(quantitylbl);
        
        JTextField quantitytxt = new JTextField();
        quantitytxt.setBounds(50, 260, 200, 30);
        quantitytxt.addKeyListener(new KeyAdapter() {
        	@Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume(); // Stop the event from being processed
                }
            }
        });
        overlayPanel.add(quantitytxt);
        
        JComboBox<String> borrower = new JComboBox<>();
        borrower.setBounds(50, 320, 200, 30);
        borrower.setBackground(Color.WHITE);
        borrower.addItem("Student");
        borrower.addItem("Teacher");
        overlayPanel.add(borrower);
        
        JButton borrowbtn = new JButton("Borrow Book");
        borrowbtn.setBounds(50, 400, 200, 30);
        borrowbtn.setBackground(Color.RED);
        borrowbtn.setForeground(Color.WHITE);
        borrowbtn.addActionListener(e->{
        	if(!checkIfIdExist(idtxt.getText())) {
        		JOptionPane.showMessageDialog(null, "User ID doesn't Exist","",JOptionPane.ERROR_MESSAGE);
        		
        	}else if(Integer.parseInt(quantitytxt.getText()) > getAvailableBook(book_id) || Integer.parseInt(quantitytxt.getText()) > getBookStock(book_id) ) {
        		JOptionPane.showMessageDialog(null, "Borrow Invalid","",JOptionPane.ERROR_MESSAGE);
        		
        	}else {
        		int quantity = Integer.parseInt(quantitytxt.getText());
        		int hours = Integer.parseInt(hourstxt.getText());
        		
        		borrowBook(idtxt.getText(), book_id, (String)borrower.getSelectedItem(),quantity, hours);
        		updateAvailableBook(book_id, quantity);
        		hideBook();
        		setPhysicalBooksToTable("All");
        		overlayDialog.dispose();
        	}
        });
        overlayPanel.add(borrowbtn);
        
        overlayDialog.setSize(300,450);
	    overlayDialog.setLocationRelativeTo(null);
	    overlayDialog.setVisible(true);
	}
	
	int getAvailableBook(int bookID) {
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
	    	PreparedStatement stmt = Conn.prepareStatement("SELECT * FROM physical_books WHERE book_id=?");
	    	stmt.setInt(1, bookID);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				return rs.getInt("Available");
			}
			
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    }
		return 0;

	}
	
	int getBookStock(int bookID) {
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
	    	PreparedStatement stmt = Conn.prepareStatement("SELECT * FROM physical_books WHERE book_id=?");
	    	stmt.setInt(1, bookID);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				return rs.getInt("stock");
			}
			
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    }
		return 0;

	}
	
	boolean checkIfIdExist(String ID) {
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
	    	PreparedStatement stmt = Conn.prepareStatement("SELECT * FROM user_id_table WHERE user_id=?");
	    	stmt.setString(1, ID);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				return true;
			}else {
				return false;
			}
			
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    }
		
		return false;
	}
	
	void borrowBook(String ID, int bookID, String borrower, int quantity, int hours) {
		try {
		    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb", "root", "");
		    PreparedStatement stmt = conn.prepareStatement("INSERT INTO borrowed_phy_books (book_id, borrowed_by, borrower, quantity, status) VALUES(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
		    stmt.setInt(1, bookID);
		    stmt.setString(2, ID);
		    stmt.setString(3, borrower);
		    stmt.setInt(4, quantity);
		    stmt.setString(5, "Unreturned");
		    int affectedRows = stmt.executeUpdate();

		    if (affectedRows == 0) {
		        System.err.println("Insertion failed, no rows affected.");
		    } else {
		        ResultSet generatedKeys = stmt.getGeneratedKeys();
		        if (generatedKeys.next()) {
		            int generatedId = generatedKeys.getInt(1);
		            System.out.println("Generated ID: " + generatedId);
		            insertToHistory(hours, generatedId);
		        } else {
		            System.err.println("No generated keys returned!");
		        }
		    }
		    JOptionPane.showMessageDialog(null, "Borrow Successful");

		} catch (SQLException e) {
		    e.printStackTrace();
		}
	}
	
	
	void insertToHistory(int hours, int borrowed_id) {
		try {
			LocalDateTime currentDateTime = LocalDateTime.now();
		    Timestamp timestamp = Timestamp.valueOf(currentDateTime);
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
	    	PreparedStatement stmt = Conn.prepareStatement("INSERT INTO history (borrowed_id, hours, date_borrowed) VALUES(?,?,?)");
	    	stmt.setInt(1, borrowed_id);
	    	stmt.setInt(2, hours);
	    	stmt.setTimestamp(3, timestamp);
			stmt.executeUpdate();
			
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    }
	}
	
	
	void updateAvailableBook(int book_id, int quantity) {
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
	    	PreparedStatement stmt = Conn.prepareStatement("UPDATE physical_books SET available = available - ? WHERE book_id=?");
	    	stmt.setInt(1, quantity);
	    	stmt.setInt(2, book_id);
			stmt.executeUpdate();
			
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    }
		
	}

	@Override
	protected void hideBook() {
		 imagelbl.setVisible(false);
		 titlelbl.setVisible(false);
		 authorlbl.setVisible(false);
		 categorylbl.setVisible(false);
		 deletebtn.setVisible(false);
		 editbtn.setVisible(false);
		 borrowbtn.setVisible(false);
	}

	@Override
	protected void showBook(int bookID) {
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
			PreparedStatement pstmt;
			pstmt = Conn.prepareStatement("SELECT * FROM physical_books WHERE book_id=?");
			pstmt.setInt(1, bookID);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				byte[] bookImg = rs.getBytes("image");
				String bookTitle = rs.getString("title");
				String bookAuthor = rs.getString("author");
				String bookCategory =rs.getString("category");
				
				 ImageIcon icon = new ImageIcon(bookImg);;
				 Image img = icon.getImage().getScaledInstance(imagelbl.getWidth(), imagelbl.getHeight(), java.awt.Image.SCALE_AREA_AVERAGING);
				 icon = new ImageIcon(img);
				 imagelbl.setIcon(icon);
				 
				 titlelbl.setText("Title: "+bookTitle);
				 authorlbl.setText("Author: "+ bookAuthor);
				 categorylbl.setText("Category: " + bookCategory);
				 
				 imagelbl.setVisible(true);
				 titlelbl.setVisible(true);
				 authorlbl.setVisible(true);
				 categorylbl.setVisible(true);
				 deletebtn.setVisible(true);
				 editbtn.setVisible(true);
				 borrowbtn.setVisible(true);
				
			}
			 rs.close();
			 pstmt.close();
			 Conn.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}

}
