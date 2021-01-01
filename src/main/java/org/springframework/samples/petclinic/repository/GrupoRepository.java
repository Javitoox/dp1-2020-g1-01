package org.springframework.samples.petclinic.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Grupo;

public interface GrupoRepository extends CrudRepository<Grupo, String>{		
	public Set<Grupo> findAll();
		
	@Query("SELECT g FROM Grupo g JOIN g.cursos c WHERE c.cursoDeIngles = :curso")
	public List<Grupo> findByCurso(@Param("curso")String curso);
	
	@Query("SELECT g.nombreGrupo FROM Grupo g JOIN g.cursos c WHERE c.cursoDeIngles = :curso")
	public List<String> findNameByCurso(@Param("curso")String curso);
	
	@Query("SELECT g.nombreGrupo FROM Grupo g")
	public List<String> findAllGroupNames();
	
//	@Query(value="SELECT g.nombreGrupo FROM Grupo g JOIN Alumno a WHERE a.grupos.nombreGrupo = g.nombreGrupo")
	@Query(value="select g.NOMBRE_GRUPO from grupos g where g.NOMBRE_GRUPO  not in(select g.NOMBRE_GRUPO  from grupos g join ASIGNACIONES_PROFESOR  a where g.NOMBRE_GRUPO = a.NOMBRE_GRUPO  )", nativeQuery=true)
	public List<String> findAllEmptyGroups();
}