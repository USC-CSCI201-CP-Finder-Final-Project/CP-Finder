package models;

public class User {
	private int id;
	private String name;
	private String email;
	private String password;
	private String preferredName;
	private String pictureURL;
	private UserType userType;
	private int defaultCourseID;
	
	public User(String name, String email, String password, String preferredName, String pictureURL, 
			UserType userType) {
		this.id = -1;
		this.name = name;
		this.email = email;
		this.password = password;
		this.preferredName = preferredName;
		this.pictureURL = pictureURL;
		this.userType = userType;
		this.defaultCourseID = -1;
	}
	
	public User(int id, String name, String email, String password, String preferredName, String pictureURL,
			UserType userType, int defaultCourseID) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.preferredName = preferredName;
		this.pictureURL = pictureURL;
		this.userType = userType;
		this.defaultCourseID = defaultCourseID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPreferredName() {
		return preferredName;
	}

	public void setPreferredName(String preferredName) {
		this.preferredName = preferredName;
	}

	public String getPictureURL() {
		return pictureURL;
	}

	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public int getDefaultCourseID() {
		return defaultCourseID;
	}

	public void setDefaultCourseID(int defaultCourseID) {
		this.defaultCourseID = defaultCourseID;
	}
}
