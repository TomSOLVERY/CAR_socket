package baby_step;
import java.io.*;
import java.net.*;
public class Server {
 private static final int port = 2020;
 private static ServerSocket server;
 
  public static void main(String args[]) throws IOException {
	  server = new ServerSocket(port);
	  while(true) {
		  Socket client = server.accept();
		  
		  InputStream is;
		  DataInputStream dis;
		  is = client.getInputStream();
		  dis = new DataInputStream(is);
		  
		  int length = dis.readInt();
		  byte[] b = new byte[length];
		  int nread = 0;
		  int num = 0;
		  while (nread < length) {
			  num = dis.read(b,nread,length-nread);
			  if (num == -1) {
				  break;
			  }
			nread+=num;  
		  }
		  
		  String name = new String(b);
		  String hello = "Hello " + name;
		  byte[] request = hello.getBytes();
		  
		  OutputStream os;
		  DataOutputStream dos;
		  os = client.getOutputStream();
		  dos = new DataOutputStream(os);
		  dos.writeInt(request.length);
		  dos.write(request);
		  
		  dos.flush();
		  is.close();
		  dis.close();
		  os.close();
		  dos.close();
		  
		  System.out.println(name);
	  }
  }
}
