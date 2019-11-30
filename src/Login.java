
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
			error += "Password cannot be empty.";
			next = "/login.jsp";
		}
		if (error.equals("")) {
			LoginResponse loginResponse = usersManager.logIn(email, password);

			switch (loginResponse) {
			case AccountDoesNotExist:
				error += "There is no account with that email.";
				next = "/login.jsp";
				break;
			case IncorrectPassword:
				error += "Incorrect password!";
				next = "/login.jsp";
				break;
			case ServerError:
				error += "Server error, please try again later";
				next = "/login.jsp";
				break;
			case Success:
				//set session attribute of user
				//set it equal to some userJson object
				user = User.getUser(email);
				Gson gson = new Gson();
				String userJson = gson.toJson(user);
				session.setAttribute("user", userJson);
				error += "Correct! You are now logged in";
				next = "/MainPage.jsp";
				break;
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
