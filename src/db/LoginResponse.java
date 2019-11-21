package db;

public enum LoginResponse {
	Success("Success"),
	AccountDoesNotExist("Account does not exist!"),
	IncorrectPassword("Incorrect password!"),
	ServerError("Server error, try again later");
	
	private final String message;
	
	private LoginResponse(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
}