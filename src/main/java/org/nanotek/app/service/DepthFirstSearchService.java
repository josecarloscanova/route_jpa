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
	private Table<Station, Station, Path> pathTable;
	Map<Route,Integer> visitedMap;
	Map<Station,Boolean> visitedRoute;
	Map<Route,Path> pathMaps;
	Path pathResult = new Path();
	int counter = 0;
	private Station st;

	public DepthFirstSearchService(ValueGraph<Station, Integer> routes, Table<Station, Station, Path> pathTable , Station station) {
		this.routes = routes;
		this.pathTable = pathTable;
		visitedMap = new HashMap<>();
		visitedRoute = new HashMap<>();
		pathMaps= new HashMap<>();
		initializeVisited();
		this.st = station;
		dfs(station);
	}

	private void initializeVisited() {
		Set<Station> nodes = routes.nodes();
		nodes.stream().forEach(x -> nodes.stream().forEach(y -> visitedMap.put(new Route(x,y), 0)));
		nodes.stream().forEach(x -> nodes.stream().forEach(y -> visitedRoute.put(x, false)));
	}

	private Path dfs(Station s){
		//		Map<Station , Path> map = pathTable.column(s);
		Path path = new Path();
		Set<Station> stations = routes.nodes();
		for (Station station : stations) {
			if(s.equals(station)) continue;
			if(station.equals(st))
				counter++;
			path = pathTable.get(s , station);
			Route rt = new Route(s,station);
			visitedMap.put(rt, visitedMap.get(rt) + 1);
			Optional<Integer> distance = Optional.ofNullable(path.getDistance());
			if(distance.isPresent() && distance.get() > 0 && distance.get() < Integer.MAX_VALUE) { 
				if(visitedMap.get(rt) < 100){
					visitedRoute.put(s , true);
					Path nextDfsResult =  dfs(station);
					if(nextDfsResult.getDistance() > 0 && nextDfsResult.getDistance() < Integer.MAX_VALUE) {
						//						Station nextStation = optDestination.get().getTo();
						//						if(path.getDistance() == Integer.MAX_VALUE)
						//							continue;
						Route route = new Route (s,station);
						pathMaps.put(route, path);
						Path newPath = new Path();
						newPath.getDestinations().addAll(path.getDestinations());
						newPath.getDestinations().addAll(nextDfsResult.getDestinations());
						Route newRoute = new Route(newPath.getDestination().getFrom() , newPath.getDestination().getTo());
						if(pathMaps.get(newRoute)==null)
							pathMaps.put(newRoute, newPath);	
					}
				}
				
			}
		}
		return path;
	}

	public Path getPath() {
		return pathResult;
	}

	public ValueGraph<Station, Integer> getRoutes() {
		return routes;
	}

	public void setRoutes(ValueGraph<Station, Integer> routes) {
		this.routes = routes;
	}

	public Table<Station, Station, Path> getPathTable() {
		return pathTable;
	}

	public void setPathTable(Table<Station, Station, Path> pathTable) {
		this.pathTable = pathTable;
	}

	public Map<Station, Boolean> getVisitedRoute() {
		return visitedRoute;
	}

	public void setVisitedRoute(Map<Station, Boolean> visitedRoute) {
		this.visitedRoute = visitedRoute;
	}

	public Path getPathResult() {
		return pathResult;
	}

	public void setPathResult(Path pathResult) {
		this.pathResult = pathResult;
	}

	public Map<Route, Integer> getVisitedMap() {
		return visitedMap;
	}

	public void setVisitedMap(Map<Route, Integer> visitedMap) {
		this.visitedMap = visitedMap;
	}

	public Map<Route, Path> getPathMaps() {
		return pathMaps;
	}

	public void setPathMaps(Map<Route, Path> pathMaps) {
		this.pathMaps = pathMaps;
	}
}
