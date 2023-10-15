package clients;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientRunner {
	static final int PORT = 7878;
	static final String HOST = "localhost";

	public static void main(String[] args) throws UnknownHostException, IOException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter your username : ");
		String username = scanner.nextLine();
		Socket socket = new Socket(HOST, PORT);
		Client client = new Client(socket, username);
		client.messagesListener();
		client.sendMessage();
	}

}
