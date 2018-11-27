package org.nanotek.app.service;

/** 
 * 
 * 
 * @author jose.carlos.canova@gmail.com
 *
 * @param <G> The Sample
 * @param <R> Representation of the computing result.
 */
@FunctionalInterface
public interface Function<G,R> {

	R compute(G graph);
	
}
