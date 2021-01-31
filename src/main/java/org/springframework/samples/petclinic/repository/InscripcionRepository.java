package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Inscripcion;

public interface InscripcionRepository extends CrudRepository<Inscripcion, Integer>{
	
	public List<Inscripcion> findAll();
	
	@Query("SELECT e.inscripciones FROM Evento e WHERE e.id = :id")
	public List<Inscripcion> findInscripcionesEvento(@Param("id") Integer id);
}
