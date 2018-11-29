package org.nanotek.app.service;

/**
 * 
 * Abstract class to test Dfs - or to be extended by a decorator of the strategy (the method to compute the DFS).
 * 
 * @author jose.carlos.canova@gmail.com
 *
 * @param <N>
 * @param <P>
 * 
 */
public class AbstractDfs<N,P> implements Dfs<N,P>{

	Function<N,P> strategy;
	
	public AbstractDfs(Function<N,P> strategy) { 
		this.strategy = strategy;
	}
	
	@Override
	public P apply(N t) {
		return strategy.compute(t);
	}

}
