package com.toughtworks.path;

//import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

//import com.google.common.graph.EndpointPair;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;

public class TestGuavaGraph {

	private MutableValueGraph<String , Integer> graph;
	
	public TestGuavaGraph (String input) { 
		initializeGraph(input);
	}
	
	private void initializeGraph(String input2) {
		graph = ValueGraphBuilder.undirected().build();
		String[] inputValues = input2.split("\\, ");
		Stream.of(inputValues).forEach(x -> addEdge(x));
	}

	private void addEdge(String x) {
		String[] stream =  x.split("");
		Integer val = Integer.valueOf(stream[2]);
		graph.putEdgeValue(stream[0], stream[1], val);
	}

	public static void main(String[] args) {
		String input = "AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7";
		TestGuavaGraph guavaGraph = new TestGuavaGraph(input);
		guavaGraph.verifyGraph();
		ShortestPath sp = new ShortestPath(guavaGraph.getGraph());
		sp.mountDistanceTable();
		sp.printDistanceTable();
		sp.printVisitedNodes();
	}

	private void verifyGraph() {
		graph.nodes().forEach(x -> printSucessors(x));
	}
	

	private void printSucessors(String x) {
		Set<String> sucessors = graph.successors(x);
		System.out.print(x + '\t');
		System.out.println(sucessors);
	}

	public MutableValueGraph<String, Integer> getGraph() {
		return graph;
	}
}
