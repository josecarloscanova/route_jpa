package org.nanotek.model.jpa;

public class Destination {

	private Station from;
	
	private Station to;
	
	private Integer distance;

	public Destination() {}
	
	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	@Override
	public String toString() {
		return "Destination [from=" + from + ", to=" + to + ", distance=" + distance + "]";
	}

}

