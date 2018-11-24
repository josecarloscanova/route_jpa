package org.nanotek.app;

import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.nanotek.app.service.GraphPathService;
import org.nanotek.model.Station;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Table;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;

public class AppRunner {

	@Autowired
	GraphPathService graphPathService;

	@PostConstruct
	public void run() throws Exception {
		populateGraph();
		MutableValueGraph<Station, Integer> routes = populateGraph();
		graphPathService.calculateShortesPath(routes);
		Table<Station, Station,Integer> pathTable = graphPathService.getDistanceTable();
		System.out.println("FROM A TO C " + pathTable.get(new Station ("A") , new Station("C")));
//		
		Integer ab = pathTable.get(new Station ("A") , new Station("B"));
		System.out.println("FROM A TO B " + ab);
//		
		Integer bc = pathTable.get(new Station ("B") , new Station("C"));
		System.out.println("FROM B TO C " + bc);
//		
		Integer val1 = ab + bc;
		System.out.println("FROM ab + bc " + val1);
	}

	private 	MutableValueGraph<Station, Integer> populateGraph() {
		String input = "AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7";
		String[] inputValues = input.split("\\,\\s");
		MutableValueGraph<Station, Integer> routes = ValueGraphBuilder.directed().build();
		Stream.of(inputValues).forEach(nodesValue -> addStationGraph(nodesValue , routes));
		return routes; 
	}

	private void addStationGraph(String nodesValueStr , MutableValueGraph<Station, Integer> routes) {
		String[] stream =  nodesValueStr.split("");
		Integer val = Integer.valueOf(stream[2]);
		Station node1 = new Station(stream[0]);
		Station node2 = new Station(stream[1]);
		routes.addNode(node1);
		routes.addNode(node2);
		routes.putEdgeValue(node1, node2, val);
	}
	
	private void printTable(Table<Station, Station, Integer> routeTable) {
		Set<Station> stationRows = routeTable.rowKeySet(); 
		for(Station i : stationRows) { 
			for(Station j : stationRows) { 
				Integer ij = routeTable.get(i, j);
				System.out.println("Distance Calculated " + i + " " + j + " " + ij);
			}
		}
	}

}
