package poolMultiThreaded;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;

public class Client {

	static String serverHost = "localhost";
	static int serverPort = 2020;
	static Socket server;
	static String name = "Groot";
	int id;
	
	public Client (int i) {
		id = i;
	}

	public void start() throws IOException {
		server = new Socket(serverHost, serverPort);
		
		OutputStream os = server.getOutputStream();
		DataOutputStream dos = new DataOutputStream(os);

		byte[] request = name.getBytes();
		dos.writeInt(request.length);
		dos.write(request);

		InputStream is = server.getInputStream();
		DataInputStream dis = new DataInputStream(is);

		int length = dis.readInt();
		byte[] response = new byte[length];
		int nread = 0;
		int nb = 0;
		while (nread < length) {
			nb = dis.read(response, nread, length - nread);
			if (nb == -1)
				break;
			nread += nb;
		}
		FileOutputStream fos = new FileOutputStream("src/" + name + "res" + id);
		fos.write(response);
		System.out.println("Client " + id + " OK");
		
		server.close();
		os.close();
		dos.close();
		is.close();
		dis.close();
		fos.close();
	}
}
