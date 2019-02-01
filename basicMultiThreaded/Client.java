package basicMultiThreaded;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.util.Random;

public class Client {

	static String serverHost = "localhost";
	static int serverPort = 2020;
	static Socket server;
	static String name = "Groot";

	public Client(String host, int port) throws UnknownHostException, IOException {
    	serverHost = host;
		serverPort = port;
	}

	public static void main(String args[]) throws IOException {
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
		FileOutputStream fos = new FileOutputStream("src/" + name + "res" + (System.currentTimeMillis()/1000));
		fos.write(response);
		System.out.println("Client OK");
		
		server.close();
		os.close();
		dos.close();
		is.close();
		dis.close();
		fos.close();
	}
}
