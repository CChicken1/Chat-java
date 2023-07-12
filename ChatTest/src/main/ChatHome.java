package main;

import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Component;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.Icon;

import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

//import Database.ConnectDatabase;
//import XML.FromXML;
//import XML.ToXML;
import form.ChatLeft;
import form.ChatLeftAvatar;
import form.ChatRight;
import form.User;

import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;

import swing.ImageAvatar;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import net.miginfocom.swing.MigLayout;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;

public class ChatHome {

	
	private JTextArea txtMess;
	private static JFrame frame;
	private static ImageAvatar imageAvatar;
	private static JPanel pnChat2;
	private static JPanel pnList;
	private Socket client;

	private static JLabel lblNewLabel;
	private static ThreadClient mythread;
	private Socket s;
	private static JScrollPane scrollPane;
	static DefaultListModel listModel;
	public static JFrame window;
	private String filepass = null;
	String day;
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ChatHome window = new ChatHome("User");
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	public ChatHome(String user) {
		try {
			s = new Socket("localhost", 7777);
			mythread = new ThreadClient(s, this);
			mythread.start();
		} catch (Exception e) {
			// TODO: handle exception
		}

		initialize(user);

		// loadUser(user);
		// loadAvatar(user);
		// connectToServer();
		// receiveMessages(user);
	}

//	private void connectToServer() {
//		try {
//			client = new Socket("localhost", 1107);
//			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
//			out = new PrintWriter(client.getOutputStream(), true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

//	private void receiveMessages(String user) {
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					String line;
//					while ((line = in.readLine()) != null) {
//						//FromXML fromXML = new FromXML(line);
//						//addReceiveMess(fromXML.getMess(), fromXML.getSender());
//						loadUser(user);
//						pnList.validate();
//						pnList.repaint();
//					}
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}).start();
//	}

	private void initialize(String user) {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.setBounds(100, 100, 994, 600);
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {

				try {
					mythread.doSendData("out", user);
					s.close();
					System.exit(0);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		frame.setTitle(user);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);

		JPanel pnListUser = new JPanel();
		pnListUser.setBounds(6, 0, 196, 563);
		pnListUser.setBackground(Color.WHITE);
		frame.getContentPane().add(pnListUser);
		pnListUser.setLayout(new BorderLayout(0, 0));
		listModel = new DefaultListModel<String>();

		pnList = new JPanel(new MigLayout("wrap"));
		pnList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				pnChat2.removeAll();
				pnChat2.revalidate();
				pnChat2.repaint();
				JPanel clickedPanel = (JPanel) e.getSource();
				Component clickedComponent = clickedPanel.getComponentAt(e.getX(), e.getY());
				String userName = ((User) clickedComponent).getUser();
				Icon img = ((User) clickedComponent).geticon();

				lblNewLabel.setText(userName);
				BufferedImage bi = new BufferedImage(img.getIconWidth(), img.getIconHeight(),
						BufferedImage.TYPE_INT_ARGB);
				Graphics g = bi.createGraphics();
				img.paintIcon(null, g, 0, 0);
				g.dispose();

				// Tính toán tỉ lệ giữa kích thước của nh và kích thước của JLabel
				double scaleX = (double) lblNewLabel.getWidth() / bi.getWidth();
				double scaleY = (double) lblNewLabel.getHeight() / bi.getHeight();
				double scale = Math.min(scaleX, scaleY);

				// Tạo ảnh mới với kích thước phù hợp và gán lại vào JLabel
				int newWidth = (int) (bi.getWidth() * scale);
				int newHeight = (int) (bi.getHeight() * scale);
				Image scaledImage = bi.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
				Icon scaledIcon = new ImageIcon(scaledImage);
				lblNewLabel.setIcon(scaledIcon);

				mythread.doSendData("history", userName, user);
			}
		});
		pnList.setBackground(new Color(255, 255, 255));

		scrollPane = new JScrollPane(pnList);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		pnListUser.add(scrollPane, BorderLayout.CENTER);

		JPanel pnAvatar = new JPanel();
		pnAvatar.setBounds(798, 0, 182, 563);
		pnAvatar.setBorder(new LineBorder(new Color(255, 175, 175), 4));
		pnAvatar.setLayout(null);
		pnAvatar.setBackground(Color.WHITE);
		frame.getContentPane().add(pnAvatar);

		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int result = fileChooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					filepass = selectedFile.getAbsolutePath();
					String filename=selectedFile.getName();
					int result1 = JOptionPane.showConfirmDialog(frame, "Bạn có chắc muốn gưi file :" + filepass,
							"Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (result1 == JOptionPane.YES_OPTION) {
						LocalDate date=	java.time.LocalDate.now();
						 day=" "+ date;	
						addSendMess(filename,day);
						try {
							String userReceive = lblNewLabel.getText();

							Socket socket = new Socket("localhost", 7777);
							SendFile send = new SendFile(socket, filepass, userReceive, user);
							Thread sd = new Thread(send);
							sd.start();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} else if (result1 == JOptionPane.NO_OPTION) {

					}

				}
			}
		});
		btnNewButton.setIcon(new ImageIcon(ChatHome.class.getResource("/img/editAvatar.png")));
		btnNewButton.setBounds(22, 370, 40, 40);
		pnAvatar.add(btnNewButton);

		imageAvatar = new ImageAvatar();
		imageAvatar.setBorderSize(0);
		// imageAvatar.setImage(new
		// ImageIcon(ChatHome.class.getResource("/img/avatar.png")));
		imageAvatar.setBounds(6, 170, 167, 172);
		pnAvatar.add(imageAvatar);
		
		JButton btnNewButton_2 = new JButton("nhom");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Room frame = new Room();
			    frame.setVisible(true);
			}
		});
		btnNewButton_2.setBounds(72, 370, 40, 40);
		pnAvatar.add(btnNewButton_2);

		JPanel pnChat = new JPanel();
		pnChat.setBounds(202, 48, 600, 512);
		pnChat.setBackground(new Color(238, 237, 238));
		frame.getContentPane().add(pnChat);
		pnChat.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(null);
		panel.setBounds(0, 472, 590, 40);
		pnChat.add(panel);
		panel.setLayout(null);

		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msg = txtMess.getText();
				if (msg.length() > 0 && msg != null) {
					// out.println((new ToXML()).toXML(user, msg));
					txtMess.setText("");
				LocalDate date=	java.time.LocalDate.now();
				 day=" "+ date;					
					addSendMess(msg,day);
				}
			}
		});
		
		  InputMap inputMap = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
	        ActionMap actionMap = panel.getActionMap();
	 
	        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enter");
	        actionMap.put("enter", new AbstractAction() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	btnNewButton_1.doClick();
	            }
	        });
	        
		btnNewButton_1.setIcon(new ImageIcon(ChatHome.class.getResource("/img/send.png")));
		btnNewButton_1.setBounds(550, 0, 40, 40);
		panel.add(btnNewButton_1);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setBounds(0, 0, 523, 40);
		panel.add(scrollPane_1);

		txtMess = new JTextArea();
		txtMess.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String msg = txtMess.getText();
					if (msg.length() > 0 && msg != null) {
						// out.println((new ToXML()).toXML(user, msg));
						txtMess.setText("");
					LocalDate date=	java.time.LocalDate.now();
					 day=" "+ date;					
						addSendMess(msg,day);
					}
				}
			}
		});
		txtMess.setFont(new Font("Monospaced", Font.PLAIN, 14));
		scrollPane_1.setViewportView(txtMess);
		txtMess.setLineWrap(true);
		txtMess.setWrapStyleWord(true);

		JScrollPane scChat = new JScrollPane();
		scChat.setBounds(0, 0, 596, 470);
		pnChat.add(scChat);

		pnChat2 = new JPanel();
		pnChat2.setBorder(new LineBorder(new Color(255, 175, 175), 4));
		pnChat2.setBackground(Color.WHITE);
		scChat.setViewportView(pnChat2);
		pnChat2.setLayout(new MigLayout("fillx", "", "5[]5"));

		lblNewLabel = new JLabel("");
		lblNewLabel.setBackground(new Color(255, 255, 255));
		lblNewLabel.setBounds(202, 0, 594, 48);
		frame.getContentPane().add(lblNewLabel);

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}


