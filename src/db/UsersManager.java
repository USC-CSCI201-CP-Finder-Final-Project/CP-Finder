package db;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

import models.User;
import models.UserType;

public class UsersManager extends TableManager {
	public UsersManager(Connection dbConnection) {
		super(dbConnection);
	}
	
	/**
	 * Attempts a login
	 * @param email the email of the user
	 * @param password the password of the user
	 * @return a login response enum of the result of the attempt
	 */
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
	
	/**
	 * Get a user
	 * @param email the email of the user to get 
	 * @return a user model of the requested user
	 * @throws DatabaseException the exception of a failed operation
	 */
	public User getUser(String email) throws DatabaseException {
		String getUserQuery = "SELECT user_id, name, email, password, preferred_name, is_cp, picture FROM users "
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
				Blob picture = results.getBlob("picture");
				UserType userType = results.getBoolean("is_cp") ? UserType.CP : UserType.Student;
				
				return new User(userID, name, userEmail, password, preferredName, picture.getBytes(1, (int) picture.length()), userType);
			}
			
			throw new ResourceNotFoundDatabaseException("No user found with that email");
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	
	/**
	 * Creates a user
	 * @param user the model of the user to create
	 * @throws DatabaseException the exception of a failed operation
	 */
	public void createUser(User user) throws DatabaseException{
		String createUserQuery = "INSERT INTO users (name, email, password, preferred_name, picture, is_cp) "
				+ "VALUES (?, ?, ?, ?, ?);";
		
		try {
			PreparedStatement createUser = dbConnection.prepareStatement(createUserQuery, Statement.RETURN_GENERATED_KEYS);
			createUser.setString(1, user.getName());
			createUser.setString(2, user.getEmail());
			createUser.setString(3, user.getPassword());
			createUser.setString(4, user.getPreferredName());
			createUser.setBlob(5, new ByteArrayInputStream(user.getImgData()));
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
	
	/**
	 * Updates a user
	 * @param email the email of the user to update
	 * @param updatedUser a user model with the updated fields
	 * @throws DatabaseException the exception of a failed operation
	 */
	public void updateUser(String email, User updatedUser) throws DatabaseException{
		String updateUserQuery = "UPDATE users SET name = ?, email = ?, password = ?, preferred_name = ?, picture = ?, is_cp = ? "
				+ "WHERE email = ?";
		
		try {
			PreparedStatement updateUser = dbConnection.prepareStatement(updateUserQuery);
			updateUser.setString(1, updatedUser.getName());
			updateUser.setString(2, updatedUser.getEmail());
			updateUser.setString(3, updatedUser.getPassword());
			updateUser.setString(4, updatedUser.getPreferredName());
			updateUser.setBlob(5, new ByteArrayInputStream(updatedUser.getImgData()));
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
