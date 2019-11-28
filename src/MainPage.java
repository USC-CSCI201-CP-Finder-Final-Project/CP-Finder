
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class MainPage
 */
@WebServlet("/MainPage")
public class MainPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String CREDENTIALS_STRING = "jdbc:mysql://google/cpfinder?cloudSqlInstance=cpfinder-259622:us-west2:db1&socke"
		+ "tFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&user=root" +
	"&password=uscCs201!";
	static Connection connection = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainPage() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	PreparedStatement st = null;
		ResultSet rs = null;
		List<CPDisplay> mainPage = new ArrayList<CPDisplay>();
		Vector<Integer> courseIDs = new Vector<Integer>();
		Vector<Integer> userIDs = new Vector<Integer>();
		Vector<Integer> locationIDs = new Vector<Integer>();
		Vector<Integer> statusIDs = new Vector<Integer>();
		Vector<String> courses = new Vector<String>();
		Vector<String> names = new Vector<String>();
		Vector<Blob> pictures = new Vector<Blob>();
		Vector<String> locations = new Vector<String>();
		Vector<String> statuses = new Vector<String>();
    	try {
    		Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(CREDENTIALS_STRING);
			st = connection.prepareStatement("SELECT * FROM sessions;");
			rs = st.executeQuery();
			while (rs.next()) {
				int courseID = rs.getInt("course_id");
				int userID = rs.getInt("user_id");
				int locationID = rs.getInt("location_id");
				int statusID = rs.getInt("status_id");
				courseIDs.add(courseID);
				userIDs.add(userID);
				locationIDs.add(locationID);
				statusIDs.add(statusID);
			}
			
			for (int i = 0; i < courseIDs.size(); i++) {
				st = connection.prepareStatement("SELECT * FROM courses WHERE course_id =" + courseIDs.get(i) + ";");
				rs = st.executeQuery();
				// getting courses; will only execute once (unique course id)
				while (rs.next()) {
					String course = rs.getString("course_name");
					courses.add(course);
				}
				st = connection.prepareStatement("SELECT * FROM users WHERE user_id =" + userIDs.get(i) + ";");
				rs = st.executeQuery();
				// getting user info; will only execute once (unique user id)
				while (rs.next()) {
					String name = rs.getString("preferred_name");
					Blob picture = rs.getBlob("picture");
					names.add(name);
					pictures.add(picture);
				}
				st = connection.prepareStatement("SELECT * FROM locations WHERE location_id =" + locationIDs.get(i) + ";");
				rs = st.executeQuery();
				// getting user info; will only execute once (unique user id)
				while (rs.next()) {
					String location = rs.getString("location_name");
					locations.add(location);
				}
				st = connection.prepareStatement("SELECT * FROM statuses WHERE status_id =" + statusIDs.get(i) + ";");
				rs = st.executeQuery();
				// getting user info; will only execute once (unique user id)
				while (rs.next()) {
					String status = rs.getString("status");
					statuses.add(status);
				}
			}
			
			// after exiting for loop, CPDisplay list populated
			for (int i = 0; i < courses.size(); i++) {
				//Blob temp = pictures.get(i);
				//byte[ ] imgData = temp.getBytes(1, (int) temp.length());
				CPDisplay newCP = new CPDisplay(courses.get(i), courseIDs.get(i), names.get(i),
						userIDs.get(i), locations.get(i),
						statuses.get(i));
				mainPage.add(newCP);
			}
			
			Gson gson = new Gson();
			String cpJson = gson.toJson(mainPage);
			request.setAttribute("data", cpJson);
			session.setAttribute("data", cpJson);
			RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/MainPage.jsp");
			dispatch.forward(request, response);
    	} catch (SQLException | ClassNotFoundException sqle) {
    		System.out.println(sqle.getMessage());
    	} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (connection != null) {
					// conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println(sqle.getMessage());
			}
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
