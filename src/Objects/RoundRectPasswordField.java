package Objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

public class RoundRectPasswordField extends JPasswordField implements FocusListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Color color = Color.BLACK;
	
	public RoundRectPasswordField(){
		addFocusListener(this);
		setOpaque(false);
		setBorder(null);
		setHorizontalAlignment(SwingConstants.CENTER);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);   
		g2.setPaint(getBackground());
	    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
	    g2.setPaint(color);
	    g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);
	    super.paintComponent(g2);
	}

	@Override
	public void focusGained(FocusEvent e) {
		color = Color.RED;
		repaint();
	}

	@Override
	public void focusLost(FocusEvent e) {
		color = Color.BLACK;
		repaint();
	}

}
