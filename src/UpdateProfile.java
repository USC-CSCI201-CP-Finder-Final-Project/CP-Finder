
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.DatabaseConnectionCreator;

@WebServlet("/UpdateProfile")
public class UpdateProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UpdateProfile() {
        super();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//HttpSession session = request.getSession();
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		FileInputStream fis = null;
		
		//int userID = (int) session.getAttribute("userid");
		String name = request.getParameter("name"); 
		String email = request.getParameter("email"); 
		String prefname = request.getParameter("prefname");
		String password = request.getParameter("password");
		String pic = request.getParameter("picture");
		
		try{
			connection = DatabaseConnectionCreator.createConnection();
    		File image = new File(pic);
			pstmt = connection.prepareStatement("UPDATE users SET name = ?, email = ?, preferred_name = ?, password = ?, picture = ? WHERE user_id = 1");
			pstmt.setString(1, name);
			pstmt.setString(2, email);
			pstmt.setString(3, prefname);
			pstmt.setString(4, password);
			fis = new FileInputStream(image);
			pstmt.setBinaryStream(5, (InputStream) fis, (int) (image.length()));
			int count = pstmt.executeUpdate();
			if (count > 0) {
				System.out.println("[Settings] Picture inserted successfully");
			}
			else {
				System.out.println("[Settings] Picture not inserted successfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try{
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (connection != null) connection.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/MainPage.jsp");
		dispatch.forward(request, response);
		
	}

}
