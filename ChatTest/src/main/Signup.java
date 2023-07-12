package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Signup extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
    private Socket s = null;
    private ThreadClient mythread;
    private String filepass = "F:\\btweb\\cuoiky\\img\\Facebook-Avatar_3.png" ;
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Signup signup = new Signup();
//					signup.setLocationRelativeTo(null);
//					signup.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public Signup() {
		 try {
			s= new Socket("localhost",7777);
			mythread = new ThreadClient(s,this);
			mythread.start();
		} catch (Exception e) {
			// TODO: handle exception
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 875, 510);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNamePage = new JLabel("Chat Application");
		lblNamePage.setBounds(85, 60, 230, 37);
		lblNamePage.setForeground(new Color(0, 51, 102));
		lblNamePage.setFont(new Font("Monotype Corsiva", Font.PLAIN, 32));
		contentPane.add(lblNamePage);
		
		JPanel panel = new JPanel();
		panel.setBounds(410, 132, 408, 271);
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblRegister = new JLabel("Register");
		lblRegister.setBounds(144, 7, 107, 37);
		panel.add(lblRegister);
		lblRegister.setFont(new Font("Mongolian Baiti", Font.PLAIN, 32));
		
		JLabel lblNewLabel_1 = new JLabel("Name");
		lblNewLabel_1.setBounds(17, 57, 72, 23);
		panel.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("Mongolian Baiti", Font.PLAIN, 20));
		
		textField = new JTextField();
		textField.setBounds(179, 52, 199, 32);
		panel.add(textField);
		textField.setFont(new Font("Mongolian Baiti", Font.PLAIN, 20));
		textField.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Password");
		lblNewLabel_1_1.setBounds(16, 104, 94, 23);
		panel.add(lblNewLabel_1_1);
		lblNewLabel_1_1.setFont(new Font("Mongolian Baiti", Font.PLAIN, 20));
		
		passwordField = new JPasswordField();
		passwordField.setBounds(179, 98, 199, 30);
		panel.add(passwordField);
		passwordField.setFont(new Font("Mongolian Baiti", Font.PLAIN, 20));
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Re-enter Password");
		lblNewLabel_1_1_1.setBounds(15, 149, 169, 23);
		panel.add(lblNewLabel_1_1_1);
		lblNewLabel_1_1_1.setFont(new Font("Mongolian Baiti", Font.PLAIN, 20));
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(179, 149, 199, 30);
		panel.add(passwordField_1);
		passwordField_1.setFont(new Font("Mongolian Baiti", Font.PLAIN, 20));
		
		JButton btnCreatNewAccont = new JButton("Create new accont");
		btnCreatNewAccont.setBounds(46, 215, 199, 32);
		panel.add(btnCreatNewAccont);
		btnCreatNewAccont.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dn = textField .getText();
				String mk1 =	passwordField.getText();
				String mk2 =passwordField_1.getText();
				
				mythread.doSendData("tao tai khoan", dn ,mk1,mk2,filepass);
				
				
			}
			
		});
		btnCreatNewAccont.setFont(new Font("Mongolian Baiti", Font.PLAIN, 18));
		
		JButton btnNewButton = new JButton("Cancel");
		btnNewButton.setFont(new Font("Mongolian Baiti", Font.PLAIN, 18));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				
				try {
					s.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(272, 215, 99, 32);
		panel.add(btnNewButton);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("Show");
		chckbxNewCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxNewCheckBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(chckbxNewCheckBox.isSelected()) {
					passwordField.setEchoChar((char)0);
					 passwordField_1.setEchoChar((char)0);
				} else {
					passwordField.setEchoChar('*');
					 passwordField_1.setEchoChar('*');
				}
			
			}
		});
		chckbxNewCheckBox.setBounds(179, 185, 137, 21);
		panel.add(chckbxNewCheckBox);
		
		JLabel lblNewLabel = new JLabel("");
		//lblNewLabel.setIcon(new ImageIcon(Signup.class.getResource("/img/chat (2).png")));
		lblNewLabel.setBounds(63, 119, 272, 230);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton_1 = new JButton("Avatar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int result = fileChooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					filepass =selectedFile.getAbsolutePath();
					ImageIcon img = new ImageIcon(new ImageIcon(filepass).getImage().getScaledInstance(lblNewLabel.getWidth(), lblNewLabel.getHeight(), Image.SCALE_SMOOTH));
					lblNewLabel.setIcon(img);
				}
			}
		});
		btnNewButton_1.setBounds(140, 372, 95, 31);
		contentPane.add(btnNewButton_1);
	}

	public  void getaccount(String tb) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("JOptionPane showMessageDialog example");
		JOptionPane.showMessageDialog(frame, tb, "Thong bao", JOptionPane.INFORMATION_MESSAGE);
	}

	
}
