package server;

import java.net.ServerSocket;
import java.net.Socket;

import clients.ClientHandler;

public class Server {
	private ServerSocket serverSocket;

	public Server(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}
	
	public void startServer() {
		try {
			while(!serverSocket.isClosed()) {
				Socket socket = serverSocket.accept();
				System.out.println("un nouveau client est connecté !");
				ClientHandler clientHandler = new ClientHandler(socket);
				Thread newClient = new Thread(clientHandler);
				newClient.start();
			}
		} catch (Exception e) {
			closeServerSocket();
		}
	}
	
	public void closeServerSocket() {
		try {
			if(serverSocket != null) {
				serverSocket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
