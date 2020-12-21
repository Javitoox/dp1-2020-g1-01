package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Premiado;

public interface PremiadoRepository extends CrudRepository<Premiado, Integer> {
	
	@Query(value = "SELECT * FROM premiados WHERE walloffames_fechawall = :fechawall", nativeQuery = true)
	public List<Premiado> premiadosPorFecha(@Param("fechawall") String fechaWall);
	
}
