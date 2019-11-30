package models;

public class Location {
	final int id;
	final String name;
	public Location(int id, String name) {
		super();
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
