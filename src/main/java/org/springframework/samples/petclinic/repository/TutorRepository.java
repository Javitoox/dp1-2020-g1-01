package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Tutor;

public interface TutorRepository extends CrudRepository<Tutor, String>{

	@Query(value = "SELECT t FROM Tutor t WHERE t.nickUsuario = :nickUsuario OR t.dniUsuario = :nif")
	public Tutor findByNickAndNif(@Param("nickUsuario") String nickUsuario, @Param("nif") String nif);

	@Query("SELECT t FROM Tutor t WHERE t.nickUsuario = :nickUsuario")
	public Tutor findByNick(@Param("nickUsuario") String nickUsuario);

	public List<Tutor>findAll();

	@Query("SELECT p.correoElectronicoUsuario FROM Profesor p JOIN p.asignaciones a WHERE a.grupo.nombreGrupo =:nombreGrupo")
	public List<String> getTeacherByGroup(@Param("nombreGrupo") String nombreGrupo);

}
