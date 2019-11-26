package models;

public class Course {
	private final int id;
	private final String title;

	public Course(int id, String title) {
		this.id = id;
		this.title = title;
	}

	public int getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
}
