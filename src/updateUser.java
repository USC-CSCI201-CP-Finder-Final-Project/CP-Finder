

import java.io.IOException;
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

import db.DatabaseException;
import db.SessionsManager;
import db.UsersManager;
import models.Session;
import models.User;
import models.UserType;
import util.ImmutableList;

/**
 * Servlet implementation class updateUser
 */
@WebServlet("/updateUser")
public class updateUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String CREDENTIALS_STRING = "jdbc:mysql://google/cpfinder?cloudSqlInstance=cpfinder-259622:us-west2:db1&socke"
			+ "tFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&user=root" +
		"&password=uscCs201!";
		static Connection connection = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public updateUser() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	User user = (User)session.getAttribute("user");
    	String name = request.getParameter("name");
    	String email = request.getParameter("email");
    	String prefName = request.getParameter("prefName");
    	String password = request.getParameter("password");
    	User newUser = new User(name, email, password, prefName, new byte[100], user.getUserType());
    	Gson gson = new Gson();
    	try {
    		Class.forName("com.mysql.jdbc.Driver");
    		connection = DriverManager.getConnection(CREDENTIALS_STRING);
        	UsersManager um = new UsersManager(connection);
        	um.updateUser(user.getEmail(), newUser);
        	String userString = gson.toJson(user);
			session.setAttribute("userJson", userString);
			request.setAttribute("userJson", userString);
			request.setAttribute("user", user);
			session.setAttribute("user", user);
        	
    	} catch (SQLException | ClassNotFoundException | DatabaseException sqle) {
    		System.out.println(sqle.getMessage());
    	} 
		RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/Settings.jsp");
		dispatch.forward(request, response);
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
