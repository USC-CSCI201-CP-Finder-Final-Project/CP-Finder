
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import db.DatabaseConnectionCreator;
import models.User;

@WebServlet("/UpdateProfile")
//@MultipartConfig(maxFileSize = 16177215)
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, maxFileSize = 1024 * 1024 * 50, maxRequestSize = 1024 * 1024 * 100)
public class UpdateProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UpdateProfile() {
        super();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		FileInputStream fis = null;
		
		User currentUser = (User) session.getAttribute("user");		
		String name = request.getParameter("name"); 
		String email = request.getParameter("email"); 
		String prefname = request.getParameter("prefname");
		String password = request.getParameter("password");
		//String pic = request.getParameter("picture");
		
		InputStream inputStream = null;
		
		//System.out.println(session.getAttribute("picturePath"));
		
		Part filePart = request.getPart("picture");
		
        if (filePart != null) {
            // prints out some information for debugging
            System.out.println(filePart.getName());
            System.out.println(filePart.getSize());
            System.out.println(filePart.getContentType());
            // obtains input stream of the upload file
            inputStream = filePart.getInputStream();
            
        }
		
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			connection = DatabaseConnectionCreator.createConnection();
			pstmt = connection.prepareStatement("UPDATE users SET name = ?, email = ?, preferred_name = ?, password = ?, picture = ? WHERE user_id = ?");
			pstmt.setString(1, name);
			pstmt.setString(2, email);
			pstmt.setString(3, prefname);
			pstmt.setString(4, password);
			
			if (inputStream != null) {
				pstmt.setBlob(5, inputStream);
            }
			pstmt.setInt(6, currentUser.getId());
			
			int row = pstmt.executeUpdate();
			if (row > 0) {
				System.out.println("[Settings] Picture inserted successfully");
			}
			else {
				System.out.println("[Settings] Picture not inserted successfully");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
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
			
			getServletContext().getRequestDispatcher("/MainPage.jsp").forward(request, response);
		}
		
		
	}

}
