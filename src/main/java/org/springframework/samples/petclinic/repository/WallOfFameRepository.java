package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.WallOfFame;

public interface WallOfFameRepository extends CrudRepository<WallOfFame, String>{

	
}
