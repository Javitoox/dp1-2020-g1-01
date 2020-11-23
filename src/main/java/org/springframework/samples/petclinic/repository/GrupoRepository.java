package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Grupo;

public interface GrupoRepository extends CrudRepository<Grupo, Integer>{		
	Collection<Grupo> findAll();
	
	@Query("SELECT nombre_grupo FROM Grupo nombre_grupo WHERE curso_de_ingles = :curso")
	public List<Grupo> findByCurso(@Param("curso")String curso);

}
