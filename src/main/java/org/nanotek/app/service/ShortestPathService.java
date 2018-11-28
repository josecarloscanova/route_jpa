package org.nanotek.app.service;

import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;import javax.swing.table.TableCellEditor;

import org.nanotek.model.Station;
import org.springframework.stereotype.Service;

import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.ValueGraph;

/**
 * Computes the shorthest path value using something like Dijkstra strategy... to solve the shortest path on directed weighted graphs(the problem proposed).
 * 
 * @author jose.carlos.canova@gmail.com
 *
 */
@Service
public class ShortestPathService implements Function<ValueGraph<Station,Integer>,Table<Station,Station,Integer>>{

	@Override
	public Table<Station, Station, Integer> compute(ValueGraph<Station, Integer> valueGraph) {
		return  calculateShortestPath(valueGraph);
	}

	private Table<Station,Station,Integer> calculateShortestPath(ValueGraph<Station,Integer> valueGraph){
		Table<Station,Station ,Integer>  distanceTable = populateTable(valueGraph);
		nodes(valueGraph).forEach(k -> 
		adjacentNodes(k,valueGraph).forEach(i -> 
		adjacentNodes(k,valueGraph).forEach(j -> 
		computeDistanceValue(k,i,j,distanceTable))));
		return distanceTable;
	}

	private Table<Station,Station ,Integer> populateTable(ValueGraph<Station, Integer> valueGraph) {
		Table<Station, Station, Integer> distanceTable = TreeBasedTable.create();
		valueGraph.nodes().stream().forEach(row -> valueGraph.nodes().stream().forEach(col -> 
						{
							if(!row.equals(col))	
								distanceTable.put(row, col, valueGraphValue(row,col,valueGraph));
							else 
								distanceTable.put(row, col, 0);
						}
						));
		return distanceTable;
	}

	private void computeDistanceValue(Station k, 
			Station i, Station j, 
			Table<Station,Station ,Integer>  distanceTable) {
				Integer computedValue = distanceTable.get(i, k) + distanceTable.get(k, j);
				Integer tableValue = distanceTable.get(i, j);
				if( computedValue < Integer.MAX_VALUE && computedValue > 0) { 
					 if(tableValue == Integer.MAX_VALUE) 
						distanceTable.put(i ,  j, computedValue);
					else if (tableValue > computedValue && tableValue < Integer.MAX_VALUE)
						distanceTable.put(i ,  j, tableValue + computedValue);
				}
	}

	private Stream<Station> nodes(ValueGraph<Station,Integer> valueGraph) {
		return valueGraph.nodes().stream();
	}

	private Stream<Station> adjacentNodes(Station source, ValueGraph<Station,Integer> valueGraph){ 
		return valueGraph.adjacentNodes(source).stream().sorted();
	}
	
	private Stream<Station> successors(Station source, ValueGraph<Station,Integer> valueGraph){ 
		return valueGraph.successors(source).stream().sorted();
	}

	private Stream<Station> predecessors(Station source, ValueGraph<Station,Integer> valueGraph){ 
		return valueGraph.predecessors(source).stream().sorted();
	}

	private Integer valueGraphValue(Station source, Station target, ValueGraph<Station, Integer> valueGraph) {
		return valueGraph.edgeValue(EndpointPair.ordered(source, target)).orElse(Integer.MAX_VALUE);
	}

}
