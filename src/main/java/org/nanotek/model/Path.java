package org.nanotek.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Class to store calculated paths, 
 * "On theory, this list is valid if a.from == a1.to.. always. otherwise the path is not valid. 
 * 
 * It stores a "Set" for destinations about the order that they were inserted on "container" Path. 
 * 
 * Usefull to verify if some algorithm to 
 * 
 */
public class Path {
	
	private LinkedList<Destination> destinations = new LinkedList<>();

	public Path() {
		super();
	}
	
	public Path(Destination destination) {
		super();
		destinations.add(destination);
	}
	
	public Destination addDestination(Destination destination) { 
		destinations.add(destination);
		return destination;
	}
	
	public List<Destination> getDestinations(){ 
		return destinations;
	}
	
	public Integer getDistance() { 
		Optional<Integer> sum = destinations.stream().map(x -> x.getDistance()).reduce((x,y) -> x+y);
		return sum.isPresent()?sum.get():Integer.MAX_VALUE;
	}

	public Destination getDestination() { 
		Destination dest = null; 
		if(destinations.size() > 0) { 
			dest  = new Destination(destinations.get(0).getFrom() , destinations.getLast().getTo() , getDistance());
		}
		return Optional.ofNullable(dest).orElse(new Destination());
	}


	@Override
	public String toString() {
		return "Path [" + "Destination()="
				+ getDestination() + " destinations=" + destinations + ", getDistance()=" + getDistance() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((destinations == null) ? 0 : destinations.hashCode());
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
		Path other = (Path) obj;
		if (destinations == null) {
			if (other.destinations != null)
				return false;
		} else if (!destinations.equals(other.destinations))
			return false;
		return true;
	}
}
