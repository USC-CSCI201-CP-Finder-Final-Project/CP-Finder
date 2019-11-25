
public class CP extends User{
	Courses clas;
	String location;
	boolean active;
	
	public CP(String name, String prefName, String email, String password, String imageLink, Courses clas) {
		super(name, prefName, email, password, imageLink);
		this.clas = clas;
		location = "";
		active = false;
	}
	
	public void setCourse(Courses newClass) {
		clas = newClass;
	}
	
	public Courses getCourse() {
		return clas;
	}
	
	public void setLocation(String loc) {
		location = loc;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void toggleActive() {
		active = true;
	}
	
	public void toggleDeactive() {
		active = false;
	}
	
	public boolean isActive() {
		return active;
	}
}
