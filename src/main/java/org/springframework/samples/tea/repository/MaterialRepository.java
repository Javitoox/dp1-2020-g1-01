package org.springframework.samples.tea.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.tea.model.Material;


public interface MaterialRepository extends CrudRepository<Material, Integer> {

	@Query("Select m from Material m where m.profesores.nickUsuario =:nickProfesor")
	public List<Material> getMaterialByProfesor(@Param("nickProfesor")String nickProfesor);

	@Query("Select m from Feedback f join f.materiales m where f.alumnos.nickUsuario =:nickUsuario")
	public List<Material> getMaterialByAlumno(@Param("nickUsuario")String nickUsuario);

}
