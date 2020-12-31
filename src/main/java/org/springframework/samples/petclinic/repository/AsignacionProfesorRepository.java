package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.AsignacionProfesor;
import org.springframework.samples.petclinic.model.AsignacionProfesorKey;

public interface AsignacionProfesorRepository extends CrudRepository<AsignacionProfesor, AsignacionProfesorKey>{
	//	@Query("SELECT a FROM AsignacionProfesor a JOIN a.profesor p WHERE a.p.nickProfesor=:nickProfesor")

	@Query("SELECT a FROM AsignacionProfesor a WHERE a.profesor.nickUsuario=:nickProfesor")
	List<AsignacionProfesor> getAsignacionesByProfesor(@Param("nickProfesor")String nickProfesor);
	
	//SELECT g.NOMBRE_GRUPO  FROM GRUPOS g where g.NOMBRE_GRUPO NOT IN 
	//(SELECT gg.nombre_grupo from grupos gg join ASIGNACIONES_PROFESOR  a where a.NOMBRE_GRUPO  = gg.NOMBRE_GRUPO  
	//AND a.NICK_PROFESOR = 'MaribelJavi')
	
//	@Query("SELECT g.nombreGrupo FROM Grupo g WHERE g.nombreGrupo NOT IN (SELECT gg.nombreGrupo FROM Grupo gg "
//			+ "JOIN gg.asignacionProfesor ggg WHERE ggg.nombreGrupo=gg.nombreGrupo AND ggg.profesor.nickUsuario:=nickProfesor)")
	
//	SELECT gg.nombre_grupo from grupos gg join ASIGNACIONES_PROFESOR  a where a.NOMBRE_GRUPO <> g.NOMBRE_GRUPO  AND a.NICK_PROFESOR = :nickProfesor
//	@Query("SELECT gg.nombre_grupo from grupos gg join gg.asignacionProfesor a where a.nombreGrupo <> g.nombreGrupo  AND a.profesor.nickUsuario = :nickProfesor")
//	@Query("SELECT g.nombreGrupo FROM Grupo g WHERE g.nombreGrupo NOT IN (SELECT a.nombreGrupo FROM AsignacionProfesor a)")
//	select nombre_grupo from grupos where nombre_grupo not in  (SELECT g.nombre_grupo from ASIGNACIONES_PROFESOR g WHERE NOT EXISTS
//	(SELECT gg.nombre_grupo FROM ASIGNACIONES_PROFESOR gg where gg.NOMBRE_GRUPO  <> g.NOMBRE_GRUPO  ))
	@Query("SELECT g.nombreGrupo FROM Grupo g WHERE g.nombreGrupo NOT IN (SELECT a.grupo.nombreGrupo FROM AsignacionProfesor a)")
	List<String> getFreeGroups();
}
