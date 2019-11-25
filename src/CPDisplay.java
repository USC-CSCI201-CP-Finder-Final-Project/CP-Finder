import java.sql.*;

public class CPDisplay {
	private String course;
	private String name;
	private Blob image;
	private byte[ ] imgData;
	private int locationID;
	
	public CPDisplay(String course, String name, Blob image, byte[ ]
			imgData, int locationID) {
		this.course = course;
		this.name = name;
		this.image = image;
		this.imgData = imgData;
		this.locationID = locationID;
	}
	
	public String getCourse() {
		return course;
	}
	
	public String getName() {
		return name;
	}
	
	public byte[ ] getImage() {
		return imgData;
	}
	
	public int getLocation() {
		return locationID;
	}
}
