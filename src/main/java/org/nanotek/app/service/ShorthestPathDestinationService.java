package org.nanotek.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.nanotek.model.Destination;
import org.nanotek.model.Path;
import org.nanotek.model.Station;

import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;
import com.google.common.graph.ValueGraph;

public class ShorthestPathDestinationService<T extends Station>  extends AbstractShortestPath<T,Path<T>>{

	private Table<T,T,Integer> pathTable;

	private Table<T,T,Path<T>> pathDistanceTable;
	
	private Table<T,T,List<Path<T>>> pathListDistanceTable;
	
	@Override
	public Table<T, T, Path<T>> compute(ValueGraph<T, Integer> graph) {
		initializeTables(graph);
		Set<T> stationRows = pathDistanceTable.rowKeySet();
		for(T k : stationRows) { 
			for (T i : stationRows) { 
				for(T j : stationRows) { 
					Path<T> pathij = pathDistanceTable.get(i, j);
					Path<T> pathik = pathDistanceTable.get(i, k);
					int ikDistance = pathik.getDistance();
					Path<T> pathkj = pathDistanceTable.get(k, j);
					int kjDistance = pathkj.getDistance();
					if(ikDistance < Integer.MAX_VALUE && kjDistance < Integer.MAX_VALUE) { 
						int newDistance = ikDistance + kjDistance;
						if(newDistance < Integer.MAX_VALUE && newDistance >= 0) { 
							List<Destination<T>> destinationsik = pathik.getDestinations();
							List<Destination<T>> destinationskj = pathkj.getDestinations();
							if(pathij.getDistance() > newDistance) { 
								pathij.getDestinations().clear();
								addDistancesToPath(pathij, destinationsik);
								addDistancesToPath(pathij, destinationskj);
								addToPathListDistanceTable(pathij);
							}
						}else { 
							throw new RuntimeException("Another problem");
						}
					}
				}
			}
		}
		return pathDistanceTable;
	}

	private void addToPathListDistanceTable(Path<T> path) {
		List<Path<T>> paths = Optional.ofNullable(pathListDistanceTable.get(path.getDestination().getFrom(), path.getDestination().getTo())).orElseGet(ArrayList::new);
		paths.add(path);
		pathListDistanceTable.put(path.getDestination().getFrom(), path.getDestination().getTo(),paths);
	}

	private void initializeTables(ValueGraph<T, Integer> routes) {
		pathTable = generatePathTable();
		pathDistanceTable = generatePathDistanceTable(routes);	
		pathListDistanceTable =  TreeBasedTable.create();
		Set<T> stations = routes.nodes();
		stations.stream().forEach(station -> createDestinationColumns(station ,  routes));
		populatePathDistanceTable();		
	}


	private void addDistancesToPath(Path<T> path, List<Destination<T>> destinations) {
		if(path.getDistance() >= Integer.MAX_VALUE) { 
			path.getDestinations().clear();
		}
		path.getDestinations().addAll(destinations);
	}

	private void populatePathDistanceTable() {
		Set<T> rows = pathTable.rowKeySet();
		for (T row : rows) { 
			Map<T , Integer> distanceMap = pathTable.row(row);
			Set<T> columnSet = distanceMap.keySet();
			for (T column : columnSet) { 
				Path<T> path = pathDistanceTable.get(row, column) !=null ? pathDistanceTable.get(row, column) : new Path<T>();
				Destination<T> destination = new Destination<>(row,column,distanceMap.get(column)); 
				path.getDestinations().clear();
				path.addDestination(destination);
				pathDistanceTable.put(row, column, path);
			}
		} 
	}

	private Table<T, T, Path<T>> generatePathDistanceTable(ValueGraph<T, Integer> routes) {
		Table<T , T , Path<T>>  theTable = TreeBasedTable.create();
		Set<T> stations = new TreeSet <>();
		routes.nodes().forEach(station -> stations.add(station));
		stations.forEach(station -> generateValues(station , stations , theTable));
		return theTable;
	}

	private void generateValues(T station, Set<T> stations, Table<T, T, Path<T>> theTable) {
		for(T to : stations) { 
			int val = to.equals(station) ? 0 : Integer.MAX_VALUE;
			Path<T> path = new Path<>();
			Destination<T> destination = new Destination<>(station,to,val);
			path.addDestination(destination);
			theTable.put(station, to, path);
		}

	}

	public Table<T , T , Integer> generatePathTable() {
		return TreeBasedTable.create();
	}

	private void createDestinationColumns(T station, ValueGraph<T,Integer> routes) {
		Set<T> destinations = routes.successors(station);
		destinations.stream().forEach(destination -> moveToTable(station , destination , routes.edgeValue(station, destination) , pathTable));
	}

	private void moveToTable(T station, T destination, Optional<Integer> edgeValue,
			Table<T, T, Integer> theTable) {
		theTable.put(station, destination, edgeValue.get());
	}

	public Table<T, T, Integer> getPathTable() {
		return pathTable;
	}

	public void setPathTable(Table<T, T, Integer> pathTable) {
		this.pathTable = pathTable;
	}

	public Table<T, T, Path<T>> getDistanceTable() {
		return pathDistanceTable;
	}

	public Table<T, T, Path<T>> getPathDistanceTable() {
		return pathDistanceTable;
	}

}
