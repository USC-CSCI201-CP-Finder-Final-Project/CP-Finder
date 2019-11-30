package models;

import java.sql.Date;

public class Session {
	private final int id;
	private final Course course;
	private final User CP;
	private final Location location;
	private final Status status;
	private final Boolean isActive;
	private final Date createdAt;
	private final Date lastUpdate;
	private final Date closedAt;
	
	public Session(int id, Course course, User user, Location location, Status status, Boolean isActive, Date createdAt,
			Date lastUpdate, Date closedAt) {
		super();
		this.id = id;
		this.course = course;
		this.CP = user;
		this.location = location;
		this.status = status;
		this.isActive = isActive;
		this.createdAt = createdAt;
		this.lastUpdate = lastUpdate;
		this.closedAt = closedAt;
	}

	public int getId() {
		return id;
	}

	public Course getCourse() {
		return course;
	}

	public User getUser() {
		return CP;
	}

	public Location getLocation() {
		return location;
	}

	public Status getStatus() {
		return status;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public Date getClosedAt() {
		return closedAt;
	}
}
