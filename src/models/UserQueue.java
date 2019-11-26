package models;

import java.util.Iterator;

import util.ImmutableList;

public class UserQueue implements Iterable<QueuedUser> {
	private final int courseId;
	private final ImmutableList<QueuedUser> queuedUsers;
	
	public UserQueue(int courseId, Iterable<QueuedUser> queuedUsers) {
		this.courseId = courseId;
		this.queuedUsers = new ImmutableList<QueuedUser>(queuedUsers);
	}

	public Iterator<QueuedUser> iterator() {
		return queuedUsers.iterator();
	}

	public int getCourseId() {
		return courseId;
	}

	public ImmutableList<QueuedUser> getQueuedUsers() {
		return queuedUsers;
	}
}
