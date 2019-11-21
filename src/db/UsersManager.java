package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
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
				UserType userType = results.getBoolean("is_cp") ? UserType.CP : UserType.Student;
				int defaultCourseID = results.getInt("default_course_id");
				
				return new User(userID, name, userEmail, password, preferredName, pictureURL, userType, defaultCourseID);
			}
			
			return null;
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	public void createUser(User user) throws DatabaseException{
		String createUserQuery = "INSERT INTO users (name, email, password, preferred_name, picture_url, is_cp, default_course_id) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?);";
		
		try {
			PreparedStatement createUser = dbConnection.prepareStatement(createUserQuery);
			createUser.setString(1, user.getName());
			createUser.setString(2, user.getEmail());
			createUser.setString(3, user.getPassword());
			createUser.setString(4, user.getPreferredName());
			createUser.setString(5, user.getPictureURL());
			createUser.setBoolean(6, user.getUserType() == UserType.CP);
			createUser.setInt(7, user.getDefaultCourseID());
			
			createUser.executeUpdate();
		} catch (SQLIntegrityConstraintViolationException e) {
			throw new UniqueFieldCollisionDatabaseException("Email is already in use");
		}
		catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
}
