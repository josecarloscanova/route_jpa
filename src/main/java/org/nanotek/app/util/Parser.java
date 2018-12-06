package org.nanotek.app.util;

@FunctionalInterface
public interface Parser<R , I> {

	R parse(I e);
	
}