//--------------hiện tin nhắn bên phải 
	public void addSendMess(String text, String day) {
		String usersend = frame.getTitle();
		String userReceive = lblNewLabel.getText();
		ChatRight item = new ChatRight();
		item.setText(text);
		// item.setimg(new
		// ImageIcon(ChatLeftAvatar.class.getResource("/img/avatar.png")));
		pnChat2.add(item, "wrap, al right, w ::80%");
		pnChat2.repaint();
		pnChat2.revalidate();
		
		mythread.doSendData("SendPM", text, userReceive, usersend,day);
	}
//----------------- hiện danh sách người dùng
	public void getonline(String message, String img) {

		Component[] components = pnList.getComponents();
		for (Component c : components) {
			if (c instanceof User && ((User) c).getUser().equals(message)) {
				pnList.remove(c);
				break;
			}
		}

		pnList.add(new User(message, img));

	}
//----------------- hiện tin nhắn bên trái 
	public void getmess(String gui, String message, String avt) {
		// TODO Auto-generated method stub
		String userReceive = lblNewLabel.getText();
		if (gui.equals(userReceive)) {
			ChatLeftAvatar item = new ChatLeftAvatar(gui, avt);
			item.setText(gui + "\n" +  message);
			// item.setimg(new ImageIcon(ChatLeftAvatar.class.getResource(message)));
			// item.setAvatar(gui);
			pnChat2.add(item, "wrap, w ::80%");
			pnChat2.repaint();
			pnChat2.validate();
		}
	}
