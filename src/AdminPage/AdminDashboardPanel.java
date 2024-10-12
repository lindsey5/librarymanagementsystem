package AdminPage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import Objects.SearchField;

public class AdminDashboardPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel studentsNoPanel, teachersNoPanel, digitalBooksNoPanel, physicalBooksNoPanel;
	JLabel studentsNolbl, studentsNo, studentImglbl, teachersNolbl, teachersNo, teacherImglbl, phy_booksNolbl, phy_booksNo, phy_booksImglbl, 
		dig_booksNolbl, dig_booksNo, dig_booksImglbl;
	JComboBox<String> comboBox;
	private SearchField studentSearchField, teacherSearchField, borrowedBookSearchField, historySearchField ;
	DefaultTableModel studentModel  = new DefaultTableModel(), 
			teacherModel  = new DefaultTableModel(), 
			borrowedBookModel= new DefaultTableModel(), 
			historyModel= new DefaultTableModel();;
	JTable studentTable = new JTable(studentModel), teacherTable = new JTable(teacherModel), 
			borrowedBookTable = new JTable(borrowedBookModel),
			historyTable = new JTable(historyModel);
	JScrollPane studentScrollPane, teacherScrollPane, borrowedBookScrollPane, historyScrollPane;
	JButton studentDeletebtn, teacherDeletebtn;
	
	public AdminDashboardPanel() {
		studentsNoPanel = new JPanel();
		studentsNoPanel.setBackground(Color.WHITE);
		studentsNoPanel.setBounds(50, 50, 250, 180);
		studentsNoPanel.setLayout(null);
		this.add(studentsNoPanel);
		
		studentsNo = new JLabel();
		studentsNo.setBounds(30,50,200,30);
		studentsNo.setFont(new Font("Arial",Font.BOLD,30));
		studentsNoPanel.add(studentsNo);
		
		studentsNolbl = new JLabel("Students");
		studentsNolbl.setBounds(30, 120, 150, 30);
		studentsNolbl.setFont(new Font("Arial",Font.BOLD,20));
		studentsNoPanel.add(studentsNolbl);
		
		studentImglbl = new JLabel();
		studentImglbl.setBounds(180, 70, 70, 70);
		studentsNoPanel.add(studentImglbl);
		
		ImageIcon studentImgIcon = new ImageIcon("res/student.png");
		Image studentImgImg = studentImgIcon.getImage().getScaledInstance(studentImglbl.getWidth(), studentImglbl.getHeight(), java.awt.Image.SCALE_AREA_AVERAGING);
		studentImgIcon = new ImageIcon(studentImgImg);
		studentImglbl.setIcon(studentImgIcon);
		
		teachersNoPanel = new JPanel();
		teachersNoPanel.setBackground(Color.WHITE);
		teachersNoPanel.setBounds(330, 50, 250, 180);
		teachersNoPanel.setLayout(null);
		this.add(teachersNoPanel);
		
		teachersNo = new JLabel();
		teachersNo.setBounds(30,50,200,30);
		teachersNo.setFont(new Font("Arial",Font.BOLD,30));
		teachersNoPanel.add(teachersNo);
		
		teachersNolbl = new JLabel("Teachers");
		teachersNolbl.setBounds(30, 120, 150, 30);
		teachersNolbl.setFont(new Font("Arial",Font.BOLD,20));
		teachersNoPanel.add(teachersNolbl);
		
		teacherImglbl = new JLabel();
		teacherImglbl.setBounds(150, 70, 70, 70);
		teachersNoPanel.add(teacherImglbl);
		
		ImageIcon teacherImgIcon = new ImageIcon("res/learning.png");
		Image teacherImgImg = teacherImgIcon.getImage().getScaledInstance(teacherImglbl.getWidth(), teacherImglbl.getHeight(), java.awt.Image.SCALE_AREA_AVERAGING);
		teacherImgIcon = new ImageIcon(teacherImgImg);
		teacherImglbl.setIcon(teacherImgIcon);
		
		digitalBooksNoPanel = new JPanel();
		digitalBooksNoPanel.setBackground(Color.WHITE);
		digitalBooksNoPanel.setBounds(600, 50, 250, 180);
		digitalBooksNoPanel.setLayout(null);
		this.add(digitalBooksNoPanel);
		
		dig_booksNo = new JLabel();
		dig_booksNo.setBounds(30,50,200,30);
		dig_booksNo.setFont(new Font("Arial",Font.BOLD,30));
		digitalBooksNoPanel.add(dig_booksNo);
		
		dig_booksNolbl = new JLabel("Digital Books");
		dig_booksNolbl.setBounds(20, 120, 150, 30);
		dig_booksNolbl.setFont(new Font("Arial",Font.BOLD,17));
		digitalBooksNoPanel.add(dig_booksNolbl);
		
		dig_booksImglbl = new JLabel();
		dig_booksImglbl.setBounds(150, 70, 70, 70);
		digitalBooksNoPanel.add(dig_booksImglbl);
		
		ImageIcon dig_booksImgIcon = new ImageIcon("res/book.png");
		Image dig_booksImgImg = dig_booksImgIcon.getImage().getScaledInstance(dig_booksImglbl.getWidth(), dig_booksImglbl.getHeight(), java.awt.Image.SCALE_AREA_AVERAGING);
		dig_booksImgIcon = new ImageIcon(dig_booksImgImg);
		dig_booksImglbl.setIcon(dig_booksImgIcon);
		
		physicalBooksNoPanel = new JPanel();
		physicalBooksNoPanel.setBackground(Color.WHITE);
		physicalBooksNoPanel.setBounds(870, 50, 250, 180);
		physicalBooksNoPanel.setLayout(null);
		this.add(physicalBooksNoPanel);
		
		phy_booksNo = new JLabel();
		phy_booksNo.setBounds(30,50,200,30);
		phy_booksNo.setFont(new Font("Arial",Font.BOLD,30));
		physicalBooksNoPanel.add(phy_booksNo);
		
		phy_booksNolbl = new JLabel("Physical Books");
		phy_booksNolbl.setBounds(20, 120, 150, 30);
		phy_booksNolbl.setFont(new Font("Arial",Font.BOLD,17));
		physicalBooksNoPanel.add(phy_booksNolbl);
		
		phy_booksImglbl = new JLabel();
		phy_booksImglbl.setBounds(150, 70, 70, 70);
		physicalBooksNoPanel.add(phy_booksImglbl);
		
		ImageIcon phy_booksImgIcon = new ImageIcon("res/book.png");
		Image phy_booksImgImg = phy_booksImgIcon.getImage().getScaledInstance(phy_booksImglbl.getWidth(), phy_booksImglbl.getHeight(), java.awt.Image.SCALE_AREA_AVERAGING);
		phy_booksImgIcon = new ImageIcon(phy_booksImgImg);
		phy_booksImglbl.setIcon(phy_booksImgIcon);
		
		comboBox = new JComboBox<>();
		comboBox.setBounds(950, 300, 200, 30);
		comboBox.requestFocus();
		comboBox.addItem("Students");
		comboBox.addItem("Teachers");
		comboBox.addItem("Borrowed Books");
		comboBox.addItem("History");
		this.add(comboBox);
		
		studentSearchField = new SearchField("Search");
		studentSearchField.setBounds(50,300,300,30);
		addDocumentListenerToStudentSearchField();
		this.add(studentSearchField);
		
		studentModel.addColumn("Student ID");
		studentModel.addColumn("Fullname");
		studentModel.addColumn("Username");
		studentModel.addColumn("Course");
		studentModel.addColumn("Section");
		
		studentTable.getTableHeader().setBackground(Color.RED);
		studentTable.getTableHeader().setFont(new Font("Callibri",Font.BOLD,13));
		studentTable.getTableHeader().setEnabled(false);
		studentTable.getTableHeader().setForeground(Color.WHITE);
		studentTable.setRowHeight(20);
		
		studentScrollPane = new JScrollPane(studentTable);
		studentScrollPane.setBounds(50,400,1100,400);
		studentScrollPane.getViewport().setBackground(Color.WHITE);
		this.add(studentScrollPane);
		
		studentDeletebtn = new JButton("Delete Account");
		studentDeletebtn.setBounds(50, 830, 150, 30);
		studentDeletebtn.setBackground(Color.RED);
		studentDeletebtn.setForeground(Color.WHITE);
		studentDeletebtn.addActionListener(e->{
			
			int selectedRow = studentTable.getSelectedRow();
			
		    if (selectedRow != -1) { // If a row is selected
		    	String student_id = studentModel.getValueAt(selectedRow, 0).toString();
		    	System.out.println(student_id);
		    	int result = JOptionPane.showConfirmDialog(null, "Do you want to delete?", "Confirmation", JOptionPane.YES_NO_OPTION);
	            if (result == JOptionPane.YES_OPTION) {
	            	 deleteStudentAccount(student_id);
	            }
		    
		    }else {
		    	JOptionPane.showMessageDialog(null, "Please select a row", "",JOptionPane.WARNING_MESSAGE);
		        //System.out.println("Please select a row.");
		    }
		});
		this.add(studentDeletebtn);
		
		teacherSearchField = new SearchField("Search");
		teacherSearchField.setBounds(50,300,300,30);
		addDocumentListenerToTeacherSearchField();
		teacherSearchField.setVisible(false);
		this.add(teacherSearchField);
		
		teacherModel.addColumn("Teacher ID");
		teacherModel.addColumn("Fullname");
		teacherModel.addColumn("Username");
		teacherModel.addColumn("Department");
		
		teacherTable.getTableHeader().setBackground(Color.RED);
		teacherTable.getTableHeader().setFont(new Font("Callibri",Font.BOLD,13));
		teacherTable.getTableHeader().setEnabled(false);
		teacherTable.getTableHeader().setForeground(Color.WHITE);
		teacherTable.setRowHeight(20);
		
		teacherScrollPane = new JScrollPane(teacherTable);
		teacherScrollPane.setBounds(50,400,1100,400);
		teacherScrollPane.getViewport().setBackground(Color.WHITE);
		teacherScrollPane.setVisible(false);
		this.add(teacherScrollPane);
		
		teacherDeletebtn = new JButton("Delete Account");
		teacherDeletebtn.setBounds(50, 830, 150, 30);
		teacherDeletebtn.setBackground(Color.RED);
		teacherDeletebtn.setForeground(Color.WHITE);
		teacherDeletebtn.setVisible(false);
		teacherDeletebtn.addActionListener(e->{
			
				int selectedRow = teacherTable.getSelectedRow();
				System.out.println(selectedRow);
			    if (selectedRow != -1) { // If a row is selected
			    	String teacher_id = teacherModel.getValueAt(selectedRow, 0).toString();
			    	System.out.println(teacher_id);
			    	int result = JOptionPane.showConfirmDialog(null, "Do you want to delete?", "Confirmation", JOptionPane.YES_NO_OPTION);
		            if (result == JOptionPane.YES_OPTION) {
		            	 deleteTeacherAccount(teacher_id);
		            }
			    
			    }else {
			    	JOptionPane.showMessageDialog(null, "Please select a row", "",JOptionPane.WARNING_MESSAGE);
			        //System.out.println("Please select a row.");
			    }
		});
		this.add(teacherDeletebtn);
		
		borrowedBookSearchField = new SearchField("Search");
		borrowedBookSearchField.setBounds(50,300,300,30);
		addDocumentListenerToBorrowedBookSearchField();
		borrowedBookSearchField.setVisible(false);
		this.add(borrowedBookSearchField);
		
		borrowedBookModel.addColumn("Borrowed ID");
		borrowedBookModel.addColumn("Book ID");
		borrowedBookModel.addColumn("Book Name");
		borrowedBookModel.addColumn("Category");
		borrowedBookModel.addColumn("Author");
		borrowedBookModel.addColumn("Quantity");
		borrowedBookModel.addColumn("Borrowed By");
		
		borrowedBookTable.getTableHeader().setBackground(Color.RED);
		borrowedBookTable.getTableHeader().setFont(new Font("Callibri",Font.BOLD,13));
		borrowedBookTable.getTableHeader().setEnabled(false);
		borrowedBookTable.getTableHeader().setForeground(Color.WHITE);
		borrowedBookTable.setRowHeight(20);
		
		borrowedBookScrollPane = new JScrollPane(borrowedBookTable);
		borrowedBookScrollPane.setBounds(50,400,1100,400);
		borrowedBookScrollPane.getViewport().setBackground(Color.WHITE);
		borrowedBookScrollPane.setVisible(false);
		this.add(borrowedBookScrollPane);
		
		historySearchField = new SearchField("Search");
		historySearchField.setBounds(50,300,300,30);
		addDocumentListenerToHistorySearchField();
		historySearchField.setVisible(false);
		this.add(historySearchField);
		
		historyModel.addColumn("Book ID");
		historyModel.addColumn("Book Name");
		historyModel.addColumn("Borrowed By");
		historyModel.addColumn("Hours");
		historyModel.addColumn("Quantity");
		historyModel.addColumn("Date Borrowed");
		historyModel.addColumn("Date Returned");
		
		historyTable.getTableHeader().setBackground(Color.RED);
		historyTable.getTableHeader().setFont(new Font("Callibri",Font.BOLD,13));
		historyTable.getTableHeader().setEnabled(false);
		historyTable.getTableHeader().setForeground(Color.WHITE);
		historyTable.setRowHeight(20);
		
		historyScrollPane = new JScrollPane(historyTable);
		historyScrollPane.setBounds(50,400,1100,400);
		historyScrollPane.getViewport().setBackground(Color.WHITE);
		historyScrollPane.setVisible(false);
		this.add(historyScrollPane);
		
		comboBox.addActionListener(e->{
			String user = (String)comboBox.getSelectedItem();
			if(user.equals("Students")) {
				showStudentTable();
				hideTeacherTable();
				hideBorrowedBooksTable();
				hideHistoryTable();

			}else if(user.equals("Teachers")){
				hideStudentTable();
				showTeacherTable();
				hideBorrowedBooksTable();
				hideHistoryTable();
			}else if(user.equals("Borrowed Books")) {
				hideStudentTable();
				hideTeacherTable();
				showBorrowedBooksTable();
				hideHistoryTable();
			}else if(user.equals("History")) {
				hideStudentTable();
				hideTeacherTable();
				hideBorrowedBooksTable();
				showHistoryTable();
			}
		});
		
		this.setLayout(null);
		setNoOfStudents();
		setNoOfTeachers();
		setNoOfPhysicalBooks();
		setNoOfDigitalBooks();
		addStudentToTable();
		addTeacherToTable();
		addBorrowedBookToTable();
		addHistoryToTable();
	}
	
	public void refresh() {
		setNoOfStudents();
		setNoOfTeachers();
		setNoOfPhysicalBooks();
		setNoOfDigitalBooks();
		addStudentToTable();
		addTeacherToTable();
		addBorrowedBookToTable();
		addHistoryToTable();
	}
	
	void showStudentTable() {
		studentSearchField.setVisible(true);
		studentScrollPane.setVisible(true);
		studentDeletebtn.setVisible(true);
		addStudentToTable();
	}
	
	void hideStudentTable() {
		studentSearchField.setVisible(false);
		studentScrollPane.setVisible(false);
		studentDeletebtn.setVisible(false);
	}
	
	void showTeacherTable() {
		teacherSearchField.setVisible(true);
		teacherScrollPane.setVisible(true);
		teacherDeletebtn.setVisible(true);
		addTeacherToTable();
	}
	
	void hideTeacherTable() {
		teacherSearchField.setVisible(false);
		teacherScrollPane.setVisible(false);
		teacherDeletebtn.setVisible(false);
	}
	
	void showBorrowedBooksTable() {
		borrowedBookSearchField.setVisible(true);
		borrowedBookScrollPane.setVisible(true);
		addBorrowedBookToTable();
	}
	
	void hideBorrowedBooksTable() {
		borrowedBookSearchField.setVisible(false);
		borrowedBookScrollPane.setVisible(false);
	}
	
	void showHistoryTable() {
		historySearchField.setVisible(true);
		historyScrollPane.setVisible(true);
		addHistoryToTable();
	}
	
	void hideHistoryTable() {
		historySearchField.setVisible(false);
		historyScrollPane.setVisible(false);
	}
	
	void deleteStudentAccount(String student_id) {
		try {
			returnBook(student_id);
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
	    	PreparedStatement stmt = Conn.prepareStatement("DELETE FROM user_id_table WHERE user_id=?");
	    	stmt.setString(1, student_id);
			stmt.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Account Successfully Deleted");
			addStudentToTable();
			
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    }
	}
	
	void deleteTeacherAccount(String teacher_id) {
		try {
			returnBook(teacher_id);
			System.out.println(teacher_id);
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
	    	PreparedStatement stmt = Conn.prepareStatement("DELETE FROM user_id_table WHERE user_id=?");
	    	stmt.setString(1, teacher_id);
			stmt.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Account Successfully Deleted");
			addTeacherToTable();
			
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    }
	}
	
	void returnBook(String borrowed_by) {
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
			PreparedStatement stmt = Conn.prepareStatement("SELECT * FROM borrowed_phy_books WHERE borrowed_by=?");
			stmt.setString(1, borrowed_by);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				int book_id = rs.getInt("book_id");
				int quantity = rs.getInt("quantity");
				updateAvailableBooks(book_id, quantity);
			}
			
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    }
		
	}
	
	void updateAvailableBooks(int book_id, int quantity) {
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
			
			PreparedStatement stmt = Conn.prepareStatement("UPDATE physical_books SET available = available + ? WHERE book_id=?");
			stmt.setInt(1, quantity);
			stmt.setInt(2, book_id);
			stmt.executeUpdate();
			
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    }
	}
	
	void setNoOfStudents() {
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
			PreparedStatement stmt = Conn.prepareStatement("SELECT COUNT(*) FROM student_accounts");
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				studentsNo.setText(rs.getString(1));
			}
			
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    }
	}
	
	void setNoOfTeachers() {
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
			PreparedStatement stmt = Conn.prepareStatement("SELECT COUNT(*) FROM teacher_accounts");
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				teachersNo.setText(rs.getString(1));
			}
			
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    }
	}
	
	void setNoOfPhysicalBooks() {
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
			PreparedStatement stmt = Conn.prepareStatement("SELECT COUNT(*) FROM physical_books");
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				phy_booksNo.setText(rs.getString(1));
			}
			
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    }
	}
	
	void setNoOfDigitalBooks() {
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
			PreparedStatement stmt = Conn.prepareStatement("SELECT COUNT(*) FROM digital_books");
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				dig_booksNo.setText(rs.getString(1));
			}
			
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    }
	}
	
	void addStudentToTable() {
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
			PreparedStatement pstmt = Conn.prepareStatement("SELECT * FROM student_accounts");
			ResultSet rs = pstmt.executeQuery();
			studentModel.setRowCount(0);
			while(rs.next()) {
				studentModel.addRow(new Object[] {
				    rs.getString("Student_ID"),
					rs.getString("Fullname"),
					rs.getString("username"),
					rs.getString("Course"),
					rs.getString("Section")});
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	void addTeacherToTable() {
		
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
			PreparedStatement pstmt = Conn.prepareStatement("SELECT * FROM teacher_accounts");
			ResultSet rs = pstmt.executeQuery();
			teacherModel.setRowCount(0);
			while(rs.next()) {
				teacherModel.addRow(new Object[] {
				    rs.getString("teacher_ID"),
					rs.getString("Fullname"),
					rs.getString("username"),
					rs.getString("department")});
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	void addBorrowedBookToTable() {
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
			PreparedStatement pstmt = Conn.prepareStatement("SELECT * FROM borrowed_phy_books JOIN physical_books ON physical_books.book_id = borrowed_phy_books.book_id WHERE borrowed_phy_books.status='Unreturned'");
			ResultSet rs = pstmt.executeQuery();
			borrowedBookModel.setRowCount(0);
			while(rs.next()) {
				borrowedBookModel.addRow(new Object[] {
					rs.getString("borrowed_id"),
					rs.getString("book_id"),
					rs.getString("title"),
					rs.getString("category"),
					rs.getString("author"),
					rs.getString("quantity"),
					rs.getString("borrowed_by")
				});
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	void addHistoryToTable() {
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
			PreparedStatement pstmt = Conn.prepareStatement("SELECT * FROM history "
					+ "INNER JOIN borrowed_phy_books ON history.borrowed_id = borrowed_phy_books.borrowed_id\r\n"
					+ "INNER JOIN physical_books ON physical_books.book_id = borrowed_phy_books.book_id;\r\n"
					+ "");
			ResultSet rs = pstmt.executeQuery();
			historyModel.setRowCount(0);
			while(rs.next()) {
				historyModel.addRow(new Object[] {
					rs.getString("book_id"),
					rs.getString("title"),
					rs.getString("borrowed_by"),
					rs.getString("hours"),
					rs.getString("quantity"),
					rs.getString("date_borrowed"),
					rs.getString("date_returned")
				});
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	void addDocumentListenerToStudentSearchField() {
		studentSearchField.getDocument().addDocumentListener(new DocumentListener() {
				
		void updateSearchResults() {
			String search = studentSearchField.getText();
			studentModel.setRowCount(0);
			try {
				
				Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
				PreparedStatement pstmt = Conn.prepareStatement("SELECT * FROM student_accounts WHERE (student_id LIKE ?) OR  (fullname LIKE ?) OR  (username LIKE ?) OR (Course LIKE ?)OR (section LIKE ?)");
				pstmt.setString(1, "%"+search+"%");
				pstmt.setString(2, "%"+search+"%");
				pstmt.setString(3, "%"+search+"%");
				pstmt.setString(4, "%"+search+"%");
				pstmt.setString(5, "%"+search+"%");
						
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()) {
					studentModel.addRow(new Object[] {
					    rs.getString("Student_ID"),
						rs.getString("Fullname"),
						rs.getString("username"),
						rs.getString("Course"),
						rs.getString("Section")});
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
	
	
	void addDocumentListenerToTeacherSearchField() {
		teacherSearchField.getDocument().addDocumentListener(new DocumentListener() {
				
		void updateSearchResults() {
			String search = teacherSearchField.getText();
			teacherModel.setRowCount(0);
			try {
				
				Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
				PreparedStatement pstmt = Conn.prepareStatement("SELECT * FROM teacher_accounts WHERE (teacher_id LIKE ?) OR (fullname LIKE ?) OR (username LIKE ?) OR (department LIKE ?)");
				pstmt.setString(1, "%"+search+"%");
				pstmt.setString(2, "%"+search+"%");
				pstmt.setString(3, "%"+search+"%");
				pstmt.setString(4, "%"+search+"%");
						
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()) {
					teacherModel.addRow(new Object[] {
					rs.getString("Teacher_ID"),
					rs.getString("Fullname"),
					rs.getString("username"),
				    rs.getString("department")});
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
	
	void addDocumentListenerToBorrowedBookSearchField() {
		borrowedBookSearchField.getDocument().addDocumentListener(new DocumentListener() {
				
		void updateSearchResults() {
			String search = borrowedBookSearchField.getText();
			borrowedBookModel.setRowCount(0);
			try {
			    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb", "root", "");
			    PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM borrowed_phy_books JOIN physical_books ON physical_books.book_id = borrowed_phy_books.book_id "
			    		+ "WHERE status='unreturned' AND ((borrowed_phy_books.borrowed_id LIKE ?) OR (physical_books.book_id LIKE ?) OR (physical_books.title LIKE ?) OR (physical_books.category LIKE ?) "
			    		+ "OR (physical_books.author LIKE ?) OR (borrowed_phy_books.quantity LIKE ?) OR (borrowed_phy_books.borrowed_by LIKE ?))");
			    pstmt.setString(1, "%" + search + "%");
			    pstmt.setString(2, "%" + search + "%");
			    pstmt.setString(3, "%" + search + "%");
			    pstmt.setString(4, "%" + search + "%");
			    pstmt.setString(5, "%" + search + "%");
			    pstmt.setString(6, "%" + search + "%");
			    pstmt.setString(7, "%" + search + "%");

			    ResultSet rs = pstmt.executeQuery();
			    while (rs.next()) {
			        borrowedBookModel.addRow(new Object[]{
			        		rs.getString("borrowed_id"),
			                rs.getString("book_id"),
			                rs.getString("title"),
			                rs.getString("category"),
			                rs.getString("author"),
			                rs.getString("quantity"),
			                rs.getString("borrowed_by")});
			    }
			} catch (SQLException e) {
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
	
	void addDocumentListenerToHistorySearchField() {
		historySearchField.getDocument().addDocumentListener(new DocumentListener() {
				
		void updateSearchResults() {
			String search = historySearchField.getText();
			historyModel.setRowCount(0);
			try {
			    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb", "root", "");
			    PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM history "
						+ "INNER JOIN borrowed_phy_books ON history.borrowed_id = borrowed_phy_books.borrowed_id "
						+ "INNER JOIN physical_books ON physical_books.book_id = borrowed_phy_books.book_id "
						+ "WHERE (physical_books.book_id LIKE ?) OR (physical_books.title LIKE ?) OR (borrowed_phy_books.borrowed_by LIKE ?) "
						+ "OR (history.hours LIKE ?) OR (borrowed_phy_books.quantity LIKE ?) OR (history.date_borrowed LIKE ?) OR (history.date_returned LIKE ?)");
			    pstmt.setString(1, "%" + search + "%");
			    pstmt.setString(2, "%" + search + "%");
			    pstmt.setString(3, "%" + search + "%");
			    pstmt.setString(4, "%" + search + "%");
			    pstmt.setString(5, "%" + search + "%");
			    pstmt.setString(6, "%" + search + "%");
			    pstmt.setString(7, "%" + search + "%");

			    ResultSet rs = pstmt.executeQuery();
			    while(rs.next()) {
					historyModel.addRow(new Object[] {
						rs.getString("book_id"),
						rs.getString("title"),
						rs.getString("borrowed_by"),
						rs.getString("hours"),
						rs.getString("quantity"),
						rs.getString("date_borrowed"),
						rs.getString("date_returned")
					});
				}
			} catch (SQLException e) {
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
