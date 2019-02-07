package consideringLargeFiles;

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
		
		try {
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

			String fileName = "src/" + new String(b);
			File f = new File(fileName);
			fis = new FileInputStream(f);
			length = (int) f.length();
			byte[] request = new byte[512];
			nread = 0;
			num = 0;
			dos.writeInt(length);
			while (nread < length) {
				num = fis.read(request, 0, 512); // On lit par chunk de 512bytes afin de permettre une meilleure efficacité les gros fichiers.
				if (num == -1) {
					break;
				}
				nread += num;
				dos.write(request); // On envoie directement le chunk s'en attendre d'avoir tous reçu
			}

			
		} catch (IOException | InterruptedException e) {
		} finally {
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
			sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Worker " + id + " OK");
		
	}
}