//"/img/avatar.png"
//------------------hiện giao diện chat khi login thành công
	public static void gettendn(String tendn, String avt) {
		ChatHome window = new ChatHome(tendn);
		window.frame.setVisible(true);
		 imageAvatar.setImage(new ImageIcon(avt));
		mythread.doSendData("online", tendn);

	}
//----------------hiện lịch sử tin nhắn 
	public void gethistorymess(String gui, String message, String avt) {
		// TODO Auto-generated method stub
		String usersend = frame.getTitle();
		if (gui.equals(usersend)) {
			ChatRight item = new ChatRight();
			item.setText(message);
			// item.setimg(new
			// ImageIcon(ChatLeftAvatar.class.getResource("/img/avatar.png")));
			pnChat2.add(item, "wrap, al right, w ::80%");
			pnChat2.repaint();
			pnChat2.revalidate();
		} else {
			ChatLeftAvatar item = new ChatLeftAvatar(gui, avt);
			item.setText(gui + "\n" +  message);
			// item.setAvatar(gui);
			pnChat2.add(item, "wrap, w ::80%");
			pnChat2.repaint();
			pnChat2.validate();
		}
	}
//--------------- nhận file 
//	public void getfile(String file, String user, String nhan) {
//		// TODO Auto-generated method stub
//
//		int result1 = JOptionPane.showConfirmDialog(frame, user + " muốn gưi file :" + file, "Xác nhận",
//				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
//		if (result1 == JOptionPane.YES_OPTION) {
//
//			try {
//				Socket sk = new Socket("localhost", 7777);
//				RecivedFile rc = new RecivedFile(sk, file);
//				Thread t1 = new Thread(rc);
//				t1.start();
//			} catch (UnknownHostException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//		} else if (result1 == JOptionPane.NO_OPTION) {
//
//		}
//	}

	public void getGroup(String groupName, String aVTGroup) {
		pnList.add(new User(groupName,aVTGroup));
		
	}

	public void getmessGroup(String gui, String message, String avt, String nhan) {
		String userReceive = lblNewLabel.getText();
		if (nhan.equals(userReceive)) {
			ChatLeftAvatar item = new ChatLeftAvatar(gui, avt);
			item.setText( gui + "\n" +  message);
			System.out.println(gui+"\n" +message);
			// item.setimg(new ImageIcon(ChatLeftAvatar.class.getResource(message)));
			// item.setAvatar(gui);
			pnChat2.add(item, "wrap, w ::80%");
			pnChat2.repaint();
			pnChat2.validate();
			
		}
		
	}
}