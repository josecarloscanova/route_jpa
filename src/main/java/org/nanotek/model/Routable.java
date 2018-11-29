package org.nanotek.model;

public interface Routable<T extends Station> {

	Destination<T> destination();
	
}
