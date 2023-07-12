package main;

import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Login extends JFrame {

	private JPanel contentPane, panel_login, panel_account, panel_2;
	private JTextField textField;
	private JPasswordField passwordField;
	private static Socket s;
	private String filepass = null;
	private ThreadClient mythread;
	private static String tendn;
	static ChatHome chathome;
	private static JFrame login;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					login = new Login();
					login.setLocationRelativeTo(null);
					login.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		try {
			s = new Socket("localhost", 7777);
			mythread = new ThreadClient(s,this);
			mythread.start();
		} catch (Exception e) {
			// TODO: handle exception
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 840, 518);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 0, 826, 481);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		panel_login = new JPanel();
		panel_login.setBounds(0, 0, 826, 481);
		panel_1.add(panel_login);
		panel_login.setLayout(null);

		JLabel lblNamePage = new JLabel("Chat Application");
		lblNamePage.setForeground(new Color(0, 51, 102));
		lblNamePage.setFont(new Font("Monotype Corsiva", Font.PLAIN, 32));
		lblNamePage.setBounds(89, 54, 230, 37);
		panel_login.add(lblNamePage);

		textField = new JTextField();
		textField.setFont(new Font("Mongolian Baiti", Font.PLAIN, 20));
		textField.setBounds(542, 223, 186, 32);
		panel_login.add(textField);
		textField.setColumns(10);

		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Mongolian Baiti", Font.PLAIN, 20));
		lblName.setBounds(451, 228, 72, 23);
		panel_login.add(lblName);

		JLabel lblPass = new JLabel("Password");
		lblPass.setFont(new Font("Mongolian Baiti", Font.PLAIN, 20));
		lblPass.setBounds(451, 269, 94, 23);
		panel_login.add(lblPass);

		passwordField = new JPasswordField();
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tendn = textField.getText();
					String mk = passwordField.getText();
					mythread.doSendData("login", tendn, mk);
				}
				
			}
		});
		passwordField.setFont(new Font("Mongolian Baiti", Font.PLAIN, 20));
		passwordField.setBounds(542, 265, 186, 30);
		panel_login.add(passwordField);

		JLabel lblLogin = new JLabel("Login");
		lblLogin.setFont(new Font("Mongolian Baiti", Font.PLAIN, 32));
		lblLogin.setBounds(544, 172, 94, 37);
		panel_login.add(lblLogin);

		JLabel lblimg = new JLabel("");
		lblimg.setIcon(new ImageIcon(Login.class.getResource("/img/background.jpg")));
		lblimg.setBounds(58, 120, 256, 256);
		panel_login.add(lblimg);

		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(428, 153, 321, 221);
		panel_login.add(panel);
		panel.setLayout(null);

		JButton btnRegiter = new JButton("Sign up");
		btnRegiter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Signup signup = new Signup();
				signup.setLocationRelativeTo(null);
				signup.setVisible(true);				
			}
		});
		btnRegiter.setBounds(172, 165, 109, 32);
		btnRegiter.setFont(new Font("Mongolian Baiti", Font.PLAIN, 18));
		panel.add(btnRegiter);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tendn = textField.getText();
				String mk = passwordField.getText();
				mythread.doSendData("login", tendn, mk);

			}
		});
		btnLogin.setBounds(61, 165, 94, 32);
		panel.add(btnLogin);
		btnLogin.setFont(new Font("Mongolian Baiti", Font.PLAIN, 18));

	}
//----------- login thành công sẽ đọc hàm sendtendn để hiện giao diện chat
	public void getlogin(String message, String avt) {
		// TODO Auto-generated method stub
		if (message.equals("dang nhap thanh cong")) {
			login.setVisible(false);
			sendtendn(tendn,avt);

			try {
				s.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		JFrame frame = new JFrame("JOptionPane showMessageDialog example");
		JOptionPane.showMessageDialog(frame, message, "Thong bao", JOptionPane.INFORMATION_MESSAGE);
		
	}

	public static void sendtendn(String tendn, String avt) {
		
		chathome.gettendn(tendn,avt);
	}

}
