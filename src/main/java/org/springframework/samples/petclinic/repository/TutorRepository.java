package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Tutor;

public interface TutorRepository extends CrudRepository<Tutor, String>{

	@Query("SELECT t FROM Tutor t WHERE t.nickUsuarioTutor = :nickUsuario")
	public Tutor findByNick(@Param("nickUsuario") String nickUsuario);
	
	public List<Tutor>findAll();

}
