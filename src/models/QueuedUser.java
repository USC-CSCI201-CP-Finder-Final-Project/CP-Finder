package models;

public class QueuedUser {
	private final int id;
	private final int position;
	private final User user;
	public QueuedUser(int id, int position, User user) {
		this.id = id;
		this.position = position;
		this.user = user;
	}
	
	public int getId() {
		return id;
	}
	
	public int getPosition() {
		return position;
	}
	
	public User getUser() {
		return user;
	}
}
