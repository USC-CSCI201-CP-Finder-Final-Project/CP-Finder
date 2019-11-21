package tablemanagers;
import java.sql.*;

public abstract class TableManager {
	protected final Connection dbConnection;
	
	public TableManager(Connection dbConnection) {
		this.dbConnection = dbConnection;
	}
}
