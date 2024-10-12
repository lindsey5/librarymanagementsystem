package Objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public abstract class AbstractPhysicalLibrary extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DefaultTableModel model;
	SearchField searchField;
	JComboBox<String> categories;
	JButton addBookbtn, addCategorybtn, deleteCategorybtn;
	public JFrame frame;
	byte[] bookImgData = null;
	protected JLabel imagelbl, titlelbl, authorlbl, categorylbl;
	protected JButton editbtn, deletebtn;
	protected int book_id;
	
	public int availableBook = 0;

	public AbstractPhysicalLibrary(JFrame frame) {
		this.frame = frame;
		
		searchField = new SearchField("Search");
		searchField.setBounds(30, 30, 300, 30);
		addDocumentListenerToSearchField();
		this.add(searchField);
		
		categories = new JComboBox<>();
		categories.setBackground(Color.WHITE);
		categories.setBounds(480, 30, 200, 30);
		this.add(categories);
		
		categories.addActionListener(e->{
			if(categories.getSelectedItem()!=null) {
				String category = (String)categories.getSelectedItem();
				if(category.equals("All")) {
					setPhysicalBooksToTable("All");
				}else {
					setPhysicalBooksToTable(category);
				}
			}
			
		});
		
		imagelbl = new JLabel();
		imagelbl.setBounds(900, 100, 200, 250);
		imagelbl.setBackground(Color.LIGHT_GRAY);
		imagelbl.setOpaque(true);
		imagelbl.setVisible(false);
		this.add(imagelbl);
		
		titlelbl = new JLabel("Title");
		titlelbl.setBounds(800, 400, 400, 30);
		titlelbl.setFont(new Font("Arial",Font.BOLD, 15));
		titlelbl.setHorizontalAlignment(SwingConstants.CENTER);
		titlelbl.setVisible(false);
		this.add(titlelbl);
		
		authorlbl = new JLabel("Author");
		authorlbl.setBounds(800, 450, 400, 30);
		authorlbl.setFont(new Font("Arial",Font.BOLD, 15));
		authorlbl.setHorizontalAlignment(SwingConstants.CENTER);
		authorlbl.setVisible(false);
		this.add(authorlbl);
		
		categorylbl = new JLabel("Category");
		categorylbl.setBounds(800, 500, 400, 30);
		categorylbl.setFont(new Font("Arial",Font.BOLD, 15));
		categorylbl.setHorizontalAlignment(SwingConstants.CENTER);
		categorylbl.setVisible(false);
		this.add(categorylbl);
		
		deletebtn = new JButton("Delete Book");
		deletebtn.setBounds(900, 650, 200, 30);
		deletebtn.setBackground(Color.LIGHT_GRAY);
		deletebtn.setForeground(Color.BLACK);
		deletebtn.setVisible(false);
		deletebtn.addActionListener(e->{
			 int result = JOptionPane.showConfirmDialog(null, "Do you want to delete?", "Confirmation", JOptionPane.YES_NO_OPTION);
             if (result == JOptionPane.YES_OPTION) {
            	 deleteBook(book_id);
             }
		});
		this.add(deletebtn);
		
		editbtn = new JButton("Edit");
		editbtn.setBounds(900, 700, 200, 30);
		editbtn.setBackground(Color.LIGHT_GRAY);
		editbtn.setForeground(Color.BLACK);
		editbtn.setVisible(false);
		editbtn.addActionListener(e-> showEditBookDialog(book_id));
		this.add(editbtn);
		
		addBookbtn = new JButton("Add Book");
		addBookbtn.setBounds(30, 800, 150, 40);
		addBookbtn.setBackground(Color.RED);
		addBookbtn.setForeground(Color.WHITE);
		addBookbtn.setFocusable(false);
		addBookbtn.setFont(new Font("Arial",Font.BOLD,15));
		addBookbtn.addActionListener(e-> showAddBookDialog());
		this.add(addBookbtn);
		
		addCategorybtn = new JButton("Add Category");
		addCategorybtn.setBounds(290, 800, 150, 40);
		addCategorybtn.setBackground(Color.RED);
		addCategorybtn.setForeground(Color.WHITE);
		addCategorybtn.setFocusable(false);
		addCategorybtn.setFont(new Font("Arial",Font.BOLD,15));
		addCategorybtn.addActionListener(e-> showAddCategoryDialog());
		this.add(addCategorybtn);
		
		deleteCategorybtn = new JButton("Delete Category");
		deleteCategorybtn.setBounds(530, 800, 150, 40);
		deleteCategorybtn.setBackground(Color.LIGHT_GRAY);
		deleteCategorybtn.setForeground(Color.BLACK);
		deleteCategorybtn.setFocusable(false);
		deleteCategorybtn.setFont(new Font("Arial",Font.BOLD,15));
		deleteCategorybtn.addActionListener(e-> showDeleteCategoryDialog());
		this.add(deleteCategorybtn);
		
		this.setLayout(null);
		this.setBackground(Color.WHITE);
		makeTable();
		setPhysicalBooksToTable("All");
		addCategoryToComboBox();
	}
	
	void addDocumentListenerToSearchField() {
		//Updates the table base on the text that in the search field
		searchField.getDocument().addDocumentListener(new DocumentListener() {
				
		void updateSearchResults() {
			String search = searchField.getText();
			model.setRowCount(0);
			try {
				
				Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
				PreparedStatement pstmt = Conn.prepareStatement("SELECT * FROM physical_books WHERE (book_id LIKE ?) OR (title LIKE ?) OR (author LIKE ?) OR (category LIKE ?) OR (status LIKE ?)");
				pstmt.setString(1, "%"+search+"%");
				pstmt.setString(2, "%"+search+"%");
				pstmt.setString(3, "%"+search+"%");
				pstmt.setString(4, "%"+search+"%");
				pstmt.setString(5, "%"+search+"%");
						
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()) {
					model.addRow(new Object[] {
							rs.getString("book_id"),
							rs.getString("title"),
							rs.getString("category"),
							rs.getString("author"),
							rs.getString("stock"),
							rs.getString("available")});
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
	
	void deleteBook(int bookID) {
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
	    	PreparedStatement stmt = Conn.prepareStatement("DELETE FROM physical_books WHERE book_id=?");
	    	stmt.setInt(1, bookID);
			stmt.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Book Successfully Deleted");
			//Refresh the table
			setPhysicalBooksToTable("All");
			hideBook();
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    }
	}
	
	void showAddCategoryDialog() {
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
        
        JLabel categorylbl = new JLabel("Enter Category");
        categorylbl.setBounds(50, 70, 150, 30);
        categorylbl.setFont(new Font("Arial",Font.BOLD,15));
        categorylbl.setForeground(Color.LIGHT_GRAY);
        overlayPanel.add(categorylbl);
        
        JTextField categorytxt = new JTextField();
        categorytxt.setBounds(50, 100, 200, 30);
        overlayPanel.add(categorytxt);
        
        JButton addCategorybtn = new JButton("Add Category");
        addCategorybtn.setBounds(50, 170, 200, 30);
        addCategorybtn.setBackground(Color.RED);
        addCategorybtn.setForeground(Color.WHITE);
        addCategorybtn.addActionListener(e->{
        	if(checkIfCategoryExist(categorytxt.getText())) {
        		JOptionPane.showMessageDialog(null, "Category Already Exist","",JOptionPane.WARNING_MESSAGE);
        	}else {
        		addCategory(categorytxt.getText());
        		overlayDialog.dispose();
        	}
        });
        overlayPanel.add(addCategorybtn);
        
        overlayDialog.setSize(300,250);
	    overlayDialog.setLocationRelativeTo(null);
	    overlayDialog.setVisible(true);
	}
	
	void addCategory(String category) {
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
	    	PreparedStatement stmt = Conn.prepareStatement("INSERT INTO physical_books_category (category) VALUES(?)");
	    	stmt.setString(1, category);
			stmt.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Category Successfull Added");
			addCategoryToComboBox();
			
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    }
	}
	
	boolean checkIfCategoryExist(String category) {
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
	    	PreparedStatement stmt = Conn.prepareStatement("SELECT * FROM physical_books_category WHERE category=?");
	    	stmt.setString(1, category);
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
	
	void showDeleteCategoryDialog() {
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
        
        
        JComboBox<String> categories = new JComboBox<>();
        categories.setBounds(50, 100, 200, 30);
        categories.setBackground(Color.WHITE);
        try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
	    	PreparedStatement stmt = Conn.prepareStatement("SELECT * FROM physical_books_category");
	    	
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				categories.addItem(rs.getString("category"));
			}
			
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    }
        
        overlayPanel.add(categories);
        
        JButton deletebtn = new JButton("Delete Category");
        deletebtn.setBounds(50, 200, 200, 30);
        deletebtn.setBackground(Color.RED);
        deletebtn.setForeground(Color.WHITE);
        deletebtn.addActionListener(e->{
        	 int result = JOptionPane.showConfirmDialog(null, "Do you want to delete?", "Confirmation", JOptionPane.YES_NO_OPTION);
             if (result == JOptionPane.YES_OPTION) {
            	 deleteCategory((String)categories.getSelectedItem());
            	 overlayDialog.dispose();
             }
        });
        overlayPanel.add(deletebtn);
        
        overlayDialog.setSize(300,300);
	    overlayDialog.setLocationRelativeTo(null);
	    overlayDialog.setVisible(true);
	}
	
	void deleteCategory(String category) {
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
	    	PreparedStatement stmt = Conn.prepareStatement("DELETE FROM physical_books_category WHERE category=?");
	    	stmt.setString(1, category);
			stmt.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Category Successfully Deleted");
			addCategoryToComboBox();
			
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    }
	}
	
	void addCategoryToComboBox() {
		categories.removeAllItems();
		categories.addItem("All");
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
	    	PreparedStatement stmt = Conn.prepareStatement("SELECT * FROM physical_books_category");
	    	
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				categories.addItem(rs.getString("category"));
			}
			
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    }
		
		categories.setSelectedIndex(0);
	} 
	
	
	void makeTable() {
		model = new DefaultTableModel(); ;
		JTable table = new JTable(model);
		
		model.addColumn("Book ID");
		model.addColumn("Book Title");
		model.addColumn("Category");
		model.addColumn("Author");
		model.addColumn("Stock");
		model.addColumn("Available");
		
		table.addMouseListener(new MouseAdapter() {
		    @Override
	        public void mouseClicked(MouseEvent e) {
		    	book_id = Integer.parseInt((String)model.getValueAt(table.getSelectedRow(), 0));
	            showBook(book_id);
	        }
	    });
		
		table.getTableHeader().setBackground(Color.RED);
		table.getTableHeader().setForeground(Color.WHITE);
		/*table.setSelectionBackground(Color.RED);
		table.setSelectionForeground(Color.WHITE);*/
		table.getTableHeader().setFont(new Font("Callibri",Font.BOLD,13));
		table.getTableHeader().setEnabled(false);
		table.getTableHeader().setPreferredSize(new Dimension(0,30));
		table.setRowHeight(20);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(30,100,800,600);
		scrollPane.getViewport().setBackground(Color.WHITE);
		
		this.add(scrollPane);
	}
	
	public void setPhysicalBooksToTable(String category) {
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
			PreparedStatement pstmt;
			if(category.equals("All")) {
				pstmt = Conn.prepareStatement("SELECT * FROM physical_books");
			}else {
				pstmt = Conn.prepareStatement("SELECT * FROM physical_books WHERE category=?");
				pstmt.setString(1, category);
			}
			ResultSet rs = pstmt.executeQuery();
			model.setRowCount(0);
			while(rs.next()) {
				model.addRow(new Object[] {
						rs.getString("book_id"),
						rs.getString("title"),
						rs.getString("category"),
						rs.getString("Author"),
						rs.getString("stock"),
						rs.getString("Available")});
			}
			 rs.close();
			 pstmt.close();
			 Conn.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	void showEditBookDialog(int book_id) {
		availableBook = 0;
		
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
        closebtn.setBounds(430, 20, 50, 30);
        closebtn.setBackground(Color.WHITE);
        closebtn.setForeground(Color.BLACK);
        closebtn.setFocusable(false);
        closebtn.setBorder(null);
        overlayPanel.add(closebtn);
        
        JLabel editBooklbl = new JLabel("Edit Book");
        editBooklbl.setBounds(40, 20, 150, 40);
        editBooklbl.setFont(new Font("Arial",Font.BOLD,25));
        editBooklbl.setForeground(Color.RED);
        overlayPanel.add(editBooklbl);
        
        JLabel bookImagelbl = new JLabel();
        bookImagelbl.setBounds(40, 80, 150, 200);
        bookImagelbl.setBackground(Color.LIGHT_GRAY);
        bookImagelbl.setOpaque(true);
        overlayPanel.add(bookImagelbl);
        
        JButton selectImagebtn = new JButton("Select Image");
        selectImagebtn.setBounds(40, 300, 150, 20);
        selectImagebtn.setBackground(Color.RED);
        selectImagebtn.setForeground(Color.WHITE);
        selectImagebtn.addActionListener(e->{
        	JFileChooser filechooser = new JFileChooser();
			 filechooser.setFileFilter(new FileNameExtensionFilter("Image files","jpg","jpeg","png","gif"));
			 int result = filechooser.showOpenDialog(null);
			 //System.out.println(result);
			 if(result != JFileChooser.APPROVE_OPTION) {
				return;
			 }
			 File selectedFile = filechooser.getSelectedFile();
			 
			 try {
				 byte[] imgData = null;
				 imgData = new byte[(int) selectedFile.length()];
				 FileInputStream inputStream = new FileInputStream(selectedFile);
				 inputStream.read(imgData);
				 inputStream.close();
				 
				 ImageIcon icon = new ImageIcon(imgData);
				 Image img = icon.getImage().getScaledInstance(bookImagelbl.getWidth(), bookImagelbl.getHeight(), java.awt.Image.SCALE_AREA_AVERAGING);
				 icon = new ImageIcon(img);
				 bookImagelbl.setIcon(icon);
				 
				 bookImgData = imgData;

			 }catch(IOException e1) {
				 e1.printStackTrace();
			 }
        });
        overlayPanel.add(selectImagebtn);
        
        JLabel titlelbl = new JLabel("Book Title");
        titlelbl.setBounds(240, 80, 150, 30);
        titlelbl.setForeground(Color.GRAY);
        titlelbl.setFont(new Font("Arial",Font.BOLD,15));
        overlayPanel.add(titlelbl);
        
        JTextField titletxt = new JTextField();
        titletxt.setBounds(240, 110, 200, 30);
        overlayPanel.add(titletxt);
        
        JLabel authorlbl = new JLabel("Author");
        authorlbl.setBounds(240, 150, 150, 30);
        authorlbl.setForeground(Color.GRAY);
        authorlbl.setFont(new Font("Arial",Font.BOLD,15));
        overlayPanel.add(authorlbl);
        
        JTextField authortxt = new JTextField();
        authortxt.setBounds(240, 180, 200, 30);
        overlayPanel.add(authortxt);
        
        JLabel categorylbl = new JLabel("Category");
        categorylbl.setBounds(240, 220, 150, 30);
        categorylbl.setForeground(Color.GRAY);
        categorylbl.setFont(new Font("Arial",Font.BOLD,15));
        overlayPanel.add(categorylbl);
        
        JComboBox<String>categories = new JComboBox<>();
		categories.setBackground(Color.WHITE);
		categories.setBounds(240, 250, 200, 30);
		overlayPanel.add(categories);
		
		 JLabel stocklbl = new JLabel("Stock");
	     stocklbl.setBounds(240, 300, 150, 30);
	     stocklbl.setForeground(Color.GRAY);
	     stocklbl.setFont(new Font("Arial",Font.BOLD,15));
	     overlayPanel.add(stocklbl);
	        
	     JTextField stocktxt = new JTextField();
	     stocktxt.setBounds(240, 330, 200, 30);
	     stocktxt.addKeyListener(new KeyAdapter() {
	        	@Override
	            public void keyTyped(KeyEvent e) {
	                char c = e.getKeyChar();
	                if (!Character.isDigit(c)) {
	                    e.consume(); // Stop the event from being processed
	                }
	            }
	        });
	     overlayPanel.add(stocktxt);
	     
		 
		 try {
				Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
		    	PreparedStatement stmt = Conn.prepareStatement("SELECT * FROM physical_books_category");
		    	categories.addItem("");
				ResultSet rs = stmt.executeQuery();
				while(rs.next()) {
					categories.addItem(rs.getString("category"));
				}
				
		}catch(SQLException e) {
			e.printStackTrace();
		}
		 
		
		 try {
				Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
		    	PreparedStatement stmt = Conn.prepareStatement("SELECT * FROM physical_books WHERE book_id=?");
		    	stmt.setInt(1, book_id);
				ResultSet rs = stmt.executeQuery();
				if(rs.next()) {
					bookImgData = rs.getBytes("image");
					titletxt.setText(rs.getString("title"));
					authortxt.setText( rs.getString("author"));
					
					categories.setSelectedItem(rs.getString("category"));
					stocktxt.setText(rs.getString("stock"));
					availableBook = rs.getInt("available");
					ImageIcon bookIcon = new ImageIcon(bookImgData);
					Image bookImg = bookIcon.getImage().getScaledInstance(bookImagelbl.getWidth(), bookImagelbl.getHeight(), java.awt.Image.SCALE_AREA_AVERAGING);
					bookIcon = new ImageIcon(bookImg);
					bookImagelbl.setIcon(bookIcon);
				}		
		}catch(SQLException e) {
			e.printStackTrace();
		} 
		
		 
		JButton savebtn = new JButton("Save");
		savebtn.setBounds(290, 380, 150, 30);
		savebtn.setBackground(Color.RED);
		savebtn.setForeground(Color.WHITE);
		overlayPanel.add(savebtn);
		savebtn.addActionListener(e->{
			if(titletxt.getText().isBlank()) {
        		JOptionPane.showMessageDialog(null, "Book Title is Blank","",JOptionPane.WARNING_MESSAGE);
        		
        	}else if(bookImgData==null) {
        		JOptionPane.showMessageDialog(null, "Select Image","",JOptionPane.WARNING_MESSAGE);
        		
        	}else if(authortxt.getText().isBlank()) {
        		JOptionPane.showMessageDialog(null, "Book Author is Blank","",JOptionPane.WARNING_MESSAGE);
        		
        	}else if(categories.getSelectedItem().toString().isBlank()) {
        		JOptionPane.showMessageDialog(null, "Select Category","",JOptionPane.WARNING_MESSAGE);
        		
        	}else {
        		int stock = Integer.parseInt(stocktxt.getText());
        		editBook(titletxt.getText(), bookImgData, (String)categories.getSelectedItem(), authortxt.getText(), stock, availableBook, book_id);
        		hideBook();
        		bookImgData = null;
        		setPhysicalBooksToTable("All");
        		overlayDialog.dispose();
        	}
			
		});
        
        overlayDialog.setSize(500,450);
	    overlayDialog.setLocationRelativeTo(null);
	    overlayDialog.setVisible(true);
	}
	
	void editBook(String title, byte[] book_image, String category, String author, int newStock, int availableBook, int book_id) {
		try {
			int stock = 0;
			
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
			PreparedStatement stmt = Conn.prepareStatement("SELECT * FROM physical_books WHERE book_id=?");
	    	stmt.setInt(1, book_id);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				stock = rs.getInt("stock");
			}
			
	    	stmt = Conn.prepareStatement("UPDATE physical_books SET title=?, image=?, category=?, author=?, stock=?, available=? WHERE book_id=?");
	    	stmt.setString(1, title);
	    	stmt.setBytes(2, book_image);
	    	stmt.setString(3, category);
	    	stmt.setString(4, author);
	    	stmt.setInt(5, newStock);
	    	if(newStock > stock) {
	    		stmt.setInt(6, availableBook + (newStock-stock));
	    	}else {
	    		stmt.setInt(6, availableBook - (stock-newStock));
	    	}
	    	
	    	stmt.setInt(7, book_id);
			stmt.executeUpdate();
			setPhysicalBooksToTable("All");
			JOptionPane.showMessageDialog(null, "Update Successful");
			
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    }
	}
	
	void showAddBookDialog() {
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
        closebtn.setBounds(430, 20, 50, 30);
        closebtn.setBackground(Color.WHITE);
        closebtn.setForeground(Color.BLACK);
        closebtn.setFocusable(false);
        closebtn.setBorder(null);
        overlayPanel.add(closebtn);
        
        JLabel addBooklbl = new JLabel("Add Book");
        addBooklbl.setBounds(40, 20, 150, 40);
        addBooklbl.setFont(new Font("Arial",Font.BOLD,25));
        addBooklbl.setForeground(Color.RED);
        overlayPanel.add(addBooklbl);
        
        JLabel bookImagelbl = new JLabel();
        bookImagelbl.setBounds(40, 80, 150, 200);
        bookImagelbl.setBackground(Color.LIGHT_GRAY);
        bookImagelbl.setOpaque(true);
        overlayPanel.add(bookImagelbl);
        
        JButton selectImagebtn = new JButton("Select Image");
        selectImagebtn.setBounds(40, 300, 150, 20);
        selectImagebtn.setBackground(Color.RED);
        selectImagebtn.setForeground(Color.WHITE);
        selectImagebtn.addActionListener(e->{
        	JFileChooser filechooser = new JFileChooser();
			 filechooser.setFileFilter(new FileNameExtensionFilter("Image files","jpg","jpeg","png","gif"));
			 int result = filechooser.showOpenDialog(null);
			 //System.out.println(result);
			 if(result != JFileChooser.APPROVE_OPTION) {
				return;
			 }
			 File selectedFile = filechooser.getSelectedFile();
			 
			 try {
				 byte[] imgData = null;
				 imgData = new byte[(int) selectedFile.length()];
				 FileInputStream inputStream = new FileInputStream(selectedFile);
				 inputStream.read(imgData);
				 inputStream.close();
				 
				 ImageIcon icon = new ImageIcon(imgData);
				 Image img = icon.getImage().getScaledInstance(bookImagelbl.getWidth(), bookImagelbl.getHeight(), java.awt.Image.SCALE_AREA_AVERAGING);
				 icon = new ImageIcon(img);
				 bookImagelbl.setIcon(icon);
				 
				 bookImgData = imgData;

			 }catch(IOException e1) {
				 e1.printStackTrace();
			 }
        });
        overlayPanel.add(selectImagebtn);
        
        JLabel titlelbl = new JLabel("Book Title");
        titlelbl.setBounds(240, 80, 150, 30);
        titlelbl.setForeground(Color.GRAY);
        titlelbl.setFont(new Font("Arial",Font.BOLD,15));
        overlayPanel.add(titlelbl);
        
        JTextField titletxt = new JTextField();
        titletxt.setBounds(240, 110, 200, 30);
        overlayPanel.add(titletxt);
        
        JLabel authorlbl = new JLabel("Author");
        authorlbl.setBounds(240, 150, 150, 30);
        authorlbl.setForeground(Color.GRAY);
        authorlbl.setFont(new Font("Arial",Font.BOLD,15));
        overlayPanel.add(authorlbl);
        
        JTextField authortxt = new JTextField();
        authortxt.setBounds(240, 180, 200, 30);
        overlayPanel.add(authortxt);
        
        JLabel stocklbl = new JLabel("Stock");
        stocklbl.setBounds(240, 220, 150, 30);
        stocklbl.setForeground(Color.GRAY);
        stocklbl.setFont(new Font("Arial",Font.BOLD,15));
        
        overlayPanel.add(stocklbl);
        
        JTextField stocktxt = new JTextField();
        stocktxt.setBounds(240, 250, 200, 30);
        stocktxt.addKeyListener(new KeyAdapter() {
        	@Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume(); // Stop the event from being processed
                }
            }
        });
        overlayPanel.add(stocktxt);
        
        JLabel categorylbl = new JLabel("Category");
        categorylbl.setBounds(240, 290, 150, 30);
        categorylbl.setForeground(Color.GRAY);
        categorylbl.setFont(new Font("Arial",Font.BOLD,15));
        overlayPanel.add(categorylbl);
        
        JComboBox<String>categories = new JComboBox<>();
		categories.setBackground(Color.WHITE);
		categories.setBounds(240, 320, 200, 30);
		overlayPanel.add(categories);
		
		 
		 try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
		    PreparedStatement stmt = Conn.prepareStatement("SELECT * FROM physical_books_category");
		    categories.addItem("");
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				categories.addItem(rs.getString("category"));
			}
				
		}catch(SQLException e) {
			e.printStackTrace();
		}
		 
		JButton addBookbtn = new JButton("Add Book");
		addBookbtn.setBounds(290, 400, 150, 30);
		addBookbtn.setBackground(Color.RED);
		addBookbtn.setForeground(Color.WHITE);
		overlayPanel.add(addBookbtn);
		addBookbtn.addActionListener(e->{
			if(titletxt.getText().isBlank()) {
        		JOptionPane.showMessageDialog(null, "Book Title is Blank","",JOptionPane.WARNING_MESSAGE);
        		
        	}else if(bookImgData==null) {
        		JOptionPane.showMessageDialog(null, "Select Image","",JOptionPane.WARNING_MESSAGE);
        		
        	}else if(authortxt.getText().isBlank()) {
        		JOptionPane.showMessageDialog(null, "Book Author is Blank","",JOptionPane.WARNING_MESSAGE);
        		
        	}else if(categories.getSelectedItem().toString().isBlank()) {
        		JOptionPane.showMessageDialog(null, "Select Category","",JOptionPane.WARNING_MESSAGE);
        		
        	}else if(checkIfBookExist(titletxt.getText(), authortxt.getText())) {
        		updateBookStock(Integer.parseInt(stocktxt.getText()), titletxt.getText(), authortxt.getText());
        		overlayDialog.dispose();
        		
        	}else if(stocktxt.getText().isBlank()) {
        		JOptionPane.showMessageDialog(null, "Book Stock is Blank","",JOptionPane.WARNING_MESSAGE);
        		
        	}else {
        		int stock = Integer.parseInt(stocktxt.getText());
        		addBook(titletxt.getText(), bookImgData, authortxt.getText(), categories.getSelectedItem().toString(), stock);
        		bookImgData = null;
        		overlayDialog.dispose();
        	}
		});
        
        overlayDialog.setSize(500,450);
	    overlayDialog.setLocationRelativeTo(null);
	    overlayDialog.setVisible(true);
	}
	
	void updateBookStock(int stock, String title, String author) {
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
			PreparedStatement stmt = Conn.prepareStatement("UPDATE physical_books SET stock = stock + ?, available = available + ? WHERE title=? AND author=?");
	    	stmt.setInt(1, stock);
	    	stmt.setInt(2, stock);
	    	stmt.setString(3, title);
	    	stmt.setString(4, author);
			stmt.executeUpdate();
			setPhysicalBooksToTable("All");
			JOptionPane.showMessageDialog(null, "Added Successful");
			
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    }
		
	}
	
	boolean checkIfBookExist(String title, String author) {
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
			PreparedStatement stmt = Conn.prepareStatement("SELECT * FROM physical_books WHERE title=? AND author=?");
			stmt.setString(1, title);
			stmt.setString(2, author);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				return true;
			}
			
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    }
		
		return false;
	}
	
	
	void addBook(String title, byte[] bookImgData, String author, String category, int stock) {
		try {
			int num = 0;
			Random random = new Random();
			do {
				num= random.nextInt(900000) + 100000;
				
			}while(checkIfBookIDBeenUsed(num));
			
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
	    	PreparedStatement stmt = Conn.prepareStatement("INSERT INTO physical_books (book_id, title, image, category, author, stock, available) VALUES(?,?,?,?,?,?,?)");
	    	stmt.setInt(1, num);
	    	stmt.setString(2, title);
	    	stmt.setBytes(3, bookImgData);
	    	stmt.setString(4, category);
	    	stmt.setString(5, author);
	    	stmt.setInt(6, stock);
	    	stmt.setInt(7, stock);
			stmt.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Book Added");
			
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    }
		
		//Refresh the table
		setPhysicalBooksToTable("All");
	}
	
	boolean checkIfBookIDBeenUsed(int bookID) {
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
			PreparedStatement stmt = Conn.prepareStatement("SELECT * FROM physical_books where book_ID=?");
			stmt.setInt(1, bookID);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				return true;
			}
			
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    }
		
		return false;
	}
	
	protected abstract void showBook(int bookID);
	
	protected abstract void hideBook();

}
