package serverchat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class ConnectSql {
	private Connection connec = null;
	private PreparedStatement pre = null;
	private Statement stmt = null;
	private ResultSet rs1 = null;

	public ConnectSql() {

		try {

			String URL = "jdbc:mysql://localhost:3306/chatap";
			String USERNAME = "root";
			String PASSWORD = "dptt34";
			Class.forName("com.mysql.cj.jdbc.Driver");
			connec = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			System.out.println("1. Connected ok");
		} catch (Exception e) {
			System.out.println("2. Connected error");
		}
	}

	// tao tk
	public void newAccount(String username, String pass,String imgpass) {
		try {
			pre = connec.prepareStatement("INSERT INTO ta_group8_account (T_USERNAME,T_PASS,IG_IMGPASS) VALUES(?,?,?)");
			pre.setString(1, username);
			pre.setString(2, pass);
			pre.setString(3, imgpass);
			pre.executeUpdate();
		} catch (Exception e) {
		}
	}

	// trả về mk để kt đnhap
	public ResultSet dangnhap(String username) {
		try {
			stmt = connec.createStatement();
			rs1 = stmt.executeQuery("select T_pass from ta_group8_account where T_USERNAME = '" + username + "'");
			return rs1;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//trả về đường dẫn ảnh đại diện 
	public ResultSet img(String username) {
		try {
			stmt = connec.createStatement();
			rs1 = stmt.executeQuery("select IG_IMGPASS from ta_group8_account where T_USERNAME = '" + username + "'");
			return rs1;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// kt tên dnhap đã tồn tai chưa
	public ResultSet kttentontai() {
		try {
			stmt = connec.createStatement();
			rs1 = stmt.executeQuery("select T_USERNAME from ta_group8_account ");
			return rs1;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// trả về id  của tài khoản
	public ResultSet ktid(String name) {
		try {
			stmt = connec.createStatement();
			rs1 = stmt.executeQuery("select I_ID from ta_group8_account  where T_USERNAME = '" + name + "'");
			return rs1;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// lưu tin nhắn đơn vào csdl
	public void settn(int name1, int name2,String tn,Date day) {
		try {
			pre = connec.prepareStatement("INSERT INTO ta_group8_mess (I_IDSENDER,I_IDRECEIVER,T_CONTENTMESS,D_SENTDATE) VALUES(?,?,?,?)");
			pre.setInt(1, name1);
			pre.setInt(2, name2);
			pre.setString(3, tn);
			pre.setDate(4, new java.sql.Date(day.getTime()));
			pre.executeUpdate();
		} catch (Exception e) {
		}
	}

	// lấy tin nhắn đơn 
	public ResultSet gettn(int name1, int name2) {
		try {
			stmt = connec.createStatement();
			rs1 = stmt.executeQuery("select I_IDSENDER , T_CONTENTMESS  from  ta_group8_mess where( I_IDSENDER  = '" + name1 + "'"+ "AND I_IDRECEIVER ='"+name2 +"')"
			+"or( I_IDRECEIVER =' "+name1+"'"+"AND I_IDSENDER ='"+name2 +"')"+"ORDER BY I_IDMESS");
			return rs1;
		} catch (Exception e) {
		}
		return null;
	}
	
	// lấy tên tài khoản
	public ResultSet name(int id) {
		try {
			stmt = connec.createStatement();
			rs1 = stmt.executeQuery("select T_USERNAME  from ta_group8_account  where I_ID = '" + id + "'");
			return rs1;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	// tạo nhóm
	public void newGroup(String Name,String avtpass) {
		try {
			pre = connec.prepareStatement("INSERT INTO ta_group8_room (T_NAME,T_AVTPASS) VALUES(?,?)");
			pre.setString(1, Name);
			pre.setString(2, avtpass);
			pre.executeUpdate();
		} catch (Exception e) {
		}
	}
	
	// lấy tên các tài khoản có trong csdl
	public ResultSet DSUser() {
		try {
			stmt = connec.createStatement();
			rs1 = stmt.executeQuery("select T_USERNAME   from ta_group8_account ");
			return rs1;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	// lấy id của nhóm theo tên nhóm
	public ResultSet idGroup(String name) {
		try {
			stmt = connec.createStatement();
			rs1 = stmt.executeQuery("select I_ID from ta_group8_room  where T_NAME = '" + name + "'");
			return rs1;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// thêm người vào nhóm 
	public void addGroup(int name1, int name2) {
		try {
			pre = connec.prepareStatement("INSERT INTO ta_group8_roomuser (I_IDGROUP,I_IDUSER) VALUES(?,?)");
			pre.setInt(1, name1);
			pre.setInt(2, name2);
			pre.executeUpdate();
		} catch (Exception e) {
		}
	}
	// thêm tin nhắn nhóm vào csdl
	public void settnGroup(int name1, int name2,String tn,Date day) {
		try {
			pre = connec.prepareStatement("INSERT INTO ta_group8_mess (I_IDSENDER,I_IDGROUP,T_CONTENTMESS,D_SENTDATE) VALUES(?,?,?,?)");
			pre.setInt(1, name1);
			pre.setInt(2, name2);
			pre.setString(3, tn);
			pre.setDate(4, new java.sql.Date(day.getTime()));
			pre.executeUpdate();
		} catch (Exception e) {
		}
	}
	
	// lấy id tất cả nhóm mà I_IDUSER có tham gia
	public ResultSet Group(int id) {
		try {
			stmt = connec.createStatement();
			rs1 = stmt.executeQuery("select I_IDGROUP from ta_group8_roomuser  where I_IDUSER = '" + id + "'");
			return rs1;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// lấy tên nhóm  
	public ResultSet GroupName(int id) {
		try {
			stmt = connec.createStatement();
			rs1 = stmt.executeQuery("select T_NAME  from ta_group8_room  where I_ID = '" + id + "'");
			return rs1;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	//lấy ảnh nhóm 
	public ResultSet GroupAvt(int id) {
		try {
			stmt = connec.createStatement();
			rs1 = stmt.executeQuery("select T_AVTPASS from ta_group8_room  where I_ID = '" + id + "'");
			return rs1;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	// lấy I_IDUSER có trong nhóm 
	public ResultSet UserGroup(int id) {
		try {
			stmt = connec.createStatement();
			rs1 = stmt.executeQuery("select I_IDUSER from ta_group8_roomuser  where  I_IDGROUP = '" + id + "'");
			return rs1;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	// lấy tin nhắn nhóm trong csdl
	public ResultSet gettnGroup(int group) {
		try {
			stmt = connec.createStatement();
			rs1 = stmt.executeQuery("select I_IDSENDER , T_CONTENTMESS  from  ta_group8_mess where I_IDGROUP = '" + group + "'"
			+"ORDER BY I_IDMESS");
			return rs1;
		} catch (Exception e) {
		}
		return null;
	}
}
