package org.nanotek.app.util;

@FunctionalInterface
public interface Parser<P> {

	P parse(String input , String regex);
	
}
