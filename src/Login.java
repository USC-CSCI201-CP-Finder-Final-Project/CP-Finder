
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import db.DatabaseException;
import db.LoginResponse;
import db.UsersManager;
import models.User;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String CREDENTIALS_STRING = "jdbc:mysql://google/cpfinder?cloudSqlInstance=cpfinder-259622:us-west2:db1&socke"
			+ "tFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&user=root" + "&password=uscCs201!";
	static Connection connection = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		String error = "";
		String next = "MainPage.java";

		if (email.equals("")) {
			error += "Please Input a valid email";
			next = "/login.jsp";
		}
		if (!email.endsWith("@usc.edu")) {
			error += "Please Input a valid email";
			next = "/login.jsp";
		}
		if (password.equals("")) {
			error += "Password cannot be empy.";
			next = "/login.jsp";
		}
		if (error.equals("")) {
			PreparedStatement st = null;
			ResultSet rs = null;

			try {
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection(CREDENTIALS_STRING);
				UsersManager um = new UsersManager(connection);
				LoginResponse loginResponse = um.logIn(email, password);
				switch (loginResponse) {
				case Success:
					User myUser = um.getUser(email);
					Gson gson = new Gson();
					String userString = gson.toJson(myUser);
					session.setAttribute("userJson", userString);
					request.setAttribute("userJson", userString);
					session.setAttribute("user", myUser);
					request.setAttribute("user", myUser);
					session.setAttribute("successful", true);
					request.setAttribute("successful", true);
					break;
				default:
					session.setAttribute("error", loginResponse.getMessage());
					request.setAttribute("error", loginResponse.getMessage());
					next = "/login.jsp";
				}
			}
			catch (SQLException | ClassNotFoundException | DatabaseException sqle) {
				System.out.println(sqle.getMessage());
			}
		}

		RequestDispatcher dispatch = getServletContext().getRequestDispatcher(next);

		try {
			dispatch.forward(request, response);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}

	}

}
