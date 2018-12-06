package org.nanotek.app.util;

@FunctionalInterface
public interface Parser<P,I,E> {

	P parse(I i  , E e);
	
}
