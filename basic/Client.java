package basic;

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

	public static void main(String args[]) throws IOException {
		server = new Socket(serverHost, serverPort);
		
		// Sending the file name
		OutputStream os = server.getOutputStream();
		DataOutputStream dos = new DataOutputStream(os);

		byte[] request = name.getBytes();
		dos.writeInt(request.length);
		dos.write(request);

		InputStream is = server.getInputStream();
		DataInputStream dis = new DataInputStream(is);

		// Getting the response
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
		
		// Writing it in to a file, on the source folder for easier testing
		// In reality we should use a hierarchy with folders and files
		FileOutputStream fos = new FileOutputStream("src/" + name + "res");
		fos.write(response);
		System.out.println("Client OK");
		
		server.close();
		
		// Closing the streams
		os.close();
		dos.close();
		is.close();
		dis.close();
		fos.close();
	}
}
