package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Tutor;

public interface TutorRepository extends CrudRepository<Tutor, String>{

}
