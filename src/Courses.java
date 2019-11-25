import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Courses {
	String name;
	ArrayList<CP> courseProducers;
	boolean queueActive;
	Queue<String> line;
	
	public Courses(String name, ArrayList<CP> courseProducers) {
		this.name = name;
		this.courseProducers = courseProducers;
		queueActive = false;
		line = new LinkedList<>();
	}
	
	public void setCourseName(String newName) {
		name = newName;
	}
	
	public String getCourseName() {
		return name;
	}
	
	public void addCP(CP newCP) {
		courseProducers.add(newCP);
	}
	
	public void removeCP(String nameCP) {
		int index = -1;
		for(int i = 0; i < courseProducers.size(); i++) {
			if(courseProducers.get(i).getName().equals(nameCP)) {
				index = i;
			}
		}
		if(index == -1) {
			return;
		}
		else {
			courseProducers.remove(index);
		}
	}
	
	public ArrayList<CP> showCPs(){
		return courseProducers;
	}
	
	public void toggleQueueActive() {
		queueActive = true;
	}
	
	public void toggleQueueDeactive() {
		queueActive = false;
	}
	
	public void enqueue(String studentName) {
		if(queueActive == false) {
			return;
		}
		line.add(studentName);
	}
	
	public void dequeue() {
		line.remove();
	}
	
	public void removeStudent(String studentName) {
		line.remove(studentName);
	}
	
	public void clearQueue() {
		for(int i = 0; i < line.size(); i++) {
			line.remove();
		}
	}
	
	public Queue<String> getQueue(){
		return line;
	}
}
