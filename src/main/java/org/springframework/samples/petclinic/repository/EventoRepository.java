package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Evento;

public interface EventoRepository extends CrudRepository<Evento, Integer>{
	
	@Query("SELECT evento FROM Evento evento")
	public List<Evento>findAllEvents();

}
