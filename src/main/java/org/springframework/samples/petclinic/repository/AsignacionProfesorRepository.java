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
	//select nombre_grupo from grupos where nombre_grupo not in  (SELECT nombre_grupo FROM ASIGNACIONES_PROFESOR )

	@Query("SELECT g.nombreGrupo FROM Grupo g WHERE g.nombreGrupo NOT IN (SELECT a.grupo.nombreGrupo FROM AsignacionProfesor a)")
	List<String> getFreeGroups();
}
