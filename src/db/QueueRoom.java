package db;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class QueueRoom {
	private Vector<Vector<ServerThread>> serverThreads;
	public QueueRoom(int port) {
		try {
			serverThreads = new Vector<Vector<ServerThread>>();
			System.out.println("Binding to port " + port);
			ServerSocket ss = new ServerSocket(port);
			System.out.println("Bound to port " + port);
			for(int i=0; i<14; i++) {
				Vector v = new Vector<ServerThread>();
				serverThreads.add(v);
			}
			while(true) {
				Socket s = ss.accept(); // blocking
				System.out.println("Connection from: " + s.getInetAddress());
				ServerThread st = new ServerThread(s, this);
			}
		} catch (IOException ioe) {
			System.out.println("ioe in ChatRoom constructor: " + ioe.getMessage());
		}
	}
	
	public void broadcast(String message, ServerThread st, int courseID) {
		if (message != null) {
			System.out.println(serverThreads.get(courseID-1));
			for(ServerThread threads : serverThreads.get(courseID-1)) {
				if (st != threads) {
					threads.sendMessage(message);
				}
			}
		}
	}
	
	public void add(ServerThread st, int courseID) {
		Vector<ServerThread> v = serverThreads.get(courseID-1);
		v.add(st);
	}
	
	public static void main(String [] args) {
		QueueRoom qr = new QueueRoom(6790);
	}
}