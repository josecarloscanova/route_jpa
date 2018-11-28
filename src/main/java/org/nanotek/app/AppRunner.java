package org.nanotek.app;

import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.nanotek.app.service.GraphPathServiceDestination;
import org.nanotek.app.service.ShortestPathService;
import org.nanotek.model.Path;
import org.nanotek.model.Station;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Table;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;

public class AppRunner {

	@Autowired
	GraphPathServiceDestination graphPathService;

	@Autowired
	ShortestPathService graphService;

	@PostConstruct
	public void run() throws Exception {
		MutableValueGraph<Station, Integer> routes = populateGraph();
		graphPathService.calculateShortesPathTable(routes);
		Table<Station, Station,Path> pathTable = graphPathService.getDistanceTable();
		
		Table<Station, Station,Integer> resultTable = graphService.compute(routes);
		
		resultTable.cellSet().stream().forEach(cell -> System.out.println(cell.getRowKey() +  " "  +cell.getColumnKey() + " " +  cell.getValue()));
		pathTable.cellSet().stream().forEach(cell -> System.out.println(cell.getRowKey() +  " "  +cell.getColumnKey() + " " +  cell.getValue()));
//		System.out.println("FROM A TO C " + pathTable.get(new Station ("A") , new Station("C")));
//		//		
//		Path ab = pathTable.get(new Station ("A") , new Station("B"));
//		System.out.println("FROM A TO B " + ab.getDistance());
//		//		
//		Path bc = pathTable.get(new Station ("B") , new Station("C"));
//		System.out.println("FROM B TO C " + bc.getDistance());
//		//		
//		Integer val1 = ab.getDistance() + bc.getDistance();
//		System.out.println("FROM ab + bc " + val1);
//		//		Table<Station, Station,List<Path>> pathListTable  = graphPathService.createTablePaths(routes);
//		printTable(pathTable);
//		//		prinPathTable(pathListTable);
//		if(Graphs.hasCycle(routes.asGraph())) 
//			System.out.println("HAS CYCLES");
//		Set<Station> stations = routes.nodes();
//		for (Station station : stations) { 
//			DepthFirstSearchService dfs = new DepthFirstSearchService(routes , pathTable , station);
//			System.out.println("BB" + dfs.getPathMaps());
////			System.out.println("BB" + dfs.getVisitedMap());
////			System.out.println("BB" + dfs.getVisitedRoute());
//		}
	}


	private MutableValueGraph<Station, Integer> populateGraph() {
//		String input = "AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7";
//		String input = "AB4, BC4, AC9 , CD8, DC8, DE6, AD5, CE2, EB3, AE7, AF9, EF1, CF3";
//		String input = "AB2, BD1, AC9 , CD8, DC3, DE6, AD5, CE2, EB3, AE7, AF9, EF1, CF3";
		String input = "AA2, AB2, BD1, AC9 , CD8, DC3, DE6, AD5, CE2, EB3, AE7, AF9, EF1, CF3";
//		String input = "AB2, AC1, AC5, BC1, CA3, AD3, CD2, BA4, DC1, CA2";
		String[] inputValues = input.split("\\,\\s");
		MutableValueGraph<Station, Integer> routes = ValueGraphBuilder.directed().allowsSelfLoops(true).build();
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

}
