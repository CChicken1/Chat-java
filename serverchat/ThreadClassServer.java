package serverchat;

import java.io.DataInputStream;


import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;
import javax.swing.text.html.HTMLDocument.Iterator;


import serverchat.ThreadClassServer;

import serverchat.ConnectSql;

public class ThreadClassServer extends Thread {
	public static ArrayList<Chatter> list = new ArrayList<>();
	public static ArrayList<Chatter> listfile = new ArrayList<>();
	public static ArrayList<Chatter> listgroup = new ArrayList<>();
	StringTokenizer st;
	static final String sepa = "###";
	private Socket s;
	private ConnectSql connec = null;
	DataInputStream in;
	DataOutputStream out;
	private Object lock = new Object();
	public String saveServerDirDefault = "F:\\saveServerDirDefault";
	public ThreadClassServer(Socket s) {
		this.s = s;

	}

	@Override
	public void run() {
		try {

			System.out.println("ket noi thanh cong");
			in = new DataInputStream(s.getInputStream());
			out = new DataOutputStream(s.getOutputStream());

			if (connec == null) {
				connec = new ConnectSql();
			}
			while (!s.isClosed()) {

				String data = in.readUTF();
				st = new StringTokenizer(data, sepa);
				String yc = st.nextToken();
				// thuc hien yeu cau tao tai khoan
				switch (yc) {
				case "tao tai khoan":
					doaccount(st);

					break;

//				// thuc hien yeu cau login
				case "login":
					dologin(st);

					break;
					// trả về danh sách những người đã login vào
				case "online":
					doonline(st);

					break;
                //gửi tin nhắn
				case "SendPM":
					dosenmess(st);

					break;
					//xóa người out 
				case "out":
					outaccount(st);
					break;
					//lịch sử tin nhắn
				case "history":
					historymess(st);
					break;
					//đọc file từ client
				case "filepass":					
			      	file(st);
					break;
					//gửi file đi
				case "nhanfile":
					String file = st.nextToken();
					Chatter chatter1 = new Chatter(file,s);
					listfile.add(chatter1);	
					File p= new File(saveServerDirDefault + File.separator + file);
					sendfile rc2= new sendfile( file,p,chatter1);
					Thread t2= new Thread(rc2);
					t2.start();
					break;
					// tạo nhóm
				case "tao nhom":					
			         CreateGroup(st);
					break;
					//nhận danh sách tất cả tài khoản 
				case "danhsach":					
			         dsGroup();
					break;
					// thêm người vào nhóm 
				case "userGroup":					
			        addGroup(st);
					break;
				default:
					System.out.println("ko ro lenh" + yc);
					break;

				}
				 System.out.println(yc);
			}
		} catch (IOException e) {

			System.out.println(s + "vua thoat");
		}

	}
	
// hàm thêm vào nhóm 
private void addGroup(StringTokenizer st) {
		String user=st.nextToken();
		String group =st.nextToken();
		String avt =st.nextToken();
		ResultSet rs2 = connec.ktid(user);
		ResultSet rs3 = connec.idGroup(group);
		
		int iduser = 0, idGroup = 0;

		try {

			if (rs2.next()) {
				iduser = rs2.getInt("I_ID");
			}
			if (rs3.next()) {
				idGroup = rs3.getInt("I_ID");
			}
			System.out.println(idGroup +" " +iduser );
               connec.addGroup(idGroup,iduser );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (Chatter ch : list)
		{
			if (ch.getName().equals(user)) {
				ch.doData("add", group, avt);
			}
		}
		
	}
// hàm hiện các tài khoản có trong sql để chọn thêm vào nhóm 
private void dsGroup() {

	ResultSet rs4 = connec.DSUser();
	try {
		while (rs4.next()) {
			String user = rs4.getString("T_USERNAME");
			doSendData("ds", user);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
		
	}
// hàm tạo nhóm
private void CreateGroup(StringTokenizer st2) {
		String groupname = st.nextToken();
		String avt = st.nextToken();

		connec.newGroup(groupname,avt);
		Chatter chatter3;
		try {
			chatter3 = new Chatter(groupname,s);
			listgroup.add(chatter3);
			for (Chatter ch : listgroup) {
				if (ch.getName().equals(groupname)) {

					ch.doData("group", "tạo nhóm thành công bạn hãy thêm thành viên cho nhóm");

				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

//-----------------------hàm gửi thông tin đến client
	
	public void doSendData(String cmd, String... params) {
		try {
			synchronized (out) {
				String data = cmd;
				for (String s : params) {
					data = data + sepa + s;
				}
				out.writeUTF(data);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//----------------------hàm login
	
	public void dologin(StringTokenizer st) {
		String ten2 = st.nextToken();
		String mk11 = st.nextToken();
		ResultSet rs1 = connec.dangnhap(ten2);
        String avt=null;
		try {
			if (rs1.next()) {
				if (mk11.equals(rs1.getString("T_pass"))) {
					String tb = "dang nhap thanh cong";

					byte[] tb1 = new byte[1024];
					ResultSet rs6 = connec.img(ten2);
					if (rs6.next()) {
						avt = rs6.getString("IG_IMGPASS");
					}
					doSendData("login", "dang nhap thanh cong",avt);

				} else {
					String tb = "mk  khong đung";
					byte[] tb2 = new byte[1024];

					doSendData("login", tb,"null");
				}
			} else {
				String tb = "khong ton tai tai khoan nay";
				byte[] tb3 = new byte[1024];

				doSendData("login", tb);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//---------------------hàm tạo tài khoản 
	public void doaccount(StringTokenizer st) {
		String ten = st.nextToken();
		String mk = st.nextToken();
		String mk1 = st.nextToken();
		String img = st.nextToken();
		if (mk.equals(mk1)) {
			boolean kt = false;
			try {
				ResultSet rs1 = connec.kttentontai();
				while (rs1.next()) {
					if (ten.equals(rs1.getString("T_USERNAME"))) {
						String tb = "ten da ton tai hay dat lai ten khac";
						doSendData("tao tai khoan", tb);
						kt = true;
					}
				}
			} catch (SQLException e) {

				e.printStackTrace();
			}
			if (kt != true) {
				connec.newAccount(ten, mk, img);
				String tb = "dang ki thanh cong";
				doSendData("tao tai khoan", tb);
			}
		} else {
			String tb = "dang ki khong  thanh cong";

			doSendData("tao tai khoan", tb);
		}
	}
//-------------------------------hàm danh sách người online
	public void doonline(StringTokenizer st) throws IOException {
		
		String avt = null;
		String name = st.nextToken();
		
		Chatter chatter = new Chatter(name, s);
		list.add(chatter); 
		
		int idGroup = -1,iduser = -1;
		String avt1=null,Group=null;
		try {
			ResultSet rs2 = connec.ktid(name);
			if (rs2.next()) {
				iduser = rs2.getInt("I_ID");
			}
			
			ResultSet rs3 = connec.Group(iduser);
			
			while (rs3.next())
			{
				idGroup = rs3.getInt("I_IDGROUP");
				
			if(idGroup != -1)
			{
				
			ResultSet rs4 = connec.GroupName(idGroup);
			if(rs4.next()) {
				 Group = rs4.getString("T_NAME");
			}
			ResultSet rs5 = connec.GroupAvt(idGroup);
			if(rs5.next()) {
				 avt1= rs5.getString("T_AVTPASS");
			}
				
				 for (Chatter ch : list)
					{
						if (ch.getName().equals(name)) {
							
							ch.doData("InsertGroup", Group,avt1);

						}
					}
			}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		if (!name.equals("Cancel")) {			
			for (int i = 0; i < list.size(); i++) {
				String user = list.get(i).getName();
				for (Chatter ch : list)
				{
					ResultSet rs4 = connec.img(user);
					try {
						if (rs4.next()) {
							avt = rs4.getString("IG_IMGPASS");
						}
						ch.doData("add", user, avt);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		}
	}
	//------------------------hàm gửi tin nhắn
	public void dosenmess(StringTokenizer st) {

		String msg = st.nextToken();
		String nhan = st.nextToken();
		String gui = st.nextToken();
		String day=st.nextToken();
		String avt = null;	
		boolean kh =  nhan.contains("("); 
		if(kh==true) {
			
			ResultSet rs2 = connec.ktid(gui);
			ResultSet rs3 = connec.idGroup(nhan);
			ResultSet rs4 = connec.img(gui);
			int idgui = 0, idnhan = 0;
			try {

				if (rs2.next()) {
					idgui = rs2.getInt("I_ID");
				}
				if (rs3.next()) {
					idnhan = rs3.getInt("I_ID");
				}
				if (rs4.next()) {
					avt = rs4.getString("IG_IMGPASS");
				}
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date javaDate = dateFormat.parse(day);
					connec.settnGroup(idgui, idnhan,  msg, javaDate);
												
						ResultSet rs5 = connec.UserGroup(idnhan);
						while(rs5.next()) {
							int idusergroup=rs5.getInt("I_IDUSER");						
							ResultSet rs6 = connec.name(idusergroup);
							if(rs6.next()) {
								String usergroup=rs6.getString("T_USERNAME");
								 System.out.println(usergroup);
								for (Chatter ch : list)
								{
									if (ch.getName().equals(usergroup) && !ch.getName().equals(gui)) {
                                      
                                       ch.doData("SendGroup", gui, msg, avt,nhan);
									}
								}
							}
							
						}
						
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		else {
			
		ResultSet rs2 = connec.ktid(gui);
		ResultSet rs3 = connec.ktid(nhan);

		int idgui = 0, idnhan = 0;

		try {

			if (rs2.next()) {
				idgui = rs2.getInt("I_ID");
			}
			if (rs3.next()) {
				idnhan = rs3.getInt("I_ID");
			}
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date javaDate = dateFormat.parse(day);
				connec.settn(idgui, idnhan, msg,javaDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (Chatter ch : list) {
			if (ch.getName().equals(nhan)) {

				ResultSet rs4 = connec.img(gui);
				try {
					if (rs4.next()) {
						avt = rs4.getString("IG_IMGPASS");
					}
					ch.doData("SendPM", gui, msg, avt);
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		}
	}
//---------------------------hàm xóa người đã out 
	public void outaccount(StringTokenizer st) {
		String tenout = st.nextToken();
		
		for (int i = 0; i < list.size(); i++) {		
			String tenaccout = list.get(i).getName();
			if (tenaccout.equals(tenout)) {
				list.remove(list.get(i));
			}

		}

	}

// ---------------------------------------------hàm nhan file 
	public void file(StringTokenizer st) {
		
		String filename = st.nextToken();
		String receiver = st.nextToken();
		String sender = st.nextToken();
		System.out.println(filename.replace(" ", "_") + " " + receiver + " " + sender);
		String filegui=filename+"send";
		
		/// doc du lieu
		File file= new File(filename);
		String clean_filename = file.getName();
	
		try {
			
			Chatter chatter2 = new Chatter(filegui,s);
			listfile.add(chatter2);	
			for (Chatter ch2 : ThreadClassServer. listfile) {
				if(ch2.getName().equals(filegui)) {
				ch2.getOut().writeUTF("nhan");
			File path= new File(saveServerDirDefault + File.separator + clean_filename);
			//String path = "F:\\" + clean_filename;
		FileOutputStream os = new FileOutputStream(path);
		while(!ch2.getSocket().isClosed())	
		{	
			
		byte[] b=new byte[1024];		
			int count=ch2.getIn().readInt();
			if(count>0)
			{ch2.getIn().read(b,0,count);
				os.write(b,0,count);
				os.flush();
			}
			else {				
				os.close();
			}
			
		}
		ch2.getIn().close();
		ch2.getSocket().close();
			}
			}
			
		
	}catch(IOException e)
		{
		for (Chatter ch : list) {
			if (ch.getName().equals(receiver)) {
				System.out.println("gui lai tin hieu");
				ch.doData("CMD_SENDFILE", clean_filename.replace(" ", "_"), sender,receiver);
			}
		}
		
		System.out.println("thoat");
		}

		

		
		
		
	}
//-----------------hàm lịch sử tin nhắn 
	public void historymess(StringTokenizer st) {
		String nhan = st.nextToken();
		String gui = st.nextToken();
		String avt = null;
		int idgui = -1, idnhan = -1;
		String tengui = null;
		
		boolean kh =  nhan.contains("("); 
		if(kh==true) {

		
			ResultSet rs3 = connec.idGroup(nhan);		
			try {

				if (rs3.next()) {
					idnhan = rs3.getInt("I_ID");
				}
							
				ResultSet rs5=connec.gettnGroup(idnhan);
				while(rs5.next()) 
				{
					int id = rs5.getInt("I_IDSENDER");
					ResultSet rs6 = connec.name(id);
					if (rs6.next()) {
						tengui = rs6.getString("T_USERNAME");
					}
					
					String mess = rs5.getString("T_CONTENTMESS");
					System.out.println(id + " "+mess);	
					System.out.println(tengui + " " + mess );
					for (Chatter ch : list)
					{
						if (ch.getName().equals(gui)) {
							ResultSet rs7 = connec.img(tengui);
							if (rs7.next()) {
								avt = rs7.getString("IG_IMGPASS");
							}
							ch.doData("history", tengui, mess, avt);

						}
					}
				}
				

						
				
				

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		else 
		{
		try {
			ResultSet rs2 = connec.ktid(gui);
			ResultSet rs3 = connec.ktid(nhan);
			if (rs2.next()) {
				idgui = rs2.getInt("I_ID");
			}
			if (rs3.next()) {
				idnhan = rs3.getInt("I_ID");
			}

			ResultSet rs4 = connec.gettn(idgui, idnhan);
			while (rs4.next()) {
				int id = rs4.getInt("I_IDSENDER");
				ResultSet rs5 = connec.name(id);
				if (rs5.next()) {
					tengui = rs5.getString("T_USERNAME");
				}
				String mess = rs4.getString("T_CONTENTMESS");
				for (Chatter ch : list)
				{
					if (ch.getName().equals(gui)) {
						ResultSet rs6 = connec.img(nhan);
						if (rs6.next()) {
							avt = rs6.getString("IG_IMGPASS");
						}
						ch.doData("history", tengui, mess, avt);

					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	
}
