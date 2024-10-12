package UserPage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import Objects.SearchField;

public class UserHistoryPage  extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String studentID;
	DefaultTableModel model;
	SearchField searchField;
	
	UserHistoryPage(String studentID){
		this.studentID = studentID;
		
		searchField = new SearchField("Search");
		searchField.setBounds(100,50,300,30);
		this.add(searchField);
		
		this.setLayout(null);
		makeTable();
		setInfoToTable(studentID);
		addDocumentListenerToStudentSearchField();
	}
	
	void makeTable() {
		model = new DefaultTableModel(); ;
		JTable table = new JTable(model);
		
		model.addColumn("Book ID");
		model.addColumn("Book Title");
		model.addColumn("Hours");
		model.addColumn("Quantity");
		model.addColumn("Date Borrowed");
		model.addColumn("Date Returned");
		
		table.getTableHeader().setBackground(Color.RED);
		table.getTableHeader().setForeground(Color.WHITE);
		/*table.setSelectionBackground(Color.RED);
		table.setSelectionForeground(Color.WHITE);*/
		table.getTableHeader().setFont(new Font("Callibri",Font.BOLD,17));
		table.getTableHeader().setEnabled(false);
		table.getTableHeader().setPreferredSize(new Dimension(0,30));
		table.setRowHeight(30);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(100,150,1000,700);
		scrollPane.getViewport().setBackground(Color.WHITE);
		
		this.add(scrollPane);
	}
	
	
	void setInfoToTable(String studentID) {
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
			PreparedStatement pstmt = Conn.prepareStatement("SELECT * FROM history "
			        + "INNER JOIN borrowed_phy_books ON history.borrowed_id = borrowed_phy_books.borrowed_id "
			        + "INNER JOIN physical_books ON physical_books.book_id = borrowed_phy_books.book_id WHERE borrowed_phy_books.borrowed_by=?");
			pstmt.setString(1, studentID);
			ResultSet rs = pstmt.executeQuery();
			model.setRowCount(0);
			while(rs.next()) {
				model.addRow(new Object[] {
						rs.getString("book_id"),
						rs.getString("title"),
						rs.getString("Hours"),
						rs.getString("Quantity"),
						rs.getString("date_borrowed"),
						rs.getString("date_returned")});
			}
			 rs.close();
			 pstmt.close();
			 Conn.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	void addDocumentListenerToStudentSearchField() {
		searchField.getDocument().addDocumentListener(new DocumentListener() {
				
		void updateSearchResults() {
			String search = searchField.getText();
			model.setRowCount(0);
			try {
				
				Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
				PreparedStatement pstmt = Conn.prepareStatement("SELECT * FROM history "
						+ "INNER JOIN borrowed_phy_books ON history.borrowed_id = borrowed_phy_books.borrowed_id "
						+ "INNER JOIN physical_books ON physical_books.book_id = borrowed_phy_books.book_id WHERE borrowed_phy_books.borrowed_by=? AND"
						+ "( (physical_books.book_id LIKE ?) OR (physical_books.title LIKE ?) OR (history.hours LIKE ?) OR (borrowed_phy_books.quantity LIKE ?) OR "
						+ "(history.date_borrowed LIKE ?) OR (history.date_returned LIKE ?) )");
				pstmt.setString(1, studentID);
				pstmt.setString(2, "%"+search+"%");
				pstmt.setString(3, "%"+search+"%");
				pstmt.setString(4, "%"+search+"%");
				pstmt.setString(5, "%"+search+"%");
				pstmt.setString(6, "%"+search+"%");
				pstmt.setString(7, "%"+search+"%");
						
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()) {
					model.addRow(new Object[] {
					    rs.getString("book_id"),
						rs.getString("title"),
						rs.getString("hours"),
						rs.getString("quantity"),
						rs.getString("date_borrowed"),
						rs.getString("date_returned")});
				}
				
			}catch(SQLException e) {
				e.printStackTrace();
			}
						
		}
				
			@Override
			public void insertUpdate(DocumentEvent e) {
				updateSearchResults();
					
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				updateSearchResults();
					
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				updateSearchResults();
					
			}
			});
	}
}
