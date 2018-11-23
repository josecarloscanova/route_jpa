package org.nanotek.app.service;

import java.util.Optional;
import java.util.Set;

import org.nanotek.app.service.repository.DestinationRepository;
import org.nanotek.app.service.repository.StationRepository;
import org.nanotek.model.jpa.Destination;
import org.nanotek.model.jpa.Route;
import org.nanotek.model.jpa.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	public Station fetchDestinations(Station station) {
		Set<Destination> destinations = destinationRepository.fetchDestinations(station);
		station.setDestinations(destinations);
		return station;
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

}
