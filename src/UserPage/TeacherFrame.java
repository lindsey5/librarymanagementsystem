package UserPage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import LoginPage.TeacherLoginPage;
import Objects.CustomButton;

public class TeacherFrame extends UserFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private TeacherSettingsPage settingsPage;
	
	public TeacherFrame(Teacher teacher) {
		super("Teacher", String.valueOf(teacher.getID()));
		
		settingsPage = new TeacherSettingsPage(teacher, this);
		cardPanel.add(settingsPage, "settings");
	}
	
	@Override
	protected void makeLeftPanel() {
		super.makeLeftPanel();
		try {
			BufferedImage settingsImg  =  ImageIO.read(new File("res/settings.png"));
			CustomButton settingsbtn = new CustomButton("Settings",settingsImg);
			settingsbtn.setBackground(new Color(237,237,237));
			settingsbtn.setBorder(null);
			settingsbtn.setPreferredSize(new Dimension(249,50));
			settingsbtn.addActionListener(e-> { showPanel("settings");});
			leftPanel.add(settingsbtn);
			
			BufferedImage logoutImg  =  ImageIO.read(new File("res/logout.png"));
			CustomButton logoutbtn = new CustomButton("Log out",logoutImg);
			logoutbtn.setBackground(new Color(237,237,237));
			logoutbtn.setBorder(null);
			logoutbtn.setPreferredSize(new Dimension(249,50));
			logoutbtn.addActionListener(e-> {
				new TeacherLoginPage();
				this.dispose();
			});
			leftPanel.add(logoutbtn);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
