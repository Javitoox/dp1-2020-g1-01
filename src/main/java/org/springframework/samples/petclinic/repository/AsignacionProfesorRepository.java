package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.AsignacionProfesor;
import org.springframework.samples.petclinic.model.AsignacionProfesorKey;

public interface AsignacionProfesorRepository extends CrudRepository<AsignacionProfesor, AsignacionProfesorKey>{

	@Query("SELECT a FROM AsignacionProfesor a WHERE a.profesor.nickUsuario=:nickProfesor")
	List<AsignacionProfesor> getAsignacionesByProfesor(@Param("nickProfesor")String nickProfesor);
	
	@Query("SELECT a.profesor.nombreCompletoUsuario FROM AsignacionProfesor a WHERE a.grupo.nombreGrupo=:nombreGrupo")
	List<String> getAsignacionesByGroup(@Param("nombreGrupo")String nombreGrupo);
	
}
