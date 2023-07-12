package serverchat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class sendfile implements Runnable{
	
	String p;
	File p2;
	Chatter chatter;
	StringTokenizer st;
	private Object lock= new Object();
	public sendfile(String p,File p2,Chatter chatter) {
		
		 this .p=p;
		 this.p2=p2;
		 this.chatter = chatter;
	  
		
	}
	@Override
	public void run()
	{ try 
	{
	

			
						
			for (Chatter ch : ThreadClassServer. listfile) {
				if(ch.getName().equals(p)) {
				ch.getOut().writeUTF("nhan");
				FileInputStream inn= new FileInputStream(p2);
				byte[] b1= new byte[1024];
				int count1;
				while((count1=inn.read(b1))!=-1)
				{
					synchronized(lock)
					{
						if(!ch.getSocket().isClosed())
						{
							ch.getOut().writeInt(count1);
							ch.getOut().write(b1,0,count1);							
						}
					}
				}
				synchronized(lock)
				{
					
					ch.getOut().write(-1);
				}
				inn.close();
				ch.getOut().flush();
			}
			
			}
		
		
		
			
		
	
	}catch(IOException e)
	{
		
		e.printStackTrace();
	}
	}
}