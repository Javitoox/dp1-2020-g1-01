package org.springframework.samples.tea.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.tea.model.Profesor;

public interface ProfesorRepository extends CrudRepository<Profesor, String>{

}
