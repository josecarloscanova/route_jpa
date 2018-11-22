package com.toughtworks.path.model.jpa;

import java.io.Serializable;

import javax.persistence.OneToOne;

public class Route implements Serializable { 
	
	@OneToOne
	private Station from; 
	
	@OneToOne
	private Station destination;

	public Station getFrom() {
		return from;
	}

	public void setFrom(Station from) {
		this.from = from;
	}

	public Station getDestination() {
		return destination;
	}

	public void setDestination(Station destination) {
		this.destination = destination;
	} 
	
}
