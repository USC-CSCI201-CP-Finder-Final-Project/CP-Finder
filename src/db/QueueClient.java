package db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import models.UserQueue;

public class QueueClient extends Thread {

	private BufferedReader br;
	private PrintWriter pw;
	public boolean changed;
	public boolean newQueue;
	public String code;
	HttpSession session;
	int id;
	public static final String CREDENTIALS_STRING = "jdbc:mysql://google/cpfinder?cloudSqlInstance=cpfinder-259622:us-west2:db1&socke"
			+ "tFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&user=root" +
		"&password=uscCs201!";
	public Connection conn;
	public QueueClient(String hostname, int port, int courseID, HttpSession ses) {
		try {
			changed = false;
			newQueue = false;
			session = ses;
			id = courseID;
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
				String c = br.readLine();
				if(!c.equals("")) {
					code = c;
					newQueue = true;
					session.setAttribute("queueChanged", "true");
					session.setAttribute("queueClient", this);
				}
			}
		} catch (IOException ioe) {
			System.out.println("ioe in ChatClient.run(): " + ioe.getMessage());
		}
	}
	
	public void change() {
		changed = true;
		pw.println("change");
		pw.flush();
		changed = false;
	}
	
	public void print(String s) {
		System.out.println(s);
	}
	
	public String getQ() {
		String s = "";
    	try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(CREDENTIALS_STRING);
			QueuesManager qm = new QueuesManager(connection);
			UserQueue uq = qm.getQueue(id);
			Gson gson = new Gson();
			String code = "";
			code += "<div id = 'queue'>";
			for (int i = 0; i < uq.getQueuedUsers().size(); i++) {
				code += "<div class = 'queueDisplay'><div class = 'student'><div class = 'img'><img class = 'studentimg' src='profile.png'/>"
					+ "</div><p class = 'studentName'>"+(i+1)+". " + uq.getQueuedUsers().get(i).getUser().getName()+"</p></div>";
			}
			code += "<button onclick = 'enqueue();' id = 'add'>Add me to the Queue</button></div>";
			return code;
			
			
		} catch (ClassNotFoundException | SQLException | DatabaseException e) {
			e.printStackTrace();
		}
    	return s;
	}
	
}

