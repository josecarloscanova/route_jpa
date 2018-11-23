package org.nanotek.app.service;

import org.nanotek.model.jpa.Station;
import org.springframework.stereotype.Service;

import com.google.common.collect.Table;

@Service
public class ShortestPathService {

	public void calculateShortesPath(Station from , Station to , Table<Station,Station,Integer> routeTable){ 
		
	}
	
}
