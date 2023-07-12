package main;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import form.User;

import java.awt.Color;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDate;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import java.awt.Component;
import javax.swing.ScrollPaneConstants;
import java.awt.LayoutManager;
import net.miginfocom.swing.MigLayout;
import javax.swing.JList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Room extends JFrame {

	private JPanel contentPane;
	private JTextField groupName;
	private static ThreadClient mythread;
	private Socket s;
	static DefaultListModel listModel;
	private JButton btnNewButton_2;
	private JList list;
	 private String filepass ="F:\\btweb\\cuoiky\\img\\Facebook-Avatar_3.png";
	 private JLabel lbavt;
	
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Room frame = new Room();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public Room() {
		try {
			s = new Socket("localhost", 7777);
			mythread = new ThreadClient(s, this);
			mythread.start();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
           
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Tạo nhóm");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name =groupName.getText()+"(nhom)";
				mythread.doSendData("tao nhom",name,filepass);
				mythread.doSendData("danhsach");
			}
		});
		btnNewButton.setBounds(322, 63, 85, 30);
		contentPane.add(btnNewButton);
		
		groupName = new JTextField();
		groupName.setBounds(94, 23, 313, 30);
		contentPane.add(groupName);
		groupName.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Tên Nhóm");
		lblNewLabel.setBounds(10, 23, 74, 30);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton_1 = new JButton("cancel");
		btnNewButton_1.addActionListener(new ActionListener() {
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
		btnNewButton_1.setBounds(10, 223, 85, 30);
		contentPane.add(btnNewButton_1);
		listModel = new DefaultListModel<String>();
				
				btnNewButton_2 = new JButton("ảnh nhóm");
				btnNewButton_2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JFileChooser fileChooser = new JFileChooser();
						int result = fileChooser.showOpenDialog(null);
						if (result == JFileChooser.APPROVE_OPTION) {
							File selectedFile = fileChooser.getSelectedFile();
							filepass =selectedFile.getAbsolutePath();
							ImageIcon img = new ImageIcon(new ImageIcon(filepass).getImage().getScaledInstance(lbavt.getWidth(), lbavt.getHeight(), Image.SCALE_SMOOTH));
							lbavt.setIcon(img);
						}
					}
				});
				btnNewButton_2.setBounds(10, 185, 85, 30);
				contentPane.add(btnNewButton_2);
				JPanel panel_acount = new JPanel();
				panel_acount.setBounds(209, 110, 217, 130);
				contentPane.add(panel_acount);
				panel_acount.setLayout(null);
				listModel = new DefaultListModel<String>();
				list = new JList(listModel);
				list.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						String user= (String) list.getSelectedValue();
						String group =groupName.getText()+"(nhom)";
						int result1 = JOptionPane.showConfirmDialog(contentPane, "Bạn có chắc muốn thêm  :" + user,
								"Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						if (result1 == JOptionPane.YES_OPTION) {
							mythread.doSendData("userGroup", user,group,filepass);
						} else if (result1 == JOptionPane.NO_OPTION) {

						}
					}
				});
				list.setBounds(0, 0, 100, 100);
				listModel.addElement("(null)                         ");
				JScrollPane scrollPane = new JScrollPane(list);
				scrollPane.setBounds(0, 0, 217, 130);
				panel_acount.add(scrollPane);
				
				 lbavt = new JLabel("");
				lbavt.setBounds(20, 72, 114, 103);
				contentPane.add(lbavt);
	}

	public void getcreate(String tb) {
		JFrame frame = new JFrame("JOptionPane showMessageDialog example");
		JOptionPane.showMessageDialog(frame, tb, "Thong bao", JOptionPane.INFORMATION_MESSAGE);
		
	}

	public void getds(String name) {
		listModel.addElement(name);
	}
}
