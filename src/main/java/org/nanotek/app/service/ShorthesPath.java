package org.nanotek.app.service;

/** 
 * 
 * 
 * @author jose.carlos.canova@gmail.com
 *
 * @param <G> The Graph
 * @param <R> Representation of the result computing
 */
@FunctionalInterface
public interface ShorthesPath<G,R> {

	R compute(G graph);
	
}
