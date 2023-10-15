package server;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {
	static final int PORT = 7878;
	public static void main(String[] args) throws IOException {
		System.out.println("================= SERVER STARTED =================");
		ServerSocket serverSocket = new ServerSocket(PORT);
		Server server = new Server(serverSocket);
		server.startServer();
	}

}
