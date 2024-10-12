package Objects;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JPasswordField;

public class ItemEventHandler implements ItemListener {
	JCheckBox checkbox;
	JPasswordField passwordField;
	
	public ItemEventHandler(JCheckBox checkbox,JPasswordField passwordField) {
		this.checkbox=checkbox;
		this.passwordField=passwordField;
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		if(checkbox.isSelected()) {
			passwordField.setEchoChar((char) 0);
			checkbox.setText("Hide");
		}else {
			passwordField.setEchoChar('•');
			checkbox.setText("Show");
		}	
	}
	
}
