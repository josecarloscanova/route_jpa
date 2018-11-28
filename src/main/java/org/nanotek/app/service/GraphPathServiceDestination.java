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
import com.google.common.graph.MutableValueGraph;

public class GraphPathServiceDestination {

	private Table<Station,Station,Integer> pathTable;

	private Table<Station,Station,Path> pathDistanceTable;
	
	private Table<Station,Station,List<Path>> pathListDistanceTable;
	

	public Table<Station,Station,Path>  calculateShortesPathTable(MutableValueGraph<Station,Integer> routes){  
		initializeTables(routes);
		Set<Station> stationRows = pathDistanceTable.rowKeySet();
		for(Station k : stationRows) { 
			for (Station i : stationRows) { 
				for(Station j : stationRows) { 
					Path pathij = pathDistanceTable.get(i, j);
					Path pathik = pathDistanceTable.get(i, k);
					Path pathkj = pathDistanceTable.get(k, j);
					if(pathik.getDistance() < Integer.MAX_VALUE && pathkj.getDistance() < Integer.MAX_VALUE) { 
						Integer newDistance = pathik.getDistance() + pathkj.getDistance();
						if(newDistance < Integer.MAX_VALUE && newDistance >= 0) { 
							List<Destination> destinationsik = pathik.getDestinations();
							List<Destination> destinationskj = pathkj.getDestinations();
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

	public void calculateDFS (MutableValueGraph<Station,Integer> routes , Station station) { 
		
	}

	private void addToPathListDistanceTable(Path path) {
		List<Path> paths = Optional.ofNullable(pathListDistanceTable.get(path.getDestination().getFrom(), path.getDestination().getTo())).orElseGet(ArrayList::new);
		paths.add(path);
		pathListDistanceTable.put(path.getDestination().getFrom(), path.getDestination().getTo(),paths);
	}

	private void initializeTables(MutableValueGraph<Station, Integer> routes) {
		pathTable = generatePathTable();
		pathDistanceTable = generatePathDistanceTable(routes);	
		pathListDistanceTable =  TreeBasedTable.create();
		Set<Station> stations = routes.nodes();
		stations.stream().forEach(station -> createDestinationColumns(station ,  routes));
		populatePathDistanceTable();		
	}


	private void addDistancesToPath(Path path, List<Destination> destinations) {
		if(path.getDistance() >= Integer.MAX_VALUE) { 
			path.getDestinations().clear();
		}
		path.getDestinations().addAll(destinations);
	}

	private void populatePathDistanceTable() {
		Set<Station> rows = pathTable.rowKeySet();
		for (Station row : rows) { 
			Map<Station , Integer> distanceMap = pathTable.row(row);
			Set<Station> columnSet = distanceMap.keySet();
			for (Station column : columnSet) { 
				Path path = pathDistanceTable.get(row, column) !=null ? pathDistanceTable.get(row, column) : new Path();
				Destination destination = new Destination(row,column,distanceMap.get(column)); 
				path.getDestinations().clear();
				path.addDestination(destination);
				pathDistanceTable.put(row, column, path);
			}
		} 
	}

	private Table<Station, Station, Path> generatePathDistanceTable(MutableValueGraph<Station, Integer> routes) {
		Table<Station , Station , Path>  theTable = TreeBasedTable.create();
		Set<Station> stations = new TreeSet <>();
		routes.nodes().forEach(station -> stations.add(station));
		stations.forEach(station -> generateValues(station , stations , theTable));
		return theTable;
	}

	private void generateValues(Station station, Set<Station> stations, Table<Station, Station, Path> theTable) {
		for(Station to : stations) { 
			int val = to.equals(station) ? 0 : Integer.MAX_VALUE;
			Path path = new Path();
			Destination destination = new Destination(station,to,val);
			path.addDestination(destination);
			theTable.put(station, to, path);
		}

	}

	public Table<Station , Station , Integer> generatePathTable() {
		Table<Station , Station , Integer>  theTable = TreeBasedTable.create();
		return theTable;
	}

	private void createDestinationColumns(Station station, MutableValueGraph<Station,Integer> routes) {
		Set<Station> destinations = routes.successors(station);
		destinations.stream().forEach(destination -> moveToTable(station , destination , routes.edgeValue(station, destination) , pathTable));
	}

	private void moveToTable(Station station, Station destination, Optional<Integer> edgeValue,
			Table<Station, Station, Integer> theTable) {
		theTable.put(station, destination, edgeValue.get());
	}

	public Table<Station, Station, Integer> getPathTable() {
		return pathTable;
	}

	public void setPathTable(Table<Station, Station, Integer> pathTable) {
		this.pathTable = pathTable;
	}

	public Table<Station, Station, Path> getDistanceTable() {
		return pathDistanceTable;
	}

	public Table<Station, Station, Path> getPathDistanceTable() {
		return pathDistanceTable;
	}

}
