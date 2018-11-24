package org.nanotek.app.service;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.nanotek.model.Station;
import org.springframework.stereotype.Service;

import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;
import com.google.common.graph.MutableValueGraph;

@Service
public class GraphPathService {

	private Table<Station,Station,Integer> pathTable;
	
	private Table<Station,Station,Integer> distanceTable;
	
	public Table<Station,Station,Integer>  calculateShortesPath(MutableValueGraph<Station,Integer> routes){  
		pathTable = generatePathTable();
		distanceTable = generateDistanceTable(routes);
		Set<Station> stations = routes.nodes();
		stations.stream().forEach(station -> createDestinationColumns(station , pathTable , routes));
		populateDistanceTable(pathTable, distanceTable);
		
		Set<Station> stationRows = distanceTable.rowKeySet();

		for(Station k : stationRows) { 
			for (Station i : stationRows) { 
				for(Station j : stationRows) { 
					if(distanceTable.get(i, k) < Integer.MAX_VALUE && distanceTable.get(k, j) < Integer.MAX_VALUE) { 
						if(distanceTable.get(i, j) > distanceTable.get(i, k) + distanceTable.get(k, j)) { 
							distanceTable.put(i,j , distanceTable.get(i, k) + distanceTable.get(k, j));						
						}
					}
				}
			}
		}
		
		return distanceTable;
	}
	
	private void populateDistanceTable(Table<Station, Station, Integer> pathTable,
			Table<Station, Station, Integer> distanceTable) {
		Set<Station> rows = pathTable.rowKeySet();
		for (Station row : rows) { 
			Map<Station , Integer> columnMap = pathTable.row(row);
			Set<Station> columnSet = columnMap.keySet();
			for (Station column : columnSet) { 
				distanceTable.put(row, column, columnMap.get(column));
			}
		} 

	}
	
	public Table<Station , Station , Integer> generatePathTable() {
		Table<Station , Station , Integer>  theTable = TreeBasedTable.create();
		return theTable;
	}

	private void createDestinationColumns(Station station, Table<Station, Station, Integer> theTable ,MutableValueGraph<Station,Integer> routes) {
		Set<Station> destinations = routes.successors(station);
		destinations.stream().forEach(destination -> moveToTable(station , destination , routes.edgeValue(station, destination) , theTable));
	}

	private void moveToTable(Station station, Station destination, Optional<Integer> edgeValue,
			Table<Station, Station, Integer> theTable) {
		theTable.put(station, destination, edgeValue.get());
	}

	private Table<Station, Station, Integer> generateDistanceTable(MutableValueGraph<Station,Integer> routes) {
		Table<Station , Station , Integer>  theTable = TreeBasedTable.create();
		Set<Station> stations = new TreeSet <>();
		routes.nodes().forEach(station -> stations.add(station));
		stations.forEach(station -> generateTable(station , stations , theTable));
		return theTable;
	}

	private void generateTable(Station station, Set<Station> stations, Table<Station, Station, Integer> theTable) {
		for(Station to : stations) { 
			int val = to.equals(station) ? 0 : Integer.MAX_VALUE;
			theTable.put(station, to, val);
		}
	}

	public Table<Station, Station, Integer> getPathTable() {
		return pathTable;
	}

	public void setPathTable(Table<Station, Station, Integer> pathTable) {
		this.pathTable = pathTable;
	}

	public Table<Station, Station, Integer> getDistanceTable() {
		return distanceTable;
	}

	public void setDistanceTable(Table<Station, Station, Integer> distanceTable) {
		this.distanceTable = distanceTable;
	}
	
}
