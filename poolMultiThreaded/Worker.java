package poolMultiThreaded;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Worker extends Thread {
	Socket client;
	SocketBuffer sb;
	int id;
	InputStream is;
	OutputStream os;
	DataInputStream dis;
	DataOutputStream dos;
	FileInputStream fis;

	public Worker(SocketBuffer buff, int id) throws IOException, InterruptedException {
		sb = buff;
		this.id = id;
		System.out.println("Worker " + id + " created");
		start();
	}

	public void run() {
		// when the worker finishes with a client he should be ready 
		// for another request, therefore a while true structure
		while (true) {
			try {
				// Getting the request from the client, socket in the buffer
				client = sb.get();
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
			} catch (IOException | InterruptedException e) {
			} finally {
				// Closing the streams
				try {
					is.close();
					os.close();
					dis.close();
					dos.close();
					fis.close();
				} catch (IOException e) {
				}
			}
			try {
				sleep(10000); // Simulate some lag
			} catch (InterruptedException e) {
				e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Erreur sur L'ouverture d'un stream");
		} finally {
			try {
				is.close();
				os.close();
				dis.close();
				dos.close();
				fis.close();
			} catch (IOException e) {
				System.out.println("Erreur sur la fermeture d'un stream ou fichier inexistant");
			}

			System.out.println("Worker " + id + " OK");
		}
	}
}
