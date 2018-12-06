package org.nanotek.app.test.graph;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class LinkedListComparator<T> implements Comparator<T>{

	
	private LinkedList<T> list;

	public LinkedListComparator(LinkedList<T> theList) { 
		this.list = theList;
	}
	
	@Override
	public int compare(T o1, T o2) {
		
		if (!list.contains(o1) || !list.contains(o2)) { 
			throw new RuntimeException("Error on comparison");
		}
		
		if(o1.equals(o2))
		return 0;
		
		return list.indexOf(o1) > list.indexOf(o2) ? 1 : -1;
	}
	
}
