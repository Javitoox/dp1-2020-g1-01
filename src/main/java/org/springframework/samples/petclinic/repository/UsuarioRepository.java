package org.springframework.samples.petclinic.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Usuario;

public interface UsuarioRepository extends  CrudRepository<Usuario, String>{
	
	@Query("SELECT u FROM Usuario u WHERE u.nickUsuario = :nickUsuario")
	public Usuario findByNick(@Param("nickUsuario") String nickUsuario);
}
