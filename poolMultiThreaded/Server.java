package poolMultiThreaded;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	private static final int port = 2020;
	private static ServerSocket server;

	public static void main(String args[]) throws IOException {
		server = new ServerSocket(port);
		int id = 1;
		/*
		 * Creer le buffer de sockets
		 * Creer les workers
		 * 
		 */
		while (true) {
			Socket client = server.accept();
			/*
			 * put ce socket dans le buffer
			 */
		}
	}
}
