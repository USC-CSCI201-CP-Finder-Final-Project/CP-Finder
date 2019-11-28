import java.sql.*;

public class CPDisplay {
	String course;
	int courseID;
	String name;
	int userID;
	byte[ ] imgData;
	String location;
	String status;
	
	public CPDisplay(String course, int courseID, String name, 
			int userID, String location, String status) {
		this.course = course;
		this.courseID = courseID;
		this.name = name;
		this.userID = userID;
		this.location = location;
		this.status = status;
	}
}
