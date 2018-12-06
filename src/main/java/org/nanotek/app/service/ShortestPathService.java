package org.nanotek.app.service;

import java.util.stream.Stream;

import org.nanotek.model.Station;

import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.ValueGraph;

/**
 * Computes the shortest path value using something like Dijkstra strategy... 
 * to solve the shortest path on directed weighted graphs(the problem proposed).
 * 
 * @author jose.carlos.canova@gmail.com
 *
 */
public class ShortestPathService extends SimpleShortestPath<Station,Integer>{
 
	public Table<Station, Station, Integer> compute(ValueGraph<Station, Integer> valueGraph) {
		return  calculateShortestPath(valueGraph);
	}

	private Table<Station,Station,Integer> calculateShortestPath(ValueGraph<Station,Integer> valueGraph){
		Table<Station,Station ,Integer>  distanceTable = populateTable(valueGraph);
		System.out.println(distanceTable);
		nodes(valueGraph).forEach(k -> 
		nodes(valueGraph).forEach(i -> 
		nodes(valueGraph).forEach(j -> 
		computeDistanceValue(k,i,j,distanceTable))));
		return distanceTable;
	}
	
	private void computeDistanceValue(Station k, 
			Station i, Station j, 
			Table<Station,Station ,Integer>  distanceTable) {
		int ik = distanceTable.get(i, k);
		int kj = distanceTable.get(k, j);
		int computedValue = ik + kj;
		int tableValue = distanceTable.get(i, j);
		if( computedValue < Integer.MAX_VALUE && computedValue > 0 && computedValue < tableValue) { 
			System.out.println("computed value " + " " + i + "  " +  j + " " + computedValue);
			int previous = distanceTable.put(i ,  j, computedValue);
			System.out.println("previous " + previous);
		}
	}

	Integer valueGraphValue(Station source, Station target, ValueGraph<Station, Integer> valueGraph) {
		return valueGraph.edgeValueOrDefault(EndpointPair.ordered(source, target),Integer.MAX_VALUE);
	}

}
