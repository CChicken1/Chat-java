package main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;

public class RecivedFile extends Thread {
	private Socket s;
	private String p;
	private String userReceive;

	public RecivedFile(Socket s, String p) {
		this.s = s;
		this.p = p;

	}

	@Override
	public void run() {
		try {

			String path = "C:\\Users\\win\\Downloads\\" + p;
			DataInputStream in = new DataInputStream(s.getInputStream());
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
			out.writeUTF("nhanfile" + "###" + p);
			String yc = in.readUTF();
			if (yc.equals("nhan")) {
				FileOutputStream os = new FileOutputStream(path);
				while(!s.isClosed())	
				{
				byte[] b = new byte[1024];

				int count = in.readInt();

				if (count > 0) {
					in.read(b, 0, count);
					os.write(b, 0, count);
					os.flush();
				} else {
					os.close();

				}

			}
				in.close();
				s.close();
				}
			
		} catch (IOException e) {
			System.out.println("vua thoat");
		}
	}
}