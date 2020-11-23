package org.springframework.samples.petclinic.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Profesor;

public interface ProfesorRepository extends CrudRepository<Profesor, String>{
	
	@Query("SELECT p FROM Profesor p WHERE p.nickUsuario = :nickUsuario")
	public Profesor findByNick(@Param("nickUsuario") String nickUsuario);

}
