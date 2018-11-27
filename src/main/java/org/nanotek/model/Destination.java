package org.nanotek.model;

import java.util.Objects;

/**
 * Represents an "Edge" for the problem proposed 
 * 
 * @author jose.carlos.canova@gmail.com
 *
 */
public class Destination extends Route{

	private Integer distance;

	public Destination() {}
	
	public Destination(Station from, Station to, Integer distance) {
		super();
		this.from = from;
		this.to = to;
		this.distance = distance;
	}

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	public Station getFrom() {
		return from;
	}

	public void setFrom(Station from) {
		this.from = from;
	}

	public Station getTo() {
		return to;
	}

	public void setTo(Station to) {
		this.to = to;
	}

	@Override
	public String toString() {
		return "Destination [from=" + from + ", to=" + to + ", distance=" + distance + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(from,to,distance);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Destination other = (Destination) obj;
		if (distance == null) {
			if (other.distance != null)
				return false;
		} else if (!distance.equals(other.distance))
			return false;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		return true;
	}
	
}

