package org.nanotek.app.util;

import java.util.Comparator;

import org.nanotek.model.jpa.Destination;
import org.nanotek.model.jpa.Route;

/** 
 * @author Usuario
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
			result = 0;
		}
		if (result == 0) { 
			String lex0 = lexical (arg0.getRoute()) ; 
			String lex1 = lexical (arg1.getRoute());
			result = lex0.compareTo(lex1);
		}
		return result;
	}

	private String lexical(Route route) {
		return route.getFrom().getStationLabel() + route.getDestination().getStationLabel();
	}
}
