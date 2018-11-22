package org.nanotek.app.service.repository;

import org.nanotek.model.jpa.Station;
import org.springframework.data.repository.CrudRepository;

public interface StationRepository extends CrudRepository<Station, String> {

}
