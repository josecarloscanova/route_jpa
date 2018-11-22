package org.nanotek.model.jpa;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Station {

	@Id
	private String stationLabel;
	
	@OneToMany
	private Set<Destination> destinations;

	public Station() {
		super();
	}
	
	public Station(String stationLabel) {
		super();
		this.stationLabel = stationLabel;
	}

	public String getStationLabel() {
		return stationLabel;
	}

	public void setStationLabel(String stationLabel) {
		this.stationLabel = stationLabel;
	}

	public Set<Destination> getDestinations() {
		return destinations;
	}

	public void setDestinations(Set<Destination> destinations) {
		this.destinations = destinations;
	}

	@Override
	public String toString() {
		return "Station [stationLabel=" + stationLabel +"]";
	}
	
}
