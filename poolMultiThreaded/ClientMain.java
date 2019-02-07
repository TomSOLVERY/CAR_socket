package poolMultiThreaded;

import java.io.IOException;

public class ClientMain {

	private static final int clientSize = 7;
	
	public static void main (String args[]) throws IOException {
		Client clients [] = new Client [clientSize];
		
		// Creating 7 clients at once for a simulation
		for (int i = 0; i < clientSize; i++) {
			clients[i] = new Client(i);
			clients[i].start();
		}
	}
}
