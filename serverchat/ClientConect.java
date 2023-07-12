package serverchat;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.DefaultListModel;

public class ClientConect {

	private ServerSocket server;
	public boolean isStop = false;
	

	public ClientConect() throws Exception {
		server = new ServerSocket(7777);
		(new WaitForConnect()).start();
	}

	public void stopserver() throws Exception {
		isStop = true;
		server.close();
	
	}

	public class WaitForConnect extends Thread {

		@Override
		public void run() {
			try {
				while (!isStop) {
					Socket connection = server.accept();
                   
					ThreadClassServer th = new ThreadClassServer(connection);
					Thread t = new Thread(th);
					t.start();

				}
			} catch (Exception e) {
				System.out.println("da dong socket");

			}
		}
	}

}
