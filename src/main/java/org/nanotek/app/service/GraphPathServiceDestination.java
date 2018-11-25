package org.nanotek.app.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.nanotek.model.Destination;
import org.nanotek.model.Path;
import org.nanotek.model.Station;
import org.springframework.stereotype.Service;

import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;
import com.google.common.graph.MutableValueGraph;

@Service
public class GraphPathServiceDestination {

	private Table<Station,Station,Path> pathDistanceTable;
	
	public Table<Station,Station,Path>  calculateShortesPath(MutableValueGraph<Station,Integer> routes){  
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
						if( newDistance < Integer.MAX_VALUE && newDistance >= 0) { 
							if(pathij.getDistance() > newDistance) { 
								List<Destination> destinationsik = pathik.getDestinations();
								List<Destination> destinationskj = pathkj.getDestinations();
								addDistancesToPath(pathij, destinationsik);
								addDistancesToPath(pathij, destinationskj);
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
	
	//Project the graph into a table of paths.
	private void initializeTables(MutableValueGraph<Station, Integer> routes) {
		pathDistanceTable = generatePathDistanceTable(routes);
		populatePathDistanceTable(routes);		
	}


	private void addDistancesToPath(Path path, List<Destination> destinations) {
		if(path.getDistance() >= Integer.MAX_VALUE) { 
			path.getDestinations().clear();
		}
		path.getDestinations().addAll(destinations);
	}

	private void populatePathDistanceTable(MutableValueGraph<Station, Integer> routes) {
		Set<Station> rows = routes.nodes();
		for (Station row : rows) { 
			Set<Station> columnSet  = routes.successors(row);
			for (Station column : columnSet) { 
				Path path = pathDistanceTable.get(row, column) !=null ? pathDistanceTable.get(row, column) : new Path();
				Destination destination = new Destination(row,column,routes.edgeValue(row,column).get()); 
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

	private void moveToTable(Station station, Station destination, Optional<Integer> edgeValue,
			Table<Station, Station, Integer> theTable) {
		theTable.put(station, destination, edgeValue.get());
	}

	public Table<Station, Station, Path> getDistanceTable() {
		return pathDistanceTable;
	}

	public Table<Station, Station, Path> getPathDistanceTable() {
		return pathDistanceTable;
	}

}
