package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionCreator {
	private final static String databaseName = "cpfinder";
	private final static String instanceConnectionName = "cpfinder-259622:us-west2:db1";
	private final static String username = "root";
	private final static String password = "uscCs201!";
	
	public static Connection createConnection() {
		try {
			return DriverManager.getConnection("jdbc:mysql://google/" + databaseName + "?cloudSqlInstance=" + instanceConnectionName + 
					"&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&user=" + username +
					"&password=" + password);
		} catch (SQLException e) {
			return null;
		}
	}
}
