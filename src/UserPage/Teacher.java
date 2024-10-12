package UserPage;

public class Teacher extends Account{
	
	String department;
	
	public Teacher(String teacherID, String username, String password, String fullname, String department, int pin) {
		super(teacherID, username, password, fullname, pin);
		this.department = department;
	}
	
	void setDepartment(String department) {
		this.department = department;
	}
	
	String getDepartment() {
		return department;
	}
	
}
