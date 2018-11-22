package org.nanotek.app;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.nanotek.app.service.AppService;
import org.nanotek.model.jpa.Destination;
import org.nanotek.model.jpa.Route;
import org.nanotek.model.jpa.Station;
import org.springframework.beans.factory.annotation.Autowired;

//@Component
public class AppRunner {

	@Autowired
	AppService appService;
	
	
	@PostConstruct
	public void run() throws Exception {
		populateDataBase();
		findRoute(new Route(new Station ("A") , new Station("C")));
		findRoute(new Route(new Station ("D") , new Station("C")));
		findRoutes("A");
	}

	private void findRoutes(String id) {
		Optional<Station> optStation = appService.findStation(id);
		if(optStation.isPresent()) { 
			Optional<Station> minDistance = findMinimalDistance(optStation.get());
		}
	}

	private Optional<Station> findMinimalDistance(Station station) {
		station.getDestinations().stream().min(comparator)
		return null;
	}

	private void findRoute(Route route) {
		Set<Destination> routes = appService.findRoutes(route);
		if(!routes.isEmpty()) { //just on possible solution otherwise the model is screwed.
			if (routes.size() > 1) { 
				throw new RuntimeException("Not a valid solution");
			}
			else { 
				Destination destination = routes.stream().findFirst().get();
				printDestination (destination , route);
				return;
			}
		}else { 
			System.out.println("No direct route from " + route.getFrom() + " to " + route.getDestination());
		}
	}

	private void printDestination(Destination destination, Route route) {
		System.out.println("Route from " + route.getFrom() + " to " + route.getDestination() + " with distance:" + destination.getDistance());
	}

	private void populateDataBase() {
		String input = "AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7";
		String[] inputValues = input.split("\\, ");
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
