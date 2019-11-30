
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
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String CREDENTIALS_STRING = "jdbc:mysql://google/cpfinder?cloudSqlInstance=cpfinder-259622:us-west2:db1&socke"
			+ "tFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&user=root" +
			"&password=uscCs201!";
	static Connection connection = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Register() {
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
		String firstname = request.getParameter("fName");
		String lastname = request.getParameter("lName");
		String preferredname = request.getParameter("pName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String isCP = request.getParameter("isCP");
		int cp = 0;
		int student = 0;
		int is_CP = 0;

		String error = "";
		String next = "/landingPage.jsp";

		if (firstname.equals("")) {
			error += "First Name cannot be empty.";
			next = "/Register.jsp";
		}

		if (lastname.equals("")) {
			error += "Last Name cannot be empty.";
			next = "/Register.jsp";
		}

		if (preferredname.equals("")) {
			error += "Preferred Name cannot be empty.";
			next = "/Register.jsp";
		}

		if (email.equals("")) {
			error += "Email cannot be left empty.";
			next = "/Register.jsp";
		}

		if (password.equals("")) {
			error += "Password cannot be left empty.";
			next = "/Register.jsp";
		}
		
		if ("CP".equals(isCP)) {
			cp = 1;
		}
		
		else if ("student".equals(isCP)) {
			student = 1;
		}
		
		if(cp==1) {
			user = UserType.student
		}

		if (error.equals("")) {

			PreparedStatement st = null;
			ResultSet rs = null;

			try {
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection(CREDENTIALS_STRING);
				st = connection.prepareStatement("SELECT * FROM users where preferred_name='" + preferredname + "'");
				rs = st.executeQuery();

				int size = 0;
				if (rs != null) {
					rs.last();
					size = rs.getRow();
				}

				if (size == 0) {
					st.executeUpdate(
							"INSERT into users (name, email, password, preferred_name) values ('" + firstname + lastname + "','" + email + "','" + password + "','" + preferredname + "')");
					next = "/MainPage.jsp";
				}

				else {
					error += "This username is already taken!";
					next = "/Register.jsp";
				}
			}

			catch (SQLException | ClassNotFoundException sqle) {
				System.out.println(sqle.getMessage());
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
		User user = new User(firstname + " " + lastname, email, password, preferredname, new byte[100], )
	}
}
