package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Premiado;
import org.springframework.samples.petclinic.model.WallOfFame;

public interface PremiadoRepository extends CrudRepository<Premiado, Integer> {
	
	@Query("SELECT p FROM Premiado p WHERE p.walloffames.fechaWall LIKE :fechawall%")
	public List<Premiado> premiadosPorFecha(@Param("fechawall") String fechaWall);
	
    @Query(value="SELECT TOP 1 fechawall FROM walloffames ORDER BY FECHAWALL DESC", nativeQuery = true)
    public String lastWallOfFame();
}
    
