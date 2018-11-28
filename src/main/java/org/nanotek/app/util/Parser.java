package org.nanotek.app.util;

@FunctionalInterface
public interface Parser<I,P> {

	P parse(I input);
	
}
