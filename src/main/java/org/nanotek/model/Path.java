package org.nanotek.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Class to store calculated paths, 
 * "On theory, this list is valid if a.from == a1.to.. always. otherwise the path is not valid. 
 * 
 */
public class Path {
	
	private List<Destination> destinations = new LinkedList<>();

	public Path() {
		super();
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

	@Override
	public String toString() {
		return "Path [destinations=" + destinations + " Distance " + getDistance() + "]";
	}
	
	public Destination getDestination() { 
		Destination dest = null; 
		if(destinations.size() > 0) { 
			dest  = new Destination(destinations.get(0).getFrom() , destinations.get(destinations.size() -1).getTo() , getDistance());
		}
		return Optional.ofNullable(dest).orElse(new Destination());
	}
}
