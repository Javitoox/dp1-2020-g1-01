package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.web.bind.annotation.RequestParam;

public interface AlumnoRepository extends CrudRepository<Alumno, String> {
	Collection<Alumno>findAll();
	
	@Query("SELECT a FROM Alumno a WHERE a.nickUsuario = :nickUsuario")
	public Alumno findByNick(@Param("nickUsuario") String nickUsuario);
	
	@Query("SELECT NICK_USUARIO FROM Alumno NICK_USUARIO WHERE NOMBRE_GRUPO= :nombreGrupo")
    public Collection<Alumno> findByGroup(@RequestParam("nombreGrupo") String nombreGrupo);
	
}
