package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Alumno;

public interface SolicitudRepository extends CrudRepository<Alumno, String> {
	
	
	@Query("SELECT alumno FROM Alumno alumno WHERE (alumno.fechaMatriculacion IS null and alumno.fechaBaja IS null and alumno.fechaSolicitud IS not null)")
	public List<Alumno>findStudentsNotAcceptedYet();

}
