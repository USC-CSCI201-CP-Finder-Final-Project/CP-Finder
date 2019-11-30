package db;

import java.sql.Connection;
import java.util.Scanner;

import models.User;
import models.UserType;

public class UsersManagerLoginTest {

	public static void main(String[] args) {
		Connection dbConn = DatabaseConnectionCreator.createConnection();
		UsersManager usersManager = new UsersManager(dbConn);
		
		try {
			usersManager.deleteUser("test@email.com");
		} catch (DatabaseException e1) {
			e1.printStackTrace();
		}
		
		User user = new User("John Smith", "test@email.com", "password123", null, new byte[100], UserType.Student);
		
		try {
			usersManager.createUser(user);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		
		System.out.println(user);
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Email: ");
		String email = scanner.next();
		
		System.out.print("Password: ");
		String password = scanner.next();
		
		LoginResponse loginResponse = usersManager.logIn(email, password);
		
		switch(loginResponse) {
		case AccountDoesNotExist:
			System.out.println("There is no account with that email");
			break;
		case IncorrectPassword:
			System.out.println("Incorrect password!");
			break;
		case ServerError:
			System.out.println("Server error, please try again later");
			break;
		case Success:
			System.out.println("Correct! You are now logged in");
			break;
		}
	}

}
