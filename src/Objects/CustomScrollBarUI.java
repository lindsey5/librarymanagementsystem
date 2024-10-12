package Objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class CustomScrollBarUI extends BasicScrollBarUI {
	Color thumbcolor, thumbColor, decreaseButtonColor, increaseButtonColor;
	
	public CustomScrollBarUI(Color thumbcolor,Color thumbColor, Color decreaseButtonColor, Color increaseButtonColor){
		this.thumbcolor=thumbcolor;
		this.thumbColor=thumbColor;
		this.decreaseButtonColor=decreaseButtonColor;
		this.increaseButtonColor=increaseButtonColor;
	}
	    

	    @Override
	    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
	        Graphics2D g2 = (Graphics2D) g;
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        g2.setPaint(thumbcolor);
	        g2.fillRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height);
	        g2.dispose();
	    }

	    @Override
	    protected void paintTrack(Graphics g, JComponent c, Rectangle thumbBounds) {
	        Graphics2D g2 = (Graphics2D) g.create();
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        g2.setPaint(thumbColor);
	        g2.fillRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height);
	        g2.dispose();
	    }
	    
	    @Override
	    protected JButton createDecreaseButton(int orientation) {
	        JButton decreaseButton = super.createDecreaseButton(orientation);
	        decreaseButton.setBackground(decreaseButtonColor);
	        return decreaseButton;
	    }

	    @Override
	    protected JButton createIncreaseButton(int orientation) {
	        JButton increaseButton = super.createIncreaseButton(orientation);
	        increaseButton.setBackground(increaseButtonColor);
	        return increaseButton;
	    }

	}