package org.nanotek.model.jpa;

import java.io.Serializable;

import javax.persistence.OneToOne;

public class Route implements Serializable { 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 47475358828203170L;

	@OneToOne
	private Station from; 
	
	@OneToOne
	private Station destination;

	public Route() { 
	}

	public Route(Station from, Station destination) {
		super();
		this.from = from;
		this.destination = destination;
	}

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
	
	@Override
	public String toString() {
		return "Route [from=" + from + ", destination=" + destination + "]";
	}
	
}
