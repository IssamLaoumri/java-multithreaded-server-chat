package clients;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	private String username;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	private Socket socket;
	
	public Client(Socket socket, String username) {
		try {
			this.socket = socket;
			this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			this.username = username;
		} catch (Exception e) {
			close(socket, bufferedReader, bufferedWriter);
		}
	}
	
	public void sendMessage() {
		try {
			bufferedWriter.write(username);
			bufferedWriter.newLine();
			bufferedWriter.flush();
			
			Scanner scanner = new Scanner(System.in);
			while(socket.isConnected()) {
				String messageToSend = scanner.nextLine();
				bufferedWriter.write(username +": "+messageToSend);
				bufferedWriter.newLine();
				bufferedWriter.flush();
			}
		} catch (Exception e) {
			close(socket, bufferedReader, bufferedWriter);
			
		}
	}
	
	public void messagesListener() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				String messageRecieved;
				try {
					while(socket.isConnected()) {
						messageRecieved = bufferedReader.readLine();
						System.out.println(messageRecieved);
					}
				} catch (Exception e) {
					close(socket, bufferedReader, bufferedWriter);
				}
			}
		}).start();;
	}
	
	private void close(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
		try {
			if(socket != null)	socket.close();
			if(bufferedReader != null)	bufferedReader.close();
			if(bufferedWriter != null)	bufferedWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
