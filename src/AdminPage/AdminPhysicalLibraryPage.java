package AdminPage;

import java.awt.Image;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import Objects.AbstractPhysicalLibrary;

public class AdminPhysicalLibraryPage extends AbstractPhysicalLibrary{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AdminPhysicalLibraryPage(JFrame frame) {
		super(frame);
		
	}

	@Override
	protected void hideBook() {
		imagelbl.setVisible(false);
		titlelbl.setVisible(false);
		authorlbl.setVisible(false);
		categorylbl.setVisible(false);
		deletebtn.setVisible(false);
		editbtn.setVisible(false);
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
				
				 ImageIcon icon = new ImageIcon(bookImg);
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
				
			}
			 rs.close();
			 pstmt.close();
			 Conn.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}

}
