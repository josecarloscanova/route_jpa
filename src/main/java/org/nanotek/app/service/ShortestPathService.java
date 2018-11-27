package org.nanotek.app.service;

import java.util.Collection;
import java.util.Set;

import org.nanotek.model.Station;
import org.springframework.stereotype.Service;

import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.ValueGraph;

/**
 * Computes the shorthest path value using something like Dijkstra strategy... to solve the shortest path on directed weighted graphs.
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
		Table<Station,Station ,Integer>  distanceTable = TreeBasedTable.create();
		nodes(valueGraph).stream().forEach(node ->{
			predecessors(node,valueGraph).stream().forEach(predecessor -> { 
				successors(node,valueGraph).stream().forEach(successor -> {
					if(!distanceTable.contains(predecessor, successor))
						distanceTable.put(predecessor, successor, Integer.MAX_VALUE);
					int computedValue = valueGraphValue(predecessor, node, valueGraph) + valueGraphValue(node, successor, valueGraph);
					if(distanceTable.get(predecessor, successor) > computedValue) { 
						distanceTable.put(predecessor, successor, computedValue);						
					}
				});
			});
		});
		return distanceTable;
	}
	
	
	
	
	private Collection<Station> nodes(ValueGraph<Station,Integer> valueGraph) {
		return valueGraph.nodes();
	}

	Set<Station> successors(Station source, ValueGraph<Station,Integer> valueGraph){ 
		return valueGraph.successors(source);
	}
	
	Set<Station> predecessors(Station source, ValueGraph<Station,Integer> valueGraph){ 
		return valueGraph.predecessors(source);
	}
	
	private Integer valueGraphValue(Station source, Station target, ValueGraph<Station, Integer> valueGraph) {
		return valueGraph.edgeValue(EndpointPair.ordered(source, target)).orElse(Integer.MAX_VALUE);
	}

}
