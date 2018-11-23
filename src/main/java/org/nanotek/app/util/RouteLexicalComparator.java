package org.nanotek.app.util;

import java.util.Comparator;

import org.nanotek.model.jpa.Route;

public class RouteLexicalComparator implements Comparator<Route>{

	@Override
	public int compare(Route route1, Route route2) {
		String lex0 = lexicalRoute (route1) ; 
		String lex1 = lexicalRoute (route2);
		return lex0.compareTo(lex1);
	}
	
	private String lexicalRoute(Route route) {
		return route.getFrom().getStationLabel().concat(route.getTo().getStationLabel());
	}

}
