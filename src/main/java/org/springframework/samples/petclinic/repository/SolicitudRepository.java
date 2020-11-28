package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Alumno;



public interface SolicitudRepository extends CrudRepository<Alumno, String> {
	
	@Query(value="SELECT * FROM ALUMNOS WHERE FECHA_SOLICITUD IS NOT NULL",nativeQuery = true)
	public List<Alumno>findStudentsNotAcceptedYet();

}
