package org.nanotek.model.jpa;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity 
public class Destination {

	@EmbeddedId 
	private Route route; 
	
	@Column
	private Integer distance;

	public Destination() {}
	
	public Destination(Route route, Integer distance) {
		super();
		this.route = route;
		this.distance = distance;
	}

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	@Override
	public String toString() {
		return "Destination [route=" + route + ", distance=" + distance + "]";
	}
	
	
	
}

