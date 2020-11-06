package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Alumno;

public interface AlumnoRepository extends CrudRepository<Alumno,Integer> {
	Collection<Alumno>findAll();

}
