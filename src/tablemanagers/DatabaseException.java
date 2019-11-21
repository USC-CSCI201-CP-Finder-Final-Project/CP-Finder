package tablemanagers;

public class DatabaseException extends Exception {
	private static final long serialVersionUID = 1L;

	public DatabaseException(String errorMessage) {
		super(errorMessage);
	}
}
