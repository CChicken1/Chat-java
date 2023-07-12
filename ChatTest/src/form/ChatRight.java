package form;

import java.awt.Color;
import java.awt.Image;
import java.awt.Label;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChatRight extends JPanel {
	
	private ChatPanel txt;

	/**
	 * Create the panel.
	 */
	public ChatRight() {
		txt = new ChatPanel();
		txt.setBackground(new Color(163, 110, 229));
		add(txt);
		
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
		
	}
	
	public void setText(String text) {
        txt.setText(text);
    }
//	public void setimg(Icon img) {
//		JLabel im=new JLabel();
//		im.setIcon(img);
//        txt.add(im);
//    }

}