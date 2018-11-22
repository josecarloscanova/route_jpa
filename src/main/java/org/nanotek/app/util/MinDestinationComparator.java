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
			String lex0 = lexicalRoute (arg0.getRoute()) ; 
			String lex1 = lexicalRoute (arg1.getRoute());
			result = lex0.compareTo(lex1);
		}
		return result;
	}

	private String lexicalRoute(Route route) {
		return route.getFrom().getStationLabel().concat(route.getDestination().getStationLabel());
	}
}
