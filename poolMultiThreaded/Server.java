package poolMultiThreaded;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	private static final int port = 2020;
	private static ServerSocket server;
	private static final int bufferSize = 3;
	private static final int workerSize = 5;

	public static void main(String args[]) throws IOException, InterruptedException {
		server = new ServerSocket(port);
		
		/* Creation du buffer de sockets */
		SocketBuffer sb = new SocketBuffer(bufferSize);
		Worker workers [] = new Worker [workerSize];
		
		for (int i = 0; i < workerSize; i++)
			workers[i] = new Worker(sb, i);
		
		
		while (true) {
			Socket client = server.accept();
			sb.put(client);
		}
	}
}
