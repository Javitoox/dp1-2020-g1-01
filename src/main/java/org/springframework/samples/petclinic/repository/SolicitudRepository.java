package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Solicitud;



public interface SolicitudRepository extends CrudRepository<Solicitud, String> {
	Collection<Solicitud>findAll();
	
//	@Query("SELECT a FROM Solicitud a WHERE a.nick_usuario = :nick_usuario")
//	public Solicitud findByNick(@Param("nick_usuario") String nick_usuario);

}
