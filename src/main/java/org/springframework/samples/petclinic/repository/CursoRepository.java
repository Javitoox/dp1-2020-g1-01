package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Curso;

public interface CursoRepository extends CrudRepository<Curso, String> {

}
