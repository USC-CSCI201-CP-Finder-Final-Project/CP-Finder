package db;

public class UniqueFieldCollisionDatabaseException extends DatabaseException {
	private static final long serialVersionUID = 1L;

	public UniqueFieldCollisionDatabaseException(String errorMessage) {
		super(errorMessage);
	}

}
