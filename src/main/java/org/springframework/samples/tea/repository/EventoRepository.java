package org.springframework.samples.tea.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.tea.model.Evento;

public interface EventoRepository extends CrudRepository<Evento, Integer>{

	@Query("SELECT evento FROM Evento evento")
	public List<Evento>findAllEvents();

	@Query("SELECT e FROM Evento e WHERE e.title = :title AND e.start = :start")
	public Evento findExist(@Param("title") String title, @Param("start") LocalDate start);

}
