package db;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import models.Course;
import models.Location;
import models.Session;
import models.Status;
import models.User;
import models.UserType;
import util.ImmutableList;

public class SessionsManager extends TableManager {

	public SessionsManager(Connection dbConnection) {
		super(dbConnection);
	}

	public ImmutableList<Session> getSessions(Course course, Boolean onlyOpen) throws DatabaseException{
		return getSessions(course.getId(), onlyOpen);
	}
	
	public ImmutableList<Session> getSessions(Boolean onlyOpen) throws DatabaseException{
		String getSessionsQuery = "SELECT session_id, sessions.course_id, sessions.user_id, sessions.location_id, sessions.status_id, sessions.course_id, "
				+ "is_active, created_at, last_update, closed_at, "
				+ "name, email, password, preferred_name, picture, is_cp, "
				+ "location_name, "
				+ "status, "
				+ "course_name "
				+ "FROM sessions "
				+ "LEFT JOIN users ON users.user_id = sessions.user_id "
				+ "LEFT JOIN locations ON locations.location_id = sessions.location_id "
				+ "LEFT JOIN statuses ON statuses.status_id = sessions.status_id "
				+ "LEFT JOIN courses ON courses.course_id = sessions.course_id ";
		if(onlyOpen) {
			getSessionsQuery += " AND closed_at IS NULL";
		}
		
		try {
			PreparedStatement getSessions = dbConnection.prepareStatement(getSessionsQuery);
			
			ResultSet results = getSessions.executeQuery();
			
			ArrayList<Session> sessions = new ArrayList<Session>();

			while(results.next()) {
				int userID = results.getInt("user_id");
				String name = results.getString("name");
				String userEmail = results.getString("email");
				String password = results.getString("password");
				String preferredName = results.getString("preferred_name");
				Blob picture = results.getBlob("picture");
				byte[] imgData = null;
				if (picture != null) {
					int blobLength = (int) picture.length();  
					imgData = picture.getBytes(1, blobLength);
					picture.free();
				}
				UserType userType = results.getBoolean("is_cp") ? UserType.CP : UserType.Student;
				User user = new User(userID, name, userEmail, password, preferredName, imgData, userType);
				
				int locationID = results.getInt("location_id");
				String locationName = results.getString("location_name");
				Location location = new Location(locationID, locationName);
				
				int statusID = results.getInt("status_id");
				String statusName = results.getString("status");
				Status status = new Status(statusID, statusName);
				
				
				String courseName = results.getString("course_name");
				int courseId = results.getInt("course_id");
				Course course = new Course(courseId, courseName);
				
				int sessionId = results.getInt("session_id");
				Boolean isActive = results.getBoolean("is_active");
				Date createdAt = results.getDate("created_at");
				Date updatedAt = results.getDate("last_update");
				Date closedAt = results.getDate("closed_at");
				sessions.add(new Session(sessionId, course, user, location, status, isActive, createdAt, updatedAt, closedAt));
			}
			
			return new ImmutableList<Session>(sessions);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new DatabaseException(e.getMessage());
		}
	}
	
	public ImmutableList<Session> getSessions(int courseId, Boolean onlyOpen) throws DatabaseException{
		String getSessionsQuery = "SELECT session_id, sessions.course_id, sessions.user_id, sessions.location_id, sessions.status_id, "
				+ "is_active, created_at, last_update, closed_at, "
				+ "name, email, password, preferred_name, picture, is_cp, "
				+ "location_name, "
				+ "status, "
				+ "course_name "
				+ "FROM sessions "
				+ "LEFT JOIN users ON users.user_id = sessions.user_id "
				+ "LEFT JOIN locations ON locations.location_id = sessions.location_id "
				+ "LEFT JOIN statuses ON statuses.status_id = sessions.status_id "
				+ "LEFT JOIN courses ON courses.course_id = sessions.course_id "
				+ "WHERE session_id = ?";
		if(onlyOpen) {
			getSessionsQuery += " AND closed_at IS NULL";
		}
		
		try {
			PreparedStatement getSessions = dbConnection.prepareStatement(getSessionsQuery);
			getSessions.setInt(1, courseId);
			
			ResultSet results = getSessions.executeQuery();
			
			ArrayList<Session> sessions = new ArrayList<Session>();

			while(results.next()) {
				int userID = results.getInt("user_id");
				String name = results.getString("name");
				String userEmail = results.getString("email");
				String password = results.getString("password");
				String preferredName = results.getString("preferred_name");
				Blob picture = results.getBlob("picture");
				byte[] imgData = null;
				if (picture != null) {
					int blobLength = (int) picture.length();  
					imgData = picture.getBytes(1, blobLength);
					picture.free();
				}
				UserType userType = results.getBoolean("is_cp") ? UserType.CP : UserType.Student;
				User user = new User(userID, name, userEmail, password, preferredName, imgData, userType);
				
				int locationID = results.getInt("location_id");
				String locationName = results.getString("location_name");
				Location location = new Location(locationID, locationName);
				
				int statusID = results.getInt("status_id");
				String statusName = results.getString("status");
				Status status = new Status(statusID, statusName);
				
				
				String courseName = results.getString("course_name");
				Course course = new Course(courseId, courseName);
				
				int sessionId = results.getInt("session_id");
				Boolean isActive = results.getBoolean("is_active");
				Date createdAt = results.getDate("created_at");
				Date updatedAt = results.getDate("last_update");
				Date closedAt = results.getDate("closed_at");
				sessions.add(new Session(sessionId, course, user, location, status, isActive, createdAt, updatedAt, closedAt));
			}
			
			return new ImmutableList<Session>(sessions);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new DatabaseException(e.getMessage());
		}
	}
	
