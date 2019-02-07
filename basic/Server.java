package basic;

import java.io.*;
import java.net.*;

public class Server {
	private static final int port = 2020;
	private static ServerSocket server;

	public static void main(String args[]) throws IOException {
		server = new ServerSocket(port);
		while (true) {
			Socket client = server.accept();

			// Creating the streams
			InputStream is;
			OutputStream os;
			DataInputStream dis;
			DataOutputStream dos;
			FileInputStream fis;

			// Getting the request from the client
			os = client.getOutputStream();
			dos = new DataOutputStream(os);
			is = client.getInputStream();
			dis = new DataInputStream(is);

			int length = dis.readInt();
			byte[] b = new byte[length];
			int nread = 0;
			int num = 0;
			while (nread < length) {
				num = dis.read(b, nread, length - nread);
				if (num == -1) {
					break;
				}
				nread += num;
			}
			
			// Searching for the file, on the source folder for easier testing
			// But not a good place to store files in reality
			String fileName = "src/" + new String(b);
			File f = new File(fileName);
			
			// Putting the content in a buffer
			fis = new FileInputStream(f);
			length = (int) f.length();
			byte[] request = new byte[length];
			nread = 0;
			num = 0;
			while (nread < length) {
				num = fis.read(request, nread, length - nread);
				if (num == -1) {
					break;
				}
				nread += num;
			}

			// Sending the file to the client
			dos.writeInt(request.length);
			dos.write(request);
			
			// Closing the streams
			is.close();
			os.close();
			dis.close();
			dos.close();
			fis.close();

			System.out.println("Server OK");
		}
	}
}
