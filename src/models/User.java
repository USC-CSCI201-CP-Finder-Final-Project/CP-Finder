package models;

import java.sql.Blob;

public class User {
	private int id;
	private String name;
	private String email;
	private String password;
	private String preferredName;
	private byte[ ] imgData;
	private UserType userType;
	
	public User(String name, String email, String password, String preferredName, byte[] imgData,
			UserType userType) {
		this.id = -1;
		this.name = name;
		this.email = email;
		this.password = password;
		this.preferredName = preferredName;
		this.imgData = imgData;
		this.userType = userType;
	}
	
	public User(int id, String name, String email, String password, String preferredName, byte[] imgData,
			UserType userType) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.preferredName = preferredName;
		this.imgData = imgData;
		this.userType = userType;
	}
	
	public String toString() {
		//String pictureString = new String(imgData);
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", preferredName="
				+ preferredName + ", userType=" + userType + "]";
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
		String pictureString = new String(imgData);
		return pictureString;
	}

	public void setPictureURL(String pictureString) {
		this.imgData = pictureString.getBytes();
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}
}
