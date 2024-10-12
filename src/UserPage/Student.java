package UserPage;

public class Student extends Account {
	
	
	private String course, section;
	
	public Student(String studentID, String username, String password, String fullname, String course, String section, int pin){
		super(studentID, username, password, fullname, pin);
		this.course = course;
		this.section = section; 
	}
	
	String getCourse() {
		return course;
	}
	
	String getSection() {
		return section;
	}
	
	void setCourse(String course) {
		this.course = course;
	}
	
	void setSection(String section) {
		this.section = section;
	}
	
}
