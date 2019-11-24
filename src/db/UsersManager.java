package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

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
			
			if(user.getPassword().equals(password))
				return LoginResponse.Success;
			
			return LoginResponse.IncorrectPassword;
		} catch(ResourceNotFoundDatabaseException e) {
			return LoginResponse.AccountDoesNotExist;
		} catch (DatabaseException e) {
			return LoginResponse.ServerError;
		}
	}
	
	public User getUser(String email) throws DatabaseException {
		String getUserQuery = "SELECT user_id, name, email, password, preferred_name, picture_url, user_type FROM users "
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
				
				return new User(userID, name, userEmail, password, preferredName, pictureURL, userType);
			}
			
			throw new ResourceNotFoundDatabaseException("No user found with that email");
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	public void createUser(User user) throws DatabaseException{
		String createUserQuery = "INSERT INTO users (name, email, password, preferred_name, picture_url, is_cp) "
				+ "VALUES (?, ?, ?, ?, ?, ?);";
		
		try {
			PreparedStatement createUser = dbConnection.prepareStatement(createUserQuery);
			createUser.setString(1, user.getName());
			createUser.setString(2, user.getEmail());
			createUser.setString(3, user.getPassword());
			createUser.setString(4, user.getPreferredName());
			createUser.setString(5, user.getPictureURL());
			createUser.setBoolean(6, user.getUserType() == UserType.CP);
			
			createUser.executeUpdate();
			
			ResultSet generatedKeys = createUser.getGeneratedKeys();
			if(generatedKeys.next()) {
				user.setId(generatedKeys.getInt(1));
			}
			else {
				throw new DatabaseException("Resource could not be created");
			}
			
		} catch (SQLIntegrityConstraintViolationException e) {
			throw new UniqueFieldCollisionDatabaseException("Email is already in use");
		}
		catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	public void updateUser(String email, User updatedUser) throws DatabaseException{
		String updateUserQuery = "UPDATE users SET name = ?, email = ?, password = ?, preferred_name = ?, picture_url = ?, is_cp = ? "
				+ "WHERE email = ?";
		
		try {
			PreparedStatement updateUser = dbConnection.prepareStatement(updateUserQuery);
			updateUser.setString(1, updatedUser.getName());
			updateUser.setString(2, updatedUser.getEmail());
			updateUser.setString(3, updatedUser.getPassword());
			updateUser.setString(4, updatedUser.getPreferredName());
			updateUser.setString(5, updatedUser.getPictureURL());
			updateUser.setBoolean(6, updatedUser.getUserType() == UserType.CP);
			updateUser.setString(7, updatedUser.getEmail());
			
			if(updateUser.executeUpdate() == 0) {
				throw new ResourceNotFoundDatabaseException("No user with that email exists");
			}
		} catch (SQLIntegrityConstraintViolationException e) {
			throw new UniqueFieldCollisionDatabaseException("Email is already in use");
		}
		catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		
	}
	
	
	/**
	 * Deletes a user from the Users table
	 * @param email the email of the user to delete
	 * @throws DatabaseException the exception of a failed operation
	 */
	public void deleteUser(String email) throws DatabaseException{
		String deleteUserQuery = "DELETE FROM users WHERE email = ?";
		
		try {
			PreparedStatement deleteUser = dbConnection.prepareStatement(deleteUserQuery);
			deleteUser.setString(1, email);
			
			if(deleteUser.executeUpdate() == 0) {
				throw new ResourceNotFoundDatabaseException("No user with that email exists");
			}
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
}
