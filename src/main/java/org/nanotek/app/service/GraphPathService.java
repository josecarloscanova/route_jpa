package org.nanotek.app.service;

import java.util.Set;

import org.nanotek.model.Station;
import org.springframework.stereotype.Service;

import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.ValueGraph;

/**
 * Computes the shorthest path value using something like Johnson-Warshall strategy... to solve the shortest path on directed weighted graphs.
 * 
 * @author jose.carlos.canova@gmail.com
 *
 */
@Service
public class GraphPathService implements ShorthesPath<ValueGraph<Station,Integer>,Table<Station,Station,Integer>>{

	@Override
	public Table<Station, Station, Integer> compute(ValueGraph<Station, Integer> valueGraph) {
		return  calculateShortestPath(valueGraph);
	}
	
	private Table<Station,Station,Integer> calculateShortestPath(ValueGraph<Station,Integer> valueGraph){
		Table<Station,Station ,Integer>  distanceTable = TreeBasedTable.create();
		Set<Station> stationRows = valueGraph.nodes();
		for(Station k : stationRows) { 
			for (Station i : stationRows) { 
				for(Station j : stationRows) { 
					if(valueGraphValue(i, k,valueGraph) < Integer.MAX_VALUE && valueGraphValue(k, j,valueGraph) < Integer.MAX_VALUE) { 
						if(valueGraphValue(i, j , valueGraph) > valueGraphValue(i, k, valueGraph) + valueGraphValue(k, j, valueGraph)) { 
							//Integer.MAX_VALUE is considered "infinity" on the problem proposed.
							distanceTable.put(i,j , valueGraphValue(i, k, valueGraph) + valueGraphValue(k, j, valueGraph));						
						}else if (!distanceTable.contains(i, j)) { 
							distanceTable.put(i, j, Integer.MAX_VALUE);//Not yet solved ... maybe there is a problem?
						}
					}
				}
			}
		}
		return distanceTable;
	}
	
	private Integer valueGraphValue(Station source, Station target, ValueGraph<Station, Integer> valueGraph) {
		return valueGraph.edgeValue(EndpointPair.ordered(source, target)).orElse(Integer.MAX_VALUE);
	}

}
