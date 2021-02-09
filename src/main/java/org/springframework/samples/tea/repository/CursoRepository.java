package org.springframework.samples.tea.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.tea.model.Curso;

public interface CursoRepository extends CrudRepository<Curso, String> {
	public List<Curso> findAll();
}
