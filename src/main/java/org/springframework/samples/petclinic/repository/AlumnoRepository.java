package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Alumno;


public interface AlumnoRepository extends CrudRepository<Alumno, String> {
	
	
	@Query("SELECT a FROM Alumno a WHERE a.nickUsuario = :nickUsuario")
	public Alumno findByNick(@Param("nickUsuario") String nickUsuario);
	
	public List<Alumno> findAll();
	
}
