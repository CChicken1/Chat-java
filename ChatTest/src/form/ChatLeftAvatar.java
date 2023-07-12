package form;

import javax.swing.Icon;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

// import Database.ConnectDatabase;
import swing.ImageAvatar;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ChatLeftAvatar extends JLayeredPane {
	
	private JLayeredPane jLayeredPane1;
	private ImageAvatar img;
	private ChatPanel txt;

	/**
	 * Create the panel.
	 */
	public ChatLeftAvatar(String user,String avt) {
		
		jLayeredPane1 = new JLayeredPane();
		jLayeredPane1.setForeground(Color.CYAN);
		jLayeredPane1.setBackground(new Color(163, 110, 229));
		img = new ImageAvatar();
		txt = new ChatPanel();
		txt.setBackground(new Color(163, 110, 229));
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		
		img = new ImageAvatar();
		 img.setImage(new ImageIcon(avt));
		img.setBorderSize(0);
		img.setMaximumSize(new Dimension(31, 31));
		img.setMinimumSize(new Dimension(31, 31));
		img.setPreferredSize(new Dimension(31, 31));
		
		add(img);
		
		jLayeredPane1.setLayer(img, javax.swing.JLayeredPane.DEFAULT_LAYER);
		
		javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
		jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addComponent(img, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(img, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
       
        add(jLayeredPane1);
        add(txt);

	}
	
	public void setText(String text) {
		txt.setText(text);
	}
	
//	public void setAvatar(String user) {
//		txt.setUserProfile(user);
//	}
//
//	public void setImageProfile(Icon image) {
//		img.setImage(image);
//    }

//	public void setimg(Icon img) {
//		// TODO Auto-generated method stub
//		JLabel im=new JLabel();
//		im.setIcon(img);
//        txt.add(im);
//	}
	
}