package form;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class ChatLeft extends JPanel {
	
	private ChatPanel txt;

	/**
	 * Create the panel.
	 */
	public ChatLeft() {
		setBackground(new Color(163, 110, 229));
		
		txt = new ChatPanel();
		txt.setBackground(Color.BLUE);
		txt.setBounds(7, 6, 21, 36);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addComponent(txt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	        );
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(groupLayout.createSequentialGroup()
	                .addComponent(txt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                .addGap(0, 0, 0))
	        );
		setLayout(groupLayout);

	}
	
	public void setText(String text) {
		txt.setText(text);
	}
}