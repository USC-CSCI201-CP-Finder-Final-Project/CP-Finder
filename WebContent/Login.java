
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

import db.UsersManager;

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
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(CREDENTIALS_STRING);
			UsersManager um = new UsersManager(connection);
			LoginResponse response = um.logIn(email, password);
		} catch (SQLException | ClassNotFoundException sqle) {
			System.out.println(sqle.getMessage());
		}
		

		String error = "";
		String next = "/landingPage.jsp";

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
				st = connection.prepareStatement("SELECT * FROM users where email='" + email + "'");
				rs = st.executeQuery();

				int size = 0;
				if (rs != null) {
					rs.last();
					size = rs.getRow();
				}

				if (size != 0) {
					if (rs.getNString("password").equals(password)) {
						next = "/MainPage.jsp";
					} else {
						error += "Incorrect Password!";
						next = "/login.jsp";
					}
				}

				else {
					error += "This username doesn't exist!";
					next = "/login.jsp";
				}
			}

			
			LoginResponse loginResponse = usersManager.logIn(email, password);

			switch (loginResponse) {
			case AccountDoesNotExist:
				System.out.println("There is no account with that email");
				break;
			case IncorrectPassword:
				System.out.println("Incorrect password!");
				break;
			case ServerError:
				System.out.println("Server error, please try again later");
				break;
			case Success:
				System.out.println("Correct! You are now logged in");
				break;
			}
		}
		request.setAttribute("error", error);
		if (error.equals("")) {
			request.setAttribute("successful", true);
			Cookie user = new Cookie("user", email);
			user.setMaxAge(60 * 60 * 24);
			response.addCookie(user);
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
