package org.springframework.samples.petclinic.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Grupo;
import org.springframework.samples.petclinic.model.TipoCurso;

public interface GrupoRepository extends CrudRepository<Grupo, String>{		
	public Set<Grupo> findAll();
	
	@Query("SELECT g.nombreGrupo FROM Grupo g JOIN g.cursos c WHERE c.cursoDeIngles = :curso")
	public List<String> findNameByCurso(@Param("curso")TipoCurso curso);
	
	@Query("SELECT c.cursoDeIngles FROM Grupo g JOIN g.cursos c WHERE g.nombreGrupo = :grupo")
	public List<String> findNameByGrupo(@Param("grupo")String grupo);
	
	@Query("SELECT g.nombreGrupo FROM Grupo g")
	public List<String> findAllGroupNames();
	
	@Query(value="select g.NOMBRE_GRUPO from grupos g where g.NOMBRE_GRUPO  not in(select g.NOMBRE_GRUPO  from grupos g join ASIGNACIONES_PROFESOR  a where g.NOMBRE_GRUPO = a.GRUPO_NOMBRE_GRUPO  )", nativeQuery=true)
	public List<String> findAllEmptyGroups();
	
	@Query("SELECT g.alumnos FROM Grupo g WHERE g.nombreGrupo = :grupo")
	//@Query(value = "SELECT count(distinct(nick_usuario)) FROM GRUPOS natural join alumnos where grupos_nombre_grupo = :grupo", nativeQuery = true)
	public List<Alumno> numAlumnosGrupo(@Param("grupo") String grupo); 
	//SELECT count(distinct(nick_usuario)) FROM GRUPOS natural join alumnos where grupos_nombre_grupo = 'grupo1'
}