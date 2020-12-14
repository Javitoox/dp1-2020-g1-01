package org.springframework.samples.petclinic.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Grupo;

public interface GrupoRepository extends CrudRepository<Grupo, String>{		
	Set<Grupo> findAll();
	@Query("SELECT g FROM Grupo g JOIN g.cursos c WHERE c.cursoDeIngles = :curso")
	public List<Grupo> findByCurso(@Param("curso")String curso);
	
}
