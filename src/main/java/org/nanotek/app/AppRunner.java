package org.nanotek.app;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.nanotek.app.service.AppService;
import org.nanotek.app.service.ShortestPathService;
import org.nanotek.app.util.DestinationComparator;
import org.nanotek.model.jpa.Destination;
import org.nanotek.model.jpa.Route;
import org.nanotek.model.jpa.Station;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Table;

public class AppRunner {

	@Autowired
	private AppService appService;
	
	@Autowired
	private ShortestPathService shortestPathService;
	
	@Autowired 
	private DestinationComparator destinationComparator;
	
	private Table<Station, Station, Integer> routeTable;

	private Table<Station, Station, Integer> distanceTable;

	@PostConstruct
	public void run() throws Exception {
		populateDataBase();
		shortestPathService.calculateShortesPath();
		Table<Station,Station,Integer> routeTable = shortestPathService.getRouteTable();
		System.out.println("FROM A TO C " + routeTable.get(new Station ("A") , new Station("C")));
//		
		Integer ab = routeTable.get(new Station ("A") , new Station("B"));
		System.out.println("FROM A TO B " + ab);
//		
		Integer bc = routeTable.get(new Station ("B") , new Station("C"));
		System.out.println("FROM B TO C " + bc);
//		
		Integer val1 = ab + bc;
		System.out.println("FROM ab + bc " + val1);
		
	}

	private void generateShortesDistanceResults() {
	}

	private void printRouteTable() {
		routeTable.rowKeySet().stream().forEach(station -> printColumn(station , routeTable.row(station)));
	}

	private void printColumn(Station station, Map<Station, Integer> column) {
		column.entrySet().stream().forEach(entry -> System.out.println("PATH TABLE " + station.toString() + " " + entry.getKey() + "  " + entry.getValue()));
	}


	private Destination findMinRoute(String id) {
		Optional<Station> optStation = appService.findStation(id);
		Optional<Destination> minDistance = findMinimalDistance(optStation.get());
		if(optStation.isPresent()) { 
			printDestination(minDistance.get());
		}
		return minDistance.get();
	}


	private Optional<Destination> findMinimalDistance(Station station) {
		return station.getDestinations().stream().min(destinationComparator);
	}

	private void findRoute(Route route) {
		Set<Destination> destinations = appService.findRoutes(route);
		if(!destinations.isEmpty()) { //just on possible solution otherwise the model is screwed.
			if (destinations.size() > 1) { 
				throw new RuntimeException("Not a valid solution");
			}
			else { 
				Destination destination = destinations.stream().findFirst().get();
				printDestination (destination);
			}
		}else { 
			System.out.println("No direct destination from " + route.getFrom() + " to " + route.getTo());
		}
	}

	private void printDestination(Destination destination) {
		System.out.println("Destination from " + destination.getRoute().getFrom() + " to " + destination.getRoute().getTo() + " with distance:" + destination.getDistance());
	}

	private void populateDataBase() {
		String input = "AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7";
		String[] inputValues = input.split("\\,\\s");
		Stream.of(inputValues).forEach(x -> addStations(x));
	}

	private void addStations(String stationDestinationInput) {
		String[] stream =  stationDestinationInput.split("");
		Integer val = Integer.valueOf(stream[2]);
		saveStationDestination(stream[0], stream[1], val);
	}

	private void saveStationDestination(String orig, String tgt, Integer distance) {
		Station stationOrigin = appService.addNewStation(new Station(orig));
		Station stationTarget = appService.addNewStation(new Station(tgt));
		Route route = new Route (stationOrigin , stationTarget);
		Destination destination = new Destination(route , distance);
		appService.saveDestination(destination);
		printStation (stationOrigin);
	}

	private void printStation(Station station) {
		appService.fetchDestinations(station);
		System.out.print(station.toString());
		System.out.print('\t');
		System.out.println(station.getDestinations().toString());
	}

}
