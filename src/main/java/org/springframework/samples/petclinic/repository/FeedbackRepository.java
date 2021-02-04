package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Feedback;
import org.springframework.samples.petclinic.model.Material;

public interface FeedbackRepository extends CrudRepository<Feedback, Integer> {

	@Query("select f from Feedback f where f.materiales.id=:idMaterial")
	public List<Feedback> findFeedbackByMaterial(@Param("idMaterial") Integer idMaterial);

	@Query("select f from Feedback f where(f.alumnos=:alumno and f.materiales=:material)")
	public Feedback getFeedbackByMaterialAndStudent(@Param("alumno")Alumno a, @Param("material")Material m);
	
	@Query("select f from Feedback f where f.alumnos.nickUsuario =:nickUsuario")
	public List<Feedback> findFeedbacksByStudent(@Param("nickUsuario") String nickUsuario);
}
