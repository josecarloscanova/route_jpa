package org.nanotek.model;

/**
 * to be used soon.
 * @author jose.carlos.canova@gmail.com
 */
public class StationNode implements Node<Station>{
	
	private Station station;

	public StationNode(Station s) { 
			this.station = s;
	}

	public Station getStation() {
		return station;
	}

	public Station element() { 
		return station;
	}
	
}
