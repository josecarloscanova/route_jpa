package org.nanotek.model;

public class Destination {

	private Station from;
	
	private Station to;
	
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

	@Override
	public String toString() {
		return "Destination [from=" + from + ", to=" + to + ", distance=" + distance + "]";
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

}

