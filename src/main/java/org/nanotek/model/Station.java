package org.nanotek.model;

import java.util.Set;

/**
 * Represents a collection a label, for the problem proposed. 
 * can be fitted on a graph to  
 * @author Usuario
 *
 */
public class Station implements Comparable<Station>{

	private String stationLabel;
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((stationLabel == null) ? 0 : stationLabel.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Station other = (Station) obj;
		if (stationLabel == null) {
			if (other.stationLabel != null)
				return false;
		} else if (!stationLabel.equals(other.stationLabel))
			return false;
		return true;
	}

	@Override
	public int compareTo(Station o) {
		return this.stationLabel.compareTo(o.getStationLabel());
	}
	
}
