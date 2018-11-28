package org.nanotek.app.service;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.nanotek.app.service.repository.StationRepository;
import org.nanotek.model.jpa.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Table;

@Service
public class ShortestPathService {


	@Autowired
	private AppService appService;
	
	private Table<Station,Station,Integer> pathTable;
	
	private Table<Station,Station,Integer> routeTable;

	public void calculateShortesPath(){ 
		
		pathTable = appService.generatePathTable();
		routeTable = appService.generateDistanceTable();

		populateRouteTable(pathTable , routeTable);

		Set<Station> stationRows = routeTable.rowKeySet();

		for(Station k : stationRows) { 
			for (Station i : stationRows) { 
				for(Station j : stationRows) { 
					if(routeTable.get(i, k) < Integer.MAX_VALUE && routeTable.get(k, j) < Integer.MAX_VALUE) { 
						if(routeTable.get(i, j) > routeTable.get(i, k) + routeTable.get(k, j)) { 
							routeTable.put(i,j , routeTable.get(i, k) + routeTable.get(k, j));						
						}
					}
				}
			}
		}
		
		printTable(routeTable);
	}


	private void printTable(Table<Station, Station, Integer> routeTable) {
		Set<Station> stationRows = routeTable.rowKeySet(); 

		for(Station i : stationRows) { 
			for(Station j : stationRows) { 
				Integer ij = routeTable.get(i, j);
				System.out.println("I " + i + " " + j + " " + ij);
			}
		}
	}

	private void populateRouteTable(Table<Station, Station, Integer> pathTable,
			Table<Station, Station, Integer> routeTable) {
		Set<Station> rows = pathTable.rowKeySet();
		for (Station row : rows) { 
			Map<Station , Integer> columnMap = pathTable.row(row);
			Set<Station> columnSet = columnMap.keySet();
			for (Station column : columnSet) { 
				routeTable.put(row, column, columnMap.get(column));
			}
		} 

	}
	
	public Table<Station, Station, Integer> getPathTable() {
		return pathTable;
	}


	public Table<Station, Station, Integer> getRouteTable() {
		return routeTable;
	}


}