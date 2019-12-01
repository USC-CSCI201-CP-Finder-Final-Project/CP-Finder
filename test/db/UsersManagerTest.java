package db;

import java.sql.Connection;

import models.Session;
import models.User;
import models.UserType;
import util.ImmutableList;

public class UsersManagerTest {
	public static void main(String[] Args) {
		Connection dbConn = DatabaseConnectionCreator.createConnection();
		UsersManager usersManager = new UsersManager(dbConn);
		
		try {
			usersManager.deleteUser("drew.cutch@gmail.com");
		} catch (DatabaseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		User user = new User("Drew", "drew.cutch@gmail.com", "password123", null, new byte[100], UserType.Student);
		
		try {
			usersManager.createUser(user);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			System.out.println(usersManager.getUser("drew.cutch@gmail.com"));
		} catch (DatabaseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		SessionsManager sessionsManager = new SessionsManager(dbConn);
		try {
			ImmutableList<Session> sessions = sessionsManager.getSessions(0, true);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
