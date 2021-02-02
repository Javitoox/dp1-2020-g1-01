package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Curso;
import org.springframework.samples.petclinic.model.TipoCurso;

public interface CursoRepository extends CrudRepository<Curso, TipoCurso> {
	public List<Curso> findAll();
}
