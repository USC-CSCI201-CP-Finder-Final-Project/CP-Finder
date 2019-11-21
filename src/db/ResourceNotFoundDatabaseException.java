package db;

public class ResourceNotFoundDatabaseException extends DatabaseException {
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundDatabaseException(String errorMessage) {
		super(errorMessage);
	}

}
