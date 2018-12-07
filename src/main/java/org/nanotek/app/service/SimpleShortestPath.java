package org.nanotek.app.service;

import java.util.stream.Stream;

import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.ValueGraph;

public abstract class SimpleShortestPath<N extends Comparable<?>,E> implements Function<ValueGraph<N,Integer>,Table<N,N,E>> {


	protected Table<N,N,Integer> populateTable(ValueGraph<N, Integer> valueGraph) {
		Table<N, N, Integer> distanceTable = TreeBasedTable.create();
		valueGraph.nodes().stream().forEach(row -> valueGraph.nodes().stream().forEach(col -> 
					{
						if(row.equals(col))	
							distanceTable.put(row, col, 0);
						else 
							distanceTable.put(row, col, valueGraphValue(row,col,valueGraph));
					}
				));
		return distanceTable;
	}
	
	Integer valueGraphValue(N source, N target, ValueGraph<N, Integer> valueGraph) {
		return valueGraph.edgeValueOrDefault(EndpointPair.ordered(source, target),Integer.MAX_VALUE);
	}

	protected Stream<N> nodes(ValueGraph<N,Integer> valueGraph) {
		return valueGraph.nodes().stream();
	}

}
