package org.nanotek.app.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.nanotek.model.Path;
import org.nanotek.model.Route;
import org.nanotek.model.Station;

import com.google.common.collect.Table;
import com.google.common.graph.ValueGraph;

public class DepthFirstSearchService {

	private ValueGraph<Station, Integer> routes; 
	private Table<Station, Station, Path<?>> pathTable;
	Map<Route<?>,Integer> visitedMap;
	Map<Station,Boolean> markedMap;
	Map<Route<?>,Path> pathMaps;
	Path<?> pathResult = new Path<>();
	int counter = 0;
	private Station st;
	private Set<?> stations;

	public DepthFirstSearchService(ValueGraph<Station, Integer> routes, Table<Station, Station, Path<?>> pathTable , Station station) {
		this.routes = routes;
		this.pathTable = pathTable;
		visitedMap = new HashMap<>();
		markedMap = new HashMap<>();
		pathMaps= new HashMap<>();
		initialize();
		this.st = station;
		dfs(station);
	}

	private void initialize() {
		Set<Station> nodes = routes.nodes();
		nodes.stream().forEach(x -> nodes.stream().forEach(y -> visitedMap.put(new Route<>(x,y), 0)));
		nodes.stream().forEach(x -> nodes.stream().forEach(y -> markedMap.put(x, false)));
	}

	private Path dfs(Station s){
		//		Map<Station , Path> map = pathTable.column(s);
		Path path = new Path();
		Set<Station> stations = routes.adjacentNodes(s);
		for (Station station : stations) {
			if(station.equals(st))
				counter++;
			path = pathTable.get(s , station);
			Route<?> rt = new Route<>(s,station);
			visitedMap.put(rt, visitedMap.get(rt) + 1);
			Optional<Integer> distance = Optional.ofNullable(path.getDistance());
			if(distance.isPresent() && distance.get() > 0 && distance.get() < Integer.MAX_VALUE) { 
				if(markedMap.get(station) == false){
					markedMap.put(s , true);
					Path nextDfsResult =  dfs(station);
					if(nextDfsResult.getDistance() > 0 && nextDfsResult.getDistance() < Integer.MAX_VALUE) {
						//						Station nextStation = optDestination.get().getTo();
						//						if(path.getDistance() == Integer.MAX_VALUE)
						//							continue;
						Route<?> route = new Route<>(s,station);
						pathMaps.put(route, path);
						Path<?> newPath = new Path<>();
						newPath.getDestinations().addAll(path.getDestinations());
						newPath.getDestinations().addAll(nextDfsResult.getDestinations());
						Route<?> newRoute = new Route<>(newPath.getDestination().getFrom() , newPath.getDestination().getTo());
						if(pathMaps.get(newRoute)==null)
							pathMaps.put(newRoute, newPath);	
					}
				}
			}
		}
		return path;
	}

	public Path<?> getPath() {
		return pathResult;
	}

	public ValueGraph<Station, Integer> getRoutes() {
		return routes;
	}

	public Table<Station, Station, Path<?>> getPathTable() {
		return pathTable;
	}

	public Path getPathResult() {
		return pathResult;
	}

	public Map<Route<?>, Integer> getVisitedMap() {
		return visitedMap;
	}

	public Map<Station, Boolean> getVisitedRoute() {
		return markedMap;
	}

	public Map<Route<?>, Path> getPathMaps() {
		return pathMaps;
	}

}
