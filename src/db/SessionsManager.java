package db;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.Course;
import models.Location;
import models.QueuedUser;
import models.Session;
import models.Status;
import models.User;
import models.UserQueue;
import models.UserType;
import util.ImmutableList;

public class SessionsManager extends TableManager {

	public SessionsManager(Connection dbConnection) {
		super(dbConnection);
	}

	public ImmutableList<Session> getSessions(Course course) throws DatabaseException{
		return getSessions(course.getId());
	}
	
	public ImmutableList<Session> getSessions(int courseId) throws DatabaseException{
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
				+ "WHERE session_id = ? ";
		System.out.println(getSessionsQuery);
		
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
				String statusName = results.getString("status_name");
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
}
