package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Profesor;

public interface ProfesorRepository extends CrudRepository<Profesor, String>{

}
