package util;

import java.util.ArrayList;
import java.util.Iterator;

public class ImmutableList<T> implements Iterable<T>{
	public ArrayList<T> list; 
	
	public ImmutableList(Iterable<T> iterable) {
		list = new ArrayList<T>();
		for(T item: iterable) {
			list.add(item);
		}
	}

	public Iterator<T> iterator() {
		return list.iterator();
	}
	
	public T get(int i) {
		return list.get(i);
	}
}
