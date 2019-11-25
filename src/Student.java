import java.util.ArrayList;

public class Student extends User{
	
	ArrayList<Courses> activeClasses;
	
	public Student(String name, String prefName, String email, String password, String imageLink, 
			ArrayList<Courses> activeClasses) {
		
		super(name, prefName, email, password, imageLink);
		this.activeClasses = activeClasses;
	}
	
	public void addClass(Courses newClass) {
		activeClasses.add(newClass);
	}
	
	public void removeClass(String rClass) {
		int index = -1;
		for(int i = 0; i < activeClasses.size(); i++) {
			if(activeClasses.get(i).getCourseName().equals(rClass)) {
				index = i;
			}
		}
		
		if(index == -1) {
			return;
		}
		else {
			activeClasses.remove(index);
		}
	}
	public ArrayList<Courses> getClasses(){
		return activeClasses;
	}
}
