package form;

import javax.swing.JPanel;
import swing.JIMSendTextPane;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;

public class ChatPanel extends JPanel {
	
	private JIMSendTextPane txt;

	/**
	 * Create the panel.
	 */
	public ChatPanel() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		txt = new JIMSendTextPane();
		txt.setForeground(Color.WHITE);
		txt.setBackground(new Color(163, 110, 229));
		txt.setEditable(false);
		txt.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		txt.setSelectionColor(new Color(92, 188, 255));
		add(txt);
		

	}
	
	public void setText(String text) {
		txt.setText(text);
	}
	
	public void setUserProfile(String user) {
        JLayeredPane layer = new JLayeredPane();
        layer.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        layer.setBorder(new EmptyBorder(10, 10, 0, 10));
        JButton cmd = new JButton(user);
        cmd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmd.setBorder(null);
        cmd.setContentAreaFilled(false);
        cmd.setFocusable(false);
        cmd.setForeground(Color.CYAN);
        cmd.setFont(new java.awt.Font("sansserif", 1, 13));
        txt.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        layer.add(cmd);
        add(layer, 0);
    }
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        super.paintComponent(g);
	}
}