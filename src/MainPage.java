

import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MainPage
 */
@WebServlet("/MainPage")
public class MainPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String CREDENTIALS_STRING = "jdbc:mysql://google/bookworm?cloudSqlInstance=carbon-shadow-255423:us-central1:assignment3&socketFactory"
			+ "=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&user=root&password=uscCs201!";
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
    	PreparedStatement st = null;
		ResultSet rs = null;
		List<CPDisplay> mainPage = new ArrayList<CPDisplay>();
    	try {
    		Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(CREDENTIALS_STRING);
			st = connection.prepareStatement("SELECT * FROM cps;");
			rs = st.executeQuery();
			while (rs.next()) {
				String course = rs.getString("course");
				String name = rs.getString("name");
				Blob image = rs.getBlob("image");
				byte[ ] imgData = image.getBytes(1, (int) image.length());
				int locationID = rs.getInt("location");
				CPDisplay newCP = new CPDisplay(course, name, image, imgData, locationID);
				mainPage.add(newCP);
			}
    	} catch (SQLException sqle) {
    		
    	} catch (ClassNotFoundException cnfe) {
    		
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
