package baby_step;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;

public class Client {

	private static String serverHost = "localhost";
	private static int serverPort = 2020;
	private static Socket server;
	private static String name = "Groot";

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
		String res = new String(response);
		System.out.println(res);
		
		server.close();
		os.close();
		dos.close();
		is.close();
		dis.close();
	}
}
