package Server;

import java.io.*;
import java.net.*;
import java.awt.*;
import javax.swing.*;

public class MultiThreadServer extends JFrame{
	private JTextArea jta = new JTextArea();
	website.youdaodic dic = new website.youdaodic();

	public static void main(String[] args) {
		new MultiThreadServer();
	}
	
	public MultiThreadServer() {
		setLayout(new BorderLayout());
		add(new JScrollPane(jta), BorderLayout.CENTER);
		setTitle("Server");
		setSize(500, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		try {
			ServerSocket serverSocket = new ServerSocket(8000);
			
			int clientNo = 1;
			
			while(true) {
				Socket socket = serverSocket.accept();
				
				InetAddress inetAddress = socket.getInetAddress();
				System.out.println(inetAddress);
				
				HandleAClient task = new HandleAClient(socket);
				
				new Thread(task).start();
				
				clientNo ++;
			}
		}
		catch(IOException ex) {
			System.err.println(ex);
		}
	}
	
	//Inner Class
	class HandleAClient implements Runnable {
		private Socket socket;
		
		public HandleAClient(Socket socket) {
			this.socket = socket;
		}
		
		public void run() {
			try {
				//Create a server socket
//				ServerSocket serverSocket = new ServerSocket(8000);
//				jta.append("Server started at " + new Date() + '\n');
				
				//Listen for a connection request
//				Socket socket = serverSocket.accept();
				
				//Create data input and output streams
				DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
				DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());
				
				while(true) {
					//Recieve radius from the client
					String word = inputFromClient.readUTF();
					System.out.println(word + "server");
					//Compute area
					String meaning = dic.lookup(word);
					
					//Send area back to the client
//					outputToClient.writeBytes(meaning);
					outputToClient.writeUTF(meaning);
//					jta.append("Radius received from client: " + radius + '\n');
//					jta.append("Area found: " + meaning + '\n');
				}
			}
			catch(IOException ex) {
				System.err.println(ex);
			}
		}
	}
}