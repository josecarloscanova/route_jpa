package org.nanotek.app.util;

import java.util.Comparator;

import org.nanotek.model.jpa.Destination;

/** 
 * @author Jose Canova.
 */
public class MinDestinationComparator implements Comparator <Destination>{

	@Override
	public int compare(Destination arg0, Destination arg1) {
		int result = Integer.MAX_VALUE;
		if(arg0.getDistance() > arg1.getDistance()) { 
			result = 1;
		}else if (arg0.getDistance() < arg1.getDistance()) { 
			result = -1;
		}else if(arg0.getDistance() == arg1.getDistance()) {
			result = new RouteLexicalComparator().compare(arg0.getRoute(), arg1.getRoute());
		}
		return result;
	}
}
