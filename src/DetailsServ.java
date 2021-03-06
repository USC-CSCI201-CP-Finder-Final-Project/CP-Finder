
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import db.*;
import models.*;
import util.ImmutableList;

/**
 * Servlet implementation class DetailsServ
 */
@WebServlet("/DetailsServ")
public class DetailsServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String CREDENTIALS_STRING = "jdbc:mysql://google/cpfinder?cloudSqlInstance=cpfinder-259622:us-west2:db1&socke"
			+ "tFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&user=root" +
		"&password=uscCs201!";
	static Connection connection = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DetailsServ() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	int courseID = Integer.parseInt(request.getParameter("id"));
    	try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(CREDENTIALS_STRING);
			QueuesManager qm = new QueuesManager(connection);
			SessionsManager sm = new SessionsManager(connection);
			ImmutableList<Session> mySessions = sm.getSessions(courseID, true);
			ArrayList<User> myCPs = new ArrayList<User>();
			for (int i = 0; i < mySessions.size(); i++) {
				myCPs.add(mySessions.get(i).getUser());
			}
			UserQueue uq = qm.getQueue(courseID);
			CoursesManager cm = new CoursesManager(connection);
			ImmutableList<Course> results = cm.getCourses();
			Course myCourse = results.get(courseID-1);
			session.setAttribute("courseID", courseID);
			Gson gson = new Gson();
			String queueJson = gson.toJson(uq);
			String courseJson = gson.toJson(myCourse);
			String cpJson = gson.toJson(mySessions);
			request.setAttribute("queue", queueJson);
			session.setAttribute("queue", queueJson);
			request.setAttribute("cps", cpJson);
			session.setAttribute("cps", cpJson);
			
			request.setAttribute("course", courseJson);
			session.setAttribute("course", courseJson);
			
			QueueClient qc = new QueueClient("localhost", 6790, courseID, request.getSession());
			session.setAttribute("queueClient", qc);
			session.setAttribute("queueChanged", "false");
			
			RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/Details.jsp");
			dispatch.forward(request, response);
		} catch (ClassNotFoundException | SQLException | DatabaseException e) {
			e.printStackTrace();
		} 
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
