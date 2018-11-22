package org.nanotek.app.service.repository;

import java.util.Set;

import org.nanotek.model.jpa.Destination;
import org.nanotek.model.jpa.Route;
import org.nanotek.model.jpa.Station;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface DestinationRepository extends CrudRepository<Destination, Route> {

	@Query("from Destination d where d.route.from = :station")
	Set<Destination> fetchDestinations(@Param("station") Station station);

	Set<Destination> findByRoute(Route route);
	
	@Query("from Destination d where d.route.from  = :origin or d.route.destination = :target")
	Set<Destination> findByOriginTarget(@Param("origin") Station origin , @Param ("target") Station target);
}
