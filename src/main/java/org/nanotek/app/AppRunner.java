package org.nanotek.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.nanotek.app.service.GraphPathServiceDestination;
import org.nanotek.app.service.ShortestPathService;
import org.nanotek.app.util.Parser;
import org.nanotek.model.Path;
import org.nanotek.model.Station;

import com.google.common.collect.Table;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.Graphs;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraph;
import com.google.common.graph.ValueGraphBuilder;

public class AppRunner {

	GraphPathServiceDestination graphPathService;
	
	ShortestPathService graphService;
	
	Parser<String,ValueGraph<String,Integer>> graphParser;

	private String input;

	public AppRunner(String input) {
		this.input = input;
	}
	
	public AppRunner(String input , Parser<String,?> parser) { 
	}
	
	public void run() throws Exception {
		
		graphPathService = new GraphPathServiceDestination();
		graphService = new ShortestPathService();
		MutableValueGraph<Station, Integer> routes = populateGraph();
		graphPathService.calculateShortesPathTable(routes);
		routes.edges();
		
		MutableValueGraph<Station,Integer> ig = Graphs.copyOf(routes);
		for (EndpointPair<Station> i : routes.edges())
		{ 
			Map<String,String> map = new HashMap<String,String>();
			ig.removeEdge(i);
			graphPathService = new GraphPathServiceDestination();
			graphPathService.calculateShortesPathTable(ig);
			graphPathService.getPaths().values().stream().forEach(x -> x.stream().forEach(p -> map.put(p.canonicalForm(), p.toString())));
			System.out.println(ig);
			System.out.println("Paths found");
			map.values().stream().forEach(x -> System.out.println(x));
		}
		
		
		
//		Table<Station, Station, Path> pathTable = graphPathService.getDistanceTable();
//		Table<Station, Station,Integer> resultTable = graphService.compute(routes);
		
//		resultTable.cellSet().stream().forEach(cell -> System.out.println(cell.getRowKey() +  " "  + cell.getColumnKey() + " " +  cell.getValue()));
//		pathTable.cellSet().stream().forEach(cell -> System.out.println(cell.getRowKey() +  " "  + cell.getColumnKey() + " " +  cell.getValue()));
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
		String[] inputValues = input.split("\\,\\s");
		MutableValueGraph<Station, Integer> routes = ValueGraphBuilder.directed().allowsSelfLoops(true).build();
		Stream.of(inputValues).forEach(nodesValue -> addStationGraph(nodesValue , routes));
		return routes; 
	}


	private void addStationGraph(String nodesValueStr , MutableValueGraph<Station, Integer> routes) {
		String[] stream =  nodesValueStr.split("");
		Integer value =  Integer.valueOf(nodesValueStr.substring(2, nodesValueStr.length()));
		Station node1 = new Station(stream[0]);
		Station node2 = new Station(stream[1]);
		routes.addNode(node1);
		routes.addNode(node2);
		routes.putEdgeValue(node1, node2, Integer.valueOf(value));
	}

}
