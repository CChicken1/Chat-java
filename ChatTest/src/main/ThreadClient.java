package main;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.StringTokenizer;

public class ThreadClient extends Thread {
	static final String sepa = "###";
	Socket socket;
	DataInputStream dis;
	DataOutputStream dos;
	StringTokenizer st;
	String client;
	private Login frmLogin ;
	private Signup frmtaotaikhoan;
	private ChatHome chat;
	private Room room;
	private Object lock = new Object();

	public ThreadClient(Socket socket,Login frmLogin) {
		this.socket = socket;
        this.frmLogin=frmLogin;
		try {
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public ThreadClient(Socket socket,Signup frmtaotaikhoan) {
		this.socket = socket;
        this.frmtaotaikhoan=frmtaotaikhoan;
		try {
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public ThreadClient(Socket socket,ChatHome chat) {
		this.socket = socket;
        this.chat=chat;
		try {
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public ThreadClient(Socket socket,Room room) {
		this.socket = socket;
        this.room=room;
		try {
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		try {
			while (!socket.isClosed()) {
				String data = dis.readUTF();
				st = new StringTokenizer(data, sepa);
				String CMD = st.nextToken();
				String cont01, cont02, cont03, cont04, cont05;
				System.out.println(CMD);
				switch (CMD) {
				case "login":
					cont01 = st.nextToken();
					cont02 = st.nextToken();
					System.out.println(cont01);
					sendlogin(cont01,cont02);

					break;
				case "tao tai khoan":
					cont01 = st.nextToken();
					sendMessageAccount(cont01);
					break;
				case "InsertGroup":
					cont01 = st.nextToken();
					cont02 = st.nextToken();
					senGroup(cont01, cont02);
					break;
				case "add":
					cont01 = st.nextToken();
					cont02 = st.nextToken();
					senonline(cont01, cont02);
					break;
				case "SendGroup":
					cont03 = st.nextToken();
					cont02 = st.nextToken();
					cont01 = st.nextToken();
					cont04 = st.nextToken();
					senmessGroup(cont03, cont02, cont01,cont04);
					break;
					
				case "SendPM":
					cont03 = st.nextToken();
					cont02 = st.nextToken();
					cont01 = st.nextToken();
					senmess(cont03, cont02, cont01);
					break;
				
				case "history":
					cont02 = st.nextToken();
					cont03 = st.nextToken();
					cont04 = st.nextToken();
					senhistorymess(cont02, cont03, cont04);
					break;
				case "CMD_SENDFILE":
					cont04 = st.nextToken();
					cont05 = st.nextToken();
					cont01 = st.nextToken();
					// gọi luông nhận file
					Socket sk = new Socket("localhost", 7777);
					RecivedFile rc = new RecivedFile(sk, cont04);
					Thread t1 = new Thread(rc);
					t1.start();
                   // file(cont04,cont05,cont01);
					break;
				case "group":
					cont04 = st.nextToken();
					createGroup(cont04);
					break;
				case "ds":
					cont04 = st.nextToken();					
					dsGroup(cont04);
					break;
					
				default:
					break;
				}

			}
		} catch (IOException e) {
			System.out.println(client);
		}
	}

	private void senmessGroup(String gui, String message, String avt, String nhan) {
		chat.getmessGroup(gui, message, avt,nhan);
		
	}
	private void senGroup(String GroupName, String AVTGroup) {
		chat.getGroup(GroupName, AVTGroup);
		
	}
	private void dsGroup(String Name) {
		room.getds(Name);
		
	}
	private void createGroup(String tb) {
		room.getcreate(tb);
		
	}
	public void doSendData(String cmd, String... params) {
		try {
			synchronized (dos) {
				String data = cmd;
				for (String s : params) {
					data = data + sepa + s;
				}
				dos.writeUTF(data);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendlogin(String message, String avt) {
		
		frmLogin.getlogin(message,avt);
	}

	public void senonline(String message, String img) {
		chat.getonline(message, img);
	}

	public void senmess(String gui, String message, String avt) {
		chat.getmess(gui, message, avt);
	}

	public void sendMessageAccount(String account) {
		frmtaotaikhoan.getaccount(account);
	}

	
	public void senhistorymess(String gui, String message, String avt) {
		chat.gethistorymess(gui, message, avt);
	}
   
//	public void file(String file, String gui, String nhan) {
//		chat.getfile(file,gui,nhan);
//	}
}
