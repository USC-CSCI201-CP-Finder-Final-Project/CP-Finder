package models;

public class Status {
	private final int id;
	private final String name;
	
	public Status(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
