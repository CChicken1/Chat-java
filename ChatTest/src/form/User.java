package form;

import javax.swing.JPanel;

//import Database.ConnectDatabase;
import swing.ImageAvatar;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Color;
import net.miginfocom.swing.MigLayout;

public class User extends JPanel {
	
	private ImageAvatar imageAvatar;
	private JLabel lblNewLabel;

	/**
	 * Create the panel.
	 */
	public User(String user,String img) {
		setBackground(Color.WHITE);
		setLayout(new MigLayout("fillx, filly", "0[50]10[100]0", "0[50]0"));
		
		imageAvatar = new ImageAvatar();
		imageAvatar.setBorderSize(0);
		 imageAvatar.setImage(new ImageIcon(img));
		//imageAvatar.setImage(new ImageIcon(User.class.getResource("/img/avatar.png")));
		add(imageAvatar, "cell 0 0,grow");
		
		 lblNewLabel = new JLabel(user);
		add(lblNewLabel, "cell 1 0,grow");

		//loadAvatar(user);
	}

	public String getUser() {
		// TODO Auto-generated method stub
		String userout=lblNewLabel.getText();
		return userout ;
	}
	public Icon geticon(){
	 Icon img = imageAvatar.getImage();
		return img;
		
	}
	
//	public void loadAvatar(String user) {
//		// ImageIcon icon = (new ConnectDatabase()).getAvatar(user);
//		imageAvatar.setImage(icon);
//	}
}