package clients;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable{
	private String username;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	private Socket socket;
	
	protected static List<ClientHandler> clientHandlers = new ArrayList<>();
	
	
	
	public ClientHandler(Socket socket) {
		try {
			this.socket = socket;
			this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			this.username = bufferedReader.readLine();
			clientHandlers.add(this);
			broadcast("SERVER : User "+ username + " has entered the chat.");
		} catch (Exception e) {
			close(socket, bufferedReader, bufferedWriter);
		}
	}

	@Override
	public void run() {
		String messageFromClient;
		
		while(socket.isConnected()) {
			try {
				messageFromClient = bufferedReader.readLine();
				broadcast(messageFromClient);
			} catch (Exception e) {
				close(socket, bufferedReader, bufferedWriter);
				break;
			}
		}
	}
	
	public void broadcast(String message) {
			for(ClientHandler clientHandler : clientHandlers) {
				try {
					if(!clientHandler.username.equals(this.username)) {
						clientHandler.bufferedWriter.write(message);
						clientHandler.bufferedWriter.newLine();
						clientHandler.bufferedWriter.flush();
					}
				} catch (Exception e) {
					close(socket, bufferedReader, bufferedWriter);
				}
			}
	}
	
	private void close(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
		removeClient();
		try {
			if(socket != null)	socket.close();
			if(bufferedReader != null)	bufferedReader.close();
			if(bufferedWriter != null)	bufferedWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void removeClient() {
		clientHandlers.remove(this);
		broadcast("SERVER : Client "+username+"has left the chat.");
	}
	
}
