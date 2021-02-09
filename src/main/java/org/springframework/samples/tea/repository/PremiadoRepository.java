package org.springframework.samples.tea.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.tea.model.Premiado;

public interface PremiadoRepository extends CrudRepository<Premiado, Integer> {

	@Query("SELECT p FROM Premiado p WHERE p.walloffames.fechaWall LIKE :fechawall%")
	public List<Premiado> premiadosPorFecha(@Param("fechawall") String fechaWall);

    @Query(value="SELECT TOP 1 fechawall FROM walloffames ORDER BY FECHAWALL DESC", nativeQuery = true)
    public String lastWallOfFame();

    @Query("SELECT count(p) from Premiado p WHERE (p.walloffames.fechaWall =:fechawall and p.alumnos.nickUsuario =:nickUsuario)")
    public Integer numAparicionesEnFecha(@Param("fechawall") String fechaWall, @Param("nickUsuario") String nickUsuario);

    @Query("SELECT p from Premiado p WHERE (p.alumnos.nickUsuario =:nickUsuario)")
    public List<Premiado> aparacionesPorAlumno(@Param("nickUsuario") String nickUsuario);



}