	public Session getSession(User user, Boolean onlyOpen) throws DatabaseException {
		return getSession(user.getId(), onlyOpen);
	}
	
	public Session getSession(int userId, Boolean onlyOpen) throws DatabaseException{
		String getSessionsQuery = "SELECT session_id, sessions.course_id, sessions.user_id, sessions.location_id, sessions.status_id, sessions.course_id, "
				+ "is_active, created_at, last_update, closed_at, "
				+ "name, email, password, preferred_name, picture, is_cp, "
				+ "location_name, "
				+ "status, "
				+ "course_name "
				+ "FROM sessions "
				+ "LEFT JOIN users ON users.user_id = sessions.user_id "
				+ "LEFT JOIN locations ON locations.location_id = sessions.location_id "
				+ "LEFT JOIN statuses ON statuses.status_id = sessions.status_id "
				+ "LEFT JOIN courses ON courses.course_id = sessions.course_id "
				+ "WHERE sessions.user_id = ?";
		if(onlyOpen) {
			getSessionsQuery += " AND closed_at IS NULL";
		}
		
		try {
			PreparedStatement getSessions = dbConnection.prepareStatement(getSessionsQuery);
			getSessions.setInt(1, userId);
			
			ResultSet results = getSessions.executeQuery();

			if(results.next()) {
				int userID = results.getInt("user_id");
				String name = results.getString("name");
				String userEmail = results.getString("email");
				String password = results.getString("password");
				String preferredName = results.getString("preferred_name");
				Blob picture = results.getBlob("picture");
				byte[] imgData = null;
				if (picture != null) {
					int blobLength = (int) picture.length();  
					imgData = picture.getBytes(1, blobLength);
					picture.free();
				}
				UserType userType = results.getBoolean("is_cp") ? UserType.CP : UserType.Student;
				User user = new User(userID, name, userEmail, password, preferredName, imgData, userType);
				
				int locationID = results.getInt("location_id");
				String locationName = results.getString("location_name");
				Location location = new Location(locationID, locationName);
				
				int statusID = results.getInt("status_id");
				String statusName = results.getString("status");
				Status status = new Status(statusID, statusName);
				
				
				String courseName = results.getString("course_name");
				int courseId = results.getInt("course_id");
				Course course = new Course(courseId, courseName);
				
				int sessionId = results.getInt("session_id");
				Boolean isActive = results.getBoolean("is_active");
				Date createdAt = results.getDate("created_at");
				Date updatedAt = results.getDate("last_update");
				Date closedAt = results.getDate("closed_at");
				return new Session(sessionId, course, user, location, status, isActive, createdAt, updatedAt, closedAt);
			}
			
			return null;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new DatabaseException(e.getMessage());
		}
	}
	
	public void openSession(Course course, Location location, Status status, User user) throws DatabaseException {
		openSession(course.getId(), location.getId(), status.getId(), user.getId());
	}
	
	public void openSession(int courseId, int locationId, int statusId, int userId) throws DatabaseException {
		String createUserQuery = "INSERT INTO sessions (course_id, user_id, location_id, status_id) "
				+ "VALUES (?, ?, ?, ?);";
		
		try {
			PreparedStatement createUser = dbConnection.prepareStatement(createUserQuery, Statement.RETURN_GENERATED_KEYS);
			createUser.setInt(1, courseId);
			createUser.setInt(2, userId);
			createUser.setInt(3, locationId);
			createUser.setInt(4, statusId);
			
			createUser.executeUpdate();
			
		}
		catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	public void setSessionStatus(Session session, Status status) throws DatabaseException {
		String updateStatusQuery = "UPDATE sessions SET status_id = ? "
				+ "WHERE session_id = ?";
		
		try {
			PreparedStatement updateStatus = dbConnection.prepareStatement(updateStatusQuery);
			updateStatus.setInt(1, status.getId());
			updateStatus.setInt(2, session.getId());
			
			if(updateStatus.executeUpdate() == 0) {
				throw new ResourceNotFoundDatabaseException("That session does not exist");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	public void closeSession(Session session) throws DatabaseException {
		String closeSessionQuery = "UPDATE sessions SET closed_at = CURRENT_TIMESTAMP "
				+ "WHERE session_id = ?";
		
		try {
			PreparedStatement closeSession = dbConnection.prepareStatement(closeSessionQuery);
			closeSession.setInt(1, session.getId());
			
			if(closeSession.executeUpdate() == 0) {
				throw new ResourceNotFoundDatabaseException("That session does not exist");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	public void closeSession(User user) throws DatabaseException {
		String closeSessionQuery = "UPDATE sessions SET closed_at = CURRENT_TIMESTAMP "
				+ "WHERE user_id = ?";
		
		try {
			PreparedStatement closeSession = dbConnection.prepareStatement(closeSessionQuery);
			closeSession.setInt(1, user.getId());
			
			if(closeSession.executeUpdate() == 0) {
				throw new ResourceNotFoundDatabaseException("That session does not exist");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
}
