package main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class SendFile implements Runnable{
	private Socket s;
	private String p;
	private String userReceive;
	private String user;
	private Object lock= new Object();
	public SendFile(Socket s, String p, String  userReceive, String user) {
		this.s=s;
		this.p=p;
	    this.userReceive=userReceive;
	    this.user=user;
		
	}
	@Override
	public void run()
	{ try {
		DataOutputStream os= new DataOutputStream(s.getOutputStream());
		os.writeUTF("filepass"+"###"+ p+"###"+userReceive+"###"+ user);
		DataInputStream is= new DataInputStream(s.getInputStream());
		FileInputStream in= new FileInputStream(p);
		String yc = is.readUTF();
		if (yc.equals("nhan")) {
		byte[] b= new byte[1024];
		int count;
		while((count=in.read(b))!=-1)
		{
			synchronized(lock)
			{
				if(!s.isClosed())
				{			
					os.writeInt(count);
					os.write(b,0,count);					
				}
			}
		}
		synchronized(lock)
		{
			
			os.write(-1);
		}
		in.close();
		os.flush();
		s.close();
		}
	}catch(IOException e)
	{
		e.printStackTrace();
	}
	}
}