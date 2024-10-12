package AdminPage;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import Objects.CustomScrollBarUI;
import Objects.SearchField;

public class AdminDigitalLibraryPage extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JPanel booksPanel;
	int booksPanelHeight;
	SearchField searchField;
	JButton searchbtn;
	JFrame frame;
	byte[] bookImgData = null, bookData = null;
	JComboBox<String> categories;
	
	public AdminDigitalLibraryPage(JFrame frame){
		this.frame = frame;
		
		categories = new JComboBox<>();
		categories.setBackground(Color.WHITE);
		categories.setBounds(550, 50, 200, 30);
		this.add(categories);
		
		 try {
				Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
		    	PreparedStatement stmt = Conn.prepareStatement("SELECT * FROM digital_books_category");
				ResultSet rs = stmt.executeQuery();
				categories.addItem("All");
				while(rs.next()) {
					categories.addItem(rs.getString("category"));
				}
				
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		categories.addActionListener(e->{
			
			if(categories.getSelectedItem()!=null) {
				String category = (String)categories.getSelectedItem();
				if(category.equals("All")) {
					showBooks("All");
				}else {
					showBooks(category);
				}
			}
			
		});
		
		
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
		booksPanel.setLayout(new FlowLayout(FlowLayout.LEFT,90,40));
		
		JScrollPane booksScrollPane = new JScrollPane();
		booksScrollPane.setBounds(70,150,1100,600);
		//booksScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		booksScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		booksScrollPane.getVerticalScrollBar().setUnitIncrement(10);
		booksScrollPane.getVerticalScrollBar().setBlockIncrement(100);
		booksScrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI(Color.GRAY, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.LIGHT_GRAY));
		booksScrollPane.setBorder(null);
		booksScrollPane.setViewportView(booksPanel);
		this.add(booksScrollPane);
		
		JButton addCategorybtn = new JButton("Add Category");
		addCategorybtn.setBounds(70, 800, 200, 30);
		addCategorybtn.setBackground(Color.RED);
		addCategorybtn.setForeground(Color.WHITE);
		addCategorybtn.addActionListener(e-> openAddCategoryDialog());
		this.add(addCategorybtn);
		
		JButton deleteCategorybtn = new JButton("Delete Category");
		deleteCategorybtn.setBounds(350, 800, 200, 30);
		deleteCategorybtn.setBackground(Color.LIGHT_GRAY);
		deleteCategorybtn.addActionListener(e-> openDeleteCategoryDialog());
		this.add(deleteCategorybtn);
		
		JButton addBookbtn = new JButton("Add Book");
		addBookbtn.setBounds(1370, 800, 200, 30);
		addBookbtn.setBackground(Color.RED);
		addBookbtn.setForeground(Color.WHITE);
		addBookbtn.addActionListener(e-> openAddBookDialog());
		this.add(addBookbtn);
		
		
		this.setLayout(null);
		
		showBooks("All");
		
		searchbtn.addActionListener(e->{
			 searchResults(searchField.getText());
			 searchField.setText("Search Book");
			 searchField.setForeground(Color.LIGHT_GRAY);
		});
	}
	
	class LabelMouseHandler extends MouseAdapter{
		JLabel label;
		int book_id;
		
		LabelMouseHandler(JLabel label, int book_id){
			this.label = label;
			this.book_id = book_id;
		}
		
			public void mouseClicked(MouseEvent e) {
				openBook(book_id);
			}
			
			@Override
            public void mouseEntered(MouseEvent e) {
				label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
            	label.setCursor(Cursor.getDefaultCursor());
        
            }
		
	}
	
	void searchResults(String searchStr) {
		booksPanel.removeAll();
		//booksPanel.revalidate();
		booksPanel.repaint();
		
		booksPanelHeight = 400;
		booksPanel.setPreferredSize(new Dimension(1020, booksPanelHeight));
		
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
			PreparedStatement stmt = Conn.prepareStatement("SELECT * FROM digital_books WHERE category LIKE ? OR title LIKE ? OR author LIKE ? ORDER BY title ASC");
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
				
				int book_id = rs.getInt("book_id");
				String book_title = rs.getString("title");
				String author = rs.getString("author");
				String book_category = rs.getString("category");
				byte[] bookImg = rs.getBytes("image");
				byte[] pdfData = rs.getBytes("book_data");
				
				JPanel panel = new JPanel();
				panel.setPreferredSize(new Dimension(250,350));
				//panel.setBackground(Color.WHITE);
				panel.setLayout(null);
				booksPanel.add(panel);
				
				JLabel bookImagelbl = new JLabel();
				bookImagelbl.setBounds(40,30, 170, 180);
				panel.add(bookImagelbl);
				bookImagelbl.addMouseListener(new LabelMouseHandler(bookImagelbl, book_id));
				
				ImageIcon imageIcon  = new ImageIcon(bookImg);
				Image bookImage = imageIcon.getImage().getScaledInstance(bookImagelbl.getWidth(), bookImagelbl.getHeight(), java.awt.Image.SCALE_AREA_AVERAGING);
				imageIcon = new ImageIcon(bookImage);
				bookImagelbl.setIcon(imageIcon);
				
				JLabel book_titlelbl = new JLabel(book_title);
				book_titlelbl.setBounds(0, 220, 250, 30);
				book_titlelbl.setHorizontalAlignment(SwingConstants.CENTER);
				book_titlelbl.setFont(new Font("Arial",Font.BOLD,15));
				panel.add(book_titlelbl);
				
				JButton editbtn = new JButton("Edit");
				editbtn.setBounds(40, 260, 170, 30);
				editbtn.setBackground(Color.RED);
				editbtn.setForeground(Color.WHITE);
				editbtn.addActionListener(e-> openEditBookDialog(book_title, author, book_category, bookImg, pdfData, book_id));
				panel.add(editbtn);
				
				JButton deletebtn = new JButton("Delete");
				deletebtn.setBounds(40, 300, 170, 30);
				deletebtn.setBackground(Color.LIGHT_GRAY);
				deletebtn.addActionListener(e->{
		        	 int result = JOptionPane.showConfirmDialog(null, "Do you want to delete?", "Confirmation", JOptionPane.YES_NO_OPTION);
		             if (result == JOptionPane.YES_OPTION) {
		            	deleteBook(book_id);
		             }
		        });
				panel.add(deletebtn);
			}
		}catch(SQLException e) {
	    	e.printStackTrace();
	    }
		
		int numColumns = 3;
		int panelHeight = 400;
		booksPanelHeight = (booksPanel.getComponentCount() / numColumns + 1) * panelHeight + 100;
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
	    		stmt = Conn.prepareStatement("SELECT * FROM digital_books ORDER BY title ASC");
	    	}else {
	    		stmt = Conn.prepareStatement("SELECT * FROM digital_books WHERE category=? ORDER BY title ASC");
	    		stmt.setString(1, category);
	    	}
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				int book_id = rs.getInt("book_id");
				String book_title = rs.getString("title");
				String author = rs.getString("author");
				String book_category = rs.getString("category");
				byte[] bookImg = rs.getBytes("image");
				byte[] pdfData = rs.getBytes("book_data");
				
				JPanel panel = new JPanel();
				panel.setPreferredSize(new Dimension(250,350));
				//panel.setBackground(Color.WHITE);
				panel.setLayout(null);
				booksPanel.add(panel);
				
				JLabel bookImagelbl = new JLabel();
				bookImagelbl.setBounds(40,30, 170, 180);
				panel.add(bookImagelbl);
				bookImagelbl.addMouseListener(new LabelMouseHandler(bookImagelbl, book_id));
				
				ImageIcon imageIcon  = new ImageIcon(bookImg);
				Image bookImage = imageIcon.getImage().getScaledInstance(bookImagelbl.getWidth(), bookImagelbl.getHeight(), java.awt.Image.SCALE_AREA_AVERAGING);
				imageIcon = new ImageIcon(bookImage);
				bookImagelbl.setIcon(imageIcon);
				
				JLabel book_titlelbl = new JLabel(book_title);
				book_titlelbl.setBounds(0, 220, 250, 30);
				book_titlelbl.setHorizontalAlignment(SwingConstants.CENTER);
				book_titlelbl.setFont(new Font("Arial",Font.BOLD,15));
				panel.add(book_titlelbl);
				
				JButton editbtn = new JButton("Edit");
				editbtn.setBounds(40, 260, 170, 30);
				editbtn.setBackground(Color.RED);
				editbtn.setForeground(Color.WHITE);
				editbtn.addActionListener(e-> openEditBookDialog(book_title, author, book_category, bookImg, pdfData, book_id));
				panel.add(editbtn);
				
				JButton deletebtn = new JButton("Delete");
				deletebtn.setBounds(40, 300, 170, 30);
				deletebtn.setBackground(Color.LIGHT_GRAY);
				deletebtn.addActionListener(e->{
		        	 int result = JOptionPane.showConfirmDialog(null, "Do you want to delete?", "Confirmation", JOptionPane.YES_NO_OPTION);
		             if (result == JOptionPane.YES_OPTION) {
		            	deleteBook(book_id);
		             }
		        });
				panel.add(deletebtn);
				
			}
			
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    }
		
		int numColumns = 3;
		int panelHeight = 400;
		booksPanelHeight = (booksPanel.getComponentCount() / numColumns + 1) * panelHeight + 70;
		booksPanel.setPreferredSize(new Dimension(booksPanel.getWidth(), booksPanelHeight));
	}
	
	
	void openBook(int book_id) {
		
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
			PreparedStatement stmt = Conn.prepareStatement("SELECT * FROM digital_books WHERE book_id=?");
			stmt.setInt(1, book_id);

			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				byte[] pdfContent = rs.getBytes("book_data");
                File tempFile = File.createTempFile("temp", ".pdf");
                
                try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                    fos.write(pdfContent);
                }
                
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(tempFile);
                }
			
			}
			
	    }catch(SQLException | IOException e) {
	    	e.printStackTrace();
	    }

	}
	
	void openEditBookDialog(String title, String author, String category, byte[] imageData, byte[] pdfData, int book_id) {
		bookImgData = imageData;
		bookData = pdfData;
		
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
        
    	ImageIcon bookIcon = new ImageIcon(imageData);
		Image bookImg = bookIcon.getImage().getScaledInstance(bookImagelbl.getWidth(), bookImagelbl.getHeight(), java.awt.Image.SCALE_AREA_AVERAGING);
		bookIcon = new ImageIcon(bookImg);
		bookImagelbl.setIcon(bookIcon);
        
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
        titletxt.setText(title);
        overlayPanel.add(titletxt);
        
        JLabel authorlbl = new JLabel("Author");
        authorlbl.setBounds(240, 150, 150, 30);
        authorlbl.setForeground(Color.GRAY);
        authorlbl.setFont(new Font("Arial",Font.BOLD,15));
        overlayPanel.add(authorlbl);
        
        JTextField authortxt = new JTextField();
        authortxt.setBounds(240, 180, 200, 30);
        authortxt.setText(author);
        overlayPanel.add(authortxt);
        
        JLabel categorylbl = new JLabel("Category");
        categorylbl.setBounds(240, 220, 150, 30);
        categorylbl.setForeground(Color.GRAY);
        categorylbl.setFont(new Font("Arial",Font.BOLD,15));
        overlayPanel.add(categorylbl);
        
        JComboBox<String>categories = new JComboBox<>();
		categories.setBackground(Color.WHITE);
		categories.setBounds(240, 250, 200, 30);
		setCategoryToComboBox(categories);
		categories.setSelectedItem(category);
		overlayPanel.add(categories);
		
		 JLabel selectFilelbl = new JLabel();
	        selectFilelbl.setBounds(40, 330, 150, 30);
	        selectFilelbl.setFont(new Font("Arial",Font.BOLD,12));
	        selectFilelbl.setForeground(Color.LIGHT_GRAY);
	        overlayPanel.add(selectFilelbl);
		
		 JButton selectFilebtn = new JButton("Select File");
	        selectFilebtn.setBounds(40, 360, 150, 20);
	        selectFilebtn.setBackground(Color.RED);
	        selectFilebtn.setForeground(Color.WHITE);
	        selectFilebtn.addActionListener(e->{
	        	JFileChooser filechooser = new JFileChooser();
				 filechooser.setFileFilter(new FileNameExtensionFilter("PDF files","pdf"));
				 int result = filechooser.showOpenDialog(null);
				 //System.out.println(result);
				 if(result != JFileChooser.APPROVE_OPTION) {
					return;
				 }
				 File selectedFile = filechooser.getSelectedFile();
				 String fileName = selectedFile.getName();
				 selectFilelbl.setText(fileName);
				 
				 try {
					 byte[] fileData = new byte[(int) selectedFile.length()];
					 FileInputStream inputStream = new FileInputStream(selectedFile);
					 inputStream.read(fileData);
					 inputStream.close();
					 
					 bookData = fileData;

				 }catch(IOException e1) {
					 e1.printStackTrace();
				 }
	        });
	   overlayPanel.add(selectFilebtn);
	        
	   JButton savebtn = new JButton("Save");
	   savebtn.setBounds(290, 330, 150, 30);
	   savebtn.setBackground(Color.RED);
	   savebtn.setForeground(Color.WHITE);
	   savebtn.addActionListener(e->{
		   if(titletxt.getText().isBlank() || authortxt.getText().isBlank()) {
			   JOptionPane.showMessageDialog(null, "Fill the Blanks", "", JOptionPane.WARNING_MESSAGE);
			   
		   }else if(categories.getSelectedItem().toString().isBlank()) {
			   JOptionPane.showMessageDialog(null, "Select Category", "", JOptionPane.WARNING_MESSAGE);
			   
		   }else if(titletxt.getText().length()>50) {
			   JOptionPane.showMessageDialog(null, "Book Title is Too Long", "", JOptionPane.WARNING_MESSAGE);
			   
		   }else if(authortxt.getText().length()>50) {
			   JOptionPane.showMessageDialog(null, "Book Author is Too Long", "", JOptionPane.WARNING_MESSAGE);
			   
		   }else {
			   editBook(titletxt.getText(), bookImgData, bookData, categories.getSelectedItem().toString(), authortxt.getText(), book_id);
			   overlayDialog.dispose();
		   }
	   });
	   overlayPanel.add(savebtn);
        
        overlayDialog.setSize(500,400);
	    overlayDialog.setLocationRelativeTo(null);
	    overlayDialog.setVisible(true);
	}
	
	void openAddBookDialog() {
		
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
        
        JLabel categorylbl = new JLabel("Category");
        categorylbl.setBounds(240, 220, 150, 30);
        categorylbl.setForeground(Color.GRAY);
        categorylbl.setFont(new Font("Arial",Font.BOLD,15));
        overlayPanel.add(categorylbl);
        
        JComboBox<String>categories = new JComboBox<>();
		categories.setBackground(Color.WHITE);
		categories.setBounds(240, 250, 200, 30);
		setCategoryToComboBox(categories);
		overlayPanel.add(categories);
		
		 JLabel selectFilelbl = new JLabel();
	        selectFilelbl.setBounds(40, 330, 150, 30);
	        selectFilelbl.setFont(new Font("Arial",Font.BOLD,12));
	        selectFilelbl.setForeground(Color.LIGHT_GRAY);
	        overlayPanel.add(selectFilelbl);
		
		 JButton selectFilebtn = new JButton("Select File");
	        selectFilebtn.setBounds(40, 360, 150, 20);
	        selectFilebtn.setBackground(Color.RED);
	        selectFilebtn.setForeground(Color.WHITE);
	        selectFilebtn.addActionListener(e->{
	        	JFileChooser filechooser = new JFileChooser();
				 filechooser.setFileFilter(new FileNameExtensionFilter("PDF files","pdf"));
				 int result = filechooser.showOpenDialog(null);
				 //System.out.println(result);
				 if(result != JFileChooser.APPROVE_OPTION) {
					return;
				 }
				 File selectedFile = filechooser.getSelectedFile();
				 String fileName = selectedFile.getName();
				 selectFilelbl.setText(fileName);
				 
				 try {
					 byte[] fileData = new byte[(int) selectedFile.length()];
					 FileInputStream inputStream = new FileInputStream(selectedFile);
					 inputStream.read(fileData);
					 inputStream.close();
					 
					 bookData = fileData;

				 }catch(IOException e1) {
					 e1.printStackTrace();
				 }
	        });
	   overlayPanel.add(selectFilebtn);
	        
	   JButton savebtn = new JButton("Save");
	   savebtn.setBounds(290, 330, 150, 30);
	   savebtn.setBackground(Color.RED);
	   savebtn.setForeground(Color.WHITE);
	   savebtn.addActionListener(e->{
		   if(titletxt.getText().isBlank() || authortxt.getText().isBlank()) {
			   JOptionPane.showMessageDialog(null, "Fill the Blanks", "", JOptionPane.WARNING_MESSAGE);
			   
		   }else if(categories.getSelectedItem().toString().isBlank()) {
			   JOptionPane.showMessageDialog(null, "Select Category", "", JOptionPane.WARNING_MESSAGE);
			   
		   }else if(bookImgData == null) {
			   JOptionPane.showMessageDialog(null, "Select Image", "", JOptionPane.WARNING_MESSAGE);
			   
		   }else if(bookData == null) {
			   JOptionPane.showMessageDialog(null, "Select File", "", JOptionPane.WARNING_MESSAGE);
			   
		   }else if(titletxt.getText().length()>50) {
			   JOptionPane.showMessageDialog(null, "Book Title is Too Long", "", JOptionPane.WARNING_MESSAGE);
			   
		   }else if(authortxt.getText().length()>50) {
			   JOptionPane.showMessageDialog(null, "Book Author is Too Long", "", JOptionPane.WARNING_MESSAGE);
			   
		   }else if(checkIfBookExist(titletxt.getText())) {
			   JOptionPane.showMessageDialog(null, "Book is Already Exist", "", JOptionPane.WARNING_MESSAGE);
			   
		   }else {
			   addBook(titletxt.getText(), bookImgData, bookData, authortxt.getText(), categories.getSelectedItem().toString());
			   bookImgData = null;
			   bookData = null;
			   overlayDialog.dispose();
		   }
	   });
	   overlayPanel.add(savebtn);
        
        overlayDialog.setSize(500,400);
	    overlayDialog.setLocationRelativeTo(null);
	    overlayDialog.setVisible(true);
	}
	
	void setCategoryToComboBox(JComboBox<String> comboBox) {
		 try {
				Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
		    	PreparedStatement stmt = Conn.prepareStatement("SELECT * FROM digital_books_category");
		    	comboBox.addItem("");
				ResultSet rs = stmt.executeQuery();
				while(rs.next()) {
					comboBox.addItem(rs.getString("category"));
				}
				
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	void addBook(String title, byte[] bookImgData, byte[] book_data, String author, String category) {
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
	    	PreparedStatement stmt = Conn.prepareStatement("INSERT INTO digital_books (title, image, book_data, category, author) VALUES(?,?,?,?,?)");
	    	stmt.setString(1, title);
	    	stmt.setBytes(2, bookImgData);
	    	stmt.setBytes(3, book_data);
	    	stmt.setString(4, category);
	    	stmt.setString(5, author);
			stmt.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Book Added");
			
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    }
		
		showBooks("All");
	}
	
	void editBook(String title, byte[] book_image, byte[] book_data, String category, String author, int book_id) {
		
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
	    	PreparedStatement stmt = Conn.prepareStatement("UPDATE digital_books SET title=?, image=?, category=?, author=?, book_data=? WHERE book_id=?");
	    	stmt.setString(1, title);
	    	stmt.setBytes(2, book_image);
	    	stmt.setString(3, category);
	    	stmt.setString(4, author);
	    	stmt.setBytes(5, book_data);
	    	stmt.setInt(6, book_id);
			stmt.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Edit Successful");
			showBooks("All");
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    }
	}
	
	void deleteBook(int bookID) {
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
	    	PreparedStatement stmt = Conn.prepareStatement("DELETE FROM digital_books WHERE book_id=?");
	    	stmt.setInt(1, bookID);
			stmt.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Book Successfully Deleted");
			showBooks("All");
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    }
	}
	
	boolean checkIfBookExist(String book_title) {
		 try {
				Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
		    	PreparedStatement stmt = Conn.prepareStatement("SELECT * FROM digital_books WHERE title=?");
		    	stmt.setString(1, book_title);
				ResultSet rs = stmt.executeQuery();
				if(rs.next()) {
					return true;
				}
				
		}catch(SQLException e) {
			e.printStackTrace();
		}
		 
		 return false;
	}
	
	void openAddCategoryDialog() {
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
	    	PreparedStatement stmt = Conn.prepareStatement("INSERT INTO digital_books_category (category) VALUES(?)");
	    	stmt.setString(1, category);
			stmt.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Category Successfull Added");
			
			stmt = Conn.prepareStatement("SELECT * FROM digital_books_category");
			ResultSet rs = stmt.executeQuery();
			categories.removeAllItems();
			categories.addItem("All");
			while(rs.next()) {
				categories.addItem(rs.getString("category"));
			}
			
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    }
	}
	
	boolean checkIfCategoryExist(String category) {
		try {
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagementdb","root","");
	    	PreparedStatement stmt = Conn.prepareStatement("SELECT * FROM digital_books_category WHERE category=?");
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
	
	void openDeleteCategoryDialog() {
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
	    	PreparedStatement stmt = Conn.prepareStatement("SELECT * FROM digital_books_category");
	    	
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
	    	PreparedStatement stmt = Conn.prepareStatement("DELETE FROM digital_books_category WHERE category=?");
	    	stmt.setString(1, category);
			stmt.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Category Successfully Deleted");
			
			stmt = Conn.prepareStatement("SELECT * FROM digital_books_category");
			ResultSet rs = stmt.executeQuery();
			categories.removeAllItems();
			categories.addItem("All");
			while(rs.next()) {
				categories.addItem(rs.getString("category"));
			}
			
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    }
	}

}
