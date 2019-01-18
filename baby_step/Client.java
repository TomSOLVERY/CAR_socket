package baby_step;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;

public class Client {

	String serverHost = "localhost";
	int serverPort = 2019;
	Socket server;
	String name = "Groot";

	public Client(String host, int port) throws UnknownHostException, IOException {
		serverHost = host;
		serverPort = port;
		server = new Socket(serverHost, serverPort);
	}

	public void main() throws IOException {
		OutputStream os = server.getOutputStream();
		DataOutputStream dos = new DataOutputStream(os);

		byte[] request = name.getBytes();
		dos.writeInt(request.length);
		dos.write(request);

		InputStream is = server.getInputStream();
		DataInputStream dis = new DataInputStream(is);

		int length = dis.readInt();
		byte[] response = new byte [length];
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
	}
}
