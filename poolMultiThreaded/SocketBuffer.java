package poolMultiThreaded;

import java.net.Socket;

public class SocketBuffer {

	Socket buff [];
	int getIdx, putIdx;
	
	public SocketBuffer (int size) {
		buff = new Socket [size];
		getIdx = putIdx = 0;
	}
	
	synchronized public void put (Socket s) throws InterruptedException {
		// If the buffer is full, we must wait
		while (nSock() >= buff.length)
			wait();
		
		// Insertion
		buff[putIdx] = s;
		putIdx = (putIdx + 1) % buff.length;
		
		notifyAll();
	}
	
	synchronized public Socket get () throws InterruptedException {
		// If the buffer is empty, the worker must wait
		while(nSock() <= 0)
			wait();
		
		// Removal
		Socket s = buff[getIdx];
		buff[getIdx] = null;
		getIdx = (getIdx + 1) % buff.length;
		
		notifyAll();
		
		return s;
	}
	
	// Counts the non null elements in buffer
	int nSock () {
		int count = 0;
		for (Socket s : buff) {
			if (s != null)
				count++;
		}
		return count;
	}
}
