

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import db.CoursesManager;
import db.DatabaseException;
import db.QueuesManager;
import db.SessionsManager;
import models.Course;
import models.Session;
import models.Status;
import models.User;
import models.UserQueue;
import util.ImmutableList;

/**
 * Servlet implementation class enqueue
 */
@WebServlet("/enqueue")
public class enqueue extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String CREDENTIALS_STRING = "jdbc:mysql://google/cpfinder?cloudSqlInstance=cpfinder-259622:us-west2:db1&socke"
			+ "tFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&user=root" +
		"&password=uscCs201!";
	static Connection connection = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public enqueue() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	int courseID = Integer.parseInt(request.getParameter("courseID"));
    	int userID = Integer.parseInt(request.getParameter("userID"));
    	String command = request.getParameter("command");
    	User myUser = (User)session.getAttribute("user");
    	PrintWriter out = response.getWriter();
    	try {
    		Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(CREDENTIALS_STRING);
			QueuesManager qm = new QueuesManager(connection);
			SessionsManager sm = new SessionsManager(connection);
			Session mySession = sm.getSession(myUser.getId(), true);
			String button ="";
			if (command.equals("add")) {
				qm.enqueue(courseID, userID);
				button = "<button onclick = 'enqueue();' id = 'add'>Remove me from the Queue</button></div>";
	    	}
	    	else if (command.equals("remove")) {
	    		qm.dequeue(courseID, userID);
	    		button = "<button onclick = 'enqueue();' id = 'add'>Add me to the Queue</button></div>";
	    	}
	    	else if (command.equals("in")) {
	    		Status status = new Status(1, "Checked In");
	    		sm.setSessionStatus(mySession, status);
	    	}
	    	else if (command.equals("out")) {
	    		Status status = new Status(2, "Checked Out");
	    		sm.setSessionStatus(mySession, status);
	    	}
			UserQueue uq = qm.getQueue(courseID);
			Gson gson = new Gson();
			String queueJson = gson.toJson(uq);
			request.setAttribute("queue", queueJson);
			session.setAttribute("queue", queueJson);
			
			String code = "";
			code += "<div id = 'queueHeader'><h3 class = 'queueText'>Students in Queue</h3>";
			code += "<div id = 'queue'>";
			for (int i = 0; i < uq.getQueuedUsers().size(); i++) {
				code += "<div id = "+ i + "class = 'queueDisplay'><div class = 'student'><div class = 'img'><img class = 'studentimg' src='profile.png'/>"
					+ "<p class = 'studentName'>"+(i+1)+". " + uq.getQueuedUsers().get(i).getUser().getName()+"</p></div></div></div>";
			}
			code += "</div>";
			code += button;

			
			out.print(code);
			out.flush();
			//RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/Details.jsp");
			//dispatch.forward(request, response);
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
