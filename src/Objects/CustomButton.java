package Objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;

public class CustomButton extends JButton implements MouseListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	BufferedImage image;
	
	public CustomButton(String text, BufferedImage image){
		super(text);
		this.image = image;
		this.setBorder(null);
		this.setFont(new Font("Arial",Font.BOLD,20));
		this.setContentAreaFilled(false);
		this.addMouseListener(this);
		this.setBackground(Color.WHITE);
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setPaint(getBackground());
		g2.fillRect(0, 0, getWidth(), getHeight());
		g2.setPaint(getForeground());
		g2.setFont(getFont());
		g2.drawString(getText(), 60, 30);
		g2.drawImage(image, 20, 10, 25, 25, null);
		g2.dispose();
	}


	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {
		setBackground(Color.WHITE);
		this.repaint();
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		setBackground(new Color(237,237,237));
		this.repaint();
	}
	
}

