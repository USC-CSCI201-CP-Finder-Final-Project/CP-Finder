package db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class QueueClient extends Thread {

	private BufferedReader br;
	private PrintWriter pw;
	private boolean changed;
	public boolean newQueue;
	public String code;
	public QueueClient(String hostname, int port, int courseID) {
		try {
			changed = false;
			newQueue = false;
			System.out.println("Trying to connect to " + hostname + ":" + port);
			Socket s = new Socket(hostname, port);
			System.out.println("Connected to " + hostname + ":" + port);
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			pw = new PrintWriter(s.getOutputStream());
			this.start();
			pw.println(Integer.toString(courseID));
			pw.flush();
			
		} catch (IOException ioe) {
			System.out.println("ioe in ChatClient constructor: " + ioe.getMessage());
		}
	}
	public void run() {
		try {
			while(true) {
				String code = br.readLine();
				newQueue = true;	
			}
		} catch (IOException ioe) {
			System.out.println("ioe in ChatClient.run(): " + ioe.getMessage());
		}
	}
	
	public void change() {
		changed = true;
		pw.println("change");
		pw.flush();
	}
	
	public void print(String s) {
		System.out.println(s);
	}
	
}

