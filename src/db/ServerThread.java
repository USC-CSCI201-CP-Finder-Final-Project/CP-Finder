package db;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;


import models.Course;
import models.UserQueue;
import util.ImmutableList;

public class ServerThread extends Thread {

	private PrintWriter pw;
	private BufferedReader br;
	private QueueRoom qr;
	private int id;
	public static final String CREDENTIALS_STRING = "jdbc:mysql://google/cpfinder?cloudSqlInstance=cpfinder-259622:us-west2:db1&socke"
			+ "tFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&user=root" +
		"&password=uscCs201!";
	public ServerThread(Socket s, QueueRoom qr) {
		try {
			this.qr = qr;
			pw = new PrintWriter(s.getOutputStream());
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			this.start();
		} catch (IOException ioe) {
			System.out.println("ioe in ServerThread constructor: " + ioe.getMessage());
		}
	}

	public void sendMessage(String message) {
		pw.println(message);
		pw.flush();
	}
	
	public void run() {
		try {
			while(true) {
				String line;
				String code;
				line = br.readLine();
				if(line.equals("")) {
					
				}
				else if(line.equals("change")) {
					code = this.getQ();
					qr.broadcast(code, this, id);
				}
				else if(Integer.parseInt(line) > 0) {
					id = Integer.parseInt(line);
					System.out.println(id);
					qr.add(this, id);
				}
			}
		} catch (IOException ioe) {
			System.out.println("ioe in ServerThread.run(): " + ioe.getMessage());
		}
	}
	
	public String getQ() {
		String s = "";
    	try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(CREDENTIALS_STRING);
			QueuesManager qm = new QueuesManager(connection);
			UserQueue uq = qm.getQueue(id);
			
			s = "<div id = 'queueHeader'>" +
			"Students in Queue: <br> <hr style='border-top: dotted 1px;' />"
			+ "</div>";
			for(int i = 0; i < uq.getQueuedUsers().size(); i++) {
				s = s + "<img class = 'img' src='profile.png'/>"
					+ "<p>"+(i+1)+". "+uq.getQueuedUsers().get(i).getUser().getName()+"</p>";
			}
			
		} catch (ClassNotFoundException | SQLException | DatabaseException e) {
			e.printStackTrace();
		} 
		return s;
	}
}