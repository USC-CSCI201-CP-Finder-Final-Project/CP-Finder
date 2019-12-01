package db;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.Course;
import models.QueuedUser;
import models.User;
import models.UserQueue;
import models.UserType;

public class QueuesManager extends TableManager {
	public QueuesManager(Connection dbConnection) {
		super(dbConnection);
	}
	
	public UserQueue getQueue(Course course) throws DatabaseException{
		return getQueue(course.getId());
	}
	
	public UserQueue getUpdatedQueue(UserQueue userQueue) throws DatabaseException{
		return getQueue(userQueue.getCourseId());
	}
	
	public UserQueue getQueue(int courseId) throws DatabaseException {
		String getQueueQuery = "SELECT queued_user_id, course_id, "
				+ "queued_users.user_id, name, email, password, preferred_name, picture, is_cp "
				+ "FROM queued_users "
				+ "LEFT JOIN users ON users.user_id = queued_users.user_id "
				+ "WHERE course_id = ? "
				+ "ORDER BY queued_user_id;";
		
		try {
			PreparedStatement getQueue = dbConnection.prepareStatement(getQueueQuery);
			getQueue.setInt(1, courseId);
			
			ResultSet results = getQueue.executeQuery();
			
			ArrayList<QueuedUser> queuedUsers = new ArrayList<QueuedUser>();
			int userIndex = 0;

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
				
				int queuedUserId = results.getInt("queued_user_id");
				
				QueuedUser queuedUser = new QueuedUser(queuedUserId, userIndex, user);
				
				queuedUsers.add(queuedUser);
				
				userIndex++;
			}
			
			return new UserQueue(courseId, queuedUsers);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new DatabaseException(e.getMessage());
		} 
	}
	
	public void enqueue(Course course, User user) throws DatabaseException {
		enqueue(course.getId(), user.getId());
	}
	
	public void enqueue(int courseId, User user) throws DatabaseException {
		enqueue(courseId, user.getId());
	}
	
	public void enqueue(int courseId, int userId) throws DatabaseException {
		String enqueueQuery = "INSERT INTO queued_users (course_id, user_id) "
				+ "VALUES (?, ?);";
		
		try {
			PreparedStatement enqueue = dbConnection.prepareStatement(enqueueQuery);
			enqueue.setInt(1, courseId);
			enqueue.setInt(2, userId);
			
			enqueue.executeUpdate();
			
		}
		catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	public void dequeue(Course course, User user) throws DatabaseException {
		dequeue(course.getId(), user.getId());
	}
	
	public void dequeue(int courseId, User user) throws DatabaseException {
		dequeue(courseId, user.getId());
	}
	
	public void dequeue(int courseId, int userId) throws DatabaseException {
		System.out.println(courseId + " " + userId);
		String dequeueQuery = "DELETE FROM queued_users "
				+ "WHERE user_id = ? AND course_id = ?";
		
		try {
			PreparedStatement dequeue = dbConnection.prepareStatement(dequeueQuery);
			dequeue.setInt(1, userId);
			dequeue.setInt(2, courseId);
			
			dequeue.executeUpdate();
			
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	public void dequeue(QueuedUser queuedUser) throws DatabaseException {
		String dequeueQuery = "DELETE FROM queued_users WHERE queued_user_id = ?";
		
		try {
			PreparedStatement dequeue = dbConnection.prepareStatement(dequeueQuery);
			dequeue.setInt(1, queuedUser.getId());
			
			dequeue.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
}
