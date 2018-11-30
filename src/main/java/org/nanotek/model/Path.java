package org.nanotek.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;

/**
 * Class to store calculated paths, 
 * "On theory, this list is valid if a.from == a1.to.. always. otherwise the path is not valid. 
 * 
 * It stores a "Set" for destinations about the order that they were inserted on "container" Path. 
 * 
 * Usefull to verify if some algorithm to 
 * 
 */
//TODO: verify a constraint of the problem.
public class Path<T extends Station> implements Routable<T>{
	
	private LinkedList<Destination<T>> destinations = new LinkedList<>();

	public Path(Destination<T> destination) {
		super();
		destinations.add(destination);
	}
	
	public Destination<T> addDestination(Destination<T> destination) { 
		destinations.add(destination);
		return destination;
	}
	
	public List<Destination<T>> getDestinations(){ 
		return destinations;
	}
	
	public Destination<T> destination() {
		return getDestination();
	}
	
	public Integer getDistance() { 
		Optional<Integer> sum = destinations.stream().map(x -> x.getDistance()).reduce(new BinaryOperator<Integer>() {
			@Override
			public Integer apply(Integer x, Integer y) {
				return x < Integer.MAX_VALUE ? x+y : Integer.MAX_VALUE;
			}
		});
		return sum.get();
	}

	public Destination<T> getDestination() { 
		Destination<T> dest = null; 
		if(destinations.size() > 0) { 
			dest  = new Destination<>(destinations.get(0).getFrom() , destinations.getLast().getTo() , getDistance());
		}
		return Optional.ofNullable(dest).isPresent() ? dest : new Destination<T>(Station.nullStation() , Station.nullStation() , Integer.MAX_VALUE);
	}


	//Used to print the path as the canonical form..
	//TODO: check a simple form of reducion
	public String canonicalForm(boolean weighted) { 
		StringBuffer form = new StringBuffer();
		destinations.stream().forEach(x -> {
						form.append(x.from.toString());
						form.append('-');
						if(x.to.equals(getDestination().to))
							form.append(x.to);
						});
		return weighted ? form.append(' ').append(getDistance()).toString() : form.toString() ;
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

	//TODO: modigy the equals
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Path<?> other = (Path<?>) obj;
		if (destinations == null) {
			if (other.destinations != null)
				return false;
		} else if (!destinations.equals(other.destinations))
			return false;
		return true;
	}

}
