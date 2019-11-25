
public class User {
	String name;
	String prefName;
	String email;
	String password;
	String imageLink;
	
	public User(String name, String prefName, String email, String password, String imageLink) {
		this.name = name;
		this.prefName = prefName;
		this.email = email;
		this.password = password;
		this.imageLink = imageLink;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrefName() {
		return prefName;
	}

	public void setPrefName(String prefName) {
		this.prefName = prefName;
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

	public String getImageLink() {
		return imageLink;
	}

	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}
}
