package Objects;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class SearchField extends JTextField implements FocusListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String text;
	
	public SearchField(String text) {
		super(text);
		this.text = text;
		this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		this.setForeground(Color.LIGHT_GRAY);
		this.addFocusListener(this);
		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.setBackground(Color.WHITE);
		this.setFont(new Font("Callibri",Font.BOLD,13));
	}

	@Override
	public void focusGained(FocusEvent e) {
		if(getText().equals(text)) {
			setText("");
			setForeground(Color.BLACK);
		}
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		if(getText().isBlank()) {
			setText(text);
			setForeground(Color.LIGHT_GRAY);
		}
		
	}
	
}