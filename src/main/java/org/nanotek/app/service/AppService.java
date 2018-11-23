package org.nanotek.app.service;

import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.nanotek.app.service.repository.DestinationRepository;
import org.nanotek.app.service.repository.StationRepository;
import org.nanotek.model.jpa.Destination;
import org.nanotek.model.jpa.Route;
import org.nanotek.model.jpa.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;

@Service
public class AppService {

	@Autowired
	private StationRepository stationRepository;
	
	@Autowired
	private DestinationRepository destinationRepository;
	
	@Transactional
	public Station addNewStation(Station station) { 
		Optional<Station> stProv = stationRepository.findById(station.getStationLabel());
		if(!stProv.isPresent()) { 
			return stationRepository.save(station);
		}
		return stProv.get();
	}

	public Destination saveDestination(Destination destination) { 
		return destinationRepository.save(destination);
	}
	
	public void fetchDestinations(Station station) {
		Set<Destination> destinations = destinationRepository.fetchDestinations(station);
		station.setDestinations(destinations);
	}
	
	public Optional<Station> findStation(String id) { 
		Optional<Station> optStation = stationRepository.findById(id);
		optStation.ifPresent(station -> fetchDestinations(station));
		return optStation;
	}
	
	public Set<Destination> findRoutes(Route route) {
		return destinationRepository.findByRoute(route);
	}
	
	public Set<Destination> findByOriginTarget(Station origin , Station target){ 
		return destinationRepository.findByOriginTarget(origin, target);
	}

	public Table<Station , Station , Integer> generatePathTable() {
		Table<Station , Station , Integer>  theTable = TreeBasedTable.create();
		stationRepository.findAll().forEach(x -> createDestinationColumns(x,theTable));
		return theTable;
	}

	private void createDestinationColumns(Station station, Table<Station, Station, Integer> theTable) {
		fetchDestinations(station);
		station.getDestinations().stream().forEach(x -> putOnTable(x , theTable));
	}

	private void putOnTable(Destination destination, Table<Station, Station, Integer> theTable) {
		theTable.put(destination.getRoute().getFrom(), destination.getRoute().getTo(), destination.getDistance());
	}

	public Integer findDistance(Station from, Station to) {
		Optional<Destination> optDest= destinationRepository.findById(new Route(from,to));
		return optDest.isPresent()?optDest.get().getDistance():Integer.MAX_VALUE;
	}

	public Set<Station> getStations() {
		Set<Station> stations = new TreeSet<>();
		stationRepository.findAll().forEach(x -> stations.add(x));
		return stations;
	}
	
}
