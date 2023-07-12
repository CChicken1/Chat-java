package serverchat;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import serverchat.Chatter;

import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;

public class Seerver extends JFrame {

	private JPanel contentPane;
	private JTextField txtServerChayVoi;
	static ClientConect server;
	private JScrollPane scrollPane;
	private JPanel panel2;
	private static JTextArea txtMessage;
	JButton btnNewButton, btnNewButton_1;
	private JPanel panel;
	private JLabel lblNewLabel;
	private static JLabel onoff, lblUserOnline;
	static ThreadClassServer server1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Seerver frame = new Seerver();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

//hien danh sach arraylist len gd server
	static void displayUser() {
		txtMessage.setText("");
		int i = 0;

		for (Chatter ten : ThreadClassServer.list) {
			txtMessage.append((i + 1) + " " + ten.getName() + "\n");
			i++;
		}

	}

	/**
	 * Create the frame.
	 */
	public Seerver() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnNewButton = new JButton("start");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					server = new ClientConect();
					txtServerChayVoi.setText("server dang chay vơi cong 8888");
					onoff.setText("<html><font color='green'>RUNNING...</font></html>");
					btnNewButton.setEnabled(false);
					btnNewButton_1.setEnabled(true);

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(325, 219, 85, 21);
		contentPane.add(btnNewButton);

		btnNewButton_1 = new JButton("stop");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblUserOnline.setText("0");
				try {
					server.stopserver();
					txtServerChayVoi.setText("server đa đong ");
					onoff.setText("<html><font color='red'>OFF</font></html>");
					btnNewButton.setEnabled(true);
					btnNewButton_1.setEnabled(false);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_1.setBounds(218, 219, 85, 21);
		contentPane.add(btnNewButton_1);
		txtMessage = new JTextArea();
		txtMessage.setBackground(new Color(64, 0, 64));
		txtMessage.setForeground(Color.WHITE);
		txtMessage.setFont(new Font("Courier New", Font.PLAIN, 18));

		panel2 = new JPanel();
		panel2.setBackground(new Color(240, 240, 240));
		panel2.setBounds(22, 79, 379, 140);
		panel2.setLayout(new GridLayout(0, 1, 0, 0));
		scrollPane = new JScrollPane(txtMessage);

		panel2.add(scrollPane);
		contentPane.add(panel2);

		panel = new JPanel();
		panel.setBackground(new Color(128, 255, 255));
		panel.setBounds(22, 10, 388, 59);
		contentPane.add(panel);
		panel.setLayout(null);

		lblNewLabel = new JLabel("STAtUS");
		lblNewLabel.setBounds(10, 10, 89, 13);
		panel.add(lblNewLabel);

		onoff = new JLabel("off");
		onoff.setBounds(148, 6, 71, 20);
		panel.add(onoff);

		txtServerChayVoi = new JTextField();
		txtServerChayVoi.setBounds(10, 33, 182, 19);
		panel.add(txtServerChayVoi);
		txtServerChayVoi.setColumns(10);
		txtServerChayVoi.setEditable(false);

		lblUserOnline = new JLabel("0");
		lblUserOnline.setBounds(245, 33, 133, 13);
		panel.add(lblUserOnline);

		JButton btnNewButton_2 = new JButton("load");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayUser();
			}
		});
		btnNewButton_2.setBounds(115, 219, 85, 21);
		contentPane.add(btnNewButton_2);

	}

}
