package UserPage;

public abstract class Account {
	
	private String ID, username, password, fullname;
	private int pin;
	
	Account(String ID,String username, String password, String fullname, int pin) {
		this.ID = ID;
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.pin = pin;
	}
	
	String getID() {
		return ID;
	}
	
	String getUsername() {
		return username;
	}
	
	String getFullname() {
		return fullname;
	}
	
	String getPassword() {
		return password;
	}
	
	int getPin() {
		return pin;
	}
	
	void setPassword(String password) {
		this.password = password;
	}
	
	void setUsername(String username) {
		this.username = username;
	}
	
	void setFullname(String fullname) {
		this.fullname = fullname;
	}

}
