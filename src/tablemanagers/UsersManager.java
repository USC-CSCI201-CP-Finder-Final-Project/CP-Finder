package tablemanagers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.User;
import models.UserType;

public class UsersManager extends TableManager {
	public UsersManager(Connection dbConnection) {
		super(dbConnection);
	}
	
	public LoginResponse logIn(String email, String password) {
		User user;
		try {
			user = getUser(email);
			
			if(user == null)
				return LoginResponse.AccountDoesNotExist;
			
			if(user.getPassword().equals(password))
				return LoginResponse.Success;
			
			return LoginResponse.IncorrectPassword;
		} catch (DatabaseException e) {
			return LoginResponse.ServerError;
		}
	}
	
	public User getUser(String email) throws DatabaseException {
		String getUserQuery = "SELECT user_id, name, email, password, preferred_name, picture_url, user_type, default_course_id FROM users "
				+ "WHERE email = ? ";
		
		try {
			PreparedStatement getUser = dbConnection.prepareStatement(getUserQuery);
			getUser.setString(1, email);
			
			ResultSet results = getUser.executeQuery();
			
			if(results.next()) {
				int userID = results.getInt("user_id");
				String name = results.getString("name");
				String userEmail = results.getString("email");
				String password = results.getString("password");
				String preferredName = results.getString("preferred_name");
				String pictureURL = results.getString("picture_url");
				UserType userType = results.getBoolean("user_type") ? UserType.CP : UserType.Student;
				int defaultCourseID = results.getInt("default_course_id");
				
				return new User(userID, name, userEmail, password, preferredName, pictureURL, userType, defaultCourseID);
			}
			
			return null;
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
}
