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
	
	@Query("SELECT g.nombreGrupo FROM Grupo g WHERE g.nombreGrupo NOT IN (SELECT DISTINCT a.grupos.nombreGrupo FROM Alumno a WHERE a.grupos.nombreGrupo != 'null' and a.fechaMatriculacion IS NOT null and a.fechaBaja IS null)")
	public List<String> findAllEmptyGroups();
	
	@Query("SELECT g.alumnos FROM Grupo g WHERE g.nombreGrupo = :grupo")
	public List<Alumno> numAlumnosGrupo(@Param("grupo") String grupo); 
	
	@Query("SELECT g.nombreGrupo FROM Grupo g WHERE g.nombreGrupo NOT IN (SELECT a.grupos.nombreGrupo FROM Alumno a where a.nickUsuario = :nickUsuario)")
	public List<String> findGroupsToAssign(@Param("nickUsuario") String nickUsuario); 
	
}