package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.Course;
import util.ImmutableList;

public class CoursesManager extends TableManager{
	public CoursesManager(Connection dbConnection) {
		super(dbConnection);
	}
	
	/**
	 * Get the complete list of available courses
	 * @return an immutable list of the courses
	 * @throws DatabaseException
	 */
	public ImmutableList<Course> getCourses() throws DatabaseException{
		String getCoursesQuery = "SELECT course_id, course_name FROM courses";
		
		try {
			PreparedStatement getUser = dbConnection.prepareStatement(getCoursesQuery);
			
			ResultSet results = getUser.executeQuery();
			
			ArrayList<Course> courses = new ArrayList<Course>();
			
			while(results.next()) {
				int courseId = results.getInt("course_id");
				String courseName = results.getString("course_name");
				Course course = new Course(courseId, courseName);
				courses.add(course);
			}
			
			return new ImmutableList<Course>(courses);
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
}
