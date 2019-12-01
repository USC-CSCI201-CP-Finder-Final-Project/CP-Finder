package db;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;

import com.google.gson.Gson;

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
		System.out.println("W");
		pw.println(message);
		pw.flush();
	}
	
	public void run() {
		try {
			while(true) {
				System.out.println("f");
				String line;
				String code;
				line = br.readLine();
				if(line.equals("")) {
					
				}
				else if(line.equals("change")) {
					System.out.println("c");
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
    		System.out.println("HERE");
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(CREDENTIALS_STRING);
			QueuesManager qm = new QueuesManager(connection);
			UserQueue uq = qm.getQueue(id);
			Gson gson = new Gson();
			String queueJson = gson.toJson(uq);
			return queueJson;
			
			
		} catch (ClassNotFoundException | SQLException | DatabaseException e) {
			e.printStackTrace();
		}
    	return s;
	}
}