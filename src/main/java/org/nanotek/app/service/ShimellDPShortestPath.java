package org.nanotek.app.service;

import com.google.common.collect.Table;
import com.google.common.graph.ValueGraph;

/**
 * Not yet tested. 
 * 
 * @author jose.carlos.canova@gmail.com
 *
 * @param <N>
 */
public class ShimellDPShortestPath<N extends Comparable<?>> extends SimpleShortestPath<N,Integer>  {

	@Override
	public Table<N, N, Integer> compute(ValueGraph<N, Integer> v) {
		Table<N,N,Integer>  d = populateTable(v);
		computeDistance(d);
		return d;
	}

	private void computeDistance(Table<N, N, Integer> d) {
		for (N k : d.rowKeySet()) {
			for (N i : d.rowKeySet()) { 
				for (N j : d.rowKeySet()) { 
					if(!i.equals(j)) { 
						int dij = d.get(i , j);
						int djk = d.get(j, k);
						int dik = d.get(i, k);
						int sum = djk  + dij;
						if(dik > sum && sum < Integer.MAX_VALUE) { 
							d.put(i , k, sum);
						}
					}
				}
			}
		}
	}
	

}
